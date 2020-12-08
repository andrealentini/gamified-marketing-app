package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire", schema = "gamified_marketing_app_db")
public class Questionnaire implements Serializable {

	private static final long serialVersionUID = -5169875463616347893L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean isMarketing;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "questionnaire", cascade = CascadeType.ALL)
	private List<Question> questions;

	public Questionnaire() {}

	public Questionnaire(boolean isMarketing) {
		this.isMarketing = isMarketing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMarketing() {
		return isMarketing;
	}

	public void setIsMarketing(boolean isMarketing) {
		this.isMarketing = isMarketing;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question question) {
		questions.add(question);
		question.setQuestionnaire(this);
	}
	
	public void removeQuestion(Question question) {
		getQuestions().remove(question);
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
		Questionnaire other = (Questionnaire) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Questionnaire [id=" + id + ", isMarketing=" + isMarketing + ", questions=" + questions + "]";
	}
	
}