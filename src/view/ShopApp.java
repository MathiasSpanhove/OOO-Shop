package view;

import javax.swing.JFrame;

public class ShopApp {

	public static void main(String[] args) {
		ShopUI shopUI = new ShopUI();
		shopUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shopUI.showMenuSwing();
		
	}
}
