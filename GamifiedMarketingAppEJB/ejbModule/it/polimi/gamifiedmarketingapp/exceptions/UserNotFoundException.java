package it.polimi.gamifiedmarketingapp.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8262617001335079566L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
