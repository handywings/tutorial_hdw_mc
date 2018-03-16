package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

import com.google.gson.Gson;
import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerSearchBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DigitalAnalogBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReportInvoiceExcelBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/customerregistration")
public class CustomerRegistrationController extends BaseController {

	final static Logger logger = Logger.getLogger(CustomerRegistrationController.class);
	public static final String CONTROLLER_NAME = "customerregistration/";
	public static final String INDIVIDUAL = "I";
	public static final String CORPORATE = "C";

	Gson g = new Gson();

	// initial service
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;

	@Autowired(required = true)
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;

	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
	private MessageSource messageSource;

	// end initial service

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
		// check permission
		if (isPermission()) {
			try {

				//init paging
				Pagination pagination = createPagination(1, 10, "customerregistration",new CustomerSearchBean());
				modelAndView.addObject("pagination",pagination);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		// CustomerFeature
		List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
		List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
		if(null != customerFeatures && !customerFeatures.isEmpty()){
			for(CustomerFeature customerFeature:customerFeatures){
				customerFeatureBeans.add(populateEntityToDto(customerFeature));
			}
		}
		modelAndView.addObject("customerFeatures", customerFeatureBeans);

		// remove session
		session.removeAttribute("alert");
		session.removeAttribute("customerSearchBean");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
		ModelAndView modelAndView = new ModelAndView();
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
				CustomerSearchBean customerSearchBean = (CustomerSearchBean) session.getAttribute("customerSearchBean");
				// set value search service application
				if (customerSearchBean != null) {
					modelAndView.addObject("customerSearchBean", customerSearchBean);
				} else {
					modelAndView.addObject("customerSearchBean", new CustomerSearchBean());
				}
				
				//search process and pagination
				if(customerSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "customerregistration",customerSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "customerregistration",new CustomerSearchBean());
					modelAndView.addObject("pagination", pagination);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		// CustomerFeature
		List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
		List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
		if(null != customerFeatures && !customerFeatures.isEmpty()){
			for(CustomerFeature customerFeature:customerFeatures){
				customerFeatureBeans.add(populateEntityToDto(customerFeature));
			}
		}
		modelAndView.addObject("customerFeatures", customerFeatureBeans);

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	//create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller,CustomerSearchBean customerSearchBean){
		long startTime = System.currentTimeMillis();
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(customerService.getCountTotal(customerSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = customerService.getByPage(pagination, customerSearchBean);
		
		//populate
		List<CustomerBean> customerBeans = new ArrayList<CustomerBean>();
		for(Customer customer : (List<Customer>)pagination.getDataList()){
			CustomerBean customerBean = new CustomerBean();
			customerBean = populateEntityToDtoInit(customer);
			customerBeans.add(customerBean);
		}
		pagination.setDataListBean(customerBeans);
		//end populate
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		
		return pagination;
	}
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchServicePackage(
			@ModelAttribute("customerSearchBean") CustomerSearchBean customerSearchBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchServicePackage][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(customerSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/customerregistration/page/1/itemPerPage/10");
		return modelAndView;
	}

	//view customer
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView view(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : view][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		WorkSheetAddController WorkSheetAddController = new WorkSheetAddController();
		WorkSheetAddController.setMessageSource(messageSource);
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			Customer customer = customerService.getCustomerById(id);
			if (customer != null) {
				CustomerBean customerBean = populateEntityToDtoV2(customer);
				modelAndView.addObject("customerBean", customerBean);
				
				session.setAttribute("customerBeanExport", customerBean);
				
				//product s/n list all
				List<ProductItemWorksheetBean> productItemWorksheetBeans = new ArrayList<ProductItemWorksheetBean>();
				for(ServiceApplicationBean serviceApplicationBean : customerBean.getServiceApplicationList()){
					
					if(!"I".equals(serviceApplicationBean.getStatus().getStringValue())){
						ServiceApplication serviceApplication = new ServiceApplication();
						serviceApplication.setId(serviceApplicationBean.getId());
						List<ProductItemWorksheet> productItemWorksheets = equipmentProductItemService.loadEquipmentProductItemHasSNAllStatus(serviceApplication);

						for(ProductItemWorksheet productItemWorksheet : productItemWorksheets){
							ProductItemWorksheetBean productItemWorksheetBean = populoateProductItemWorksheet(productItemWorksheet);
							if(productItemWorksheet.getProductItem().getWorkSheet().getStatus().equals(messageSource.getMessage("worksheet.status.value.s", null, LocaleContextHolder.getLocale()))){
								productItemWorksheetBeans.add(productItemWorksheetBean);
							}
						}
					}
				}
				modelAndView.addObject("productItemWorksheetBeans", productItemWorksheetBeans);
				
				//invoice
				InvoiceController invc = new InvoiceController();
				invc.setMessageSource(messageSource);
				List<InvoiceDocumentBean> invoiceDocumentBeanList = new ArrayList<InvoiceDocumentBean>();
				for(ServiceApplication serviceApplication : customer.getServiceApplications()){
					for(Invoice invoice : serviceApplication.getInvoices()){
						InvoiceDocumentBean invoiceBean = new InvoiceDocumentBean();
						invoiceBean = invc.PoppulateInvoiceEntityToDto(invoice);
						invoiceDocumentBeanList.add(invoiceBean);
					}
				}
				modelAndView.addObject("invoiceDocumentBeanList", invoiceDocumentBeanList);
				
				//list-digital-analog
//				List<ServiceApplication> serviceApplicationList = serviceApplicationService.getListDigitalAnalogByCustomerId(id);
//				List<DigitalAnalogBean> digitalAnalogBeanList = new ArrayList<DigitalAnalogBean>();
//				if(null != serviceApplicationList){
//					for(ServiceApplication serviceApplication:serviceApplicationList){
//						DigitalAnalogBean bean = new DigitalAnalogBean();
//						bean.setServiceApplicationId(serviceApplication.getId());
//						bean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
//						String typePoints = "";
//						
//						bean.setTypePoints(typePoints);
//						digitalAnalogBeanList.add(bean);
//					}
//				}
//				modelAndView.addObject("digitalAnalogBeanList", digitalAnalogBeanList);
				
				//product internet list all
				productItemWorksheetBeans = new ArrayList<ProductItemWorksheetBean>();
				for(ServiceApplicationBean serviceApplicationBean : customerBean.getServiceApplicationList()){
					
					if(!"I".equals(serviceApplicationBean.getStatus().getStringValue())){
						ServiceApplication serviceApplication = new ServiceApplication();
						serviceApplication.setId(serviceApplicationBean.getId());
						List<ProductItemWorksheet> productItemWorksheets = equipmentProductItemService.loadInternetProductItemByserviceApplicationId(serviceApplication);

						for(ProductItemWorksheet productItemWorksheet : productItemWorksheets){
							ProductItemWorksheetBean productItemWorksheetBean = populoateProductItemWorksheetInternet(productItemWorksheet);
							if(productItemWorksheet.getProductItem().getWorkSheet().getStatus().equals(messageSource.getMessage("worksheet.status.value.s", null, LocaleContextHolder.getLocale()))){
								productItemWorksheetBeans.add(productItemWorksheetBean);
							}
						}
					}
				}
				modelAndView.addObject("productItemWorksheetInternetBeans", productItemWorksheetBeans);
				
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
	
	// view customer
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			Customer customer = customerService.getCustomerById(id);
			if (customer != null) {
				CustomerBean customerBean = populateEntityToDtoV2(customer);
				modelAndView.addObject("customerBean", customerBean);
				// province all
				List<Province> provinces = addressService.findAll();
				List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
				for (Province province : provinces) {
					ProvinceBean provinceBean = new ProvinceBean();
					provinceBean.setId(province.getId());
					provinceBean.setPROVINCE_NAME(province.getPROVINCE_NAME());
					provinceBeans.add(provinceBean);
				}
				modelAndView.addObject("provinceBeans", provinceBeans);
				// address type1 province by
				Province provincesType1 = addressService
						.getProvinceById(customerBean.getAddressList().get(0).getProvinceBean().getId());
				modelAndView.addObject("provinceBeansType1", populateProvince(provincesType1));

				// address type2 province by
				Province provincesType2 = addressService
						.getProvinceById(customerBean.getAddressList().get(1).getProvinceBean().getId());
				modelAndView.addObject("provinceBeansType2", populateProvince(provincesType2));
				
				// career list
				List<CareerBean> careerBeans = new ArrayList<CareerBean>();
				List<Career> careers = customerService.findAllCareer();
				for(Career career : careers){
					CareerBean careerBean = new CareerBean();
					careerBean.setId(career.getId());
					careerBean.setCareerName(career.getCareerName());
					careerBean.setCareerCode(career.getCareerCode());
					careerBeans.add(careerBean);
				}
				modelAndView.addObject("careerBeans", careerBeans);
				
				//customer feature list
				List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
				List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
				for(CustomerFeature customerFeature : customerFeatures){
					CustomerFeatureBean CustomerFeatureBean = new CustomerFeatureBean();
					CustomerFeatureBean.setId(customerFeature.getId());
					CustomerFeatureBean.setCustomerFeatureName(customerFeature.getCustomerFeatureName());
					CustomerFeatureBean.setCustomerFeatureCode(customerFeature.getCustomerFeatureCode());
					customerFeatureBeans.add(CustomerFeatureBean);
				}
				modelAndView.addObject("customerFeatureBeans", customerFeatureBeans);
				
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

	// update customer
	@RequestMapping(value = "updateCustomer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateCustomer(@RequestBody final CustomerBean customerBean, HttpServletRequest request) {
		logger.info("[method : updateCustomer][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();

		if (isPermission()) {
			// create timestamp
			try {
				Customer customer = customerService.getCustomerById(customerBean.getId());
				if (customer != null) {
					customer.setCustCode(customerBean.getCustCode());
					customer.setSex(customerBean.getSex());
					customer.setPrefix(customerBean.getPrefix());
					customer.setCustType(customerBean.getCustTypeReal());
					customer.setFirstName(customerBean.getFirstName());
					customer.setLastName(customerBean.getLastName());
					//customer.setCareer(customerBean.getCareer());
					//career
					Career career = customerService.findCareerById(customerBean.getCareerBean().getId());
					customer.setCareer(career);
					//customer feature
					CustomerFeature customerFeature = customerService.findCustomerFeatureById(customerBean.getCustomerFeatureBean().getId());
					customer.setCustomerFeature(customerFeature);
					
					customer.setIdentityNumber(customerBean.getIdentityNumber());
					// contact
					customer.getContact().setMobile(customerBean.getContact().getMobile());
					customer.getContact().setEmail(customerBean.getContact().getEmail());
					customer.getContact().setFax(customerBean.getContact().getFax());
					customerService.update(customer);

					// address
					Address addressType1 = addressService.getAddressById(customerBean.getAddressList().get(0).getId());
					Address addressType2 = addressService.getAddressById(customerBean.getAddressList().get(1).getId());
					if (addressType1 != null && addressType2 != null) {
						addressType1.setNo(customerBean.getAddressList().get(0).getNo());
						addressType1.setSection(customerBean.getAddressList().get(0).getSection());
						addressType1.setBuilding(customerBean.getAddressList().get(0).getBuilding());
						addressType1.setRoom(customerBean.getAddressList().get(0).getRoom());
						addressType1.setFloor(customerBean.getAddressList().get(0).getFloor());
						addressType1.setVillage(customerBean.getAddressList().get(0).getVillage());
						addressType1.setAlley(customerBean.getAddressList().get(0).getAlley());
						addressType1.setRoad(customerBean.getAddressList().get(0).getRoad());
						addressType1.setPostcode(customerBean.getAddressList().get(0).getPostcode());
						addressType1.setNearbyPlaces(customerBean.getAddressList().get(0).getNearbyPlaces());

						Province province = addressService
								.getProvinceById(customerBean.getAddressList().get(0).getProvinceBean().getId());
						addressType1.setProvinceModel(province);
						Amphur amphur = addressService
								.getAmphurById(customerBean.getAddressList().get(0).getAmphurBean().getId());
						addressType1.setAmphur(amphur);
						District district = addressService
								.getDistrictById(customerBean.getAddressList().get(0).getDistrictBean().getId());
						addressType1.setDistrictModel(district);

						if (customerBean.getAddressList().get(1).getOverrideAddressId() != null) {
							addressType2.setOverrideAddressId(customerBean.getAddressList().get(0).getId());
							addressType2.setNo(addressType1.getNo());
							addressType2.setSection(addressType1.getSection());
							addressType2.setBuilding(addressType1.getBuilding());
							addressType2.setRoom(addressType1.getRoom());
							addressType2.setFloor(addressType1.getFloor());
							addressType2.setVillage(addressType1.getVillage());
							addressType2.setAlley(addressType1.getAlley());
							addressType2.setRoad(addressType1.getRoad());
							addressType2.setPostcode(addressType1.getPostcode());
							addressType2.setNearbyPlaces(addressType1.getNearbyPlaces());
							addressType2.setProvinceModel(province);
							addressType2.setAmphur(amphur);
							addressType2.setDistrictModel(district);

						} else {
							addressType2.setOverrideAddressId(null);
							addressType2.setNo(customerBean.getAddressList().get(1).getNo());
							addressType2.setSection(customerBean.getAddressList().get(1).getSection());
							addressType2.setBuilding(customerBean.getAddressList().get(1).getBuilding());
							addressType2.setRoom(customerBean.getAddressList().get(1).getRoom());
							addressType2.setFloor(customerBean.getAddressList().get(1).getFloor());
							addressType2.setVillage(customerBean.getAddressList().get(1).getVillage());
							addressType2.setAlley(customerBean.getAddressList().get(1).getAlley());
							addressType2.setRoad(customerBean.getAddressList().get(1).getRoad());
							addressType2.setPostcode(customerBean.getAddressList().get(1).getPostcode());
							addressType2.setNearbyPlaces(customerBean.getAddressList().get(1).getNearbyPlaces());

							Province provinceType2 = addressService
									.getProvinceById(customerBean.getAddressList().get(1).getProvinceBean().getId());
							addressType2.setProvinceModel(provinceType2);
							Amphur amphurType2 = addressService
									.getAmphurById(customerBean.getAddressList().get(1).getAmphurBean().getId());
							addressType2.setAmphur(amphurType2);
							District districtType2 = addressService
									.getDistrictById(customerBean.getAddressList().get(1).getDistrictBean().getId());
							addressType2.setDistrictModel(districtType2);

						}

						addressService.updateAddress(addressType1);
						addressService.updateAddress(addressType2);
					} else {
						// thow exception
					}

				} else {
					// thow exception and message
				}
				System.out.println(customerBean.toString());
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
				messageSource.getMessage("customer.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// loadAmphur
	@RequestMapping(value = "loadAmphur/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadAmphur(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadAmphur][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				Province province = addressService.getProvinceById(Long.valueOf(id));
				if (province != null) {
					List<AmphurBean> amphurBeans = new ArrayList<AmphurBean>();
					for (Amphur amphur : province.getAmphurs()) {
						AmphurBean amphurBean = new AmphurBean();
						amphurBean.setId(amphur.getId());
						amphurBean.setAMPHUR_NAME(amphur.getAMPHUR_NAME());
						amphurBean.setPOSTCODE(amphur.getPOSTCODE());
						amphurBeans.add(amphurBean);
					}
					jsonResponse.setError(false);
					jsonResponse.setResult(amphurBeans);
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

	// loadDistrict
	@RequestMapping(value = "loadDistrict/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadDistrict(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadDistrict][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				String postCode= "";
				List<District> districts = addressService.getDistrictByAmphurId(Long.valueOf(id));
				
				if (districts != null) {
					 List<DistrictBean> districtBeans = new ArrayList<DistrictBean>();
					 for(District district : districts){
						 DistrictBean districtBean = new DistrictBean();
						 districtBean.setId(district.getId());
						 districtBean.setDISTRICT_NAME(district.getDISTRICT_NAME());
						 AmphurBean amBean = new AmphurBean();
						 amBean.setId(district.getAmphur().getId());
						 amBean.setPOSTCODE(district.getAmphur().getPOSTCODE());
						 districtBean.setAmphurBean(amBean);
						 districtBeans.add(districtBean);
						 postCode = district.getAmphur().getPOSTCODE();
					 }
					 jsonResponse.setError(false);
					 jsonResponse.setResult(districtBeans);
					 jsonResponse.setResultStore01(postCode);
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
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse delete(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : delete][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				Customer customer = customerService.getCustomerById(Long.valueOf(id));
				if (customer != null) {
					customer.setDeleted(Boolean.TRUE);
					customerService.update(customer);
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
				messageSource.getMessage("customer.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	public List<CustomerBean> searchCustomerProcess(CustomerSearchBean customerSearchBean) {
		List<CustomerBean> customerBeans = new ArrayList<CustomerBean>();
		String key = "";
		String custType = "";
		Long custFeature = (long) 0;
		
		if (customerSearchBean.getKey() != null && (!customerSearchBean.getKey().isEmpty()))
			key = customerSearchBean.getKey();
		if (customerSearchBean.getCustType() != null && (!customerSearchBean.getCustType().isEmpty()))
			custType = customerSearchBean.getCustType();
		if (customerSearchBean.getCustomerFeatures() != null && customerSearchBean.getCustomerFeatures() > 0)
			custFeature = customerSearchBean.getCustomerFeatures();

		List<Customer> customers = customerService.searchCustomer(key, custType, custFeature);
		// populate
		for (Customer customer : customers) {
			CustomerBean customerBean = new CustomerBean();
			customerBean = populateEntityToDtoInit(customer);
			customerBeans.add(customerBean);
		}
		return customerBeans;
	}

	public CustomerBean populateEntityToDtoV2(Customer customer) {
		CustomerBean customerBean = new CustomerBean();
		if (customer != null) {
			// customer
			customerBean.setId(customer.getId());
			customerBean.setSex(customer.getSex());
			customerBean.setPrefix(customer.getPrefix());
			customerBean.setCustCode(customer.getCustCode());
			customerBean.setFirstName(customer.getFirstName());
			customerBean.setLastName(customer.getLastName());
			customerBean.setActive(customer.isActive());
			customerBean.setIdentityNumber(customer.getIdentityNumber());
			customerBean.setCustTypeReal(customer.getCustType());
			
			//customer feature
			CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
			customerFeatureBean.setId(customer.getCustomerFeature().getId());
			customerFeatureBean.setCustomerFeatureName(customer.getCustomerFeature().getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(customer.getCustomerFeature().getCustomerFeatureCode());
			customerBean.setCustomerFeatureBean(customerFeatureBean);
			
			//career
			CareerBean careerBean = new CareerBean();
			careerBean.setId(customer.getCareer().getId());
			careerBean.setCareerName(customer.getCareer().getCareerName());
			careerBean.setCareerCode(customer.getCareer().getCareerCode());
			customerBean.setCareerBean(careerBean);
			
			// customer type
			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
			customerTypeBean.setMessageSource(messageSource);
			if (customer.getCustType().equals(CORPORATE)) {
				customerTypeBean.corPorate();
			} else {
				customerTypeBean.inDividual();
			}
			customerBean.setCustomerType(customerTypeBean);
			
			// contact
			ContactBean contactBean = new ContactBean();
			contactBean.setId(customer.getContact().getId());

			if (customer.getContact().getMobile() != null && (!customer.getContact().getMobile().isEmpty())) {
				contactBean.setMobile(customer.getContact().getMobile());
			} else {
				contactBean.setMobile("-");
			}

			if (customer.getContact().getEmail() != null && (!customer.getContact().getEmail().isEmpty())) {
				contactBean.setEmail(customer.getContact().getEmail());
			} else {
				contactBean.setEmail("-");
			}

			if (customer.getContact().getFax() != null && (!customer.getContact().getFax().isEmpty())) {
				contactBean.setFax(customer.getContact().getFax());
			} else {
				contactBean.setFax("-");
			}
			customerBean.setContact(contactBean);

			// address
			List<AddressBean> addressBeans = new ArrayList<AddressBean>();
			List<Address> addressList = customer.getAddresses();
			if(null != addressList && addressList.size() > 1){
				int i = 0;
				for(Address address:addressList){
					if("1".equals(address.getAddressType()) || "2".equals(address.getAddressType())){
						i++;
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
						addressBeans.add(addressBean);
						if(i == 2) break;
					}
				}
			}
			customerBean.setAddressList(addressBeans);

			// service application
			List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
			for (ServiceApplication serviceApplication : customer.getServiceApplications()) {
				ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
				serviceApplicationBean.setId(serviceApplication.getId());
				serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
				SimpleDateFormat dataCreateTh = new SimpleDateFormat(
						messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
						new Locale("TH", "th"));
				serviceApplicationBean.setStartDate((null == serviceApplication.getStartDate() ? ""
						: dataCreateTh.format(serviceApplication.getStartDate())));
				StatusBean statusBean = new StatusBean();
				statusBean.setStringValue(serviceApplication.getStatus());
				serviceApplicationBean.setStatus(statusBean);
				//date current invoice create
				Date dateCurrentInvoiceCreate = null;
				if(serviceApplication.getInvoices().size() > 0){
					dateCurrentInvoiceCreate = serviceApplication.getInvoices().get(serviceApplication.getInvoices().size()-1).getCreateDate();
				}
				serviceApplicationBean.setCurrentCreateInvoice(null == dateCurrentInvoiceCreate ? "" : dataCreateTh.format(dateCurrentInvoiceCreate));
				// service packge
				ServicePackageBean servicePackageBean = new ServicePackageBean();
				servicePackageBean.setId(serviceApplication.getServicePackage().getId());
				servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
				servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
				serviceApplicationBean.setServicepackage(servicePackageBean);

				// product item
				List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
				for (ProductItem productItem : serviceApplication.getProductItems()) {
					ProductItemBean productItemBean = new ProductItemBean();
					productItemBean.setId(productItem.getId());
					productItemBean.setQuantity(productItem.getQuantity());

					if (productItem.getEquipmentProduct() != null) {
						EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
						ProductAddController productAddController = new ProductAddController();
						productAddController.setMessageSource(messageSource);
						equipmentProductBean = productAddController
								.populateEntityToDto(productItem.getEquipmentProduct());
						productItemBean.setProduct(equipmentProductBean);

					} else if (productItem.getInternetProduct() != null) {
						InternetProductBean internetProductBean = new InternetProductBean();
						ProductOrderInternetProductController internetController = new ProductOrderInternetProductController();
						internetController.setMessageSource(messageSource);
						internetProductBean = internetController.populateEntityToDto(productItem.getInternetProduct());
						productItemBean.setProduct(internetProductBean);
						productItemBean.getProduct().unitTypeInternet();

					} else if (productItem.getServiceProduct() != null) {
						ServiceProductBean serviceProductBean = new ServiceProductBean();
						ProductOrderServiceProductController serviceController = new ProductOrderServiceProductController();
						serviceController.setMessageSource(messageSource);
						serviceProductBean = serviceController.populateEntityToDto(productItem.getServiceProduct());
						productItemBean.setProduct(serviceProductBean);
					}

					productItemBeans.add(productItemBean);
				}
				serviceApplicationBean.setProductitemList(productItemBeans);
				
				//worksheet 
				AssignWorksheetController AssignWorksheetController = new AssignWorksheetController();
				AssignWorksheetController.setMessageSource(messageSource);
				List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
				for(Worksheet worksheet : serviceApplication.getWorksheets()){
					WorksheetBean worksheetBean = AssignWorksheetController.populateEntityToDto(worksheet);
					//current date assing 
					HistoryTechnicianGroupWorkBean hgw = new HistoryTechnicianGroupWorkBean();
					int hisSize = worksheetBean.getHistoryTechnicianGroupWorkBeans().size();
					if(hisSize > 0){
						hgw = worksheetBean.getHistoryTechnicianGroupWorkBeans().get(hisSize - 1);
						
						worksheetBean.setCurrentDateAssignText((null == hgw.getAssingCurrentDate() ? ""
								: dataCreateTh.format(hgw.getAssingCurrentDate())));
					}
					worksheetBeanList.add(worksheetBean);
				}
				serviceApplicationBean.setWorksheetBeanList(worksheetBeanList);
				// address
				List<AddressBean> addressBeansServiceApp = new ArrayList<AddressBean>();
				for (Address address : serviceApplication.getAddresses()) {
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
					
					//zone setup
					if(address.getZone() != null){
						ZoneBean zoneBeanSetup = new ZoneBean();
						zoneBeanSetup.setId(address.getZone().getId());
						zoneBeanSetup.setZoneName(address.getZone().getZoneName());
						zoneBeanSetup.setZoneDetail(address.getZone().getZoneDetail());
						addressBean.setZoneBean(zoneBeanSetup);
					}
					
					addressBean.collectAddress();
					addressBeansServiceApp.add(addressBean);
				}
				serviceApplicationBean.setAddressList(addressBeansServiceApp);
				
				//address list
				
				
				// add list application
				serviceApplicationBeans.add(serviceApplicationBean);

			}

			if(null == serviceApplicationBeans || serviceApplicationBeans.size() == 0){
				ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
				serviceApplicationBean.setId(0L);
				serviceApplicationBeans.add(serviceApplicationBean);
			}
			
			customerBean.setServiceApplicationList(serviceApplicationBeans);

		} // end if

		return customerBean;
	}
	
	public CustomerBean populateEntityToDtoInit(Customer customer) {
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
			
			//customer feature
			CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
			customerFeatureBean.setId(customer.getCustomerFeature().getId());
			customerFeatureBean.setCustomerFeatureName(customer.getCustomerFeature().getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(customer.getCustomerFeature().getCustomerFeatureCode());
			customerBean.setCustomerFeatureBean(customerFeatureBean);
			
			//career
			CareerBean careerBean = new CareerBean();
			careerBean.setId(customer.getCareer().getId());
			careerBean.setCareerName(customer.getCareer().getCareerName());
			careerBean.setCareerCode(customer.getCareer().getCareerCode());
			customerBean.setCareerBean(careerBean);
			
			// customer type
			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
			customerTypeBean.setMessageSource(messageSource);
			if (customer.getCustType().equals(CORPORATE)) {
				customerTypeBean.corPorate();
			} else {
				customerTypeBean.inDividual();
			}
			customerBean.setCustomerType(customerTypeBean);
			
			// contact
			ContactBean contactBean = new ContactBean();
			contactBean.setId(customer.getContact().getId());

			if (customer.getContact().getMobile() != null && (!customer.getContact().getMobile().isEmpty())) {
				contactBean.setMobile(customer.getContact().getMobile());
			} else {
				contactBean.setMobile("-");
			}

			if (customer.getContact().getEmail() != null && (!customer.getContact().getEmail().isEmpty())) {
				contactBean.setEmail(customer.getContact().getEmail());
			} else {
				contactBean.setEmail("-");
			}

			if (customer.getContact().getFax() != null && (!customer.getContact().getFax().isEmpty())) {
				contactBean.setFax(customer.getContact().getFax());
			} else {
				contactBean.setFax("-");
			}
			customerBean.setContact(contactBean);
			
			// address
			List<AddressBean> addressBeans = new ArrayList<AddressBean>();
			if (null != customer.getAddresses() && customer.getAddresses().size() > 0) {
				AddressBean addressBean = new AddressBean();
				addressBean.setAddressType(customer.getAddresses().get(0).getAddressType());
				addressBean.setNearbyPlaces(customer.getAddresses().get(0).getNearbyPlaces());
				addressBeans.add(addressBean);
			}
			customerBean.setAddressList(addressBeans);

		} // end if

		return customerBean;
	}
	
	public CustomerBean populateEntityToDto(Customer customer) {
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
			
			//customer feature
			CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
			customerFeatureBean.setId(customer.getCustomerFeature().getId());
			customerFeatureBean.setCustomerFeatureName(customer.getCustomerFeature().getCustomerFeatureName());
			customerFeatureBean.setCustomerFeatureCode(customer.getCustomerFeature().getCustomerFeatureCode());
			customerBean.setCustomerFeatureBean(customerFeatureBean);
			
			//career
			CareerBean careerBean = new CareerBean();
			careerBean.setId(customer.getCareer().getId());
			careerBean.setCareerName(customer.getCareer().getCareerName());
			careerBean.setCareerCode(customer.getCareer().getCareerCode());
			customerBean.setCareerBean(careerBean);
			
			// customer type
			CustomerTypeBean customerTypeBean = new CustomerTypeBean();
			customerTypeBean.setMessageSource(messageSource);
			if (customer.getCustType().equals(CORPORATE)) {
				customerTypeBean.corPorate();
			} else {
				customerTypeBean.inDividual();
			}
			customerBean.setCustomerType(customerTypeBean);
			
			// contact
			ContactBean contactBean = new ContactBean();
			contactBean.setId(customer.getContact().getId());

			if (customer.getContact().getMobile() != null && (!customer.getContact().getMobile().isEmpty())) {
				contactBean.setMobile(customer.getContact().getMobile());
			} else {
				contactBean.setMobile("-");
			}

			if (customer.getContact().getEmail() != null && (!customer.getContact().getEmail().isEmpty())) {
				contactBean.setEmail(customer.getContact().getEmail());
			} else {
				contactBean.setEmail("-");
			}

			if (customer.getContact().getFax() != null && (!customer.getContact().getFax().isEmpty())) {
				contactBean.setFax(customer.getContact().getFax());
			} else {
				contactBean.setFax("-");
			}
			customerBean.setContact(contactBean);

			// address
			List<AddressBean> addressBeans = new ArrayList<AddressBean>();
			for (Address address : customer.getAddresses()) {
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
				addressBeans.add(addressBean);
			}
			customerBean.setAddressList(addressBeans);

			// service application
			List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
			for (ServiceApplication serviceApplication : customer.getServiceApplications()) {
				ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
				serviceApplicationBean.setId(serviceApplication.getId());
				serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
				SimpleDateFormat dataCreateTh = new SimpleDateFormat(
						messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
						new Locale("TH", "th"));
				serviceApplicationBean.setStartDate((null == serviceApplication.getStartDate() ? ""
						: dataCreateTh.format(serviceApplication.getStartDate())));
				StatusBean statusBean = new StatusBean();
				statusBean.setStringValue(serviceApplication.getStatus());
				serviceApplicationBean.setStatus(statusBean);

				// service packge
				ServicePackageBean servicePackageBean = new ServicePackageBean();
				servicePackageBean.setId(serviceApplication.getServicePackage().getId());
				servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
				servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
				serviceApplicationBean.setServicepackage(servicePackageBean);

				// product item
				List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
				for (ProductItem productItem : serviceApplication.getProductItems()) {
					ProductItemBean productItemBean = new ProductItemBean();
					productItemBean.setId(productItem.getId());
					productItemBean.setQuantity(productItem.getQuantity());

					if (productItem.getEquipmentProduct() != null) {
						EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
						ProductAddController productAddController = new ProductAddController();
						productAddController.setMessageSource(messageSource);
						equipmentProductBean = productAddController
								.populateEntityToDto(productItem.getEquipmentProduct());
						productItemBean.setProduct(equipmentProductBean);

					} else if (productItem.getInternetProduct() != null) {
						InternetProductBean internetProductBean = new InternetProductBean();
						ProductOrderInternetProductController internetController = new ProductOrderInternetProductController();
						internetController.setMessageSource(messageSource);
						internetProductBean = internetController.populateEntityToDto(productItem.getInternetProduct());
						productItemBean.setProduct(internetProductBean);
						productItemBean.getProduct().unitTypeInternet();

					} else if (productItem.getServiceProduct() != null) {
						ServiceProductBean serviceProductBean = new ServiceProductBean();
						ProductOrderServiceProductController serviceController = new ProductOrderServiceProductController();
						serviceController.setMessageSource(messageSource);
						serviceProductBean = serviceController.populateEntityToDto(productItem.getServiceProduct());
						productItemBean.setProduct(serviceProductBean);
					}

					productItemBeans.add(productItemBean);
				}
				serviceApplicationBean.setProductitemList(productItemBeans);
				
				//worksheet 
				AssignWorksheetController AssignWorksheetController = new AssignWorksheetController();
				AssignWorksheetController.setMessageSource(messageSource);
				AssignWorksheetController.setUnitService(unitService);
				List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
				for(Worksheet worksheet : serviceApplication.getWorksheets()){
					WorksheetBean worksheetBean = AssignWorksheetController.populateEntityToDto(worksheet);
					//current date assing 
					HistoryTechnicianGroupWorkBean hgw = new HistoryTechnicianGroupWorkBean();
					int hisSize = worksheetBean.getHistoryTechnicianGroupWorkBeans().size();
					if(hisSize > 0){
						hgw = worksheetBean.getHistoryTechnicianGroupWorkBeans().get(hisSize - 1);
						
						worksheetBean.setCurrentDateAssignText((null == hgw.getAssingCurrentDate() ? ""
								: dataCreateTh.format(hgw.getAssingCurrentDate())));
					}
					worksheetBeanList.add(worksheetBean);
				}
				serviceApplicationBean.setWorksheetBeanList(worksheetBeanList);
				// address
				List<AddressBean> addressBeansServiceApp = new ArrayList<AddressBean>();
				for (Address address : serviceApplication.getAddresses()) {
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
					
					//zone setup
					if(address.getZone() != null){
						ZoneBean zoneBeanSetup = new ZoneBean();
						zoneBeanSetup.setId(address.getZone().getId());
						zoneBeanSetup.setZoneName(address.getZone().getZoneName());
						zoneBeanSetup.setZoneDetail(address.getZone().getZoneDetail());
						addressBean.setZoneBean(zoneBeanSetup);
					}
					
					addressBean.collectAddress();
					addressBeansServiceApp.add(addressBean);
				}
				serviceApplicationBean.setAddressList(addressBeansServiceApp);
				
				//address list
				
				
				// add list application
				serviceApplicationBeans.add(serviceApplicationBean);

			}

			if(null == serviceApplicationBeans || serviceApplicationBeans.size() == 0){
				ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
				serviceApplicationBean.setId(0L);
				serviceApplicationBeans.add(serviceApplicationBean);
			}
			
			customerBean.setServiceApplicationList(serviceApplicationBeans);

		} // end if

		return customerBean;
	}

	public ProvinceBean populateProvince(Province province) {
		ProvinceBean provinceBean = new ProvinceBean();
		provinceBean.setId(province.getId());
		provinceBean.setPROVINCE_NAME(province.getPROVINCE_NAME());
		// amphur
		List<AmphurBean> amphurBeans = new ArrayList<AmphurBean>();
		for (Amphur amphur : province.getAmphurs()) {
			AmphurBean amphurBean = new AmphurBean();
			amphurBean.setId(amphur.getId());
			amphurBean.setAMPHUR_NAME(amphur.getAMPHUR_NAME());
			amphurBean.setPOSTCODE(amphur.getPOSTCODE());
			// district
			List<DistrictBean> districtBeans = new ArrayList<DistrictBean>();
			for (District district : amphur.getDistricts()) {
				DistrictBean districtBean = new DistrictBean();
				districtBean.setId(district.getId());
				districtBean.setDISTRICT_NAME(district.getDISTRICT_NAME());
				districtBean.setAmphur_id(amphur.getId());
				districtBeans.add(districtBean);
			}
			amphurBean.setDistrictBeans(districtBeans);
			amphurBeans.add(amphurBean);
		}
		provinceBean.setAmphurBeans(amphurBeans);
		return provinceBean;

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
		productItemWorksheetBean.setEquipmentProductItemBean(populateProductItem(productItemWorksheet.getEquipmentProductItem()));
		return productItemWorksheetBean;
	}
	
	public ProductItemWorksheetBean populoateProductItemWorksheetInternet(ProductItemWorksheet productItemWorksheet){
		ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
		productItemWorksheetBean.setId(productItemWorksheet.getId());
		productItemWorksheetBean.setType(productItemWorksheet.getProductType());
		productItemWorksheetBean.setInternetProductBeanItem(populateProductItemInternet(productItemWorksheet.getInternetProductItem()));
		return productItemWorksheetBean;
	}

	public InternetProductBeanItem populateProductItemInternet(InternetProductItem internetProductItem) {
		// product item
		InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
		if(null != internetProductItem){
			internetProductBeanItem.setId(internetProductItem.getId());
			internetProductBeanItem.setReference(internetProductItem.getReference());
			internetProductBeanItem.setUserName(internetProductItem.getUserName());
			internetProductBeanItem.setPassword(internetProductItem.getPassword());
			
			InternetProduct internetProduct = internetProductItem.getInternetProduct();
			if(null != internetProduct){
				InternetProductBean internetProductBean = new InternetProductBean();
				internetProductBean.setId(internetProduct.getId());
				internetProductBean.setProductName(internetProduct.getProductName());
				internetProductBean.setProductCode(internetProduct.getProductCode());
				internetProductBeanItem.setInternetProductBean(internetProductBean);
			}
		}
		return internetProductBeanItem;
	}
	
	public EquipmentProductItemBean populateProductItem(EquipmentProductItem equipmentProductItem) {
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
			// unit
			UnitBean unitBean = new UnitBean();
			unitBean.setId(equipmentProductItem.getEquipmentProduct().getUnit().getId());
			unitBean.setUnitName(equipmentProductItem.getEquipmentProduct().getUnit().getUnitName());
			equipmentProductBean.setUnit(unitBean);
			equipmentProductItemBean.setEquipmentProductBean(equipmentProductBean);
		

		return equipmentProductItemBean;
	}
	
	@RequestMapping(value = "exportPdf", method = RequestMethod.GET)
	public ModelAndView exportPdf(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JasperRender jasperRender = new JasperRender();
		JasperJrxmlComponent jasperJrxmlComponent = new JasperJrxmlComponent();
		HttpSession httpSession = request.getSession(true);
		Object resUse = new Object();
		CustomerBean customerBean = (CustomerBean) httpSession.getAttribute("customerBeanExport");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custCode", customerBean.getCustCode());
		params.put("custName", customerBean.getPrefix()+customerBean.getFirstName()+" "+customerBean.getLastName());
		params.put("identityNumber", customerBean.getIdentityNumber());
		params.put("customerType", customerBean.getCustomerType().getValue());
		params.put("customerFeature", customerBean.getCustomerFeatureBean().getCustomerFeatureName());
		params.put("career", customerBean.getCareerBean().getCareerName());
		params.put("mobile", customerBean.getContact().getMobile());
		params.put("fax", customerBean.getContact().getFax());
		params.put("email", customerBean.getContact().getEmail());
		
		List<AddressBean> addressBean = customerBean.getAddressList();
		if(null != addressBean && addressBean.size() > 0){
			for(AddressBean address:addressBean){
				if("1".equals(address.getAddressType())){
					params.put("address1", address.getCollectAddressDetail());
					params.put("nearbyPlaces1", address.getNearbyPlaces());
				}
				if("2".equals(address.getAddressType())){
					params.put("address2", address.getCollectAddressDetail());
					params.put("nearbyPlaces2", address.getNearbyPlaces());
				}
			}
		}
		
		jasperRender.setParams(params);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("customer_pdf",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

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
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public void generateSearchSession(CustomerSearchBean customerSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("customerSearchBean", customerSearchBean);
	}

	// getter setter
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setEquipmentProductItemService(EquipmentProductItemService equipmentProductItemService) {
		this.equipmentProductItemService = equipmentProductItemService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
}
