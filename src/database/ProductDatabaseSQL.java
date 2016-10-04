package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Product;

public class ProductDatabaseSQL {
	
	//basis voor initialiseren databaseconnectie
	private java.sql.Connection connection;
	private PreparedStatement statement;
	
	private ProductDatabaseSQL() throws DatabaseException, ClassNotFoundException{
		
		//properties voor verbinding maken
		Properties properties = new Properties();
		String url = "url van onze databank";
		
		properties.setProperty("user","r0448327");
		properties.setProperty("password", "iederzeneigenwachtwoord");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		//connectie maken met gegeven databank met opgegeven credentials beschreven in properties
		try{
	    	Class.forName("org.postgresql.Driver");
	    	this.connection = DriverManager.getConnection(url, properties);
	    	
	    }catch (SQLException e){
	    	throw new DatabaseException(e.getMessage());
	    }catch (ClassNotFoundException e){
	    	throw new DatabaseException(e.getMessage());
	    }
	}
	
	//toevoegen van een product
	public void addProduct(Product product) throws DatabaseException{
		if(product == null){
			throw new DatabaseException("No product to add");
		}else{
			String sql = "INSERT INTO r0448327.product(id, title, state, lastBorrowed)" + "VALUES(?,?,?,?)";
			try{
				statement = connection.prepareStatement(sql);
				statement.setString(1, "" + product.getId());
				statement.setString(2, product.getTitle());
				statement.setString(3, "" + product.isBorrowed());
				statement.setString(4, "" + product.getLastBorrowed());
				statement.execute();
			}catch(Exception e){
				throw new DatabaseException(e.getMessage());
			}finally{
				closeConnection();
			}
		}
	}
	
	//alle producten opvragen, fout in omzetten naar datum en boolean
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();
		Product product = null;
		String sql = "SELECT * FROM r0448327.product";
		try{
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				int id = Integer.parseInt(result.getString("id"));
				String title = result.getString("title");
				Boolean borrowed = (Boolean) result.getString("state");
				LocalDate lastBorrowed = (LocalDate) result.getString("lastBorrowed");
				
				product = new Product(id, title, borrowed, lastBorrowed);
				products.add(product);
			}
			
			return products;
			
		}catch(Exception e){
			new DatabaseException(e.getMessage());
		}finally{
			closeConnection();
		}
	}
	
	//aanpassen van de uitgeleende status van een product
	public void setState(String id, Boolean borrowed) throws DatabaseException{
		String sql = "UPDATE r0448327.product SET borrowed ='" + "" + borrowed + "' WHERE id= '" + id+"'";
		
		try{
			statement = connection.prepareStatement(sql);
			statement.execute();
		}catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}finally{
			closeConnection();
		}
	}
	
	private void closeConnection() throws DatabaseException{
		try{
			statement.close();
			connection.close();
		}catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}
	}
	
	

}
