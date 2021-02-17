package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.polimi.gamifiedmarketingapp.api.DataService;
import it.polimi.gamifiedmarketingapp.entities.Filling;

@WebServlet("/Leaderboard")
public class GoToLeaderboardPage extends AbstractController{
	

	private static final long serialVersionUID = 3368066821247813078L;

	
	@EJB(name = "it.polimi.gamifiedmarketingapp.api/DataService")
	private DataService dataService;
	
	public GoToLeaderboardPage() {}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if (!initialize(request, response))
			return;
		
		
		//Retrieve fillings
		List<Filling> fillings= new LinkedList<>();
		try {
			fillings = dataService.getFillings(product.getMasterQuestionnaire().getId());
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return;
		}
		
		if (fillings == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Nobody filled yet in the questionnaire of the day.");
			return;
		} else {
			String path = "/WEB-INF/Leaderboard.html";
			process(request, response, path,
					new String[] {"fillings"},
					new Object[] {fillings});
			
		}
		
		
  
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}

}
