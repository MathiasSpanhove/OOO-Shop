package domain.product.state;

import domain.product.Product;

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
	public void borrowProduct() {
		throw new IllegalStateException();
	}

	@Override
	public void returnProduct(boolean damaged) {
		throw new IllegalStateException();
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
