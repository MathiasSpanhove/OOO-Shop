package view;

import javax.swing.JOptionPane;

import domain.Shop;
import exception.DomainException;

public class ShopUI {
	Shop shop;
	
	public ShopUI() {
		shop = new Shop();
	}
	
	public void showMenu() {
		String menu = "1. Add product"
				+ "\n2. Show product"
				+ "\n3. Show rental price"
				+ "\n4. Show all products"
				+ "\n5. Check if product is borrowed"
				+ "\n6. Borrow product"
				+ "\n7. Return product"
				+ "\n\n0. Quit";
		int choice = -1;
		
		while (choice != 0) {
			try {
				String choiceString = JOptionPane.showInputDialog(menu);
				if (choiceString == null) {
					break;
				}
				choice = Integer.parseInt(choiceString);
				if (choice > 7 || choice < 0) {
					throw new DomainException("Invalid number");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a number");
				e.printStackTrace();
			} catch (DomainException e) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number");
				e.printStackTrace();
			}
			
			switch(choice) {
			case 1:
				addProduct();
				break;
			case 2:
				showProduct();
				break;
			case 3:
				showPrice();
				break;
			case 4:
				showAllProducts();
				break;
			case 5:
				showIsProductBorrowed();
				break;
			case 6:
				borrowProduct();
				break;
			case 7:
				returnProduct();
				break;
			}
		}
		
		shop.close();
	}

	private void addProduct() {
		try {
			String title = JOptionPane.showInputDialog("Enter the title:");
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			Character type = JOptionPane.showInputDialog("Enter the type (M for movie/G for game/C for CD):").charAt(0);
			shop.addProduct(id, title, type);
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	private void showProduct(){
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, shop.getProduct(id).getTitle());
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}

	private void showPrice(){
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			int days = Integer.parseInt(JOptionPane.showInputDialog("Enter number of days:"));
			JOptionPane.showMessageDialog(null, shop.getPrice(id, days));
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	private void showAllProducts() {
		JOptionPane.showMessageDialog(null, shop.toString());
	}
	
	private void showIsProductBorrowed() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			if(shop.isProductBorrowed(id)) {
				JOptionPane.showMessageDialog(null, "This product is borrowed.");
			} else {
				JOptionPane.showMessageDialog(null, "This product is not borrowed.");
			}	
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	private void borrowProduct() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			if(shop.isProductBorrowed(id)) {
				JOptionPane.showMessageDialog(null, "This product is already borrowed.");
			} else {
				shop.toggleBorrowed(id);
				JOptionPane.showMessageDialog(null, "Thank you for borrowing!");
			}	
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}
	
	private void returnProduct() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			if(!shop.isProductBorrowed(id)) {
				JOptionPane.showMessageDialog(null, "This product is not borrowed.");
			} else {
				shop.toggleBorrowed(id);
				JOptionPane.showMessageDialog(null, "Thank you for returning!");
			}	
		} catch (DomainException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Input not valid");
			e.printStackTrace();
		}
	}

}
