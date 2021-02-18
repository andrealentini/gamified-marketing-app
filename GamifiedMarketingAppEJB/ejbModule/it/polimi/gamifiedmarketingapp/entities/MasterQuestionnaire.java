package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "master_questionnaire", schema = "gamified_marketing_app_db")
@NamedQueries({
	@NamedQuery(name = "MasterQuestionnaire.findByProduct", query = "SELECT m FROM MasterQuestionnaire m WHERE m.product.id = :productId"),
})
public class MasterQuestionnaire implements Serializable {

	private static final long serialVersionUID = 4900834410686723790L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "marketing_section")
	private Questionnaire marketingSection;
	
	@ManyToOne
	@JoinColumn(name = "statistical_section")
	private Questionnaire statisticalSection;
	
	@OneToOne
	@JoinColumn(name = "product")
	private Product product;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterQuestionnaire", cascade = CascadeType.REMOVE)
	@OrderBy("points DESC")
	private List<Filling> fillings;
	

	
	public MasterQuestionnaire() {
	}
	
	public MasterQuestionnaire(Questionnaire marketingSection, Questionnaire statisticalSection, Product product) {
		this.marketingSection = marketingSection;
		this.statisticalSection = statisticalSection;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Questionnaire getMarketingSection() {
		return marketingSection;
	}

	public void setMarketingSection(Questionnaire marketingSection) {
		this.marketingSection = marketingSection;
	}

	public Questionnaire getStatisticalSection() {
		return statisticalSection;
	}

	public void setStatisticalSection(Questionnaire statisticalSection) {
		this.statisticalSection = statisticalSection;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Filling> getFillings() {
		return fillings;
	}

	public void setFillings(List<Filling> fillings) {
		this.fillings = fillings;
	}
	
	public void addFilling(Filling filling) {
		getFillings().add(filling);
		filling.setMasterQuestionnaire(this);
	}
	
	public void removeFilling(Filling filling) {
		getFillings().remove(filling);
	}

	@Override
	public String toString() {
		return "MasterQuestionnaire [id=" + id + ", marketingSection=" + marketingSection + ", statisticalSection="
				+ statisticalSection + ", product=" + product + "]";
	}

}
