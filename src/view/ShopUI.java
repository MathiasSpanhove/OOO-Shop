package view;

import javax.swing.JOptionPane;

import domain.Shop;
import domain.product.Product;
import exception.DatabaseException;
import exception.DomainException;
import exception.StateException;

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
				+ "\n5. Check product state"
				+ "\n6. Borrow product"
				+ "\n7. Return product"
				+ "\n8. Repair product"
				+ "\n9. Remove product"
				+ "\n\n0. Quit";
		int choice = -1;
		
		while (choice != 0) {
			try {
				String choiceString = JOptionPane.showInputDialog(menu);
				if (choiceString == null) {
					break;
				}
				choice = Integer.parseInt(choiceString);
				if (choice > 9 || choice < 0) {
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
				showProductState();
				break;
			case 6:
				borrowProduct();
				break;
			case 7:
				returnProduct();
				break;
			case 8:
				repairProduct();
				break;
			case 9:
				deleteProduct();
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
		} catch (DatabaseException e) {
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
		} catch (DatabaseException e) {
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
			JOptionPane.showMessageDialog(null, shop.getProduct(id).getPrice(days));
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
	
	private void showAllProducts() {
		JOptionPane.showMessageDialog(null, shop.toString());
	}
	
	private void showProductState() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			JOptionPane.showMessageDialog(null, "This product is " + shop.getProduct(id).getCurrentState().toString());	
		} catch (DatabaseException e) {
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
			shop.getProduct(id).borrowProduct();
		} catch (StateException e) {
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
	
	private void returnProduct() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			Product p = shop.getProduct(id);
			
			int n = JOptionPane.showConfirmDialog(null,"Is the product damaged?", "Return product", JOptionPane.YES_NO_OPTION);
			boolean damaged = (n == JOptionPane.YES_OPTION);
			p.returnProduct(damaged);
			double fine = shop.calculateFine(p.getLastBorrowed());
			
			if(fine > 0.0) {
				JOptionPane.showMessageDialog(null, "Please pay the fine of €" + fine);
			}
		} catch (StateException e) {
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

	private void repairProduct() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			shop.getProduct(id).repairProduct();
		} catch (StateException e) {
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
	
	private void deleteProduct() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id:"));
			shop.getProduct(id).deleteProduct();
		} catch (StateException e) {
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
}
