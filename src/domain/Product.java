package domain;

import exception.DomainException;

public abstract class Product {

	private String title;
	private int id;
	private boolean borrowed;
	
	public Product(String title, int id) throws DomainException {
		setId(id);
		setTitle(title);
		setBorrowed(false);
	}
	
	public abstract double getPrice(int days) throws DomainException;
	
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
				+ borrowed;
	}
}
