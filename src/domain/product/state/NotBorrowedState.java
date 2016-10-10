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
	public void returnProduct(boolean damaged) throws IllegalStateException {
		throw new IllegalStateException("You can't return this product, because this product isn't borrowed");
	}

	@Override
	public void repairProduct() throws IllegalStateException {
		throw new IllegalStateException("You can't repair this product, because this product isn't damaged");
	}
	
	@Override
	public String toString() {
		return "available";
	}
}
