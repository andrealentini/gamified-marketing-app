package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Question;
import it.polimi.gamifiedmarketingapp.entities.QuestionChoice;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;

@Stateless
public class QuestionChoiceService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public void createQuestionChoice(String text, Integer questionId) {
		if (questionId == null)
			throw new IllegalArgumentException("Question ID can't be null");
		if (text == null)
			throw new IllegalArgumentException("Text can't be null");
		if (text.length() > QuestionChoice.TEXT_LENGTH)
			throw new FieldLengthException("Question choice text too long");
		Question question = em.find(Question.class, questionId);
		if (question == null)
			throw new EntryNotFoundException("Question not found");
		QuestionChoice questionChoice = new QuestionChoice(text, question);
		question.addQuestionChoice(questionChoice);
		em.persist(questionChoice);
	}

}
