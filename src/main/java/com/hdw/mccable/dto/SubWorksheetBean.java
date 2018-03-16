package com.hdw.mccable.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.hdw.mccable.entity.Worksheet;

public class SubWorksheetBean extends DSTPUtilityBean{
	
	@Autowired
    MessageSource messageSource;
	
	private Long id;
	private String workSheetType;
	private String remark;
	private Worksheet workSheet;
	private float price;
	private String workSheetTypeText;
	
	public void loadWorksheetTypeText(){
		String type = getWorkSheetType();
		String typeText = "";
		if (type.equals(
				messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.addpoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.connect.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.setup.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.tune.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.repair", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.repair.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.addsettop", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.addsettop.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.movepoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.reducepoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.cut.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.move.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.borrow", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.borrow.text", null, LocaleContextHolder.getLocale());
		}
		
		setWorkSheetTypeText(typeText);
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWorkSheetType() {
		return workSheetType;
	}
	public void setWorkSheetType(String workSheetType) {
		this.workSheetType = workSheetType;
	}
	public Worksheet getWorkSheet() {
		return workSheet;
	}
	public void setWorkSheet(Worksheet workSheet) {
		this.workSheet = workSheet;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public String getWorkSheetTypeText() {
		return workSheetTypeText;
	}

	public void setWorkSheetTypeText(String workSheetTypeText) {
		this.workSheetTypeText = workSheetTypeText;
	}
}
