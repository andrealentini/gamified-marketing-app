package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.exceptions.RangeOutOfBoundException;

@Stateless
public class AnswerService {
	
	private static final int TEXT_LENGTH = 500;

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	private Answer assembleAnswer(int questionId, int fillingId) {
		Question question = em.find(Question.class, questionId);
		if (question == null)
			throw new EntryNotFoundException("Question not found");
		Filling filling = em.find(Filling.class, fillingId);
		if (filling == null)
			throw new EntryNotFoundException("Filling not found");
		Answer answer = new Answer(question, filling);
		return answer;
	}
	
	public int createTextAnswer(String text, int questionId, int fillingId) {
		if (text == null || text.length() == 0)
			throw new IllegalArgumentException("Answer text can't be null or empty");
		if (text.length() > TEXT_LENGTH)
			throw new FieldLengthException("Text too long");
		Answer answer = assembleAnswer(questionId, fillingId);
		if (answer.getQuestion().getRange() > 0)
			throw new UnsupportedOperationException("Can't answer with text to a ranged question");
		answer.setText(text);
		em.persist(answer);
		em.flush();
		return answer.getId();
	}
	
	public int createRangedAnswer(int rangeValue, int questionId, int fillingId) {
		if (rangeValue < 0)
			throw new RangeOutOfBoundException("Range upper bound can't be negative");
		Answer answer = assembleAnswer(questionId, fillingId);
		if (answer.getQuestion().getRange() == 0)
			throw new UnsupportedOperationException("Can't answer with range to a text question");
		answer.setRangeValue(rangeValue);
		em.persist(answer);
		em.flush();
		return answer.getId();
	}

}
