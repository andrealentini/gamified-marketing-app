package it.polimi.gamifiedmarketingapp.exceptions;

public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = -4667872533804253896L;
	
	public PermissionDeniedException(String message) {
		super(message);
	}

}
