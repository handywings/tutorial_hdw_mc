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
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.DocumentFileBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.SearchAllProductBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.WorksheetUpdateSnapShotBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetSetup;
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
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/changeservice")
public class ChangeServiceController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ChangeServiceController.class);
	public static final String CONTROLLER_NAME = "changeservice/";
	public static final String CHANGE_PAGE = "change";
	public static final String INDIVIDUAL = "I";
	public static final String CORPORATE = "C";
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	Gson g = new Gson();
	
	//initial service
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageService")
	private ServicePackageService servicePackageService;
	
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
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemWorksheetService")
	private ProductItemWorksheetService productItemWorksheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired
	private MessageSource messageSource;
	
	//end initial service
	
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
		//check permission
		if(isPermission()){
			try{
				//call service
				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();

				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
				List<ServicePackage> servicePackageList = servicePackageService.findAll();
				List<ServicePackageBean> servicePackageBeanList = new ArrayList<ServicePackageBean>();
				if (servicePackageList != null && servicePackageList.size() > 0) {
					ServicePackageController serv = new ServicePackageController();
					serv.setMessageSource(messageSource);
					for(ServicePackage servicePackage:servicePackageList){
						ServicePackageBean servicePackageBean = new ServicePackageBean();
						servicePackageBean.setId(servicePackage.getId());
						servicePackageBean.setPackageName(servicePackage.getPackageName());
						servicePackageBean.setPackageCode(servicePackage.getPackageCode());
						servicePackageBeanList.add(servicePackageBean);
					}
					modelAndView.addObject("servicePackageBeans", servicePackageBeanList);
				}
				
				ServiceApplicationListController serviceAppCon = new ServiceApplicationListController();
				serviceAppCon.setServiceApplicationService(serviceApplicationService);
				serviceAppCon.setMessageSource(messageSource);
				List<ServiceApplicationTypeBean> serviceApplicationTypeBeans = new ArrayList<ServiceApplicationTypeBean>();
				List<ServiceApplicationType> serviceApplicationTypes = serviceApplicationService.findAllServiceApplicationType();
				if(null != serviceApplicationTypes && !serviceApplicationTypes.isEmpty()){
					for(ServiceApplicationType serviceApplicationType:serviceApplicationTypes){
						serviceApplicationTypeBeans.add(serviceAppCon.populateEntityToDto(serviceApplicationType));
					}
				}
				modelAndView.addObject("serviceApplicationTypes", serviceApplicationTypeBeans);
				
				//init paging
				Pagination pagination = createPagination(1, 10, "changeservice",new ApplicationSearchBean());
				modelAndView.addObject("pagination",pagination);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("applicationSearchBean");
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
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
				
				// search service application
				ApplicationSearchBean applicationSearchBean = (ApplicationSearchBean) session
						.getAttribute("applicationSearchBean");
				// set value search service application
				if (applicationSearchBean != null) {
					modelAndView.addObject("applicationSearchBean", applicationSearchBean);
				} else {
					modelAndView.addObject("applicationSearchBean", new ApplicationSearchBean());
				}

				// service package
//				if (applicationSearchBean != null && applicationSearchBean.getServicePackageType() != null && applicationSearchBean.getServicePackageType() > 0) {
//					List<ServicePackage> servicePackages = servicePackageService
//							.findServicePackageByServicePackageTypeId(Long.valueOf(id));
//					if (servicePackages != null) {
//						List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
//						for (ServicePackage servicePackage : servicePackages) {
//							ServicePackageBean servicePackageBean = new ServicePackageBean();
//							servicePackageBean.setId(servicePackage.getId());
//							servicePackageBean.setPackageName(servicePackage.getPackageName());
//							servicePackageBeans.add(servicePackageBean);
//						}
//						modelAndView.addObject("servicePackageBeans", servicePackageBeans);
//					}
//				} else {
//					modelAndView.addObject("servicePackageBeans", new ArrayList<ServicePackageBean>());
//				}
				 
				List<ServicePackage> servicePackageList = servicePackageService.findAll();
				List<ServicePackageBean> servicePackageBeanList = new ArrayList<ServicePackageBean>();
				if (servicePackageList != null && servicePackageList.size() > 0) {
					for(ServicePackage servicePackage:servicePackageList){
						ServicePackageBean servicePackageBean = new ServicePackageBean();
						servicePackageBean.setId(servicePackage.getId());
						servicePackageBean.setPackageName(servicePackage.getPackageName());
						servicePackageBean.setPackageCode(servicePackage.getPackageCode());
						servicePackageBeanList.add(servicePackageBean);
					}
					modelAndView.addObject("servicePackageBeans", servicePackageBeanList);
				}
				
				ServiceApplicationListController serviceAppCon = new ServiceApplicationListController();
				serviceAppCon.setServiceApplicationService(serviceApplicationService);
				serviceAppCon.setMessageSource(messageSource);
				List<ServiceApplicationTypeBean> serviceApplicationTypeBeans = new ArrayList<ServiceApplicationTypeBean>();
				List<ServiceApplicationType> serviceApplicationTypes = serviceApplicationService.findAllServiceApplicationType();
				if(null != serviceApplicationTypes && !serviceApplicationTypes.isEmpty()){
					for(ServiceApplicationType serviceApplicationType:serviceApplicationTypes){
						serviceApplicationTypeBeans.add(serviceAppCon.populateEntityToDto(serviceApplicationType));
					}
				}
				modelAndView.addObject("serviceApplicationTypes", serviceApplicationTypeBeans);
				
				//search process and pagination
				if(applicationSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "changeservice",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "changeservice",new ApplicationSearchBean());
					modelAndView.addObject("pagination", pagination);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "change", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse change(
			@RequestBody final List<ServiceApplicationBean> serviceApplicationBeanList,HttpServletRequest request) {
		logger.info("[method : change][Type : Controller]");
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if(null != serviceApplicationBeanList && serviceApplicationBeanList.size() > 0){
					for(ServiceApplicationBean serviceApplicationBean:serviceApplicationBeanList){
						ServiceApplication serviceApplicationOld = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						if(null != serviceApplicationOld){
						ServiceApplication serviceApplication = new ServiceApplication();
						
						serviceApplication.setRemarkOtherDocuments(serviceApplicationOld.getRemarkOtherDocuments());
						serviceApplication.setServiceApplicationNo(serviceApplicationService.genServiceApplicationCode());
						serviceApplication.setCustomer(serviceApplicationOld.getCustomer());
						serviceApplication.setStatus("A");
						
						serviceApplication.setEasyInstallationDateTime(serviceApplicationOld.getEasyInstallationDateTime());
						
						serviceApplication.setMonthlyService(serviceApplicationOld.isMonthlyService());
						
						if(serviceApplicationBean.isMonthlyService()){
							serviceApplication.setInstallationFee(serviceApplicationOld.getInstallationFee());
							serviceApplication.setDeposit(serviceApplicationOld.getDeposit());
							serviceApplication.setFirstBillFree(serviceApplicationOld.getFirstBillFree());
							serviceApplication.setFirstBillFreeDisCount(serviceApplicationBean.getFirstBillFreeDisCount());
							serviceApplication.setMonthlyServiceFee(serviceApplicationBean.getMonthlyServiceFee());
							serviceApplication.setPerMonth(serviceApplicationBean.getPerMonth());
						}else{
							serviceApplication.setInstallationFee(serviceApplicationOld.getInstallationFee());
							serviceApplication.setDeposit(serviceApplicationOld.getDeposit());
							serviceApplication.setOneServiceFee(serviceApplicationOld.getOneServiceFee());
						}
						
						serviceApplication.setHouseRegistrationDocuments(serviceApplicationOld.isHouseRegistrationDocuments());
						serviceApplication.setIdentityCardDocuments(serviceApplicationOld.isIdentityCardDocuments());
						serviceApplication.setOtherDocuments(serviceApplicationOld.isOtherDocuments());
						
						serviceApplication.setRemark(serviceApplicationOld.getRemark());
						serviceApplication.setStartDate(new Date());
						serviceApplication.setPlateNumber(serviceApplicationOld.getPlateNumber());
						serviceApplication.setLatitude(serviceApplicationOld.getLatitude());
						serviceApplication.setLongitude(serviceApplicationOld.getLongitude());
						
						serviceApplication.setServiceApplicationNo(serviceApplicationService.genServiceApplicationCode());
						ServicePackage servicePackage = servicePackageService.getServicePackageById(serviceApplicationBean.getServicepackage().getId());
						serviceApplication.setServicePackage(servicePackage);
						
						ServiceApplicationType serviceApplicationType = 
								serviceApplicationService.getServiceApplicationTypeById(serviceApplicationBean.getServiceApplicationTypeBean().getId());
						serviceApplication.setServiceApplicationType(serviceApplicationType);
						serviceApplication.setDeleted(Boolean.FALSE);
						serviceApplication.setCreateDate(CURRENT_TIMESTAMP);
						serviceApplication.setCreatedBy(getUserNameLogin());
						
						if(null != serviceApplicationOld){// ยกเลิกการใช้บริการ ใบสมัครเดิม
							serviceApplication.setReferenceServiceApplicationId(serviceApplicationOld.getId());
						}
						
						Long serviceApplicationId = serviceApplicationService.save(serviceApplication);
						ServiceApplication serviceApplicationNew = serviceApplicationService.getServiceApplicationById(serviceApplicationId);
						
						// map address ไปที่ใบสมัครใหม่
						List<Address> addressList = serviceApplicationOld.getAddresses();
						if(null != addressList && addressList.size() > 0){
							for(Address address:addressList){
								address.setServiceApplication(serviceApplicationNew);
								addressService.updateAddress(address);
							}
						}
						
						// map priductItem ไปที่ใบสมัครใหม่
						List<ProductItem> productItemList = serviceApplicationOld.getProductItems();
						if(null != productItemList && productItemList.size() > 0){
							for(ProductItem productItem:productItemList){
								productItem.setServiceApplication(serviceApplicationNew);
								productItemService.update(productItem);
							}
						}
						
						// map Worksheet ไปที่ใบสมัครใหม่
						List<Worksheet> worksheetList = serviceApplicationOld.getWorksheets();
						if(null != worksheetList && worksheetList.size() > 0){
							for(Worksheet worksheet:worksheetList){
								if("C_S".equals(worksheet.getWorkSheetType())){
									if(null != serviceApplicationBean.getOrderBillDate() && !"".equals(serviceApplicationBean.getOrderBillDate())){
										SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
										Date dateOrderBill = formatData.parse(serviceApplicationBean.getOrderBillDate());
										worksheet.setDateOrderBill(dateOrderBill);
									}
								}
								worksheet.setServiceApplication(serviceApplicationNew);
								worksheet.setUpdatedBy(getUserNameLogin());
								worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
								workSheetService.update(worksheet);
							}
						}
						
						// map invoice ไปที่ใบสมัครใหม่
						List<Invoice> invoiceList = serviceApplicationOld.getInvoices();
						if(null != invoiceList && invoiceList.size() > 0){
							for(Invoice invoice:invoiceList){
								invoice.setServiceApplication(serviceApplicationNew);
								invoice.setUpdatedBy(getUserNameLogin());
								invoice.setUpdatedDate(CURRENT_TIMESTAMP);
								financialService.updateInvoice(invoice);
							}
						}
						
						ServiceApplication serviceApplicationO = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						if(null != serviceApplicationO){// ยกเลิกการใช้บริการ ใบสมัครเดิม
							serviceApplicationO.setStatus("I");
							serviceApplicationO.setUpdatedDate(CURRENT_TIMESTAMP);
							serviceApplicationO.setUpdatedBy(getUserNameLogin());
							serviceApplicationService.update(serviceApplicationO);
						}
						
//						Worksheet worksheet = new Worksheet();
//						if(null != serviceApplicationBean.getOrderBillDate() && !"".equals(serviceApplicationBean.getOrderBillDate())){
//							SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//							Date dateOrderBill = formatData.parse(serviceApplicationBean.getOrderBillDate());
//							worksheet.setDateOrderBill(dateOrderBill);
//							worksheet.setDeleted(Boolean.FALSE);
//							worksheet.setStatus("S");
//							worksheet.setRemark("เปลี่ยนบริการ");
//							worksheet.setWorkSheetCode(workSheetService.genWorkSheetCode());
//							worksheet.setWorkSheetType("C_S");
//							worksheet.setServiceApplication(serviceApplicationNew);
//							worksheet.setCreateDate(CURRENT_TIMESTAMP);
//							worksheet.setCreatedBy(getUserNameLogin());
//							workSheetService.save(worksheet);
//						}
						
						}
						
					}
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
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchServiceApplication(
			@ModelAttribute("applicationSearchBean") ApplicationSearchBean applicationSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchServiceApplication][Type : Controller]");
		logger.info("[method : searchServiceApplication][applicationSearchBean : "+ applicationSearchBean.toString() +"]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(applicationSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/changeservice/page/1/itemPerPage/10");
		return modelAndView;
	}
	
	// view service application detail
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
			ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(id);
			if(serviceApplication != null){
				ServiceApplicationListController serviceApplicationListController = new ServiceApplicationListController();
				serviceApplicationListController.setServiceApplicationService(serviceApplicationService);
				serviceApplicationListController.setMessageSource(messageSource);
				ServiceApplicationBean serviceApplicationBean = serviceApplicationListController.populateEntityToDto(serviceApplication);
				modelAndView.addObject("serviceApplicationBean", serviceApplicationBean);
			}else{
				//thow redirect page 404
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + CHANGE_PAGE);
		return modelAndView;
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
		List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			ServicePackageController spc = new ServicePackageController();
			spc.setMessageSource(messageSource);
			spc.setProductService(productService);
			spc.setInternetProductService(internetProductService);
			spc.setServiceProductService(serviceProductService);
			spc.setServicePackageService(servicePackageService);
			List<ProductBean> productBeans = spc.searchAllProduct(new SearchAllProductBean());
			modelAndView.addObject("productBeans", productBeans);
			
			//call service
			List<Province> provinces = addressService.findAll();
			if(null != provinces && !provinces.isEmpty()){
				for(Province province:provinces){
					ServiceApplicationController serviceApplicationController = new ServiceApplicationController();
					provinceBeans.add(serviceApplicationController.populateEntityToDto(province));
				}
			}
			
			List<Zone> zones = zoneService.findAll();
			if(null != zones && !zones.isEmpty()){
				ZoneController zoneController = new ZoneController();
				for(Zone zone:zones){
					zoneBeans.add(zoneController.populateEntityToDto(zone));
				}
			}

			// service type
			ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
			servicePackageTypeController.setServicePackageTypeService(servicePackageTypeService);
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
			
			ServiceApplicationListController serviceAppCon = new ServiceApplicationListController();
			serviceAppCon.setServiceApplicationService(serviceApplicationService);
			serviceAppCon.setMessageSource(messageSource);
			
			ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(id);
			if(serviceApplication != null){
				ServiceApplicationBean serviceApplicationBean = serviceAppCon.populateEntityToDto(serviceApplication);
				modelAndView.addObject("serviceApplicationBean", serviceApplicationBean);
			}else{
				//thow redirect page 404
			}
			
			// CustomerFeature
			List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
			List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
			if(null != customerFeatures && !customerFeatures.isEmpty()){
				for(CustomerFeature customerFeature:customerFeatures){
					customerFeatureBeans.add(serviceAppCon.populateEntityToDto(customerFeature));
				}
			}
			modelAndView.addObject("customerFeatures", customerFeatureBeans);
			
			List<ServiceApplicationTypeBean> serviceApplicationTypeBeans = new ArrayList<ServiceApplicationTypeBean>();
			List<ServiceApplicationType> serviceApplicationTypes = serviceApplicationService.findAllServiceApplicationType();
			if(null != serviceApplicationTypes && !serviceApplicationTypes.isEmpty()){
				for(ServiceApplicationType serviceApplicationType:serviceApplicationTypes){
					serviceApplicationTypeBeans.add(serviceAppCon.populateEntityToDto(serviceApplicationType));
				}
			}
			modelAndView.addObject("serviceApplicationTypes", serviceApplicationTypeBeans);
			
			List<CareerBean> careerBeans = new ArrayList<CareerBean>();
			List<Career> careers = customerService.findAllCareer();
			if(null != careers && !careers.isEmpty()){
				for(Career career:careers){
					careerBeans.add(serviceAppCon.populateEntityToDto(career));
				}
			}
			modelAndView.addObject("careers", careerBeans);
			
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

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("provinces", provinceBeans);
		modelAndView.addObject("zones", zoneBeans);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + EDIT);
		return modelAndView;
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse save(@RequestParam("file_map") final MultipartFile file_map,
			@RequestParam("file_house") final MultipartFile file_house,
			@RequestParam("file_identity_card") final MultipartFile file_identity_card,
			@RequestParam("file_other") final MultipartFile file_other,
			@RequestParam("serviceApplicationBean") final String serviceApplicationBeanSrt,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		if(isPermission()){
			try{
				 try {
					 Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
					// Convert JSON string to Object Java
					ServiceApplicationBean serviceApplicationBean = mapper.readValue(serviceApplicationBeanSrt, ServiceApplicationBean.class);

					ServiceApplication serviceApplication = new ServiceApplication();
					ServiceApplication serviceApplicationOld = new ServiceApplication();
					Customer customer = new Customer();
					
					if(null != serviceApplicationBean){
						
						if(null != serviceApplicationBean.getCustomer().getId()){

							customer = customerService.getCustomerById(serviceApplicationBean.getCustomer().getId());
							customer.setSex(serviceApplicationBean.getCustomer().getSex());
							customer.setPrefix(serviceApplicationBean.getCustomer().getPrefix());
							customer.setFirstName(serviceApplicationBean.getCustomer().getFirstName());
							customer.setLastName(serviceApplicationBean.getCustomer().getLastName());
							customer.setCustType(serviceApplicationBean.getCustomer().getCustomerType().getValue());
							customer.setIdentityNumber(serviceApplicationBean.getCustomer().getIdentityNumber());
							
							Career Career = customerService.findCareerById(serviceApplicationBean.getCustomer().getCareerBean().getId());
							customer.setCareer(Career);
							
							CustomerFeature customerFeature = customerService.findCustomerFeatureById(serviceApplicationBean.getCustomer().getCustomerFeatureBean().getId());
							customer.setCustomerFeature(customerFeature);
	
							Contact contact = customer.getContact();
							contact.setMobile(serviceApplicationBean.getCustomer().getContact().getMobile());
							contact.setFax(serviceApplicationBean.getCustomer().getContact().getFax());
							contact.setEmail(serviceApplicationBean.getCustomer().getContact().getEmail());
							contact.setUpdatedDate(CURRENT_TIMESTAMP);
							contact.setUpdatedBy(getUserNameLogin());
							contact.setCustomer(customer);
							
							customer.setContact(contact);
							customer.setUpdatedDate(CURRENT_TIMESTAMP);
							customer.setUpdatedBy(getUserNameLogin());
							
							customer.setDeleted(Boolean.FALSE);
							customer.setActive(Boolean.TRUE);
							
							customerService.update(customer);
						}
						
						serviceApplication.setRemarkOtherDocuments(serviceApplicationBean.getRemarkOtherDocuments());
						serviceApplication.setServiceApplicationNo(serviceApplicationService.genServiceApplicationCode());
						serviceApplication.setCustomer(customer);
						serviceApplication.setStatus(serviceApplicationBean.getStatus().getStringValue());
						
						ServicePackage servicePackage = servicePackageService.getServicePackageById(serviceApplicationBean.getServicepackage().getId());
						serviceApplication.setServicePackage(servicePackage);
						
						serviceApplication.setEasyInstallationDateTime(serviceApplicationBean.getEasyInstallationDateTime());
						
						serviceApplication.setMonthlyService(serviceApplicationBean.isMonthlyService());
						
						if(serviceApplicationBean.isMonthlyService()){
							serviceApplication.setInstallationFee(serviceApplicationBean.getInstallationFee());
							serviceApplication.setDeposit(serviceApplicationBean.getDeposit());
							serviceApplication.setFirstBillFree(serviceApplicationBean.getFirstBillFree());
							serviceApplication.setFirstBillFreeDisCount(serviceApplicationBean.getFirstBillFreeDisCount());
							serviceApplication.setMonthlyServiceFee(serviceApplicationBean.getMonthlyServiceFee());
							serviceApplication.setPerMonth(serviceApplicationBean.getPerMonth());
						}else{
							serviceApplication.setInstallationFee(serviceApplicationBean.getInstallationFee());
							serviceApplication.setDeposit(serviceApplicationBean.getDeposit());
							serviceApplication.setOneServiceFee(serviceApplicationBean.getOneServiceFee());
						}
						
						serviceApplication.setHouseRegistrationDocuments(serviceApplicationBean.isHouseRegistrationDocuments());
						serviceApplication.setIdentityCardDocuments(serviceApplicationBean.isIdentityCardDocuments());
						serviceApplication.setOtherDocuments(serviceApplicationBean.isOtherDocuments());
						
						serviceApplication.setRemark(serviceApplicationBean.getRemark());
						serviceApplication.setStartDate(new Date());
						serviceApplication.setPlateNumber(serviceApplicationBean.getPlateNumber());
						serviceApplication.setLatitude(serviceApplicationBean.getLatitude());
						serviceApplication.setLongitude(serviceApplicationBean.getLongitude());
						
						ServiceApplicationType serviceApplicationType = 
								serviceApplicationService.getServiceApplicationTypeById(serviceApplicationBean.getServiceApplicationTypeBean().getId());
						
						// serviceApplicationType
						serviceApplication.setServiceApplicationType(serviceApplicationType);
						
						serviceApplication.setDeleted(Boolean.FALSE);
						serviceApplication.setCreateDate(CURRENT_TIMESTAMP);
						serviceApplication.setCreatedBy(getUserNameLogin());
						
						serviceApplicationOld = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						if(null != serviceApplicationOld){// ยกเลิกการใช้บริการ ใบสมัครเดิม
							serviceApplication.setReferenceServiceApplicationId(serviceApplicationOld.getId());
						}
						Long serviceApplicationId = serviceApplicationService.save(serviceApplication);
						serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationId);
						
						// ที่อยู่ customer
						for(AddressBean addressBean:serviceApplicationBean.getCustomer().getAddressList()){
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
							
							address.setOverrideAddressId(addressBean.getOverrideAddressId());
							
							address.setServiceApplication(serviceApplication);
							address.setCustomer(customer);
							address.setCreateDate(CURRENT_TIMESTAMP);
							address.setCreatedBy(getUserNameLogin());
							addressService.save(address);
						}						
						
						for(AddressBean addressBean:serviceApplicationBean.getAddressList()){
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
							
							// ข้อมูลรายละเอียดการติดตั้ง จะมี zone ด้วย AddressType = 3,4
							if(addressBean.getZoneBean() != null && null != addressBean.getZoneBean().getId() && ("3".equals(addressBean.getAddressType()) || "4".equals(addressBean.getAddressType()))){
								address.setZone(zoneService.getZoneById(addressBean.getZoneBean().getId()));
							}
							
							address.setOverrideAddressId(addressBean.getOverrideAddressId());
							
							address.setServiceApplication(serviceApplication);
							address.setCreateDate(CURRENT_TIMESTAMP);
							address.setCreatedBy(getUserNameLogin());
							addressService.save(address);
						}
						
						Worksheet worksheet = new Worksheet();
						// เช็คว่าเป็นการ บันทึกและออกใบงาน หรือไม่
						if(WAIT_FOR_PAY.equals(serviceApplicationBean.getStatus().getStringValue())){
							WorksheetSetup worksheetSetup = new WorksheetSetup();
							worksheetSetup.setCreateDate(CURRENT_TIMESTAMP);
							worksheetSetup.setCreatedBy(getUserNameLogin());
							worksheetSetup.setDeleted(Boolean.FALSE);
							worksheetSetup.setWorkSheet(worksheet);
							worksheet.setWorkSheetCode(workSheetService.genWorkSheetCode());
							worksheet.setServiceApplication(serviceApplication);
							worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()));
							worksheet.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));
							worksheet.setWorksheetSetup(worksheetSetup);
							worksheet.setCreateDate(CURRENT_TIMESTAMP);
							worksheet.setCreatedBy(getUserNameLogin());
							worksheet.setDeleted(Boolean.FALSE);
							
						}
						EquipmentProduct equipmentProduct = new EquipmentProduct();
						for(ProductItemBean productItemBean:serviceApplicationBean.getProductitemList()){
							if(productItemBean.isReturnDevice()){// คืนอุปกรณ์
								ProductItem productItem = productItemService.getProductItemById(productItemBean.getId());
								if(null != productItem){
								List<ProductItemWorksheet> productItemWorksheetList= productItem.getProductItemWorksheets();
								if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
									for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
										if(null != productItemWorksheet){
											EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
											if(null != equipmentProductItem){
												int status = 5; // ขายขาด
												equipmentProductItem.setBalance(0);
												if(productItemWorksheet.isLend()){ // ถ้าอุปกรณ์มีสถานะยืมจะกลับเป็นปกติ นอกนั้นจะเป็นขายขาด
													status = 1; // ปกติ 
													equipmentProductItem.setBalance(1);
												}
												equipmentProductItem.setStatus(status);
												equipmentProductItem.setSpare(0);
												equipmentProductItem.setReservations(0);
												equipmentProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
												equipmentProductItem.setUpdatedBy(getUserNameLogin());
												equipmentProductItemService.update(equipmentProductItem);
												
												ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
												pro.setEquipmentProductService(equipmentProductService);
												EquipmentProduct eqmp = equipmentProductItem.getEquipmentProduct();
												// อัพ product ตัวแม่
												if(null != eqmp){
													pro.autoUpdateStatusEquipmentProduct(eqmp);
												}
											}
										}
									}
								}
								}
							}else{
								ProductItem productItem = new ProductItem();
								if(TYPE_EQUIMENT.equals(productItemBean.getType())){
	
									equipmentProduct = equipmentProductService.getEquipmentProductById(productItemBean.getProductId());
									productItem.setEquipmentProduct(equipmentProduct);
	
								}else if(TYPE_INTERNET_USER.equals(productItemBean.getType())){
	
									InternetProduct internetProduct = internetProductService.getInternetProductById(productItemBean.getProductId());
									productItem.setInternetProduct(internetProduct);
									
								}else if(TYPE_SERVICE.equals(productItemBean.getType())){
									
									ServiceProduct serviceProduct = serviceProductService.getServiceProductById(productItemBean.getProductId());
									productItem.setServiceProduct(serviceProduct);
									
								}
								productItem.setProductType(productItemBean.getType());
								productItem.setQuantity(productItemBean.getQuantity());
								productItem.setFree(productItemBean.isFree());
								productItem.setLend(productItemBean.isLend());
								productItem.setAmount(productItemBean.getAmount());
								productItem.setPrice(productItemBean.getPrice());
								
								productItem.setCreateDate(CURRENT_TIMESTAMP);
								productItem.setCreatedBy(getUserNameLogin());
								productItem.setDeleted(Boolean.FALSE);
								
								productItem.setServiceApplication(serviceApplication);
								// เช็คว่าเป็นการ บันทึกและออกใบงาน หรือไม่
								if(WAIT_FOR_PAY.equals(serviceApplicationBean.getStatus().getStringValue())){
									productItem.setWorkSheet(worksheet);
								}
								productItemService.save(productItem);
								
								// insert product_item_worksheet
								if(!TYPE_SERVICE.equals(productItemBean.getType())){
									List<EquipmentProductItem> equipmentProductItems = equipmentProduct.getEquipmentProductItems();
									if(null != equipmentProductItems && equipmentProductItems.size() > 0){
										String serialNo = equipmentProductItems.get(0).getSerialNo();
										if("".equals(serialNo)){
											ProductItemWorksheet productItemWorksheet = new ProductItemWorksheet();
											productItemWorksheet.setProductItem(productItem);
											productItemWorksheet.setProductType(productItemBean.getType());
											productItemWorksheet.setFree(productItemBean.isFree());
											productItemWorksheet.setLend(productItemBean.isLend());
											productItemWorksheet.setAmount(productItemBean.getAmount());
											productItemWorksheet.setPrice(productItemBean.getPrice());
											productItemWorksheet.setQuantity(productItemBean.getQuantity());
											productItemWorksheet.setEquipmentProductItem(equipmentProductItems.get(0));
											productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
											productItemWorksheet.setCreatedBy(getUserNameLogin());
											
											productItemWorksheetService.save(productItemWorksheet);
										}else{
											for(int i=0; i < productItemBean.getQuantity(); i++){
												ProductItemWorksheet productItemWorksheet = new ProductItemWorksheet();
												productItemWorksheet.setProductItem(productItem);
												productItemWorksheet.setProductType(productItemBean.getType());
												productItemWorksheet.setFree(productItemBean.isFree());
												productItemWorksheet.setLend(productItemBean.isLend());
												productItemWorksheet.setAmount(productItemBean.getPrice());
												productItemWorksheet.setPrice(productItemBean.getPrice());
												productItemWorksheet.setQuantity(1);
												ProductItem productItemOld = productItemService.getProductItemById(productItemBean.getId());
												List<ProductItemWorksheet> productItemWorksheetList = productItemOld.getProductItemWorksheets();
												if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
														int size = productItemWorksheetList.size();
														if(productItemWorksheetList.size() > i){
															if(null != productItemWorksheetList.get(i).getId()){
																ProductItemWorksheet proItemW = productItemWorksheetList.get(i);
																if(null != proItemW){
																	EquipmentProductItem equi = proItemW.getEquipmentProductItem();
																	productItemWorksheet.setEquipmentProductItem(equi);
																}
															}
														}
												}
												productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
												productItemWorksheet.setCreatedBy(getUserNameLogin());
												
												productItemWorksheetService.save(productItemWorksheet);
											}
										}
									}else{ // internet_product
										for(int i=0; i < productItemBean.getQuantity(); i++){
											ProductItemWorksheet productItemWorksheet = new ProductItemWorksheet();
											productItemWorksheet.setProductItem(productItem);
											productItemWorksheet.setProductType(productItemBean.getType());
											productItemWorksheet.setFree(productItemBean.isFree());
											productItemWorksheet.setLend(productItemBean.isLend());
											productItemWorksheet.setAmount(productItemBean.getPrice());
											productItemWorksheet.setPrice(productItemBean.getPrice());
											ProductItem productItemOld = productItemService.getProductItemById(productItemBean.getId());
											List<ProductItemWorksheet> productItemWorksheetList = productItemOld.getProductItemWorksheets();
											if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
												for(int j = 0; j < productItemWorksheetList.size(); j++){
													if(productItemWorksheetList.size() > j){
														InternetProductItem inter = productItemWorksheetList.get(j).getInternetProductItem();
														productItemWorksheet.setInternetProductItem(inter);
													}
												}
											}
											productItemWorksheet.setQuantity(1);
											productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
											productItemWorksheet.setCreatedBy(getUserNameLogin());
											
											productItemWorksheetService.save(productItemWorksheet);
										}
									}
								}
							}
						}
						
						serviceApplicationOld = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						if(null != serviceApplicationOld){// ยกเลิกการใช้บริการ ใบสมัครเดิม
							serviceApplication.setReferenceServiceApplicationId(serviceApplicationOld.getId());
							serviceApplicationOld.setStatus("I");
							serviceApplicationOld.setUpdatedDate(CURRENT_TIMESTAMP);
							serviceApplicationOld.setUpdatedBy(getUserNameLogin());
							List<ProductItem> productItemList = serviceApplicationOld.getProductItems(); // ใบสมัครเดิม คืนอุปกรณ์
							if(null != productItemList){
								for(ProductItem productItem:productItemList){
									List<ProductItemWorksheet> productItemWorksheetList = productItem.getProductItemWorksheets();
									if(null != productItemWorksheetList){
										for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
											productItemWorksheet.setEquipmentProductItem(null);
											productItemWorksheet.setInternetProductItem(null);
										}
										productItem.setProductItemWorksheets(productItemWorksheetList);
									}
								}
								serviceApplicationOld.setProductItems(productItemList);
							}
							serviceApplicationService.update(serviceApplicationOld);
						}
						
						jsonResponse.setError(false);
					}
					
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("serviceapplication.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "loadServicePackage/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadServicePackage(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadServicePackage][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				List<ServicePackage> servicePackages = new ArrayList<ServicePackage>();
				if(Long.valueOf(id) <= 0){
					servicePackages = servicePackageService.findAll();
				}else{
					servicePackages = servicePackageService.findServicePackageByServicePackageTypeId(Long.valueOf(id));
				}
				//List<ServicePackage> servicePackages = servicePackageService.findServicePackageByServicePackageTypeId(Long.valueOf(id));
				 if(servicePackages != null){
					 List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
					 for(ServicePackage servicePackage : servicePackages){
						 ServicePackageBean servicePackageBean = new ServicePackageBean();
						 servicePackageBean.setId(servicePackage.getId());
						 servicePackageBean.setPackageName(servicePackage.getPackageName());
						 servicePackageBean.setMonthlyService(servicePackage.isMonthlyService());
						 servicePackageBeans.add(servicePackageBean);
					 }
					 jsonResponse.setError(false);
					 jsonResponse.setResult(servicePackageBeans);
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

	//create search session
	public void generateSearchSession(ApplicationSearchBean applicationSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("applicationSearchBean", applicationSearchBean);
	}
	
	//create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller,ApplicationSearchBean applicationSearchBean){
		long startTime = System.currentTimeMillis();
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		applicationSearchBean.setStatus("A"); // เฉพาะ ใบงานใช้งานปกติ
		pagination.setTotalItem(serviceApplicationService.getChangeserviceCountTotal(applicationSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = serviceApplicationService.getChangeserviceByPage(pagination, applicationSearchBean);
		
		//populate
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : (List<ServiceApplication>)pagination.getDataList()){
			ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
			serviceApplicationBean.setId(serviceApplication.getId());
			serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			
			//customer
			CustomerBean customerBean = new CustomerBean();
			customerBean.setId(serviceApplication.getCustomer().getId());
			customerBean.setSex(serviceApplication.getCustomer().getSex());
			customerBean.setPrefix(serviceApplication.getCustomer().getPrefix());
			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
			customerBean.setIdentityNumber(serviceApplication.getCustomer().getIdentityNumber());
			
			CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
			customerFeatureBean.setId(serviceApplication.getCustomer().getCustomerFeature().getId());
			customerFeatureBean.setCustomerFeatureName(serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureCode());
			customerBean.setCustomerFeatureBean(customerFeatureBean);
			
			CareerBean careerBean = new CareerBean();
			careerBean.setId(serviceApplication.getCustomer().getCareer().getId());
			careerBean.setCareerName(serviceApplication.getCustomer().getCareer().getCareerName());
			careerBean.setCareerCode(serviceApplication.getCustomer().getCareer().getCareerCode());
			customerBean.setCareerBean(careerBean);
			
//			customerBean.setCareer(serviceApplication.getCustomer().getCareer());
			//customer type
			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
			customerTypeBean.setMessageSource(messageSource);
			if (serviceApplication.getCustomer().getCustType().equals(CORPORATE)) {
				customerTypeBean.corPorate();
			} else {
				customerTypeBean.inDividual();
			}
			customerBean.setCustomerType(customerTypeBean);
			
			customerBean.setCustTypeReal(serviceApplication.getCustomer().getCustType());
			// contact
			ContactBean contactBean = new ContactBean();
			contactBean.setId(serviceApplication.getCustomer().getContact().getId());

			if (serviceApplication.getCustomer().getContact().getMobile() != null && (!serviceApplication.getCustomer().getContact().getMobile().isEmpty())) {
				contactBean.setMobile(serviceApplication.getCustomer().getContact().getMobile());
			} else {
				contactBean.setMobile("-");
			}

			if (serviceApplication.getCustomer().getContact().getEmail() != null && (!serviceApplication.getCustomer().getContact().getEmail().isEmpty())) {
				contactBean.setEmail(serviceApplication.getCustomer().getContact().getEmail());
			} else {
				contactBean.setEmail("-");
			}

			if (serviceApplication.getCustomer().getContact().getFax() != null && (!serviceApplication.getCustomer().getContact().getFax().isEmpty())) {
				contactBean.setFax(serviceApplication.getCustomer().getContact().getFax());
			} else {
				contactBean.setFax("-");
			}
			customerBean.setContact(contactBean);
			//set customer
			serviceApplicationBean.setCustomer(customerBean);
			
			// address for service application
			List<AddressBean> addressApplicationBeans = new ArrayList<AddressBean>();
			for (Address address : serviceApplication.getAddresses()) {
				if("3".equals(address.getAddressType()) ||
					"4".equals(address.getAddressType()) ||
					"5".equals(address.getAddressType())){ // เช็คที่อยู่ต้องเป็น type 3,4,5 เท่านั้น เพราะ ที่อยู่จะมีของ cumtomer มาด้วย ที่อยู่ customer type = 1,2
					AddressBean addressBean = new AddressBean();
					addressBean.setMessageSource(messageSource);
					addressBean.setId(address.getId());
					addressBean.setDetail(address.getDetail());
					addressBean.setNo(address.getNo());
					addressBean.setAlley(address.getAlley());
					addressBean.setRoad(address.getRoad());
					addressBean.setRoom(address.getRoom());
					addressBean.setFloor(address.getFloor());
					addressBean.setBuilding(address.getBuilding());
					addressBean.setSection(address.getSection());
					addressBean.setPostcode(address.getPostcode());
					addressBean.setAddressType(address.getAddressType());
					addressBean.setVillage(address.getVillage());
					addressBean.setNearbyPlaces(address.getNearbyPlaces());
					addressBean.setOverrideAddressId(address.getOverrideAddressId());
		
					// user bean
					// province
					if (address.getProvinceModel() != null) {
						ProvinceBean provinceBean = new ProvinceBean();
						provinceBean.setId(address.getProvinceModel().getId());
						provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
						addressBean.setProvinceBean(provinceBean);
					}
		
					if (address.getAmphur() != null) {
						// amphur
						AmphurBean amphurBean = new AmphurBean();
						amphurBean.setId(address.getAmphur().getId());
						amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
						amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
						addressBean.setAmphurBean(amphurBean);
					}
		
					if (address.getDistrictModel() != null) {
						// district
						DistrictBean districtBean = new DistrictBean();
						districtBean.setId(address.getDistrictModel().getId());
						districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
						addressBean.setDistrictBean(districtBean);
					}
		
					addressBean.collectAddress();
					// zone
					ZoneBean zoneBean = new ZoneBean();
					if (address.getZone() != null) {
						zoneBean.setId(address.getZone().getId());
						zoneBean.setZoneName(address.getZone().getZoneName());
						zoneBean.setZoneDetail(address.getZone().getZoneDetail());
					}
					addressBean.setZoneBean(zoneBean);
					
					addressApplicationBeans.add(addressBean);
				}
			}
			serviceApplicationBean.setAddressList(addressApplicationBeans);
			//End address for service application
						
			//status
			StatusBean statusBean = new StatusBean();
			statusBean.setStringValue(serviceApplication.getStatus());
			serviceApplicationBean.setStatus(statusBean);
			//service package
			ServicePackageBean servicePackageBean = new ServicePackageBean();
			servicePackageBean.setId(serviceApplication.getServicePackage().getId());
			servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
			servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
			//package type
			ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
			servicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
			servicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
			servicePackageBean.setServiceType(servicePackageTypeBean);
			serviceApplicationBean.setServicepackage(servicePackageBean);
			
//			List<Worksheet> worksheetList = serviceApplication.getWorksheets();
//			if(null != worksheetList && worksheetList.size() > 0){
//				for(Worksheet worksheet:worksheetList){
//					if("C_S".equals(worksheet.getWorkSheetType())){
//						WorksheetBean worksheetSetup = new WorksheetBean() {
//						};
//						SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type6", null, LocaleContextHolder.getLocale()),new Locale("TH", "th"));
//						worksheetSetup.setDateOrderBillTh(null == worksheet.getDateOrderBill()?"":formatDataTh.format(worksheet.getDateOrderBill()));
//						serviceApplicationBean.setWorksheetSetup(worksheetSetup);
//					}
//					
//				}
//			}
			
			
			//add to list
			serviceApplicationBeans.add(serviceApplicationBean);

		}
		pagination.setDataListBean(serviceApplicationBeans);
		//end populate
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		
		return pagination;
	}
	
	public ServiceApplicationBean populateEntityToDto(ServiceApplication serviceApplication) {
		ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
		//service application 
		serviceApplicationBean.setId(serviceApplication.getId());
		serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
		serviceApplicationBean.setDeposit(serviceApplication.getDeposit());
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		serviceApplicationBean.setCreateDateTh(
				null == serviceApplication.getCreateDate() ? "" : formatDataTh.format(serviceApplication.getCreateDate()));
		serviceApplicationBean.setRemark(serviceApplication.getRemark());
		serviceApplicationBean.setEasyInstallationDateTime(serviceApplication.getEasyInstallationDateTime());
		serviceApplicationBean.setInstallationFee(serviceApplication.getInstallationFee());
		serviceApplicationBean.setDeposit(serviceApplication.getDeposit());
		serviceApplicationBean.setMonthlyService(serviceApplication.isMonthlyService());
		serviceApplicationBean.setPerMonth(serviceApplication.getPerMonth());
		serviceApplicationBean.setMonthlyServiceFee(serviceApplication.getMonthlyServiceFee());
		serviceApplicationBean.setOneServiceFee(serviceApplication.getOneServiceFee());
		serviceApplicationBean.setFirstBillFree(serviceApplication.getFirstBillFree());
		serviceApplicationBean.setFirstBillFreeDisCount(serviceApplication.getFirstBillFreeDisCount());
		serviceApplicationBean.setHouseRegistrationDocuments(serviceApplication.isHouseRegistrationDocuments());
		serviceApplicationBean.setIdentityCardDocuments(serviceApplication.isIdentityCardDocuments());
		serviceApplicationBean.setOtherDocuments(serviceApplication.isOtherDocuments());
		StatusBean status = new StatusBean();
		status.setStringValue(serviceApplication.getStatus());
		serviceApplicationBean.setStatus(status);
		//customer
		CustomerBean customerBean = new CustomerBean();
		customerBean.setId(serviceApplication.getCustomer().getId());
		customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
		customerBean.setLastName(serviceApplication.getCustomer().getLastName());
		customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
		customerBean.setIdentityNumber(serviceApplication.getCustomer().getIdentityNumber());
		//customerBean.setCareer(serviceApplication.getCustomer().getCareer());
		// customer type
		CustomerTypeBean customerTypeBean = new CustomerTypeBean();
		customerTypeBean.setMessageSource(messageSource);
		if (serviceApplication.getCustomer().getCustType().equals(CORPORATE)) {
			customerTypeBean.corPorate();
		} else {
			customerTypeBean.inDividual();
		}
		customerBean.setCustomerType(customerTypeBean);
		customerBean.setCustTypeReal(serviceApplication.getCustomer().getCustType());
		// contact
		ContactBean contactBean = new ContactBean();
		contactBean.setId(serviceApplication.getCustomer().getContact().getId());

		if (serviceApplication.getCustomer().getContact().getMobile() != null && (!serviceApplication.getCustomer().getContact().getMobile().isEmpty())) {
			contactBean.setMobile(serviceApplication.getCustomer().getContact().getMobile());
		} else {
			contactBean.setMobile("-");
		}

		if (serviceApplication.getCustomer().getContact().getEmail() != null && (!serviceApplication.getCustomer().getContact().getEmail().isEmpty())) {
			contactBean.setEmail(serviceApplication.getCustomer().getContact().getEmail());
		} else {
			contactBean.setEmail("-");
		}

		if (serviceApplication.getCustomer().getContact().getFax() != null && (!serviceApplication.getCustomer().getContact().getFax().isEmpty())) {
			contactBean.setFax(serviceApplication.getCustomer().getContact().getFax());
		} else {
			contactBean.setFax("-");
		}
		customerBean.setContact(contactBean);
		// address
		List<AddressBean> addressBeans = new ArrayList<AddressBean>();
		for (Address address : serviceApplication.getCustomer().getAddresses()) {
			// เช็คที่อยู่ ลูกค้า ต้องอยู่ใน ใบสมัครนี้ด้วย
			if(null != address.getServiceApplication() &&
					address.getServiceApplication().getId() == serviceApplication.getId()){
				AddressBean addressBean = new AddressBean();
				addressBean.setMessageSource(messageSource);
				addressBean.setId(address.getId());
				addressBean.setDetail(address.getDetail());
				addressBean.setNo(address.getNo());
				addressBean.setAlley(address.getAlley());
				addressBean.setRoad(address.getRoad());
				addressBean.setRoom(address.getRoom());
				addressBean.setFloor(address.getFloor());
				addressBean.setBuilding(address.getBuilding());
				addressBean.setSection(address.getSection());
				addressBean.setPostcode(address.getPostcode());
				addressBean.setAddressType(address.getAddressType());
				addressBean.setVillage(address.getVillage());
				addressBean.setNearbyPlaces(address.getNearbyPlaces());
				addressBean.setOverrideAddressId(address.getOverrideAddressId());
				addressBean.setOverrideAddressType(address.getOverrideAddressType());
				// user bean
				// province
				if (address.getProvinceModel() != null) {
					ProvinceBean provinceBean = new ProvinceBean();
					provinceBean.setId(address.getProvinceModel().getId());
					provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
					addressBean.setProvinceBean(provinceBean);
				}
	
				if (address.getAmphur() != null) {
					// amphur
					AmphurBean amphurBean = new AmphurBean();
					amphurBean.setId(address.getAmphur().getId());
					amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
					amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
					addressBean.setAmphurBean(amphurBean);
				}
	
				if (address.getDistrictModel() != null) {
					// district
					DistrictBean districtBean = new DistrictBean();
					districtBean.setId(address.getDistrictModel().getId());
					districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
					addressBean.setDistrictBean(districtBean);
				}
	
				addressBean.collectAddress();
				addressBeans.add(addressBean);
			}
		}
		customerBean.setAddressList(addressBeans);
		//set customer
		serviceApplicationBean.setCustomer(customerBean);
		
		// address for service application
		List<AddressBean> addressApplicationBeans = new ArrayList<AddressBean>();
		for (Address address : serviceApplication.getAddresses()) {
			if("3".equals(address.getAddressType()) ||
				"4".equals(address.getAddressType()) ||
				"5".equals(address.getAddressType())){ // เช็คที่อยู่ต้องเป็น type 3,4,5 เท่านั้น เพราะ ที่อยู่จะมีของ cumtomer มาด้วย ที่อยู่ customer type = 1,2
				AddressBean addressBean = new AddressBean();
				addressBean.setMessageSource(messageSource);
				addressBean.setId(address.getId());
				addressBean.setDetail(address.getDetail());
				addressBean.setNo(address.getNo());
				addressBean.setAlley(address.getAlley());
				addressBean.setRoad(address.getRoad());
				addressBean.setRoom(address.getRoom());
				addressBean.setFloor(address.getFloor());
				addressBean.setBuilding(address.getBuilding());
				addressBean.setSection(address.getSection());
				addressBean.setPostcode(address.getPostcode());
				addressBean.setAddressType(address.getAddressType());
				addressBean.setVillage(address.getVillage());
				addressBean.setNearbyPlaces(address.getNearbyPlaces());
				addressBean.setOverrideAddressId(address.getOverrideAddressId());
	
				// user bean
				// province
				if (address.getProvinceModel() != null) {
					ProvinceBean provinceBean = new ProvinceBean();
					provinceBean.setId(address.getProvinceModel().getId());
					provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
					addressBean.setProvinceBean(provinceBean);
				}
	
				if (address.getAmphur() != null) {
					// amphur
					AmphurBean amphurBean = new AmphurBean();
					amphurBean.setId(address.getAmphur().getId());
					amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
					amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
					addressBean.setAmphurBean(amphurBean);
				}
	
				if (address.getDistrictModel() != null) {
					// district
					DistrictBean districtBean = new DistrictBean();
					districtBean.setId(address.getDistrictModel().getId());
					districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
					addressBean.setDistrictBean(districtBean);
				}
	
				addressBean.collectAddress();
				//zone
				if(addressBean.getAddressType().equals("3")){
					ZoneBean zoneBean = new ZoneBean();
					if(address.getZone() != null){
						zoneBean.setId(address.getZone().getId());
						zoneBean.setZoneName(address.getZone().getZoneName());
						zoneBean.setZoneDetail(address.getZone().getZoneDetail());
					}
					addressBean.setZoneBean(zoneBean);
				}
				addressApplicationBeans.add(addressBean);
			}
		}
		serviceApplicationBean.setAddressList(addressApplicationBeans);
		//End address for service application
		
		//product list
		List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
		for (ProductItem productItem : serviceApplication.getProductItems()) {
			ProductItemBean productItemBean = new ProductItemBean();
			productItemBean.setId(productItem.getId());
			productItemBean.setType(productItem.getProductType());
			productItemBean.setQuantity(productItem.getQuantity());
			productItemBean.setFree(productItem.isFree());
			productItemBean.setLend(productItem.isLend());
			productItemBean.setAmount(productItem.getAmount());
			productItemBean.setPrice(productItem.getPrice());

			// case equipment product
			if (productItem.getProductType().equals(TYPE_EQUIMENT)) {
				ProductAddController productAddController = new ProductAddController();
				productAddController.setMessageSource(messageSource);
				EquipmentProductBean equipmentProductBean = productAddController
						.populateEntityToDto(productItem.getEquipmentProduct());
				productItemBean.setProduct(equipmentProductBean);
				productItemBean.getProduct().setTypeEquipment();

				// case internet product
			} else if (productItem.getProductType().equals(TYPE_INTERNET_USER)) {
				ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
				interProduct.setMessageSource(messageSource);
				InternetProductBean internetProductBean = interProduct
						.populateEntityToDto(productItem.getInternetProduct());
				productItemBean.setProduct(internetProductBean);
				productItemBean.getProduct().setTypeInternet();

				// case service product
			} else if (productItem.getProductType().equals(TYPE_SERVICE)) {
				ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
				serviceProduct.setMessageSource(messageSource);
				ServiceProductBean serviceProductBean = serviceProduct
						.populateEntityToDto(productItem.getServiceProduct());
				productItemBean.setProduct(serviceProductBean);
				productItemBean.getProduct().setTypeService();

			}
			
			// set data product_item_worksheet
			List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
			List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
			if(null != productItemWorksheets && productItemWorksheets.size() > 0){
				for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
					ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
					productItemWorksheetBean.setId(productItemWorksheet.getId());
					productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
					productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
					productItemWorksheetBean.setFree(productItemWorksheet.isFree());
					productItemWorksheetBean.setLend(productItemWorksheet.isLend());
					if(TYPE_EQUIMENT.equals(productItemWorksheet.getProductType())){
						EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
						if(null != equipmentProductItem){
							EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
							equipmentProductItemBean.setId(equipmentProductItem.getId());
							equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
							
							productItemWorksheetBean.setEquipmentProductItemBean(equipmentProductItemBean);
						}
					}else if(TYPE_INTERNET_USER.equals(productItemWorksheet.getProductType())){
						InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
						if(null != internetProductItem){
							InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
							internetProductBeanItem.setId(internetProductItem.getId());
							internetProductBeanItem.setUserName(internetProductItem.getUserName());
							internetProductBeanItem.setPassword(internetProductItem.getPassword());
							
							productItemWorksheetBean.setInternetProductBeanItem(internetProductBeanItem);
						}
					}
					
					productItemWorksheetBeanList.add(productItemWorksheetBean);
				}
			}
			productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList );
			
			productItemBeans.add(productItemBean);

		}
		serviceApplicationBean.setProductitemList(productItemBeans);
		
		//service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(serviceApplication.getServicePackage().getId());
		servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
		servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
		//service package type
		ServicePackageTypeBean ServicePackageTypeBean = new ServicePackageTypeBean();
		ServicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
		ServicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
		servicePackageBean.setServiceType(ServicePackageTypeBean);
		serviceApplicationBean.setServicepackage(servicePackageBean);
		
		//docment
		List<DocumentFileBean> docments = new ArrayList<DocumentFileBean>();
		for(DocumentFile DocumentFile : serviceApplication.getDocumentFiles()){
			DocumentFileBean documentFileBean = new DocumentFileBean();
			documentFileBean.setId(DocumentFile.getId());
			documentFileBean.setFilePath(DocumentFile.getFilePath());
			documentFileBean.setFileName(DocumentFile.getFileName());
			documentFileBean.setFileType(DocumentFile.getFileType());
			docments.add(documentFileBean);
		}
		serviceApplicationBean.setDocuments(docments);
		
		return serviceApplicationBean;
	}
	
	@RequestMapping(value="getProductitem/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getProductitem(@PathVariable String id) {
		logger.info("[method : getProductitem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
		if(isPermission()){
			try{
				List<ProductItem> productItems = productItemService.getProductItemByServiceApplicationId(Long.valueOf(id));
				if(null != productItems && productItems.size() > 0){
					for(ProductItem productItem:productItems){
						ProductItemBean productItemBean = populateEntityToDtoProductIsLend(productItem);
						if(null != productItemBean){
							productItemBeans.add(productItemBean);
						}
					}
					jsonResponse.setResult(productItemBeans);
				}
				jsonResponse.setError(false);
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
	
	public ProductItemBean populateEntityToDtoProductIsLend(ProductItem productItem) {
		ProductItemBean productItemBean = new ProductItemBean();
		if(null != productItem){
			Worksheet worksheet = productItem.getWorkSheet();
			Boolean checkWorksheet = false;
			if(null == worksheet){
				checkWorksheet = true;
			}else{
				String worksheetSetup = worksheet.getWorkSheetType();
				if("C_S".equals(worksheetSetup) || productItem.isLend()){
					checkWorksheet = true;
				}
			}
			if(checkWorksheet){
				productItemBean.setId(productItem.getId());
				productItemBean.setAmount(productItem.getAmount());
				productItemBean.setFree(productItem.isFree());
				productItemBean.setLend(productItem.isLend());
				productItemBean.setPrice(productItem.getPrice());
				productItemBean.setQuantity(productItem.getQuantity());
				productItemBean.setType(productItem.getProductType());
				productItemBean.setSerialNo(Boolean.FALSE);
				
				List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
				
				if (productItem.getEquipmentProduct() != null) {
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					ProductAddController productAddController = new ProductAddController();
					productAddController.setMessageSource(messageSource);
					equipmentProductBean = productAddController
							.populateEntityToDto(productItem.getEquipmentProduct());
					productItemBean.setProduct(equipmentProductBean);
					
					// เช็ค SerialNo
					List<EquipmentProductItemBean> equipmentProductItemBeans = equipmentProductBean.getEquipmentProductItemBeans();
					if(null != equipmentProductItemBeans && equipmentProductItemBeans.size() > 0){
						String serialNo = equipmentProductItemBeans.get(0).getSerialNo();
						if(!"".equals(serialNo)){
							productItemBean.setSerialNo(Boolean.TRUE);
						}
					}
					
					List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
					if(null != productItemWorksheets && productItemWorksheets.size() > 0){
						for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
							ProductItemWorksheetBean bean = new ProductItemWorksheetBean();
							bean.setId(productItemWorksheet.getId());
							bean.setAmount(productItemWorksheet.getAmount());
							bean.setPrice(productItemWorksheet.getPrice());
							bean.setDeposit(productItemWorksheet.getDeposit());
							bean.setFree(productItemWorksheet.isFree());
							bean.setLend(productItemWorksheet.isLend());
							bean.setQuantity(productItemWorksheet.getQuantity());
							
							EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
							if(null != equipmentProductItem){
								EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
								equipmentProductItemBean.setId(equipmentProductItem.getId());
								equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
								equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
								equipmentProductItemBean.setReservations(equipmentProductItem.getReservations());
								equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
								
								bean.setEquipmentProductItemBean(equipmentProductItemBean);
							}
							productItemWorksheetBeanList.add(bean);
						}
					}
					productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList);
	
				} else if (productItem.getInternetProduct() != null) {
					InternetProductBean internetProductBean = new InternetProductBean();
					ProductOrderInternetProductController internetController = new ProductOrderInternetProductController();
					internetController.setMessageSource(messageSource);
					internetProductBean = internetController.populateEntityToDto(productItem.getInternetProduct());
					productItemBean.setProduct(internetProductBean);
					productItemBean.getProduct().unitTypeInternet();
					
					List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
					if(null != productItemWorksheets && productItemWorksheets.size() > 0){
						for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
							ProductItemWorksheetBean bean = new ProductItemWorksheetBean();
							bean.setId(productItemWorksheet.getId());
							
							InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
							if(null != internetProductItem){
								InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
								internetProductBeanItem.setId(internetProductItem.getId());
								internetProductBeanItem.setUserName(internetProductItem.getUserName());
								internetProductBeanItem.setPassword(internetProductItem.getPassword());
								
								bean.setInternetProductBeanItem(internetProductBeanItem);
							}
							productItemWorksheetBeanList.add(bean);
						}
						productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList);
					}
	
				} else if (productItem.getServiceProduct() != null) {
					ServiceProductBean serviceProductBean = new ServiceProductBean();
					ProductOrderServiceProductController serviceController = new ProductOrderServiceProductController();
					serviceController.setMessageSource(messageSource);
					serviceProductBean = serviceController.populateEntityToDto(productItem.getServiceProduct());
					productItemBean.setProduct(serviceProductBean);
	//				productItemBean.getProduct().unitTypeServiceCharge();
				}
				
			}else{
				return null;
			}
		}
		return productItemBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	//getter setter
	public void setServicePackageTypeService(ServicePackageTypeService servicePackageTypeService) {
		this.servicePackageTypeService = servicePackageTypeService;
	}

	public void setServiceApplicationService(ServiceApplicationService serviceApplicationService) {
		this.serviceApplicationService = serviceApplicationService;
	}

	public void setServicePackageService(ServicePackageService servicePackageService) {
		this.servicePackageService = servicePackageService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
