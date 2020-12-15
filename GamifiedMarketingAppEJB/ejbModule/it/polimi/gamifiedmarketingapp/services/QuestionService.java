package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.entities.Questionnaire;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.exceptions.RangeOutOfBoundException;

@Stateless
public class QuestionService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public Question findQuestionById(Integer questionId) {
		if (questionId == null)
			throw new IllegalArgumentException("Question ID can't be null");
		Question question = em.find(Question.class, questionId);
		return question;
	}
	
	private Question assembleQuestion(String text, Boolean optional, Integer questionnaireId) {
		if (questionnaireId == null)
			throw new IllegalArgumentException("Questionnaire ID can't be null");
		if (optional == null)
			throw new IllegalArgumentException("Optional flag can't be null");
		if (text == null)
			throw new IllegalArgumentException("Text can't be null");
		if (text.length() > Question.TEXT_LENGTH)
			throw new FieldLengthException("Question text too long");
		Questionnaire questionnaire = em.find(Questionnaire.class, questionnaireId);
		if (questionnaire == null)
			throw new EntryNotFoundException("Questionnaire not found");
		Question question = new Question(text, optional, null, null, questionnaire);
		return question;
	}
	
	public Integer createQuestion(String text, Boolean optional, Integer questionnaireId) {
		Question question = this.assembleQuestion(text, optional, questionnaireId);
		question.getQuestionnaire().addQuestion(question);
		em.persist(question);
		em.flush();
		return question.getId();
	}
	
	public Integer createRangedQuestion(String text, Boolean optional, Integer range, Integer questionnaireId) {
		if (range == null)
			throw new IllegalArgumentException("Range upper bound can't be null");
		if (range <= 0)
			throw new RangeOutOfBoundException("Range upper bound can't be negative or equal to 0");
		Question question = this.assembleQuestion(text, optional, questionnaireId);
		question.setRange(range);
		question.getQuestionnaire().addQuestion(question);
		em.persist(question);
		em.flush();
		return question.getId();
	}
	
	public Integer createMultipleChoiceQuestion(String text, Boolean optional, Boolean multipleChoicesSupport, Integer questionnaireId) {
		if (multipleChoicesSupport == null)
			throw new IllegalArgumentException("Multiple choices support flag can't be null");
		Question question = this.assembleQuestion(text, optional, questionnaireId);
		question.setMultipleChoicesSupport(multipleChoicesSupport);
		question.getQuestionnaire().addQuestion(question);
		em.persist(question);
		em.flush();
		return question.getId();
	}
	
}
