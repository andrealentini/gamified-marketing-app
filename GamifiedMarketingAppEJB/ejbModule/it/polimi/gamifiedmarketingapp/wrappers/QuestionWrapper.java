package it.polimi.gamifiedmarketingapp.wrappers;

import java.io.Serializable;
import java.util.List;

public class QuestionWrapper implements Serializable {
	
	private static final long serialVersionUID = 8115701721043575499L;

	private String text;
	
	private Boolean optional;
	
	private Integer upperBound;
	
	private Boolean multipleChoicesSupport;
	
	private List<String> choices;

	public QuestionWrapper(String text, Boolean optional, Boolean multipleChoicesSupport, Integer upperBound, List<String> choices) {
		this.text = text;
		this.optional = optional;
		this.upperBound = upperBound;
		this.multipleChoicesSupport = multipleChoicesSupport;
		this.choices = choices;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean isOptional() {
		return optional;
	}

	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	
	public Boolean isMultipleChoicesSupport() {
		return multipleChoicesSupport;
	}

	public void setMultipleChoicesSupport(Boolean multipleChoicesSupport) {
		this.multipleChoicesSupport = multipleChoicesSupport;
	}

	@Override
	public String toString() {
		return "QuestionWrapper [text=" + text + ", optional=" + optional + ", upperBound=" + upperBound
				+ ", multipleChoicesSupport=" + multipleChoicesSupport + ", choices=" + choices + "]";
	}

}
