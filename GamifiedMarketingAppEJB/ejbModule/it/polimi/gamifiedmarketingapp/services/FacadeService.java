package it.polimi.gamifiedmarketingapp.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

@Stateless
public class FacadeService {

	@EJB(name = "it.polimi.db2.mission.services/MasterQuestionnaireService")
	private MasterQuestionnaireService masterQuestionnaireService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionnaireService")
	private QuestionnaireService questionnaireService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionService")
	private QuestionService questionService;
	
	@EJB(name = "it.polimi.db2.mission.services/QuestionChoiceService")
	private QuestionChoiceService questionChoiceService;
	
	@EJB(name = "it.polimi.db2.mission.services/ProductService")
	private ProductService productService;
	
	public void createDailyEntry(Date date, String productName, byte[] productPicture,
			int statisticalSectionId, List<QuestionWrapper> questions) {
		int productId = productService.createProduct(productName, date);
		if (productPicture != null)
			productService.setProductPicture(productService.findProductByDate(date).getId(), productPicture);
		int questionnaireId = questionnaireService.createQuestionnaire(true);
		for (QuestionWrapper questionWrapper : questions) {
			int range = questionWrapper.getRange();
			int questionId = 
					range == 0 ?
					questionService.createQuestion(questionWrapper.getText(), questionWrapper.isOptional(), questionnaireId) :
					questionService.createRangedQuestion(questionWrapper.getText(), questionWrapper.isOptional(), range, questionnaireId);
			List<String> choices = questionWrapper.getChoices();
			if (choices != null) {
				for (String choice : choices)
					questionChoiceService.createQuestionChoice(choice, questionId);
			}
		}
		masterQuestionnaireService.createMasterQuestionnaire(questionnaireId, statisticalSectionId, productId);
	}
	
}
