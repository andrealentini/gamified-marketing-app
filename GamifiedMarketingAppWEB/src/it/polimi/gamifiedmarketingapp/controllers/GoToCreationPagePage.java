package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreationPage")
public class GoToCreationPagePage extends AbstractController{

	
	private static final long serialVersionUID = 5975788684160889220L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				if (!initialize(request, response))
					return;
				
				
				String path = "/WEB-INF/CreationPage.html";
				process(request, response, path,
						new String[] {},
						new Object[] {});
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				doGet(request, response);
			}
}
