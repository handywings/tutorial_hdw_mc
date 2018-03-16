package com.hdw.mccable.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class CustomerTypeBean extends DSTPUtilityBean{
	
	@Autowired
	MessageSource messageSource;
	
	private String value;
	
	public void inDividual(){
		setValue(messageSource.getMessage("customer.type.Individual", null, LocaleContextHolder.getLocale()));
	}
	public void corPorate(){
		setValue(messageSource.getMessage("customer.type.Corporate", null, LocaleContextHolder.getLocale()));
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	} 
}
