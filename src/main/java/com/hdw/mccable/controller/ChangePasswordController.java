package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.PermissionGroup;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.Position;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.service.AuthenService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.SecurityUtils;


@Controller
@RequestMapping("/changepassword")
public class ChangePasswordController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ChangePasswordController.class);
	public static final String CONTROLLER_NAME = "changepassword/";
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired(required = true)
	@Qualifier(value = "authenService")
	private AuthenService authenService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initChangePassword(Model model, HttpServletRequest request, 
			HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initChangePassword][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();

		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission 
		if (isPermission()) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}			
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}		

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}

	@RequestMapping(value = "json/validateOldPassword", method = RequestMethod.POST)
	@ResponseBody
	public String validateOldPassword(HttpServletRequest request,
			@RequestParam("old_pass") String old_pass) {
		logger.info("[method : validateOldPassword][Type : Controller]");
		String status = "";
		if (isPermission()) {
			try {
				HttpSession session = request.getSession();	
				LoginForm loginForm = new LoginForm();
				Authentication authenSession = new Authentication();
				if(session == null){
					status = "session expire";
		        } else {
		        	authenSession = (Authentication) session.getAttribute("authen");
				}
				loginForm.setUsername(authenSession.getUsername());
				loginForm.setPassword(old_pass);
				Authentication authen = authenService.findByUserNamePassword(loginForm);
				if (null == authen) {
					status = "password not match";
				} else {
					status = "success";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return status;
	}
	
	
	@RequestMapping(value = "confirmChangePassword", method = RequestMethod.POST)
	@ResponseBody
	public String confirmChangePassword(HttpServletRequest request,
			@RequestParam("new_pass") String new_pass,
			@RequestParam("old_pass") String old_pass) {
		logger.info("[method : confirmChangePassword][Type : Controller]");
		String status = "";
		if (isPermission()) {
			try {
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
				HttpSession session = request.getSession();	
				LoginForm loginForm = new LoginForm();
				Authentication authenSession = new Authentication();
				if(session == null){
					status = "session expire";
		        } else {
		        	authenSession = (Authentication) session.getAttribute("authen");
				}
				loginForm.setUsername(authenSession.getUsername());
				loginForm.setPassword(old_pass);
				Authentication authen = authenService.findByUserNamePassword(loginForm);
				if (null != authen) {
					status = "success";
					authen.setUpdatedBy(getUserNameLogin());
					authen.setUpdatedDate(CURRENT_TIMESTAMP);
					authen.setPassword(SecurityUtils.encrypt(new_pass));
					
					authenService.update(authen);
					session.removeAttribute("authen");
		            session.invalidate();
		            session = null;
				} 
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return status;
	}
	
}
