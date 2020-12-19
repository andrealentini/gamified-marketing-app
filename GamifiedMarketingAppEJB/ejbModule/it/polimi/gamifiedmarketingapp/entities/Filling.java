package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "filling", schema = "gamified_marketing_app_db")
@NamedQueries({
	@NamedQuery(name = "Filling.findByRegisteredUserIdAndMasterQuestionnaireId", query = "SELECT f FROM Filling f WHERE f.registeredUser.id = :registeredUserId AND f.masterQuestionnaire.id = :masterQuestionnaireId"),
})
public class Filling implements Serializable {
	
	private static final long serialVersionUID = 6705036418564375917L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "registered_user")
	private RegisteredUser registeredUser;
	
	@ManyToOne
	@JoinColumn(name = "master_questionnaire")
	private MasterQuestionnaire masterQuestionnaire;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	private Integer points;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "filling", cascade = CascadeType.REMOVE)
	private List<Answer> answers;

	public Filling() {
	}

	public Filling(RegisteredUser registeredUser, MasterQuestionnaire masterQuestionnaire, Date timestamp) {
		this.registeredUser = registeredUser;
		this.masterQuestionnaire = masterQuestionnaire;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public MasterQuestionnaire getMasterQuestionnaire() {
		return masterQuestionnaire;
	}

	public void setMasterQuestionnaire(MasterQuestionnaire masterQuestionnaire) {
		this.masterQuestionnaire = masterQuestionnaire;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setFilling(this);
	}
	
	public void removeAnswer(Answer answer) {
		getAnswers().remove(answer);
	}

	@Override
	public String toString() {
		return "Filling [id=" + id + ", registeredUser=" + registeredUser + ", masterQuestionnaire="
				+ masterQuestionnaire + ", timestamp=" + timestamp + ", points=" + points + ", answers=" + answers
				+ "]";
	}

}
