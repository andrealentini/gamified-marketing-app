package it.polimi.gamifiedmarketingapp.exceptions;

public class QuestionnaireNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1336342529610277696L;
	
	public QuestionnaireNotFoundException(String message) {
		super(message);
	}

}
