package view;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

import domain.Shop;
import exception.DatabaseException;
import exception.DomainException;

public class CustomerUI {
	Shop shop;
	ShopUI shopUI;
	
	public CustomerUI(Shop shop, ShopUI shopUI) {
		this.shop = shop;
		this.shopUI = shopUI;
	}
	
	public void showMenu() {
		int choice = -1;
		String menu = "1. Add customer"
				+ "\n2. Show customer"
				+ "\n3. Show all customers"
				+ "\n4. Is customer subscribed?"
				+ "\n5. Subscribe customer"
				+ "\n6. Unsubscribe customer"
				+ "\n\n0. Back";
		
		while (choice != 0) {
			try {
				String choiceString = JOptionPane.showInputDialog(menu);
				if (choiceString == null) {
					break;
				} else {
					choice = Integer.parseInt(choiceString);
					
					switch(choice) {
					case 0:
						break;
					case 1:
						addCustomer();
						break;
					case 2:
						showCustomer();
						break;
					case 3:
						showAllCustomers();
						break;
					case 4:
						isCustomerSubscribed();
						break;
					case 5:
						subscribeCustomer();
						break;
					case 6:
						unsubscribeCustomer();
						break;
					default:
						JOptionPane.showMessageDialog(null, "Please enter a valid number");
					}
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a number");
				e.printStackTrace();
			}
		}
	}

	protected void addCustomer() {
		try {
			String firstName = JOptionPane.showInputDialog("Enter first name:");
			String lastName = JOptionPane.showInputDialog("Enter last name:");
			String email = JOptionPane.showInputDialog("Enter email:");
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			
			int n = JOptionPane.showConfirmDialog(null,"Is the customer subscribed?", "Subscribed?", JOptionPane.YES_NO_OPTION);
			boolean subscribed = (n == JOptionPane.YES_OPTION);
			
			shop.addCustomer(firstName, lastName, email, id, subscribed, this.shop);
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	protected void showCustomer() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getFirstName() + " " + shop.getCustomer(id).getLastName());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
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
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getMailSubscription().isSubscribed() ? "Customer is subscribed" : "Customer isn't subscribed");
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void subscribeCustomer() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			shop.registerSubscriber(shop.getCustomer(id).getMailSubscription());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void unsubscribeCustomer() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			shop.removeSubscriber(shop.getCustomer(id).getMailSubscription());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
}
