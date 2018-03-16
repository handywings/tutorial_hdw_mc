package com.hdw.mccable.dto;

import java.util.List;

public class TemplateServiceBean extends DSTPUtilityBean{
	
	private Long id;
	private List<TemplateServiceItemBean> listTemplateServiceItemBean;
	private float amount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<TemplateServiceItemBean> getListTemplateServiceItemBean() {
		return listTemplateServiceItemBean;
	}
	public void setListTemplateServiceItemBean(List<TemplateServiceItemBean> listTemplateServiceItemBean) {
		this.listTemplateServiceItemBean = listTemplateServiceItemBean;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "TemplateServiceBean [id=" + id + ", listTemplateServiceItemBean=" + listTemplateServiceItemBean
				+ ", amount=" + amount + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
				+ ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()="
				+ getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
