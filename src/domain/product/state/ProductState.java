package domain.product.state;

public interface ProductState {

	public void deleteProduct();
	public void borrowProduct();
	public void returnProduct(boolean damaged);
	public void repairProduct();
	
	public abstract String toString();
	
}
