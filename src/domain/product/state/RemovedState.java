package domain.product.state;

import domain.product.Product;

public class RemovedState implements ProductState {

	private Product product;
	
	public RemovedState(Product product) {
		this.product = product;
	}
		
	@Override
	public void deleteProduct() {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrowProduct() {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnProduct() {
		// TODO Auto-generated method stub

	}

	@Override
	public void repairProduct() {
		// TODO Auto-generated method stub

	}

}
