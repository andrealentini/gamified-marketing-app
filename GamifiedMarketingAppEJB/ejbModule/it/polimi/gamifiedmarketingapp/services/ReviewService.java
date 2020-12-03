package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.entities.Review;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.exceptions.UserNotFoundException;

@Stateless
public class ReviewService {
	
	private static int TITLE_LENGTH = 250;
	private static int TEXT_LENGTH = 500;
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public ReviewService() {}
	
	public void addReview(String title, String text, int registeredUserId, int productId) {
		if (title == null) 
			throw new IllegalArgumentException("Title can't be null");
		if (title.length() > TITLE_LENGTH)
			throw new FieldLengthException("Title too long");
		if (text != null && text.length() > TEXT_LENGTH)
			throw new FieldLengthException("Text too long");
		if (registeredUserId < 0)
			throw new IllegalArgumentException("Registered user ID can't be negative");
		if (productId < 0)
			throw new IllegalArgumentException("Project ID can't be negative");
		RegisteredUser registeredUser = em.find(RegisteredUser.class, registeredUserId);
		if (registeredUser == null)
			throw new UserNotFoundException("Registered user not found");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new UserNotFoundException("Product not found");
		Review review = new Review(title, text, registeredUser, product);
		product.addReview(review);
		em.persist(review);
	}
	
	public void removeReview(int reviewId) {
		if (reviewId < 0)
			throw new IllegalArgumentException("Review ID can't be negative");
		Review review = em.find(Review.class, reviewId);
		if (review == null)
			throw new IllegalArgumentException("Review not found");
		Product product = review.getProduct();
		product.removeReview(review);
		em.remove(review);
	}

}
