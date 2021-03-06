package database.product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import domain.product.Product;
import domain.product.enums.Products;
import domain.product.factory.ProductFactory;
import exception.DatabaseException;
import properties.PropertiesFile;

public class ProductDatabaseText implements IProductDatabase {

	private Map<Integer, Product> products;
	private File file;
	private volatile static ProductDatabaseText uniqueInstance;
	
	private ProductDatabaseText() {
		file = new File("ProductDatabase.txt");
		this.products = new HashMap<Integer, Product>();
		open();
	}
	
	public static ProductDatabaseText getInstance(PropertiesFile properties) {
		if(uniqueInstance == null) {
			synchronized (ProductDatabaseText.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new ProductDatabaseText();
				}
			}
		}
		return uniqueInstance;
	}
	
	@Override
	public Product getProduct(int id) throws DatabaseException {
		if(!products.containsKey(id)) {
			throw new DatabaseException("There is no product with the given ID");
		}
		
		return products.get(id);
	}
	
	@Override
	public List<Product> getAllProducts() {
		return new ArrayList<Product>(products.values());
	}
	
	@Override
	public void addProduct(Product p) throws DatabaseException {
		if(products.containsKey(p.getId())) {
			throw new DatabaseException("There already is a product with the given ID");
		}
		
		products.put(p.getId(), p);
	}

	@Override
	public void updateProduct(Product p) throws DatabaseException {
		if(!products.containsKey(p.getId())) {
			throw new DatabaseException("There is no product with the given ID");
		}
		
		products.put(p.getId(), p);
	}

	@Override
	public void deleteProduct(int id) throws DatabaseException {
		if(!products.containsKey(id)) {
			throw new DatabaseException("There is no product with the given ID");
		}
		
		products.remove(id);
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
				String title = line.next();
				String type = line.next();
				String lastBorrowedString = line.next();
				LocalDate lastBorrowed = (lastBorrowedString.equals("null") ? null : LocalDate.parse(lastBorrowedString));
				String stateString = line.next();
				
				Product newProduct = ProductFactory.createProduct(title, id, lastBorrowed, stateString, Products.valueOf(type.toUpperCase()));
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

	@Override
	public void close() {
		try {
			PrintWriter emptyFile = new PrintWriter (file);
			emptyFile.close();
			
			PrintWriter writer = new PrintWriter(file);
			
			for(Product p : getAllProducts()) {
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
