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
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/equipmentproductcategory")
public class EquipmentProductCategoryController extends BaseController{
	
	final static Logger logger = Logger.getLogger(EquipmentProductCategoryController.class);
	public static final String CONTROLLER_NAME = "equipmentproductcategory/";
	Gson g = new Gson();
	
	//initial service
	@Autowired(required=true)
	@Qualifier(value="equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;	
	
	 
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initEquipmentProductCategory(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initEquipmentProductCategory][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<EquipmentProductCategoryBean> equipmentProductCategoryBean = new ArrayList<EquipmentProductCategoryBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<EquipmentProductCategory> equipmentProductCategories = equipmentProductCategoryService.findAll();
				if(null != equipmentProductCategories && !equipmentProductCategories.isEmpty()){
					for(EquipmentProductCategory equipmentProductCategory:equipmentProductCategories){
						EquipmentProductCategoryBean bean = populateEntityToDto(equipmentProductCategory);
						if(null != bean){
							equipmentProductCategoryBean.add(bean);
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
		modelAndView.addObject("equipmentProductCategories", equipmentProductCategoryBean);
		
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveEquipmentProductCategory(@RequestBody final EquipmentProductCategoryBean equipmentProductCategoryBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveEquipmentProductCategory][Type : Controller]");
//				logger.info("[servicePackageTypeBean : "+g.toJson(equipmentProductCategoryBean)+"]");
				if(null != equipmentProductCategoryBean){
					//save
					EquipmentProductCategory equipmentProductCategory = new EquipmentProductCategory();
					equipmentProductCategory.setEquipmentProductCategoryName(equipmentProductCategoryBean.getEquipmentProductCategoryName());
					equipmentProductCategory.setDescription(equipmentProductCategoryBean.getDescription());
					equipmentProductCategory.setEquipmentProductCategoryCode(equipmentProductCategoryService.genEquipmentProductCategoryCode());
					equipmentProductCategory.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductCategory.setCreatedBy(getUserNameLogin());
					equipmentProductCategoryService.save(equipmentProductCategory);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("equipmentproductcategory.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getDataForEdit(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if(isPermission()){
			try{
				EquipmentProductCategory equipmentProductCategory = equipmentProductCategoryService.getEquipmentProductCategoryById(Long.valueOf(id));
				if(equipmentProductCategory != null){
					EquipmentProductCategoryBean equipmentProductCategoryBean = populateEntityToDto(equipmentProductCategory);
					jsonResponse.setError(false);
					jsonResponse.setResult(equipmentProductCategoryBean);
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
	public JsonResponse updateEquipmentProductCategory(@RequestBody final EquipmentProductCategoryBean equipmentProductCategoryBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateEquipmentProductCategory][Type : Controller]");
//				logger.info("[equipmentProductCategoryBean : "+g.toJson(equipmentProductCategoryBean)+"]");
				if(null != equipmentProductCategoryBean){
					EquipmentProductCategory equipmentProductCategory = equipmentProductCategoryService.getEquipmentProductCategoryById(Long.valueOf(equipmentProductCategoryBean.getId()));
					if(null != equipmentProductCategory){
						//update
						equipmentProductCategory.setEquipmentProductCategoryName(equipmentProductCategoryBean.getEquipmentProductCategoryName());
						equipmentProductCategory.setDescription(equipmentProductCategoryBean.getDescription());;
						equipmentProductCategory.setUpdatedDate(CURRENT_TIMESTAMP);
						equipmentProductCategory.setUpdatedBy(getUserNameLogin());
						equipmentProductCategoryService.update(equipmentProductCategory);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("equipmentproductcategory.transaction.save.success", null, LocaleContextHolder.getLocale()));
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
				EquipmentProductCategory equipmentProductCategory = equipmentProductCategoryService.getEquipmentProductCategoryById(Long.valueOf(id));
				if(equipmentProductCategory != null){
					equipmentProductCategoryService.delete(equipmentProductCategory);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("equipmentproductcategory.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	public EquipmentProductCategoryBean populateEntityToDto(EquipmentProductCategory equipmentProductCategory){
		EquipmentProductCategoryBean equipmentProductCategoryBean = new EquipmentProductCategoryBean();
		int countEquipmentProduct = 0;
		if(null != equipmentProductCategory){
			equipmentProductCategoryBean.setId(equipmentProductCategory.getId());
			equipmentProductCategoryBean.setEquipmentProductCategoryCode(equipmentProductCategory.getEquipmentProductCategoryCode());
			equipmentProductCategoryBean.setEquipmentProductCategoryName(equipmentProductCategory.getEquipmentProductCategoryName());
			equipmentProductCategoryBean.setDescription(equipmentProductCategory.getDescription());
			
			List<EquipmentProduct> equipmentProduct = equipmentProductCategory.getEquipmentProducts();
			if(null != equipmentProduct){
				countEquipmentProduct += equipmentProduct.size();
			}
			List<InternetProduct> internetProduct = equipmentProductCategory.getInternetProducts();
			if(null != internetProduct){
				countEquipmentProduct += internetProduct.size();
			}
			List<ServiceProduct> serviceProduct = equipmentProductCategory.getServiceProducts();
			if(null != serviceProduct){
				countEquipmentProduct += serviceProduct.size();
			}
		}
		equipmentProductCategoryBean.setCountEquipmentProduct(countEquipmentProduct);
		return equipmentProductCategoryBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	
}
