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
@Table(name = "review", schema = "gamified_marketing_app_db")
public class Review implements Serializable {

	private static final long serialVersionUID = -4517875468330906225L;
	
	public static Integer TITLE_LENGTH = 250;
	
	public static Integer TEXT_LENGTH = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "registered_user")
	private RegisteredUser registeredUser;
	
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;

	public Review(String title, String text, RegisteredUser registeredUser, Product product) {
		this.title = title;
		this.text = text;
		this.registeredUser = registeredUser;
		this.product = product;
	}

	public Review() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", title=" + title + ", text=" + text + ", registeredUser=" + registeredUser
				+ ", product=" + product + "]";
	}

}
