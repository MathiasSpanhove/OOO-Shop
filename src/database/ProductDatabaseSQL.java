package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;

public class ProductDatabaseSQL implements IProductDatabase {
	
	//basis voor initialiseren databaseconnectie
	private Connection connection;
	private PreparedStatement statement;
	
	public ProductDatabaseSQL() throws DatabaseException, ClassNotFoundException{
		open();
	}

	@Override
	public Product getProduct(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		
		try{
			String sql = "SELECT * FROM sql7139719.Product";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				int id = Integer.parseInt(result.getString("id"));
				String title = result.getString("title");
				String type = result.getString("type");
				String lastBorrowedString = result.getString("lastBorrowed");
				LocalDate lastBorrowed = (lastBorrowedString.equals("null") ? null : LocalDate.parse(lastBorrowedString));
				String stateString = result.getString("state");
				
				String value = Products.getProductCharValue(type.charAt(0));
				Product newProduct = Products.valueOf(value).createProduct(title, id, lastBorrowed, stateString);
				
				products.add(newProduct);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return products;
	}

	@Override
	public void addProduct(Product p) throws DatabaseException {
		if(p == null){
			throw new DatabaseException("No product to add");
		}else{
			String sql = "INSERT INTO Product(id, title, type, lastBorrowed, state)" + "VALUES(?,?,?,?,?)";
			try{
				statement = connection.prepareStatement(sql);
				statement.setString(1, "" + p.getId());
				statement.setString(2, p.getTitle());
				statement.setString(3, p.getClass().getSimpleName());
				statement.setString(4, "" + p.getLastBorrowed());
				statement.setString(5, "" + p.getCurrentState());
				statement.execute();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				close();
			}
		}
	}

	@Override
	public void updateProduct(Product p) throws DatabaseException {
		//TODO: volledige update
//		String sql = "UPDATE r0448327.product SET borrowed ='" + "" + borrowed + "' WHERE id= '" + id+"'";
//		
//		try{
//			statement = connection.prepareStatement(sql);
//			statement.execute();
//		}catch(Exception e){
//			throw new DatabaseException(e.getMessage());
//		}finally{
//			close();
//		}
	}

	@Override
	public void deleteProduct(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open() {
		//properties voor verbinding maken
		Properties properties = new Properties();
		String url = "jdbc:mysql://sql7.freesqldatabase.com/sql7139719";
		
		properties.setProperty("user","sql7139719");
		properties.setProperty("password", "nT6fJKVEci");
		properties.setProperty("ssl", "true");
		//properties.setProperty("sslfactory", "org.mysql.ssl.NonValidatingFactory");
		
		//connectie maken met gegeven databank met opgegeven credentials beschreven in properties
		try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	this.connection = DriverManager.getConnection(url, properties);
	    	
	    }catch (SQLException e){
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e){
	    	e.printStackTrace();
	    }
	}

	@Override
	public void close() {
		try{
			statement.close();
			connection.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}