package com.hdw.mccable.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customer_feature")
public class CustomerFeature {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "customerFeatureName")
	private String customerFeatureName;
	
	@Column(name = "customerFeatureCode")
	private String customerFeatureCode;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerFeature", cascade={CascadeType.ALL})
	List<Customer> customers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerFeatureName() {
		return customerFeatureName;
	}

	public void setCustomerFeatureName(String customerFeatureName) {
		this.customerFeatureName = customerFeatureName;
	}

	public String getCustomerFeatureCode() {
		return customerFeatureCode;
	}

	public void setCustomerFeatureCode(String customerFeatureCode) {
		this.customerFeatureCode = customerFeatureCode;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
