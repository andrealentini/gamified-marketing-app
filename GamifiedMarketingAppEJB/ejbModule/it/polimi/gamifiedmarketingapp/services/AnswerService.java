package it.polimi.gamifiedmarketingapp.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.exceptions.RangeOutOfBoundException;

@Stateless
public class AnswerService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public Answer findAnswerById(Integer answerId) {
		if (answerId == null)
			throw new IllegalArgumentException("Answer ID can't be null");
		Answer answer = em.find(Answer.class, answerId);
		return answer;
	}
	
	public Answer findByQuestionIdAndFillingId(Integer questionId, Integer fillingId) {
		if (questionId == null)
			throw new IllegalArgumentException("Question ID can't be null");
		if (fillingId == null)
			throw new IllegalArgumentException("Filling ID can't be null");
		List<Answer> answers = em.createNamedQuery("Answer.findByQuestionIdAndFillingId", Answer.class)
				.setParameter("questionId", questionId)
				.setParameter("fillingId", fillingId)
				.getResultList();
		if (answers == null || answers.size() == 0)
			return null;
		if (answers.size() == 1)
			return answers.get(0);
		throw new NonUniqueResultException("More than one answer with the same question and the same filling");
	}
	
	private Answer assembleAnswer(Integer questionId, Integer fillingId) {
		if (questionId == null)
			throw new IllegalArgumentException("Question ID can't be null");
		if (fillingId == null)
			throw new IllegalArgumentException("Filling ID can't be null");
		Answer test = findByQuestionIdAndFillingId(questionId, fillingId);
		if (test != null)
			throw new UnsupportedOperationException("Can't save multiple answers for the same question in the filling");
		Question question = em.find(Question.class, questionId);
		if (question == null)
			throw new EntryNotFoundException("Question not found");
		Filling filling = em.find(Filling.class, fillingId);
		if (filling == null)
			throw new EntryNotFoundException("Filling not found");
		Answer answer = new Answer(question, filling);
		return answer;
	}
	
	public Integer createMultipleChoiceAnswer(Integer questionId, Integer fillingId) {
		Answer answer = assembleAnswer(questionId, fillingId);
		if (answer.getQuestion().getUpperBound() != null)
			throw new UnsupportedOperationException("Can't answer with choices to a ranged question");
		answer.getFilling().addAnswer(answer);
		em.persist(answer);
		em.flush();
		return answer.getId();
	}
	
	public Integer createTextAnswer(String text, Integer questionId, Integer fillingId) {
		if (text == null || text.length() == 0)
			throw new IllegalArgumentException("Answer text can't be null or empty");
		if (text.length() > Answer.TEXT_LENGTH)
			throw new FieldLengthException("Text too long");
		Answer answer = assembleAnswer(questionId, fillingId);
		if (answer.getQuestion().getUpperBound() != null)
			throw new UnsupportedOperationException("Can't answer with text to a ranged question");
		if (answer.getQuestion().isMultipleChoicesSupport() != null)
			throw new UnsupportedOperationException("Can't answer with text to a multiple choice question");
		answer.setText(text);
		answer.getFilling().addAnswer(answer);
		em.persist(answer);
		em.flush();
		return answer.getId();
	}
	
	public Integer createRangedAnswer(Integer rangeValue, Integer questionId, Integer fillingId) {
		if (rangeValue == null)
			throw new RangeOutOfBoundException("Range upper bound can't be negative");
		Answer answer = assembleAnswer(questionId, fillingId);
		if (answer.getQuestion().getUpperBound() == null)
			throw new UnsupportedOperationException("Can't answer with range to a non-ranged question");
		answer.setRangeValue(rangeValue);
		answer.getFilling().addAnswer(answer);
		em.persist(answer);
		em.flush();
		return answer.getId();
	}

}
