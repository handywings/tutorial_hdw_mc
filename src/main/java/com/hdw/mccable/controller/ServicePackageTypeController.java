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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/servicepackagetype")
public class ServicePackageTypeController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ServicePackageTypeController.class);
	public static final String CONTROLLER_NAME = "servicepackagetype/";
	Gson g = new Gson();
	
	//initial service
	
	@Autowired(required=true)
	@Qualifier(value="servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initZone(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initZone][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
				if(null != servicePackageTypes && !servicePackageTypes.isEmpty()){
					for(ServicePackageType servicePackageType:servicePackageTypes){
						ServicePackageTypeBean servicePackageTypeBean = populateEntityToDto(servicePackageType);
						servicePackageTypeBean.setCountPackage(servicePackageTypeService.getNumberOfCountPackage(servicePackageTypeBean.getId()));
						if(null != servicePackageTypeBean){
							servicePackageTypeBeans.add(servicePackageTypeBean);
						}
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("servicePackageTypes", servicePackageTypeBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getDataForEdit(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if(isPermission()){
			try{
				ServicePackageType servicePackageType = servicePackageTypeService.getServicePackageTypeById(Long.valueOf(id));
				if(servicePackageType != null){
					ServicePackageTypeBean servicePackageTypeBean = populateEntityToDto(servicePackageType);
					jsonResponse.setError(false);
					jsonResponse.setResult(servicePackageTypeBean);
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
		
		return jsonResponse;
	}
	
	@RequestMapping(value="update", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updateServicePackageTypeBean(@RequestBody final ServicePackageTypeBean servicePackageTypeBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateServicePackageTypeBean][Type : Controller]");
//				logger.info("[servicePackageTypeBean : "+g.toJson(servicePackageTypeBean)+"]");
				if(null != servicePackageTypeBean){
					ServicePackageType servicePackageType = servicePackageTypeService.getServicePackageTypeById(Long.valueOf(servicePackageTypeBean.getId()));
					if(null != servicePackageType){
						//update
						servicePackageType.setPackageTypeName(servicePackageTypeBean.getPackageTypeName());
						servicePackageType.setDescription(servicePackageTypeBean.getDescription());
						servicePackageType.setUpdatedDate(CURRENT_TIMESTAMP);
						servicePackageType.setUpdatedBy(getUserNameLogin());
						servicePackageTypeService.update(servicePackageType);
						jsonResponse.setError(false);
					}else{
						jsonResponse.setError(true);
					}
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("servicePackageType.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveServicePackageTypeBean(@RequestBody final ServicePackageTypeBean servicePackageTypeBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveServicePackageTypeBean][Type : Controller]");
//				logger.info("[servicePackageTypeBean : "+g.toJson(servicePackageTypeBean)+"]");
				if(null != servicePackageTypeBean){
					//save
					ServicePackageType servicePackageType = new ServicePackageType();
					servicePackageType.setPackageTypeName(servicePackageTypeBean.getPackageTypeName());
					servicePackageType.setDescription(servicePackageTypeBean.getDescription());
					servicePackageType.setPackageTypeCode(servicePackageTypeService.genPackageTypeCode());
					servicePackageType.setCreateDate(CURRENT_TIMESTAMP);
					servicePackageType.setCreatedBy(getUserNameLogin());
					servicePackageTypeService.save(servicePackageType);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("servicePackageType.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteServicePackageType(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteServicePackageType][Type : Controller]");
		logger.info("[ServicePackageTypeId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				ServicePackageType servicePackageType = servicePackageTypeService.getServicePackageTypeById(Long.valueOf(id));
				if(servicePackageType != null){
					servicePackageTypeService.delete(servicePackageType);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("servicePackageType.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public ServicePackageTypeBean populateEntityToDto(ServicePackageType servicePackageType){
		ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
		
		if(null != servicePackageType){			
			try {
				servicePackageTypeBean.setId(servicePackageType.getId());
				servicePackageTypeBean.setPackageTypeCode(servicePackageType.getPackageTypeCode());
				servicePackageTypeBean.setPackageTypeName(servicePackageType.getPackageTypeName());
				servicePackageTypeBean.setDescription(servicePackageType.getDescription());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return servicePackageTypeBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public ServicePackageTypeService getServicePackageTypeService() {
		return servicePackageTypeService;
	}

	public void setServicePackageTypeService(ServicePackageTypeService servicePackageTypeService) {
		this.servicePackageTypeService = servicePackageTypeService;
	}
	
	
}
