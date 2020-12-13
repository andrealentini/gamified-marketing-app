package it.polimi.gamifiedmarketingapp.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.exceptions.DateException;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;
import it.polimi.gamifiedmarketingapp.utils.DateComparator;

@Stateless	//Stateless Java Bean that doesn't mantain or depend to session information
public class ProductService {
	
	private static Integer PRODUCT_NAME_LENGTH = 45;

	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;

	public ProductService() {}
	
	public Product findProductById(Integer productId) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = em.find(Product.class, productId);
		return product;
	}
	
	public Product findProductByDate(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date can't be null");
		List<Product> products = em.createNamedQuery("Product.findByDate", Product.class)
				.setParameter("date", date)
				.getResultList();
		if (products == null)
			return null;
		if (products.size() == 1)
			return products.get(0);
		throw new NonUniqueResultException("More than one product with the same date");		
	}
	
	public Integer createProduct(String name, Date date) {
		if (name == null)
			throw new IllegalArgumentException("Product name can't be null");
		if (name.length() > PRODUCT_NAME_LENGTH)
			throw new FieldLengthException("Product name can't be more than 45 characters long");
		if (date == null)
			throw new IllegalArgumentException("Date can't be null");
		if (DateComparator.getInstance().compare(date, new Date()) < 0)
			throw new DateException("Date can't be older than today");
		Product queriedProduct = this.findProductByDate(date);
		if (queriedProduct != null)
			throw new DateException("Can't save two products with the same date");
		Product product = new Product(name, date);
		em.persist(product);
		em.flush();
		return product.getId();
	}
	
	public void setProductPicture(Integer productId, byte[] picture) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		if (picture == null)
			throw new IllegalArgumentException("Product picture can't be null");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		product.setPicture(picture);
	}
	
	public void deleteProduct(Integer productId) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		if (DateComparator.getInstance().compare(product.getDate(), new Date()) >= 0)
			throw new DateException("Delete is not supported for daily or future product");
		em.remove(product);
	}
	
}
