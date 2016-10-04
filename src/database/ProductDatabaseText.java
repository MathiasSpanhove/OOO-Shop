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

public class ProductDatabaseText implements IProductDatabase {

	private Map<Integer, Product> products;
	private File file;
	
	public ProductDatabaseText() {
		file = new File("Database.txt");
		this.products = new HashMap<Integer, Product>();
		open();
	}
	
	@Override
	public Product getProduct(int id) throws DomainException {
		if(!products.containsKey(id)) {
			throw new DomainException("There is no product with the given ID");
		}
		
		return products.get(id);
	}
	
	@Override
	public List<Product> getAllProducts() {
		return new ArrayList<Product>(products.values());
	}
	
	@Override
	public void addProduct(Product p) throws DomainException {
		if(products.containsKey(p.getId())) {
			throw new DomainException("There already is a product with the given ID");
		}
		
		products.put(p.getId(), p);
	}

	@Override
	public void updateProduct(Product p) throws DomainException {
		if(!products.containsKey(p.getId())) {
			throw new DomainException("There is no product with the given ID");
		}
		
		products.put(p.getId(), p);
	}

	@Override
	public void deleteProduct(int id) throws DomainException {
		if(!products.containsKey(id)) {
			throw new DomainException("There is no product with the given ID");
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
				boolean borrowed = line.nextBoolean();
				String lastBorrowedString = line.next();
				LocalDate lastBorrowed = (lastBorrowedString.equals("null") ? null : LocalDate.parse(lastBorrowedString));
				
				String value = Products.getProductCharValue(type.charAt(0));
				Product newProduct = Products.valueOf(value).createProduct(title, id, borrowed, lastBorrowed);
				
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
			
			for(Product p : products.values()) {
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
