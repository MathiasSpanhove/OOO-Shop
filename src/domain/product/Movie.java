package domain.product;

import java.time.LocalDate;

import domain.product.enums.Products;
import exception.DomainException;

public class Movie extends Product {
	
	public Movie(String title, int id) throws DomainException {
		super(title, id);
	}
	
	public Movie(String title, int id, boolean borrowed, LocalDate lastBorrowed) throws DomainException {
		super(title, id, borrowed, lastBorrowed);
	}

	@Override
	public double getPrice(int days) throws DomainException {
		if(days <= 0) {
			throw new DomainException("Please enter a valid amount of days");
		}
		
		double price = Products.MOVIE.getPrice();
		int daysLeft = days - 3;
		
		if(daysLeft > 0) {
			price += (daysLeft * 2);
		}
		
		return price;
	}
}
