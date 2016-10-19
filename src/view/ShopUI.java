package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import domain.Shop;
import exception.DatabaseException;
import exception.DomainException;

public class ShopUI extends JFrame {
	private Shop shop;
	private ProductUI productUI;
	private CustomerUI customerUI;
	
	public ShopUI() {
		try {
			shop = new Shop();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (DomainException e) {
			e.printStackTrace();
		}
		this.productUI = new ProductUI(shop, this);
		this.customerUI = new CustomerUI(shop, this);
		
	}
	
	public void showMenu() {
		this.setSize(new Dimension(200,200));
		JPanel menu = new JPanel();
		this.add(menu);
		setContentPane(menu);
		
		JButton button1 = new JButton("product menu");
		JButton button2 = new JButton("customer menu");
		menu.add(button1); 
		menu.add(button2);
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productUI.setVisible(true);
				productUI.showMenu();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customerUI.showMenu();
				customerUI.setVisible(true);
			}
		});
	}
	
	
//	public void showMenu() {
//		int choice = -1;
//		String menu = "1. Product menu"
//				+ "\n2. Customer menu"
//				+ "\n\n0. Quit";
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
//						productUI.showMenu();
//						break;
//					case 2:
//						costumerUI.showMenu();
//						break;
//					default:
//						JOptionPane.showMessageDialog(null, "Please enter a valid number");
//					}
//				}
//				
//			} catch (NumberFormatException e) {
//				JOptionPane.showMessageDialog(null, "Please enter a number");
//				e.printStackTrace();
//			}
//		}
//		
//		shop.close();
//	}
	
}
