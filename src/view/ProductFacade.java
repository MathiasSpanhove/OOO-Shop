package view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import domain.Shop;
import domain.product.enums.Products;
import exception.CancelledException;
import exception.DatabaseException;
import exception.DomainException;
import exception.StateException;

public class ProductFacade {
	Shop shop;
	ShopUI shopUI;

	public ProductFacade(Shop shop, ShopUI shopUI) {
		this.shop = shop;
		this.shopUI = shopUI;
	}

	protected void addProduct() {
		try {
			String title = showJOptionInputDialog("Enter the title:", "Add Product");
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Add Product"));
						
			String[] choices = Products.getNames();
		    String type = (String) showJOptionDropdownDialog("Choose your product type",
		        "Select type", choices, choices[0]);

			shop.addProduct(id, title, type);
			JOptionPane.showMessageDialog(null, "Product succesfully added to the database");
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

	protected void showProduct() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Show Product"));
			JOptionPane.showMessageDialog(null, shop.getProduct(id).getTitle());
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

	protected void showPrice() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Show Price"));
			int days = Integer.parseInt(showJOptionInputDialog("Enter number of days:", "Show Price"));
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
		} catch (CancelledException e) {
			return;
		}
	}

	protected void showAllProducts() {
		try {
			JOptionPane.showMessageDialog(null, shop.productsToString());
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	protected void showProductState() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Show Product State"));
			JOptionPane.showMessageDialog(null, "This product is " + shop.getProduct(id).getCurrentState().toString());
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

	protected void borrowProduct() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Borrow Product"));
			shop.borrowProduct(id);
		} catch (StateException e) {
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

	protected void returnProduct() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Return Product"));
			int n = JOptionPane.showConfirmDialog(null, "Is the product damaged?", "Return product",
					JOptionPane.YES_NO_OPTION);
			boolean damaged = (n == JOptionPane.YES_OPTION);
			shop.returnProduct(id, damaged);
			
			double fine = shop.getProductFine(id);
			if (fine > 0.0) {
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
		} catch (CancelledException e) {
			return;
		}
	}

	protected void repairProduct() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Repair Product"));
			shop.repairProduct(id);
		} catch (StateException e) {
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

	protected void deleteProduct() {
		try {
			int id = Integer.parseInt(showJOptionInputDialog("Enter the id:", "Remove Product"));

			int n = JOptionPane.showConfirmDialog(null,
					"Are you sure that you want to permanently remove this product?", "Remove Product",
					JOptionPane.YES_NO_OPTION);

			if (n == JOptionPane.YES_OPTION) {
				shop.deleteProduct(id);
				JOptionPane.showMessageDialog(null, "Product succesfully removed from the database");
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
	
	private String showJOptionDropdownDialog(String message, String title, String [] list, String def) throws CancelledException {
		String value = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, list, def);
		
		if (value == null) {
			throw new CancelledException("User pressed the cancel button");
		} else {
			return value;
		}
	}

	public void showStatistics() {
		try {
			List<String> statisticsList = this.shop.getStatistics();
			JList<String> listeee = new JList<String>(statisticsList.toArray(new String[0]));
			
			JScrollPane listScroller = new JScrollPane(listeee);
			listScroller.setPreferredSize(new Dimension(250, 80));

			
			JOptionPane.showMessageDialog(null, listScroller);
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
}
