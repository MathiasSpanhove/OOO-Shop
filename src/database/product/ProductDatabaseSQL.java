package database.product;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.DatabaseMetaData;

import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;

public class ProductDatabaseSQL implements IProductDatabase {

	// basis voor initialiseren databaseconnectie
	private Connection connection;
	private PreparedStatement statement;
	private Properties properties;
	private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7139719";

	public ProductDatabaseSQL() {
		// properties voor verbinding maken
		this.properties = new Properties();
		this.properties.setProperty("user", "sql7139719");
		this.properties.setProperty("password", "nT6fJKVEci");
		this.properties.setProperty("ssl", "true");
		this.properties.setProperty("sslfactory", "org.mysql.ssl.NonValidatingFactory");

		// connectie maken met gegeven databank met opgegeven credentials
		// beschreven in properties
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createProductTable() throws DatabaseException {
		String sql = "CREATE TABLE product " + "(id INTEGER not NULL, " + " title VARCHAR(255), "
				+ " type VARCHAR(255), " + " lastBorrowed VARCHAR(255), " + " state VARCHAR(255), "
				+ " PRIMARY KEY ( id ))";

		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Product getProduct(int id) throws DatabaseException {
		this.open();

		Product product = null;
		String sql = "SELECT * FROM product " + "WHERE id ='" + id + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();

			if (result.next()) {
				String title = result.getString("title");
				String type = result.getString("type");
				String lastBorrowedString = result.getString("lastBorrowed");
				LocalDate lastBorrowed = (lastBorrowedString.equals("null") ? null
						: LocalDate.parse(lastBorrowedString));
				String stateString = result.getString("state");

				String value = Products.getProductCharValue(type.charAt(0));
				product = Products.valueOf(value).createProduct(title, id, lastBorrowed, stateString);
			} else {
				throw new DatabaseException("There is no product with the given ID");
			}

			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return product;
	}

	@Override
	public List<Product> getAllProducts() throws DatabaseException {
		this.open();

		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();

			while (result.next()) {
				int id = Integer.parseInt(result.getString("id"));
				String title = result.getString("title");
				String type = result.getString("type");
				String lastBorrowedString = result.getString("lastBorrowed");
				LocalDate lastBorrowed = (lastBorrowedString.equals("null") ? null
						: LocalDate.parse(lastBorrowedString));
				String stateString = result.getString("state");

				String value = Products.getProductCharValue(type.charAt(0));
				Product newProduct = Products.valueOf(value).createProduct(title, id, lastBorrowed, stateString);

				products.add(newProduct);
			}

			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return products;
	}

	@Override
	public void addProduct(Product p) throws DatabaseException {
		if (p == null) {
			throw new DatabaseException("No product to add");
		} else {
			this.open();

			int id = p.getId();
			String checkIdAlreadyExists = "SELECT * FROM product " + "WHERE id ='" + id + "'";
			String sql = "INSERT INTO product(id, title, type, lastBorrowed, state)" + "VALUES(?,?,?,?,?)";

			try {
				this.statement = connection.prepareStatement(checkIdAlreadyExists);
				ResultSet result = this.statement.executeQuery(checkIdAlreadyExists);

				// if the ID already exists, we quit
				if (result.next()) {
					throw new DatabaseException("There is already a product with the given ID");
				}

				result.close();

				this.statement = this.connection.prepareStatement(sql);
				this.statement.setString(1, "" + p.getId());
				this.statement.setString(2, p.getTitle());
				this.statement.setString(3, p.getClass().getSimpleName());
				this.statement.setString(4, "" + p.getLastBorrowed());
				this.statement.setString(5, "" + p.getCurrentState());
				this.statement.execute();
			} catch (Exception e) {
				throw new DatabaseException(e.getMessage());
			} finally {
				this.close();
			}
		}
	}

	@Override
	public void updateProduct(Product p) throws DatabaseException {
		this.open();

		String sql = "UPDATE product " + "SET state ='" + p.getCurrentState() + "', lastBorrowed ='"
				+ p.getLastBorrowed() + "' " + "WHERE id ='" + p.getId() + "'";

		try {
			this.statement = connection.prepareStatement(sql);
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void deleteProduct(int id) throws DatabaseException {
		this.open();

		String sql = "DELETE FROM product " + "WHERE id ='" + id + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void open() throws DatabaseException {
		try {
			// make connection
			this.connection = DriverManager.getConnection(URL, this.properties);

			// does table product exist?
			java.sql.DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "product", null);
			if (!tables.next()) {
				createProductTable();
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void close() throws DatabaseException {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}