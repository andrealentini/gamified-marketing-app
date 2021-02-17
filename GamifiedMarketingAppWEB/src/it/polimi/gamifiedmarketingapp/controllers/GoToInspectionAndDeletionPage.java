package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InspectionAndDeletion")
public class GoToInspectionAndDeletionPage extends AbstractController{

	
	private static final long serialVersionUID = -2760014336836439459L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				if (!initialize(request, response))
					return;
				
				
				String path = "/WEB-INF/InspectionAndDeletion.html";
				process(request, response, path,
						new String[] {"dataService"},
						new Object[] {dataService});
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				doGet(request, response);
			}
}
