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
@Table(name = "answer", schema = "gamified_marketing_app_db")
public class Answer implements Serializable {

	private static final long serialVersionUID = -4725398773109519182L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String text;
	
	private int rangeValue;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "filling")
	private Filling filling;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "answer", cascade = CascadeType.ALL)
	private List<AnswerChoice> answerChoices;

	public Answer() {
	}

	public Answer(Question question, Filling filling) {
		this.question = question;
		this.filling = filling;
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

	public int getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(int rangeValue) {
		this.rangeValue = rangeValue;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Filling getFilling() {
		return filling;
	}

	public void setFilling(Filling filling) {
		this.filling = filling;
	}

	public List<AnswerChoice> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoice> answerChoices) {
		this.answerChoices = answerChoices;
	}
	
	public void addAnswerChoice(AnswerChoice answerChoice) {
		getAnswerChoices().add(answerChoice);
		answerChoice.setAnswer(this);
	}
	
	public void removeAnswerChoice(AnswerChoice answerChoice) {
		getAnswerChoices().remove(answerChoice);
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
		Answer other = (Answer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", text=" + text + ", rangeValue=" + rangeValue + ", question=" + question
				+ ", filling=" + filling + "]";
	}

}
