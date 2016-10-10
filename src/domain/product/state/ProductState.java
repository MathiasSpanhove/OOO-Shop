package domain.product.state;

import exception.StateException;

public interface ProductState {

	public void deleteProduct() throws StateException;
	public void borrowProduct() throws StateException;
	public void returnProduct(boolean damaged) throws StateException;
	public void repairProduct() throws StateException;
	
	public abstract String toString();
	
}
