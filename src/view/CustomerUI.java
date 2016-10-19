package view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import domain.Shop;
import exception.DatabaseException;
import exception.DomainException;

public class CustomerUI extends JFrame {
	Shop shop;
	ShopUI shopUI;
	
	public CustomerUI(Shop shop, ShopUI shopUI) {
		this.shop = shop;
		this.shopUI = shopUI;
	}
	
	public void showMenu() {
		this.setSize(new Dimension(200,400));
		JPanel menu = new JPanel();
		this.add(menu);
		setContentPane(menu);
		
		JButton button1 = new JButton("Add customer");
		JButton button2 = new JButton("Show customer");
		JButton button3 = new JButton("Show all customers");
		JButton button4 = new JButton("Is customer subscribed?");
		JButton button5 = new JButton("Subscribe");
		JButton button6 = new JButton("Unsubscribe");
		JButton button0 = new JButton("Back");
		menu.add(button1); 
		menu.add(button2);
		menu.add(button3); 
		menu.add(button4);
		menu.add(button5); 
		menu.add(button6);
		menu.add(button0); 
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCustomer();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showCustomer();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAllCustomers();
			}
		});
		
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCustomerSubscribed();
			}
		});
		
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				subscribeCustomer();
			}
		});
		
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unsubscribeCustomer();
			}
		});
		
		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shopUI.showMenu();
			}
		});
	}
	
//	public void showMenu() {
//		int choice = -1;
//		String menu = "1. Add customer"
//				+ "\n2. Show customer"
//				+ "\n3. Show all customers"
//				+ "\n4. Is customer subscribed?"
//				+ "\n5. Subscribe customer"
//				+ "\n6. Unsubscribe customer"
//				+ "\n\n0. Back";
//		
//		while (choice != 0) {
//			try {
//				String choiceString = JOptionPane.showInputDialog(menu);
//				if (choiceString == null) {
//					break;
//				} else {
//					choice = Integer.parseInt(choiceString);
//					
//					switch(choice) {
//					case 0:
//						break;
//					case 1:
//						addCustomer();
//						break;
//					case 2:
//						showCustomer();
//						break;
//					case 3:
//						showAllCustomers();
//						break;
//					case 4:
//						isCustomerSubscribed();
//						break;
//					case 5:
//						subscribeCustomer();
//						break;
//					case 6:
//						unsubscribeCustomer();
//						break;
//					default:
//						JOptionPane.showMessageDialog(null, "Please enter a valid number");
//					}
//				}
//			} catch (NumberFormatException e) {
//				JOptionPane.showMessageDialog(null, "Please enter a number");
//				e.printStackTrace();
//			}
//		}
//	}

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
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getFirstName() + " " + shop.getCustomer(id).getLastName());
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
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void isCustomerSubscribed() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, shop.getCustomer(id).getMailSubscription().isSubscribed() ? "Customer is subscribed" : "Customer isn't subscribed");
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void subscribeCustomer() {
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
	
	private void unsubscribeCustomer() {
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
