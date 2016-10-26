package domain.product.factory;

import java.time.LocalDate;

import domain.product.CD;
import domain.product.Game;
import domain.product.Movie;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DomainException;

public class ProductFactory {
	public static Product createProduct(String title, int id, LocalDate lastBorrowed, String stateString,
			Products productType) throws DomainException {
		Product p = null;

		switch (productType) {
		case CD:
			p = new CD(title, id, lastBorrowed, stateString);
			break;
		case MOVIE:
			p = new Movie(title, id, lastBorrowed, stateString);
			break;
		case GAME:
			p = new Game(title, id, lastBorrowed, stateString);
			break;
		}

		return p;
	}
}
