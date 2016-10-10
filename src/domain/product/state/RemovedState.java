package domain.product.state;

import domain.product.Product;

public class RemovedState implements ProductState {

	private Product product;
	
	public RemovedState(Product product) {
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
		throw new IllegalStateException();
	}

	@Override
	public void repairProduct() {
		throw new IllegalStateException();
	}

	@Override
	public String toString() {
		return "removed";
	}
}
