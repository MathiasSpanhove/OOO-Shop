package domain.customer;

import exception.DomainException;

public interface Observer {
	
	public void update(Object arg) throws DomainException;
	
}
