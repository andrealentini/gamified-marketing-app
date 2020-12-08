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
	
	private static final int TEXT_LENGTH = 500;

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	private Question assembleQuestion(String text, boolean optional, int questionnaireId) {
		if (text == null)
			throw new IllegalArgumentException("Text can't be null");
		if (text.length() > TEXT_LENGTH)
			throw new FieldLengthException("Question text too long");
		Questionnaire questionnaire = em.find(Questionnaire.class, questionnaireId);
		if (questionnaire == null)
			throw new EntryNotFoundException("Questionnaire not found");
		Question question = new Question(text, optional, 0, questionnaire);
		return question;
	}
	
	public int createQuestion(String text, boolean optional, int questionnaireId) {
		Question question = this.assembleQuestion(text, optional, questionnaireId);
		question.getQuestionnaire().addQuestion(question);
		em.persist(question);
		em.flush();
		return question.getId();
	}
	
	public int createRangedQuestion(String text, boolean optional, int range, int questionnaireId) {
		if (range <= 0)
			throw new RangeOutOfBoundException("Range upper bound can't be negative or equal to 0");
		Question question = this.assembleQuestion(text, optional, questionnaireId);
		question.setRange(range);
		question.getQuestionnaire().addQuestion(question);
		em.persist(question);
		em.flush();
		return question.getId();
	}
	
}
