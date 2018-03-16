package com.hdw.mccable.controller;

import java.io.IOException;
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
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.SearchAllCustomerBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/serviceapplicationlistzone")
public class ServiceApplicationListZoneController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ServiceApplicationListZoneController.class);
	public static final String CONTROLLER_NAME = "serviceapplicationlistzone/";
		
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
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
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
				Pagination pagination = createPagination(1, 10, "serviceapplicationlistzone",new ApplicationSearchBean());
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
					Pagination pagination = createPagination(id, itemPerPage, "serviceapplicationlistzone",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "serviceapplicationlistzone",new ApplicationSearchBean());
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
		modelAndView.setViewName(REDIRECT + "/serviceapplicationlistzone/page/1/itemPerPage/10");
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
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		applicationSearchBean.setStatus("A"); // รายการใบสมัครตามเขตพื้นที่ จะแสดง เฉพาะ ใบงานใช้งานปกติ
		pagination.setTotalItem(serviceApplicationService.getCountTotal(applicationSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = serviceApplicationService.getByPage(pagination, applicationSearchBean);
		
		//populate
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : (List<ServiceApplication>)pagination.getDataList()){
			ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
			
			serviceApplicationBean = populateEntityToDto(serviceApplication);
			
			//add to list
			serviceApplicationBeans.add(serviceApplicationBean);
		}
		pagination.setDataListBean(serviceApplicationBeans);
		//end populate
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
	
	public CustomerFeatureBean populateEntityToDto(CustomerFeature customerFeature) {
		CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
		if(null != customerFeature){
			customerFeatureBean.setId(customerFeature.getId());
			customerFeatureBean.setCustomerFeatureName(customerFeature.getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(customerFeature.getCustomerFeatureCode());
		}
		return customerFeatureBean;
	}
	
	@RequestMapping(value = "searchServiceapplicationAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
		public JsonResponse searchServiceapplicationAjax(@RequestBody final ApplicationSearchBean applicationSearchBean,
				HttpServletRequest request) {
			logger.info("[method : searchServiceapplicationAjax][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					logger.info("param applicationSearchBean : " + applicationSearchBean.toString());
					List<ServiceApplication> serviceApplicationList = serviceApplicationService.searchServiceApplicationByApplicationSearchBean(applicationSearchBean);
					//populate
					List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
					for(ServiceApplication serviceApplication : serviceApplicationList){
						ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
						serviceApplicationBean = populateEntityToDto(serviceApplication);
						//add to list
						serviceApplicationBeans.add(serviceApplicationBean);
					}
					jsonResponse.setResult(serviceApplicationBeans); 
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
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
