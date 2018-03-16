package com.hdw.mccable.dto;

public class ChangeAndCancelServiceApplicationBean  extends DSTPUtilityBean{
	private StatusBean status;
	private ServiceApplicationBean serviceApplication;

	public StatusBean getStatus() {
		return status;
	}

	public void setStatus(StatusBean status) {
		this.status = status;
	}

	public ServiceApplicationBean getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplicationBean serviceApplication) {
		this.serviceApplication = serviceApplication;
	}
}