package it.polimi.gamifiedmarketingapp.exceptions;

public class NonUniqueEntryException extends RuntimeException {

	private static final long serialVersionUID = -4645137250610064444L;

	public NonUniqueEntryException(String message) {
		super(message);
	}
	
}
