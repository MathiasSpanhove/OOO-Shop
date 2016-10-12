package domain.customer;

import domain.Observable;
import domain.product.Product;
import exception.DatabaseException;
import exception.DomainException;

public class Customer implements Observer {

	private Observable shop;
	private String firstName, lastName, email;
	private int id;
	private boolean subscribed;
	
	public Customer(String firstName, String lastName, String email, int id, boolean subscribed, Observable shop) throws DatabaseException, DomainException {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setId(id);
		setSubscribed(subscribed);
		setShop(shop);
		
		if(subscribed) {
			shop.registerSubscriber(this);
		}
	}
	
	@Override
	public void update(Object arg) {
		if(arg instanceof Product) {
			Product p = (Product)arg;
			System.out.println("Mail - 'New product: " + p.getTitle() + "' - sent to " + getEmail());
		}
	}
	
	@Override
	public String toString() {
		return getId() + " - " + getFirstName() + " " + getLastName() + " - " + getEmail();
	}
	
	//GETTERS + SETTERS

	public Observable getShop() {
		return shop;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}
	
	public boolean isSubscribed() {
		return subscribed;
	}

	private void setShop(Observable shop) {
		this.shop = shop;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setId(int id) {
		this.id = id;
	}
	
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

}
