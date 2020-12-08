package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	
	public void createAnswerChoice(int answerId, int questionChoiceId) {
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
