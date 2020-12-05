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
		productService.createProduct(productName, date);
		if (productPicture != null)
			productService.setProductPicture(productService.findProductByDate(date).getId(), productPicture);
		questionnaireService.createQuestionnaire(true);
		for (QuestionWrapper questionWrapper : questions) {
			//questionService.createQuestion(questionWrapper.getText(), questionWrapper.isOptional(), questionnaireId);
		}
	}
	
}
