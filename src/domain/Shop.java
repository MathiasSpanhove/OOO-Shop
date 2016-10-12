package domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import database.customer.ICustomerDatabase;
import database.product.IProductDatabase;
import database.product.ProductDatabaseSQL;
import domain.customer.Customer;
import domain.customer.Observer;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;
import exception.DomainException;

public class Shop implements Observable {
	
	private IProductDatabase productDb;
	private ICustomerDatabase customerDb;
	
	public Shop() {
		this.productDb = new ProductDatabaseSQL();
	}
	
	public Product getProduct(int id) throws DatabaseException {
		return productDb.getProduct(id);
	}
	
	public List<Product> getProducts() {
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
	
	public String toString() {
		if(productDb.getAllProducts().isEmpty()) {
			return "There are no products";
		}
		
		String output = "";
		
		for(Product p : productDb.getAllProducts()) {
			output += p.toString() + "\n";
		}
		
		return output;
	}
	
	public void close() {
		productDb.close();
	}
	
	//Observer

	@Override
	public void registerSubscriber(Observer o) throws DatabaseException, DomainException {
		if(o instanceof Customer) {
			Customer c = (Customer)o;
			
			if(c.isSubscribed()) {
				throw new DomainException("Customer is already subscribed.");
			}
			
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
