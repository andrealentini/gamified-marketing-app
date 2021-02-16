package it.polimi.gamifiedmarketingapp.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import it.polimi.gamifiedmarketingapp.utils.DateComparator;
import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

public class CreateDailyEntry extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3092699228610397652L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if (!initialize(request, response))
				return;
	}
	
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Post needed to add another product and related marketing questionnaire: Need to respect this signature
		//createDailyEntry(Date date, String productName, byte[] productPicture,
		//Integer statisticalSectionId, List<QuestionWrapper> questions)
		//A QuestionWrapper is composed of private String text, Boolean optional; Integer upperBound; Boolean multipleChoicesSupport; List<String> choices;
		Date date = (Date)request.getAttribute("date");
		Calendar today = Calendar.getInstance();
		Date todayDate = today.getTime();
		DateComparator comparator = DateComparator.getInstance();
		if(comparator.compare(todayDate,date) == 1) 
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can only create questionnaires for today or later dates");
		String productName = (String)request.getAttribute("productName");
		File imageFile = (File)request.getAttribute("image");
		byte[] productPicture = Files.readAllBytes(imageFile.toPath());
		//TODO: Understand which statisicalSectionId to use
		Integer statisticalSectionId = (Integer)request.getSession().getAttribute("statisticalSectionId");
		List<QuestionWrapper> marketingQuestions = (List<QuestionWrapper>)request.getSession().getAttribute("marketingQuestions");
		try {
			facadeService.createDailyEntry(date, productName, productPicture, statisticalSectionId, marketingQuestions);
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating the product: " + e.getMessage());
			return;
		}
		//TODO: See if post has to return something
	}
	
	
}
