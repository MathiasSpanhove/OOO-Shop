package domain.product;

import java.time.LocalDate;

import domain.product.state.BorrowedState;
import domain.product.state.DamagedState;
import domain.product.state.NotBorrowedState;
import domain.product.state.ProductState;
import domain.product.state.RemovedState;
import exception.DomainException;

public abstract class Product {

	private String title;
	private int id;
	private boolean borrowed;
	private LocalDate lastBorrowed;
	private ProductState state, borrowedState, notBorrowedState, removedState, damagedState;
	
	public Product(String title, int id) throws DomainException {
		setId(id);
		setTitle(title);
		setBorrowed(false);
		
		borrowedState = new BorrowedState(this);
		notBorrowedState = new NotBorrowedState(this);
		removedState = new RemovedState(this);
		damagedState = new DamagedState(this);
		
		state = notBorrowedState;
	}
	
	public Product(String title, int id, boolean borrowed, LocalDate lastBorrowed, String stateString) throws DomainException {
		setId(id);
		setTitle(title);
		setBorrowed(borrowed);
		setLastBorrowed(lastBorrowed);
		
		borrowedState = new BorrowedState(this);
		notBorrowedState = new NotBorrowedState(this);
		removedState = new RemovedState(this);
		damagedState = new DamagedState(this);
		
		switch(stateString) {
			case "borrowedState":
				state = borrowedState;
				break;
			case "notBorrowedState":
				state = notBorrowedState;
				break;
			case "removedState":
				state = removedState;
				break;
			case "damagedState":
				state = damagedState;
				break;
		}
	}
	
	public abstract double getPrice(int days) throws DomainException;
	
	
	public void toggleBorrowed() {
		this.borrowed = !this.borrowed;
	}

	public String toString() {
		return id + " - " + title + " (" + this.getClass().getSimpleName() + ")"; 
	}
	
	public String toCSV() {
		return id + ";"
				+ title + ";"
				+ this.getClass().getSimpleName() + ";"
				+ borrowed + ";"
				+ lastBorrowed;
	}
	
	//STATES
	
	public void deleteProduct() {
		state.deleteProduct();
	}
	
	public void borrowProduct() {
		state.borrowProduct();
	}
	
	public void returnProduct() {
		state.returnProduct();
	}
	
	public void repairProduct() {
		state.repairProduct();
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
		return borrowed;
	}

	private void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	public LocalDate getLastBorrowed() {
		return lastBorrowed;
	}

	protected void setLastBorrowed(LocalDate lastBorrowed) {
		this.lastBorrowed = lastBorrowed;
	}

	public ProductState getState() {
		return state;
	}

	public void setState(ProductState state) {
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
