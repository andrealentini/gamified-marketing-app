package it.polimi.gamifiedmarketingapp.api;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.wrappers.AnswerWrapper;
import it.polimi.gamifiedmarketingapp.wrappers.QuestionWrapper;

@Stateless
public class FacadeService extends AbstractFacadeService {
	
	public void createDailyEntry(Date date, String productName, byte[] productPicture,
			Integer statisticalSectionId, List<QuestionWrapper> questions) {
		Integer productId = productService.createProduct(productName, date);
		if (productPicture != null)
			productService.setProductPicture(productService.findProductByDate(date).getId(), productPicture);
		Integer questionnaireId = questionnaireService.createQuestionnaire(true);
		for (QuestionWrapper questionWrapper : questions) {
			Integer range = questionWrapper.getRange();
			Boolean multipleChoicesSupport = questionWrapper.isMultipleChoicesSupport();
			Integer questionId;
			if (range != null && multipleChoicesSupport != null)
				throw new UnsupportedOperationException("A question can't be both ranged and multiple choice");
			if (range == null && multipleChoicesSupport == null)
				questionId = questionService.
						createQuestion(questionWrapper.getText(), questionWrapper.isOptional(), questionnaireId);
			else
				questionId = range != null ?
						questionService.
								createRangedQuestion(questionWrapper.getText(), questionWrapper.isOptional(), range, questionnaireId) :
						questionService.
								createMultipleChoiceQuestion(questionWrapper.getText(), questionWrapper.isOptional(), multipleChoicesSupport, questionnaireId);	
			List<String> choices = questionWrapper.getChoices();
			if (choices != null && choices.size() > 0 && multipleChoicesSupport == null)
				throw new UnsupportedOperationException("Can't save a multiple choice question without choices");
			if ((choices == null || choices.size() == 0) && multipleChoicesSupport != null)
				throw new UnsupportedOperationException("Can't save choices for a non-multiple choice question");
			if (choices != null) {
				for (String choice : choices)
					questionChoiceService.createQuestionChoice(choice, questionId);
			}
		}
		masterQuestionnaireService.createMasterQuestionnaire(questionnaireId, statisticalSectionId, productId);
	}
	
	public void deleteDailyEntry(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date can't be null");
		Product product = productService.findProductByDate(date);
		productService.deleteProduct(product.getId());
	}
	
	public void createQuestionnaireFilling(Integer registeredUserId, Integer productId, List<AnswerWrapper> answers) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = productService.findProductById(productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		MasterQuestionnaire masterQuestionnaire = product.getMasterQuestionnaire();
		Integer fillingId = fillingService.addFilling(registeredUserId, masterQuestionnaire.getId());
		if (answers != null) {
			for (AnswerWrapper answerWrapper : answers) {
				Integer answerId;
				Integer questionId = answerWrapper.getQuestionId();
				Integer rangeValue = answerWrapper.getRangeValue();
				String answerText = answerWrapper.getText();
				List<Integer> answerChoices = answerWrapper.getAnswerChoices();
				if (answerText == null && rangeValue == null && (answerChoices == null || answerChoices.size() == 0))
					throw new UnsupportedOperationException("Can't save an empty answer");
				if (rangeValue != null) {
					if (answerText != null || answerChoices != null || (answerChoices != null && answerChoices.size() > 0))
						throw new UnsupportedOperationException("Can't save multiple-type answer");
					answerId = answerService.createRangedAnswer(rangeValue, questionId, fillingId);
				}
				if (answerText != null) {
					if (rangeValue != null || answerChoices != null || (answerChoices != null && answerChoices.size() > 0))
						throw new UnsupportedOperationException("Can't save multiple-type answer");
					answerId = answerService.createTextAnswer(answerText, questionId, fillingId);
				}
				if (answerChoices != null) {
					if (rangeValue != null || answerText != null)
						throw new UnsupportedOperationException("Can't save multiple-type answer");
					if (answerChoices.size() == 0)
						throw new UnsupportedOperationException("Can't save multiple choice answer with no choices");
					answerId = answerService.createMultipleChoiceAnswer(questionId, fillingId);
					for (Integer questionChoiceId : answerChoices)
						answerChoiceService.createAnswerChoice(answerId, questionChoiceId);
				}
			}
		}
	}
	
}
