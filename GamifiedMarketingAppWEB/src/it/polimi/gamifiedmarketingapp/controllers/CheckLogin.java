package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.gamifiedmarketingapp.api.DataService;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.exceptions.CredentialsException;
import it.polimi.gamifiedmarketingapp.services.RegisteredUserService;

import javax.persistence.NonUniqueResultException;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	
	private static final long serialVersionUID = -4606421436421278011L;
	
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.gamifiedmarketingapp.services/RegisteredUserService")
	private RegisteredUserService registeredUserService;
	
	@EJB(name = "it.polimi.gamifiedmarketingapp.api/DataService")
	protected DataService dataService;

	public CheckLogin() {}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String username = null;
		String password = null;
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			
			if (username == null || password == null || username.isEmpty() || password.isEmpty())
				throw new Exception("Missing or empty credential value");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		RegisteredUser registeredUser;
		try {
			registeredUser = registeredUserService.checkCredentials(username, password);
		} catch (CredentialsException | NonUniqueResultException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}
		
		Product product = null;
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			product = dataService.getProduct(calendar.getTime());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}

		String path;
		
		if (registeredUser == null) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Incorrect username or password");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			request.getSession().setAttribute("user", registeredUser);
			if (product != null)
				request.getSession().setAttribute("product", product);
			path = getServletContext().getContextPath() + "/Home";
			response.sendRedirect(path);
		}
	}

	public void destroy() {}
	
}