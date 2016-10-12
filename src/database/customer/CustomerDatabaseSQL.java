package database.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCustomer(Customer c) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer c) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Customer> getSubscribers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
