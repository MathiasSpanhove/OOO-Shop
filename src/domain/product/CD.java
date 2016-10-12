package domain.product;

import java.time.LocalDate;

import domain.product.enums.Products;
import exception.DomainException;

public class CD extends Product {

	public CD(String title, int id) throws DomainException {
		super(title, id);
	}
	
	public CD(String title, int id, LocalDate lastBorrowed, String stateString) throws DomainException {
		super(title, id, lastBorrowed, stateString);
	}

	@Override
	public double getPrice(int days) throws DomainException {
		if(days <= 0) {
			throw new DomainException("Please enter a valid amount of days");
		}
		
		double price = days * Products.CD.getPrice();
		
		return price;
	}
}