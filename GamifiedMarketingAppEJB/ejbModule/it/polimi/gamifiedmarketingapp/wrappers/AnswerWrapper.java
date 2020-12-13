package it.polimi.gamifiedmarketingapp.wrappers;

import java.io.Serializable;
import java.util.List;

public class AnswerWrapper implements Serializable {

	private static final long serialVersionUID = -2477702493482011818L;
	
	private String text;
	
	private Integer rangeValue;
	
	private Integer questionId;
	
	private List<Integer> answerChoices;

	public AnswerWrapper(String text, Integer rangeValue, Integer questionId, List<Integer> answerChoices) {
		this.text = text;
		this.rangeValue = rangeValue;
		this.questionId = questionId;
		this.answerChoices = answerChoices;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(Integer rangeValue) {
		this.rangeValue = rangeValue;
	}

	public List<Integer> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<Integer> answerChoices) {
		this.answerChoices = answerChoices;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "AnswerWrapper [text=" + text + ", rangeValue=" + rangeValue + ", questionId=" + questionId
				+ ", answerChoices=" + answerChoices + "]";
	}

}
