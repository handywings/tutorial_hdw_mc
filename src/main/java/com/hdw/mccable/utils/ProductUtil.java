package com.hdw.mccable.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class ProductUtil {
	
	public static final int STATUS_INACTIVE = 0; // เบิกตัดสค๊อคทันที่ (ชำรุด)
	public static final int STATUS_ACTIVE = 1; // ปกติ 
	public static final int STATUS_HOLD = 2; // พักไว้ก่อนเด้อ (จอง) ถ้าใบงานเสร็จจะขาขขาด 
	public static final int STATUS_LEND = 3; // ยืม  
	public static final int STATUS_RESERVE = 4; // สำรองช่าง
	public static final int STATUS_SELL = 5; // ขายขาด
	public static final int STATUS_REPAIR = 6; // ซ่อม
	public static final int STATUS_CA = 7; // สถานะ CA
	
	@Autowired
	MessageSource messageSource;
	
	//send status type to convert to string
		public String getMessageStatusProduct(int type){
			String messageStatus = null;
			switch(type){
				case STATUS_INACTIVE :
					messageStatus = messageSource.getMessage("status.equipmentproduct.inactive", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_ACTIVE :
					messageStatus = messageSource.getMessage("status.equipmentproduct.active", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_HOLD :
					messageStatus = messageSource.getMessage("status.equipmentproduct.hold", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_LEND :
					messageStatus = messageSource.getMessage("status.equipmentproduct.lend", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_RESERVE :
					messageStatus = messageSource.getMessage("status.equipmentproduct.reserve", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_SELL :
					messageStatus = messageSource.getMessage("status.equipmentproduct.sell", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_REPAIR :
					messageStatus = messageSource.getMessage("status.equipmentproduct.repair", null, LocaleContextHolder.getLocale());
					break;
				case STATUS_CA :
					messageStatus = messageSource.getMessage("status.equipmentproduct.ca", null, LocaleContextHolder.getLocale());
					break;
			}
			return messageStatus;
		}

		public void setMessageSource(MessageSource messageSource) {
			this.messageSource = messageSource;
		}
}
