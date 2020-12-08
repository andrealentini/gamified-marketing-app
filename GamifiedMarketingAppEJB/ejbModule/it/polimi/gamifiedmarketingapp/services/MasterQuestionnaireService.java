package it.polimi.gamifiedmarketingapp.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.MasterQuestionnaire;
import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.Questionnaire;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.IntegrityException;
import it.polimi.gamifiedmarketingapp.exceptions.NonUniqueEntryException;

@Stateless
public class MasterQuestionnaireService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public MasterQuestionnaire findMasterQuestionnaireByProduct(int productId) {
		List<MasterQuestionnaire> masterQuestionnaires = em.createNamedQuery("MasterQuestionnaire.findByProduct", MasterQuestionnaire.class)
				.setParameter("productId", productId)
				.getResultList();
		if (masterQuestionnaires == null)
			return null;
		if (masterQuestionnaires.size() == 1)
			return masterQuestionnaires.get(0);
		throw new NonUniqueResultException("More than one master questionnaire with the same product");
	}
	
	public void createMasterQuestionnaire(int marketingSectionId, int statisticalSectionId, int productId) {
		if (marketingSectionId == statisticalSectionId)
			throw new IntegrityException("Marketing and statistical exception can't be the same objects");
		Questionnaire marketingSection = em.find(Questionnaire.class, marketingSectionId);
		if (marketingSection == null)
			throw new EntryNotFoundException("Marketing section not found");
		Questionnaire statisticalSection = em.find(Questionnaire.class, statisticalSectionId);
		if (statisticalSection == null)
			throw new EntryNotFoundException("Statistical section not found");
		if (!marketingSection.isMarketing())
			throw new IntegrityException("Marketing section must correspond to a marketing questionnaire");
		if (statisticalSection.isMarketing())
			throw new IntegrityException("Statistical section must correspond to a statistical questionnaire");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		MasterQuestionnaire queriedMasterQuestionnaire = this.findMasterQuestionnaireByProduct(productId);
		if (queriedMasterQuestionnaire != null)
			throw new NonUniqueEntryException("Product already linked to a master questionnaire");
		MasterQuestionnaire masterQuestionnaire = new MasterQuestionnaire(marketingSection, statisticalSection, product);
		em.persist(masterQuestionnaire);
		product.setMasterQuestionnaire(masterQuestionnaire);
	}
	
}
