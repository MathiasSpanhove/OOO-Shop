package domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import database.customer.CustomerDatabaseSQL;
import database.customer.CustomerDatabaseText;
import database.customer.ICustomerDatabase;
import database.product.IProductDatabase;
import database.product.ProductDatabaseSQL;
import database.product.ProductDatabaseText;
import domain.customer.Customer;
import domain.customer.Observer;
import domain.product.Product;
import domain.product.enums.Products;
import domain.statistics.Statistics;
import exception.DatabaseException;
import exception.DomainException;

@SuppressWarnings("unused")
public class Shop implements Observable {
	
	private IProductDatabase productDb;
	private ICustomerDatabase customerDb;
	private List<Observer> observers;
	private Statistics statistics;
	
	public Shop() throws DatabaseException, DomainException {
		this.productDb = new ProductDatabaseSQL();
		this.customerDb = new CustomerDatabaseSQL(this);
		this.observers = new ArrayList<Observer>();
		this.statistics = new Statistics(this);
	}
	
	public void close() {
		try {
			productDb.close();
			customerDb.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	//Product
	
	public Product getProduct(int id) throws DatabaseException {
		return productDb.getProduct(id);
	}
	
	public List<Product> getProducts() throws DatabaseException {
		return productDb.getAllProducts();
	}
	
	public void addProduct(int id, String title, Character type) throws DatabaseException, DomainException {
		String value = Products.getProductCharValue(type);
		
		if (value != null) {
			Product newProduct = Products.valueOf(value).createProduct(title, id);
			productDb.addProduct(newProduct);
			notifySubscribers(newProduct);
		} else {
			throw new DomainException("Invalid product type.");
		}
	}
	
	public void updateProduct(Product p) throws DatabaseException {
		productDb.updateProduct(p);
	}
	
	public void deleteProduct(int id) throws DatabaseException {
		productDb.deleteProduct(id);
	}
	
	public double calculateFine(LocalDate lastBorrowed) {
		double fine = 0.0;
		double amountPerDay = 3.0;
		
		long days = Period.between(lastBorrowed, LocalDate.now()).getDays();
		
		if(days >= 5) {
			fine = (days - 4) * amountPerDay;
		}
		
		return fine;
	}
	
	public String productsToString() throws DatabaseException {
		if(productDb.getAllProducts().isEmpty()) {
			return "There are no products";
		}
		
		String output = "";
		
		for(Product p : productDb.getAllProducts()) {
			output += p.toString() + "\n";
		}
		
		return output;
	}
	
	//Customer
	
	public Customer getCustomer(int id) throws DatabaseException {
		return customerDb.getCustomer(id);
	}
	
	public List<Customer> getCustomers() throws DatabaseException {
		return customerDb.getAllCustomers();
	}
	
	public void addCustomer(String firstName, String lastName, String email, int id, boolean subscribed, Observable shop) 
			throws DatabaseException, DomainException {
		Customer newCustomer = new Customer(firstName, lastName, email, id, subscribed, shop);
		customerDb.addCustomer(newCustomer);
	}
	
	public void updateCustomer(Customer p) throws DatabaseException {
		customerDb.updateCustomer(p);
	}
	
	public void deleteCustomer(int id) throws DatabaseException {
		customerDb.deleteCustomer(id);
	}
	
	public String customersToString() throws DatabaseException {
		if(customerDb.getAllCustomers().isEmpty()) {
			return "There are no customers";
		}
		
		String output = "";
		
		for(Customer c : customerDb.getAllCustomers()) {
			output += c.toString() + "\n";
		}
		
		return output;
	}
	
	//Observer

	@Override
	public void registerSubscriber(Observer o) throws DatabaseException, DomainException {
		if(o instanceof Customer) {
			Customer c = (Customer) o;
			
			if(c.isSubscribed()) {
				throw new DomainException("Customer is already subscribed.");
			}
			
			this.observers.add(o);
			c.setSubscribed(true);
			customerDb.updateCustomer(c);
		}
	}

	@Override
	public void removeSubscriber(Observer o) throws DatabaseException, DomainException {
		if(o instanceof Customer) {
			Customer c = (Customer)o;
			
			if(!c.isSubscribed()) {
				throw new DomainException("Customer is not subscribed.");
			}
			
			this.observers.remove(o);
			c.setSubscribed(false);
			customerDb.updateCustomer(c);
		}
	}

	@Override
	public void notifySubscribers(Object arg) throws DatabaseException {
		for(Observer o : customerDb.getSubscribers()) {
			o.update(arg);
		}
	}
}
