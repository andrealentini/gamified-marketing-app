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
	private int id;
	
	private String username;

	private String password;

	private String name;

	private String surname;
	
	private boolean blocked;
	
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	public RegisteredUser() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
		RegisteredUser other = (RegisteredUser) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegisteredUser [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", surname=" + surname + ", blocked=" + blocked + ", role=" + role + "]";
	}
		
}
