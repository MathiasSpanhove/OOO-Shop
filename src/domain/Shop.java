package domain;

import java.util.List;

import database.ProductDatabase;
import exception.DomainException;

public class Shop {
	
	private ProductDatabase db;
	
	public Shop() {
		this.db = new ProductDatabase();
	}
	
	public Product getProduct(int id) throws DomainException {
		return db.getProduct(id);
	}
	
	public List<Product> getProducts() {
		return db.getAllProducts();
	}
	
	public void addProduct(int id, String title, Character type) throws DomainException {
		db.addProduct(id, title, type);
	}
	
	public double getPrice(int id, int days) throws DomainException {
		Product p = getProduct(id);
		return p.getPrice(days);
	}
	
	public boolean isProductBorrowed(int id) throws DomainException {
		Product p = getProduct(id);
		return p.isBorrowed();
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
		db.write(db.getAllProducts());
	}

}
