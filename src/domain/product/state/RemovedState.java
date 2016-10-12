package domain.product.state;

import domain.product.Product;
import exception.StateException;

public class RemovedState implements ProductState {

	@SuppressWarnings("unused")
	private Product product;
	
	public RemovedState(Product product) {
		this.product = product;
	}
		
	@Override
	public void deleteProduct() throws StateException {
		throw new StateException("This product has been deleted");
	}

	@Override
	public void borrowProduct() throws StateException {
		throw new StateException("This product has been deleted");
	}

	@Override
	public void returnProduct(boolean damaged) throws StateException {
		throw new StateException("This product has been deleted");
	}

	@Override
	public void repairProduct() throws StateException {
		throw new StateException("This product has been deleted");
	}

	@Override
	public String toString() {
		return "removed";
	}
}
