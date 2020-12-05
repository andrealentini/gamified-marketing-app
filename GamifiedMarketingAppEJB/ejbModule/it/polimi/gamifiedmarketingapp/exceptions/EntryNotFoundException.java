package it.polimi.gamifiedmarketingapp.exceptions;

public class EntryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5296145440528387694L;

	public EntryNotFoundException(String message) {
		super(message);
	}
	
}
