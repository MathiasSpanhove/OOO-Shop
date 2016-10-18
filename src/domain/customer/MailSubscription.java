package domain.customer;

import domain.Observable;
import domain.product.Product;
import exception.DatabaseException;
import exception.DomainException;

public class MailSubscription implements Observer {

	private Customer customer;
	private Observable shop;
	private boolean subscribed;
	
	public MailSubscription(Customer customer, Observable shop, boolean subscribed) throws DatabaseException, DomainException {
		this.customer = customer;
		this.shop = shop;
		
		if(subscribed) {
			this.shop.registerSubscriber(this);
		} else {
			setSubscribed(false);
		}
	}
	
	@Override
	public void update(Object arg) {
		if(arg instanceof Product) {
			Product p = (Product)arg;
			System.out.println("Mail - 'New product: " + p.getTitle() + "' - sent to " + customer.getEmail());
		}
	}
	
	//GETTERS + SETTERS
	
	public Customer getCustomer() {
		return customer;
	}
	
	public boolean isSubscribed() {
		return subscribed;
	}
	
	public Observable getShop() {
		return shop;
	}

	private void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setSubscribed(boolean subscribed) throws DatabaseException, DomainException {
		this.subscribed = subscribed;
	}
	
	private void setShop(Observable shop) {
		this.shop = shop;
	}
	
}
