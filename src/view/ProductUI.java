package view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import domain.Shop;
import domain.product.Product;
import exception.DatabaseException;
import exception.DomainException;
import exception.StateException;

public class ProductUI extends JFrame {
	Shop shop;
	ShopUI shopUI;
	
	public ProductUI(Shop shop, ShopUI shopUI) {
		this.shop = shop;
		this.shopUI = shopUI;
	}
	
	public void showMenu() {
		this.setSize(new Dimension(200,400));
		JPanel menu = new JPanel();
		this.add(menu);
		setContentPane(menu);
		
		JButton button1 = new JButton("Add product");
		JButton button2 = new JButton("Show product");
		JButton button3 = new JButton("Show all products");
		JButton button4 = new JButton("Show rental price");
		JButton button5 = new JButton("Check product state");
		JButton button6 = new JButton("Borrow product");
		JButton button7 = new JButton("Return product");
		JButton button8 = new JButton("Repair product");
		JButton button9 = new JButton("Remove product");
		JButton button0 = new JButton("Back");
		menu.add(button1); 
		menu.add(button2);
		menu.add(button3); 
		menu.add(button4);
		menu.add(button5); 
		menu.add(button6);
		menu.add(button7);
		menu.add(button8); 
		menu.add(button9);
		menu.add(button0); 
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showProduct();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAllProducts();
			}
		});
		
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPrice();
			}
		});
		
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showProductState();
			}
		});
		
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrowProduct();
			}
		});
		
		button7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				returnProduct();
			}
		});
		
		button8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repairProduct();
			}
		});
		
		button9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteProduct();
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
//		String menu = "1. Add product"
//				+ "\n2. Show product"
//				+ "\n3. Show rental price"
//				+ "\n4. Show all products"
//				+ "\n5. Check product state"
//				+ "\n6. Borrow product"
//				+ "\n7. Return product"
//				+ "\n8. Repair product"
//				+ "\n9. Remove product"
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
//						addProduct();
//						break;
//					case 2:
//						showProduct();
//						break;
//					case 3:
//						showPrice();
//						break;
//					case 4:
//						showAllProducts();
//						break;
//					case 5:
//						showProductState();
//						break;
//					case 6:
//						borrowProduct();
//						break;
//					case 7:
//						returnProduct();
//						break;
//					case 8:
//						repairProduct();
//						break;
//					case 9:
//						deleteProduct();
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
			Product p = shop.getProduct(id);
			p.borrowProduct();
			shop.updateProduct(p);
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
			shop.updateProduct(p);
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
			Product p = shop.getProduct(id);
			p.repairProduct();
			shop.updateProduct(p);
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
			Product p = shop.getProduct(id);
			p.deleteProduct();
			shop.updateProduct(p);
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
