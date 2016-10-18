package database.customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Shop;
import domain.customer.Customer;
import domain.customer.Observer;
import exception.DatabaseException;

public class CustomerDatabaseSQL implements ICustomerDatabase {

	// basis voor initialiseren databaseconnectie
	private Connection connection;
	private PreparedStatement statement;
	private Properties properties;
	private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7139719";
	private Shop shop;

	public CustomerDatabaseSQL(Shop shop) {
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

		this.shop = shop;
	}

	private void createCustomerTable() throws DatabaseException {
		String sql = "CREATE TABLE customer " + "(id INTEGER not NULL, " + " firstName VARCHAR(255), "
				+ " lastName VARCHAR(255), " + " email VARCHAR(255), " + " subscribed BIT, " + " PRIMARY KEY ( id ))";

		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Customer getCustomer(int id) throws DatabaseException {
		this.open();

		Customer c = null;
		String sql = "SELECT * FROM customer " + "WHERE id ='" + id + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();

			if (result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				Boolean subscribed = Boolean.parseBoolean(result.getString("subscribed"));

				c = new Customer(firstName, lastName, email, id, subscribed, shop);
			} else {
				throw new DatabaseException("There is no customer with the given ID");
			}

			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return c;
	}

	@Override
	public List<Customer> getAllCustomers() throws DatabaseException {
		this.open();

		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customer";

		try {
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				int id = Integer.parseInt(result.getString("id"));
				Boolean subscribed = Boolean.parseBoolean(result.getString("subscribed"));

				Customer c = new Customer(firstName, lastName, email, id, subscribed, shop);
				customers.add(c);
			}
			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return customers;
	}

	@Override
	public void addCustomer(Customer c) throws DatabaseException {
		if (c == null) {
			throw new DatabaseException("No customer to add");
		} else {
			this.open();

			int id = c.getId();
			String checkIdAlreadyExists = "SELECT * FROM customer " + "WHERE id ='" + id + "'";
			String sql = "INSERT INTO customer(id, firstName, lastName, email, subscribed)" + "VALUES(?,?,?,?,?)";

			try {
				this.statement = connection.prepareStatement(checkIdAlreadyExists);
				ResultSet result = this.statement.executeQuery(checkIdAlreadyExists);

				// if the ID already exists, we quit
				if (result.next()) {
					throw new DatabaseException("There is already a customer with the given ID");
				}

				result.close();

				this.statement = this.connection.prepareStatement(sql);
				this.statement.setInt(1, c.getId());
				this.statement.setString(2, c.getFirstName());
				this.statement.setString(3, c.getLastName());
				this.statement.setString(4, c.getEmail());
				this.statement.setBoolean(5, c.isSubscribed());
				this.statement.execute();
			} catch (Exception e) {
				throw new DatabaseException(e.getMessage());
			} finally {
				this.close();
			}
		}
	}

	@Override
	public void updateCustomer(Customer c) throws DatabaseException {
		this.open();

		String sql = "UPDATE customer " + "SET subscribed ='" + (c.isSubscribed() ? 1 : 0) + "' " + " WHERE id ='"
				+ c.getId() + "'";

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
	public void deleteCustomer(int id) throws DatabaseException {
		this.open();

		String sq1 = "DELETE FROM customer " + "WHERE id = '" + id + "'";
		try {
			this.statement = this.connection.prepareStatement(sq1);
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public List<Observer> getSubscribers() throws DatabaseException {
		this.open();

		List<Observer> subscribers = new ArrayList<Observer>();
		String sq1 = "SELECT * FROM customer " + "WHERE subscribed = 1";

		try {
			this.statement = this.connection.prepareStatement(sq1);
			ResultSet result = this.statement.executeQuery();

			while (result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				int id = Integer.parseInt(result.getString("id"));

				Customer c = new Customer(firstName, lastName, email, id, true, shop);
				subscribers.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return subscribers;
	}

	@Override
	public void open() throws DatabaseException {
		try {
			// make connection
			this.connection = DriverManager.getConnection(URL, this.properties);

			// does table customer exist?
			java.sql.DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "customer", null);
			if (!tables.next()) {
				createCustomerTable();
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
