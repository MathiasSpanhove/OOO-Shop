package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import domain.Shop;
import exception.DatabaseException;
import exception.DomainException;
import properties.PropertiesFile;

@SuppressWarnings("serial")
public class ShopUI extends JFrame {
	private Shop shop;
	private ProductUI productUI;
	private CustomerUI customerUI;
	private PropertiesFile properties;

	public ShopUI(PropertiesFile properties) throws DatabaseException, DomainException {
		this.properties = properties;
		this.shop = new Shop(properties.get("database"));
		this.productUI = new ProductUI(shop, this);
		this.customerUI = new CustomerUI(shop, this);
	}

	public void showMenuSwing() {
		this.setVisible(true);
		this.setSize(new Dimension(400, 800));
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1, 5, 5));
		this.add(panel);
		setContentPane(panel);

		JButton buttonAddProduct = new JButton("Add product");
		JButton buttonShowProduct = new JButton("Show product");
		JButton buttonShowAllProducts = new JButton("Show all products");
		JButton buttonShowPrice = new JButton("Show rental price");
		JButton buttonCheckState = new JButton("Check product state");
		JButton buttonBorrowProduct = new JButton("Borrow product");
		JButton buttonReturnProduct = new JButton("Return product");
		JButton buttonRepairProduct = new JButton("Repair product");
		JButton buttonRemoveProduct = new JButton("Remove product");

		JButton buttonAddCustomer = new JButton("Add customer");
		JButton buttonShowCustomer = new JButton("Show customer");
		JButton buttonShowAllCustomers = new JButton("Show all customers");
		JButton buttonIsSubscribed = new JButton("Is customer subscribed?");
		JButton buttonSubscribe = new JButton("Subscribe customer");
		JButton buttonUnsubscribe = new JButton("Unsubscribe customer");
		
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(3, 2, 5, 5));
		panel.add(menu);

		menu.add(buttonAddProduct);
		menu.add(buttonAddCustomer);
		menu.add(buttonShowProduct);
		menu.add(buttonShowCustomer);
		menu.add(buttonShowAllProducts);
		menu.add(buttonShowAllCustomers);

		JPanel menu1 = new JPanel();
		menu1.setLayout(new GridLayout(3, 1, 5, 5));
		panel.add(menu1);
		
		menu1.add(buttonShowPrice);
		menu1.add(buttonCheckState);
		menu1.add(buttonIsSubscribed);
		
		JPanel menu2 = new JPanel();
		menu2.setLayout(new GridLayout(3, 2, 5, 5));
		panel.add(menu2);

		menu2.add(buttonSubscribe);
		menu2.add(buttonUnsubscribe);
		menu2.add(buttonBorrowProduct);
		menu2.add(buttonReturnProduct);
		menu2.add(buttonRepairProduct);
		menu2.add(buttonRemoveProduct);		

		revalidate();

		// product

		buttonAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.addProduct();
				setVisible(true);
			}
		});

		buttonShowProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.showProduct();
				setVisible(true);
			}
		});

		buttonShowAllProducts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.showAllProducts();
				setVisible(true);
			}
		});

		buttonShowPrice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.showPrice();
				setVisible(true);
			}
		});

		buttonCheckState.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.showProductState();
				setVisible(true);
			}
		});

		buttonBorrowProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.borrowProduct();
				setVisible(true);
			}
		});

		buttonReturnProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.returnProduct();
				setVisible(true);
			}
		});

		buttonRepairProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.repairProduct();
				setVisible(true);
			}
		});

		buttonRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				productUI.deleteProduct();
				setVisible(true);
			}
		});

		// customer

		buttonAddCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.addCustomer();
				setVisible(true);
			}
		});

		buttonShowCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.showCustomer();
				setVisible(true);
			}
		});

		buttonShowAllCustomers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.showAllCustomers();
				setVisible(true);
			}
		});

		buttonIsSubscribed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.isCustomerSubscribed();
				setVisible(true);
			}
		});

		buttonSubscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.subscribeCustomer();
				setVisible(true);
			}
		});

		buttonUnsubscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				customerUI.unsubscribeCustomer();
				setVisible(true);
			}
		});
	}

	public void showMenuJOptionPane() {
		int choice = -1;
		String menu = "1. Product menu" + "\n2. Customer menu" + "\n\n0. Quit";

		while (choice != 0) {
			try {
				String choiceString = JOptionPane.showInputDialog(menu);
				if (choiceString == null) {
					break;
				} else {
					choice = Integer.parseInt(choiceString);

					switch (choice) {
					case 0:
						break;
					case 1:
						this.productUI.showMenu();
						break;
					case 2:
						this.customerUI.showMenu();
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

	public void showMenuError(String text) {
		JOptionPane.showMessageDialog(null, text);
	}
}
