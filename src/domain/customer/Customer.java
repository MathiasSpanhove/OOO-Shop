package domain.customer;

import domain.Observable;
import exception.DatabaseException;
import exception.DomainException;

public class Customer {

	private String firstName, lastName, email;
	private int id;
	private MailSubscription mailSubscription;
	
	public Customer(String firstName, String lastName, String email, int id, boolean subscribed, Observable shop) throws DatabaseException, DomainException {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setId(id);
		
		this.mailSubscription = new MailSubscription(this, subscribed, shop);
	}
	
	@Override
	public String toString() {
		return getId() + " - " + getFirstName() + " " + getLastName() + " - " + getEmail();
	}
	
	public String toCSV() {
		return getId() + ";"
				+ getFirstName() + ";"
				+ getLastName() + ";"
				+ getEmail() + ";"
				+ mailSubscription.isSubscribed();
	}
	
	//GETTERS + SETTERS

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

	public MailSubscription getMailSubscription() {
		return mailSubscription;
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

	private void setMailSubscription(MailSubscription mailSubscription) {
		this.mailSubscription = mailSubscription;
	}

}
