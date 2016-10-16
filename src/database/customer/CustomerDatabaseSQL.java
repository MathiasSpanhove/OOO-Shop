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
	
	@Override
	public Customer getCustomer(int id) throws DatabaseException {
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() throws DatabaseException {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customer";
		
		try{
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String email = result.getString("email");
				int id = Integer.parseInt(result.getString("id"));
				Boolean subscribed = result.getBoolean("subscribed");
				
				Customer c = new Customer(firstName, lastName, email, id, subscribed, shop);
				customers.add(c);
			}
			return customers;
		}catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}finally{
			this.close();
		}
	}



	@Override
	public void addCustomer(Customer c) throws DatabaseException {
		if(c == null){
			throw new DatabaseException("Nothing to add");
		}
		else{
		this.open();
		String sql = "INSERT INTO Customer (firstname, lastname, email)" + " VALUES(?,?,?)";
		try{
			statement = connection.prepareStatement(sql);
			statement.setString(1, c.getFirstName());
			statement.setString(2, c.getLastName());
			statement.setString(3, c.getEmail());
			statement.execute();
			System.out.println("Great succes");
		}catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}finally{
			this.close();
		}
		}
	}

	@Override
	public void updateCustomer(Customer c) throws DatabaseException {
		// TODO Auto-generated method stub
		
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
	public List<Customer> getSubscribers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void open() {
		try {
			this.connection = DriverManager.getConnection(URL, this.properties);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			if (statement != null) {
		    	statement.close();
			}
			if (connection != null) {
		    	connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
