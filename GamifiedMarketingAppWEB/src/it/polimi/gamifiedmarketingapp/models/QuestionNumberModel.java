package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;

public class QuestionNumberModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048450335934009496L;
	
	private Integer questionNumber;
	
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	public QuestionNumberModel(Integer questionNumber) {
		super();
		this.questionNumber = questionNumber;
	}
	
}
