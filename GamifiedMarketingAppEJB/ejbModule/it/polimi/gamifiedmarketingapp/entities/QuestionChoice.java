package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_choice", schema = "gamified_marketing_app_db")
public class QuestionChoice implements Serializable {

	private static final long serialVersionUID = -1613673532740796552L;
	
	public static final Integer TEXT_LENGTH = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;

	public QuestionChoice() {}

	public QuestionChoice(String text, Question question) {
		this.text = text;
		this.question = question;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "QuestionChoice [id=" + id + ", text=" + text + ", question=" + question + "]";
	}

}
