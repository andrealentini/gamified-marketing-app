package it.polimi.gamifiedmarketingapp.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
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
	
	public Filling findByRegisteredUserIdAndMasterQuestionnaireId(Integer registeredUserId, Integer masterQuestionnaireId) {
		if (registeredUserId == null)
			throw new IllegalArgumentException("Registered user ID can't be null");
		if (masterQuestionnaireId == null)
			throw new IllegalArgumentException("Master questionnaire ID can't be null");
		List<Filling> fillings = em.createNamedQuery("Filling.findByRegisteredUserIdAndMasterQuestionnaireId", Filling.class)
				.setParameter("registeredUserId", registeredUserId)
				.setParameter("masterQuestionnaireId", masterQuestionnaireId)
				.getResultList();
		if (fillings == null)
			return null;
		if (fillings.size() == 1)
			return fillings.get(0);
		throw new NonUniqueResultException("More than one filling with the same registered user and the same master questionnaire");
	}
	
	public Integer addFilling(Integer registeredUserId, Integer masterQuestionnaireId) {
		if (registeredUserId == null)
			throw new IllegalArgumentException("Registered user ID can't be null");
		if (masterQuestionnaireId == null)
			throw new IllegalArgumentException("Master questionnaire ID can't be null");
		Filling test = findByRegisteredUserIdAndMasterQuestionnaireId(registeredUserId, masterQuestionnaireId);
		if (test != null)
			throw new UnsupportedOperationException("Can't save two fillings for the same registered user and the same master questionnaire");
		RegisteredUser registeredUser = em.find(RegisteredUser.class, registeredUserId);
		if (registeredUser == null)
			throw new EntryNotFoundException("Registered user not found");
		MasterQuestionnaire masterQuestionnaire = em.find(MasterQuestionnaire.class, masterQuestionnaireId);
		if (masterQuestionnaire == null)
			throw new EntryNotFoundException("Master questionnaire not found");
		if (registeredUser.isBlocked())
			throw new PermissionDeniedException("A blocked user can't fill a questionnaire");
		Filling filling = new Filling(registeredUser, masterQuestionnaire, new Date());
		masterQuestionnaire.addFilling(filling);
		em.persist(filling);
		em.flush();
		return filling.getId();
	}

}
