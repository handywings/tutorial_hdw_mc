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
@Table(name="service_application_type")
public class ServiceApplicationType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "serviceApplicationTypeName")
	private String serviceApplicationTypeName;
	
	@Column(name = "serviceApplicationTypeCode")
	private String serviceApplicationTypeCode;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplicationType", cascade={CascadeType.ALL})
	List<ServiceApplication> serviceApplications;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceApplicationTypeName() {
		return serviceApplicationTypeName;
	}

	public void setServiceApplicationTypeName(String serviceApplicationTypeName) {
		this.serviceApplicationTypeName = serviceApplicationTypeName;
	}

	public String getServiceApplicationTypeCode() {
		return serviceApplicationTypeCode;
	}

	public void setServiceApplicationTypeCode(String serviceApplicationTypeCode) {
		this.serviceApplicationTypeCode = serviceApplicationTypeCode;
	}

	public List<ServiceApplication> getServiceApplications() {
		return serviceApplications;
	}

	public void setServiceApplications(List<ServiceApplication> serviceApplications) {
		this.serviceApplications = serviceApplications;
	}
}
