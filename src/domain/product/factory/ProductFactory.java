package domain.product.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DomainException;

public class ProductFactory {
	public static Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString,
			Products productType) throws DomainException {
		try {
			//Get fully qualified path name
			String type = productType.getPathName();
			
			//Create class
			Class<?> productClass = Class.forName(type);
			
			//Get constructor for object
			Constructor<?> constructor = productClass.getConstructor(String.class, int.class, LocalDate.class, String.class);
			
			//Get instance using constructor
			Object productObject = constructor.newInstance(title, id, lastBorrowed, stateString);
			
			//Cast to product and return
			Product product = (Product) productObject;
			return product;
		} catch (InvocationTargetException | IllegalArgumentException | SecurityException | NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DomainException("Could not create your product because of factory issue.\n" + e.getMessage());
		}
		
	}
}
