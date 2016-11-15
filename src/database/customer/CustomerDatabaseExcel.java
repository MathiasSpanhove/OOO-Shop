package database.customer;

import java.util.List;
import java.util.Map;

import database.excel.ExcelHandler;
import domain.customer.Customer;
import domain.customer.Observer;
import exception.DatabaseException;

public class CustomerDatabaseExcel implements ICustomerDatabase {
	
	private ExcelHandler adapter = new ExcelHandler();
	

	@Override
	public Customer getCustomer(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCustomer(Customer c) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer c) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Integer, Observer> getSubscribers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
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
