package domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import database.IProductDatabase;
import database.ProductDatabaseSQL;
import database.ProductDatabaseText;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;
import exception.DomainException;

public class Shop {
	
	private IProductDatabase db;
	
	public Shop() {
		this.db = new ProductDatabaseSQL();
	}
	
	public Product getProduct(int id) throws DatabaseException {
		return db.getProduct(id);
	}
	
	public List<Product> getProducts() {
		return db.getAllProducts();
	}
	
	public void addProduct(int id, String title, Character type) throws DatabaseException, DomainException {
		String value = Products.getProductCharValue(type);
		
		if (value != null) {
			Product newProduct = Products.valueOf(value).createProduct(title, id);
			db.addProduct(newProduct);
		} else {
			throw new DomainException("Invalid product type.");
		}
	}
	
	public void updateProduct(Product p) throws DatabaseException {
		db.updateProduct(p);
	}
	
	public void deleteProduct(int id) throws DatabaseException {
		db.deleteProduct(id);
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
