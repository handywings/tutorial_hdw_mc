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
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/productorderserviceproduct")
public class ProductOrderServiceProductController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ProductOrderServiceProductController.class);
	public static final String CONTROLLER_NAME = "productorderserviceproduct/";
	Gson g = new Gson();
	
	//initial service
	@Autowired(required=true)
	@Qualifier(value="stockService")
	private StockService stockService;
	
	@Autowired(required=true)
	@Qualifier(value="serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "unitService")
	private UnitService unitService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initServiceProduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initServiceProduct][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<Stock> stocks = new ArrayList<Stock>();
		List<ServiceProductBean> serviceProductBeans = new ArrayList<ServiceProductBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				stocks = stockService.findAll();
				List<ServiceProduct> serviceProducts = serviceProductService.findAll();
				if(null != serviceProducts && !serviceProducts.isEmpty()){
					for(ServiceProduct serviceProduct:serviceProducts){
						serviceProductBeans.add(populateEntityToDto(serviceProduct));
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
		modelAndView.addObject("stocks", stocks);
		modelAndView.addObject("serviceProducts", serviceProductBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "searchservicesroduct", method = RequestMethod.POST)
	public ModelAndView searchServiceProduct(ServiceProductBean serviceProductBean, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initServiceProduct][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<Stock> stocks = new ArrayList<Stock>();
		List<ServiceProductBean> serviceProductBeans = new ArrayList<ServiceProductBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				stocks = stockService.findAll();
				List<ServiceProduct> serviceProducts = 
						serviceProductService.searchByStockOrCriteria(serviceProductBean.getCriteria(), serviceProductBean.getStock().getId());
				if(null != serviceProducts && !serviceProducts.isEmpty()){
					for(ServiceProduct serviceProduct:serviceProducts){
						serviceProductBeans.add(populateEntityToDto(serviceProduct));
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
		modelAndView.addObject("stocks", stocks);
		modelAndView.addObject("serviceProducts", serviceProductBeans);
		modelAndView.addObject("criteria", serviceProductBean.getCriteria());
		modelAndView.addObject("stockId", serviceProductBean.getStock().getId());
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="update", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse update(@RequestBody final ServiceProductBean serviceProductBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateServicePackageTypeBean][Type : Controller]");
				logger.info("[ServiceProductBean : "+serviceProductBean.getStock().getId()+" ]");
				if(null != serviceProductBean){
					ServiceProduct serviceProduct = serviceProductService.getServiceProductById(Long.valueOf(serviceProductBean.getId()));
					if(null != serviceProduct){
						//update
						serviceProduct.setServiceChargeName(serviceProductBean.getProductName());
						serviceProduct.setPrice(serviceProductBean.getPrice());
						
						Stock stock = stockService.getStockById(serviceProductBean.getStock().getId());
						serviceProduct.setStock(stock);
						
						serviceProduct.setUpdatedDate(CURRENT_TIMESTAMP);
						serviceProduct.setUpdatedBy(getUserNameLogin());
						serviceProductService.update(serviceProduct);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("serviceProduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getData(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		ServiceProductBean serviceProductBean = new ServiceProductBean();
		
		if(isPermission()){
			try{
				ServiceProduct serviceProduct = serviceProductService.getServiceProductById(Long.valueOf(id));
				if(serviceProduct != null){
					serviceProductBean = populateEntityToDto(serviceProduct);
					jsonResponse.setResult(serviceProductBean);
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
	
	@SuppressWarnings("unused")
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse delete(@PathVariable String id, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				ServiceProduct serviceProduct = serviceProductService.getServiceProductById(Long.valueOf(id));
				serviceProduct.setDeleted(Boolean.TRUE);
				
				if(serviceProduct != null){
					serviceProductService.update(serviceProduct);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("serviceProduct.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addServiceProduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : addServiceProduct][Type : Controller]");
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// call service
				// equipmentProductType
				List<EquipmentProductCategoryBean> epcBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcs = equipmentProductCategoryService.findAll();
				for (EquipmentProductCategory epc : epcs) {
					EquipmentProductCategoryBean epcBean = new EquipmentProductCategoryBean();
					epcBean = epcController.populateEntityToDto(epc);
					epcBeans.add(epcBean);
				}
				modelAndView.addObject("epcBeans", epcBeans);

				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);
				
				// dropdown unit
				List<Unit> units = unitService.findAll();
				List<UnitBean> unitBeans = new ArrayList<UnitBean>();
				for (Unit unit : units) {
					UnitBean unitBean = new UnitBean();
					unitBean.setId(unit.getId());
					unitBean.setUnitName(unit.getUnitName());
					unitBeans.add(unitBean);
				}
				modelAndView.addObject("unitBeans", unitBeans);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + ADD);
		return modelAndView;
	}
	
	// save saveServiceCharge
	@RequestMapping(value = "saveServiceCharge", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveServiceCharge(@RequestBody final ServiceProductBean serviceProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveServiceCharge][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (serviceProductBean != null) {
					ServiceProduct serviceProduct = new ServiceProduct();
					// load stock
					Stock stock = stockService.getStockById(serviceProductBean.getStock().getId());
					serviceProduct.setStock(stock);

					// load unit
					Unit unit = unitService.getUnitById(serviceProductBean.getUnit().getId());
					serviceProduct.setUnit(unit);

					// load inter user type
					EquipmentProductCategory epc = equipmentProductCategoryService.getEquipmentProductCategoryByCode(
							messageSource.getMessage("equipmentproductcategory.type.charge", null,
									LocaleContextHolder.getLocale()));

					serviceProduct.setProductCategory(epc);
					serviceProduct.setServiceChargeName(serviceProductBean.getProductName());
					serviceProduct.setPrice(serviceProductBean.getPrice());
					serviceProduct.setDeleted(false);
					serviceProduct.setCreateDate(CURRENT_TIMESTAMP);
					// ---> change when LoginController success
					serviceProduct.setCreatedBy(getUserNameLogin());
					Long id = serviceProductService.save(serviceProduct);
					if (id != null) {
						ServiceProduct ServiceProductNew = serviceProductService.getServiceProductById(id);
						if (ServiceProductNew != null) {
							ServiceProductNew.setProductCode(String.format("%05d", ServiceProductNew.getId()));

							serviceProductService.update(ServiceProductNew);
						}
					} else {
						// input text for message exception
						throw new Exception("");
					}
					jsonResponse.setError(false);

				} else {
					// input text for message exception
					throw new Exception("");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("addproduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// load stock dropdown
	public List<StockBean> loadStockList() {
		List<StockBean> stockBeans = new ArrayList<StockBean>();
		List<Stock> stocks = stockService.findAllOrderCompany();
		for (Stock stock : stocks) {
			StockBean stockBean = new StockBean();
			stockBean.setId(stock.getId());
			stockBean.setStockName(stock.getStockName());
			stockBean.setStockCode(stock.getStockCode());
			stockBean.setStockDetail(stock.getStockDetail());

			CompanyBean companyBean = new CompanyBean();
			companyBean.setId(stock.getCompany().getId());
			companyBean.setCompanyName(stock.getCompany().getCompanyName());
			stockBean.setCompany(companyBean);
			stockBeans.add(stockBean);
		}

		return stockBeans;
	}

	public ServiceProductBean populateEntityToDto(ServiceProduct serviceProduct){
		ServiceProductBean serviceProductBean = new ServiceProductBean();
		if(null != serviceProduct){
			serviceProductBean.setId(serviceProduct.getId());
			serviceProductBean.setProductName(serviceProduct.getServiceChargeName());
			serviceProductBean.setProductCode(serviceProduct.getProductCode());
			serviceProductBean.setPrice(serviceProduct.getPrice());
			serviceProductBean.setType("S");
			
			ProductOverviewController productOverviewController = new ProductOverviewController();
			
			StockBean stockBean = new StockBean();
			Stock stock = serviceProduct.getStock();
			stockBean = productOverviewController.populateEntityToDto(stock);
			
			serviceProductBean.setStock(stockBean);
			
			//product type
			EquipmentProductCategoryBean epc = new EquipmentProductCategoryBean();
			epc.setId(serviceProduct.getEquipmentProductCategory().getId());
			epc.setEquipmentProductCategoryName(serviceProduct.getEquipmentProductCategory().getEquipmentProductCategoryName());
			epc.setEquipmentProductCategoryCode(serviceProduct.getEquipmentProductCategory().getEquipmentProductCategoryCode());
			serviceProductBean.setProductCategory(epc);
			//set unit type
			UnitBean unitBean = new UnitBean();
			unitBean.setId(serviceProduct.getUnit().getId());
			unitBean.setUnitName(serviceProduct.getUnit().getUnitName());
			serviceProductBean.setUnit(unitBean);
		}
		return serviceProductBean;
	}
	
	//getter setter
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setServiceProductService(ServiceProductService serviceProductService) {
		this.serviceProductService = serviceProductService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setEquipmentProductCategoryService(EquipmentProductCategoryService equipmentProductCategoryService) {
		this.equipmentProductCategoryService = equipmentProductCategoryService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	} 
}
