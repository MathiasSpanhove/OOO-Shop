package domain;

import domain.customer.Observer;
import exception.DatabaseException;
import exception.DomainException;

public interface Observable {
	
	public void registerSubscriber(Observer o) throws DatabaseException, DomainException;
	public void removeSubscriber(Observer o) throws DatabaseException, DomainException;
	public void notifySubscribers(Object arg) throws DatabaseException;
	
}
