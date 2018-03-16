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

import com.hdw.mccable.dto.AddPointWorksheetBean;
import com.hdw.mccable.dto.AddSetTopBoxWorksheetBean;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AnalyzeProblemsWorksheetBean;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.BorrowWorksheetBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ConnectWorksheetBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CutWorksheetBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.MenuReportBean;
import com.hdw.mccable.dto.MovePointWorksheetBean;
import com.hdw.mccable.dto.MoveWorksheetBean;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReducePointWorksheetBean;
import com.hdw.mccable.dto.RepairConnectionWorksheetBean;
import com.hdw.mccable.dto.RepairMatchItemBean;
import com.hdw.mccable.dto.SearchAllProductBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TuneWorksheetBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.SubWorksheet;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetAddSetTopBox;
import com.hdw.mccable.entity.WorksheetAnalyzeProblems;
import com.hdw.mccable.entity.WorksheetBorrow;
import com.hdw.mccable.entity.WorksheetConnect;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.entity.WorksheetMovePoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetRepairConnection;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.WorksheetTune;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ProductItemWorksheetService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/worksheetadd")
public class WorkSheetAddController extends BaseController{
	
	final static Logger logger = Logger.getLogger(WorkSheetAddController.class);
	public static final String CONTROLLER_NAME = "worksheetadd/";
	public static final String INDIVIDUAL = "I";
	public static final String CORPORATE = "C";
	//product type
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	
	
	//initial service
	@Autowired
	private MessageSource messageSource;
	
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;

	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;

	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;

	@Autowired(required = true)
	@Qualifier(value = "servicePackageService")
	private ServicePackageService servicePackageService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemWorksheetService")
	private ProductItemWorksheetService productItemWorksheetService;
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	//end initial service
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// get current session
		HttpSession session = request.getSession();
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ProductAddController productAddController = new ProductAddController();
		productAddController.setProductService(productService);
		productAddController.setMessageSource(messageSource);
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{ 
				//load all service application
//				List<ServiceApplicationBean> serviceApplicationBeans = searchServiceApplicationProcess(EMPTY_STRING, EMPTY_STRING, 0L);
//				modelAndView.addObject("serviceApplicationBeans",serviceApplicationBeans);
				
				//for modal equiment product
				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findTypeEquipmentOnly();
				if (null != epcSearchs && !epcSearchs.isEmpty()) {
					for (EquipmentProductCategory epcSearch : epcSearchs) {
						EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
						epcSearchBean = epcController.populateEntityToDto(epcSearch);
						epcSearchBeans.add(epcSearchBean);
					}
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);

				// load equipment product
				List<EquipmentProductBean> epbSearchs = productAddController
						.loadEquipmentProductNotSN(new EquipmentProductBean());
				modelAndView.addObject("epbSearchs", epbSearchs);

				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);
				//End modal equipment produt
				
				// dropdown zones
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				List<Zone> zones = zoneService.findAll();
				if(null != zones && !zones.isEmpty()){
					ZoneController zoneController = new ZoneController();
					for(Zone zone:zones){
						zoneBeans.add(zoneController.populateEntityToDto(zone));
					}
				}
				modelAndView.addObject("zones", zoneBeans);
				
				// dropdown province
				ServiceApplicationController servAppController = new ServiceApplicationController();
				List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
				List<Province> provinces = addressService.findAll();
				if(null != provinces && !provinces.isEmpty()){
					for(Province province:provinces){
						provinceBeans.add(servAppController.populateEntityToDto(province));
					}
				}
				modelAndView.addObject("provinces", provinceBeans);
				
				ServicePackageController spc = new ServicePackageController();
				spc.setMessageSource(messageSource);
				spc.setProductService(productService);
				spc.setServiceProductService(serviceProductService);
				List<ProductBean> productBeans = spc.searchProductService(new SearchAllProductBean());
				modelAndView.addObject("productBeans", productBeans);				
			}catch(Exception ex){
				ex.printStackTrace();
			}			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		// CustomerFeature
		List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
		List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
		if (null != customerFeatures && !customerFeatures.isEmpty()) {
			for (CustomerFeature customerFeature : customerFeatures) {
				customerFeatureBeans.add(populateEntityToDto(customerFeature));
			}
		}
		modelAndView.addObject("customerFeatures", customerFeatureBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "serviceapplication/{serviceapplicationId}/jobType/{jobType}", method = RequestMethod.GET)
	public ModelAndView getServiceapplication(@PathVariable Long serviceapplicationId, @PathVariable String jobType,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		
		logger.info("[method : getServiceapplication][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// get current session
		HttpSession session = request.getSession();
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ProductAddController productAddController = new ProductAddController();
		productAddController.setProductService(productService);
		productAddController.setMessageSource(messageSource);
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{ 
				//load all service application
//				List<ServiceApplicationBean> serviceApplicationBeans = searchServiceApplicationProcess(EMPTY_STRING, EMPTY_STRING, 0L);
//				modelAndView.addObject("serviceApplicationBeans",serviceApplicationBeans);
				
				//for modal equiment product
				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findTypeEquipmentOnly();
				if (null != epcSearchs && !epcSearchs.isEmpty()) {
					for (EquipmentProductCategory epcSearch : epcSearchs) {
						EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
						epcSearchBean = epcController.populateEntityToDto(epcSearch);
						epcSearchBeans.add(epcSearchBean);
					}
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);

				// load equipment product
				List<EquipmentProductBean> epbSearchs = productAddController
						.loadEquipmentProduct(new EquipmentProductBean());
				modelAndView.addObject("epbSearchs", epbSearchs);

				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);
				//End modal equipment produt
				
				// dropdown zones
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				List<Zone> zones = zoneService.findAll();
				if(null != zones && !zones.isEmpty()){
					ZoneController zoneController = new ZoneController();
					for(Zone zone:zones){
						zoneBeans.add(zoneController.populateEntityToDto(zone));
					}
				}
				modelAndView.addObject("zones", zoneBeans);
				
				// dropdown province
				ServiceApplicationController servAppController = new ServiceApplicationController();
				List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
				List<Province> provinces = addressService.findAll();
				if(null != provinces && !provinces.isEmpty()){
					for(Province province:provinces){
						provinceBeans.add(servAppController.populateEntityToDto(province));
					}
				}
				modelAndView.addObject("provinces", provinceBeans);
				
				ServicePackageController spc = new ServicePackageController();
				spc.setMessageSource(messageSource);
				spc.setProductService(productService);
				spc.setServiceProductService(serviceProductService);
				List<ProductBean> productBeans = spc.searchProductService(new SearchAllProductBean());
				modelAndView.addObject("productBeans", productBeans);				
			}catch(Exception ex){
				ex.printStackTrace();
			}			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		// CustomerFeature
		List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
		List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
		if (null != customerFeatures && !customerFeatures.isEmpty()) {
			for (CustomerFeature customerFeature : customerFeatures) {
				customerFeatureBeans.add(populateEntityToDto(customerFeature));
			}
		}
		modelAndView.addObject("customerFeatures", customerFeatureBeans);
		
		modelAndView.addObject("serviceapplicationId", serviceapplicationId);
		modelAndView.addObject("jobType", jobType);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	// searchServiceApplication
	@RequestMapping(value = "searchServiceApplication", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse searchServiceApplication(@RequestBody final ApplicationSearchBean applicationSearchBean,
			HttpServletRequest request) {
		logger.info("[method : searchServiceApplication][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				logger.info("param applicationSearchBean : " + applicationSearchBean.toString());
				List<ServiceApplicationBean>  serviceApplicationBeans = searchServiceApplicationProcess(applicationSearchBean.getKey(),applicationSearchBean.getCustType(),applicationSearchBean.getCustomerFeatures());
				jsonResponse.setResult(serviceApplicationBeans);
				jsonResponse.setError(false);
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
	
	@RequestMapping(value = "loadServiceApplication/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadServiceApplication(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadServiceApplication][Type : Controller]");
		
		ServiceApplicationListController serviceApplicationListController = new ServiceApplicationListController();
		serviceApplicationListController.setMessageSource(messageSource);
		serviceApplicationListController.setServiceApplicationService(serviceApplicationService);
		
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				  ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(Long.valueOf(id));
				  if(serviceApplication != null){
					  ServiceApplicationBean serviceApplicationBean = serviceApplicationListController.populateEntityToDto(serviceApplication);
					  //load current bill
					  List<InvoiceDocumentBean> invoiceCurrentBill = new ArrayList<InvoiceDocumentBean>();
					  List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();
					  
					  InvoiceController invController = new InvoiceController();
					  invController.setMessageSource(messageSource);
					  
					  for(Invoice invoice : serviceApplication.getInvoices()){
						  //InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
						  if(invoice.getStatus().equals("W")){
							  invoiceCurrentBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
							  
						  }else if(invoice.getStatus().equals("O")){
							  invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
						  }
					  }
					  serviceApplicationBean.setInvoiceCurrentBill(invoiceCurrentBill);
					  serviceApplicationBean.setInvoiceOverBill(invoiceOverBill);
					  
					  jsonResponse.setResult(serviceApplicationBean);
					  jsonResponse.setError(false);
				  }else{
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
	
	
	//----------------------------------------- method for worksheet create type cable -------------------------------------//
	
	//save tune
	@RequestMapping(value="saveTune", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveTune(@RequestBody final TuneWorksheetBean tuneWorksheetBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{ 
				
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()));
				worksheet.setRemark(tuneWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(tuneWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));
				//service application
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(tuneWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);
				
				//tune worksheet
				WorksheetTune worksheetTune = new WorksheetTune();
				worksheetTune.setWorkSheet(worksheet);
				worksheetTune.setTuneType(tuneWorksheetBean.getTuneType());
				worksheetTune.setDeleted(Boolean.FALSE);
				worksheetTune.setCreateDate(CURRENT_TIMESTAMP);
				worksheetTune.setCreatedBy(getUserNameLogin());
				worksheet.setWorksheetTune(worksheetTune);
				
				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// load connect worksheet
	@RequestMapping(value = "loadConnect", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadConnect(@RequestBody final ConnectWorksheetBean connectWorksheetBean, HttpServletRequest request) {
		logger.info("[method : loadConnect][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try { 
				ServiceProduct serviceProduct = serviceProductService.getSerivceProductByCode(messageSource.getMessage("serviceProduct.servicecode.type.connect", null, LocaleContextHolder.getLocale()));
				if(serviceProduct != null){
					ServiceProductBean serviceProductBean = new ServiceProductBean();
					serviceProductBean.setId(serviceProduct.getId());
					serviceProductBean.setPrice(serviceProduct.getPrice());
					serviceProductBean.setProductCode(serviceProduct.getProductCode());
					jsonResponse.setResult(serviceProductBean);
					jsonResponse.setError(false);
				}else{
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
	
	
	// save connect
	@RequestMapping(value = "saveConnect", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveConnect(@RequestBody final ConnectWorksheetBean connectWorksheetBean, HttpServletRequest request) {
		logger.info("[method : saveConnect][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try { 
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()));
				worksheet.setRemark(connectWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(connectWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));
				
				//service application
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(connectWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);
					
				ServiceProduct serviceProduct = serviceProductService.getSerivceProductByCode(connectWorksheetBean.getServiceProductBean().getProductCode());
				//connect worksheet
				WorksheetConnect worksheetConnect = new WorksheetConnect();
				worksheetConnect.setWorkSheet(worksheet);
				worksheetConnect.setDeleted(Boolean.FALSE);
				worksheetConnect.setCreateDate(CURRENT_TIMESTAMP);
				worksheetConnect.setCreatedBy(getUserNameLogin());
				worksheetConnect.setServiceProduct(serviceProduct);
				worksheet.setWorksheetConnect(worksheetConnect);
				
				//product item
				ProductItem ProductItem = new ProductItem();
				ProductItem.setCreateDate(CURRENT_TIMESTAMP);
				ProductItem.setCreatedBy(getUserNameLogin());
				ProductItem.setPrice(connectWorksheetBean.getPrice());
				ProductItem.setDeleted(Boolean.FALSE);
				ProductItem.setLend(Boolean.FALSE);
				ProductItem.setFree(Boolean.FALSE);
				ProductItem.setProductType(TYPE_SERVICE);
				ProductItem.setQuantity(1);
				ProductItem.setServiceProduct(serviceProduct);
				ProductItem.setWorkSheet(worksheet);
				ProductItem.setProductTypeMatch("O");
				ProductItem.setAmount(connectWorksheetBean.getPrice());
				List<ProductItem> productItemList = new ArrayList<ProductItem>();
				productItemList.add(ProductItem);
				worksheet.setProductItems(productItemList);
				
				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}


	// save connect
	@RequestMapping(value = "saveAddSettop", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveAddSettop(@RequestBody final AddSetTopBoxWorksheetBean addSetTopBoxWorksheetBean,
			HttpServletRequest request) {
		logger.info("[method : saveAddSettop][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.addsettop", null,
						LocaleContextHolder.getLocale()));
				worksheet.setRemark(addSetTopBoxWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(addSetTopBoxWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(addSetTopBoxWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);
				
				//connect worksheet
				WorksheetAddSetTopBox worksheetAddSetTopBox = new WorksheetAddSetTopBox();
				worksheetAddSetTopBox.setWorkSheet(worksheet);
				worksheetAddSetTopBox.setDeleted(Boolean.FALSE);
				worksheetAddSetTopBox.setCreateDate(CURRENT_TIMESTAMP);
				worksheetAddSetTopBox.setCreatedBy(getUserNameLogin());
				worksheet.setWorksheetAddSetTopBox(worksheetAddSetTopBox);
				
				// product item
				List<ProductItem> productItemList = new ArrayList<ProductItem>();
				for(ProductItemBean productItemBean : addSetTopBoxWorksheetBean.getProductItemList()){
					ProductItem ProductItem = new ProductItem();
					ProductItem.setCreateDate(CURRENT_TIMESTAMP);
					ProductItem.setCreatedBy(getUserNameLogin());
					ProductItem.setDeleted(Boolean.FALSE);
					ProductItem.setLend(Boolean.FALSE);
					ProductItem.setFree(Boolean.FALSE);
					ProductItem.setProductType(TYPE_EQUIMENT);
					ProductItem.setProductTypeMatch("A");
					ProductItem.setServiceApplication(serviceApplication);
					//set equipment
					EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductById(productItemBean.getId());
					ProductItem.setEquipmentProduct(equipmentProduct);
					ProductItem.setWorkSheet(worksheet);
					//worksheet item
					List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
					for(ProductItemWorksheetBean productItemWorksheetBean : productItemBean.getProductItemWorksheetBeanList()){
						ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
						ProductItemWorksheet.setProductItem(ProductItem);
						ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
						ProductItemWorksheet.setAmount(productItemWorksheetBean.getAmount());
						ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
						ProductItemWorksheet.setCreatedBy(getUserNameLogin());
						ProductItemWorksheet.setDeleted(Boolean.FALSE);
						ProductItemWorksheet.setLend(Boolean.FALSE);
						ProductItemWorksheet.setFree(Boolean.FALSE);
						ProductItemWorksheet.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
						ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
						ProductItemWorksheet.setProductTypeMatch("A");
						//update temp item
						EquipmentProductItem equipmentProductItemTemp = productService
								.findEquipmentProductItemById(productItemWorksheetBean.getEquipmentProductItemBean().getId());
						if (equipmentProductItemTemp != null && (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

							equipmentProductItemTemp.setSpare(0);
							equipmentProductItemTemp.setStatus(STATUS_HOLD);
							equipmentProductItemTemp.setBalance(0);
							equipmentProductItemTemp.setReservations(1);

							productService.updateProductItem(equipmentProductItemTemp);

						}
						//load product item
						EquipmentProductItem equipmentProductItem = productService
								.findEquipmentProductItemById(productItemWorksheetBean.getEquipmentProductItemBean().getId());
						
						ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
						productItemWorksheetList.add(ProductItemWorksheet);
					}
					ProductItem.setProductItemWorksheets(productItemWorksheetList);
					productItemList.add(ProductItem);
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
				 
				worksheet.setProductItems(productItemList);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// save borrow
	@RequestMapping(value = "saveBorrow", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveBorrow(@RequestBody final BorrowWorksheetBean borrowWorksheetBean,
			HttpServletRequest request) {
		logger.info("[method : saveBorrow][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.borrow", null,
						LocaleContextHolder.getLocale()));
				worksheet.setRemark(borrowWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(borrowWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(borrowWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// connect worksheet
				WorksheetBorrow worksheetBorrow = new WorksheetBorrow();
				worksheetBorrow.setWorkSheet(worksheet);
				worksheetBorrow.setDeleted(Boolean.FALSE);
				worksheetBorrow.setCreateDate(CURRENT_TIMESTAMP);
				worksheetBorrow.setCreatedBy(getUserNameLogin());
				worksheet.setWorksheetBorrow(worksheetBorrow);

				// product item
				List<ProductItem> productItemList = new ArrayList<ProductItem>();
				for (ProductItemBean productItemBean : borrowWorksheetBean.getProductItemList()) {
					ProductItem ProductItem = new ProductItem();
					ProductItem.setCreateDate(CURRENT_TIMESTAMP);
					ProductItem.setCreatedBy(getUserNameLogin());
					ProductItem.setDeleted(Boolean.FALSE);
					ProductItem.setLend(Boolean.TRUE);
					ProductItem.setFree(Boolean.FALSE);
					ProductItem.setProductType(TYPE_EQUIMENT);
					ProductItem.setProductTypeMatch("B");
					ProductItem.setServiceApplication(serviceApplication);
					// set equipment
					EquipmentProduct equipmentProduct = equipmentProductService
							.getEquipmentProductById(productItemBean.getId());
					ProductItem.setEquipmentProduct(equipmentProduct);
					ProductItem.setWorkSheet(worksheet);
					// worksheet item
					List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
					for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
							.getProductItemWorksheetBeanList()) {
						ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
						ProductItemWorksheet.setProductItem(ProductItem);
						ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
						ProductItemWorksheet.setDeposit(productItemWorksheetBean.getDeposit());
						ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
						ProductItemWorksheet.setCreatedBy(getUserNameLogin());
						ProductItemWorksheet.setDeleted(Boolean.FALSE);
						ProductItemWorksheet.setLend(Boolean.TRUE);
						ProductItemWorksheet.setFree(Boolean.FALSE);
						ProductItemWorksheet.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
						ProductItemWorksheet.setFree(Boolean.FALSE);
						ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
						ProductItemWorksheet.setProductTypeMatch("B");
						ProductItemWorksheet.setAmount(0);
						//update temp item
						EquipmentProductItem equipmentProductItemTemp = productService
								.findEquipmentProductItemById(productItemWorksheetBean.getEquipmentProductItemBean().getId());

						if (equipmentProductItemTemp != null && (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

							if(equipmentProductItemTemp.getSpare() > 0){
								equipmentProductItemTemp.setSpare(0);
							}
							equipmentProductItemTemp.setStatus(STATUS_HOLD);
							equipmentProductItemTemp.setBalance(1);
							equipmentProductItemTemp.setReservations(1);

							productService.updateProductItem(equipmentProductItemTemp);

						} 
						
						//load product item
						EquipmentProductItem equipmentProductItem = productService
								.findEquipmentProductItemById(productItemWorksheetBean.getEquipmentProductItemBean().getId());
						
						ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
						productItemWorksheetList.add(ProductItemWorksheet);
					}
					ProductItem.setProductItemWorksheets(productItemWorksheetList);
					productItemList.add(ProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}

				worksheet.setProductItems(productItemList);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// save repair
	@RequestMapping(value = "saveRepair", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveRepair(@RequestBody final RepairConnectionWorksheetBean repairWorksheetBean,
			HttpServletRequest request) {
		logger.info("[method : saveRepair][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try { 
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.repair", null,LocaleContextHolder.getLocale()));
				worksheet.setRemark(repairWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(repairWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(repairWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// repair worksheet
				WorksheetRepairConnection worksheetRepair = new WorksheetRepairConnection();
				worksheetRepair.setWorkSheet(worksheet);
				worksheetRepair.setDeleted(Boolean.FALSE);
				worksheetRepair.setCreateDate(CURRENT_TIMESTAMP);
				worksheetRepair.setCreatedBy(getUserNameLogin());
				worksheet.setWorksheetRepairConnection(worksheetRepair);
				
				//product item
				List<ProductItem> productItemList = new ArrayList<ProductItem>();
				for(RepairMatchItemBean repairMatchItemBean : repairWorksheetBean.getRepairMatchItemBeanList()){
					//old productItemWorksheetOldId
					Long productItemWorksheetOldId = repairMatchItemBean.getOldItemId();
					//new product item record
					EquipmentProductItem oldEquipmentItem = new EquipmentProductItem();
					ProductItemWorksheet productItemWorksheetOld = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheetOldId);
					if(null != productItemWorksheetOld){
						oldEquipmentItem = productItemWorksheetOld.getEquipmentProductItem();
					}
					
					//product item create
					ProductItem ProductItem = new ProductItem();
					ProductItem.setCreateDate(CURRENT_TIMESTAMP);
					ProductItem.setCreatedBy(getUserNameLogin());
					ProductItem.setDeleted(Boolean.FALSE);
					ProductItem.setLend(productItemWorksheetOld.isLend());
					ProductItem.setFree(productItemWorksheetOld.isFree());
					ProductItem.setProductType(TYPE_EQUIMENT);
					ProductItem.setProductTypeMatch("R");
					ProductItem.setEquipmentProduct(oldEquipmentItem.getEquipmentProduct());
					ProductItem.setWorkSheet(worksheet);
					ProductItem.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
					ProductItem.setServiceApplication(serviceApplication);
					//product item worksheet
					List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
					ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
					ProductItemWorksheet.setProductItem(ProductItem);
					ProductItemWorksheet.setPrice(productItemWorksheetOld.getPrice());
					ProductItemWorksheet.setDeposit(productItemWorksheetOld.getDeposit());
					ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
					ProductItemWorksheet.setCreatedBy(getUserNameLogin());
					ProductItemWorksheet.setDeleted(Boolean.FALSE);
					ProductItemWorksheet.setLend(productItemWorksheetOld.isLend());
					ProductItemWorksheet.setFree(productItemWorksheetOld.isFree());
					ProductItemWorksheet.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
					ProductItemWorksheet.setFree(Boolean.FALSE);
					ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
					ProductItemWorksheet.setProductTypeMatch("R");
					ProductItemWorksheet.setAmount(productItemWorksheetOld.getAmount());
					ProductItemWorksheet.setParent(productItemWorksheetOld.getId());
					ProductItemWorksheet.setComment(repairMatchItemBean.getComment());
					
					EquipmentProductItem equipmentProductItemTemp = productService
							.findEquipmentProductItemById(repairMatchItemBean.getNewItemId());

					if (equipmentProductItemTemp != null && (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

						if(equipmentProductItemTemp.getSpare() > 0){
							equipmentProductItemTemp.setSpare(0);
						}
						equipmentProductItemTemp.setStatus(STATUS_HOLD);
						equipmentProductItemTemp.setBalance(0);
						equipmentProductItemTemp.setReservations(1);

						productService.updateProductItem(equipmentProductItemTemp);

					} 
					//load product item
					EquipmentProductItem equipmentProductItem = productService
							.findEquipmentProductItemById(repairMatchItemBean.getNewItemId());
					
					ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
					productItemWorksheetList.add(ProductItemWorksheet);
					
					ProductItem.setProductItemWorksheets(productItemWorksheetList);
					productItemList.add(ProductItem);
					
					// อัพ product ตัวแม่
					if(null != oldEquipmentItem.getEquipmentProduct()){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(oldEquipmentItem.getEquipmentProduct());
					}
				}
				
				//save worksheet
				worksheet.setProductItems(productItemList);
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// load loadRepair (Equipment product item has s/n)
	@RequestMapping(value = "loadRepair", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadRepair(@RequestBody final ServiceApplicationBean serviceApplicationBean,
			HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try { 
				ServiceApplication serviceApplication = new ServiceApplication();
				serviceApplication.setId(serviceApplicationBean.getId());
				List<ProductItemWorksheet> productItemWorksheets = equipmentProductItemService.loadEquipmentProductItemHasSNAllStatus(serviceApplication);
				
				List<ProductItemWorksheetBean> productItemWorksheetBeans = new ArrayList<ProductItemWorksheetBean>();
				for(ProductItemWorksheet productItemWorksheet : productItemWorksheets){
					ProductItemWorksheetBean productItemWorksheetBean = populoateProductItemWorksheet(productItemWorksheet);
					productItemWorksheetBeans.add(productItemWorksheetBean);
				}
				 
				jsonResponse.setResult(productItemWorksheetBeans);
				jsonResponse.setError(false);
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
	
	//-------------For TJ ยนน--------------//

	@RequestMapping(value = "loadPointAllByAddPointWorksheetId", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadPointAllByAddPointWorksheetId(@RequestBody final ServiceApplicationBean serviceApplication,
			HttpServletRequest request) {
		logger.info("[method : loadConnect][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if (null != serviceApplication) {
					AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
					List<ServiceProductBean> serviceProductBean = new ArrayList<ServiceProductBean>();
					// ดึงราคาจาก ServiceProduct มาใช้ในการคำนวน
					String[] productCodeArray = {
							messageSource.getMessage("serviceProduct.servicecode.type.add.digitalpoint", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.add.analogpoint", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.move.point", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.move.cable", null,
									LocaleContextHolder.getLocale())};
					List<ServiceProduct> setUpPoint = serviceProductService.getSetUpPoint(productCodeArray);
					if (null != setUpPoint && setUpPoint.size() > 0) {
						for (ServiceProduct point : setUpPoint) {
							ServiceProductBean bean = new ServiceProductBean();
							bean.setId(point.getId());
							bean.setProductCode(point.getProductCode());
							bean.setPrice(point.getPrice());
							serviceProductBean.add(bean);
						}
					}
					addPointWorksheetBean.setServiceProductBean(serviceProductBean);

					List<ProductItem> productItems = productItemService
							.getProductItemByServiceApplicationId(serviceApplication.getId());
					int digitalPoint = 0;
					int analogPoint = 0;
					if (null != productItems && productItems.size() > 0) {
						for (ProductItem productItem : productItems) {

							Worksheet worksheet = productItem.getWorkSheet();
							ServiceProduct serviceProduct = productItem.getServiceProduct();
							// ใบงานติดตั้ง
							if (null != worksheet) {
								WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
//								WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
//								WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
								if (null != worksheetSetup && null != serviceProduct) {
									String productCode = serviceProduct.getProductCode();
									if ("00002".equals(productCode)) {
										digitalPoint += productItem.getQuantity();
									}
									if ("00003".equals(productCode)) {
										analogPoint += productItem.getQuantity();
									}
								}
							}
						}
					}

					// ใบงานเสริมจุด
					List<Worksheet> worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(
							messageSource.getMessage("worksheet.type.cable.addpoint", null,
									LocaleContextHolder.getLocale()),
							serviceApplication.getId());

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
							if (null != worksheetAddPoint && worksheetAddPoint.isActive()) {
								digitalPoint += worksheetAddPoint.getDigitalPoint();
								analogPoint += worksheetAddPoint.getAnalogPoint();
							}
						}
					}

					// ใบงานลดจุด
					worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(messageSource
							.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()),
							serviceApplication.getId());

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
							if (null != worksheetReducePoint && worksheetReducePoint.isActive()) {
								digitalPoint -= worksheetReducePoint.getDigitalPoint();
								analogPoint -= worksheetReducePoint.getAnalogPoint();
							}
						}
					}

					addPointWorksheetBean.setDigitalPoint(digitalPoint);
					addPointWorksheetBean.setAnalogPoint(analogPoint);

					jsonResponse.setResult(addPointWorksheetBean);
				}
				jsonResponse.setError(false);
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
	
	// load loadPointAll worksheet
	@RequestMapping(value = "loadPointAll", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadPointAll(@RequestBody final ServiceApplicationBean serviceApplication,
			HttpServletRequest request) {
		logger.info("[method : loadConnect][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if (null != serviceApplication) {
					AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
					List<ServiceProductBean> serviceProductBean = new ArrayList<ServiceProductBean>();
					// ดึงราคาจาก ServiceProduct มาใช้ในการคำนวน
					String[] productCodeArray = {
							messageSource.getMessage("serviceProduct.servicecode.type.add.digitalpoint", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.add.analogpoint", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.move.point", null,
									LocaleContextHolder.getLocale()),
							messageSource.getMessage("serviceProduct.servicecode.type.move.cable", null,
									LocaleContextHolder.getLocale())};
					List<ServiceProduct> setUpPoint = serviceProductService.getSetUpPoint(productCodeArray);
					if (null != setUpPoint && setUpPoint.size() > 0) {
						for (ServiceProduct point : setUpPoint) {
							ServiceProductBean bean = new ServiceProductBean();
							bean.setId(point.getId());
							bean.setProductCode(point.getProductCode());
							bean.setPrice(point.getPrice());
							serviceProductBean.add(bean);
						}
					}
					addPointWorksheetBean.setServiceProductBean(serviceProductBean);

					List<ProductItem> productItems = productItemService
							.getProductItemByServiceApplicationId(serviceApplication.getId());
					int digitalPoint = 0;
					int analogPoint = 0;
					if (null != productItems && productItems.size() > 0) {
						for (ProductItem productItem : productItems) {

							Worksheet worksheet = productItem.getWorkSheet();
							ServiceProduct serviceProduct = productItem.getServiceProduct();
							// ใบงานติดตั้ง
							if (null != worksheet) {
								WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
//								WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
//								WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
								if (null != worksheetSetup && null != serviceProduct) {
									String productCode = serviceProduct.getProductCode();
									if ("00002".equals(productCode)) {
										digitalPoint += productItem.getQuantity();
									}
									if ("00003".equals(productCode)) {
										analogPoint += productItem.getQuantity();
									}
								}
							}
						}
					}

					// ใบงานเสริมจุด
					List<Worksheet> worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(
							messageSource.getMessage("worksheet.type.cable.addpoint", null,
									LocaleContextHolder.getLocale()),
							serviceApplication.getId());

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
							if (null != worksheetAddPoint && worksheetAddPoint.isActive()) {
								digitalPoint += worksheetAddPoint.getDigitalPoint();
								analogPoint += worksheetAddPoint.getAnalogPoint();
							}
						}
					}

					// ใบงานลดจุด
					worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(messageSource
							.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()),
							serviceApplication.getId());

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
							if (null != worksheetReducePoint && worksheetReducePoint.isActive()) {
								digitalPoint -= worksheetReducePoint.getDigitalPoint();
								analogPoint -= worksheetReducePoint.getAnalogPoint();
							}
						}
					}

					addPointWorksheetBean.setDigitalPoint(digitalPoint);
					addPointWorksheetBean.setAnalogPoint(analogPoint);

					jsonResponse.setResult(addPointWorksheetBean);
				}
				jsonResponse.setError(false);
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

	// saveAddPoint
	@RequestMapping(value = "saveAddPoint", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveAddPoint(@RequestBody final AddPointWorksheetBean addPointWorksheetBean,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			// create timestamp
//			
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.addpoint", null,
						LocaleContextHolder.getLocale()));
				worksheet.setRemark(addPointWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(addPointWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(addPointWorksheetBean.getServiceApplication().getId());
				
				worksheet.setServiceApplication(serviceApplication);

				// worksheetAddPoint
				WorksheetAddPoint worksheetAddPoint = new WorksheetAddPoint();
				worksheetAddPoint.setDigitalPoint(addPointWorksheetBean.getDigitalPoint());
				worksheetAddPoint.setAnalogPoint(addPointWorksheetBean.getAnalogPoint());
				worksheetAddPoint.setAddPointPrice(addPointWorksheetBean.getAddPointPrice());
				worksheetAddPoint.setMonthlyFree(addPointWorksheetBean.getMonthlyFree());
				worksheetAddPoint.setWorkSheet(worksheet);
				worksheetAddPoint.setDeleted(Boolean.FALSE);
				worksheetAddPoint.setCreateDate(CURRENT_TIMESTAMP);
				worksheetAddPoint.setCreatedBy(getUserNameLogin());

				worksheet.setWorksheetAddPoint(worksheetAddPoint);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);

				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// saveMovePoint
	@RequestMapping(value = "saveMovePoint", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveMovePoint(@RequestBody final MovePointWorksheetBean movePointWorksheetBean,
			HttpServletRequest request) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
//			
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.movepoint", null,
						LocaleContextHolder.getLocale()));
				worksheet.setRemark(movePointWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(movePointWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(movePointWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// worksheetMovePoint
				WorksheetMovePoint worksheetMovePoint = new WorksheetMovePoint();
				worksheetMovePoint.setDigitalPoint(movePointWorksheetBean.getDigitalPoint());
				worksheetMovePoint.setAnalogPoint(movePointWorksheetBean.getAnalogPoint());
				worksheetMovePoint.setMovePointPrice(movePointWorksheetBean.getMovePointPrice());
				worksheetMovePoint.setWorkSheet(worksheet);
				worksheetMovePoint.setDeleted(Boolean.FALSE);
				worksheetMovePoint.setCreateDate(CURRENT_TIMESTAMP);
				worksheetMovePoint.setCreatedBy(getUserNameLogin());

				worksheet.setWorksheetMovePoint(worksheetMovePoint);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// saveReducePoint
	@RequestMapping(value = "saveReducePoint", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveReducePoint(@RequestBody final ReducePointWorksheetBean reducePointWorksheetBean,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			// create timestamp
//			
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.reducepoint", null,
						LocaleContextHolder.getLocale()));
				worksheet.setRemark(reducePointWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(reducePointWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(reducePointWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// worksheetMovePoint
				WorksheetReducePoint worksheetReducePoint = new WorksheetReducePoint();
				worksheetReducePoint.setDigitalPoint(reducePointWorksheetBean.getDigitalPoint());
				worksheetReducePoint.setAnalogPoint(reducePointWorksheetBean.getAnalogPoint());
				worksheetReducePoint.setMonthlyFree(reducePointWorksheetBean.getMonthlyFree());
				worksheetReducePoint.setWorkSheet(worksheet);
				worksheetReducePoint.setDeleted(Boolean.FALSE);
				worksheetReducePoint.setCreateDate(CURRENT_TIMESTAMP);
				worksheetReducePoint.setCreatedBy(getUserNameLogin());

				worksheet.setWorksheetReducePoint(worksheetReducePoint);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// saveCutcable
	@RequestMapping(value = "saveCutcable", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveCutcable(@RequestBody final CutWorksheetBean cutWorksheetBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			// create timestamp
//			
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(
						messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()));
				worksheet.setRemark(cutWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(cutWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(cutWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// มีการคืนอุปกรณ์ update รายการอุปกรณ์ที่เช่ายืม
				List<ProductItemWorksheetBean> productItemWorksheetBeans= cutWorksheetBean.getProductItemWorksheetBeanList();
				if(null != productItemWorksheetBeans && productItemWorksheetBeans.size() > 0){
					for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeans){
						ProductItemWorksheet productItemWorksheet = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheetBean.getId());
						if(null != productItemWorksheet){
							productItemWorksheet.setLendStatus(productItemWorksheetBean.getLendStatus());
							productItemWorksheet.setReturnEquipment(productItemWorksheetBean.isReturnEquipment());
							productItemWorksheetService.update(productItemWorksheet);
						}
					}
				}
				
				SimpleDateFormat formatDateUS = new SimpleDateFormat(
						messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),Locale.US);
				// worksheetMovePoint
				WorksheetCut worksheetCut = new WorksheetCut();
				worksheetCut.setReporter(cutWorksheetBean.getReporter());
				worksheetCut.setMobile(cutWorksheetBean.getMobile());
				worksheetCut.setCutWorkType(cutWorksheetBean.getCutWorkType());
				worksheetCut.setReturnEquipment(cutWorksheetBean.isReturnEquipment());
				worksheetCut.setSubmitCA(cutWorksheetBean.isSubmitCA());
				worksheetCut.setCancelDate("".equals(cutWorksheetBean.getCancelDate())?null:formatDateUS.parse(cutWorksheetBean.getCancelDate()));
				worksheetCut.setEndPackageDate("".equals(cutWorksheetBean.getEndPackageDate())?null:formatDateUS.parse(cutWorksheetBean.getEndPackageDate()));
				worksheetCut.setSpecialDiscount(cutWorksheetBean.getSpecialDiscount());
				worksheetCut.setWorkSheet(worksheet);
				worksheetCut.setDeleted(Boolean.FALSE);
				worksheetCut.setCreateDate(CURRENT_TIMESTAMP);
				worksheetCut.setCreatedBy(getUserNameLogin());

				worksheet.setWorksheetCut(worksheetCut);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				
				// ค่าใช้จ่ายเพิ่มเติม มีเฉพาะ serviceProduct
				List<ProductItemBean> productItemBeans = cutWorksheetBean.getProductItemList();
				if(null != productItemBeans && productItemBeans.size() > 0){
					for(ProductItemBean productItemBean:productItemBeans){	
						ProductItem productItem = new ProductItem();
						productItem.setProductType(TYPE_SERVICE);
						productItem.setPrice(productItemBean.getServiceProductBean().getPrice());
						productItem.setAmount(productItemBean.getServiceProductBean().getPrice());
						ServiceProduct serviceProduct = serviceProductService.getServiceProductById(productItemBean.getServiceProductBean().getId());
						productItem.setServiceProduct(serviceProduct);
						productItem.setWorkSheet(worksheet);
						productItem.setServiceApplication(serviceApplication);
						productItem.setCreateDate(CURRENT_TIMESTAMP);
						productItem.setCreatedBy(getUserNameLogin());
						productItemService.save(productItem);
					}
				}
				
				//บันทึกคืนเงินมัดจำลงใบสมัคร
				ServiceApplication serviceApplicationUdateDeposit = 
						serviceApplicationService.getServiceApplicationById(cutWorksheetBean.getServiceApplication().getId());
				
				serviceApplicationUdateDeposit.setRefund(cutWorksheetBean.getServiceApplication().getRefund());
				serviceApplicationUdateDeposit.setFlagRefund(cutWorksheetBean.getServiceApplication().isFlagRefund());
				serviceApplicationService.update(serviceApplicationUdateDeposit);
				
				//save invoice
				createInvoiceReceipt(worksheetId);
				
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// saveMove
	@RequestMapping(value = "saveMove", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveMove(@RequestBody final MoveWorksheetBean moveWorksheetBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			// create timestamp
//			
			try {
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(
						messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()));
				worksheet.setRemark(moveWorksheetBean.getRemark());
				worksheet.setAvailableDateTime(moveWorksheetBean.getAvailableDateTime());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(
						messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));

				// service application
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(moveWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);

				// WorksheetMove
				
				WorksheetMove worksheetMove = new WorksheetMove();
				worksheetMove.setDigitalPoint(moveWorksheetBean.getDigitalPoint());
				worksheetMove.setAnalogPoint(moveWorksheetBean.getAnalogPoint());
				worksheetMove.setMoveCablePrice(moveWorksheetBean.getMoveCablePrice());
				worksheetMove.setWorkSheet(worksheet);
				worksheetMove.setDeleted(Boolean.FALSE);
				worksheetMove.setCreateDate(CURRENT_TIMESTAMP);
				worksheetMove.setCreatedBy(getUserNameLogin());

				worksheet.setWorksheetMove(worksheetMove);

				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
				List<AddressBean> addressBeans = moveWorksheetBean.getAddressList();
				if(null != addressBeans && addressBeans.size() > 0){
					for(AddressBean addressBean:addressBeans){
						Address address = new Address();
						address.setAddressType(addressBean.getAddressType());
						address.setNo(addressBean.getNo());
						address.setSection(addressBean.getSection());
						address.setBuilding(addressBean.getBuilding());
						address.setRoom(addressBean.getRoom());
						address.setFloor(addressBean.getFloor());
						address.setVillage(addressBean.getVillage());
						address.setAlley(addressBean.getAlley());
						address.setRoad(addressBean.getRoad());
						
						Province provinceModel = addressService.getProvinceById(addressBean.getProvinceBean().getId());
						address.setProvinceModel(provinceModel);
						
						Amphur amphur = addressService.getAmphurById(addressBean.getAmphurBean().getId());
						address.setAmphur(amphur);
						
						District districtModel = addressService.getDistrictById(addressBean.getDistrictBean().getId());
						address.setDistrictModel(districtModel);
						
						address.setPostcode(addressBean.getPostcode());
						address.setNearbyPlaces(addressBean.getNearbyPlaces());
						
						address.setWorksheetMove(worksheetMove);
						
						Zone zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
						address.setZone(zone);
						
						address.setServiceApplication(serviceApplication);
						address.setCreateDate(CURRENT_TIMESTAMP);
						address.setCreatedBy(getUserNameLogin());
						addressService.save(address);
					}
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
				messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
				
	//-------------End For TJ ยนน--------------//
	
	
	@RequestMapping(value = "loadEquipmentProductItemWithEparentId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadPersonnelTeam(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductItemWithEparentId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				List<EquipmentProductItem> eqItem = equipmentProductService.getEquipmentProductItemByParentId(Long.valueOf(id));
				List<EquipmentProductItemBean> equipmentProductItemBeanList = new ArrayList<EquipmentProductItemBean>();
				for(EquipmentProductItem equipmentProductItem : eqItem){
					
					EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
					equipmentProductItemBean.setId(equipmentProductItem.getId());
					equipmentProductItemBean.setCost(equipmentProductItem.getCost());
					equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
					equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
					equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
					equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());

					SimpleDateFormat formatDataTh = new SimpleDateFormat(
							messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					// guarantee date
					equipmentProductItemBean.setGuaranteeDate(null == equipmentProductItem.getGuaranteeDate() ? ""
							: formatDataTh.format(equipmentProductItem.getGuaranteeDate()));
					equipmentProductItemBean.setOrderDate(null == equipmentProductItem.getOrderDate() ? ""
							: formatDataTh.format(equipmentProductItem.getOrderDate()));
					
					SimpleDateFormat formatDateAndTimeTh = new SimpleDateFormat(
							messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					//import system datetime
					equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate() ? ""
							: formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));
					
					//status
					ProductUtil productUtil = new ProductUtil();
					productUtil.setMessageSource(messageSource);
					equipmentProductItemBean.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));
					//EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
					equipmentProductItemBeanList.add(equipmentProductItemBean);
					jsonResponse.setResult(equipmentProductItemBeanList);
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
	
	
	@RequestMapping(value = "loadEquipmentProductItem/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProductItem(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(Long.valueOf(id));
				if(equipmentProductItem != null){
					EquipmentProductItemBean equipmentProductItemBean = populateProductItemIgnoreDeleted(equipmentProductItem);
					jsonResponse.setResult(equipmentProductItemBean);
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
	
	//create invoice and receipt
	public void createInvoiceReceipt(Long workSheetId){
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		Worksheet worksheet = workSheetService.getWorksheetById(workSheetId);
		if(worksheet != null){
			//calculate amount
			float amount = calculateAmountInvoice(worksheet);
			//invoice set value
			Invoice invoice = new Invoice();
			invoice.setInvoiceCode(financialService.genInVoiceCode());
			invoice.setAmount(amount);
			invoice.setWorkSheet(worksheet);
			invoice.setServiceApplication(worksheet.getServiceApplication());
			//invoice type
			if(worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))){
				invoice.setInvoiceType(messageSource.getMessage("financial.invoice.type.setup", null, LocaleContextHolder.getLocale()));
			}else{
				invoice.setInvoiceType(messageSource.getMessage("financial.invoice.type.repair", null, LocaleContextHolder.getLocale()));
			}
			//invoice status
			invoice.setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale()));
			//invoice.setIssueDocDate();
			//invoice.setPaymentDate();
			invoice.setDeleted(Boolean.FALSE);
			invoice.setCreatedBy(getUserNameLogin());
			//invoice.setCreateDate(BaseController.CURRENT_TIMESTAMP);
			invoice.setIssueDocDate(CURRENT_TIMESTAMP);
			invoice.setPaymentDate(CURRENT_TIMESTAMP);
			invoice.setStatusScan("N");
			invoice.setCutting(Boolean.FALSE);
			
			//receipt set value
			Receipt receipt = new Receipt();
			receipt.setReceiptCode(financialService.genReceiptCode());
			receipt.setAmount(amount);
			receipt.setStatus(messageSource.getMessage("financial.receipt.status.hold", null, LocaleContextHolder.getLocale()));
			//receipt.setIssueDocDate();
			//receipt.setPaymentDate();
			receipt.setDeleted(Boolean.FALSE);
			receipt.setCreatedBy(getUserNameLogin());
			receipt.setCreateDate(CURRENT_TIMESTAMP);
			
			receipt.setInvoice(invoice);
			invoice.setReceipt(receipt);
			try {
				financialService.saveInvoice(invoice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//calculate amount
	public float calculateAmountInvoice(Worksheet worksheet){
		float amount = 0.0f;
		//general worksheet cal product
		for(ProductItem productItem : worksheet.getProductItems()){
			if(productItem.getProductType().equals(TYPE_EQUIMENT)){
				//cal equipment
				for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
					
					if(worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", 
							null, LocaleContextHolder.getLocale()))){
						//ใบงานติดตั้ง
						if((!productItemWorksheet.isFree()) && (!productItemWorksheet.isLend())){
							amount = amount+productItemWorksheet.getAmount();
						}
						
						//ใบงานอื่นๆ
					}else{
						if((!productItemWorksheet.isFree()) && (!productItemWorksheet.isLend())){
							amount = amount+productItemWorksheet.getAmount();
						}
						//คิดค่ามัดจำ
						if(productItemWorksheet.isLend()){
							amount = amount+productItemWorksheet.getDeposit();
						}
					}
				}
			}else if(productItem.getProductType().equals(TYPE_INTERNET_USER)){
				//cal internet
				for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
					if((!productItemWorksheet.isFree()) && (!productItemWorksheet.isLend())){
						amount = amount+productItemWorksheet.getAmount();
					}
				}
			}else if(productItem.getProductType().equals(TYPE_SERVICE)){
				//cal service
				if((!productItem.isFree()) && (!productItem.isLend())){
					amount = amount+productItem.getAmount();
				}
			}
		}
		
		//calculate subworksheet
		if(worksheet.getSubWorksheets() != null){
			for(SubWorksheet subWorksheet : worksheet.getSubWorksheets()){
				amount = amount + subWorksheet.getPrice();
			}
		}
		
		//----------------------------SETTING----------------------------------------//
		if (worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			
			if(worksheet.getServiceApplication() != null){
				if(!worksheet.getServiceApplication().isMonthlyService()){
					//ค่าบริการรายครั้ง
					amount = amount + worksheet.getServiceApplication().getOneServiceFee();
					//ค่าบริการติดตั้ง
					amount = amount + worksheet.getServiceApplication().getInstallationFee();
					//ค่ามันจำอุปกรณ์
					amount = amount + worksheet.getServiceApplication().getDeposit();
					
				}else{
					//ค่าบริการรายเดือน
					amount = amount + worksheet.getServiceApplication().getMonthlyServiceFee();
					//ค่าบริการติดตั้ง
					amount = amount + worksheet.getServiceApplication().getInstallationFee();
					//ค่ามันจำอุปกรณ์
					amount = amount + worksheet.getServiceApplication().getDeposit();
					//รอบบิลแรกลดให้
					amount = amount - worksheet.getServiceApplication().getFirstBillFreeDisCount();
				}
				
			}
		}
		
		//----------------------------ADD POINT----------------------------------------//
		if (worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			//ค่าเพิ่มจุด
			amount = amount + worksheet.getWorksheetAddPoint().getAddPointPrice();
		}
		
		//----------------------------MOVE POINT----------------------------------------//
		if (worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			//ค่าลดจุด
			amount = amount + worksheet.getWorksheetMovePoint().getMovePointPrice();
		}
		
		//----------------------------MOVE----------------------------------------//
		if (worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			//ค่าย้ายสาย
			amount = amount + worksheet.getWorksheetMove().getMoveCablePrice();
		}
		
		logger.info("calculateAmountInvoice amount : " + amount);
		return amount;
	}
	
	public void updateInvoiceWorksheet(Invoice invoice){
		if(invoice.getInvoiceType().equals(messageSource.getMessage("financial.invoice.type.setup", null, LocaleContextHolder.getLocale()))){
			invoice.setAmount(calculateAmountInvoice(invoice.getWorkSheet()));
			invoice.getReceipt().setAmount(calculateAmountInvoice(invoice.getWorkSheet()));
			
		}else if(invoice.getInvoiceType().equals(messageSource.getMessage("financial.invoice.type.repair", null, LocaleContextHolder.getLocale()))){
			invoice.setAmount(calculateAmountInvoice(invoice.getWorkSheet()));
			invoice.getReceipt().setAmount(calculateAmountInvoice(invoice.getWorkSheet()));
		}else if(invoice.getInvoiceType().equals(messageSource.getMessage("financial.invoice.type.order", null, LocaleContextHolder.getLocale()))){
			
		}
		
		try {
			financialService.updateInvoice(invoice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping(value = "loadDepositItem/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadDepositItem(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadDepositItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				ProductItemWorksheet productItemWorksheet = productItemWorksheetService.getProductItemWorksheetById(Long.valueOf(id));
				if(productItemWorksheet != null){
					jsonResponse.setResult(productItemWorksheet.getDeposit());
					jsonResponse.setError(false);
				}else{
					throw new Exception();
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
	
	//----------------------------------------- End method for worksheet create type cable -------------------------------------//
	
	List<ServiceApplicationBean> searchServiceApplicationProcess(String key, String custType, Long customerFeatures){
//		ChangeServiceController changeServiceController = new ChangeServiceController();
//		changeServiceController.setMessageSource(messageSource);
		
		if(customerFeatures == null || customerFeatures <= 0)
			customerFeatures = 0L;
		
		ServiceApplicationListController serviceApplicationListController = new ServiceApplicationListController();
		serviceApplicationListController.setMessageSource(messageSource);
		serviceApplicationListController.setServiceApplicationService(serviceApplicationService);
		serviceApplicationListController.setUnitService(unitService);
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		List<ServiceApplication> serviceApplications = serviceApplicationService.search(key, custType, customerFeatures);
		for(ServiceApplication serviceApplication : serviceApplications){
			ServiceApplicationBean ServiceApplicationBean = serviceApplicationListController.populateEntityToDto(serviceApplication);
			serviceApplicationBeans.add(ServiceApplicationBean);
		}
		
		return serviceApplicationBeans;
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
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	//generate worksheet code
	public String generateWorkSheetCode(){
		String workSheetCode = "";
		try {
			 workSheetCode = workSheetService.genWorkSheetCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return workSheetCode;
	}
	
	public ProductItemWorksheetBean populoateProductItemWorksheet(ProductItemWorksheet productItemWorksheet){
		ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
		productItemWorksheetBean.setId(productItemWorksheet.getId());
		productItemWorksheetBean.setPrice(productItemWorksheet.getPrice());
		productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
		productItemWorksheetBean.setLend(productItemWorksheet.isLend());
		productItemWorksheetBean.setFree(productItemWorksheet.isFree());
		//productItemWorksheetBean.setFeeLend(productItemWorksheet);
		productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
		productItemWorksheetBean.setType(productItemWorksheet.getProductType());
		productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
		productItemWorksheetBean.setProductTypeMatch(productItemWorksheet.getProductTypeMatch());
		productItemWorksheetBean.setParent(productItemWorksheet.getParent());
		productItemWorksheetBean.setEquipmentProductItemBean(populateProductItemIgnoreDeleted(productItemWorksheet.getEquipmentProductItem()));
		return productItemWorksheetBean;
	}
	
	public EquipmentProductItemBean populateProductItemIgnoreDeleted(EquipmentProductItem equipmentProductItem) {
		// product item
		EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();

			equipmentProductItemBean.setId(equipmentProductItem.getId());
			equipmentProductItemBean.setCost(equipmentProductItem.getCost());
			equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
			equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
			equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
			equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());

			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));

			// guarantee date
			equipmentProductItemBean.setGuaranteeDate(null == equipmentProductItem.getGuaranteeDate() ? ""
					: formatDataTh.format(equipmentProductItem.getGuaranteeDate()));
			// check expire guarantee date
			DateUtil dateUtil = new DateUtil();
			equipmentProductItemBean.setExpireGuarantee(dateUtil.ExpireDate(equipmentProductItem.getGuaranteeDate()));

			// order date
			equipmentProductItemBean.setOrderDate(null == equipmentProductItem.getOrderDate() ? ""
					: formatDataTh.format(equipmentProductItem.getOrderDate()));

			SimpleDateFormat formatDateAndTimeTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			// import system datetime
			equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate() ? ""
					: formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));

			// status
			ProductUtil productUtil = new ProductUtil();
			productUtil.setMessageSource(messageSource);
			equipmentProductItemBean.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));

			// equipment product item
			EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
			equipmentProductBean.setId(equipmentProductItem.getEquipmentProduct().getId());
			equipmentProductBean.setProductCode(equipmentProductItem.getEquipmentProduct().getProductCode());
			equipmentProductBean.setProductName(equipmentProductItem.getEquipmentProduct().getProductName());
			equipmentProductBean.setEquipmentTypeName(equipmentProductItem.getEquipmentProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
			// unit
			UnitBean unitBean = new UnitBean();
			unitBean.setId(equipmentProductItem.getEquipmentProduct().getUnit().getId());
			unitBean.setUnitName(equipmentProductItem.getEquipmentProduct().getUnit().getUnitName());
			equipmentProductBean.setUnit(unitBean);
			equipmentProductItemBean.setEquipmentProductBean(equipmentProductBean);
		

		return equipmentProductItemBean;
	}

	public EquipmentProductItemBean populateProductItem(EquipmentProductItem equipmentProductItem){
			// product item
			EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
			
			if(!equipmentProductItem.isDeleted()){	
			equipmentProductItemBean.setId(equipmentProductItem.getId());
			equipmentProductItemBean.setCost(equipmentProductItem.getCost());
			equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
			equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
			equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
			equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
			
			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			
			// guarantee date
			equipmentProductItemBean.setGuaranteeDate(null == equipmentProductItem.getGuaranteeDate() ? ""
					: formatDataTh.format(equipmentProductItem.getGuaranteeDate()));
			//check expire guarantee date
			DateUtil dateUtil = new DateUtil();
			equipmentProductItemBean.setExpireGuarantee(dateUtil.ExpireDate(equipmentProductItem.getGuaranteeDate()));
			
			//order date
			equipmentProductItemBean.setOrderDate(null == equipmentProductItem.getOrderDate() ? ""
					: formatDataTh.format(equipmentProductItem.getOrderDate()));
			
			SimpleDateFormat formatDateAndTimeTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			//import system datetime
			equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate() ? ""
					: formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));
			
			//status
			ProductUtil productUtil = new ProductUtil();
			productUtil.setMessageSource(messageSource);
			equipmentProductItemBean.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));
			
			//equipment product item
			EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
			equipmentProductBean.setId(equipmentProductItem.getEquipmentProduct().getId());
			equipmentProductBean.setProductCode(equipmentProductItem.getEquipmentProduct().getProductCode());
			equipmentProductBean.setProductName(equipmentProductItem.getEquipmentProduct().getProductName());
			//unit 
			UnitBean unitBean = new UnitBean();
			unitBean.setId(equipmentProductItem.getEquipmentProduct().getUnit().getId());
			unitBean.setUnitName(equipmentProductItem.getEquipmentProduct().getUnit().getUnitName());
			equipmentProductBean.setUnit(unitBean);
			equipmentProductItemBean.setEquipmentProductBean(equipmentProductBean);
			}
		
		return equipmentProductItemBean;
	}
	
	public CustomerFeatureBean populateEntityToDto(CustomerFeature customerFeature) {
		CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
		if(null != customerFeature){
			customerFeatureBean.setId(customerFeature.getId());
			customerFeatureBean.setCustomerFeatureName(customerFeature.getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(customerFeature.getCustomerFeatureCode());
		}
		return customerFeatureBean;
	}
	
	// load loadAnalyzeProblems worksheet
	@RequestMapping(value = "loadAnalyzeProblems", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadAnalyzeProblems(HttpServletRequest request) {
		logger.info("[method : loadAnalyzeProblems][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				List<MenuReportBean> menuReportBeans = new ArrayList<MenuReportBean>();
				List<MenuReport> menuReports = unitService.findAllMenuReport();
				if(null != menuReports && menuReports.size() > 0){
					for(MenuReport menuReport:menuReports){
						MenuReportBean menuReportBean = populateEntityToDto(menuReport);
						menuReportBeans.add(menuReportBean);
					}
				}
				jsonResponse.setResult(menuReportBeans);
				jsonResponse.setError(false);
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
	
	//saveAnalyzeProblems
	@RequestMapping(value="saveAnalyzeProblems", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveAnalyzeProblems(@RequestBody final AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{ 
				
				Worksheet worksheet = new Worksheet();
				worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.internet.analyzeproblems", null, LocaleContextHolder.getLocale()));
				worksheet.setAvailableDateTime(analyzeProblemsWorksheetBean.getAvailableDateTime());
				worksheet.setRemark(analyzeProblemsWorksheetBean.getRemark());
				worksheet.setDeleted(Boolean.FALSE);
				worksheet.setCreateDate(CURRENT_TIMESTAMP);
				worksheet.setCreatedBy(getUserNameLogin());
				worksheet.setWorkSheetCode(generateWorkSheetCode());
				worksheet.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));
				//service application
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(analyzeProblemsWorksheetBean.getServiceApplication().getId());
				worksheet.setServiceApplication(serviceApplication);
				
				//analyzeProblems worksheet
				WorksheetAnalyzeProblems worksheetAnalyzeProblems = new WorksheetAnalyzeProblems();
				worksheetAnalyzeProblems.setWorkSheet(worksheet);
				MenuReport menuReport = unitService.getMenuReportById(analyzeProblemsWorksheetBean.getMenuReportId());
				worksheetAnalyzeProblems.setMenuReport(menuReport);
				worksheetAnalyzeProblems.setDeleted(Boolean.FALSE);
				worksheetAnalyzeProblems.setCreateDate(CURRENT_TIMESTAMP);
				worksheetAnalyzeProblems.setCreatedBy(getUserNameLogin());
				
				worksheet.setWorksheetAnalyzeProblems(worksheetAnalyzeProblems);
				
				//save worksheet
				Long worksheetId = workSheetService.save(worksheet);
				//save invoice
				createInvoiceReceipt(worksheetId);
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("worksheet.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	//getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public void setServiceApplicationService(ServiceApplicationService serviceApplicationService) {
		this.serviceApplicationService = serviceApplicationService;
	}

	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}

	public void setServiceProductService(ServiceProductService serviceProductService) {
		this.serviceProductService = serviceProductService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setEquipmentProductCategoryService(EquipmentProductCategoryService equipmentProductCategoryService) {
		this.equipmentProductCategoryService = equipmentProductCategoryService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setEquipmentProductService(EquipmentProductService equipmentProductService) {
		this.equipmentProductService = equipmentProductService;
	}

	public void setEquipmentProductItemService(EquipmentProductItemService equipmentProductItemService) {
		this.equipmentProductItemService = equipmentProductItemService;
	}

	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
	}

}
