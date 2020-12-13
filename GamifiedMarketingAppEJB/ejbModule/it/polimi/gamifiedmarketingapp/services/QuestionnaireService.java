package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Questionnaire;

@Stateless
public class QuestionnaireService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public Questionnaire findQuestionnaireById(Integer questionnaireId) {
		if (questionnaireId == null)
			throw new IllegalArgumentException("Questionnaire ID can't be null");
		Questionnaire questionnaire = em.find(Questionnaire.class, questionnaireId);
		return questionnaire;
	}
	
	public Integer createQuestionnaire(Boolean isMarketing) {
		if (isMarketing == null)
			throw new IllegalArgumentException("Marketing flag can't be null");
		Questionnaire questionnaire = new Questionnaire(isMarketing);
		em.persist(questionnaire);
		em.flush();
		return questionnaire.getId();
	}

}
