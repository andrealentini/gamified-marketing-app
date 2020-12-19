package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.gamifiedmarketingapp.api.FacadeService;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

@WebServlet("/Home")
public class GoToHomePage extends HttpServlet {
	
	private static final long serialVersionUID = -4824243016724725302L;
	
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.gamifiedmarketingapp.services/FacadeService")
	private FacadeService facadeService;

	public GoToHomePage() {}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String loginpath = getServletContext().getContextPath() + "/index.html";
		
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}

		@SuppressWarnings("unused")
		RegisteredUser registeredUser = (RegisteredUser) session.getAttribute("user");

		try {
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}
		
		List<QuestionWrapper> questions = new LinkedList<>();
		questions.add(new QuestionWrapper("Test 1", false, null, null, null));
		questions.add(new QuestionWrapper("Test 2", false, null, null, null));
		
		facadeService.createDailyEntry(new Date(2020, 12, 17), "Product 1", null, 1, questions);
		
		String path = "/WEB-INF/Home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {}

}
