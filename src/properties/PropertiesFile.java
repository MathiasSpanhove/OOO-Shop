package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class PropertiesFile {
	private Properties properties;
	
	public PropertiesFile() {
		this.properties = new Properties();
		
		File f = new File("shop.ini");
		if(!f.exists() || f.isDirectory()) { 
		    this.create();
		}
	}
	
	public void read() {
		try {
			InputStream is = new FileInputStream("shop.ini");
			this.properties.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write() {
		try {
			OutputStream os = new FileOutputStream("shop.ini");
			this.properties.store(os, null);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String get(String property) {
		return this.properties.getProperty(property).toLowerCase();
	}

	public void set(String property, String value) {
		this.properties.setProperty(property, value);
	}
	
	public void create() {
		try{
		    PrintWriter writer = new PrintWriter("shop.ini", "UTF-8");
		    writer.println("database=sql");
		    writer.println("ui=swing");
		    writer.close();
		} catch (Exception e) {
		   e.printStackTrace();
		}
	}
}
