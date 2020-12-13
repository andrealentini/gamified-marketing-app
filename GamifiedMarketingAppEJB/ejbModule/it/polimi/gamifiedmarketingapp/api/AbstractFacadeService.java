package it.polimi.gamifiedmarketingapp.api;

import javax.ejb.EJB;

import it.polimi.gamifiedmarketingapp.services.AnswerChoiceService;
import it.polimi.gamifiedmarketingapp.services.AnswerService;
import it.polimi.gamifiedmarketingapp.services.FillingService;
import it.polimi.gamifiedmarketingapp.services.MasterQuestionnaireService;
import it.polimi.gamifiedmarketingapp.services.ProductService;
import it.polimi.gamifiedmarketingapp.services.QuestionChoiceService;
import it.polimi.gamifiedmarketingapp.services.QuestionService;
import it.polimi.gamifiedmarketingapp.services.QuestionnaireService;

public abstract class AbstractFacadeService {
	
	@EJB(name = "it.polimi.db2.mission.services/MasterQuestionnaireService")
	protected MasterQuestionnaireService masterQuestionnaireService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionnaireService")
	protected QuestionnaireService questionnaireService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionService")
	protected QuestionService questionService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionChoiceService")
	protected QuestionChoiceService questionChoiceService;
	
	@EJB(name = "it.polimi.db2.mission.services/ProductService")
	protected ProductService productService;
	
	@EJB(name = "it.polimi.db2.mission.services/AnswerService")
	protected AnswerService answerService;
	
	@EJB(name = "it.polimi.db2.mission.services/AnswerChoiceService")
	protected AnswerChoiceService answerChoiceService;
	
	@EJB(name = "it.polimi.db2.mission.services/FillingService")
	protected FillingService fillingService;

}
