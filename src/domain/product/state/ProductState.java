package domain.product.state;

public interface ProductState {

	public void deleteProduct() throws IllegalStateException;
	public void borrowProduct() throws IllegalStateException;
	public void returnProduct(boolean damaged) throws IllegalStateException;
	public void repairProduct() throws IllegalStateException;
	
	public abstract String toString();
	
}
