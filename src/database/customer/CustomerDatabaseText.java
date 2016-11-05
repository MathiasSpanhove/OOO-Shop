package database.customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import domain.Shop;
import domain.customer.Customer;
import domain.customer.Observer;
import exception.DatabaseException;
import properties.PropertiesFile;

public class CustomerDatabaseText implements ICustomerDatabase {

	private Map<Integer, Customer> customers;
	private File file;
	private Shop shop;
	private volatile static CustomerDatabaseText uniqueInstance;
	
	private CustomerDatabaseText(Shop shop) {
		file = new File("CustomerDatabase.txt");
		this.customers = new HashMap<Integer, Customer>();
		this.shop = shop;
		open();
	}
	
	public static CustomerDatabaseText getInstance(PropertiesFile properties, Shop shop) {
		if(uniqueInstance == null) {
			synchronized (CustomerDatabaseText.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new CustomerDatabaseText(shop);
				}
			}
		}
		return uniqueInstance;
	}
	
	@Override
	public Customer getCustomer(int id) throws DatabaseException {
		if(!customers.containsKey(id)) {
			throw new DatabaseException("There is no customer with the given ID");
		}
		
		return customers.get(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<Customer>(customers.values());
	}

	@Override
	public void addCustomer(Customer c) throws DatabaseException {
		if(customers.containsKey(c.getId())) {
			throw new DatabaseException("There already is a customer with the given ID");
		}
		
		customers.put(c.getId(), c);
	}

	@Override
	public void updateCustomer(Customer c) throws DatabaseException {
		if(!customers.containsKey(c.getId())) {
			throw new DatabaseException("There is no customer with the given ID");
		}
		
		customers.put(c.getId(), c);
	}

	@Override
	public void deleteCustomer(int id) throws DatabaseException {
		if(!customers.containsKey(id)) {
			throw new DatabaseException("There is no customer with the given ID");
		}
		
		customers.remove(id);
	}

	@Override
	public Map<Integer, Observer> getSubscribers() throws DatabaseException {
		Map<Integer,Observer> subscribers = new HashMap<Integer, Observer>();
		
		for(Customer c : getAllCustomers()) {
			if(c.getMailSubscription().isSubscribed()) {
				subscribers.put(c.getId(), c.getMailSubscription());
			}
		}
		
		return subscribers;
	}

	@Override
	public void open() {
		try {
			Scanner scanner = new Scanner(file);
			Scanner line = null;
			
			while(scanner.hasNext()) {
				line = new Scanner(scanner.nextLine());
				line.useDelimiter(";");
				
				int id = line.nextInt();
				String firstName = line.next();
				String lastName = line.next();
				String email = line.next();
				boolean subscribed = line.nextBoolean();
				
				Customer newCustomer = new Customer(firstName, lastName, email, id, subscribed, shop);
				this.customers.put(id, newCustomer);						
			}
			if(scanner != null) {
				scanner.close();
			}
			if(line != null) {
				line.close();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			PrintWriter emptyFile = new PrintWriter (file);
			emptyFile.close();
			
			PrintWriter writer = new PrintWriter(file);
			
			for(Customer c : getAllCustomers()) {
				String line = c.toCSV();
				writer.println(line);
			}
			if(writer != null) {
				writer.close();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
