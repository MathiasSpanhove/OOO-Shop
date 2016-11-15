package view;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

import domain.Shop;
import exception.CancelledException;
import exception.DatabaseException;
import exception.DomainException;

public class CustomerFacade {
	Shop shop;
	ShopUI shopUI;

	public CustomerFacade(Shop shop, ShopUI shopUI) {
		this.shop = shop;
		this.shopUI = shopUI;
	}

	protected void addCustomer() {
		try {
			String firstName = showJOptionInputDialog("Enter first name:", "Add Customer");
			String lastName = showJOptionInputDialog("Enter last name:", "Add Customer");
			String email = showJOptionInputDialog("Enter email:", "Add Customer");
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Add Customer"));

			int n = JOptionPane.showConfirmDialog(null, "Is the customer subscribed?", "Subscribed?",
					JOptionPane.YES_NO_OPTION);
			boolean subscribed = (n == JOptionPane.YES_OPTION);
			shop.addCustomer(firstName, lastName, email, id, subscribed, this.shop);

			JOptionPane.showMessageDialog(null, "Customer succesfully added to the database");
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		} catch (CancelledException e) {
			return;
		}
	}

	protected void showCustomer() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Show Customer"));
			JOptionPane.showMessageDialog(null,
					shop.getCustomer(id).getFirstName() + " " + shop.getCustomer(id).getLastName());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		} catch (CancelledException e) {
			return;
		}
	}

	protected void showAllCustomers() {
		try {
			JOptionPane.showMessageDialog(null, shop.customersToString());
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	protected void isCustomerSubscribed() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Is Customer Subscribed?"));
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getMailSubscription().isSubscribed()
					? "Customer is subscribed" : "Customer isn't subscribed");
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (CancelledException e) {
			return;
		}
	}

	protected void subscribeCustomer() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Subscribe Customer"));
			shop.registerSubscriber(shop.getCustomer(id).getMailSubscription());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (CancelledException e) {
			return;
		}
	}

	protected void unsubscribeCustomer() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Unsubscribe Customer"));
			shop.removeSubscriber(shop.getCustomer(id).getMailSubscription());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (CancelledException e) {
			return;
		}
	}

	private String showJOptionInputDialog(String message, String title) throws CancelledException {
		String value = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);

		if (value == null) {
			throw new CancelledException("User pressed the cancel button");
		} else {
			return value;
		}
	}
}
