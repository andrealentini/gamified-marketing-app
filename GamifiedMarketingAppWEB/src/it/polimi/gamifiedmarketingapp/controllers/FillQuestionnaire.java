package it.polimi.gamifiedmarketingapp.controllers;

import java.io.IOException; 
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.entities.QuestionChoice;
import it.polimi.gamifiedmarketingapp.models.AnswerModel;
import it.polimi.gamifiedmarketingapp.models.ChoiceModel;
import it.polimi.gamifiedmarketingapp.wrappers.AnswerWrapper;

@WebServlet("/FillQuestionnaire")
public class FillQuestionnaire extends AbstractController {

	private static final long serialVersionUID = -8417759319258175019L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if (!initialize(request, response))
			return;
		
		/* Get master questionnaire from datasource */
		MasterQuestionnaire masterQuestionnaire = null;
		try {
			masterQuestionnaire = dataService.getMasterQuestionnaire(product.getId());
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return;
		}
		if (masterQuestionnaire == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Master questionnaire not found.");
			return;
		}
		
		/* Initialize marketing questionnaire to retrieve to view */
		List<AnswerModel> marketingQuestionnaire = new LinkedList<>();
		try {
			
			List<Question> marketingQuestions = 
					dataService.getQuestions(masterQuestionnaire.getMarketingSection().getId());
			for (Question marketingQuestion : marketingQuestions) {
				AnswerModel answerModel = new AnswerModel(marketingQuestion, "", 0);
				for (QuestionChoice questionChoice : dataService.getQuestionChoices(marketingQuestion.getId()))
					answerModel.addChoice(new ChoiceModel(questionChoice, false));
				marketingQuestionnaire.add(answerModel);
			}
			
			/* Retrieve to view */
			String path = "/WEB-INF/Questionnaire.html";
			process(request, response, path,
					new String[] {"masterQuestionnaire", "marketingQuestionnaire"},
					new Object[] {masterQuestionnaire, marketingQuestionnaire});
			
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if (!initialize(request, response))
			return;
		
		/* Get master questionnaire from datasource */
		MasterQuestionnaire masterQuestionnaire = null;
		try {
			masterQuestionnaire = dataService.getMasterQuestionnaire(product.getId());
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return;
		}
		if (masterQuestionnaire == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Master questionnaire not found.");
			return;
		}
		
		/* Get marketing questionnaire from session */
		List<AnswerModel> marketingQuestionnaire = null;
		try {
			marketingQuestionnaire = (List<AnswerModel>)request.getSession().getAttribute("marketingQuestionnaire");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (marketingQuestionnaire != null) {
			/* Submission coming from statistical section. Finalize the request */
			this.finalize(request, response, masterQuestionnaire, marketingQuestionnaire);
		} else {
			/* Store marketing questionnaire in section and redirect to statistical section */
			this.redirectStatisticalSection(request, response, masterQuestionnaire);
		}
	}
	
	private void redirectStatisticalSection(HttpServletRequest request, HttpServletResponse response, MasterQuestionnaire masterQuestionnaire) throws IOException {
		List<AnswerModel> marketingQuestionnaire = new LinkedList<>();
		
		/* Initialize marketing section models */
		try {
			List<Question> marketingQuestions = 
					dataService.getQuestions(masterQuestionnaire.getMarketingSection().getId());
			for (Question marketingQuestion : marketingQuestions) {
				AnswerModel answerModel = new AnswerModel(marketingQuestion, "", 0);
				for (QuestionChoice questionChoice : dataService.getQuestionChoices(marketingQuestion.getId()))
					answerModel.addChoice(new ChoiceModel(questionChoice, false));
				marketingQuestionnaire.add(answerModel);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in retrieving marketing questionnaire from context.");
			return;
		}
	
		/* Fill marketing section models with answers from view */
		List<AnswerModel> filledMarketingQuestionnaire = new LinkedList<>();
		for (AnswerModel answerModel : marketingQuestionnaire) {
			AnswerModel processedAnswerModel = processAnswerModel(request, answerModel);
			if (processedAnswerModel == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Filling incomplete or malformed.");
				return;
			}
			filledMarketingQuestionnaire.add(processedAnswerModel);
		}
		
		/* Initialize statistical section for view */
		List<AnswerModel> statisticalQuestionnaire = new LinkedList<>();
		try {
			
			List<Question> statisticalQuestions = 
					dataService.getQuestions(masterQuestionnaire.getStatisticalSection().getId());
			for (Question statisticalQuestion : statisticalQuestions) {
				AnswerModel answerModel = new AnswerModel(statisticalQuestion, "", 0);
				for (QuestionChoice questionChoice : dataService.getQuestionChoices(statisticalQuestion.getId()))
					answerModel.addChoice(new ChoiceModel(questionChoice, false));
				statisticalQuestionnaire.add(answerModel);
			}
			
			/* Store filled marketing questionnaire into session */
			request.getSession().setAttribute("marketingQuestionnaire", filledMarketingQuestionnaire);
			
			/* Redirect to statistical section */
			String path = "/WEB-INF/Questionnaire.html";
			process(request, response, path,
					new String[] {"masterQuestionnaire", "statisticalQuestionnaire"},
					new Object[] {masterQuestionnaire, statisticalQuestionnaire});
			
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	private void finalize(HttpServletRequest request, HttpServletResponse response, MasterQuestionnaire masterQuestionnaire, List<AnswerModel> marketingQuestionnaire) throws IOException {
		/* Remove filled marketing questionnaire from session */
		request.getSession().removeAttribute("marketingQuestionnaire");

		try {
			List<AnswerModel> statisticalQuestionnaire = new LinkedList<>();
			
			List<Question> statisticalQuestions = 
					dataService.getQuestions(masterQuestionnaire.getStatisticalSection().getId());
			
			/* Initialize statistical section models with answers from view */
			for (Question statisticalQuestion : statisticalQuestions) {
				AnswerModel answerModel = new AnswerModel(statisticalQuestion, "", 0);
				for (QuestionChoice questionChoice : dataService.getQuestionChoices(statisticalQuestion.getId()))
					answerModel.addChoice(new ChoiceModel(questionChoice, false));
				AnswerModel processedAnswerModel = processAnswerModel(request, answerModel);
				if (processedAnswerModel != null) {
					statisticalQuestionnaire.add(processedAnswerModel);
				}
			}
			
			String path = "/WEB-INF/Response.html";
			String type = request.getParameter("action");
			
			if (type.contains("Submit")) {
				/* Store filling and answers in database */
				List<AnswerWrapper> answers = new LinkedList<>();
				
				for (AnswerModel marketingAnswer : marketingQuestionnaire) {
					List<Integer> choices = new LinkedList<>();
					for (ChoiceModel choiceModel : marketingAnswer.getChoices()) {
						if (choiceModel.getChecked() == true)
							choices.add(choiceModel.getChoice().getId());
					}
					if (choices.size() == 0)
						choices = null;
					AnswerWrapper answerWrapper = 
							marketingAnswer.getQuestion().getUpperBound() != null ?
									new AnswerWrapper(null, marketingAnswer.getValue(), marketingAnswer.getQuestion().getId(), null) : 
										marketingAnswer.getQuestion().isMultipleChoicesSupport() != null ?
									new AnswerWrapper(null, null, marketingAnswer.getQuestion().getId(), choices)	:
									new AnswerWrapper(marketingAnswer.getText(), null, marketingAnswer.getQuestion().getId(), choices);
					answers.add(answerWrapper);
				}
				
				for (AnswerModel statisticalAnswer : statisticalQuestionnaire) {
					List<Integer> choices = new LinkedList<>();
					for (ChoiceModel choiceModel : statisticalAnswer.getChoices()) {
						if (choiceModel.getChecked() == true)
							choices.add(choiceModel.getChoice().getId());
					}
					if (choices.size() == 0)
						choices = null;
					AnswerWrapper answerWrapper = 
							statisticalAnswer.getQuestion().getUpperBound() != null ?
									new AnswerWrapper(null, statisticalAnswer.getValue(), statisticalAnswer.getQuestion().getId(), null) : 
							statisticalAnswer.getQuestion().isMultipleChoicesSupport() != null ?
									new AnswerWrapper(null, null, statisticalAnswer.getQuestion().getId(), choices)	:
									new AnswerWrapper(statisticalAnswer.getText(), null, statisticalAnswer.getQuestion().getId(), choices);
					answers.add(answerWrapper);
				}
				
				facadeService.createQuestionnaireFilling(registeredUser.getId(), masterQuestionnaire.getProduct().getId(), answers);
				
				/* Redirect to response page with positive result */
				process(request, response, path,
						new String[] {"response"},
						new Object[] {true});
				
			} else {
				/* Store just the filling in the database*/
				facadeService.createQuestionnaireFilling(registeredUser.getId(), masterQuestionnaire.getProduct().getId(), null);
				
				/* Redirect to response page with negative result */
				process(request, response, path,
						new String[] {"response"},
						new Object[] {false});
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in retrieving marketing questionnaire from context.");
			return;
		}
		
	}
	
	private AnswerModel processAnswerModel(HttpServletRequest request, AnswerModel answerModel) {
		try {
			Question question = answerModel.getQuestion();
			if (question.isMultipleChoicesSupport() != null) {
				Integer count = 0;
				for (ChoiceModel choiceModel : answerModel.getChoices()) {
					String answer = request.getParameter(question.getId() + "-" + choiceModel.getChoice().getText());
					if (answer != null && answer.equals("on")) {
						choiceModel.setChecked(true);
						count ++;
					}
				}
				if (count == 0)
					throw new RuntimeException("Filling incomplete or malformed.");
				if (count > 1 && question.isMultipleChoicesSupport() == false)
					throw new RuntimeException("Filling incomplete or malformed.");
				return answerModel;
			} else {
				String answer = request.getParameter(question.getId().toString());
				if (answer == null || answer.length() == 0) {
					throw new RuntimeException("Filling incomplete or malformed.");
				}
				if (question.getUpperBound() != null) {
					Integer answerValue = Integer.parseInt(answer);
					if (answerValue < 0 || answerValue > question.getUpperBound())
						throw new RuntimeException("Filling incomplete or malformed.");
					answerModel.setValue(answerValue);
					return answerModel;
				} else {
					answerModel.setText(answer);
					return answerModel;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}