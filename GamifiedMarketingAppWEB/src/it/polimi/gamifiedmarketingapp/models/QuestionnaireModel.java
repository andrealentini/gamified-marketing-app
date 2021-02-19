package it.polimi.gamifiedmarketingapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class QuestionnaireModel implements Serializable {


	private static final long serialVersionUID = 1794140753425915049L;

	private List<UserModel> usersModel;
	
	private String productName;
	
	private Date questionnaireDate;
	
	private Integer productId;
	

	public QuestionnaireModel(String productName,Date questionnaireDate,Integer productId) {
		this.usersModel = new ArrayList<UserModel>();
		this.productName = productName;
		this.questionnaireDate = questionnaireDate;
		this.productId = productId;
		
		
	}
	

	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
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

	


	public List<UserModel> getUsersModel() {
		return usersModel;
	}


	public void setUsersModel(List<UserModel> usersModel) {
		this.usersModel = usersModel;
	}


	@Override
	public String toString() {
		return "FillingUserModel [usersModel=" + usersModel + ", productName=" + productName + ", questionnaireDate="
				+ questionnaireDate + "]";
	}


	
	
	
	
}
