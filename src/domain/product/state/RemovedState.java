package domain.product.state;

import domain.product.Product;

public class RemovedState implements ProductState {

	private Product product;
	
	public RemovedState(Product product) {
		this.product = product;
	}
		
	@Override
	public void deleteProduct() throws IllegalStateException {
		throw new IllegalStateException("This product has been deleted");
	}

	@Override
	public void borrowProduct() throws IllegalStateException {
		throw new IllegalStateException("This product has been deleted");
	}

	@Override
	public void returnProduct(boolean damaged) throws IllegalStateException {
		throw new IllegalStateException("This product has been deleted");
	}

	@Override
	public void repairProduct() throws IllegalStateException {
		throw new IllegalStateException("This product has been deleted");
	}

	@Override
	public String toString() {
		return "removed";
	}
}
