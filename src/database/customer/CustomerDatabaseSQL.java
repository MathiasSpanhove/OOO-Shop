package database.customer;

import java.util.List;

import domain.customer.Customer;
import exception.DatabaseException;

public class CustomerDatabaseSQL implements ICustomerDatabase {

	@Override
	public Customer getCustomer(int id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
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
	public List<Customer> getSubscribers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
