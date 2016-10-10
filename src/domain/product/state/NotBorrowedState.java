package domain.product.state;

import java.time.LocalDate;

import domain.product.Product;
import exception.StateException;

public class NotBorrowedState implements ProductState {

	private Product product;
	
	public NotBorrowedState(Product product) {
		this.product = product;
	}
	
	@Override
	public void deleteProduct() {
		product.setCurrentState(product.getRemovedState());
	}

	@Override
	public void borrowProduct() {
		product.setCurrentState(product.getBorrowedState());
		product.setLastBorrowed(LocalDate.now());
	}

	@Override
	public void returnProduct(boolean damaged) throws StateException {
		throw new StateException("You can't return this product, because this product isn't borrowed");
	}

	@Override
	public void repairProduct() throws StateException {
		throw new StateException("You can't repair this product, because this product isn't damaged");
	}
	
	@Override
	public String toString() {
		return "available";
	}
}
