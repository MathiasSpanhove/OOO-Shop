package domain;

import java.time.LocalDate;
import java.util.List;

import database.ProductDatabaseText;
import exception.DomainException;

public class Shop {
	
	private ProductDatabaseText db;
	
	public Shop() {
		this.db = new ProductDatabaseText();
	}
	
	public Product getProduct(int id) throws DomainException {
		return db.getProduct(id);
	}
	
	public List<Product> getProducts() {
		return db.getAllProducts();
	}
	
	public void addProduct(int id, String title, Character type) throws DomainException {
		String value = Products.getProductCharValue(type);
		
		if (value != null) {
			Product newProduct = Products.valueOf(value).createProduct(title, id);
			db.addProduct(newProduct);
		} else {
			throw new DomainException("Invalid product type.");
		}
	}
	
	public double getPrice(int id, int days) throws DomainException {
		Product p = getProduct(id);
		return p.getPrice(days);
	}
	
	public boolean isProductBorrowed(int id) throws DomainException {
		Product p = getProduct(id);
		return p.isBorrowed();
	}
	
	public void borrowProduct(int id) throws DomainException {
		Product p = getProduct(id);
		p.setLastBorrowed(LocalDate.now());
		p.toggleBorrowed();
	}
	
	public void toggleBorrowed(int id) throws DomainException {
		Product p = getProduct(id);
		p.toggleBorrowed();
	}
	
	public String toString() {
		if(db.getAllProducts().isEmpty()) {
			return "There are no products";
		}
		
		String output = "";
		
		for(Product p : db.getAllProducts()) {
			output += p.toString() + "\n";
		}
		
		return output;
	}
	
	public void close() {
		db.close();
	}

}
