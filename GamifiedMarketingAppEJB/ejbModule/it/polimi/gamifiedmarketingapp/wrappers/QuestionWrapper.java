package it.polimi.gamifiedmarketingapp.wrappers;

import java.io.Serializable;
import java.util.List;

public class QuestionWrapper implements Serializable {
	
	private static final long serialVersionUID = 8115701721043575499L;

	private String text;
	
	private boolean optional;
	
	private int range;
	
	private List<String> choices;

	public QuestionWrapper(String text, boolean optional, int range, List<String> choices) {
		this.text = text;
		this.optional = optional;
		this.range = range;
		this.choices = choices;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	@Override
	public String toString() {
		return "QuestionWrapper [text=" + text + ", optional=" + optional + ", range=" + range + ", choices=" + choices
				+ "]";
	}

}
