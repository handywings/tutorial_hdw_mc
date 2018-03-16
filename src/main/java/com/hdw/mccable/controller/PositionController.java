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

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PositionBean;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Position;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.PositionService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/position")
public class PositionController extends BaseController{
	
	final static Logger logger = Logger.getLogger(PositionController.class);
	public static final String CONTROLLER_NAME = "position/";
	
	//initial service
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required=true)
	@Qualifier(value="positionService")
	private PositionService positionService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initPosition(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initPosition][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<Company> companys = new ArrayList<Company>();
		List<Position> positions = new ArrayList<Position>();
		List<PositionBean> positionBeans = new ArrayList<PositionBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				companys = companyService.findAll();
				positions = positionService.findAll();
				logger.info("positions size : "+positions.size());
				for(Position pos:positions){
					positionBeans.add(populateEntityToDto(pos));
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
		modelAndView.addObject("companys", companys);
		modelAndView.addObject("positions", positionBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getPosition(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		PositionBean positionBeans = new PositionBean();
		
		if(isPermission()){
			try{
				Position position = positionService.getPositionById(Long.valueOf(id));
				if(position != null){
					positionBeans = populateEntityToDto(position);
					jsonResponse.setError(false);
					jsonResponse.setResult(positionBeans);
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
	
	@RequestMapping(value="update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updatePosition(@RequestBody final PositionBean positionBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if(isPermission()){
			try{
				logger.info("[method : updatePosition][Type : Controller]");
				logger.info("[positionBean : "+positionBean.toString()+"]");
				Position position = positionService.getPositionById(Long.valueOf(positionBean.getId()));
				if(position != null){
					Company company = companyService.getCompanyById(Long.valueOf(positionBean.getCompany().getId()));
					if(company != null){
						//update timestamp
						
						//---> change when LoginController success
						position.setPositionName(positionBean.getPositionName());
						position.setDesctiption(positionBean.getDesctiption());
						position.setCreatedBy(getUserNameLogin());
						position.setCreateDate(CURRENT_TIMESTAMP);
						position.setCompany(company);
		
						positionService.update(position);
						jsonResponse.setError(false);
						jsonResponse.setResult(populateEntityToDto(position));
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("position.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse savePosition(@RequestBody final PositionBean positionBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : savePosition][Type : Controller]");
				logger.info("[positionBean : "+positionBean.toString()+"]");
				//save
				Position position = new Position();
				Company company = companyService.getCompanyById(Long.valueOf(positionBean.getCompany().getId()));
				if(company != null){
					//---> change when LoginController success
					position.setPositionName(positionBean.getPositionName());
					position.setDesctiption(positionBean.getDesctiption());
					position.setCreatedBy(getUserNameLogin());
					position.setCreateDate(CURRENT_TIMESTAMP);
					position.setCompany(company);
	
					Long positionId = positionService.save(position);
					if(positionId != null){
						jsonResponse.setError(false);
					}
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("position.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deletePosition(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deletePosition][Type : Controller]");
		logger.info("[positionId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Position position = positionService.getPositionById(Long.valueOf(id));
				if(position != null){
					positionService.delete(position);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("position.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public PositionBean populateEntityToDto(Position position){
		PositionBean positionBean = new PositionBean();
		
		positionBean.setId(position.getId());
		positionBean.setPositionName(position.getPositionName());
		positionBean.setDesctiption(position.getDesctiption());
		//set company
		CompanyBean companyBean = new CompanyBean();
		companyBean.setId(position.getCompany().getId());
		companyBean.setCompanyName(position.getCompany().getCompanyName());
		positionBean.setCompany(companyBean);
		
		return positionBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	//getter or setter dependency
	public void setCompanyService(CompanyService sv){
		this.companyService = sv;
	}
	
}
