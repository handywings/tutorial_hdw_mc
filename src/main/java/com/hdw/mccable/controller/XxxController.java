package com.hdw.mccable.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/xxx")
public class XxxController extends BaseController{
	final static Logger logger = Logger.getLogger(XxxController.class);
	public static final String CONTROLLER_NAME = "xxx/";

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initXxx(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initXxx][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
}
