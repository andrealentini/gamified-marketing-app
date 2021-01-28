package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;

import it.polimi.gamifiedmarketingapp.entities.QuestionChoice;

public class ChoiceModel implements Serializable {
	
	private static final long serialVersionUID = 7663574277085591159L;

	private QuestionChoice choice;
	
	private Boolean checked;

	public ChoiceModel(QuestionChoice choice, Boolean checked) {
		this.choice = choice;
		this.checked = checked;
	}

	public QuestionChoice getChoice() {
		return choice;
	}

	public void setChoice(QuestionChoice choice) {
		this.choice = choice;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "ChoiceModel [choice=" + choice + ", checked=" + checked + "]";
	}
	
}
