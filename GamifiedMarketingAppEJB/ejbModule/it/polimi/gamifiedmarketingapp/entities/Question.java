package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question", schema = "gamified_marketing_app_db")
public class Question implements Serializable {

	private static final long serialVersionUID = 7656448238022023181L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String text;
	
	private boolean optional;
	
	private int range;
	
	@ManyToOne
	@JoinColumn(name = "questionnaire")
	private Questionnaire questionnaire;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
	private List<QuestionChoice> questionChoices;

	public Question() {}

	public Question(String text, boolean optional, int range, Questionnaire questionnaire) {
		this.text = text;
		this.optional = optional;
		this.range = range;
		this.questionnaire = questionnaire;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public List<QuestionChoice> getQuestionChoices() {
		return questionChoices;
	}

	public void setQuestionChoices(List<QuestionChoice> questionChoices) {
		this.questionChoices = questionChoices;
	}
	
	public void addQuestionChoice(QuestionChoice questionChoice) {
		getQuestionChoices().add(questionChoice);
		questionChoice.setQuestion(this);
	}
	
	public void removeQuestionChoice(QuestionChoice questionChoice) {
		getQuestionChoices().remove(questionChoice);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", optional=" + optional + ", range="
				+ range + ", questionnaire=" + questionnaire + "]";
	}

}
