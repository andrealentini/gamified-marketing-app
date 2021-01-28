package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import it.polimi.gamifiedmarketingapp.entities.Question;

public class AnswerModel implements Serializable {
	
	private static final long serialVersionUID = -5699954346993691020L;

	private Question question;
	
	private String text;
	
	private Integer value;
	
	private List<ChoiceModel> choices;

	public AnswerModel(Question question, String text, Integer value) {
		this.question = question;
		this.text = text;
		this.value = value;
		choices = new LinkedList<>();
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public List<ChoiceModel> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceModel> choices) {
		this.choices = choices;
	}
	
	public void addChoice(ChoiceModel choice) {
		this.choices.add(choice);
	}
	
	public void removeChoice(ChoiceModel choice) {
		this.choices.remove(choice);
	}

	@Override
	public String toString() {
		return "AnswerModel [question=" + question + ", text=" + text + ", value=" + value + ", choices=" + choices
				+ "]";
	}

}
