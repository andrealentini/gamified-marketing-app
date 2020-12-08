package it.polimi.gamifiedmarketingapp.services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Filling;
import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.PermissionDeniedException;

@Stateless
public class FillingService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public void addFilling(int registeredUserId, int masterQuestionnaireId) {
		RegisteredUser registeredUser = em.find(RegisteredUser.class, registeredUserId);
		if (registeredUser == null)
			throw new EntryNotFoundException("Registered user not found");
		MasterQuestionnaire masterQuestionnaire = em.find(MasterQuestionnaire.class, masterQuestionnaireId);
		if (masterQuestionnaire == null)
			throw new EntryNotFoundException("Master questionnaire not found");
		if (registeredUser.isBlocked())
			throw new PermissionDeniedException("A blocked user can't fill a questionnaire");
		Filling filling = new Filling(registeredUser, masterQuestionnaire, new Date());
		em.persist(filling);
		masterQuestionnaire.addFilling(filling);
	}

}
