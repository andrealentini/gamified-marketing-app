package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "answer", schema = "gamified_marketing_app_db")
@NamedQueries({
	@NamedQuery(name = "Answer.findByQuestionIdAndFillingId", query = "SELECT a FROM Answer a WHERE a.question.id = :questionId AND a.filling.id = :fillingId"),
})
public class Answer implements Serializable {

	private static final long serialVersionUID = -4725398773109519182L;
	
	public static final Integer TEXT_LENGTH = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String text;
	
	@Column(name = "range_value")
	private Integer rangeValue;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "filling")
	private Filling filling;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "answer", cascade = CascadeType.REMOVE)
	private List<AnswerChoice> answerChoices;

	public Answer() {
	}

	public Answer(Question question, Filling filling) {
		this.question = question;
		this.filling = filling;
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

	public Integer getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(Integer rangeValue) {
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
	public String toString() {
		return "Answer [id=" + id + ", text=" + text + ", rangeValue=" + rangeValue + ", question=" + question
				+ ", filling=" + filling + "]";
	}

}
