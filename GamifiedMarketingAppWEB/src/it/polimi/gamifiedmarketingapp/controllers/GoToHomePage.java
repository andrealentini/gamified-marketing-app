package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class GoToHomePage extends AbstractController {
	
	private static final long serialVersionUID = -4824243016724725302L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if (!initialize(request, response))
			return;
		
		String path = "/WEB-INF/Home.html";
		process(request, response, path,
				new String[] {"product"},
				new Object[] {product});
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}

}
