package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product", schema = "gamified_marketing_app_db")
@NamedQueries({
	@NamedQuery(name = "Product.findByDate", query = "SELECT p FROM Product p WHERE p.date = :date"),
})
public class Product implements Serializable {

	private static final long serialVersionUID = -6194293176038889235L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Temporal(TemporalType.DATE)	//Annotation used to store the date as an actual date and not as timestamp
	private Date date;
	
	@Basic(fetch = FetchType.LAZY)	//Basic annotation to define fetch policy
	@Lob	//To define a field that maps as BLOB in the database
	private byte[] picture;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private MasterQuestionnaire masterQuestionnaire;
	
	public Product() {}

	public Product(String name, Date date, byte[] picture) {
		this.name = name;
		this.date = date;
		this.picture = picture;
	}
	
	public Product(String name, Date date) {
		this.name = name;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}
	
	public void setMasterQuestionnaire(MasterQuestionnaire masterQuestionnaire) {
		this.masterQuestionnaire = masterQuestionnaire;
	}

	public MasterQuestionnaire getMasterQuestionnaire() {
		return masterQuestionnaire;
	}

	public void addReview(Review review) {
		/*TODO: check order of operations*/
		getReviews().add(review);
		review.setProduct(this);
	}

	public void removeReview(Review review) {
		getReviews().remove(review);
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
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", date=" + date + ", picture=" + Arrays.toString(picture)
				+ ", reviews=" + reviews + "]";
	}

}
