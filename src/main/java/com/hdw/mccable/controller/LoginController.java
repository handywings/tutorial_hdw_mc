package com.hdw.mccable.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.service.AuthenService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.utils.ConstantUtils;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	
	final static Logger logger = Logger.getLogger(LoginController.class);
	public static final String CONTROLLER_NAME = "login/";
	
	@Autowired(required = true)
	@Qualifier(value = "authenService")
	private AuthenService authenService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initLogin(Model model, HttpServletRequest request) throws Exception {
		logger.info("[method : initLogin][Type : Controller]");
		
		Company company = companyService.getCompanyById(1L);
		
		// set url web
		String url = request.getRequestURL().toString();
		if(ConstantUtils.ISPROD){
			url = url.replace("http", "https");
        }
		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
		logger.info("baseURL : "+baseURL);
		ConstantUtils.PATH_WEB = baseURL+"images/";
		ConstantUtils.PATH_WEB_PROJECT = baseURL;
		
		
		
		logger.info("PATH_WEB : "+ConstantUtils.PATH_WEB);
		logger.info("PATH_WEB_PROJECT : "+ConstantUtils.PATH_WEB_PROJECT);
		// set url web
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "/authen", method = RequestMethod.POST)
	public ModelAndView authen(@ModelAttribute("loginForm") LoginForm loginForm,HttpServletRequest request) {
		logger.info("[method : authen][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		String loginStatus = "N";
		logger.info("username : " + loginForm.getUsername());
		logger.info("password : " + loginForm.getPassword());
		//check authen
		HttpSession session = request.getSession();
		try {			
			Authentication authen = authenService.findByUserNamePassword(loginForm);
			session.setAttribute("authen", authen);
			if(authen != null){
				loginStatus = "Y";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("loginStatus", loginStatus);
		modelAndView.setViewName(REDIRECT+"/");
		return modelAndView;
	}
	
	@RequestMapping(value = "removeSession", method = RequestMethod.GET)
	public ModelAndView logout(Model model,HttpServletRequest request) {
		logger.info("[method : logout][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		HttpSession session = request.getSession();		
		
		if(session != null){
			session.removeAttribute("authen");
            session.invalidate();
            session = null;
        }
		
		modelAndView.setViewName(REDIRECT+"/");
		return modelAndView;
	}
	
}
