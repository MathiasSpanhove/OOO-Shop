package domain.product.state;

import domain.product.Product;

public class NotBorrowedState implements ProductState {

	private Product product;
	
	public NotBorrowedState(Product product) {
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
