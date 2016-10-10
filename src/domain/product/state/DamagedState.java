package domain.product.state;

import domain.product.Product;
import exception.StateException;

public class DamagedState implements ProductState {

	private Product product;
	
	public DamagedState(Product product) {
		this.product = product;
	}
		
	@Override
	public void deleteProduct() {
		product.setCurrentState(product.getRemovedState());
	}

	@Override
	public void borrowProduct() throws StateException {
		throw new StateException("You can't borrow this product, because this product is currently damaged");
	}

	@Override
	public void returnProduct(boolean damaged) throws StateException {
		throw new StateException("You can't return this product, because this product isn't borrowed");
	}

	@Override
	public void repairProduct() {
		product.setCurrentState(product.getNotBorrowedState());
	}
	
	@Override
	public String toString() {
		return "damaged";
	}
}
