package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "filling", schema = "gamified_marketing_app_db")
public class Filling implements Serializable {
	
	private static final long serialVersionUID = 6705036418564375917L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "registered_user")
	private RegisteredUser registeredUser;
	
	@ManyToOne
	@JoinColumn(name = "master_questionnaire")
	private MasterQuestionnaire masterQuestionnaire;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	private int points;

	public Filling() {
	}

	public Filling(RegisteredUser registeredUser, MasterQuestionnaire masterQuestionnaire, Date timestamp) {
		this.registeredUser = registeredUser;
		this.masterQuestionnaire = masterQuestionnaire;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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
		Filling other = (Filling) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Filling [id=" + id + ", registeredUser=" + registeredUser + ", masterQuestionnaire="
				+ masterQuestionnaire + ", timestamp=" + timestamp + ", points=" + points + "]";
	}

}
