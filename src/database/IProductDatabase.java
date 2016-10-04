package database;

import java.util.List;

import domain.Product;
import exception.DomainException;

public interface IProductDatabase {
	
	public Product getProduct(int id) throws DomainException;
	public List<Product> getAllProducts();
	public void addProduct(Product p) throws DomainException;
	public void updateProduct(Product p) throws DomainException;
	public void deleteProduct(int id) throws DomainException;
	
	public void open();
	public void close();

}
