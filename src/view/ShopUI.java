package view;

import javax.swing.JOptionPane;

import domain.Shop;
import exception.DatabaseException;
import exception.DomainException;

public class ShopUI {
	Shop shop;
	ProductUI productUI;
	CustomerUI costumerUI;
	
	public ShopUI() {
		try {
			shop = new Shop();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.productUI = new ProductUI(shop, this);
		this.costumerUI = new CustomerUI(shop, this);
	}
	
	
	public void showMenu() {
		int choice = -1;
		String menu = "1. Product menu"
				+ "\n2. Customer menu"
				+ "\n\n0. Quit";
		
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
						productUI.showMenu();
						break;
					case 2:
						costumerUI.showMenu();
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
		
		shop.close();
	}
	
}
