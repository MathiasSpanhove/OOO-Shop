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
				+ "\n4. Subscribe customer"
				+ "\n5. Unsubscribe customer"
				+ "\n\n0. Back";
		
		while (choice != 0) {
			try {
				String choiceString = JOptionPane.showInputDialog(menu);
				if (choiceString == null) {
					break;
				} else {
					choice = Integer.parseInt(choiceString);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a number");
				e.printStackTrace();
			}
			
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
				subscribeCustomer();
				break;
			case 5:
				unsubscribeCustomer();
				break;
			default:
				JOptionPane.showMessageDialog(null, "Please enter a valid number");
			}
		}
	}

	private void addCustomer() {
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
	
	private void showCustomer() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getFirstName() + shop.getCustomer(id).getLastName());
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	private void showAllCustomers() {
		try {
			JOptionPane.showMessageDialog(null, shop.customersToString());
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void subscribeCustomer() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "not implemented");
	}
	
	private void unsubscribeCustomer() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "not implemented");
	}
}
