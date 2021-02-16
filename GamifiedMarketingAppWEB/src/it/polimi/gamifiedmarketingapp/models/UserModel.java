package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;

public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2313622765001611461L;
	
	private String username;
	
	public UserModel(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + "]";
	}
	
	
}
