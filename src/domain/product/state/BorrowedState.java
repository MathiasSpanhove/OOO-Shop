package domain.product.state;

import domain.product.Product;
import exception.StateException;

public class BorrowedState implements ProductState {
	private Product product;
	
	public BorrowedState(Product product) {nklmh
		this.product = product;
	}
	
	@Override
	public void deleteProduct() throws StateException {
		throw new StateException("You can't delete this product, because this product is currently borrowed");
	}

	@Override
	public void borrowProduct() throws StateException {
		throw new StateException("You can't borrow this product, because this product is currently borrowed");
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
	public void repairProduct() throws StateException {
		throw new StateException("You can't repair this product, because this product is currently borrowed");
	}
	
	@Override
	public String toString() {
		return "borrowed";
	}
}
