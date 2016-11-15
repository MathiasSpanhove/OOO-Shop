package domain;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import database.customer.CustomerDatabaseSQL;
import database.customer.CustomerDatabaseText;
import database.customer.ICustomerDatabase;
import database.product.IProductDatabase;
import database.product.ProductDatabaseSQL;
import database.product.ProductDatabaseText;
import domain.customer.Customer;
import domain.customer.MailSubscription;
import domain.customer.Observer;
import domain.product.Product;
import domain.product.enums.Products;
import domain.product.factory.ProductFactory;
import domain.statistics.Statistics;
import exception.DatabaseException;
import exception.DomainException;
import exception.StateException;
import properties.PropertiesFile;

@SuppressWarnings("unused")
public class Shop implements Observable {

	private IProductDatabase productDb;
	private ICustomerDatabase customerDb;
	private Map<Integer, Observer> observers;
	private Statistics statistics;

	public Shop(PropertiesFile properties) throws DatabaseException, DomainException {
		if (properties.get("database").equals("text")) {
			this.productDb = ProductDatabaseText.getInstance(properties);
			this.customerDb = CustomerDatabaseText.getInstance(properties, this);
		} else if (properties.get("database").equals("sql")) {
			this.productDb = ProductDatabaseSQL.getInstance(properties);
			this.customerDb = CustomerDatabaseSQL.getInstance(properties, this);
		} else {
			throw new DatabaseException("Invalid database property: " + properties.get("database")
					+ "\nPlease change your shop.ini settings to database=text or database=sql");
		}

		this.observers = customerDb.getSubscribers();
		this.statistics = new Statistics(properties, this);
	}

	public void close() {
		try {
			productDb.close();
			customerDb.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	// Product

	public Product getProduct(int id) throws DatabaseException {
		return productDb.getProduct(id);
	}

	public List<Product> getProducts() throws DatabaseException {
		return productDb.getAllProducts();
	}

	public void addProduct(int id, String title, String type) throws DatabaseException, DomainException {
		Product newProduct = ProductFactory.createProduct(title, id, null, "available",
				Products.valueOf(type.toUpperCase()));
		productDb.addProduct(newProduct);
		notifySubscribers(newProduct);
	}

	public void updateProductInDB(Product p) throws DatabaseException {
		productDb.updateProduct(p);
	}
	
	public void removeProductInDB(int id) throws DatabaseException {
		productDb.deleteProduct(id);
	}
	
	public void borrowProduct(int id) throws StateException, DatabaseException {
		// state
		Product p = this.getProduct(id);
		p.borrowProduct();
		
		// db
		this.updateProductInDB(p);
	}

	public void returnProduct(int id, boolean damaged) throws StateException, DatabaseException {
		// state
		Product p = this.getProduct(id);
		p.returnProduct(damaged);
		
		// db
		this.updateProductInDB(p);
	}
	
	public void repairProduct(int id) throws StateException, DatabaseException {
		// state
		Product p = this.getProduct(id);
		p.repairProduct();
		
		// db
		this.updateProductInDB(p);
	}
	
	public void deleteProduct(int id) throws StateException, DatabaseException {
		// state
		Product p = this.getProduct(id);
		p.deleteProduct();
		
		// db
		this.removeProductInDB(id);
	}
	
	public double getProductFine(int id) throws DatabaseException {
		Product p = this.getProduct(id);
		return this.calculateFine(p.getLastBorrowed());
	}

	public double calculateFine(LocalDate lastBorrowed) {
		double fine = 0.0;
		double amountPerDay = 3.0;

		long days = Period.between(lastBorrowed, LocalDate.now()).getDays();

		if (days >= 5) {
			fine = (days - 4) * amountPerDay;
		}

		return fine;
	}

	public String productsToString() throws DatabaseException {
		if (productDb.getAllProducts().isEmpty()) {
			return "There are no products";
		}

		String output = "";

		for (Product p : productDb.getAllProducts()) {
			output += p.toString() + "\n";
		}

		return output;
	}

	// Customer

	public Customer getCustomer(int id) throws DatabaseException {
		return customerDb.getCustomer(id);
	}

	public List<Customer> getCustomers() throws DatabaseException {
		return customerDb.getAllCustomers();
	}

	public void addCustomer(String firstName, String lastName, String email, int id, boolean subscribed,
			Observable shop) throws DatabaseException, DomainException {
		Customer newCustomer = new Customer(firstName, lastName, email, id, subscribed, shop);
		customerDb.addCustomer(newCustomer);

		if (subscribed) {
			registerSubscriber(newCustomer.getMailSubscription());
		}
	}

	public void updateCustomer(Customer p) throws DatabaseException {
		customerDb.updateCustomer(p);
	}

	public void deleteCustomer(int id) throws DatabaseException {
		customerDb.deleteCustomer(id);
	}

	public String customersToString() throws DatabaseException {
		if (customerDb.getAllCustomers().isEmpty()) {
			return "There are no customers";
		}

		String output = "";

		for (Customer c : customerDb.getAllCustomers()) {
			output += c.toString() + "\n";
		}

		return output;
	}

	// Observer

	@Override
	public void registerSubscriber(Observer o) throws DatabaseException, DomainException {
		if (o instanceof MailSubscription) {
			MailSubscription m = (MailSubscription) o;

			if (this.observers.containsKey(m.getCustomer().getId())) {
				throw new DomainException("Customer is already subscribed.");
			}

			this.observers.put(m.getCustomer().getId(), m);
			m.setSubscribed(true);
			customerDb.updateCustomer(m.getCustomer());
		}
		if(o instanceof Statistics) {
			//TODO: what integer should it have?
			this.observers.put(0, o);
		}
	}

	@Override
	public void removeSubscriber(Observer o) throws DatabaseException, DomainException {
		if (o instanceof MailSubscription) {
			MailSubscription m = (MailSubscription) o;

			if (!this.observers.containsKey(m.getCustomer().getId())) {
				throw new DomainException("Customer is not subscribed.");
			}

			this.observers.remove(m.getCustomer().getId());
			m.setSubscribed(false);
			customerDb.updateCustomer(m.getCustomer());
		}
		if(o instanceof Statistics) {
			//TODO: what integer should it have?
			this.observers.remove(0);
		}
	}

	@Override
	public void notifySubscribers(Object arg) throws DatabaseException, DomainException {
		for (Entry<Integer, Observer> entry : this.observers.entrySet()) {
			entry.getValue().update(arg);
		}
	}
	
	//Statistics
	public List<String> getStatistics() throws DatabaseException {
		return this.statistics.statsToStringList();
	}
}
