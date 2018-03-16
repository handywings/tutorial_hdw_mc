package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.hdw.mccable.dto.MenuReportBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/menureport")
public class MenuReportController extends BaseController{
	
	final static Logger logger = Logger.getLogger(MenuReportController.class);
	public static final String CONTROLLER_NAME = "menureport/";
	Gson g = new Gson();
	
	//initial service
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initUnit(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initUnit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<MenuReportBean> menuReportBeans = new ArrayList<MenuReportBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<MenuReport> menuReports = unitService.findAllMenuReport();
				if(null != menuReports && menuReports.size() > 0){
					for(MenuReport menuReport:menuReports){
						MenuReportBean menuReportBean = populateEntityToDto(menuReport);
						menuReportBeans.add(menuReportBean);
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
		modelAndView.addObject("menuReports", menuReportBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}

	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getPosition(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if(isPermission()){
			try{
				MenuReport menuReport = unitService.getMenuReportById(Long.valueOf(id));
				if(menuReport != null){
					MenuReportBean menuReportBean = populateEntityToDto(menuReport);
					jsonResponse.setError(false);
					jsonResponse.setResult(menuReportBean);
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
	public JsonResponse updateMenuReport(@RequestBody final MenuReportBean menuReportBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateMenuReport][Type : Controller]");
				if(null != menuReportBean){
					//update zoneBean
					MenuReport menuReport = unitService.getMenuReportById(menuReportBean.getId());
					if(null != menuReport){
						menuReport.setMenuReportName(menuReportBean.getMenuReportName());
						menuReport.setMenuReportCode(menuReportBean.getMenuReportCode());
						menuReport.setUpdatedDate(CURRENT_TIMESTAMP);
						menuReport.setUpdatedBy(getUserNameLogin());
						unitService.update(menuReport);
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
		return jsonResponse;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveMenuReport(@RequestBody final MenuReportBean menuReportBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveMenuReport][Type : Controller]");
				if(null != menuReportBean){
					//save zoneBean
					MenuReport menuReport = new MenuReport();
					menuReport.setMenuReportName(menuReportBean.getMenuReportName());
					menuReport.setMenuReportCode(menuReportBean.getMenuReportCode());
					menuReport.setCreateDate(CURRENT_TIMESTAMP);
					menuReport.setCreatedBy(getUserNameLogin());
					unitService.save(menuReport);
					jsonResponse.setError(false);
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
	
	
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteMenuReport(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteMenuReport][Type : Controller]");
		logger.info("[UnitId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				MenuReport menuReport = unitService.getMenuReportById(Long.valueOf(id));
				if(menuReport != null){
					unitService.delete(menuReport);
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
		
		return jsonResponse;
	}
	
	
	public MenuReportBean populateEntityToDto(MenuReport menuReport){
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		MenuReportBean menuReportBean = new MenuReportBean();
		menuReportBean.setId(menuReport.getId());
		menuReportBean.setMenuReportCode(menuReport.getMenuReportCode());
		menuReportBean.setMenuReportName(menuReport.getMenuReportName());
		menuReportBean.setCreateBy(menuReport.getCreatedBy());
		menuReportBean.setCreateDate(menuReport.getCreateDate());
		menuReportBean.setCreateDateTh(null==menuReport.getCreateDate()?"":formatDataTh.format(menuReport.getCreateDate()));
		menuReportBean.setUpdateBy(menuReport.getUpdatedBy());
		menuReportBean.setUpdateDate(menuReport.getUpdatedDate());
		menuReportBean.setUpdateDateTh(null==menuReport.getUpdatedDate()?"":formatDataTh.format(menuReport.getUpdatedDate()));
		return menuReportBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
}
