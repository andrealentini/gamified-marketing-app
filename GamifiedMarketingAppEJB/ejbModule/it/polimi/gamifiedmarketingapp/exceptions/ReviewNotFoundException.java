package it.polimi.gamifiedmarketingapp.exceptions;

public class ReviewNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 9009324477781781212L;

	public ReviewNotFoundException(String message) {
		super(message);
	}

}
