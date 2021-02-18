package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.AnswerChoice;
import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.models.AnswerModel;
import it.polimi.gamifiedmarketingapp.models.ChoiceModel;
import it.polimi.gamifiedmarketingapp.models.QuestionnaireModel;
import it.polimi.gamifiedmarketingapp.models.UserModel;
import it.polimi.gamifiedmarketingapp.utils.DateComparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/DeleteQuestionnaire")
public class DeleteQuestionnaire extends AbstractController {


	private static final long serialVersionUID = 428978509286851094L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if (!initialize(request, response))
				return;
			
			//Getting Paginated MasterQuestionnaire
			Integer questionnairesNumber;
			
			try {
				questionnairesNumber = Integer.parseInt(request.getParameter("page"));
				
				if (questionnairesNumber == null || questionnairesNumber==0)
					throw new Exception("Missing or empty credential value");
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing acceptable values or/and missing of values");
				return;
			}
			
			
			List<MasterQuestionnaire> masterQuestionnaires = new ArrayList<MasterQuestionnaire>();
			
			try {
				
				List<Product> products= dataService.getProducts(Math.min(Math.abs(questionnairesNumber), dataService.getAllProducts().size()));
				for(it.polimi.gamifiedmarketingapp.entities.Product product : products)
				{
					masterQuestionnaires.add(product.getMasterQuestionnaire());
				}
			}catch(Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return;
			}
			if(masterQuestionnaires.isEmpty()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Master questionnaire not found1.");
				return;
			}
			
			try {
			//From paginated MasterQuestionnaire creating QuestionnaireModel
			List<QuestionnaireModel> questionnaireModels = getQuestionnaireModels(masterQuestionnaires);
			
			
			/* Retrieve to view */
			String path = "/WEB-INF/InspectionAndDeletion.html";
			process(request, response, path,
					new String[] {"questionnaires"},
					new Object[] {questionnaireModels});
			}catch(Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//TODO: Check this condition
		Date date = (Date)request.getSession().getAttribute("date");
		Calendar today = Calendar.getInstance();
		Date todayDate = today.getTime();
		DateComparator comparator = DateComparator.getInstance();
		if(comparator.compare(todayDate,date) == 1) {
			facadeService.deleteDailyEntry(date);
		}else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can only delete questionnaires with date older than today");
		}
		//TODO: Check if needing a response, maybe triggering a new get request from the user
	}
	
	private List<QuestionnaireModel> getQuestionnaireModels(List<MasterQuestionnaire> masterQuestionnaires){
		List<QuestionnaireModel> questionnaireModels = new ArrayList<QuestionnaireModel>();
		for(MasterQuestionnaire masterQuestionnaire : masterQuestionnaires) {
			questionnaireModels.add(getQuestionnaireModel(masterQuestionnaire));
		}
		return questionnaireModels;
	};
	
	private QuestionnaireModel getQuestionnaireModel(MasterQuestionnaire masterQuestionnaire) {
		List<Filling> fillings = dataService.getFillings(masterQuestionnaire.getId());
		QuestionnaireModel questionnaireModel = new QuestionnaireModel
				(masterQuestionnaire.getProduct().getName(), masterQuestionnaire.getProduct().getDate());
		questionnaireModel.setUsersModel(getUsers(fillings));
		return questionnaireModel;
		
	}
	
	/*private List<UserModel> getCanceledUsers(List<Filling> fillings) {
		List<UserModel> usersCanceled =  new ArrayList<UserModel>();
		for(Filling filling : fillings) {
			if(!dataService.isFillingSubmitted(filling))
				usersCanceled.add(getUserModel(filling.getRegisteredUser()));
		}
		return usersCanceled;
	}*/
	
	private List<UserModel> getUsers(List<Filling> fillings){
		List<UserModel> usersSubmissions = new ArrayList<UserModel>();
		List<AnswerModel> answers = new ArrayList<AnswerModel>();
		for(Filling filling : fillings) {
			
			if(!dataService.isFillingSubmitted(filling))
				usersSubmissions.add(new UserModel(filling.getRegisteredUser().getSurname()));
			else if(dataService.isFillingSubmitted(filling)) {
				answers = getAnswerModels(filling.getAnswers());
			usersSubmissions.add(new UserModel(filling.getRegisteredUser().getUsername(),answers));
		}
		}
		return usersSubmissions;
	}
	
	private List<AnswerModel> getAnswerModels(List<Answer> answers){
		List<AnswerModel> answerModels = new ArrayList<AnswerModel>();
		for(Answer answer : answers) {
			AnswerModel answerModel = new AnswerModel(answer.getQuestion(),answer.getText(),answer.getRangeValue());
			List<ChoiceModel> choiceModels = getChoiceModels(answer);
			for(ChoiceModel choiceModel : choiceModels) {
				answerModel.addChoice(choiceModel);
			}
			answerModels.add(answerModel);
		}
		return answerModels;
	}
	
	private List<ChoiceModel> getChoiceModels(Answer answer) {
		List<AnswerChoice> answerChoices = answer.getAnswerChoices();
		List<ChoiceModel> choiceModels = new ArrayList<ChoiceModel>();
		for(AnswerChoice answerChoice : answerChoices) {
			choiceModels.add(new ChoiceModel(answerChoice.getQuestionChoice(), true));
		}
		return choiceModels;
	}
		

}

