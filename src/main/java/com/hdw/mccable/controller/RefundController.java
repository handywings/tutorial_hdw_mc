package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController{
	final static Logger logger = Logger.getLogger(RefundController.class);
	public static final String CONTROLLER_NAME = "refund/";
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired
    MessageSource messageSource;

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initRefund][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			ApplicationSearchBean applicationSearchBean = (ApplicationSearchBean) session.getAttribute("applicationSearchBean");
			if(applicationSearchBean != null){
				modelAndView.addObject("applicationSearchBean", applicationSearchBean);
			}else{
				applicationSearchBean = new ApplicationSearchBean();
				modelAndView.addObject("applicationSearchBean", applicationSearchBean);
			}
			applicationSearchBean.setStatus("I");
			//search
			List<ServiceApplication> serviceApplications = serviceApplicationService.searchServiceApplicationByStatusAndRefund(applicationSearchBean);
			 //populate
			 List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
			 ServiceApplicationListController serAppCon = new ServiceApplicationListController();
			 
			 serAppCon.setMessageSource(messageSource);
			 serAppCon.setServiceApplicationService(serviceApplicationService);
			 for(ServiceApplication serviceApplication : serviceApplications){
					ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
					serviceApplicationBean = serAppCon.populateEntityToDtoForList(serviceApplication);
					//add to list
					serviceApplicationBeans.add(serviceApplicationBean);
				}
			 modelAndView.addObject("serviceApplicationBeans", serviceApplicationBeans);
			 
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
				
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		session.removeAttribute("applicationSearchBean");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}	
	
	@RequestMapping(value="updaterefund/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse updaterefund(@PathVariable Long id, HttpServletRequest request) {
		logger.info("[method : updaterefund][Type : Controller]");
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(id);
				if(serviceApplication != null){
					serviceApplication.setRefundDate(CURRENT_TIMESTAMP);
					serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
					serviceApplication.setUpdatedBy(getUserNameLogin());
					serviceApplicationService.update(serviceApplication);
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
//		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("serviceapplication.transaction.save.success", null, LocaleContextHolder.getLocale()));
		
		return jsonResponse;
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("applicationSearchBean")
	ApplicationSearchBean applicationSearchBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		
		logger.info("[method : search][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(applicationSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		modelAndView.setViewName(REDIRECT+"/refund");
		return modelAndView;
	}
	
	
	public void generateSearchSession(ApplicationSearchBean applicationSearchBean, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("applicationSearchBean", applicationSearchBean);
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
