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
import com.hdw.mccable.dto.FunctionBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PermissionGroupBean;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.entity.Function;
import com.hdw.mccable.entity.PermissionGroup;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.service.FunctionService;
import com.hdw.mccable.service.PermissionGroupService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/permissiongroup")
public class PermissionGroupController extends BaseController{
	
	final static Logger logger = Logger.getLogger(PermissionGroupController.class);
	public static final String CONTROLLER_NAME = "permissiongroup/";
	Gson g = new Gson();
	
	//initial service
	
	@Autowired(required=true)
	@Qualifier(value="permissionGroupService")
	private PermissionGroupService permissionGroupService;
	
	@Autowired(required=true)
	@Qualifier(value="functionService")
	private FunctionService functionService;
	
	@Autowired(required=true)
	@Qualifier(value="personnelService")
	private PersonnelService personnelService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initPermissionGroup(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initPermissionGroup][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<PermissionGroupBean> permissionGroupBeans = new ArrayList<PermissionGroupBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<PermissionGroup> permissionGroups = permissionGroupService.findByPermissionNotTypeDefault();
				if(null != permissionGroups && permissionGroups.size() > 0){
					for(PermissionGroup permissionGroup:permissionGroups){
						PermissionGroupBean permissionGroupBean = populateEntityToDto(permissionGroup);
						permissionGroupBeans.add(permissionGroupBean);
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
		modelAndView.addObject("permissionGroups", permissionGroupBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "/formAddPermissionGroup", method = RequestMethod.GET)
	public ModelAndView formAddPermissionGroup(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : formAddPermissionGroup][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		PermissionGroup permissionGroups = new PermissionGroup();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				permissionGroups = permissionGroupService.findByPermissionTypeDefault();
//				logger.info("permissionGroups "+g.toJson(permissionGroups));

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
		modelAndView.addObject("permissionGroups", permissionGroups);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+ADD);
		return modelAndView;
	}
	
	@RequestMapping(value="view/{id}", method = RequestMethod.GET)
	public ModelAndView viewPermissionGroup(@PathVariable Long id, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : viewPermissionGroup][Type : Controller]");
		PermissionGroupBean permissionGroupBeans = new PermissionGroupBean();
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
				PermissionGroup permissionGroup = permissionGroupService.getPermissionGroupById(id);
				if(permissionGroup != null){
					permissionGroupBeans = populateEntityToDto(permissionGroup);
//					logger.info("permissionGroupBeans : "+g.toJson(permissionGroupBeans));
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
		modelAndView.addObject("permissionGroups", permissionGroupBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+VIEW);
		return modelAndView;
	}
	
	@RequestMapping(value="edit/{id}", method = RequestMethod.GET)
	public ModelAndView editPermissionGroup(@PathVariable Long id, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : viewPermissionGroup][Type : Controller]");
		PermissionGroupBean permissionGroupBeans = new PermissionGroupBean();
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
				PermissionGroup permissionGroup = permissionGroupService.getPermissionGroupById(id);
				if(permissionGroup != null){
					permissionGroupBeans = populateEntityToDto(permissionGroup);
//					logger.info("permissionGroupBeans : "+g.toJson(permissionGroupBeans));
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
		modelAndView.addObject("permissionGroups", permissionGroupBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+EDIT);
		return modelAndView;
	}
	
	@RequestMapping(value="update", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updatePermissionGroup(@RequestBody final PermissionGroupBean permissionGroupBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updatePermissionGroup][Type : Controller]");
//				logger.info("[positionBean : "+g.toJson(permissionGroupBean)+"]");
				if(null != permissionGroupBean){
					//update PermissionGroup
					PermissionGroup permissionGroup = new PermissionGroup();
					permissionGroup = populateDtoToEntity(permissionGroupBean);
					permissionGroup.setUpdatedDate(CURRENT_TIMESTAMP);
					permissionGroup.setUpdatedBy(getUserNameLogin());
					permissionGroupService.update(permissionGroup);
					
					//update Function
					List<Function> functions = permissionGroup.getFunctions();
					if(null != functions && 0 < functions.size()){
						for(Function function:functions){
							function.setUpdatedDate(CURRENT_TIMESTAMP);
							function.setUpdatedBy(getUserNameLogin());
							functionService.update(function);
						}
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("permissiongroup.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse savePermissionGroup(@RequestBody final PermissionGroupBean permissionGroupBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : savePermissionGroup][Type : Controller]");
//				logger.info("[positionBean : "+g.toJson(permissionGroupBean)+"]");
				if(null != permissionGroupBean){
					//save PermissionGroup
					PermissionGroup permissionGroup = new PermissionGroup();
					permissionGroup.setPermissionGroupName(permissionGroupBean.getPermissionGroupName());
					permissionGroup.setPermissionGroupDesc(permissionGroupBean.getPermissionGroupDesc());
					permissionGroup.setStatus(true);
					permissionGroup.setCreateDate(CURRENT_TIMESTAMP);
					permissionGroup.setCreatedBy(getUserNameLogin());
					Long permissionGroupId = permissionGroupService.save(permissionGroup);
					permissionGroup = permissionGroupService.getPermissionGroupById(permissionGroupId);
					
					//save Function
					List<FunctionBean> functionBeans = permissionGroupBean.getFunctions();
					if(null != functionBeans && 0 < functionBeans.size()){
						Long parent = 0L;
						int count = 0;
						boolean flagServicePackage = false;
						for(FunctionBean functionBean:functionBeans){
							Function function = new Function();
							function.setFunctionName(functionBean.getFunctionName());
							function.setPermissionGroup(permissionGroup);
							function.setView(functionBean.isView());
							function.setAdd(functionBean.isAdd());
							function.setEdit(functionBean.isEdit());
							function.setDelete(functionBean.isDelete());
							function.setCheck(functionBean.isCheck());
							function.setCreateDate(CURRENT_TIMESTAMP);
							function.setCreatedBy(getUserNameLogin());
							//set key
							count++;
							if(count == 15){ //แทรกเมนู service package
								count = 46;
							}
							if(flagServicePackage){
								function.setKeyName("M"+(count-1));
							}else{
								function.setKeyName("M"+count);
							}
							
							if(count == 46){
								count = 15;
								flagServicePackage = true;
							}
							
							if(functionBean.getParent()==0){
								function.setParent(0L);
								parent = functionService.save(function);
							}else{
								function.setParent(parent);
								functionService.save(function);
							}
						}
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("permissiongroup.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deletePermissionGroup(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deletePermissionGroup][Type : Controller]");
		logger.info("[PermissionGroupId : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				PermissionGroup permissionGroup = permissionGroupService.getPermissionGroupById(Long.valueOf(id));
				if(permissionGroup != null){
					List<Personnel> personnels = permissionGroup.getPersonnels();
					if(null != personnels && personnels.size() > 0){
						PermissionGroup permissionGroupsDefault = permissionGroupService.findByPermissionTypeDefault();
						if(null != permissionGroupsDefault){
							for(Personnel personnel:personnels){
								personnel.setPermissionGroup(permissionGroupsDefault);
								personnelService.update(personnel);
							}
						}
					}
					
					permissionGroupService.delete(permissionGroup);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("permissiongroup.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public PermissionGroupBean populateEntityToDto(PermissionGroup permissionGroup){
		PermissionGroupBean permissionGroupBean = new PermissionGroupBean();
		List<FunctionBean> functionBeans = new ArrayList<FunctionBean>();
		List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
		PersonnelController personnelController = new PersonnelController();
		
		if(null != permissionGroup){
			if(permissionGroup.getFunctions().size() > 0){
				for(Function function:permissionGroup.getFunctions()){
					FunctionBean functionBean = new FunctionBean();
					functionBean.setId(function.getId());
					functionBean.setFunctionName(function.getFunctionName());
					functionBean.setView(function.isView());
					functionBean.setAdd(function.isAdd());
					functionBean.setEdit(function.isEdit());
					functionBean.setDelete(function.isDelete());
					functionBean.setCheck(function.isCheck());
					functionBean.setParent(function.getParent());
					functionBeans.add(functionBean);
				}
			}
			
			if(permissionGroup.getPersonnels().size() > 0){
				for(Personnel personnel:permissionGroup.getPersonnels()){
					PersonnelBean personnelBean = personnelController.populateEntityToDto(personnel);
					
					if(personnelBean.getId() != 1) {
						personnelBeans.add(personnelBean);
					}					
				}
			}
			permissionGroupBean.setId(permissionGroup.getId());
			permissionGroupBean.setPermissionGroupName(permissionGroup.getPermissionGroupName());
			permissionGroupBean.setPermissionGroupDesc(permissionGroup.getPermissionGroupDesc());
			permissionGroupBean.setStatus(permissionGroup.isStatus());
			permissionGroupBean.setFunctions(functionBeans);
			permissionGroupBean.setPersonnels(personnelBeans);
			permissionGroupBean.setPermissionType(permissionGroup.getPermissionType());
			
		}
		
		return permissionGroupBean;
	}
	
	public PermissionGroup populateDtoToEntity(PermissionGroupBean permissionGroupBean){
		List<Function> functions = new ArrayList<Function>();
		
		PermissionGroup permissionGroup = permissionGroupService.getPermissionGroupById(permissionGroupBean.getId());
		
		if(null != permissionGroupBean && null != permissionGroup){
			if(permissionGroupBean.getFunctions().size() > 0){
				for(FunctionBean functionBean:permissionGroupBean.getFunctions()){
					Function function = functionService.getFunctionById(functionBean.getId());
					if(null != function){
						function.setId(functionBean.getId());
	//					function.setFunctionName(functionBean.getFunctionName());
						function.setView(functionBean.isView());
						function.setAdd(functionBean.isAdd());
						function.setEdit(functionBean.isEdit());
						function.setDelete(functionBean.isDelete());
						function.setCheck(functionBean.isCheck());
						function.setParent(functionBean.getParent());
						functions.add(function);
					}
				}
			}

			permissionGroup.setId(permissionGroupBean.getId());
			permissionGroup.setPermissionGroupName(permissionGroupBean.getPermissionGroupName());
			permissionGroup.setPermissionGroupDesc(permissionGroupBean.getPermissionGroupDesc());
			permissionGroup.setStatus(permissionGroupBean.isStatus());
			permissionGroup.setFunctions(functions);
			
		}
		
		return permissionGroup;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	
}
