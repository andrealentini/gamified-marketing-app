package it.polimi.gamifiedmarketingapp.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.exceptions.ProductNotFoundException;

@Stateless	//Stateless Java Bean that doesn't mantain or depend to session information
public class ProductService {
	
	private static int PRODUCT_NAME_LENGTH = 45;

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;

	public ProductService() {}
	
	public void createProduct(String name) {
		if (name == null)
			throw new IllegalArgumentException("Product name can't be null");
		if (name.length() > PRODUCT_NAME_LENGTH)
			throw new FieldLengthException("Product name can't be more than 45 characters long");
		Product product = new Product(name);
		em.persist(product);
	}
	
	public void setProductPicture(int productId, byte[] picture) {
		if (productId < 0)
			throw new IllegalArgumentException("Product ID can't be negative");
		if (picture == null)
			throw new IllegalArgumentException("Product picture can't be null");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new ProductNotFoundException("Product not found");
		product.setPicture(picture);
	}
	
}
