package view;

import javax.swing.JFrame;
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
			shopUI.showMenuError("Could not initialize the shop\n" + e.getMessage());
		}

		properties.write();
	}
}
