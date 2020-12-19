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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question", schema = "gamified_marketing_app_db")
public class Question implements Serializable {

	private static final long serialVersionUID = 7656448238022023181L;
	
	public static final Integer TEXT_LENGTH = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String text;
	
	private Boolean optional;
	
	@Column(name = "upper_bound")
	private Integer upperBound;
	
	@Column(name = "multiple_choices_support")
	private Boolean multipleChoicesSupport;
	
	@ManyToOne
	@JoinColumn(name = "questionnaire")
	private Questionnaire questionnaire;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<QuestionChoice> questionChoices;

	public Question() {}

	public Question(String text, Boolean optional, Integer upperBound, Boolean multipleChoicesSupport, Questionnaire questionnaire) {
		this.text = text;
		this.optional = optional;
		this.upperBound = upperBound;
		this.multipleChoicesSupport = multipleChoicesSupport;
		this.questionnaire = questionnaire;
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

	public Boolean isMultipleChoicesSupport() {
		return multipleChoicesSupport;
	}

	public void setMultipleChoicesSupport(Boolean multipleChoicesSupport) {
		this.multipleChoicesSupport = multipleChoicesSupport;
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
	
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void addQuestionChoice(QuestionChoice questionChoice) {
		getQuestionChoices().add(questionChoice);
		questionChoice.setQuestion(this);
	}
	
	public void removeQuestionChoice(QuestionChoice questionChoice) {
		getQuestionChoices().remove(questionChoice);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", optional=" + optional + ", upperBound=" + upperBound
				+ ", multipleChoicesSupport=" + multipleChoicesSupport + ", questionnaire=" + questionnaire
				+ ", questionChoices=" + questionChoices + "]";
	}

}
