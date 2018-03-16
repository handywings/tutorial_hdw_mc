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
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/zone")
public class ZoneController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ZoneController.class);
	public static final String CONTROLLER_NAME = "zone/";
	Gson g = new Gson();
	
	//initial service
	
	@Autowired(required=true)
	@Qualifier(value="zoneService")
	private ZoneService zoneService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initZone(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initZone][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<Zone> zones = zoneService.findAll();
				if(null != zones && zones.size() > 0){
					for(Zone zone:zones){
						ZoneBean zoneBean = populateEntityToDto(zone);
						zoneBeans.add(zoneBean);
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
		modelAndView.addObject("zones", zoneBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getPosition(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		ZoneBean zoneBeans = new ZoneBean();
		
		if(isPermission()){
			try{
				Zone zone = zoneService.getZoneById(Long.valueOf(id));
				if(zone != null){
					zoneBeans = populateEntityToDto(zone);
					jsonResponse.setError(false);
					jsonResponse.setResult(zoneBeans);
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
	public JsonResponse updateZone(@RequestBody final ZoneBean zoneBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateZone][Type : Controller]");
//				logger.info("[zoneBean : "+g.toJson(zoneBean)+"]");
				if(null != zoneBean){
					//update zoneBean
					Zone zone = zoneService.getZoneById(Long.valueOf(zoneBean.getId()));
					if(null != zone){
						zone.setZoneName(zoneBean.getZoneName());
						zone.setZoneDetail(zoneBean.getZoneDetail());
						zone.setZoneType(zoneBean.getZoneType());
						zone.setUpdatedDate(CURRENT_TIMESTAMP);
						zone.setUpdatedBy(getUserNameLogin());
						zoneService.update(zone);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("zone.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveZone(@RequestBody final ZoneBean zoneBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveZone][Type : Controller]");
//				logger.info("[zoneBean : "+g.toJson(zoneBean)+"]");
				if(null != zoneBean){
					//save zoneBean
					Zone zone = new Zone();
					zone.setZoneName(zoneBean.getZoneName());
					zone.setZoneDetail(zoneBean.getZoneDetail());
					zone.setZoneType(zoneBean.getZoneType());
					zone.setCreateDate(CURRENT_TIMESTAMP);
					zone.setCreatedBy(getUserNameLogin());
					zoneService.save(zone);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("zone.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteZone(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteZone][Type : Controller]");
		logger.info("[ZoneId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Zone zone = zoneService.getZoneById(Long.valueOf(id));
				if(zone != null){
					zoneService.delete(zone);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("zone.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public ZoneBean populateEntityToDto(Zone zone){
		ZoneBean zoneBean = new ZoneBean();
		
		if(null != zone){
			zoneBean.setId(zone.getId());
			zoneBean.setZoneName(zone.getZoneName());
			zoneBean.setZoneDetail(zone.getZoneDetail());
			zoneBean.setZoneType(zone.getZoneType());
		}
		
		return zoneBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public void setZoneService(ZoneService zoneService) {
		this.zoneService = zoneService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
