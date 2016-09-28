package domain;

import java.time.LocalDate;

import exception.DomainException;

public class Game extends Product {
	
	public Game(String title, int id) throws DomainException {
		super(title, id);
	}

	public Game(String title, int id, boolean borrowed, LocalDate lastBorrowed) throws DomainException {
		super(title, id, borrowed, lastBorrowed);
	}

	@Override
	public double getPrice(int days) throws DomainException {
		if(days <= 0) {
			throw new DomainException("Please enter a valid amount of days");
		}
		
		double price = days * Products.GAME.getPrice();
		
		return price;
	}
}
