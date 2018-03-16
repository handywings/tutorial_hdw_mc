package com.hdw.mccable.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hdw.mccable.dto.AuthenticationBean;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Personnel;

public class BaseController {
	
	public static final String INIT = "init";
	public static final String LIST = "list";
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String VIEW = "view";
//	public static final String PERMISSION_DENIED = "permission/index";
	public static final String PERMISSION_DENIED = "redirect:/login";
	public static final long COMPANY_PARENT = 0;
	public static final String REDIRECT = "redirect:";
	public static final long ZERO_VALUE = 0;
	public static final String EMPTY_STRING = "";
	public static final int QUANTITY_PRODUCT_ITEM_WORKSHEET = 1;
//	public static final Timestamp CURRENT_TIMESTAMP = new Timestamp(Calendar.getInstance(new Locale("EN", "en")).getTimeInMillis());
//	public static final Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
	protected static String userNameLogin;

	public static final int STATUS_INACTIVE = 0; // เบิกตัดสค๊อคทันที่ (ชำรุด)
	public static final int STATUS_ACTIVE = 1; // ปกติ 
	public static final int STATUS_HOLD = 2; // พักไว้ก่อนเด้อ (จอง) ถ้าใบงานเสร็จจะขาขขาด หรือ ยืม
	public static final int STATUS_LEND = 3; // ยืม  
	public static final int STATUS_RESERVE = 4; // สำรองช่าง
	public static final int STATUS_SELL = 5; // ขายขาด
	public static final int STATUS_REPAIR = 6; // ซ่อม
	public static final int STATUS_CA = 7; // สถานะ CA
	
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	public static final String WAIT_FOR_PAY = "H"; // H = สถานะแรกคือ "รอจ่ายงาน"
	public static final String CORPORATE = "C";
	public static final String FILE_TYPE_IMAGE = "I"; //I = รูป	
	public static final String FILE_TYPE_HOUSE_REGISTRATION = "H"; // H = สำเนาทะเบียนบ้าน
	public static final String FILE_TYPE_IDENTITY = "P"; // P = สำเนาบัตรประจำตัวประชาชน
	public static final String FILE_TYPE_OTHER = "O"; // O เอกสารอื่นๆ

	//check permission
	public boolean isPermission(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Authentication authen = (Authentication) httpSession.getAttribute("authen");
	    if(authen !=null)return true;
		
	    return false;
	}
	
	public static String getUserNameLogin() {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Authentication authen = (Authentication) httpSession.getAttribute("authen");
	    if(authen == null || authen.getPersonnel() == null)return "Admin_"+CURRENT_TIMESTAMP;
	    else{
	    	String userNameLogin = String.format("%1s %2s (%3s)",
	    			authen.getPersonnel().getFirstName(),
	    			authen.getPersonnel().getLastName(),
	    			authen.getPersonnel().getNickName());
	    	return userNameLogin;
	    }
	}
	
	public static Personnel getPersonnelLogin() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Authentication authen = (Authentication) httpSession.getAttribute("authen");
	    if(authen == null || authen.getPersonnel() == null)return null;
	    else return authen.getPersonnel();
	}
	
	//check authentication
	public boolean isAuthentication(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Authentication authen = (Authentication) httpSession.getAttribute("authen");
	    if(authen !=null)return true;
		
	    return false;
	}
	
	public String getUserLogin(){
		String userLogin = "" ;
		if(getAuthenticationBean() !=null && getAuthenticationBean().getPersonnel() != null){
			userLogin = getAuthenticationBean().getPersonnel().getPrefix()+getAuthenticationBean().getPersonnel().getFirstName()+" "+getAuthenticationBean().getPersonnel().getLastName();
		}
		return userLogin;
	}
	
	public AuthenticationBean getAuthenticationBean(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    AuthenticationBean authen = (AuthenticationBean) httpSession.getAttribute("authen");
	    
	    return authen;
	}
	
}
