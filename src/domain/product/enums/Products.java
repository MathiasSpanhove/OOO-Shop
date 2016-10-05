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
		public Product createProduct(String title, int id, boolean borrowed, LocalDate lastBorrowed)
				throws DomainException {
			return new CD(title, id, borrowed, lastBorrowed);
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
		public Product createProduct(String title, int id, boolean borrowed, LocalDate lastBorrowed)
				throws DomainException {
			return new Movie(title, id, borrowed, lastBorrowed);
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
		public Product createProduct(String title, int id, boolean borrowed, LocalDate lastBorrowed)
				throws DomainException {
			return new Game(title, id, borrowed, lastBorrowed);
		}
	};
	
	public abstract double getPrice();
	public abstract Product createProduct(String title, int id) throws DomainException;
	public abstract Product createProduct(String title, int id, boolean borrowed, LocalDate lastBorrowed) throws DomainException;
	
    public static String getProductCharValue(final char character)
    {
        for (Products type : Products.values())
            if (type.name().charAt(0) == character)
                return type.name();

        return null;
    }
}
