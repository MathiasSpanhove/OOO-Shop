package database.product;

import java.util.List;

import database.excel.ExcelHandler;
import domain.product.Product;
import exception.DatabaseException;

public class ProductDatabaseExcel implements IProductDatabase {
	
	private ExcelHandler adapter = new ExcelHandler();

	@Override
	public Product getProduct(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product p) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product p) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open() throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	public ExcelHandler getAdapter() {
		return adapter;
	}

	public void setAdapter(ExcelHandler adapter) {
		this.adapter = adapter;
	}

}
