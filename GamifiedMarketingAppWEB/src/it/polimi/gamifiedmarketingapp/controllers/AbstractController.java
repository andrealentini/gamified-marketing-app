package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.gamifiedmarketingapp.api.DataService;
import it.polimi.gamifiedmarketingapp.api.FacadeService;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;

public abstract class AbstractController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.gamifiedmarketingapp.api/FacadeService")
	protected FacadeService facadeService;
	
	@EJB(name = "it.polimi.gamifiedmarketingapp.api/DataService")
	protected DataService dataService;
	
	protected RegisteredUser registeredUser;
	
	protected Product product;
	
	public AbstractController() {
		super();
	}
	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
	
	protected boolean initialize(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String loginPath = getServletContext().getContextPath() + "/index.html";
		String homePath = getServletContext().getContextPath() + "/Home";
		
		try {
			HttpSession session = request.getSession();
			
			if (session.isNew() || session.getAttribute("user") == null) {
				response.sendRedirect(loginPath);
				return false;
			}
			
			if (session.getAttribute("product") == null) {
				response.sendRedirect(homePath);
				return false;
			}
	
			RegisteredUser registeredUser = (RegisteredUser) session.getAttribute("user");
			Product product = (Product) session.getAttribute("product");
			
			this.registeredUser = registeredUser;
			this.product = product;
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot initialize the servlet");
			return false;
		}
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response,
			String path, String[] names, Object[] values) throws IOException {
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if (values != null) {
			if (values.length != names.length)
				throw new IllegalArgumentException("Names and values not correspondent");
			for (int i = 0; i < values.length; i ++) {
				ctx.setVariable(names[i], values[i]);
			}
		}
		templateEngine.process(path, ctx, response.getWriter());
	}

}
