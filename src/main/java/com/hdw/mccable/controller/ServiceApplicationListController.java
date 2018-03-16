package com.hdw.mccable.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.DocumentFileBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.InvoiceDocumentBean;
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
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Company;
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
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.DocumentFileService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.InternetProductItemService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ProductItemWorksheetService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.RequisitionItemService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/serviceapplicationlist")
public class ServiceApplicationListController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ServiceApplicationListController.class);
	public static final String CONTROLLER_NAME = "serviceapplicationlist/";
	public static final String WORKSHEETADD = "worksheetadd";
	
	Gson g = new Gson();
	
	// TEST
//  private static String UPLOADED_FOLDER = "/home/thanarat/space/timesheet/03-60/temp/";
	 private static String UPLOADED_FOLDER = "C:/temp/";
	 
	 public static final String TYPE_EQUIMENT = "E";
	 public static final String TYPE_INTERNET_USER = "I";
	 public static final String TYPE_SERVICE = "S";
	 public static final String WAIT_FOR_PAY = "H"; // H = สถานะแรกคือ "รอจ่ายงาน"
	 public static final String CORPORATE = "C";
	 public static final String FILE_TYPE_IMAGE = "I"; //I = รูป	
	 public static final String FILE_TYPE_HOUSE_REGISTRATION = "H"; // H = สำเนาทะเบียนบ้าน
	 public static final String FILE_TYPE_IDENTITY = "P"; // P = สำเนาบัตรประจำตัวประชาชน
	 public static final String FILE_TYPE_OTHER = "O"; // O เอกสารอื่นๆ
		
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
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "documentFileService")
	private DocumentFileService documentFileService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemWorksheetService")
	private ProductItemWorksheetService productItemWorksheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "internetProductItemService")
	private InternetProductItemService internetProductItemService;

	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "requisitionItemService")
	private RequisitionItemService requisitionItemService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
	private MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");

		ModelAndView modelAndView = new ModelAndView();
		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		servicePackageTypeController.setServicePackageTypeService(servicePackageTypeService);
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
				
				//init paging
				Pagination pagination = createPagination(1, 10, "serviceapplicationlist",new ApplicationSearchBean());
				modelAndView.addObject("pagination",pagination);
				
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				
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
		servicePackageTypeController.setServicePackageTypeService(servicePackageTypeService);
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
				if (applicationSearchBean != null && applicationSearchBean.getServicePackageType() != null && applicationSearchBean.getServicePackageType() > 0) {
					List<ServicePackage> servicePackages = servicePackageService
							.findServicePackageByServicePackageTypeId(Long.valueOf(id));
					if (servicePackages != null) {
						List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
						for (ServicePackage servicePackage : servicePackages) {
							ServicePackageBean servicePackageBean = new ServicePackageBean();
							servicePackageBean.setId(servicePackage.getId());
							servicePackageBean.setPackageName(servicePackage.getPackageName());
							servicePackageBeans.add(servicePackageBean);
						}
						modelAndView.addObject("servicePackageBeans", servicePackageBeans);
					}
				} else {
					modelAndView.addObject("servicePackageBeans", new ArrayList<ServicePackageBean>());
				}
				 
				
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				
				//search process and pagination
				if(applicationSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "serviceapplicationlist",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "serviceapplicationlist",new ApplicationSearchBean());
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
		modelAndView.setViewName(REDIRECT + "/serviceapplicationlist/page/1/itemPerPage/10");
		return modelAndView;
	}
	
	//create search session
	public void generateSearchSession(ApplicationSearchBean applicationSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("applicationSearchBean", applicationSearchBean);
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

	
	//create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller,ApplicationSearchBean applicationSearchBean){
		long startTime = System.currentTimeMillis();
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(serviceApplicationService.getCountTotal(applicationSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = serviceApplicationService.getByPage(pagination, applicationSearchBean);
		
		//populate
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : (List<ServiceApplication>)pagination.getDataList()){
			ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
			
			serviceApplicationBean = populateEntityToDtoForList(serviceApplication);
			
//			serviceApplicationBean.setId(serviceApplication.getId());
//			serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
//			//customer
//			CustomerBean customerBean = new CustomerBean();
//			customerBean.setId(serviceApplication.getCustomer().getId());
//			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
//			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
//			serviceApplicationBean.setCustomer(customerBean);
//			//status
//			StatusBean statusBean = new StatusBean();
//			statusBean.setStringValue(serviceApplication.getStatus());
//			serviceApplicationBean.setStatus(statusBean);
//			//service package
//			ServicePackageBean servicePackageBean = new ServicePackageBean();
//			servicePackageBean.setId(serviceApplication.getServicePackage().getId());
//			servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
//			servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
//			//package type
//			ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
//			servicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
//			servicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
//			servicePackageBean.setServiceType(servicePackageTypeBean);
//			serviceApplicationBean.setServicepackage(servicePackageBean);
//			//address
//			List<AddressBean> addressBeans = new ArrayList<AddressBean>();
//			for(Address address : serviceApplication.getAddresses()){
//				AddressBean addressBean = new AddressBean();
//				addressBean.setId(address.getId());
//				addressBean.setAddressType(address.getAddressType());
//				//zone
//				ZoneBean zoneBean = new ZoneBean();
//				if(address.getZone() != null){
//					zoneBean.setId(address.getZone().getId());
//					zoneBean.setZoneName(address.getZone().getZoneName());
//					zoneBean.setZoneDetail(address.getZone().getZoneDetail());
//				} 
//				addressBean.setZoneBean(zoneBean);
//				addressBean.collectAddress();
//				addressBeans.add(addressBean);
//			}
//			serviceApplicationBean.setAddressList(addressBeans);
			
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
				ChangeServiceController changeServiceController = new ChangeServiceController();
				changeServiceController.setMessageSource(messageSource);
				ServiceApplicationBean serviceApplicationBean = populateEntityToDto(serviceApplication);
				modelAndView.addObject("serviceApplicationBean", serviceApplicationBean);
				
				//invoice
				InvoiceController invc = new InvoiceController();
				invc.setMessageSource(messageSource);
				invc.setServiceApplicationService(serviceApplicationService);
				List<InvoiceDocumentBean> invoiceDocumentBeanList = new ArrayList<InvoiceDocumentBean>();
				for(Invoice invoice : serviceApplication.getInvoices()){
					InvoiceDocumentBean invoiceBean = new InvoiceDocumentBean();
					invoiceBean = invc.PoppulateInvoiceEntityToDto(invoice);
					invoiceDocumentBeanList.add(invoiceBean);
				}
				modelAndView.addObject("invoiceDocumentBeanList", invoiceDocumentBeanList);
				
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

		modelAndView.setViewName(CONTROLLER_NAME + VIEW);
		return modelAndView;
	}
	
	// view service application detail
	@RequestMapping(value = "worksheetadd/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView worksheetadd(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
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
			if (serviceApplication != null) {
				ChangeServiceController changeServiceController = new ChangeServiceController();
				changeServiceController.setMessageSource(messageSource);
				ServiceApplicationBean serviceApplicationBean = populateEntityToDto(serviceApplication);
				modelAndView.addObject("serviceApplicationBean", serviceApplicationBean);

				// productItems
				List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
				try {
					List<ProductItem> productItems = productItemService.getProductItemByServiceApplicationId(id);
					if (null != productItems && productItems.size() > 0) {
						for (ProductItem productItem : productItems) {
							productItemBeans.add(populateEntityToDto(productItem));
						}
					}
					modelAndView.addObject("productItemBeans", productItemBeans);

					int equimentSize = 0;
					int internetSize = 0;
					int serviceSize = 0;

					if (null != productItemBeans && productItemBeans.size() > 0) {
						for (ProductItemBean productItemBean : productItemBeans) {
							List<ProductItemWorksheetBean> productItemWorksheetBeans = productItemBean
									.getProductItemWorksheetBeanList();
							if (null != productItemWorksheetBeans && productItemWorksheetBeans.size() > 0) {
								for (ProductItemWorksheetBean productItemWorksheetBean : productItemWorksheetBeans) {
									productItemWorksheetBean.getEquipmentProductItemBean();
									if (TYPE_EQUIMENT.equals(productItemBean.getType())) {
										equimentSize++;
									} else if (TYPE_INTERNET_USER.equals(productItemBean.getType())) {
										internetSize++;
									}
								}
							}
							if (TYPE_SERVICE.equals(productItemBean.getType())) {
								serviceSize++;
							}
						}
					}

					modelAndView.addObject("equimentSize", equimentSize);
					modelAndView.addObject("internetSize", internetSize);
					modelAndView.addObject("serviceSize", serviceSize);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// thow redirect page 404
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + WORKSHEETADD);
		return modelAndView;
	}
		
	// view service application detail
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : view][Type : Controller]");
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
			
			ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(id);
			if(serviceApplication != null){
				ServiceApplicationBean serviceApplicationBean = populateEntityToDto(serviceApplication);
				modelAndView.addObject("serviceApplicationBean", serviceApplicationBean);
			}else{
				//thow redirect page 404
			}
			
			// CustomerFeature
			List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
			List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
			if(null != customerFeatures && !customerFeatures.isEmpty()){
				for(CustomerFeature customerFeature:customerFeatures){
					customerFeatureBeans.add(populateEntityToDto(customerFeature));
				}
			}
			modelAndView.addObject("customerFeatures", customerFeatureBeans);
			
			List<ServiceApplicationTypeBean> serviceApplicationTypeBeans = new ArrayList<ServiceApplicationTypeBean>();
			List<ServiceApplicationType> serviceApplicationTypes = serviceApplicationService.findAllServiceApplicationType();
			if(null != serviceApplicationTypes && !serviceApplicationTypes.isEmpty()){
				for(ServiceApplicationType serviceApplicationType:serviceApplicationTypes){
					serviceApplicationTypeBeans.add(populateEntityToDto(serviceApplicationType));
				}
			}
			modelAndView.addObject("serviceApplicationTypes", serviceApplicationTypeBeans);
			
			List<CareerBean> careerBeans = new ArrayList<CareerBean>();
			List<Career> careers = customerService.findAllCareer();
			if(null != careers && !careers.isEmpty()){
				for(Career career:careers){
					careerBeans.add(populateEntityToDto(career));
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
			
			List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
			List<Company> companys = companyService.findAll();
			if(null != companys && !companys.isEmpty()){
				CompanyController companyController = new CompanyController();
				for(Company company:companys){
					companyBeans.add(companyController.populateEntityToDto(company));
				}
			}
			modelAndView.addObject("companys", companyBeans);
			
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
	
	
	@RequestMapping(value="createworksheetsetup", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse createworksheetsetup(@RequestParam("file_map") final MultipartFile file_map,
			@RequestParam("file_house") final MultipartFile file_house,
			@RequestParam("file_identity_card") final MultipartFile file_identity_card,
			@RequestParam("file_other") final MultipartFile file_other,
			@RequestParam("serviceApplicationBean") final String serviceApplicationBeanSrt,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		Long worksheetId = null;
		
		if(isPermission()){
			//create timestamp
//			
			try{
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
				 try {
					
					// Convert JSON string to Object Java
					ServiceApplicationBean serviceApplicationBean = mapper.readValue(serviceApplicationBeanSrt, ServiceApplicationBean.class);
					ServiceApplication serviceApplication = new ServiceApplication();
					
					if(null != serviceApplicationBean){
						serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						
						// เช็คใบสมัครมีใบงานติดตั้งหรือยัง
						Worksheet worksheet = new Worksheet();
						List<Worksheet> worksheetList = serviceApplication.getWorksheets();
						Boolean checkWorksheetSetup = true;
						if(null != worksheetList && worksheetList.size() > 0){
							for(Worksheet worksheets:worksheetList){
								WorksheetSetup worksheetSetup = worksheets.getWorksheetSetup();
								if(null != worksheetSetup){
									checkWorksheetSetup = false;
									worksheet = worksheets;
									worksheetId = worksheet.getId();
									worksheet.setStatus("W");
									if(null != worksheet.getInvoice()){
									worksheet.getInvoice().setDeleted(Boolean.FALSE);
									worksheet.getInvoice().setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale()));
										if(null != worksheet.getInvoice().getReceipt()){
											worksheet.getInvoice().getReceipt().setDeleted(Boolean.FALSE);
										}
									}
									workSheetService.update(worksheet);
									
									serviceApplication.setStatus(WAIT_FOR_PAY);
									serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
									serviceApplication.setUpdatedBy(getUserNameLogin());
									serviceApplicationService.update(serviceApplication);
									break;
								}
							}
						}
						// เช็คว่าเป็นการ บันทึกและออกใบงาน หรือไม่
						if(WAIT_FOR_PAY.equals(serviceApplicationBean.getStatus().getStringValue()) && checkWorksheetSetup){
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
							
							//save worksheet
							worksheetId = workSheetService.save(worksheet);
//							//save invoice
//							WorkSheetAddController wAdd = new WorkSheetAddController();
//							wAdd.setMessageSource(messageSource);
//							wAdd.setWorkSheetService(workSheetService);
//							wAdd.setFinancialService(financialService);
//							wAdd.createInvoiceReceipt(worksheetId);
							
							serviceApplication.setStatus(WAIT_FOR_PAY);
							
							serviceApplicationService.update(serviceApplication);
						}
						
						for(ProductItemBean productItemBean:serviceApplicationBean.getProductitemList()){
							List<ProductItemWorksheetBean> productItemWorksheetBeans = productItemBean.getProductItemWorksheetBeanList();
							logger.info("productItemWorksheetBeans : "+productItemWorksheetBeans);
							if(null != productItemWorksheetBeans && productItemWorksheetBeans.size() > 0){
								for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeans){
									if("E".equals(productItemWorksheetBean.getType())){
										ProductItemWorksheet productItemWorksheet = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheetBean.getId());
										EquipmentProductItemBean equipmentProductItemBean = productItemWorksheetBean.getEquipmentProductItemBean();
										EquipmentProductItem equipmentProductItem = null;
										if(null != equipmentProductItemBean.getId() && equipmentProductItemBean.getId() != 0){
											equipmentProductItem = equipmentProductItemService.getEquipmentProductItemById(equipmentProductItemBean.getId());
											if(!"".equals(equipmentProductItem.getSerialNo())){// มี SN
												equipmentProductItem.setStatus(STATUS_HOLD);
												RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
												if(null != requisitionItem){
													requisitionItem.setReturnEquipmentProductItem(1);
													requisitionItem.setUpdatedBy(getUserNameLogin());
													requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
													requisitionItemService.update(requisitionItem);
													equipmentProductItem.setSpare(0);
													equipmentProductItem.setBalance(1);
												}else{
													equipmentProductItem.setBalance(0);
												}
												equipmentProductItem.setReservations(1);
											}else{
												RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
												int quantity = productItemWorksheetBean.getQuantity();
												int spare = equipmentProductItem.getSpare();
												int balance = equipmentProductItem.getBalance();
												if(null != requisitionItem){
													int returnEquipmentProductItem = requisitionItem.getReturnEquipmentProductItem();
													returnEquipmentProductItem = returnEquipmentProductItem - quantity;
													if(returnEquipmentProductItem < 0){
														returnEquipmentProductItem = returnEquipmentProductItem * (-1);
													}
													requisitionItem.setReturnEquipmentProductItem(returnEquipmentProductItem);
													requisitionItem.setUpdatedBy(getUserNameLogin());
													requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
													requisitionItemService.update(requisitionItem);

													spare = spare - quantity;
													if(spare < 0){
														spare = spare * (-1);
													}
													equipmentProductItem.setSpare(spare);
												}else{
													balance = balance - quantity;
													if(balance < 0){
														balance = balance * (-1);
													}
													equipmentProductItem.setBalance(balance);
												}
												equipmentProductItem.setReservations(equipmentProductItem.getReservations()+productItemWorksheetBean.getQuantity());
											}
											productItemWorksheet.setEquipmentProductItem(equipmentProductItem);
											equipmentProductItemService.update(equipmentProductItem);
											
											EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
											//autoUpdateStatusEquipmentProduct
											if(null != equipmentProduct){
												ProductOrderEquipmentProductController poepc = new ProductOrderEquipmentProductController();
												poepc.setEquipmentProductService(equipmentProductService);
												poepc.autoUpdateStatusEquipmentProduct(equipmentProduct);
											}
										}
										productItemWorksheet.setQuantity(productItemWorksheetBean.getQuantity());
										productItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
										productItemWorksheet.setFree(productItemWorksheetBean.isFree());
										productItemWorksheet.setLend(productItemWorksheetBean.isLend());
										productItemWorksheet.setAmount(productItemWorksheetBean.getAmount());
										
										productItemWorksheetService.update(productItemWorksheet);
										
										ProductItem productItem = productItemService.getProductItemById(productItemWorksheetBean.getProductItemBean().getId());
										productItem.setQuantity(productItemWorksheetBean.getQuantity());
										productItem.setPrice(productItemWorksheetBean.getPrice());
										productItem.setFree(productItemWorksheetBean.isFree());
										productItem.setLend(productItemWorksheetBean.isLend());
										productItem.setAmount(productItemWorksheetBean.getAmount());
										productItem.setWorkSheet(worksheet);
										
										productItemService.update(productItem);
										
									}
									
									if("I".equals(productItemWorksheetBean.getType())){
										ProductItemWorksheet productItemWorksheet = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheetBean.getId());
										InternetProductBeanItem internetProductBeanItem = productItemWorksheetBean.getInternetProductBeanItem();
										InternetProductItem internetProductItem = null;
										if(null != internetProductBeanItem.getId() && internetProductBeanItem.getId() != 0){
											internetProductItem = internetProductItemService.getInternetProductItemById(internetProductBeanItem.getId());
											internetProductItem.setStatus(Integer.toString(STATUS_HOLD));
											internetProductItemService.update(internetProductItem);
											
											productItemWorksheet.setInternetProductItem(internetProductItem);
											productItemWorksheetService.update(productItemWorksheet);
											
											ProductItem productItem = productItemWorksheet.getProductItem();
											productItem.setWorkSheet(worksheet);
											productItemService.update(productItem);
										}
										
									}
									
									if("S".equals(productItemWorksheetBean.getType())){
										ProductItem productItem = productItemService.getProductItemById(productItemWorksheetBean.getProductItemBean().getId());
										productItem.setQuantity(productItemWorksheetBean.getQuantity());
										productItem.setPrice(productItemWorksheetBean.getPrice());
										productItem.setFree(productItemWorksheetBean.isFree());
										productItem.setAmount(productItemWorksheetBean.getAmount());
										productItem.setWorkSheet(worksheet);
										productItemService.update(productItem);
										
									}
									
								}
							}
							
						}
						
						// เช็คว่าเป็นการ บันทึกและออกใบงาน หรือไม่
						if(WAIT_FOR_PAY.equals(serviceApplicationBean.getStatus().getStringValue()) && checkWorksheetSetup){
							//save invoice
							WorkSheetAddController wAdd = new WorkSheetAddController();
							wAdd.setMessageSource(messageSource);
							wAdd.setWorkSheetService(workSheetService);
							wAdd.setFinancialService(financialService);
							wAdd.createInvoiceReceipt(worksheetId);
						}else if(WAIT_FOR_PAY.equals(serviceApplicationBean.getStatus().getStringValue()) && !checkWorksheetSetup){
							InvoiceController invoiceController = new InvoiceController();
							invoiceController.setMessageSource(messageSource);
							invoiceController.setFinancialService(financialService);
							invoiceController.setWorkSheetService(workSheetService);
							invoiceController.updateAmountInvoiceTypeWorksheet(worksheetId);
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
	
	@RequestMapping(value="update", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse update(@RequestParam("file_map") final MultipartFile file_map,
			@RequestParam("file_house") final MultipartFile file_house,
			@RequestParam("file_identity_card") final MultipartFile file_identity_card,
			@RequestParam("file_other") final MultipartFile file_other,
			@RequestParam("serviceApplicationBean") final String serviceApplicationBeanSrt,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{

				 try {
					
					// Convert JSON string to Object Java
					ServiceApplicationBean serviceApplicationBean = mapper.readValue(serviceApplicationBeanSrt, ServiceApplicationBean.class);

					ServiceApplication serviceApplication = new ServiceApplication();
					
					if(null != serviceApplicationBean){
						serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
						
						Company company = companyService.getCompanyById(serviceApplicationBean.getCompanyBean().getId());
						serviceApplication.setCompany(company);
						
						serviceApplication.setPlateNumber(serviceApplicationBean.getPlateNumber());
						serviceApplication.setRemarkOtherDocuments(serviceApplicationBean.getRemarkOtherDocuments());
//						serviceApplication.setStatus(serviceApplicationBean.getStatus().getStringValue());
						serviceApplication.setEasyInstallationDateTime(serviceApplicationBean.getEasyInstallationDateTime());
						
						serviceApplication.setHouseRegistrationDocuments(serviceApplicationBean.isHouseRegistrationDocuments());
						serviceApplication.setIdentityCardDocuments(serviceApplicationBean.isIdentityCardDocuments());
						serviceApplication.setOtherDocuments(serviceApplicationBean.isOtherDocuments());
						
						serviceApplication.setRemark(serviceApplicationBean.getRemark());
						
						serviceApplication.setDeleted(Boolean.FALSE);
						serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
						serviceApplication.setUpdatedBy(getUserNameLogin());
						
						serviceApplication.setLatitude(serviceApplicationBean.getLatitude());
						serviceApplication.setLongitude(serviceApplicationBean.getLongitude());
						
						serviceApplicationService.update(serviceApplication);

						// delete address by serviceApplicationId
						addressService.deleteByServiceApplicationId(serviceApplication.getId());
						
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
							
							// ข้อมูลรายละเอียดการติดตั้ง จะมี zone ด้วย AddressType = 3
							if(addressBean.getZoneBean() != null && null != addressBean.getZoneBean().getId() && ("3".equals(addressBean.getAddressType()) || "4".equals(addressBean.getAddressType()))){
								Zone zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
								address.setZone(zone);
							}
							
							address.setOverrideAddressId(addressBean.getOverrideAddressId());
							
							address.setServiceApplication(serviceApplication);
							address.setCreateDate(CURRENT_TIMESTAMP);
							address.setCreatedBy(getUserNameLogin());
							addressService.save(address);
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
	
	@RequestMapping(value="autoInsertEquiment", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse autoInsertEquiment(@RequestBody final ServiceApplicationBean serviceApplicationBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			Boolean isResult = false;
			try{
				logger.info("[method : autoInsertEquiment][Type : Controller]");
			if(null != serviceApplicationBean){
				EquipmentProduct equipmentProduct = new EquipmentProduct();
				logger.info("[serviceApplicationBeanId : "+serviceApplicationBean.getId()+"]");
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
				
				// ลบ productItem and productItemWorksheet ทั้งหมดของ serviceApplication
				List<ProductItem> productItems = productItemService.getProductItemByServiceApplicationId(serviceApplicationBean.getId());
				if(null != productItems && productItems.size() > 0){
					for(ProductItem productItem:productItems){
						productItemWorksheetService.deleteByProductItemId(productItem.getId());
					}
				}
				productItemService.deleteByServiceApplicationId(serviceApplicationBean.getId());
				
				for(ProductItemBean productItemBean:serviceApplicationBean.getProductitemList()){
					ProductItem productItem = new ProductItem();
					if(TYPE_EQUIMENT.equals(productItemBean.getType())){

						equipmentProduct = equipmentProductService.getEquipmentProductById(productItemBean.getId());
						productItem.setEquipmentProduct(equipmentProduct);

					}else if(TYPE_INTERNET_USER.equals(productItemBean.getType())){

						InternetProduct internetProduct = internetProductService.getInternetProductById(productItemBean.getId());
						productItem.setInternetProduct(internetProduct);
						
					}else if(TYPE_SERVICE.equals(productItemBean.getType())){
						
						ServiceProduct serviceProduct = serviceProductService.getServiceProductById(productItemBean.getId());
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

					productItemService.save(productItem);
					
					// insert product_item_worksheet
					if(!TYPE_SERVICE.equals(productItemBean.getType())){
						List<EquipmentProductItem> equipmentProductItems = equipmentProduct.getEquipmentProductItems();
						if(null != equipmentProductItems && equipmentProductItems.size() > 0){
							String serialNo = equipmentProductItems.get(0).getSerialNo();
							if("".equals(serialNo)){
								int total = 0, quantity = 0;
								List<EquipmentProductItem> equipmentProductItemList = equipmentProduct.getEquipmentProductItems();
								if(null != equipmentProductItemList && equipmentProductItemList.size() > 0){
									for(EquipmentProductItem equipmentProductItem:equipmentProductItemList){
										total += equipmentProductItem.getBalance();
										total += equipmentProductItem.getSpare();
									}
								}
								quantity = productItemBean.getQuantity();
								if(quantity > total){
									quantity = total;
									isResult = true;
								}
								
								ProductItemWorksheet productItemWorksheet = new ProductItemWorksheet();
								productItemWorksheet.setProductItem(productItem);
								productItemWorksheet.setProductType(productItemBean.getType());
								productItemWorksheet.setFree(productItemBean.isFree());
								productItemWorksheet.setLend(productItemBean.isLend());
								productItemWorksheet.setAmount(productItemBean.getAmount());
								productItemWorksheet.setPrice(productItemBean.getPrice());
								productItemWorksheet.setQuantity(quantity);
								productItemWorksheet.setEquipmentProductItem(equipmentProductItems.get(0));
								productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								productItemWorksheet.setCreatedBy(getUserNameLogin());
								
								productItemWorksheetService.save(productItemWorksheet);
								
								ProductItem pro = productItemWorksheet.getProductItem();
								if(null != pro){
									pro.setQuantity(quantity);
									productItemService.update(pro);
								}
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
									
									productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
									productItemWorksheet.setCreatedBy(getUserNameLogin());
									
									productItemWorksheetService.save(productItemWorksheet);
								}
							}
						}
					}
				}
			}
				jsonResponse.setResult(isResult);
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
						ProductItemBean productItemBean = populateEntityToDto(productItem);
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
	
	public ProductItemBean populateEntityToDto(ProductItem productItem) {
		ProductItemBean productItemBean = new ProductItemBean();
		if(null != productItem){
			Worksheet worksheet = productItem.getWorkSheet();
			Boolean checkWorksheet = false;
			if(null == worksheet){
				checkWorksheet = true;
			}else{
				String worksheetSetup = worksheet.getWorkSheetType();
				if("C_S".equals(worksheetSetup)){
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
	
	public ServiceApplicationBean populateEntityToDtoForList(ServiceApplication serviceApplication) {
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
		
		String easyInstallationDate = serviceApplication.getEasyInstallationDateTime();
		if(null != easyInstallationDate && !"".equals(easyInstallationDate)){
			String easyInstallationDateTime[] = serviceApplication.getEasyInstallationDateTime().split(" - ");
			if(easyInstallationDateTime.length > 1){
			serviceApplicationBean.setStartDate(easyInstallationDateTime[0]);
			serviceApplicationBean.setEndDate(easyInstallationDateTime[1]);
			}
		}
		
		serviceApplicationBean.setPlateNumber(serviceApplication.getPlateNumber());
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
		serviceApplicationBean.setRemarkOtherDocuments(serviceApplication.getRemarkOtherDocuments());
		serviceApplicationBean.setLatitude(serviceApplication.getLatitude());
		serviceApplicationBean.setLongitude(serviceApplication.getLongitude());
		
		StatusBean status = new StatusBean();
		status.setStringValue(serviceApplication.getStatus());
		serviceApplicationBean.setStatus(status);
		
		//cancel date
		serviceApplicationBean.setCancelServiceDate(
				null == serviceApplication.getCancelServiceDate() ? "" : formatDataTh.format(serviceApplication.getCancelServiceDate()));
		//refund date
		serviceApplicationBean.setRefundDate(
				null == serviceApplication.getRefundDate() ? "" : formatDataTh.format(serviceApplication.getRefundDate()));
		
		//refund money
		if(serviceApplication.isFlagRefund()){
			serviceApplicationBean.setRefund(serviceApplication.getRefund());
		}else{
			serviceApplicationBean.setRefund(0);
		}
		
		
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		serviceApplicationTypeBean.setId(serviceApplication.getServiceApplicationType().getId());
		serviceApplicationTypeBean.setServiceApplicationTypeName(serviceApplication.getServiceApplicationType().getServiceApplicationTypeName());
		serviceApplicationTypeBean.setServiceApplicationTypeCode(serviceApplication.getServiceApplicationType().getServiceApplicationTypeCode());
		serviceApplicationBean.setServiceApplicationTypeBean(serviceApplicationTypeBean);
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
		
//		customerBean.setCareer(serviceApplication.getCustomer().getCareer());
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
		
		//service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(serviceApplication.getServicePackage().getId());
		servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
		servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
		servicePackageBean.setMonthlyService(serviceApplication.getServicePackage().isMonthlyService());
		servicePackageBean.setMonthlyServiceFee(serviceApplication.getServicePackage().getMonthlyServiceFee());
		servicePackageBean.setOneServiceFee(serviceApplication.getServicePackage().getOneServiceFee());
		servicePackageBean.setPerMounth(serviceApplication.getServicePackage().getPerMounth());
		
		//service package type
		ServicePackageTypeBean ServicePackageTypeBean = new ServicePackageTypeBean();
		ServicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
		ServicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
		servicePackageBean.setServiceType(ServicePackageTypeBean);
		//company
		CompanyBean companyBean = new CompanyBean();
		companyBean.setId(serviceApplication.getServicePackage().getCompany().getId());
		companyBean.setVat(serviceApplication.getServicePackage().getCompany().getVat());
		servicePackageBean.setCompany(companyBean);
		
		serviceApplicationBean.setServicepackage(servicePackageBean);
		
		//worksheet list
		List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
		AssignWorksheetController assWorksheetController = new AssignWorksheetController();
		assWorksheetController.setMessageSource(messageSource);
		
//		for(Worksheet worksheet : serviceApplication.getWorksheets()){
//			WorksheetBean worksheetBean = assWorksheetController.populateEntityToDto(worksheet);
//			
//			//current date assing 
//			HistoryTechnicianGroupWorkBean hgw = new HistoryTechnicianGroupWorkBean();
//			int hisSize = worksheetBean.getHistoryTechnicianGroupWorkBeans().size();
//			if(hisSize > 0){
//				hgw = worksheetBean.getHistoryTechnicianGroupWorkBeans().get(hisSize - 1);
//				
//				worksheetBean.setCurrentDateAssignText((null == hgw.getAssingCurrentDate() ? ""
//						: formatDataTh.format(hgw.getAssingCurrentDate())));
//			}
//			worksheetBeanList.add(worksheetBean);
//			
//			WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
//			if(null != worksheetSetup){
//				String stringValue = worksheetSetup.getWorkSheet().getStatus();
//				if("S".equals(stringValue)){
//					serviceApplicationBean.setServiceDate((null == hgw.getAssingCurrentDate() ? ""
//						: formatDataTh.format(hgw.getAssingCurrentDate())));
//				}
//			}
//			
//		}
		
		//date current invoice create
		Date dateCurrentInvoiceCreate = null;
		if(serviceApplication.getInvoices().size() > 0){
			dateCurrentInvoiceCreate = serviceApplication.getInvoices().get(serviceApplication.getInvoices().size()-1).getCreateDate();
		}
		
		SimpleDateFormat dataCreateTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		serviceApplicationBean.setCurrentCreateInvoice(null == dateCurrentInvoiceCreate ? "" : dataCreateTh.format(dateCurrentInvoiceCreate));
		
		serviceApplicationBean.setWorksheetBeanList(worksheetBeanList);
		
		return serviceApplicationBean;
	}
	
	public ServiceApplicationBean populateEntityToDto(ServiceApplication serviceApplication) {
		ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
		
		// ใบสมัครเดิม
		ServiceApplicationBean referenceServiceApplicationBean = new ServiceApplicationBean();
		Long referenceServiceApplicationId = serviceApplication.getReferenceServiceApplicationId();
		if(null != referenceServiceApplicationId && null != serviceApplicationService){
			ServiceApplication referenceServiceApplication = serviceApplicationService.getServiceApplicationById(referenceServiceApplicationId);
			if(null != referenceServiceApplication){
			referenceServiceApplicationBean.setId(referenceServiceApplication.getId());
			referenceServiceApplicationBean.setServiceApplicationNo(referenceServiceApplication.getServiceApplicationNo());
			serviceApplicationBean.setReferenceServiceApplicationBean(referenceServiceApplicationBean);
			}
		}
		
		
		//service application 
		serviceApplicationBean.setId(serviceApplication.getId());
		serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
		serviceApplicationBean.setDeposit(serviceApplication.getDeposit());
		serviceApplicationBean.setCreateBy(serviceApplication.getCreatedBy());
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		serviceApplicationBean.setCreateDateTh(
				null == serviceApplication.getCreateDate() ? "" : formatDataTh.format(serviceApplication.getCreateDate()));
		serviceApplicationBean.setRemark(serviceApplication.getRemark());
		serviceApplicationBean.setEasyInstallationDateTime(serviceApplication.getEasyInstallationDateTime());
		
		String easyInstallationDate = serviceApplication.getEasyInstallationDateTime();
		if(null != easyInstallationDate && !"".equals(easyInstallationDate)){
			String easyInstallationDateTime[] = serviceApplication.getEasyInstallationDateTime().split(" - ");
			if(easyInstallationDateTime.length > 1){
			serviceApplicationBean.setStartDate(easyInstallationDateTime[0]);
			serviceApplicationBean.setEndDate(easyInstallationDateTime[1]);
			}
		}
		
		serviceApplicationBean.setPlateNumber(serviceApplication.getPlateNumber());
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
		serviceApplicationBean.setRemarkOtherDocuments(serviceApplication.getRemarkOtherDocuments());
		serviceApplicationBean.setLatitude(StringUtils.isBlank(serviceApplication.getLatitude())?"13.555549433427485":serviceApplication.getLatitude());
		serviceApplicationBean.setLongitude(StringUtils.isBlank(serviceApplication.getLongitude())?"100.27415315828375":serviceApplication.getLongitude());
		
		Company company = serviceApplication.getCompany();
		if(null != company){
			CompanyBean companyBean = new CompanyBean();
			companyBean.setId(company.getId());
			companyBean.setCompanyName(company.getCompanyName());
			serviceApplicationBean.setCompanyBean(companyBean);
		}
		
		StatusBean status = new StatusBean();
		status.setStringValue(serviceApplication.getStatus());
		serviceApplicationBean.setStatus(status);
		
		//cancel date
		serviceApplicationBean.setCancelServiceDate(
				null == serviceApplication.getCancelServiceDate() ? "" : formatDataTh.format(serviceApplication.getCancelServiceDate()));
		//refund date
		serviceApplicationBean.setRefundDate(
				null == serviceApplication.getRefundDate() ? "" : formatDataTh.format(serviceApplication.getRefundDate()));
		
		//refund money
		if(serviceApplication.isFlagRefund()){
			serviceApplicationBean.setRefund(serviceApplication.getRefund());
		}else{
			serviceApplicationBean.setRefund(0);
		}
		
		
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		serviceApplicationTypeBean.setId(serviceApplication.getServiceApplicationType().getId());
		serviceApplicationTypeBean.setServiceApplicationTypeName(serviceApplication.getServiceApplicationType().getServiceApplicationTypeName());
		serviceApplicationTypeBean.setServiceApplicationTypeCode(serviceApplication.getServiceApplicationType().getServiceApplicationTypeCode());
		serviceApplicationBean.setServiceApplicationTypeBean(serviceApplicationTypeBean);
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
		
//		customerBean.setCareer(serviceApplication.getCustomer().getCareer());
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
		// address
		List<AddressBean> addressBeans = new ArrayList<AddressBean>();
		for (Address address : serviceApplication.getCustomer().getAddresses()) {
			// เช็คที่อยู่ ลูกค้า ต้องอยู่ใน ใบสมัครนี้ด้วย
			if(null != address ||
					null != address.getServiceApplication() ||
					null != address.getServiceApplication().getId() ||
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
		
		//product list
		List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
		for (ProductItem productItem : serviceApplication.getProductItems()) {
			Worksheet worksheet = productItem.getWorkSheet();
			Boolean checkWorksheet = true;
			if(null == worksheet){
				checkWorksheet = true;
			}else{
				String worksheetSetup = worksheet.getWorkSheetType();
				if("C_S".equals(worksheetSetup)){
					checkWorksheet = true;
				}
			}
			if(checkWorksheet){
			ProductItemBean productItemBean = new ProductItemBean();
			productItemBean.setId(productItem.getId());
			productItemBean.setType(productItem.getProductType());
			productItemBean.setQuantity(productItem.getQuantity());
			productItemBean.setFree(productItem.isFree());
			productItemBean.setLend(productItem.isLend());
			productItemBean.setAmount(productItem.getAmount());
			productItemBean.setPrice(productItem.getPrice());
			
			productItemBean.setInstallDigital(
					null == productItem.getInstallDigital() ? "" : formatDataTh.format(productItem.getInstallDigital()));

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
					productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
					productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
					productItemWorksheetBean.setPrice(productItemWorksheet.getPrice());
					if(TYPE_EQUIMENT.equals(productItemWorksheet.getProductType())){
						EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
						if(null != equipmentProductItem){
							EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
							equipmentProductItemBean.setId(equipmentProductItem.getId());
							equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
							equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
							equipmentProductItemBean.setReservations(equipmentProductItem.getReservations());
							equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
							
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
			//check worksheet setup and null
		}
		serviceApplicationBean.setProductitemList(productItemBeans);
		
		//service package
		ServicePackageBean servicePackageBean = new ServicePackageBean();
		servicePackageBean.setId(serviceApplication.getServicePackage().getId());
		servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
		servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
		servicePackageBean.setMonthlyService(serviceApplication.getServicePackage().isMonthlyService());
		servicePackageBean.setMonthlyServiceFee(serviceApplication.getServicePackage().getMonthlyServiceFee());
		servicePackageBean.setOneServiceFee(serviceApplication.getServicePackage().getOneServiceFee());
		servicePackageBean.setPerMounth(serviceApplication.getServicePackage().getPerMounth());
		
		//service package type
		ServicePackageTypeBean ServicePackageTypeBean = new ServicePackageTypeBean();
		ServicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
		ServicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
		servicePackageBean.setServiceType(ServicePackageTypeBean);
		//company
		CompanyBean companyBean = new CompanyBean();
		companyBean.setId(serviceApplication.getServicePackage().getCompany().getId());
		companyBean.setVat(serviceApplication.getServicePackage().getCompany().getVat());
		servicePackageBean.setCompany(companyBean);
		
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
		
		//worksheet list
		List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
		AssignWorksheetController assWorksheetController = new AssignWorksheetController();
		assWorksheetController.setMessageSource(messageSource);
		assWorksheetController.setUnitService(unitService);
		
		for(Worksheet worksheet : serviceApplication.getWorksheets()){
			
			WorksheetBean worksheetBean = assWorksheetController.populateEntityToDto(worksheet);
			
			//current date assing 
			HistoryTechnicianGroupWorkBean hgw = new HistoryTechnicianGroupWorkBean();
			int hisSize = worksheetBean.getHistoryTechnicianGroupWorkBeans().size();
			if(hisSize > 0){
				hgw = worksheetBean.getHistoryTechnicianGroupWorkBeans().get(hisSize - 1);
				
				worksheetBean.setCurrentDateAssignText((null == hgw.getAssingCurrentDate() ? ""
						: formatDataTh.format(hgw.getAssingCurrentDate())));
			}
			worksheetBeanList.add(worksheetBean);
			
			WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
			if(null != worksheetSetup){
				String stringValue = worksheetSetup.getWorkSheet().getStatus();
				if("S".equals(stringValue)){
					serviceApplicationBean.setServiceDate((null == hgw.getAssingCurrentDate() ? ""
						: formatDataTh.format(hgw.getAssingCurrentDate())));
				}
			}
			
		}
		//date current invoice create
		Date dateCurrentInvoiceCreate = null;
		if(serviceApplication.getInvoices().size() > 0){
			dateCurrentInvoiceCreate = serviceApplication.getInvoices().get(serviceApplication.getInvoices().size()-1).getCreateDate();
		}
		
		SimpleDateFormat dataCreateTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		serviceApplicationBean.setCurrentCreateInvoice(null == dateCurrentInvoiceCreate ? "" : dataCreateTh.format(dateCurrentInvoiceCreate));
		
		serviceApplicationBean.setWorksheetBeanList(worksheetBeanList);
		
		return serviceApplicationBean;
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
	
	public ServiceApplicationTypeBean populateEntityToDto(ServiceApplicationType serviceApplicationType) {
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		if(null != serviceApplicationType){
			serviceApplicationTypeBean.setId(serviceApplicationType.getId());
			serviceApplicationTypeBean.setServiceApplicationTypeName(serviceApplicationType.getServiceApplicationTypeName());
			serviceApplicationTypeBean.setServiceApplicationTypeCode(serviceApplicationType.getServiceApplicationTypeCode());
		}
		return serviceApplicationTypeBean;
	}
	
	public CareerBean populateEntityToDto(Career career) {
		CareerBean careerBean = new CareerBean();
		if(null != career){
			careerBean.setId(career.getId());
			careerBean.setCareerName(career.getCareerName());
			careerBean.setCareerCode(career.getCareerCode());
		}
		return careerBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ServiceApplicationService getServiceApplicationService() {
		return serviceApplicationService;
	}

	public void setServiceApplicationService(ServiceApplicationService serviceApplicationService) {
		this.serviceApplicationService = serviceApplicationService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	
}
