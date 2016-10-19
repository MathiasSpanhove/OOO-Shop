package domain.customer;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import domain.Observable;
import domain.product.Product;
import exception.DatabaseException;
import exception.DomainException;

public class MailSubscription implements Observer {

	private Customer customer;
	private Observable shop;
	private boolean subscribed;
	
	public MailSubscription(Customer customer, boolean subscribed, Observable shop) throws DatabaseException, DomainException {
		setCustomer(customer);
		setShop(shop);
		setSubscribed(subscribed);
	}
	
	@Override
	public void update(Object arg) throws DomainException {
		if(arg instanceof Product) {
			Product p = (Product)arg;
			System.out.println("Mail - 'New product: " + p.getTitle() + "' - sent to " + customer.getEmail());
			// loadProperties();
			// sendFromGMail("subject", "body", "smtp.gmail.com");
		}
	}
	
	private void loadProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.password", "1ntegration");
		properties.put("mail.smtp.user", "integration.continuous");
	}

	private void sendFromGMail(String subject, String body, String... to) throws DomainException {
		try {
			Properties properties = new Properties();
			Session session = Session.getDefaultInstance(properties);
			
			MimeMessage message = new MimeMessage(session);
			for (int i = 0; i < to.length; i++) {
				InternetAddress toAddress = new InternetAddress(to[i]);
				message.addRecipient(Message.RecipientType.TO, toAddress);
			}
			message.setSubject(subject);
			message.setText(body);
			
			String from = properties.getProperty("mail.smtp.user");
			String password = properties.getProperty("mail.smtp.password");
			Transport transport = session.getTransport("smtp");
			transport.connect(from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			throw new DomainException(e.getMessage());
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
