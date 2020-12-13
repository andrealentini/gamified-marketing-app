package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "answer", schema = "gamified_marketing_app_db")
@NamedQueries({
	@NamedQuery(name = "AnswerChoice.findByAnswerIdAndQuestionChoiceId", query = "SELECT ac FROM AnswerChoice ac WHERE ac.answer.id = :answerId AND ac.questionChoice.id = :questionChoiceId"),
})
public class AnswerChoice implements Serializable {

	private static final long serialVersionUID = 416074754831877344L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "answer")
	private Answer answer;
	
	@ManyToOne
	@JoinColumn(name = "question_choice")
	private QuestionChoice questionChoice;

	public AnswerChoice() {
	}

	public AnswerChoice(Answer answer, QuestionChoice questionChoice) {
		this.answer = answer;
		this.questionChoice = questionChoice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public QuestionChoice getQuestionChoice() {
		return questionChoice;
	}

	public void setQuestionChoice(QuestionChoice questionChoice) {
		this.questionChoice = questionChoice;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
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
		AnswerChoice other = (AnswerChoice) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnswerChoice [id=" + id + ", answer=" + answer + ", questionChoice=" + questionChoice + "]";
	}

}
