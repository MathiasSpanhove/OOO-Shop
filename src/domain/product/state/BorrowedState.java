package domain.product.state;

import domain.product.Product;

public class BorrowedState implements ProductState {

	private Product product;
	
	public BorrowedState(Product product) {
		this.product = product;
	}
	
	@Override
	public void deleteProduct() {
		throw new IllegalStateException();
	}

	@Override
	public void borrowProduct() {
		throw new IllegalStateException();
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
	public void repairProduct() {
		throw new IllegalStateException();
	}
	
	@Override
	public String toString() {
		return "borrowed";
	}
}
