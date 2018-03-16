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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.SearchAllProductBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageSearchBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TemplateServiceBean;
import com.hdw.mccable.dto.TemplateServiceItemBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.TemplateService;
import com.hdw.mccable.entity.TemplateServiceItem;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.ServicePackageService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/servicepackage")
public class ServicePackageController extends BaseController {

	final static Logger logger = Logger.getLogger(ServicePackageController.class);
	public static final String CONTROLLER_NAME = "servicepackage/";
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	// initial service

	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;

	@Autowired(required = true)
	@Qualifier(value = "servicePackageService")
	private ServicePackageService servicePackageService;

	@Autowired(required = true)
	@Qualifier(value = "companyService")
	private CompanyService companyService;

	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;

	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;

	@Autowired(required = true)
	@Qualifier(value = "serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;

	@Autowired
	MessageSource messageSource;

	// end initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();

				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}

				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);

				// company
				CompanyController companyController = new CompanyController();
				companyController.setMessageSource(messageSource);
				List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
				List<Company> companys = companyService.findAll();
				for (Company company : companys) {
					CompanyBean companyBean = companyController.populateEntityToDto(company);
					companyBeans.add(companyBean);
				}
				modelAndView.addObject("companyBeans", companyBeans);

				// search service package
				ServicePackageSearchBean servicePackageSearchBean = (ServicePackageSearchBean) session
						.getAttribute("ServicePackageSearchBean");
				// set value search service package
				if (servicePackageSearchBean != null) {
					modelAndView.addObject("servicePackageSearchBean", servicePackageSearchBean);
				} else {
					modelAndView.addObject("servicePackageSearchBean", new ServicePackageSearchBean());
				}

				// call search process
				List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
				if (servicePackageSearchBean != null) {
					servicePackageBeans = this.searchServicePackage(servicePackageSearchBean);
				} else {
					servicePackageBeans = this.searchServicePackage(new ServicePackageSearchBean());
				}
				modelAndView.addObject("servicePackageBeans", servicePackageBeans);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		session.removeAttribute("ServicePackageSearchBean");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchServicePackage(
			@ModelAttribute("servicePackageSearchBean") ServicePackageSearchBean servicePackageSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchServicePackage][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(servicePackageSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/servicepackage");
		return modelAndView;
	}

	// view service package
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView view(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : view][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			ServicePackage servicePackage = servicePackageService.getServicePackageById(id);
			if (servicePackage != null) {
				ServicePackageBean servicePackageBean = populateEntityToDto(servicePackage);
				modelAndView.addObject("servicePackageBean", servicePackageBean);
			} else {
				// or redirect page 404 not found
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + VIEW);
		return modelAndView;
	}

	// add service package
	@RequestMapping(value = "add", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : add][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();

		CompanyController companyController = new CompanyController();
		companyController.setMessageSource(messageSource);

		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			// load product all type
			List<ProductBean> productBeans = searchAllProduct(new SearchAllProductBean());
			modelAndView.addObject("productBeans", productBeans);

			// load company
			List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
			List<Company> companys = companyService.findAll();
			for (Company company : companys) {
				CompanyBean companyBean = companyController.populateEntityToDto(company);
				companyBeans.add(companyBean);
			}
			modelAndView.addObject("companyBeans", companyBeans);

			// service type
			List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
			List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
			for (ServicePackageType servicePackageType : servicePackageTypes) {
				ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
						.populateEntityToDto(servicePackageType);
				if (null != servicePackageTypeBean) {
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
			}
			modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);

			// equipmentProductType
			EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
			List<EquipmentProductCategoryBean> epcBeans = new ArrayList<EquipmentProductCategoryBean>();
			List<EquipmentProductCategory> epcs = equipmentProductCategoryService.findAll();
			for (EquipmentProductCategory epc : epcs) {
				EquipmentProductCategoryBean epcBean = new EquipmentProductCategoryBean();
				epcBean = epcController.populateEntityToDto(epc);
				epcBeans.add(epcBean);
			}
			modelAndView.addObject("epcBeans", epcBeans);

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(CONTROLLER_NAME + ADD);
		return modelAndView;
	}

	// load product all type
	List<ProductBean> searchAllProduct(SearchAllProductBean searchAllProductBean) {
		String key = "";
		String productTypeCode = "";
		List<ProductBean> productBeans = new ArrayList<ProductBean>();
		// key
		if (searchAllProductBean.getKey() != null && (!searchAllProductBean.getKey().isEmpty())) {
			key = searchAllProductBean.getKey();
		}
		// product type
		if (searchAllProductBean.getProductTypeCode() != null
				&& (!searchAllProductBean.getProductTypeCode().isEmpty())) {
			productTypeCode = searchAllProductBean.getProductTypeCode();
		}
		if (productTypeCode.isEmpty()) {
			// load all
			// equipment
			List<EquipmentProduct> equipmentProducts = productService.searchByTypeEquipment(key, productTypeCode, 0L);
			for (EquipmentProduct equipmentProduct : equipmentProducts) {
				EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
				equipmentProductBean.setId(equipmentProduct.getId());
				equipmentProductBean.setProductCode(equipmentProduct.getProductCode());
				equipmentProductBean.setProductName(equipmentProduct.getProductName());
				//stock
				StockBean stockBean = new StockBean();
				stockBean.setId(equipmentProduct.getStock().getId());
				stockBean.setStockName(equipmentProduct.getStock().getStockName());
				equipmentProductBean.setStock(stockBean);
				// product type
				EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
				equpmentProductCategory.setId(equipmentProduct.getEquipmentProductCategory().getId());
				equpmentProductCategory.setEquipmentProductCategoryName(
						equipmentProduct.getEquipmentProductCategory().getEquipmentProductCategoryName());
				equpmentProductCategory.setEquipmentProductCategoryCode(
						equipmentProduct.getEquipmentProductCategory().getEquipmentProductCategoryCode());
				equipmentProductBean.setProductCategory(equpmentProductCategory);
				equipmentProductBean.setTypeEquipment();
				if(equipmentProduct.getBalance() > 0){
					productBeans.add(equipmentProductBean);
				}
			}
			// internet
			List<InternetProduct> internetProducts = internetProductService.searchByStockOrCriteria(key, 0L);
			for (InternetProduct internetProduct : internetProducts) {
				InternetProductBean internetProductBean = new InternetProductBean();
				internetProductBean.setId(internetProduct.getId());
				internetProductBean.setProductCode(internetProduct.getProductCode());
				internetProductBean.setProductName(internetProduct.getProductName());
				//stock
				StockBean stockBean = new StockBean();
				stockBean.setId(internetProduct.getStock().getId());
				stockBean.setStockName(internetProduct.getStock().getStockName());
				internetProductBean.setStock(stockBean);
				// product type
				EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
				equpmentProductCategory.setId(internetProduct.getProductCategory().getId());
				equpmentProductCategory.setEquipmentProductCategoryName(
						internetProduct.getProductCategory().getEquipmentProductCategoryName());
				equpmentProductCategory.setEquipmentProductCategoryCode(
						internetProduct.getProductCategory().getEquipmentProductCategoryCode());
				internetProductBean.setProductCategory(equpmentProductCategory);
				internetProductBean.setTypeInternet();
				internetProductBean.setMessageSource(messageSource);
				internetProductBean.unitTypeInternet();
				productBeans.add(internetProductBean);
			}

			// service charge
			List<ServiceProduct> serviceProducts = serviceProductService.searchByStockOrCriteria(key, 0L);
			for (ServiceProduct serviceProduct : serviceProducts) {
				ServiceProductBean serviceProductBean = new ServiceProductBean();
				serviceProductBean.setId(serviceProduct.getId());
				serviceProductBean.setProductName(serviceProduct.getServiceChargeName());
				serviceProductBean.setProductCode(serviceProduct.getProductCode());
				// product type
				EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
				equpmentProductCategory.setId(serviceProduct.getProductCategory().getId());
				equpmentProductCategory.setEquipmentProductCategoryName(
						serviceProduct.getProductCategory().getEquipmentProductCategoryName());
				equpmentProductCategory.setEquipmentProductCategoryCode(
						serviceProduct.getProductCategory().getEquipmentProductCategoryCode());
				serviceProductBean.setProductCategory(equpmentProductCategory);
				serviceProductBean.setTypeService();
				serviceProductBean.setMessageSource(messageSource);
				//set unit type
				UnitBean unitBean = new UnitBean();
				unitBean.setId(serviceProduct.getUnit().getId());
				unitBean.setUnitName(serviceProduct.getUnit().getUnitName());
				serviceProductBean.setUnit(unitBean);
				
				//stock
				StockBean stockBean = new StockBean();
				stockBean.setId(serviceProduct.getStock().getId());
				stockBean.setStockName(serviceProduct.getStock().getStockName());
				serviceProductBean.setStock(stockBean);
				
				productBeans.add(serviceProductBean);
			}

		} else {
			// load seperate
			if(productTypeCode.equals(messageSource.getMessage("equipmentproductcategory.type.internet", null, LocaleContextHolder.getLocale()))){
				// internet
				List<InternetProduct> internetProducts = internetProductService.searchByStockOrCriteria(key, 0L);
				for (InternetProduct internetProduct : internetProducts) {
					InternetProductBean internetProductBean = new InternetProductBean();
					internetProductBean.setId(internetProduct.getId());
					internetProductBean.setProductCode(internetProduct.getProductCode());
					internetProductBean.setProductName(internetProduct.getProductName());
					//stock
					StockBean stockBean = new StockBean();
					stockBean.setId(internetProduct.getStock().getId());
					stockBean.setStockName(internetProduct.getStock().getStockName());
					internetProductBean.setStock(stockBean);
					// product type
					EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
					equpmentProductCategory.setId(internetProduct.getProductCategory().getId());
					equpmentProductCategory.setEquipmentProductCategoryName(
							internetProduct.getProductCategory().getEquipmentProductCategoryName());
					equpmentProductCategory.setEquipmentProductCategoryCode(
							internetProduct.getProductCategory().getEquipmentProductCategoryCode());
					internetProductBean.setProductCategory(equpmentProductCategory);
					internetProductBean.setTypeInternet();
					internetProductBean.setMessageSource(messageSource);
					internetProductBean.unitTypeInternet();
					productBeans.add(internetProductBean);
				}
			}else if(productTypeCode.equals(messageSource.getMessage("equipmentproductcategory.type.charge", null, LocaleContextHolder.getLocale()))){
				// service charge
				List<ServiceProduct> serviceProducts = serviceProductService.searchByStockOrCriteria(key, 0L);
				for (ServiceProduct serviceProduct : serviceProducts) {
					ServiceProductBean serviceProductBean = new ServiceProductBean();
					serviceProductBean.setId(serviceProduct.getId());
					serviceProductBean.setProductName(serviceProduct.getServiceChargeName());
					serviceProductBean.setProductCode(serviceProduct.getProductCode());
					// product type
					EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
					equpmentProductCategory.setId(serviceProduct.getProductCategory().getId());
					equpmentProductCategory.setEquipmentProductCategoryName(
							serviceProduct.getProductCategory().getEquipmentProductCategoryName());
					equpmentProductCategory.setEquipmentProductCategoryCode(
							serviceProduct.getProductCategory().getEquipmentProductCategoryCode());
					serviceProductBean.setProductCategory(equpmentProductCategory);
					serviceProductBean.setTypeService();
					serviceProductBean.setMessageSource(messageSource);
					//set unit type
					UnitBean unitBean = new UnitBean();
					unitBean.setId(serviceProduct.getUnit().getId());
					unitBean.setUnitName(serviceProduct.getUnit().getUnitName());
					serviceProductBean.setUnit(unitBean);
					//stock
					StockBean stockBean = new StockBean();
					stockBean.setId(serviceProduct.getStock().getId());
					stockBean.setStockName(serviceProduct.getStock().getStockName());
					serviceProductBean.setStock(stockBean);
					productBeans.add(serviceProductBean);
				}
			}else{
				// equipment
				List<EquipmentProduct> equipmentProducts = productService.searchByTypeEquipment(key, productTypeCode, 0L);
				for (EquipmentProduct equipmentProduct : equipmentProducts) {
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					equipmentProductBean.setId(equipmentProduct.getId());
					equipmentProductBean.setProductCode(equipmentProduct.getProductCode());
					equipmentProductBean.setProductName(equipmentProduct.getProductName());
					//stock
					StockBean stockBean = new StockBean();
					stockBean.setId(equipmentProduct.getStock().getId());
					stockBean.setStockName(equipmentProduct.getStock().getStockName());
					equipmentProductBean.setStock(stockBean);
					
					// product type
					EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
					equpmentProductCategory.setId(equipmentProduct.getEquipmentProductCategory().getId());
					equpmentProductCategory.setEquipmentProductCategoryName(
							equipmentProduct.getEquipmentProductCategory().getEquipmentProductCategoryName());
					equpmentProductCategory.setEquipmentProductCategoryCode(
							equipmentProduct.getEquipmentProductCategory().getEquipmentProductCategoryCode());
					equipmentProductBean.setProductCategory(equpmentProductCategory);
					equipmentProductBean.setTypeEquipment();
					if(equipmentProduct.getBalance() > 0){
						productBeans.add(equipmentProductBean);
					}
				}
			}

		}

		return productBeans;
	}
	

	List<ProductBean> searchProductService(SearchAllProductBean searchAllProductBean) {
		String key = "";
		String productTypeCode = "";
		List<ProductBean> productBeans = new ArrayList<ProductBean>();
		// key
		if (searchAllProductBean.getKey() != null && (!searchAllProductBean.getKey().isEmpty())) {
			key = searchAllProductBean.getKey();
		}
		// product type
		if (searchAllProductBean.getProductTypeCode() != null
				&& (!searchAllProductBean.getProductTypeCode().isEmpty())) {
			productTypeCode = searchAllProductBean.getProductTypeCode();
		}
		if (productTypeCode.isEmpty()) {

			// service charge
			List<ServiceProduct> serviceProducts = serviceProductService.searchByStockOrCriteria(key, 0L);
			for (ServiceProduct serviceProduct : serviceProducts) {
				ServiceProductBean serviceProductBean = new ServiceProductBean();
				serviceProductBean.setId(serviceProduct.getId());
				serviceProductBean.setProductName(serviceProduct.getServiceChargeName());
				serviceProductBean.setProductCode(serviceProduct.getProductCode());
				serviceProductBean.setPrice(serviceProduct.getPrice());
				// product type
				EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
				equpmentProductCategory.setId(serviceProduct.getProductCategory().getId());
				equpmentProductCategory.setEquipmentProductCategoryName(
						serviceProduct.getProductCategory().getEquipmentProductCategoryName());
				equpmentProductCategory.setEquipmentProductCategoryCode(
						serviceProduct.getProductCategory().getEquipmentProductCategoryCode());
				serviceProductBean.setProductCategory(equpmentProductCategory);
				serviceProductBean.setTypeService();
				serviceProductBean.setMessageSource(messageSource);
				//set unit type
				UnitBean unitBean = new UnitBean();
				unitBean.setId(serviceProduct.getUnit().getId());
				unitBean.setUnitName(serviceProduct.getUnit().getUnitName());
				serviceProductBean.setUnit(unitBean);
				
				//stock
				StockBean stockBean = new StockBean();
				stockBean.setId(serviceProduct.getStock().getId());
				stockBean.setStockName(serviceProduct.getStock().getStockName());
				serviceProductBean.setStock(stockBean);
				
				productBeans.add(serviceProductBean);
			}

		} else {
			if(productTypeCode.equals(messageSource.getMessage("equipmentproductcategory.type.charge", null, LocaleContextHolder.getLocale()))){
				// service charge
				List<ServiceProduct> serviceProducts = serviceProductService.searchByStockOrCriteria(key, 0L);
				for (ServiceProduct serviceProduct : serviceProducts) {
					ServiceProductBean serviceProductBean = new ServiceProductBean();
					serviceProductBean.setId(serviceProduct.getId());
					serviceProductBean.setProductName(serviceProduct.getServiceChargeName());
					serviceProductBean.setProductCode(serviceProduct.getProductCode());
					serviceProductBean.setPrice(serviceProduct.getPrice());
					// product type
					EquipmentProductCategoryBean equpmentProductCategory = new EquipmentProductCategoryBean();
					equpmentProductCategory.setId(serviceProduct.getProductCategory().getId());
					equpmentProductCategory.setEquipmentProductCategoryName(
							serviceProduct.getProductCategory().getEquipmentProductCategoryName());
					equpmentProductCategory.setEquipmentProductCategoryCode(
							serviceProduct.getProductCategory().getEquipmentProductCategoryCode());
					serviceProductBean.setProductCategory(equpmentProductCategory);
					serviceProductBean.setTypeService();
					serviceProductBean.setMessageSource(messageSource);
					//set unit type
					UnitBean unitBean = new UnitBean();
					unitBean.setId(serviceProduct.getUnit().getId());
					unitBean.setUnitName(serviceProduct.getUnit().getUnitName());
					serviceProductBean.setUnit(unitBean);
					//stock
					StockBean stockBean = new StockBean();
					stockBean.setId(serviceProduct.getStock().getId());
					stockBean.setStockName(serviceProduct.getStock().getStockName());
					serviceProductBean.setStock(stockBean);
					productBeans.add(serviceProductBean);
				}
			}
		}

		return productBeans;
	}

	// loadEquipmentProduct
	@RequestMapping(value = "loadEquipmentProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductWithId][Type : Controller]");
		ProductAddController productAddController = new ProductAddController();
		productAddController.setMessageSource(messageSource);

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				EquipmentProduct equipmentProduct = productService.findEquipmentProductById(Long.valueOf(id));
				if (equipmentProduct != null) {
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					equipmentProductBean = productAddController.populateEntityToDto(equipmentProduct);
					equipmentProductBean.setTypeEquipment();
					jsonResponse.setError(false);
					jsonResponse.setResult(equipmentProductBean);
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
		return jsonResponse;
	}

	// loadInternetProduct
	@RequestMapping(value = "loadInternetProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInternetProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadInternetProductWithId][Type : Controller]");
		ProductOrderInternetProductController productOrderInternetProductController = new ProductOrderInternetProductController();
		productOrderInternetProductController.setMessageSource(messageSource);

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProduct internetProduct = internetProductService.getInternetProductById(Long.valueOf(id));
				if (internetProduct != null) {
					InternetProductBean internetProductBean = productOrderInternetProductController
							.populateEntityToDto(internetProduct);
					internetProductBean.setTypeInternet();
					jsonResponse.setError(false);
					jsonResponse.setResult(internetProductBean);
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
		return jsonResponse;
	}

	// loadServiceProduct
	@RequestMapping(value = "loadServiceProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadServiceProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadServiceProductWithId][Type : Controller]");
		ProductOrderServiceProductController productOrderServiceProductController = new ProductOrderServiceProductController();
		productOrderServiceProductController.setMessageSource(messageSource);

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ServiceProduct serviceProduct = serviceProductService.getServiceProductById(Long.valueOf(id));
				if (serviceProduct != null) {
					ServiceProductBean serviceProductBean = productOrderServiceProductController
							.populateEntityToDto(serviceProduct);
					serviceProductBean.setTypeService();
					jsonResponse.setError(false);
					jsonResponse.setResult(serviceProductBean);
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
		return jsonResponse;
	}

	// save saveServicePackage
	@RequestMapping(value = "saveServicePackage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveServicePackage(@RequestBody final ServicePackageBean servicePackageBean,
			HttpServletRequest request) {
		logger.info("[method : saveServicePackage][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();

		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				ServicePackage servicePackage = new ServicePackage();
				servicePackage.setPackageCode(servicePackageBean.getPackageCode());
				servicePackage.setPackageName(servicePackageBean.getPackageName());
				servicePackage.setActive(Boolean.TRUE);
				servicePackage.setDeleted(Boolean.FALSE);
				servicePackage.setCreateDate(CURRENT_TIMESTAMP);
				// change when LoginController success ------>
				servicePackage.setCreatedBy(getUserNameLogin());
				servicePackage.setInstallationFee(servicePackageBean.getInstallationFee());
				servicePackage.setDeposit(servicePackageBean.getDeposit());
				servicePackage.setMonthlyService(servicePackageBean.isMonthlyService());

				if (servicePackageBean.isMonthlyService()) {
					servicePackage.setMonthlyServiceFee(servicePackageBean.getMonthlyServiceFee());
					servicePackage.setPerMounth(servicePackageBean.getPerMounth());
					servicePackage.setFirstBillFree(servicePackageBean.getFirstBillFree());
					servicePackage.setFirstBillFreeDisCount(servicePackageBean.getFirstBillFreeDisCount());
				} else {
					servicePackage.setOneServiceFee(servicePackageBean.getOneServiceFee());
				}
				// company
				Company company = companyService.getCompanyById(servicePackageBean.getCompany().getId());
				servicePackage.setCompany(company);
				// package type
				ServicePackageType servicePackageType = servicePackageTypeService
						.getServicePackageTypeById(servicePackageBean.getServiceType().getId());
				servicePackage.setServicePackageType(servicePackageType);

				// template
				TemplateService templateService = new TemplateService();
				templateService.setCreateDate(CURRENT_TIMESTAMP);
				// change when LoginController success ------>
				templateService.setCreatedBy(getUserNameLogin());
				templateService.setDeleted(Boolean.FALSE);
				templateService.setServicePackage(servicePackage);

				// template item
				List<TemplateServiceItem> templateServiceItems = new ArrayList<TemplateServiceItem>();
				for (TemplateServiceItemBean templateServiceItemBean : servicePackageBean.getTemplate()
						.getListTemplateServiceItemBean()) {

					TemplateServiceItem templateServiceItem = new TemplateServiceItem();
					templateServiceItem.setQuantity(templateServiceItemBean.getQuantity());
					templateServiceItem.setProductType(templateServiceItemBean.getType());
					templateServiceItem.setFree(templateServiceItemBean.isFree());
					templateServiceItem.setLend(templateServiceItemBean.isLend());
					templateServiceItem.setDeleted(Boolean.FALSE);
					templateServiceItem.setCreateDate(CURRENT_TIMESTAMP);
					// change when LoginController success ------>
					templateServiceItem.setCreatedBy(getUserNameLogin());
					templateServiceItem.setTemplateService(templateService);

					// case equipment product
					if (templateServiceItemBean.getType().equals(TYPE_EQUIMENT)) {
						EquipmentProduct equipmentProduct = productService
								.findEquipmentProductById(templateServiceItemBean.getId());
						templateServiceItem.setEquipmentProduct(equipmentProduct);

						// case internet product
					} else if (templateServiceItemBean.getType().equals(TYPE_INTERNET_USER)) {
						InternetProduct internetProduct = internetProductService
								.getInternetProductById(templateServiceItemBean.getId());
						templateServiceItem.setInternetProduct(internetProduct);
						// case service product
					} else if (templateServiceItemBean.getType().equals(TYPE_SERVICE)) {
						ServiceProduct serviceProduct = serviceProductService
								.getServiceProductById(templateServiceItemBean.getId());
						templateServiceItem.setServiceProduct(serviceProduct);
					}
					// add to template
					templateServiceItems.add(templateServiceItem);
				}
				templateService.setTemplateServiceItems(templateServiceItems);
				// save service package
				servicePackage.setTemplateService(templateService);
				servicePackageService.save(servicePackage);

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
				messageSource.getMessage("package.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "cancel/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse cancel(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : cancel][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ServicePackage servicePackage = servicePackageService.getServicePackageById(Long.valueOf(id));
				if (servicePackage != null) {
					servicePackage.setActive(Boolean.FALSE);
					servicePackageService.update(servicePackage);
				} else {
					// thows exception
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
				messageSource.getMessage("alert.title.cancel.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("package.transaction.cancel.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "enable/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse enable(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : cancel][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ServicePackage servicePackage = servicePackageService.getServicePackageById(Long.valueOf(id));
				if (servicePackage != null) {
					servicePackage.setActive(Boolean.TRUE);
					servicePackageService.update(servicePackage);
				} else {
					// thows exception
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
				messageSource.getMessage("package.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse delete(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : delete][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ServicePackage servicePackage = servicePackageService.getServicePackageById(Long.valueOf(id));
				if (servicePackage != null) {
					servicePackage.setDeleted(Boolean.TRUE);
					servicePackageService.update(servicePackage);
				} else {
					// thows exception
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
				messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("package.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// load one service package
	@RequestMapping(value = "loadServicePackage/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse loadServicePackage(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		ServicePackageBean servicePackageBean = new ServicePackageBean();

		if (isPermission()) {
			try {
				ServicePackage servicePackage = servicePackageService.getServicePackageById(Long.valueOf(id));
				if (servicePackage != null) {
					servicePackageBean = populateEntityToDto(servicePackage);
					jsonResponse.setError(false);
					jsonResponse.setResult(servicePackageBean);
				} else {
					jsonResponse.setError(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		return jsonResponse;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateServicePackage(@RequestBody final ServicePackageBean servicePackageBean,
			HttpServletRequest request) {
		logger.info("[method : updateServicePackage][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageBean.getId());
				if (servicePackage != null) {
					servicePackage.setPackageName(servicePackageBean.getPackageName());
					servicePackageService.update(servicePackage);
				} else {
					jsonResponse.setError(true);
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
				messageSource.getMessage("package.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// edit service package
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();

		CompanyController companyController = new CompanyController();
		companyController.setMessageSource(messageSource);

		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			ServicePackage servicePackage = servicePackageService.getServicePackageById(id);
			if (servicePackage != null) {
				ServicePackageBean servicePackageBean = populateEntityToDtoV3(servicePackage);
				modelAndView.addObject("servicePackageBean", servicePackageBean);

				// load product all type
				List<ProductBean> productBeans = searchAllProduct(new SearchAllProductBean());
				modelAndView.addObject("productBeans", productBeans);

				// load company
				List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
				List<Company> companys = companyService.findAll();
				for (Company company : companys) {
					CompanyBean companyBean = companyController.populateEntityToDto(company);
					companyBeans.add(companyBean);
				}
				modelAndView.addObject("companyBeans", companyBeans);

				// service type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
							.populateEntityToDto(servicePackageType);
					if (null != servicePackageTypeBean) {
						servicePackageTypeBeans.add(servicePackageTypeBean);
					}
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
				
				// equipmentProductType
				EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
				List<EquipmentProductCategoryBean> epcBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcs = equipmentProductCategoryService.findAll();
				for (EquipmentProductCategory epc : epcs) {
					EquipmentProductCategoryBean epcBean = new EquipmentProductCategoryBean();
					epcBean = epcController.populateEntityToDto(epc);
					epcBeans.add(epcBean);
				}
				modelAndView.addObject("epcBeans", epcBeans);

			} else {
				// or redirect page 404 not found
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + EDIT);
		return modelAndView;
	}

	// save updateServicePackage
	@RequestMapping(value = "updateServicePackage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateServicePackageLevel2(@RequestBody final ServicePackageBean servicePackageBean,
			HttpServletRequest request) {
		logger.info("[method : updateServicePackage][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();

		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {

				// delete template item
				ServicePackage servicePackageTemp = servicePackageService
						.getServicePackageById(servicePackageBean.getId());
				if(null != servicePackageTemp){
					TemplateService ts = servicePackageTemp.getTemplateService();
					if(null != ts){
						servicePackageService.deleteTemplateItem(servicePackageTemp.getTemplateService().getId());
					}
				}
				ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageBean.getId());
				servicePackage.setPackageCode(servicePackageBean.getPackageCode());
				servicePackage.setPackageName(servicePackageBean.getPackageName());
				servicePackage.setInstallationFee(servicePackageBean.getInstallationFee());
				servicePackage.setDeposit(servicePackageBean.getDeposit());
				servicePackage.setMonthlyService(servicePackageBean.isMonthlyService());

				if (servicePackageBean.isMonthlyService()) {
					servicePackage.setMonthlyServiceFee(servicePackageBean.getMonthlyServiceFee());
					servicePackage.setPerMounth(servicePackageBean.getPerMounth());
					servicePackage.setFirstBillFree(servicePackageBean.getFirstBillFree());
					servicePackage.setFirstBillFreeDisCount(servicePackageBean.getFirstBillFreeDisCount());
					// set old value
					servicePackage.setOneServiceFee(0);
				} else {
					servicePackage.setOneServiceFee(servicePackageBean.getOneServiceFee());
					// clear old value
					servicePackage.setMonthlyServiceFee(0);
					servicePackage.setPerMounth(0);
					servicePackage.setFirstBillFree(0);
					servicePackage.setFirstBillFreeDisCount(0);
				}
				// company
				Company company = companyService.getCompanyById(servicePackageBean.getCompany().getId());
				servicePackage.setCompany(company);
				// package type
				ServicePackageType servicePackageType = servicePackageTypeService
						.getServicePackageTypeById(servicePackageBean.getServiceType().getId());
				servicePackage.setServicePackageType(servicePackageType);

				// template
				TemplateService templateService = new TemplateService();
				if(null != servicePackage.getTemplateService()){
				templateService = servicePackageService
						.getTemplateService(servicePackage.getTemplateService().getId());
				}
				
				if(null == templateService){
					templateService = new TemplateService();
					templateService.setCreateDate(CURRENT_TIMESTAMP);
					// change when LoginController success ------>
					templateService.setCreatedBy(getUserNameLogin());
					templateService.setDeleted(Boolean.FALSE);
					templateService.setServicePackage(servicePackage);
				}
				
				
				// template item
				List<TemplateServiceItem> templateServiceItems = new ArrayList<TemplateServiceItem>();
				for (TemplateServiceItemBean templateServiceItemBean : servicePackageBean.getTemplate()
						.getListTemplateServiceItemBean()) {

					TemplateServiceItem templateServiceItem = new TemplateServiceItem();
					templateServiceItem.setQuantity(templateServiceItemBean.getQuantity());
					templateServiceItem.setProductType(templateServiceItemBean.getType());
					templateServiceItem.setFree(templateServiceItemBean.isFree());
					templateServiceItem.setLend(templateServiceItemBean.isLend());
					templateServiceItem.setDeleted(Boolean.FALSE);
					templateServiceItem.setCreateDate(CURRENT_TIMESTAMP);
					// change when LoginController success ------>
					templateServiceItem.setCreatedBy(getUserNameLogin());
					templateServiceItem.setTemplateService(templateService);

					// case equipment product
					if (templateServiceItemBean.getType().equals(TYPE_EQUIMENT)) {
						EquipmentProduct equipmentProduct = productService
								.findEquipmentProductById(templateServiceItemBean.getId());
						templateServiceItem.setEquipmentProduct(equipmentProduct);

						// case internet product
					} else if (templateServiceItemBean.getType().equals(TYPE_INTERNET_USER)) {
						InternetProduct internetProduct = internetProductService
								.getInternetProductById(templateServiceItemBean.getId());
						templateServiceItem.setInternetProduct(internetProduct);
						// case service product
					} else if (templateServiceItemBean.getType().equals(TYPE_SERVICE)) {
						ServiceProduct serviceProduct = serviceProductService
								.getServiceProductById(templateServiceItemBean.getId());
						templateServiceItem.setServiceProduct(serviceProduct);
					}
					// add to template
					templateServiceItems.add(templateServiceItem);
				}

				// update template
				if(null != templateServiceItems && templateServiceItems.size() > 0){
					templateService.setTemplateServiceItems(templateServiceItems);
					servicePackageService.updateTemplate(templateService);
				}
				// update service package
				servicePackageService.update(servicePackage);

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
				messageSource.getMessage("package.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// search product
	@RequestMapping(value = "searchProductAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse searchProductAjax(@RequestBody final SearchAllProductBean searchAllProductBean,
			HttpServletRequest request) {
		logger.info("[method : searchProductAjax][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				logger.info("param searchAllProductBean : " + searchAllProductBean.toString());
				// load product all type
				List<ProductBean> productBeans = searchAllProduct(searchAllProductBean);
				jsonResponse.setResult(productBeans); 
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}
		return jsonResponse;
	}

	// search process
	public List<ServicePackageBean> searchServicePackage(ServicePackageSearchBean servicePackageSearchBean) {
		logger.info("[method : searchServicePackage][Type : Controller]");

		List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
		// parameter for search service
		String key = "";
		String servicePackageTypeCode = "";
		String status = "";
		Long company = 0L;
		// key
		if (servicePackageSearchBean.getKey() != null) {
			key = servicePackageSearchBean.getKey();
		}
		// service type code
		if (servicePackageSearchBean.getServicePackageTypeCode() != null
				&& (!servicePackageSearchBean.getServicePackageTypeCode().isEmpty())) {
			servicePackageTypeCode = servicePackageSearchBean.getServicePackageTypeCode();
		}
		// status
		if (servicePackageSearchBean.getStatus() != null && (!servicePackageSearchBean.getStatus().isEmpty())) {
			status = servicePackageSearchBean.getStatus();
		}
		// company
		if (servicePackageSearchBean.getCompanyId() != null && (servicePackageSearchBean.getCompanyId() > 0L)) {
			company = servicePackageSearchBean.getCompanyId();
		}

		// call service search
		List<ServicePackage> servicePackages = servicePackageService.searchBykey(key, servicePackageTypeCode, status,
				company);
		for (ServicePackage servicePackage : servicePackages) {
			ServicePackageBean servicePackageBean = new ServicePackageBean();
			servicePackageBean = populateEntityToDtoV2(servicePackage);
			servicePackageBeans.add(servicePackageBean);
		}

		return servicePackageBeans;
	}

	// populate bean

	public ServicePackageBean populateEntityToDto(ServicePackage servicePackage) {
		// service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(servicePackage.getId());
		servicePackageBean.setPackageName(servicePackage.getPackageName());
		servicePackageBean.setPackageCode(servicePackage.getPackageCode());
		servicePackageBean.setActive(servicePackage.isActive());
		servicePackageBean.setMonthlyService(servicePackage.isMonthlyService());
		servicePackageBean.setMonthlyServiceFee(servicePackage.getMonthlyServiceFee());
		servicePackageBean.setPerMounth(servicePackage.getPerMounth());
		servicePackageBean.setFirstBillFree(servicePackage.getFirstBillFree());
		servicePackageBean.setFirstBillFreeDisCount(servicePackage.getFirstBillFreeDisCount());
		servicePackageBean.setOneServiceFee(servicePackage.getOneServiceFee());
		servicePackageBean.setDeposit(servicePackage.getDeposit());
		servicePackageBean.setInstallationFee(servicePackage.getInstallationFee());

		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		servicePackageBean.setCreateDateTh(
				null == servicePackage.getCreateDate() ? "" : formatDataTh.format(servicePackage.getCreateDate()));

		// service package type
		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
				.populateEntityToDto(servicePackage.getServicePackageType());
		servicePackageBean.setServiceType(servicePackageTypeBean);

		// company
		CompanyController companyController = new CompanyController();
		companyController.setMessageSource(messageSource);
		CompanyBean companyBean = companyController.populateEntityToDto(servicePackage.getCompany());
		servicePackageBean.setCompany(companyBean);

		List<TemplateServiceItemBean> templateItems = new ArrayList<TemplateServiceItemBean>();
		TemplateServiceBean templateServiceBean = new TemplateServiceBean();
		TemplateService templateService = servicePackage.getTemplateService();
	if(null != templateService){
		// template

		templateServiceBean.setId(servicePackage.getTemplateService().getId());
		// template item

		for (TemplateServiceItem templateServiceItem : servicePackage.getTemplateService().getTemplateServiceItems()) {
			TemplateServiceItemBean templateServiceItemBean = new TemplateServiceItemBean();
			templateServiceItemBean.setId(templateServiceItem.getId());
			templateServiceItemBean.setType(templateServiceItem.getProductType());
			templateServiceItemBean.setQuantity(templateServiceItem.getQuantity());
			templateServiceItemBean.setFree(templateServiceItem.isFree());
			templateServiceItemBean.setLend(templateServiceItem.isLend());

			// case equipment product
			if (templateServiceItem.getProductType().equals(TYPE_EQUIMENT)) {
				ProductAddController productAddController = new ProductAddController();
				productAddController.setMessageSource(messageSource);
				EquipmentProductBean equipmentProductBean = productAddController
						.populateEntityToDto(templateServiceItem.getEquipmentProduct());
				templateServiceItemBean.setProductBean(equipmentProductBean);
				templateServiceItemBean.getProductBean().setTypeEquipment();

				// case internet product
			} else if (templateServiceItem.getProductType().equals(TYPE_INTERNET_USER)) {
				ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
				interProduct.setMessageSource(messageSource);
				InternetProductBean internetProductBean = interProduct
						.populateEntityToDto(templateServiceItem.getInternetProduct());
				templateServiceItemBean.setProductBean(internetProductBean);
				templateServiceItemBean.getProductBean().setTypeInternet();

				// case service product
			} else if (templateServiceItem.getProductType().equals(TYPE_SERVICE)) {
				ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
				serviceProduct.setMessageSource(messageSource);
				ServiceProductBean serviceProductBean = serviceProduct
						.populateEntityToDto(templateServiceItem.getServiceProduct());
				templateServiceItemBean.setProductBean(serviceProductBean);
				templateServiceItemBean.getProductBean().setTypeService();

			}

			templateItems.add(templateServiceItemBean);
			
		}
		
	}

	templateServiceBean.setListTemplateServiceItemBean(templateItems);
	servicePackageBean.setTemplate(templateServiceBean);

		// service application
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		for (ServiceApplication serviceApplication : servicePackage.getServiceApplication()) {
			ServiceApplicationBean ServiceApplicationBean = new ServiceApplicationBean();
			ServiceApplicationBean.setId(serviceApplication.getId());
			// customer
			CustomerBean customerBean = new CustomerBean();
			customerBean.setId(serviceApplication.getCustomer().getId());
			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
			// customer type
			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
			customerTypeBean.setValue(serviceApplication.getCustomer().getCustType());
			customerBean.setCustomerType(customerTypeBean);

			ServiceApplicationBean.setCustomer(customerBean);
			// create date th
			SimpleDateFormat dataCreateTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			ServiceApplicationBean.setStartDate((null == serviceApplication.getStartDate() ? ""
					: dataCreateTh.format(serviceApplication.getStartDate())));

			if (!serviceApplication.isDeleted()) {
				serviceApplicationBeans.add(ServiceApplicationBean);
			}
			// check สถานะด้วย รอ Application service เสร็จ
		}
		servicePackageBean.setServiceApplicationBeans(serviceApplicationBeans);

		return servicePackageBean;
	}
	
	public ServicePackageBean populateEntityToDtoV2(ServicePackage servicePackage) {
		// service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(servicePackage.getId());
		servicePackageBean.setPackageName(servicePackage.getPackageName());
		servicePackageBean.setPackageCode(servicePackage.getPackageCode());
		servicePackageBean.setActive(servicePackage.isActive());
//		servicePackageBean.setMonthlyService(servicePackage.isMonthlyService());
//		servicePackageBean.setMonthlyServiceFee(servicePackage.getMonthlyServiceFee());
//		servicePackageBean.setPerMounth(servicePackage.getPerMounth());
//		servicePackageBean.setFirstBillFree(servicePackage.getFirstBillFree());
//		servicePackageBean.setFirstBillFreeDisCount(servicePackage.getFirstBillFreeDisCount());
//		servicePackageBean.setOneServiceFee(servicePackage.getOneServiceFee());
//		servicePackageBean.setDeposit(servicePackage.getDeposit());
//		servicePackageBean.setInstallationFee(servicePackage.getInstallationFee());
//
//		SimpleDateFormat formatDataTh = new SimpleDateFormat(
//				messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
//				new Locale("TH", "th"));
//		servicePackageBean.setCreateDateTh(
//				null == servicePackage.getCreateDate() ? "" : formatDataTh.format(servicePackage.getCreateDate()));
//
		// service package type
		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
				.populateEntityToDto(servicePackage.getServicePackageType());
		servicePackageBean.setServiceType(servicePackageTypeBean);

//		// company
		CompanyController companyController = new CompanyController();
		companyController.setMessageSource(messageSource);
		CompanyBean companyBean = companyController.populateEntityToDto(servicePackage.getCompany());
		servicePackageBean.setCompany(companyBean);
//
//		// template
//		TemplateServiceBean templateServiceBean = new TemplateServiceBean();
//		templateServiceBean.setId(servicePackage.getTemplateService().getId());
//		// template item
//		List<TemplateServiceItemBean> templateItems = new ArrayList<TemplateServiceItemBean>();
//
//		for (TemplateServiceItem templateServiceItem : servicePackage.getTemplateService().getTemplateServiceItems()) {
//			TemplateServiceItemBean templateServiceItemBean = new TemplateServiceItemBean();
//			templateServiceItemBean.setId(templateServiceItem.getId());
//			templateServiceItemBean.setType(templateServiceItem.getProductType());
//			templateServiceItemBean.setQuantity(templateServiceItem.getQuantity());
//			templateServiceItemBean.setFree(templateServiceItem.isFree());
//			templateServiceItemBean.setLend(templateServiceItem.isLend());
//
//			// case equipment product
//			if (templateServiceItem.getProductType().equals(TYPE_EQUIMENT)) {
//				ProductAddController productAddController = new ProductAddController();
//				productAddController.setMessageSource(messageSource);
//				EquipmentProductBean equipmentProductBean = productAddController
//						.populateEntityToDto(templateServiceItem.getEquipmentProduct());
//				templateServiceItemBean.setProductBean(equipmentProductBean);
//				templateServiceItemBean.getProductBean().setTypeEquipment();
//
//				// case internet product
//			} else if (templateServiceItem.getProductType().equals(TYPE_INTERNET_USER)) {
//				ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
//				interProduct.setMessageSource(messageSource);
//				InternetProductBean internetProductBean = interProduct
//						.populateEntityToDto(templateServiceItem.getInternetProduct());
//				templateServiceItemBean.setProductBean(internetProductBean);
//				templateServiceItemBean.getProductBean().setTypeInternet();
//
//				// case service product
//			} else if (templateServiceItem.getProductType().equals(TYPE_SERVICE)) {
//				ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
//				serviceProduct.setMessageSource(messageSource);
//				ServiceProductBean serviceProductBean = serviceProduct
//						.populateEntityToDto(templateServiceItem.getServiceProduct());
//				templateServiceItemBean.setProductBean(serviceProductBean);
//				templateServiceItemBean.getProductBean().setTypeService();
//
//			}
//
//			templateItems.add(templateServiceItemBean);
//
//		}
//		templateServiceBean.setListTemplateServiceItemBean(templateItems);
//		servicePackageBean.setTemplate(templateServiceBean);
//
//		// service application
//		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
//		for (ServiceApplication serviceApplication : servicePackage.getServiceApplication()) {
//			ServiceApplicationBean ServiceApplicationBean = new ServiceApplicationBean();
//			ServiceApplicationBean.setId(serviceApplication.getId());
//			// customer
//			CustomerBean customerBean = new CustomerBean();
//			customerBean.setId(serviceApplication.getCustomer().getId());
//			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
//			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
//			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
//			// customer type
//			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
//			customerTypeBean.setValue(serviceApplication.getCustomer().getCustType());
//			customerBean.setCustomerType(customerTypeBean);
//
//			ServiceApplicationBean.setCustomer(customerBean);
//			// create date th
//			SimpleDateFormat dataCreateTh = new SimpleDateFormat(
//					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
//					new Locale("TH", "th"));
//			ServiceApplicationBean.setStartDate((null == serviceApplication.getStartDate() ? ""
//					: dataCreateTh.format(serviceApplication.getStartDate())));
//
//			if (!serviceApplication.isDeleted()) {
//				serviceApplicationBeans.add(ServiceApplicationBean);
//			}
//			// check สถานะด้วย รอ Application service เสร็จ
//		}
//		servicePackageBean.setServiceApplicationBeans(serviceApplicationBeans);

		return servicePackageBean;
	}
	
	public ServicePackageBean populateEntityToDtoV3(ServicePackage servicePackage) {
		// service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(servicePackage.getId());
		servicePackageBean.setPackageName(servicePackage.getPackageName());
		servicePackageBean.setPackageCode(servicePackage.getPackageCode());
		servicePackageBean.setActive(servicePackage.isActive());
		servicePackageBean.setMonthlyService(servicePackage.isMonthlyService());
		servicePackageBean.setMonthlyServiceFee(servicePackage.getMonthlyServiceFee());
		servicePackageBean.setPerMounth(servicePackage.getPerMounth());
		servicePackageBean.setFirstBillFree(servicePackage.getFirstBillFree());
		servicePackageBean.setFirstBillFreeDisCount(servicePackage.getFirstBillFreeDisCount());
		servicePackageBean.setOneServiceFee(servicePackage.getOneServiceFee());
		servicePackageBean.setDeposit(servicePackage.getDeposit());
		servicePackageBean.setInstallationFee(servicePackage.getInstallationFee());

		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		servicePackageBean.setCreateDateTh(
				null == servicePackage.getCreateDate() ? "" : formatDataTh.format(servicePackage.getCreateDate()));

		// service package type
		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
				.populateEntityToDto(servicePackage.getServicePackageType());
		servicePackageBean.setServiceType(servicePackageTypeBean);

		// company
		CompanyController companyController = new CompanyController();
		companyController.setMessageSource(messageSource);
		CompanyBean companyBean = companyController.populateEntityToDto(servicePackage.getCompany());
		servicePackageBean.setCompany(companyBean);

		List<TemplateServiceItemBean> templateItems = new ArrayList<TemplateServiceItemBean>();
		TemplateServiceBean templateServiceBean = new TemplateServiceBean();
		TemplateService templateService = servicePackage.getTemplateService();
	if(null != templateService){
		// template
		
		templateServiceBean.setId(servicePackage.getTemplateService().getId());
		// template item
		

		for (TemplateServiceItem templateServiceItem : servicePackage.getTemplateService().getTemplateServiceItems()) {
			TemplateServiceItemBean templateServiceItemBean = new TemplateServiceItemBean();
			templateServiceItemBean.setId(templateServiceItem.getId());
			templateServiceItemBean.setType(templateServiceItem.getProductType());
			templateServiceItemBean.setQuantity(templateServiceItem.getQuantity());
			templateServiceItemBean.setFree(templateServiceItem.isFree());
			templateServiceItemBean.setLend(templateServiceItem.isLend());

			// case equipment product
			if (templateServiceItem.getProductType().equals(TYPE_EQUIMENT)) {
				ProductAddController productAddController = new ProductAddController();
				productAddController.setMessageSource(messageSource);
				EquipmentProductBean equipmentProductBean = productAddController
						.populateEntityToDto(templateServiceItem.getEquipmentProduct());
				templateServiceItemBean.setProductBean(equipmentProductBean);
				templateServiceItemBean.getProductBean().setTypeEquipment();

				// case internet product
			} else if (templateServiceItem.getProductType().equals(TYPE_INTERNET_USER)) {
				ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
				interProduct.setMessageSource(messageSource);
				InternetProductBean internetProductBean = interProduct
						.populateEntityToDto(templateServiceItem.getInternetProduct());
				templateServiceItemBean.setProductBean(internetProductBean);
				templateServiceItemBean.getProductBean().setTypeInternet();

				// case service product
			} else if (templateServiceItem.getProductType().equals(TYPE_SERVICE)) {
				ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
				serviceProduct.setMessageSource(messageSource);
				ServiceProductBean serviceProductBean = serviceProduct
						.populateEntityToDto(templateServiceItem.getServiceProduct());
				templateServiceItemBean.setProductBean(serviceProductBean);
				templateServiceItemBean.getProductBean().setTypeService();

			}

			templateItems.add(templateServiceItemBean);

		}

	}
	
	templateServiceBean.setListTemplateServiceItemBean(templateItems);
	servicePackageBean.setTemplate(templateServiceBean);
	
//		// service application
//		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
//		for (ServiceApplication serviceApplication : servicePackage.getServiceApplication()) {
//			ServiceApplicationBean ServiceApplicationBean = new ServiceApplicationBean();
//			ServiceApplicationBean.setId(serviceApplication.getId());
//			// customer
//			CustomerBean customerBean = new CustomerBean();
//			customerBean.setId(serviceApplication.getCustomer().getId());
//			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
//			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
//			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
//			// customer type
//			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
//			customerTypeBean.setValue(serviceApplication.getCustomer().getCustType());
//			customerBean.setCustomerType(customerTypeBean);
//
//			ServiceApplicationBean.setCustomer(customerBean);
//			// create date th
//			SimpleDateFormat dataCreateTh = new SimpleDateFormat(
//					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
//					new Locale("TH", "th"));
//			ServiceApplicationBean.setStartDate((null == serviceApplication.getStartDate() ? ""
//					: dataCreateTh.format(serviceApplication.getStartDate())));
//
//			if (!serviceApplication.isDeleted()) {
//				serviceApplicationBeans.add(ServiceApplicationBean);
//			}
//			// check สถานะด้วย รอ Application service เสร็จ
//		}
//		servicePackageBean.setServiceApplicationBeans(serviceApplicationBeans);

		return servicePackageBean;
	}

	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public void generateSearchSession(ServicePackageSearchBean servicePackageSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("ServicePackageSearchBean", servicePackageSearchBean);
	}

	// getter setter /////////
	public void setServicePackageTypeService(ServicePackageTypeService servicePackageTypeService) {
		this.servicePackageTypeService = servicePackageTypeService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setServicePackageService(ServicePackageService servicePackageService) {
		this.servicePackageService = servicePackageService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setInternetProductService(InternetProductService internetProductService) {
		this.internetProductService = internetProductService;
	}

	public void setServiceProductService(ServiceProductService serviceProductService) {
		this.serviceProductService = serviceProductService;
	}

	public void setEquipmentProductCategoryService(EquipmentProductCategoryService equipmentProductCategoryService) {
		this.equipmentProductCategoryService = equipmentProductCategoryService;
	}
	 
}
