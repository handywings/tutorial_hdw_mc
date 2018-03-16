package com.hdw.mccable.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ServiceApplicationAssignCashierId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private ServiceApplication serviceApplication;
	
	@ManyToOne
    private Personnel personnel;

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
    
}
