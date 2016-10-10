package domain.product.state;

import domain.product.Product;

public class BorrowedState implements ProductState {

	private Product product;
	
	public BorrowedState(Product product) {
		this.product = product;
	}
	
	@Override
	public void deleteProduct() throws IllegalStateException {
		throw new IllegalStateException("You can't delete this product, because this product is currently borrowed");
	}

	@Override
	public void borrowProduct() throws IllegalStateException {
		throw new IllegalStateException("You can't borrow this product, because this product is currently borrowed");
	}

	@Override
	public void returnProduct(boolean damaged) {
		if (! damaged) {
			product.setCurrentState(product.getNotBorrowedState());
		} else {
			product.setCurrentState(product.getDamagedState());
		}
	}

	@Override
	public void repairProduct() throws IllegalStateException {
		throw new IllegalStateException("You can't repair this product, because this product is currently borrowed");
	}
	
	@Override
	public String toString() {
		return "borrowed";
	}
}
