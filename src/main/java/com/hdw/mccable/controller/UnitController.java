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
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/unit")
public class UnitController extends BaseController{
	
	final static Logger logger = Logger.getLogger(UnitController.class);
	public static final String CONTROLLER_NAME = "unit/";
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
		List<UnitBean> unitBeans = new ArrayList<UnitBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<Unit> units = unitService.findAll();
				if(null != units && units.size() > 0){
					for(Unit unit:units){
						UnitBean unitBean = populateEntityToDto(unit);
						unitBeans.add(unitBean);
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
		modelAndView.addObject("units", unitBeans);
		
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
				Unit unit = unitService.getUnitById(Long.valueOf(id));
				if(unit != null){
					UnitBean unitBean = populateEntityToDto(unit);
					jsonResponse.setError(false);
					jsonResponse.setResult(unitBean);
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
	public JsonResponse updateUnit(@RequestBody final UnitBean unitBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateUnit][Type : Controller]");
//				logger.info("[unitBean : "+g.toJson(unitBean)+"]");
				if(null != unitBean){
					//update zoneBean
					Unit unit = unitService.getUnitById(Long.valueOf(unitBean.getId()));
					if(null != unit){
						unit.setUnitName(unitBean.getUnitName());
						unit.setUpdatedDate(CURRENT_TIMESTAMP);
						unit.setUpdatedBy(getUserNameLogin());
						unitService.update(unit);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("unit.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveUnit(@RequestBody final UnitBean unitBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveUnit][Type : Controller]");
//				logger.info("[unitBean : "+g.toJson(unitBean)+"]");
				if(null != unitBean){
//					Timestamp CURRENT_TIMESTAMP1 = new Timestamp(new Date().getTime());
					//save zoneBean
					Unit unit = new Unit();
					unit.setUnitName(unitBean.getUnitName());
					unit.setCreateDate(CURRENT_TIMESTAMP);
					unit.setCreatedBy(getUserNameLogin());
					unitService.save(unit);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("unit.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteUnit(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteUnit][Type : Controller]");
		logger.info("[UnitId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Unit unit = unitService.getUnitById(Long.valueOf(id));
				if(unit != null){
					unitService.delete(unit);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("unit.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public UnitBean populateEntityToDto(Unit unit){
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		UnitBean unitBean = new UnitBean();
		unitBean.setId(unit.getId());
		unitBean.setUnitName(unit.getUnitName());
		unitBean.setCreateBy(unit.getCreatedBy());
		unitBean.setCreateDate(unit.getCreateDate());
		unitBean.setCreateDateTh(null==unit.getCreateDate()?"":formatDataTh.format(unit.getCreateDate()));
		unitBean.setUpdateBy(unit.getUpdatedBy());
		unitBean.setUpdateDate(unit.getUpdatedDate());
		unitBean.setUpdateDateTh(null==unit.getUpdatedDate()?"":formatDataTh.format(unit.getUpdatedDate()));
		return unitBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
}
