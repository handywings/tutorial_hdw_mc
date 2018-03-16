package com.hdw.mccable.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "service_application_assign_cashier")
@AssociationOverrides({
		@AssociationOverride(name = "pk.serviceApplication",
			joinColumns = @JoinColumn(name = "serviceApplicationId")),
		@AssociationOverride(name = "pk.personnel",
			joinColumns = @JoinColumn(name = "personnelId")) })
public class ServiceApplicationAssignCashier {
	
	@EmbeddedId
	private ServiceApplicationAssignCashierId pk = new ServiceApplicationAssignCashierId();

	public ServiceApplicationAssignCashierId getPk() {
		return pk;
	}

	public void setPk(ServiceApplicationAssignCashierId pk) {
		this.pk = pk;
	}

}
