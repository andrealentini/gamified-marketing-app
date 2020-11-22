package it.polimi.gamifiedmarketingapp.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.exceptions.CredentialsException;
import it.polimi.gamifiedmarketingapp.exceptions.UpdateProfileException;

@Stateless
public class RegisteredUserService {

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;

	public RegisteredUserService() {}
	
	public RegisteredUser checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
		List<RegisteredUser> registeredUsers = null;
		try {
			registeredUsers = em.createNamedQuery("RegisteredUser.checkCredentials", RegisteredUser.class)
								.setParameter(1, username).setParameter(2, password)
								.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentials");
		}
		if (registeredUsers.isEmpty())
			return null;
		else if (registeredUsers.size() == 1)
			return registeredUsers.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

	public void updateProfile(RegisteredUser r) throws UpdateProfileException {
		try {
			em.merge(r);
		} catch (PersistenceException e) {
			throw new UpdateProfileException("Could not change profile");
		}
	}
	
}
