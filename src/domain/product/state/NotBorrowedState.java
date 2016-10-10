package domain.product.state;

import domain.product.Product;

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
	}

	@Override
	public void returnProduct(boolean damaged) {
		throw new IllegalStateException();
	}

	@Override
	public void repairProduct() {
		throw new IllegalStateException();
	}
	
	@Override
	public String toString() {
		return "available";
	}
}
