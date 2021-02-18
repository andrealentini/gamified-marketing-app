package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CreateMarketingQuestionnaire extends AbstractController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1029129452532031423L;
	
	private Integer questions;
	@Override
	protected boolean initialize(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			super.initialize(request, response);
			this.questions = 0;
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
			String path = "/WEB-INF/CreateDailyEntry.html";
			process(request, response, path,
					new String[] {"questionNumber"},
					new Integer[] {this.questions});
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 this.questions = (Integer)request.getSession().getAttribute("questionNumber");
		
	}
	
	
}