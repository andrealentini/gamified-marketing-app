package it.polimi.gamifiedmarketingapp.exceptions;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -508828052042015841L;
	
	public ProductNotFoundException(String message) {
		super(message);
	}

}
