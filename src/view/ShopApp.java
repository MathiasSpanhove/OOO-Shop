package view;

import java.awt.Dimension;

public class ShopApp {

	public static void main(String[] args) {
		ShopUI shopUI = new ShopUI();
		shopUI.setLocationRelativeTo(null);
		shopUI.setVisible(true);
		shopUI.showMenu();
	}
}
