package database;

import java.util.List;

import domain.product.Product;
import exception.DatabaseException;

public interface IProductDatabase {
	
	public Product getProduct(int id) throws DatabaseException;
	public List<Product> getAllProducts();
	public void addProduct(Product p) throws DatabaseException;
	public void updateProduct(Product p) throws DatabaseException;
	public void deleteProduct(int id) throws DatabaseException;
	
	public void open();
	public void close();

}
