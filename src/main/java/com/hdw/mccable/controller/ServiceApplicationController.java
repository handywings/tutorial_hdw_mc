package com.hdw.mccable.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.SearchAllCustomerBean;
import com.hdw.mccable.dto.SearchAllProductBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.SmartCardReaderBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.TemplateServiceBean;
import com.hdw.mccable.dto.TemplateServiceItemBean;
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
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.TemplateService;
import com.hdw.mccable.entity.TemplateServiceItem;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.ZipCode;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.DocumentFileService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
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
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.FilePathUtil;
import com.hdw.mccable.utils.SmartCardReader;
import com.hdw.mccable.utils.SmartCardReaderUtil;

@Controller
@RequestMapping("/serviceapplication")
public class ServiceApplicationController extends BaseController {
	
	final static Logger logger = Logger.getLogger(ServiceApplicationController.class);
	public static final String CONTROLLER_NAME = "serviceapplication/";
	
	// TEST
//    private static String UPLOADED_FOLDER = "/home/thanarat/space/timesheet/03-60/temp/";
	 private static String UPLOADED_FOLDER = "C:/temp/";
	 
	 public static final String TYPE_EQUIMENT = "E";
	 public static final String TYPE_INTERNET_USER = "I";
	 public static final String TYPE_SERVICE = "S";
	 public static final String WAIT_FOR_PAY = "H"; // H = สถานะแรกคือ "รอมอบหมายงาน"
	 public static final String STATUS_DRAFT = "D"; // D = สถานะแรกคือเมื่อกดปุ่ม บันทึกและออกใบงาน "แบบร่าง"
	 
	 
	 public static final String FILE_TYPE_IMAGE = "I"; // I = รูป
	 public static final String FILE_TYPE_HOUSE_REGISTRATION = "H"; // H = สำเนาทะเบียนบ้าน
	 public static final String FILE_TYPE_IDENTITY = "P"; // P = สำเนาบัตรประจำตัวประชาชน
	 public static final String FILE_TYPE_OTHER = "O"; // O เอกสารอื่นๆ
	 
	 public static final String CABLE_TV_CODE = "00002";
	 
	//initial service
	@Autowired(required = true)
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageService")
	private ServicePackageService servicePackageService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
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
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		long startTime = System.currentTimeMillis();
		ModelAndView modelAndView = new ModelAndView();
		List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
		List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
		List<CustomerBean> customerBean = new ArrayList<CustomerBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				ServicePackageController spc = new ServicePackageController();
				spc.setMessageSource(messageSource);
				spc.setProductService(productService);
				spc.setInternetProductService(internetProductService);
				spc.setServiceProductService(serviceProductService);
				spc.setServicePackageService(servicePackageService);
				List<ProductBean> productBeans = spc.searchAllProduct(new SearchAllProductBean());
				modelAndView.addObject("productBeans", productBeans);
				
//				CustomerRegistrationController custController = new CustomerRegistrationController();
//				List<Customer> customers = customerService.findAll();
//				custController.setMessageSource(messageSource);
//				if(null != customers && !customers.isEmpty()){
//					for(Customer customer:customers){
//						if(customer != null && customer.getContact() != null) {
//							customerBean.add(populateEntityToDtoForModal(customer));
//						}
//					}
//				}
//				long endTime   = System.currentTimeMillis();
//				long totalTime = endTime - startTimeCustomers;
//				int seconds = (int) (totalTime / 1000) % 60 ;
//				int minutes = (int) ((totalTime / (1000*60)) % 60);
//				int hours   = (int) ((totalTime / (1000*60*60)) % 24);
//				logger.info("TimeCustomers : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
				
				List<Province> provinces = addressService.findAll();
				if(null != provinces && !provinces.isEmpty()){
					for(Province province:provinces){
						provinceBeans.add(populateEntityToDto(province));
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
				modelAndView.addObject("serviceApplicationBean", new ServiceApplicationBean());
				
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
						ServiceApplicationTypeBean serviceApplicationTypeBean = populateEntityToDtoNot0004(serviceApplicationType);
						if(null != serviceApplicationTypeBean)serviceApplicationTypeBeans.add(serviceApplicationTypeBean);
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
		modelAndView.addObject("provinces", provinceBeans);
		modelAndView.addObject("zones", zoneBeans);
		modelAndView.addObject("customers", customerBean);
		modelAndView.addObject("defaultImage", FilePathUtil.pathAvatarOnWebCustomer(null,"male"));
		
		//remove session
		session.removeAttribute("alert");
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		logger.info("EndTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value="getAmphurByProvince/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getAmphurByProvince(@PathVariable String id) {
		logger.info("[method : getAmphurByProvince][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();

		if(isPermission()){
			try{
				Province province = addressService.getProvinceById(Long.valueOf(id));
				ProvinceBean provinceBean =  populateEntityToDto2(province);
				
				jsonResponse.setResult(provinceBean);
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
	
	@RequestMapping(value="getDistrict/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getDistrict(@PathVariable String id) {
		logger.info("[method : getDistrict][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();

		if(isPermission()){
			try{
				District district = addressService.getDistrictById(Long.valueOf(id));
				DistrictBean districtBean =  populateEntityToDtoV2(district);
				
				jsonResponse.setResult(districtBean);
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
	
	@RequestMapping(value="getServicePackage/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getServicePackage(@PathVariable String id) {
		logger.info("[method : getServicePackage][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		List<ServicePackageBean> servicePackageBeans = new ArrayList<ServicePackageBean>();
		
		if(isPermission()){
			try{
				List<ServicePackage> servicePackages = servicePackageService.findServicePackageByServicePackageTypeId(Long.valueOf(id));
				if(null != servicePackages && !servicePackages.isEmpty()){
					ServicePackageController servicePackageController = new ServicePackageController();
					servicePackageController.setMessageSource(messageSource);
					for(ServicePackage servicePackage:servicePackages){
						servicePackageBeans.add(servicePackageController.populateEntityToDtoV2(servicePackage));
					}
				}
				jsonResponse.setResult(servicePackageBeans);
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
	
	@RequestMapping(value="getServicePackageDetail/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getServicePackageDetail(@PathVariable String id) {
		logger.info("[method : getServicePackageDetail][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		
		if(isPermission()){
			try{
				ServicePackage servicePackage = servicePackageService.getServicePackageById(Long.valueOf(id));
				if(null != servicePackage){
					ServicePackageBean servicePackageBean = populateEntityToDto(servicePackage);
					
					// เช็ค ประเภทบริการ ถ้าเป็น บริการ Cable TV จะ Add ค่าติดตั้งจุด Digital และ ค่าติดตั้งจุด Analog Auto
					String[] CABLE_CODE = {"00001","00004", "00005", "00006"};
					if(Arrays.asList(CABLE_CODE).contains(servicePackageBean.getServiceType().getPackageTypeCode())){
						String[] productCode = {"00002","00003"};
						List<ServiceProduct> serviceProducts = serviceProductService.getSetUpPoint(productCode);
						if(null != serviceProducts && serviceProducts.size() > 0){
							for(ServiceProduct serviceProduct:serviceProducts){
								TemplateServiceItemBean templateServiceItemBean = new TemplateServiceItemBean();
								
								templateServiceItemBean.setId(serviceProduct.getId());
								templateServiceItemBean.setType("S");
								templateServiceItemBean.setQuantity(1);
								
								ProductOrderServiceProductController serviceProductController = new ProductOrderServiceProductController();
								serviceProductController.setMessageSource(messageSource);
								ServiceProductBean serviceProductBean = serviceProductController
										.populateEntityToDto(serviceProduct);
								templateServiceItemBean.setProductBean(serviceProductBean);
								
								servicePackageBean.getTemplate().getListTemplateServiceItemBean().add(templateServiceItemBean);
							}
						}
						
					}
					
					jsonResponse.setResult(servicePackageBean);
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

		// template
		List<TemplateServiceItemBean> templateItems = new ArrayList<TemplateServiceItemBean>();
		TemplateServiceBean templateServiceBean = new TemplateServiceBean();
		TemplateService templateService = servicePackage.getTemplateService();
	if(null != templateService){
		
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
					Customer customer = new Customer();
					
					if(null != serviceApplicationBean){
						if(null == serviceApplicationBean.getCustomer().getId()){

						customer.setCustCode(customerService.genCustomerCode());
						customer.setSex(serviceApplicationBean.getCustomer().getSex());
						customer.setPrefix(serviceApplicationBean.getCustomer().getPrefix());
						customer.setFirstName(serviceApplicationBean.getCustomer().getFirstName());
						customer.setLastName(serviceApplicationBean.getCustomer().getLastName());
						customer.setCustType(serviceApplicationBean.getCustomer().getCustomerType().getValue());
						customer.setIdentityNumber(serviceApplicationBean.getCustomer().getIdentityNumber());
						
						Career Career = customerService.findCareerById(serviceApplicationBean.getCustomer().getCareerBean().getId());
						customer.setCareer(Career);

						Contact contact = new Contact();
						contact.setMobile(serviceApplicationBean.getCustomer().getContact().getMobile());
						contact.setFax(serviceApplicationBean.getCustomer().getContact().getFax());
						contact.setEmail(serviceApplicationBean.getCustomer().getContact().getEmail());
						contact.setCreateDate(CURRENT_TIMESTAMP);
						contact.setCreatedBy(getUserNameLogin());
						contact.setCustomer(customer);
						customer.setContact(contact);
						
						//customerFeature
						CustomerFeature customerFeature = customerService.findCustomerFeatureById(serviceApplicationBean.getCustomer().getCustomerFeatureBean().getId());
						customer.setCustomerFeature(customerFeature);
						
						customer.setCreateDate(CURRENT_TIMESTAMP);
						customer.setCreatedBy(getUserNameLogin());
						
						customer.setDeleted(Boolean.FALSE);
						customer.setActive(Boolean.FALSE);
											
						Long customerId  = customerService.save(customer);
						
						customer = customerService.getCustomerById(customerId);
						
						}else{
							customer = customerService.getCustomerById(serviceApplicationBean.getCustomer().getId());
						}
						
						Company company = companyService.getCompanyById(serviceApplicationBean.getCompanyBean().getId());
						serviceApplication.setCompany(company);
						
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
						
						Long serviceApplicationId = serviceApplicationService.save(serviceApplication);
						serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationId);
						
						// ที่อยู่ customer
						if(null == serviceApplicationBean.getCustomer().getId()){
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
						}
						
						Zone zone = null;
						
						for(AddressBean addressBean:serviceApplicationBean.getAddressList()){
//							logger.info("addressBean : "+addressBean.toString());
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
//								if("3".equals(addressBean.getAddressType())) {
//									zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
//								}
								
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
							
							//save worksheet
//							Long worksheetId = workSheetService.save(worksheet);
							//save invoice
//							WorkSheetAddController wAdd = new WorkSheetAddController();
//							wAdd.setMessageSource(messageSource);
//							wAdd.setWorkSheetService(workSheetService);
//							wAdd.setFinancialService(financialService);
//							wAdd.createInvoiceReceipt(worksheetId);
						}
						
						for(ProductItemBean productItemBean:serviceApplicationBean.getProductitemList()){
							EquipmentProduct equipmentProduct = new EquipmentProduct();
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
										productItemWorksheet.setQuantity(1);
										
										productItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
										productItemWorksheet.setCreatedBy(getUserNameLogin());
										
										productItemWorksheetService.save(productItemWorksheet);
									}
								}
							}
						}
						
						File file = new File(UPLOADED_FOLDER);
						if (!file.exists()) {
							file.mkdirs();
						}
						
						// upload file
						if(null != file_map && file_map.getSize() > 0){
					        byte[] bytes = file_map.getBytes();
					        Path path = Paths.get(UPLOADED_FOLDER + file_map.getOriginalFilename());
					        Files.write(path, bytes);
							DocumentFile documentFile = new DocumentFile();
							documentFile.setServiceApplication(serviceApplication);
							documentFile.setFileType(FILE_TYPE_IMAGE);
							documentFile.setFileName(file_map.getOriginalFilename());
							documentFile.setFilePath(UPLOADED_FOLDER);
							documentFile.setDeleted(Boolean.FALSE);
							documentFile.setCreateDate(CURRENT_TIMESTAMP);
							documentFile.setCreatedBy(getUserNameLogin());
							
							documentFileService.save(documentFile);
						}
						if(null != file_house && file_house.getSize() > 0){
					        byte[] bytes = file_house.getBytes();
					        Path path = Paths.get(UPLOADED_FOLDER + file_house.getOriginalFilename());
					        Files.write(path, bytes);
							DocumentFile documentFile = new DocumentFile();
							documentFile.setServiceApplication(serviceApplication);
							documentFile.setFileType(FILE_TYPE_HOUSE_REGISTRATION);
							documentFile.setFileName(file_house.getOriginalFilename());
							documentFile.setFilePath(UPLOADED_FOLDER);
							documentFile.setDeleted(Boolean.FALSE);
							documentFile.setCreateDate(CURRENT_TIMESTAMP);
							documentFile.setCreatedBy(getUserNameLogin());
							
							documentFileService.save(documentFile);
						}
						if(null != file_identity_card && file_identity_card.getSize() > 0){
					        byte[] bytes = file_identity_card.getBytes();
					        Path path = Paths.get(UPLOADED_FOLDER + file_identity_card.getOriginalFilename());
					        Files.write(path, bytes);
							DocumentFile documentFile = new DocumentFile();
							documentFile.setServiceApplication(serviceApplication);
							documentFile.setFileType(FILE_TYPE_IDENTITY);
							documentFile.setFileName(file_identity_card.getOriginalFilename());
							documentFile.setFilePath(UPLOADED_FOLDER);
							documentFile.setDeleted(Boolean.FALSE);
							documentFile.setCreateDate(CURRENT_TIMESTAMP);
							documentFile.setCreatedBy(getUserNameLogin());
							
							documentFileService.save(documentFile);
						}
						if(null != file_other && file_other.getSize() > 0){
					        byte[] bytes = file_other.getBytes();
					        Path path = Paths.get(UPLOADED_FOLDER + file_other.getOriginalFilename());
					        Files.write(path, bytes);
							DocumentFile documentFile = new DocumentFile();
							documentFile.setServiceApplication(serviceApplication);
							documentFile.setFileType(FILE_TYPE_OTHER);
							documentFile.setFileName(file_other.getOriginalFilename());
							documentFile.setFilePath(UPLOADED_FOLDER);
							documentFile.setDeleted(Boolean.FALSE);
							documentFile.setCreateDate(CURRENT_TIMESTAMP);
							documentFile.setCreatedBy(getUserNameLogin());
							
							documentFileService.save(documentFile);
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
	
	// search product
	@RequestMapping(value = "searchCustomerAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
		public JsonResponse searchCustomerAjax(@RequestBody final SearchAllCustomerBean searchAllCustomerBean,
				HttpServletRequest request) {
			logger.info("[method : searchCustomerAjax][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					logger.info("param searchAllProductBean : " + searchAllCustomerBean.toString());
					// load product all type
					List<CustomerBean> customerBeans = searchAllCustomer(searchAllCustomerBean);
					jsonResponse.setResult(customerBeans); 
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
		
	@RequestMapping(value = "loadDataCustomer/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
		public JsonResponse loadDataCustomer(@PathVariable String id, HttpServletRequest request) {
			logger.info("[method : searchCustomerAjax][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					
					Customer customer = customerService.getCustomerById(Long.valueOf(id));
					CustomerRegistrationController custController = new CustomerRegistrationController();
					custController.setMessageSource(messageSource);
					custController.setUnitService(unitService);
					CustomerBean customerBean = custController.populateEntityToDto(customer);

					jsonResponse.setResult(customerBean); 
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
	
	@RequestMapping(value = "smartCardReader", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
		public JsonResponse smartCardReader(HttpServletRequest request) {
			logger.info("[method : smartCardReader][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					HashMap<String, Object> result = SmartCardReader.Reader();
					Set<String> key = result.keySet();
					logger.info("key : "+key);
					if(null != result.get("error")){
						jsonResponse.setError(true);
						jsonResponse.setResult(result.get("error"));
					}else{
						SmartCardReaderUtil smartCardReaderUtil = (SmartCardReaderUtil) result.get("success");
						SmartCardReaderBean smartCardReaderBean = new SmartCardReaderBean();
						logger.info("smartCardReaderUtil : "+smartCardReaderUtil.toString());
						smartCardReaderBean.setCitizenid(smartCardReaderUtil.getCitizenid());
						smartCardReaderBean.setTh_Prefix(smartCardReaderUtil.getTh_Prefix());
						smartCardReaderBean.setTh_Firstname(smartCardReaderUtil.getTh_Firstname());
						smartCardReaderBean.setTh_Lastname(smartCardReaderUtil.getTh_Lastname());
						smartCardReaderBean.setEn_Prefix(smartCardReaderUtil.getEn_Prefix());
						smartCardReaderBean.setEn_Firstname(smartCardReaderUtil.getEn_Firstname());
						smartCardReaderBean.setEn_Lastname(smartCardReaderUtil.getEn_Lastname());
						smartCardReaderBean.setBirthday(smartCardReaderUtil.getBirthday());
						smartCardReaderBean.setSex(smartCardReaderUtil.getSex());
						smartCardReaderBean.setCard_Issuer(smartCardReaderUtil.getCard_Issuer());
						smartCardReaderBean.setIssue(smartCardReaderUtil.getIssue());
						smartCardReaderBean.setExpire(smartCardReaderUtil.getExpire());

						smartCardReaderBean.setAddrHouseNo(smartCardReaderUtil.getAddrHouseNo());
						smartCardReaderBean.setAddrVillageNo(smartCardReaderUtil.getAddrVillageNo());
						
						
						Province province = addressService.getProvinceByProvinceName(smartCardReaderUtil.getAddrProvince().substring(7));
						Amphur amphur = addressService.getAmphurByAmphurName(smartCardReaderUtil.getAddrAmphur().substring(5));
						District district = addressService.getDistrictByDistrictNameANDAmphurId(smartCardReaderUtil.getAddrTambol().substring(4),amphur.getId());
						
						if(null != district)
							smartCardReaderBean.setAddrTambol(""+district.getId());
							ZipCode zipCode = addressService.getZipCodeByDistrictCode(district.getDISTRICT_CODE());
							if(null != zipCode)
								smartCardReaderBean.setAddrZipCode(zipCode.getZIP_CODE());
						if(null != amphur)
							smartCardReaderBean.setAddrAmphur(""+amphur.getId());
						if(null != province)
							smartCardReaderBean.setAddrProvince(""+province.getId());
						smartCardReaderBean.setPhotoPart(smartCardReaderUtil.getPhotoPart());
						jsonResponse.setResult(smartCardReaderBean);
						jsonResponse.setError(false);
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
	
	// load product all type
	List<CustomerBean> searchAllCustomer(SearchAllCustomerBean searchAllCustomerBean) {
		List<CustomerBean> customerBean = new ArrayList<CustomerBean>();
		String key = "";
		String customerType = "";
		Long custFeature = (long) 0;

		// key
		if (searchAllCustomerBean.getKey() != null && (!searchAllCustomerBean.getKey().isEmpty())) {
			key = searchAllCustomerBean.getKey();
		}
		// customerType
		if (searchAllCustomerBean.getCustomerType() != null
				&& (!searchAllCustomerBean.getCustomerType().isEmpty())) {
			customerType = searchAllCustomerBean.getCustomerType();
		}
		// customer feature
		if (searchAllCustomerBean.getCustomerFeatures() != null && searchAllCustomerBean.getCustomerFeatures() > 0) {
			custFeature = searchAllCustomerBean.getCustomerFeatures();
		}
		
		List<Customer> customers = customerService.searchCustomer(key, customerType, custFeature);
		
		CustomerRegistrationController custController = new CustomerRegistrationController();
		custController.setMessageSource(messageSource);
		if(null != customers && !customers.isEmpty()){
			for(Customer customer:customers){
				customerBean.add(populateEntityToDtoForModal(customer));
			}
		}
		
		return customerBean;
	}
	
	public ProvinceBean populateEntityToDto(Province province) {
		ProvinceBean provinceBean = new ProvinceBean();
		if(null != province){
			provinceBean.setId(province.getId());
			provinceBean.setPROVINCE_NAME(province.getPROVINCE_NAME());
			provinceBean.setPROVINCE_CODE(province.getPROVINCE_CODE());
			
//			List<AmphurBean> amphurBeans = new ArrayList<AmphurBean>();
//			List<Amphur> amphurs = province.getAmphurs();
//			if(null != amphurs && !amphurs.isEmpty()){
//				for(Amphur amphur:amphurs){
//					amphurBeans.add(populateEntityToDto(amphur));
//				}
//			}
//			provinceBean.setAmphurBeans(amphurBeans);

		}
		return provinceBean;
	}
	
	public ProvinceBean populateEntityToDto2(Province province) {
		ProvinceBean provinceBean = new ProvinceBean();
		if(null != province){
			provinceBean.setId(province.getId());
			provinceBean.setPROVINCE_NAME(province.getPROVINCE_NAME());
			provinceBean.setPROVINCE_CODE(province.getPROVINCE_CODE());
			
			List<AmphurBean> amphurBeans = new ArrayList<AmphurBean>();
			List<Amphur> amphurs = province.getAmphurs();
			if(null != amphurs && !amphurs.isEmpty()){
				for(Amphur amphur:amphurs){
					amphurBeans.add(populateEntityToDto(amphur));
				}
			}
			provinceBean.setAmphurBeans(amphurBeans);

		}
		return provinceBean;
	}
	
	public AmphurBean populateEntityToDto(Amphur amphur) {
		AmphurBean amphurBeans = new AmphurBean();
		if(null != amphur){
			amphurBeans.setId(amphur.getId());
			amphurBeans.setAMPHUR_NAME(amphur.getAMPHUR_NAME());
			amphurBeans.setAMPHUR_CODE(amphur.getAMPHUR_CODE());
			amphurBeans.setPOSTCODE(amphur.getPOSTCODE());
			
			List<DistrictBean> districtBeans = new ArrayList<DistrictBean>();
			List<District> districts = amphur.getDistricts();
			if(null != districts && !districts.isEmpty()){
				for(District district:districts){
					districtBeans.add(populateEntityToDto(district));
				}
			}
			amphurBeans.setDistrictBeans(districtBeans);
			
		}
		return amphurBeans;
	}
	
	public DistrictBean populateEntityToDto(District district) {
		DistrictBean districtBean = new DistrictBean();
		if(null != district){
			districtBean.setId(district.getId());
			districtBean.setDISTRICT_NAME(district.getDISTRICT_NAME());
			districtBean.setDISTRICT_CODE(district.getDISTRICT_CODE());
		}
		return districtBean;
	}
	
	public DistrictBean populateEntityToDtoV2(District district) {
		DistrictBean districtBean = new DistrictBean();
		if(null != district){
			districtBean.setId(district.getId());
			districtBean.setDISTRICT_NAME(district.getDISTRICT_NAME());
			districtBean.setDISTRICT_CODE(district.getDISTRICT_CODE());
			ZipCode zipCode = addressService.getZipCodeByDistrictCode(district.getDISTRICT_CODE());
			if(null != zipCode){
				districtBean.setZIP_CODE(zipCode.getZIP_CODE());
			}
		}
		return districtBean;
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
	
	public ServiceApplicationTypeBean populateEntityToDtoNot0004(ServiceApplicationType serviceApplicationType) {
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		if(null != serviceApplicationType){
			if("0004".equals(serviceApplicationType.getServiceApplicationTypeCode())){
				return null;
			}
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
	
	
	public CustomerBean populateEntityToDtoForModal(Customer customer) {
		CustomerBean customerBean = new CustomerBean();
		if (customer != null) {
			// customer
			customerBean.setId(customer.getId());
			customerBean.setCustCode(customer.getCustCode());
			customerBean.setSex(customer.getSex());
			customerBean.setPrefix(customer.getPrefix());
			customerBean.setFirstName(customer.getFirstName());
			customerBean.setLastName(customer.getLastName());
			customerBean.setActive(customer.isActive());
			customerBean.setIdentityNumber(customer.getIdentityNumber());
			customerBean.setCustTypeReal(customer.getCustType());

		} // end if

		return customerBean;
	}
	
	
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
}
