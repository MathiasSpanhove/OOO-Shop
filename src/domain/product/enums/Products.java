package domain.product.enums;

import java.time.LocalDate;

import domain.product.CD;
import domain.product.Game;
import domain.product.Movie;
import domain.product.Product;
import exception.DomainException;

public enum Products {
	CD {
		public double getPrice() {
			return (1.5);
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new CD(title, id);
		}

		@Override
		public Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString)
				throws DomainException {
			return new CD(title, id, lastBorrowed, stateString);
		}
	
	},
	MOVIE {
		public double getPrice() {
			return 3;
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new Movie(title, id);
		}

		@Override
		public Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString)
				throws DomainException {
			return new Movie(title, id, lastBorrowed, stateString);
		}
	},
	GAME {
		public double getPrice() {
			return 5;
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new Game(title, id);
		}

		@Override
		public Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString)
				throws DomainException {
			return new Game(title, id, lastBorrowed, stateString);
		}
	};
	
	public abstract double getPrice();
	public abstract Product createProduct(String title, int id) throws DomainException;
	public abstract Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString) throws DomainException;
	
    public static String getProductCharValue(final char character)
    {
        for (Products type : Products.values())
            if (type.name().charAt(0) == character)
                return type.name();

        return null;
    }
}