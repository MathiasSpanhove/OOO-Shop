package domain.product;

import java.time.LocalDate;
import domain.product.state.BorrowedState;
import domain.product.state.DamagedState;
import domain.product.state.NotBorrowedState;
import domain.product.state.ProductState;
import domain.product.state.RemovedState;
import exception.DomainException;
import exception.StateException;

public abstract class Product {

	private String title;
	private int id;
	private LocalDate lastBorrowed;
	private ProductState state, borrowedState, notBorrowedState, removedState, damagedState;
	
	public Product(String title, int id) throws DomainException {
		setId(id);
		setTitle(title);
		setLastBorrowed(null);
		
		borrowedState = new BorrowedState(this);
		notBorrowedState = new NotBorrowedState(this);
		removedState = new RemovedState(this);
		damagedState = new DamagedState(this);
		
		state = notBorrowedState;
	}
	
	public Product(String title, int id, LocalDate lastBorrowed, String stateString) throws DomainException {
		setId(id);
		setTitle(title);
		setLastBorrowed(lastBorrowed);
		
		borrowedState = new BorrowedState(this);
		notBorrowedState = new NotBorrowedState(this);
		removedState = new RemovedState(this);
		damagedState = new DamagedState(this);
		
		switch(stateString) {
			case "borrowed":
				state = borrowedState;
				break;
			case "available":
				state = notBorrowedState;
				break;
			case "removed":
				state = removedState;
				break;
			case "damaged":
				state = damagedState;
				break;
		}
	}
	
	public abstract double getPrice(int days) throws DomainException;

	public String toString() {
		return id + " - " + title + " (" + this.getClass().getSimpleName() + ") - " + state.toString(); 
	}
	
	public String toCSV() {
		return id + ";"
				+ title + ";"
				+ this.getClass().getSimpleName() + ";"
				+ lastBorrowed + ";"
				+ state.toString();
	}
	
	//STATES
	
	public void borrowProduct() throws StateException {
		state.borrowProduct();
	}

	public void returnProduct(boolean damaged) throws StateException {	
		state.returnProduct(damaged);
	}
	
	public void repairProduct() throws StateException {
		state.repairProduct();
	}
	
	public void deleteProduct() throws StateException {
		state.deleteProduct();
	}
	
	//GETTERS & SETTERS
	
	public String getTitle() {
		return title;
	}
	
	private void setTitle(String title) throws DomainException {
		if(title == null || title.trim().equals("")) {
			throw new DomainException("Title can't be empty");
		}
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	private void setId(int id) throws DomainException {
		if(id <= 0) {
			throw new DomainException("ID can't be 0 or lower.");
		}
		this.id = id;
	}
	
	public boolean isBorrowed() {
		return getCurrentState() == borrowedState;
	}

	public LocalDate getLastBorrowed() {
		return lastBorrowed;
	}

	public void setLastBorrowed(LocalDate lastBorrowed) {
		this.lastBorrowed = lastBorrowed;
	}

	public ProductState getCurrentState() {
		return state;
	}

	public void setCurrentState(ProductState state) {
		this.state = state;
	}

	public ProductState getBorrowedState() {
		return borrowedState;
	}

	private void setBorrowedState(ProductState borrowedState) {
		this.borrowedState = borrowedState;
	}

	public ProductState getNotBorrowedState() {
		return notBorrowedState;
	}

	private void setNotBorrowedState(ProductState notBorrowedState) {
		this.notBorrowedState = notBorrowedState;
	}

	public ProductState getRemovedState() {
		return removedState;
	}

	private void setRemovedState(ProductState removedState) {
		this.removedState = removedState;
	}

	public ProductState getDamagedState() {
		return damagedState;
	}

	private void setDamagedState(ProductState damagedState) {
		this.damagedState = damagedState;
	}
		
}
