package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product", schema = "gamified_marketing_app_db")
public class Product implements Serializable {

	private static final long serialVersionUID = -6194293176038889235L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Basic(fetch = FetchType.LAZY)	//Basic annotation to define fetch policy
	@Lob	//To define a field that maps as BLOB in the database
	private byte[] picture;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	public Product() {}

	public Product(int id, String name, byte[] picture) {
		this.id = id;
		this.name = name;
		this.picture = picture;
	}
	
	public Product(String name) {
		this.name = name;
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
	
	public List<Review> getReviews() {
		return this.reviews;
	}

	public void addReview(Review review) {
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
		return "Product [id=" + id + ", name=" + name + ", picture=" + Arrays.toString(picture) + "]";
	}

}
