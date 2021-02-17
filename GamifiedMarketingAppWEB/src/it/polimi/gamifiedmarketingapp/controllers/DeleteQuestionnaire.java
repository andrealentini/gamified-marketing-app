package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.polimi.gamifiedmarketingapp.api.DataService;
import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.AnswerChoice;
import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.models.AnswerModel;
import it.polimi.gamifiedmarketingapp.models.ChoiceModel;
import it.polimi.gamifiedmarketingapp.models.QuestionnaireModel;
import it.polimi.gamifiedmarketingapp.models.UserModel;
import it.polimi.gamifiedmarketingapp.utils.DateComparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteQuestionnaire extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 428978509286851094L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if (!initialize(request, response))
				return;
			
			//Getting Paginated MasterQuestionnaire
			List<MasterQuestionnaire> masterQuestionnaires = new ArrayList<MasterQuestionnaire>();
			Integer pageNumber = (Integer)request.getSession().getAttribute("pageNumber");
			Integer pageSize = (Integer)request.getSession().getAttribute("pageSize");
			try {
				masterQuestionnaires = dataService.getPaginatedMasterQuestionnaires(pageNumber,pageSize);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return;
			}
			if(masterQuestionnaires.isEmpty()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Master questionnaire not found.");
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
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can only delete questionnaires older than today");
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
		questionnaireModel.setUsersCanceled(getCanceledUsers(fillings));
		questionnaireModel.setUsersSubmissions(getUsersSubmissions(fillings));
		return questionnaireModel;
		
	}
	
	private List<UserModel> getCanceledUsers(List<Filling> fillings) {
		List<UserModel> usersCanceled =  new ArrayList<UserModel>();
		for(Filling filling : fillings) {
			if(!dataService.isFillingSubmitted(filling))
				usersCanceled.add(getUserModel(filling.getRegisteredUser()));
		}
		return usersCanceled;
	}
	
	private HashMap<UserModel,List<AnswerModel>> getUsersSubmissions(List<Filling> fillings){
		HashMap<UserModel,List<AnswerModel>> usersSubmissions = new  HashMap<UserModel,List<AnswerModel>>();
		List<AnswerModel> answers = new ArrayList<AnswerModel>();
		for(Filling filling : fillings) {
			if(dataService.isFillingSubmitted(filling)) {
				answers = getAnswerModels(filling.getAnswers());
			}
			usersSubmissions.put(getUserModel(filling.getRegisteredUser()),answers);
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
		
	private UserModel getUserModel(RegisteredUser user) {
		return new UserModel(user.getUsername());
	}

}

