package database.customer;

import java.util.List;
import domain.customer.Customer;
import domain.customer.Observer;
import exception.DatabaseException;

public interface ICustomerDatabase {

	public Customer getCustomer(int id) throws DatabaseException;
	public List<Customer> getAllCustomers() throws DatabaseException;
	public void addCustomer(Customer c) throws DatabaseException;
	public void updateCustomer(Customer c) throws DatabaseException;
	public void deleteCustomer(int id) throws DatabaseException;
	
	public List<Observer> getSubscribers() throws DatabaseException;
	
	public void open();
	public void close();
	
}
