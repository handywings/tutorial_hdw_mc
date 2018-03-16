package com.hdw.mccable.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/suspenstiontempo")
public class SuspenstionTempoController extends BaseController{
	final static Logger logger = Logger.getLogger(SuspenstionTempoController.class);
	public static final String CONTROLLER_NAME = "suspenstiontempo/";
	
	 public static final String TYPE_EQUIMENT = "E";
	 public static final String TYPE_INTERNET_USER = "I";
	 public static final String TYPE_SERVICE = "S";
	 public static final String WAIT_FOR_PAY = "H"; // H = สถานะแรกคือ "รอจ่ายงาน"
	 public static final String CORPORATE = "C";
	 public static final String TEMPORARY = "T"; // T = ระงับสายสัญญาณชั่วคราว Temporary
	 
	// initial service
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired
	private MessageSource messageSource;
	//End initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 		
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
				//init paging
				Pagination pagination = createPagination(1, 10, "suspenstiontempo",new ApplicationSearchBean());
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
//		ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
		
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
				
				// search service application
				ApplicationSearchBean applicationSearchBean = (ApplicationSearchBean) session
						.getAttribute("applicationSearchBean");
				// set value search service application
				if (applicationSearchBean != null) {
					modelAndView.addObject("applicationSearchBean", applicationSearchBean);
				} else {
					modelAndView.addObject("applicationSearchBean", new ApplicationSearchBean());
				}
				 
				//search process and pagination
				if(applicationSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "suspenstiontempo",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "suspenstiontempo",new ApplicationSearchBean());
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
	
	@SuppressWarnings("unchecked")
	//create pagination
	Pagination createPagination(int currentPage, int itemPerPage, String controller,ApplicationSearchBean applicationSearchBean){
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(serviceApplicationService.getCountTotal(new ApplicationSearchBean()));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = serviceApplicationService.getByPage(pagination, new ApplicationSearchBean());
		
		int periods = 0;
		if(null != applicationSearchBean){
			periods = ("".equals(applicationSearchBean.getKey()) || null == applicationSearchBean.getKey())?0:Integer.parseInt(applicationSearchBean.getKey());
		}
		
		if(periods <= 0) {
			periods = 1;
		}
		
		//populate
		List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : (List<ServiceApplication>)pagination.getDataList()){
			List<Invoice> invoices = serviceApplication.getInvoices();
			int overpay = 0;
			if(null != invoices && invoices.size() > 0){			
				for(Invoice invoice:invoices){
					if("O".equals(invoice.getStatus())){
						overpay++;
					}
				}
			}
			
			if(periods <= overpay && overpay > 0){
				ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
				serviceApplicationBean.setId(serviceApplication.getId());
				serviceApplicationBean.setOverpay(overpay);
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
				
				//add to list
				serviceApplicationBeans.add(serviceApplicationBean);
			}
		}
		pagination.setDataListBean(serviceApplicationBeans);
		//end populate
		return pagination;
	}
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchServiceApplication(
			@ModelAttribute("applicationSearchBean") ApplicationSearchBean applicationSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchServiceApplication][Type : Controller]");
		logger.info("[method : searchServiceApplication][applicationSearchBean : " + applicationSearchBean.toString()
				+ "]");
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
		modelAndView.setViewName(REDIRECT + "/suspenstiontempo/page/1/itemPerPage/10");
		return modelAndView;
	}

	// create search session
	public void generateSearchSession(ApplicationSearchBean applicationSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("applicationSearchBean", applicationSearchBean);
	}

	@RequestMapping(value="updateServiceApplication", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updateServiceApplication(@RequestBody final ServiceApplicationBean serviceApplicationBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
//			
			try{
				logger.info("[method : save][Type : Controller]");
				ServiceApplication serviceApplication = new ServiceApplication();
				if(null != serviceApplicationBean){
					serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
					serviceApplication.setStatus(TEMPORARY);
					serviceApplicationService.update(serviceApplication);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("requisitionDocument.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	//getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
