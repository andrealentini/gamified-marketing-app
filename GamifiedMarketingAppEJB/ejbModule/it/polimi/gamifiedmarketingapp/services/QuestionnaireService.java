package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Questionnaire;

@Stateless
public class QuestionnaireService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public int createQuestionnaire(boolean isMarketing) {
		Questionnaire questionnaire = new Questionnaire(isMarketing);
		em.persist(questionnaire);
		em.flush();
		return questionnaire.getId();
	}

}
