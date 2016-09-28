package domain;

import exception.DomainException;

public class Movie extends Product {
	
	public Movie(String title, int id) throws DomainException {
		super(title, id);
	}

	@Override
	public double getPrice(int days) throws DomainException {
		if(days <= 0) {
			throw new DomainException("Please enter a valid amount of days");
		}
		
		double price = Products.MOVIE.getPrice();
		int daysLeft = days - 3;
		
		if (daysLeft > 0) {
			price += (daysLeft * 2);
		}
		
		return price;
	}

}
