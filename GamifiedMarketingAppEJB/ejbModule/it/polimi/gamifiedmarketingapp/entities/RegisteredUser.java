package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "registered_user", schema = "gamified_marketing_app_db")
@NamedQuery(name = "RegisteredUser.checkCredentials", query = "SELECT r FROM RegisteredUser r  WHERE r.username = ?1 and r.password = ?2")
public class RegisteredUser implements Serializable {

	private static final long serialVersionUID = -8648456338049855840L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;

	private String password;
	
	private String email;

	private String name;

	private String surname;
	
	private Boolean blocked;
	
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	public RegisteredUser() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		RegisteredUser other = (RegisteredUser) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegisteredUser [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", name=" + name + ", surname=" + surname + ", blocked=" + blocked + ", role=" + role + "]";
	}
		
}
