package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "role", schema = "gamified_marketing_app_db")
public class Role implements Serializable {

	private static final long serialVersionUID = 2131538205466734888L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String description;

	public Role() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
		
}
