package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import properties.PropertiesFile;

public class ShopApp {

	public static void main(String[] args) {
		// initialize settings
		PropertiesFile properties = new PropertiesFile();
		properties.read();

		// initialize and run UI
		ShopUI shopUI = null;
		try {
			shopUI = new ShopUI(properties);
			shopUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			if (properties.get("ui").equals("swing")) {
				shopUI.showMenuSwing();
			} else if (properties.get("ui").equals("joptionpane")) {
				shopUI.showMenuJOptionPane();
			} else {
				throw new Exception("Invalid menu property: " + properties.get("ui")
						+ "\nPlease change your shop.ini settings to ui=swing or ui=joptionpane");
			}
		} catch (Exception e) {
			showMenuError("Could not initialize the shop\n" + e.getMessage());
		}

		properties.write();
	}
	

	public static void showMenuError(String text) {
		JOptionPane.showMessageDialog(null, text);
	}
}
