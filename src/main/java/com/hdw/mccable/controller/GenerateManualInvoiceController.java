package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.JsonRequestOrderBill;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.OrderBillSearchBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.SetupWorksheetBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/generatemanualinvoice")
public class GenerateManualInvoiceController extends BaseController {
	final static Logger logger = Logger.getLogger(GenerateManualInvoiceController.class);
	public static final String CONTROLLER_NAME = "generatemanualinvoice/";

	// initial service
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required=true)
	@Qualifier(value="zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired
	private MessageSource messageSource;
	// End initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		ZoneController zoneController = new ZoneController();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				for (Zone zone : zones) {
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				
				//init paging
				SimpleDateFormat formatType3 = new SimpleDateFormat(messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),Locale.US);
				OrderBillSearchBean orderBillSearchBean = new OrderBillSearchBean();
				orderBillSearchBean.setOrderBillDate(formatType3.format(new Date()));
				Pagination pagination = createPagination(1, 10, "generatemanualinvoice", orderBillSearchBean);
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("orderBillSearchBean",orderBillSearchBean);
				
				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
				ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
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
		ZoneController zoneController = new ZoneController();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
					
				// search order bill bean
				OrderBillSearchBean orderBillSearchBean = (OrderBillSearchBean) session.getAttribute("orderBillSearchBean");
				// set value search order bill
				if (orderBillSearchBean != null) {
					modelAndView.addObject("orderBillSearchBean", orderBillSearchBean);
				} else {
					modelAndView.addObject("orderBillSearchBean", new OrderBillSearchBean());
				}
				
				// search process and pagination
				if (orderBillSearchBean != null) {
					Pagination pagination = createPagination(id, itemPerPage, "generatemanualinvoice",orderBillSearchBean);
					modelAndView.addObject("pagination", pagination);
				} else {
					Pagination pagination = createPagination(id, itemPerPage, "generatemanualinvoice",new OrderBillSearchBean());
					modelAndView.addObject("pagination", pagination);
				}

				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
				ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
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

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchOrderBill(@ModelAttribute("orderBillSearchBean") OrderBillSearchBean orderBillSearchBean,HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchOrderBill][Type : Controller]");
		logger.info("[method : searchOrderBill][orderBillSearch : " + orderBillSearchBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(orderBillSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/generatemanualinvoice/page/1/itemPerPage/10");
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	// create pagination
	Pagination createPagination(int currentPage, int itemPerPage, String controller,OrderBillSearchBean orderBillSearchBean) {
		if (itemPerPage == 0)itemPerPage = 10;

		Pagination pagination = new Pagination();
//		pagination.setTotalItem(financialService.getCountTotalOrderBill(orderBillSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		int sizeData = 0;
		List<ServiceApplication> serviceApplicationList = new ArrayList<ServiceApplication>();
		pagination = financialService.getByPageForOrderBill(pagination, orderBillSearchBean,Boolean.TRUE);
		if(null != pagination && null != pagination.getDataList() && ((List<ServiceApplication>) pagination.getDataList()).size() > 0){
			sizeData = ((List<ServiceApplication>) pagination.getDataList()).size();
			serviceApplicationList = ((List<ServiceApplication>) pagination.getDataList());
			logger.info("paginationSize : "+sizeData);
			pagination.setTotalItem(sizeData);
		}
		// -------------------------------check กรณีรอบบิลมากกว่า 1 เดือน-------------------------------//
		List<ServiceApplication> serviceApplicationTempList = new ArrayList<ServiceApplication>();
		int index = 0, startIndex = (currentPage*itemPerPage)-(itemPerPage);
		logger.info("currentPage : "+currentPage);
		logger.info("itemPerPage : "+itemPerPage);
		logger.info("startIndex : "+startIndex);
		for(int i = startIndex; i < sizeData; i++){
			ServiceApplication serviceApplication = serviceApplicationList.get(i);
			if(serviceApplication.getServicePackage().getPerMounth() == 1){
				serviceApplicationTempList.add(serviceApplication);
				index++;
			}else if(serviceApplication.getServicePackage().getPerMounth() > 1){
				List<Invoice> invoiceList = financialService.findInvoiceByServiceApplication(serviceApplication.getId(),"O");
				int perMounth = serviceApplication.getServicePackage().getPerMounth();
				Date dateGenBill = new Date();
				if(invoiceList.size() > 0){
					dateGenBill = invoiceList.get(invoiceList.size()-1).getCreateDate();
				}else{
					Worksheet worksheetSetup = null;
					for(Worksheet worksheet : serviceApplication.getWorksheets()){
						if("C_S".equals(worksheet.getWorkSheetType())){
							worksheetSetup = worksheet;
						}
					}//end for
					if(worksheetSetup.getHistoryTechnicianGroupWorks().size() > 0){
						HistoryTechnicianGroupWork historyTechnicianGroupWork = 
								worksheetSetup.getHistoryTechnicianGroupWorks().
								get(worksheetSetup.getHistoryTechnicianGroupWorks().size()-1);
						
						dateGenBill = historyTechnicianGroupWork.getAssignDate();
					}
					
				}//end else
				
				Calendar calDateGenBill = Calendar.getInstance(new Locale("EN", "en"));
				calDateGenBill.setTime(dateGenBill);
				calDateGenBill.add(Calendar.MONTH, perMounth+1);
				
				int currentDay;
				int currentMonth;
				int currentYear;
				
				if(orderBillSearchBean.getOrderBillDate()!= null && !orderBillSearchBean.getOrderBillDate().isEmpty()){
					String[] splitDate = orderBillSearchBean.getOrderBillDate().split("-");
					currentDay = Integer.valueOf(splitDate[0]);
					currentMonth = Integer.valueOf(splitDate[1]);
					currentYear = Integer.valueOf(splitDate[2]);
				} else {
					Calendar now = Calendar.getInstance(new Locale("EN", "en"));
					currentDay = now.get(Calendar.DAY_OF_MONTH);
					currentMonth = now.get(Calendar.MONTH) + 1;
					currentYear = now.get(Calendar.YEAR);
				}
				
//				System.out.println("==========================================");
//				System.out.println(calDateGenBill.get(Calendar.DAY_OF_MONTH)+" : "+currentDay);
//				System.out.println(calDateGenBill.get(Calendar.MONTH)+" : "+currentMonth);
//				System.out.println(calDateGenBill.get(Calendar.YEAR)+" : "+currentYear);
//				System.out.println("==========================================");
				
				if((calDateGenBill.get(Calendar.DAY_OF_MONTH) == currentDay)
					&&(calDateGenBill.get(Calendar.MONTH) == currentMonth)
					&&(calDateGenBill.get(Calendar.YEAR) == currentYear)){
					
					//add to list service application
					System.out.println("append true sa id : "+serviceApplication.getId());
					serviceApplicationTempList.add(serviceApplication);
					index++;
				}
				
			}//end else if
			
			// set limit data
			if(index >= itemPerPage){
				break;
			}
			
		}//end for
		//-------------------------------End check กรณีรอบบิลมากกว่า 1 เดือน-------------------------------
		
		//poppulate
		List<ServiceApplicationBean> serviceApplicationBeanList = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : serviceApplicationTempList){
			ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
			serviceApplicationBean.setId(serviceApplication.getId());
			serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			//customer
			CustomerBean customerBean = new CustomerBean();
			customerBean.setId(serviceApplication.getCustomer().getId());
			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
			serviceApplicationBean.setCustomer(customerBean);
			//status
			StatusBean statusBean = new StatusBean();
			statusBean.setStringValue(serviceApplication.getStatus());
			serviceApplicationBean.setStatus(statusBean);
			//service package
			ServicePackageBean servicePackageBean = new ServicePackageBean();
			servicePackageBean.setId(serviceApplication.getServicePackage().getId());
			servicePackageBean.setPackageName(serviceApplication.getServicePackage().getPackageName());
			servicePackageBean.setPackageCode(serviceApplication.getServicePackage().getPackageCode());
			servicePackageBean.setMonthlyServiceFee(serviceApplication.getServicePackage().getMonthlyServiceFee());
			//package type
			ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
			servicePackageTypeBean.setId(serviceApplication.getServicePackage().getServicePackageType().getId());
			servicePackageTypeBean.setPackageTypeName(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
			servicePackageBean.setServiceType(servicePackageTypeBean);
			serviceApplicationBean.setServicepackage(servicePackageBean);
			//address
			List<AddressBean> addressBeans = new ArrayList<AddressBean>();
			for(Address address : serviceApplication.getAddresses()){
				AddressBean addressBean = new AddressBean();
				addressBean.setMessageSource(messageSource);
				addressBean.setId(address.getId());
				addressBean.setAddressType(address.getAddressType());
				addressBean.setDetail(address.getDetail());
				addressBean.setNo(address.getNo());
				addressBean.setAlley(address.getAlley());
				addressBean.setRoad(address.getRoad());
				addressBean.setRoom(address.getRoom());
				addressBean.setFloor(address.getFloor());
				addressBean.setBuilding(address.getBuilding());
				addressBean.setSection(address.getSection());
				addressBean.setPostcode(address.getPostcode());
				addressBean.setVillage(address.getVillage());
				addressBean.setNearbyPlaces(address.getNearbyPlaces());
				addressBean.setOverrideAddressId(address.getOverrideAddressId());
				
				//zone
				ZoneBean zoneBean = new ZoneBean();
				if(address.getZone() != null){
					zoneBean.setId(address.getZone().getId());
					zoneBean.setZoneName(address.getZone().getZoneName());
					zoneBean.setZoneDetail(address.getZone().getZoneDetail());
				} 
				addressBean.setZoneBean(zoneBean);
				if(addressBean.getAddressType().equals("4")){
					addressBean.collectAddress();
					addressBeans.add(addressBean);
				}
			}
			serviceApplicationBean.setAddressList(addressBeans);
			
			//worksheet setup
			WorksheetBean worksheetBean = null;
			for(Worksheet worksheet : serviceApplication.getWorksheets()){
				if (worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
					worksheetBean = new SetupWorksheetBean();
					worksheetBean.setIdWorksheetParent(worksheet.getId());
					worksheetBean.setWorkSheetCode(worksheet.getWorkSheetCode());
					StatusBean statusBeanWorksheet = new StatusBean();
					statusBean.setStringValue(worksheet.getStatus());
					worksheetBean.setStatus(statusBeanWorksheet);
					SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),new Locale("TH", "th"));
					
					worksheetBean.setCreateDateTh(
							null == worksheet.getCreateDate() ? "" : formatDataTh.format(worksheet.getCreateDate()));
					
					SimpleDateFormat formatDataThNotTime = new SimpleDateFormat(messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),new Locale("TH", "th"));

					worksheetBean.setDateOrderBillTh(
							(null == new DateUtil().convertStringToOrderBillDateCurrent(orderBillSearchBean.getOrderBillDate(), worksheet.getDateOrderBill())
									? ""
									: formatDataThNotTime.format(new DateUtil()
											.convertStringToOrderBillDateCurrent(orderBillSearchBean.getOrderBillDate(), worksheet.getDateOrderBill()))));
					
					//worksheet type
					worksheetBean.setWorkSheetType(worksheet.getWorkSheetType());
					worksheetBean.setMessageSource(messageSource);
					worksheetBean.loadWorksheetTypeText();
					worksheetBean.setRemark(worksheet.getRemark());
					serviceApplicationBean.setWorksheetSetup(worksheetBean);
				} 
			}
			
			//add to list
			serviceApplicationBeanList.add(serviceApplicationBean);
			
		}
		pagination.setDataListBean(serviceApplicationBeanList);
		
		return pagination;
	}
	
	// create order bill
	@RequestMapping(value = "createManualInvoice", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createManualInvoice(@RequestBody final List<JsonRequestOrderBill> jsonRequestOrderBillList,HttpServletRequest request) {
		logger.info("[method : createManualInvoice][Type : Controller]");
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			try {
				for (JsonRequestOrderBill jsonRequestOrderBill : jsonRequestOrderBillList) {
					logger.info("service application id : " + jsonRequestOrderBill.getServiceApplicationBean().getId());
					ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(jsonRequestOrderBill.getServiceApplicationBean().getId());
					//invoice set value
					Invoice invoice = new Invoice();
					invoice.setInvoiceCode(financialService.genInVoiceCode());
					invoice.setAmount(serviceApplication.getMonthlyServiceFee());
					invoice.setServiceApplication(serviceApplication);
					//invoice type
					invoice.setInvoiceType(messageSource.getMessage("financial.invoice.type.order", null, LocaleContextHolder.getLocale()));
					//invoice status
					invoice.setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale()));
					//invoice.setIssueDocDate();
					//invoice.setPaymentDate();
					invoice.setDeleted(Boolean.FALSE);
					invoice.setCreatedBy(getUserNameLogin());
					if(jsonRequestOrderBill.getOrderBillDate() != null && !jsonRequestOrderBill.getOrderBillDate().isEmpty()){
						invoice.setCreateDate(new DateUtil().convertStringToDateTimeDb(jsonRequestOrderBill.getOrderBillDate()));
					}else{
						invoice.setCreateDate(CURRENT_TIMESTAMP);
					}
					invoice.setIssueDocDate(CURRENT_TIMESTAMP);
					invoice.setPaymentDate(financialService.getPaymentOrderDate(serviceApplication.getId()));
					invoice.setStatusScan("N");
					invoice.setCutting(Boolean.FALSE);
					
					//receipt set value
					Receipt receipt = new Receipt();
					receipt.setReceiptCode(financialService.genReceiptCode());
					receipt.setAmount(invoice.getAmount());
					receipt.setStatus(messageSource.getMessage("financial.receipt.status.hold", null, LocaleContextHolder.getLocale()));
					receipt.setIssueDocDate(CURRENT_TIMESTAMP);
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
				jsonResponse.setError(false);
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request,
				messageSource.getMessage("financial.invoice.gennerate.transaction.title.success", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("financial.invoice.gennerate.transaction.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// create search session
	public void generateSearchSession(OrderBillSearchBean orderBillSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("orderBillSearchBean", orderBillSearchBean);
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	// getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
	}
	
}
