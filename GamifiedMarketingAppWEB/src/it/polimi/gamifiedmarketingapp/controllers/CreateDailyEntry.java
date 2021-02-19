package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.gamifiedmarketingapp.entities.Questionnaire;
import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

@WebServlet("/CreateDailyEntry")
public class CreateDailyEntry extends AbstractController {


	private static final long serialVersionUID = -3092699228610397652L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if (!initialize(request, response))
				return;
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Post needed to add another product and related marketing questionnaire: Need to respect this signature
		//createDailyEntry(Date date, String productName, byte[] productPicture,
		//Integer statisticalSectionId, List<QuestionWrapper> questions)
		//A QuestionWrapper is composed of private String text, Boolean optional; Integer upperBound; Boolean multipleChoicesSupport; List<String> choices;
		String dateToFormat = (String)request.getParameter("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = (Date) sdf.parse(request.getParameter("date"));
		} catch (ParseException e1) {
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating the product: " + e1.getMessage());
			return;
		}
		String productName = (String)request.getParameter("productName");
		Integer statisticalSectionId = getStatisticalQuestionnaire(request);
		System.out.println("Entering processMarketingQuestionnaire");
		List<QuestionWrapper> marketingQuestions = processMarketinQuestionnaire(request);
		try {
			facadeService.createDailyEntry(date, productName, null , statisticalSectionId, marketingQuestions);
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating the product: " + e.getMessage());
			return;
		}
		String path = "/WEB-INF/Response.html";
		process(request, response, path,
				new String[] {"response"},
				new Object[] {true});
		
	}
	
	private List<QuestionWrapper> processMarketinQuestionnaire(HttpServletRequest request){
		//I have to build all the questions iterating on the number of questions, 
		//for each question i have to parse the string related to the text of the question to understand the type
		//In case it is choice i have to iterate on the maxnumber of questions and check for the presence of the attribute
		//In case of null i have to ignore it otherwise i have to put it in the list
		Integer questionNumber = (Integer)request.getSession().getAttribute("questionNumber");
		System.out.println("questionNumber:" + String.valueOf(questionNumber));
		List<QuestionWrapper> marketingQuestionnaire = new ArrayList<QuestionWrapper>();
		String[] types = {"t","r","c"};
		for(int i = 0; i < questionNumber; ++i) {
			for(String type : types){
				String attributeName = String.valueOf(i) + "-text-" + type;
				System.out.println("searching for: " + String.valueOf(attributeName));
				String questionText = (String)request.getParameter(attributeName);
				System.out.println("questionText:" + questionText);
				if(questionText != null) {
					//Check the type
					if(type == "t") {
						marketingQuestionnaire = insertTextQuestion(marketingQuestionnaire,i,questionText,request);	
					}else if(type == "c") {
						marketingQuestionnaire = insertChoiceQuestion(marketingQuestionnaire,i,questionText,request);
					}else if(type == "r") {
						marketingQuestionnaire = insertRangedQuestion(marketingQuestionnaire,i,questionText,request);
					}
				}
			}
		}
		request.getSession().removeAttribute("questionNumber");
		return marketingQuestionnaire;
	}
	
	private Integer getStatisticalQuestionnaire(HttpServletRequest request) {
		List<Questionnaire> statisticalQuestionnaires = dataService.getStatisticalQuestionnaires();
		for(Questionnaire q : statisticalQuestionnaires) {
			String attributeSearch = "stat-" + String.valueOf(q.getId());
			if (request.getParameter(attributeSearch) != null) {
				if(request.getParameter(attributeSearch).contains("on"))
				return q.getId();
			}
		}
		return null;
	}
	//String text, Boolean optional, Boolean multipleChoicesSupport, Integer upperBound, List<String> choices
	public List<QuestionWrapper> insertTextQuestion(List<QuestionWrapper> marketingQuestionnaire,Integer id,String questionText,HttpServletRequest request) {
		System.out.println("text");
		QuestionWrapper textQuestion = new QuestionWrapper(questionText,false,null,null,null);
		marketingQuestionnaire.add(textQuestion);
		System.out.println("Added textQuestion " + questionText);
		return marketingQuestionnaire;
	}
	
	public List<QuestionWrapper> insertRangedQuestion(List<QuestionWrapper> marketingQuestionnaire,Integer id,String questionText,HttpServletRequest request) {
		System.out.println("going in ranged");
		String attributeName = String.valueOf(id) + "-range";
		Integer range =  Integer.parseInt(request.getParameter(attributeName));
		QuestionWrapper rangedQuestion = new QuestionWrapper(questionText,false,null,range,null);
		marketingQuestionnaire.add(rangedQuestion);
		return marketingQuestionnaire;
	}
	
	public List<QuestionWrapper> insertChoiceQuestion(List<QuestionWrapper> marketingQuestionnaire,Integer id,String questionText,HttpServletRequest request) {
		System.out.println("going in insert choice");
		Integer maxChoices = (Integer)request.getSession().getAttribute("choices");
		Boolean exclusive = false;
		String attributeName = String.valueOf(id) + "-choice-x"; 
		if(request.getParameter(attributeName) != null)
			exclusive = true;
		List<String> choicesList = new ArrayList<String>();
		for(int j = 0; j < maxChoices; ++j) {
			System.out.println("cycling for choices");
			attributeName = String.valueOf(id) + "-choice-" + String.valueOf(j);
			System.out.println("searching for choice  " + attributeName);
			String questionChoice = (String)request.getParameter(attributeName);
			if (questionChoice != null) {
				System.out.println("Saving choice " + questionChoice);
				choicesList.add(questionChoice);
			}
		}
		request.getSession().removeAttribute("choices");
		QuestionWrapper choiceQuestion = new QuestionWrapper(questionText,false,exclusive,null,choicesList);
		marketingQuestionnaire.add(choiceQuestion);
		return marketingQuestionnaire;
	}
}

