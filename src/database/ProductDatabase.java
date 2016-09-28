package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.Product;
import domain.Products;
import exception.DomainException;

public class ProductDatabase {

	private Map<Integer, Product> products;
	private File file;
	
	public ProductDatabase() {
		file = new File("Database.txt");
		this.products = new HashMap<Integer, Product>();
		read();
	}
	
	public Product getProduct(int id) throws DomainException {
		if(!products.containsKey(id)) {
			throw new DomainException("There is no product with the given ID");
		}
		return products.get(id);
	}
	
	public List<Product> getAllProducts() {
		return new ArrayList<Product>(products.values());
	}
	
	public void addProduct(int id, String title, Character type) throws DomainException {
		if(products.containsKey(id)) {
			throw new DomainException("There already is a product with the given ID");
		}
		
		String value = Products.getProductCharValue(type);
		
		if (value != null) {
			Product newProduct = Products.valueOf(value).createProduct(title, id);
			this.products.put(id, newProduct);
		} else {
			throw new DomainException("Invalid product type.");
		}
	}
	
	public void read() {
		try {
			Scanner scanner = new Scanner(file);
			Scanner line = null;
			
			while(scanner.hasNext()) {
				line = new Scanner(scanner.nextLine());
				line.useDelimiter(";");
				
				int id = line.nextInt();
				String title = line.next();
				String type = line.next();
				boolean borrowed = line.nextBoolean();
				LocalDate lastBorrowed = LocalDate.parse(line.next());
				
				String value = Products.getProductCharValue(type.charAt(0));
				Product newProduct = Products.valueOf(value).createProduct(title, id);
				
				this.products.put(id, newProduct);
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
	
	public void write(List<Product> products) {
		try {
			PrintWriter emptyFile = new PrintWriter (file);
			emptyFile.close();
			
			PrintWriter writer = new PrintWriter(file);
			
			for(Product p : products) {
				String line = p.toCSV();
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
