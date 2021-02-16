package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class QuestionnaireModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1794140753425915049L;

	private List<UserModel> usersCanceled;
	
	private HashMap<UserModel,List<AnswerModel>> usersSubmissions;
	
	private String productName;
	
	private Date questionnaireDate;
	

	public QuestionnaireModel(String productName,Date questionnaireDate) {
		this.usersCanceled = new ArrayList<UserModel>();
		this.usersSubmissions = new HashMap<UserModel,List<AnswerModel>>();
		this.productName = productName;
		this.questionnaireDate = questionnaireDate;
		
		
	}
	

	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Date getQuestionnaireDate() {
		return questionnaireDate;
	}
	
	public void setQuestionnaireDate(Date questionnaireDate) {
		this.questionnaireDate = questionnaireDate;
	}

	public List<UserModel> getUsersCanceled() {
		return usersCanceled;
	}

	public void setUsersCanceled(List<UserModel> usersCanceled) {
		this.usersCanceled = usersCanceled;
	}

	public HashMap<UserModel, List<AnswerModel>> getUsersSubmissions() {
		return usersSubmissions;
	}

	public void setUsersSubmissions(HashMap<UserModel, List<AnswerModel>> usersSubmissions) {
		this.usersSubmissions = usersSubmissions;
	}

	public void addUserSubmission(UserModel user,List<AnswerModel> answers) {
		this.usersSubmissions.put(user, answers);
	}
	
	public void removeUserSubmission(UserModel user) {
		this.usersSubmissions.remove(user);
	}
	
	public void addUserCanceled(UserModel user) {
		this.usersCanceled.add(user);
	}
	
	public void removeUserCanceled(UserModel user) {
		this.usersCanceled.remove(user);
	}


	@Override
	public String toString() {
		return "QuestionnaireModel [usersCanceled=" + usersCanceled + ", usersSubmissions=" + usersSubmissions
				+ ", productName=" + productName + ", questionnaireDate=" + questionnaireDate + "]";
	}

	
	
	
	
	
}
