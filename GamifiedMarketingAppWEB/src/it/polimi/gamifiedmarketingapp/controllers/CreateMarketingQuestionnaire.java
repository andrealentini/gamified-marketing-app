package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.gamifiedmarketingapp.entities.Questionnaire;
import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

@WebServlet("/CreateMarketingQuestionnaire")
public class CreateMarketingQuestionnaire extends AbstractController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1029129452532031423L;
	
	@Override
	protected boolean initialize(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			super.initialize(request, response);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot initialize the servlet");
			return false;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if (!initialize(request, response))
				return;
			Integer choices =  Integer.parseInt(request.getParameter("choices"));
			Integer choiceQuestions = Integer.parseInt(request.getParameter("choiceQuestions"));
			Integer textQuestions = Integer.parseInt(request.getParameter("textQuestions"));
			Integer rangedQuestions = Integer.parseInt(request.getParameter("rangedQuestions"));
			request.getSession().setAttribute("choices", choices);
			request.getSession().setAttribute("questionNumber", choiceQuestions + textQuestions + rangedQuestions);
			List<QuestionWrapper> marketingQuestions = new LinkedList<QuestionWrapper>();
			for(int i = 0; i < choiceQuestions; ++i) {
				List<String> choicesList = new ArrayList<String>();
				for(int j = 0;j < choices; ++j) {
					choicesList.add(null);
				}
				marketingQuestions.add(new QuestionWrapper(null,null,true,null,choicesList));
			}
			for(int i = 0; i < textQuestions; ++i) {
				List<String> choicesList = new ArrayList<String>();
				for(int j = 0;j < choices; ++j) {
					choicesList.add(null);
				}
				marketingQuestions.add(new QuestionWrapper("",null,null,null,choicesList));
			}
			
			for(int i = 0; i < rangedQuestions; ++i) {
				List<String> choicesList = new ArrayList<String>();
				for(int j = 0;j < choices; ++j) {
					choicesList.add(null);
				}
				marketingQuestions.add(new QuestionWrapper(null,null,null,0,choicesList));
			}
			List<Questionnaire> statisticalQuestionnaires = dataService.getStatisticalQuestionnaires();
			String path = "/WEB-INF/CreationPage.html";
			process(request, response, path,
					new String[] {"marketingQuestions","statisticalQuestionnaires"},
					new Object[] {marketingQuestions,statisticalQuestionnaires});
	}
	
	
	
	
}
