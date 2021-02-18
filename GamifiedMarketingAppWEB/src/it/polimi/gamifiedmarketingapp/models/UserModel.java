package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2313622765001611461L;
	
	private String username;
	private List<AnswerModel> usersAnswer;
	
	public UserModel(String username) {
		this.username = username;
	}
	
	public UserModel(String username, List<AnswerModel> usersAnswer) {
		this.username = username;
		this.usersAnswer= usersAnswer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

	public List<AnswerModel> getUsersAnswer() {
		return usersAnswer;
	}

	public void setUsersAnswer(List<AnswerModel> usersAnswer) {
		this.usersAnswer = usersAnswer;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + "]";
	}
	
	
}
