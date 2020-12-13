package it.polimi.gamifiedmarketingapp.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Answer;
import it.polimi.gamifiedmarketingapp.entities.AnswerChoice;
import it.polimi.gamifiedmarketingapp.entities.QuestionChoice;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.IntegrityException;

@Stateless
public class AnswerChoiceService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public AnswerChoice findByAnswerIdAndQuestionChoiceId(Integer answerId, Integer questionChoiceId) {
		if (answerId == null)
			throw new IllegalArgumentException("Answer ID can't be null");
		if (questionChoiceId == null)
			throw new IllegalArgumentException("Question choice ID can't be null");
		List<AnswerChoice> answerChoices = em.createNamedQuery("AnswerChoice.findByAnswerIdAndQuestionChoiceId", AnswerChoice.class)
				.setParameter("answerId", answerId)
				.setParameter("questionChoiceId", questionChoiceId)
				.getResultList();
		if (answerChoices == null)
			return null;
		if (answerChoices.size() == 1)
			return answerChoices.get(0);
		throw new NonUniqueResultException("More than one answer choice with the same answer and the same question choice");
	}
	
	public void createAnswerChoice(Integer answerId, Integer questionChoiceId) {
		if (answerId == null)
			throw new IllegalArgumentException("Answer ID can't be null");
		if (questionChoiceId == null)
			throw new IllegalArgumentException("Question choice ID can't be null");
		AnswerChoice test = findByAnswerIdAndQuestionChoiceId(answerId, questionChoiceId);
		if (test != null)
			throw new UnsupportedOperationException("Can't save two answer choices with same answer and same question choice");
		Answer answer = em.find(Answer.class, answerId);
		if (answer == null)
			throw new EntryNotFoundException("Answer not found");
		QuestionChoice questionChoice = em.find(QuestionChoice.class, questionChoiceId);
		if (questionChoice == null)
			throw new EntryNotFoundException("Question choice not found");
		if (questionChoice.getQuestion().getId() != answer.getQuestion().getId())
			throw new IntegrityException("Can't create an answer choice for a question different from the one of the answer");
		AnswerChoice answerChoice = new AnswerChoice(answer, questionChoice);
		em.persist(answerChoice);
		answer.addAnswerChoice(answerChoice);
	}

}
