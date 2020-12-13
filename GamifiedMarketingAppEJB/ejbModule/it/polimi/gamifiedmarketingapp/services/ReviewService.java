package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.entities.Review;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;

@Stateless
public class ReviewService {
	
	private static Integer TITLE_LENGTH = 250;
	private static Integer TEXT_LENGTH = 500;
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public ReviewService() {}
	
	public void addReview(String title, String text, Integer registeredUserId, Integer productId) {
		if (registeredUserId == null)
			throw new IllegalArgumentException("Registered user ID can't be null");
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		if (title == null) 
			throw new IllegalArgumentException("Title can't be null");
		if (title.length() > TITLE_LENGTH)
			throw new FieldLengthException("Title too long");
		if (text != null && text.length() > TEXT_LENGTH)
			throw new FieldLengthException("Text too long");
		RegisteredUser registeredUser = em.find(RegisteredUser.class, registeredUserId);
		if (registeredUser == null)
			throw new EntryNotFoundException("Registered user not found");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		Review review = new Review(title, text, registeredUser, product);
		product.addReview(review);
		em.persist(review);
	}
	
	public void removeReview(Integer reviewId) {
		if (reviewId == null)
			throw new IllegalArgumentException("Review ID can't be null");
		Review review = em.find(Review.class, reviewId);
		if (review == null)
			throw new IllegalArgumentException("Review not found");
		Product product = review.getProduct();
		product.removeReview(review);
		em.remove(review);
	}

}
