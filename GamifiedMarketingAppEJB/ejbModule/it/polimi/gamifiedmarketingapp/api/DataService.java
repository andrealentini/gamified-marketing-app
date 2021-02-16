package it.polimi.gamifiedmarketingapp.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.AnswerChoice;
import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.entities.QuestionChoice;
import it.polimi.gamifiedmarketingapp.entities.Questionnaire;
import it.polimi.gamifiedmarketingapp.entities.Review;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;

@Stateless
public class DataService extends AbstractFacadeService {
	
	public Product getProduct(Date date) {
		return productService.findProductByDate(date);
	}
	
	public List<Product> getProducts(Integer limit) {
		return productService.findLimitedNumberOfProducts(limit);
	}
	
	public List<Review> getReviews(Integer productId, Integer limit) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = productService.findProductById(productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		if (limit == null)
			return product.getReviews();
		else
			return reviewService.findLimitedNumberOfReviewsByProductId(productId, limit);
	}
	
	public MasterQuestionnaire getMasterQuestionnaire(Integer productId) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = productService.findProductById(productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		return product.getMasterQuestionnaire();
	}
	
	public byte[] getPicture(Integer productId) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = productService.findProductById(productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		return product.getPicture();
	}
	
	public List<AnswerChoice> getAnswerChoices(Integer answerId) {
		if (answerId == null)
			throw new IllegalArgumentException("Answer ID can't be null");
		Answer answer = answerService.findAnswerById(answerId);
		if (answer == null)
			throw new EntryNotFoundException("Answer not found");
		return answer.getAnswerChoices();
	}
	
	public List<Filling> getFillings(Integer masterQuestionnaireId) {
		if (masterQuestionnaireId == null)
			throw new IllegalArgumentException("Master questionnaire ID can't be null");
		MasterQuestionnaire masterQuestionnaire = masterQuestionnaireService.findMasterQuestionnaireById(masterQuestionnaireId);
		if (masterQuestionnaire == null)
			throw new EntryNotFoundException("Master Questionnaire not found");
		return masterQuestionnaire.getFillings();
	}
	
	public Boolean isFillingSubmitted(Filling filling) {
		return fillingService.isFillingSubmitted(filling);
	}
	
	
	public List<QuestionChoice> getQuestionChoices(Integer questionId) {
		if (questionId == null)
			throw new IllegalArgumentException("Question ID can't be null");
		Question question = questionService.findQuestionById(questionId);
		if (question == null)
			throw new EntryNotFoundException("Question not found");
		return question.getQuestionChoices();
	}
	
	public List<Question> getQuestions(Integer questionnaireId) {
		if (questionnaireId == null)
			throw new IllegalArgumentException("Questionnaire ID can't be null");
		Questionnaire questionnaire = questionnaireService.findQuestionnaireById(questionnaireId);
		if (questionnaire == null)
			throw new EntryNotFoundException("Questionnaire not found");
		return questionnaire.getQuestions();
	}
	
	//TODO: add exception handling to last three methods
	public List<MasterQuestionnaire> getPaginatedMasterQuestionnaires(Integer pageNumber,Integer pageSize){
		List<MasterQuestionnaire> masterQuestionnaires = new ArrayList<MasterQuestionnaire>();
		if (pageNumber == null || pageSize == null) {
			//Return all the masterQuestionnaires ordered by product date
			masterQuestionnaires = getAllMasterQuestionnaires();
		}else {
			//Taking pageNumber * pageSize elements and then taking the last pageSize elements
			List<Product> products = productService.findLimitedNumberOfProducts(pageNumber * pageSize);
			List<Product> paginatedProducts = products.subList(products.size()- pageSize, products.size());
			//For each product taking the respective MasterQuestionnaires
			masterQuestionnaires = getMasterQuestionnairesFromProducts(paginatedProducts);
		}
		if(masterQuestionnaires.isEmpty())
			throw new EntryNotFoundException("Master questionnaires not found");
		return masterQuestionnaires;
		
	}
	
	private List<MasterQuestionnaire> getAllMasterQuestionnaires(){
		List<Product> products = productService.findAllProducts();
		return getMasterQuestionnairesFromProducts(products);
	}
	
	private List<MasterQuestionnaire> getMasterQuestionnairesFromProducts(List<Product> products){
		List<MasterQuestionnaire> masterQuestionnaires = new ArrayList<MasterQuestionnaire>();
		for (Product product : products) {
			masterQuestionnaires.add(getMasterQuestionnaire(product.getId()));
		}
		return masterQuestionnaires;
	}
	

	

}
