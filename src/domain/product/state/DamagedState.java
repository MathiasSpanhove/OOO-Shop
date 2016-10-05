package domain.product.state;

import domain.product.Product;

public class DamagedState implements ProductState {

	private Product product;
	
	public DamagedState(Product product) {
		this.product = product;
	}
		
	@Override
	public void deleteProduct() {
		product.setState(product.getRemovedState());
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
