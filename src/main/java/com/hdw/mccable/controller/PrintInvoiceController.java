package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.BillDocumentBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerSearchBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.DocumentFileBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.InvoiceHistoryPrintBean;
import com.hdw.mccable.dto.InvoiceReportBean;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReportCashierExcelAllTables;
import com.hdw.mccable.dto.ReportCashierExcelBean;
import com.hdw.mccable.dto.ReportInvoiceExcelAllTables;
import com.hdw.mccable.dto.ReportInvoiceExcelBean;
import com.hdw.mccable.dto.ReportInvoiceExcelCTables;
import com.hdw.mccable.dto.ReportInvoiceExcelOTables;
import com.hdw.mccable.dto.ReportInvoiceExcelSTables;
import com.hdw.mccable.dto.ReportInvoiceExcelWTables;
//import com.hdw.mccable.dto.ReportWorksheetMainBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationAssignCashier;
import com.hdw.mccable.entity.ServiceApplicationAssignCashierId;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;


@Controller
@RequestMapping("/printinvoice")
public class PrintInvoiceController extends BaseController{
	final static Logger logger = Logger.getLogger(PrintInvoiceController.class);
	public static final String CONTROLLER_NAME = "printinvoice/";
	
	@Autowired
    MessageSource messageSource;
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		List<Personnel> cashier = new ArrayList<Personnel>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {				
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				cashier = personnelService.findPersonnelCashier(true);
				if(cashier != null){
					for(Personnel personnel : cashier){
						PersonnelController person = new PersonnelController();
						PersonnelBean personnelBean = person.populateEntityToDto(personnel);
						personnelBeans.add(personnelBean);
					}
					modelAndView.addObject("personnelBeans", personnelBeans);

					SimpleDateFormat formatDataTh = new SimpleDateFormat("MMMM", new Locale("TH", "th"));
					SimpleDateFormat mmyyyy = new SimpleDateFormat("MM-yyyy", new Locale("TH", "th"));
					SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.US);
					SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.US);
					int[] years = new int[3];
					String[] month  = new String[12];
					Date currentDate = new Date();
			        // convert date to calendar
			        Calendar c = Calendar.getInstance(new Locale("TH", "th"));
			        
			        // สร้าง 3 ปีย้อนหลัง
			        c.setTime(currentDate);
			        years[0] = c.getWeekYear();
			        
			        c.setTime(currentDate);
			        c.add(Calendar.YEAR, -1);
			        years[1] = c.getWeekYear();
			        
			        c.setTime(currentDate);
			        c.add(Calendar.YEAR, -2);
			        years[2] = c.getWeekYear();
			        // สร้าง 3 ปีย้อนหลัง
			        
			        // สร้าง เดือน
			        Date dateForMonth = mm.parse("01");
			        for(int i=0; i < 12; i++){
			        	c.setTime(dateForMonth);
				        c.add(Calendar.MONTH, +(i));
			        	month[i] = formatDataTh.format(c.getTime());
			        }
			        // สร้าง เดือน
			        List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList = new ArrayList<InvoiceHistoryPrintBean>();
					SearchBillScanBean searchBillScanBean = (SearchBillScanBean) session.getAttribute("searchBillScanBeanPrint");
					if(null == searchBillScanBean ||
							StringUtils.isEmpty(searchBillScanBean.getMonth()) ||
							StringUtils.isEmpty(searchBillScanBean.getYear())){
						searchBillScanBean = new SearchBillScanBean();
						searchBillScanBean.setYear(""+years[0]);
						searchBillScanBean.setMonth(mm.format(currentDate));
						
						invoiceHistoryPrintBeanList = searchInvoiceHistoryPrintProcess(searchBillScanBean);
					}else{
						invoiceHistoryPrintBeanList = searchInvoiceHistoryPrintProcess(searchBillScanBean);
					}
					
					// หาจำนวนวันของเดือน
					String lastDayOfMonth = searchBillScanBean.getMonth()+"-"+searchBillScanBean.getYear();
					Date lastDayOfMonthDate = mmyyyy.parse(lastDayOfMonth);
					c.setTime(lastDayOfMonthDate);
					c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
					lastDayOfMonthDate = c.getTime();
					lastDayOfMonth = dd.format(lastDayOfMonthDate);
					// หาจำนวนวันของเดือน
					
					// เช็คมอบหมายผู้เก็บเงิน
					invoiceHistoryPrintBeanList = setAssignPersonnelNotNull(invoiceHistoryPrintBeanList);
					
					// จัดการข้อมูล
					invoiceHistoryPrintBeanList = setDateDetailPrint(invoiceHistoryPrintBeanList, lastDayOfMonth);
					
					modelAndView.addObject("lastDayOfMonth", lastDayOfMonth);
			        modelAndView.addObject("months", month);
					modelAndView.addObject("years", years);
					modelAndView.addObject("searchBillScanBean", searchBillScanBean);
					modelAndView.addObject("invoiceHistoryPrintBeanList", invoiceHistoryPrintBeanList);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + "invoice_list");
		return modelAndView;
	}
	
	public List<InvoiceHistoryPrintBean> setAssignPersonnelNotNull(List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList){
		List<InvoiceHistoryPrintBean> result = new ArrayList<InvoiceHistoryPrintBean>();
		if(null != invoiceHistoryPrintBeanList && invoiceHistoryPrintBeanList.size() > 0){
			for(InvoiceHistoryPrintBean bean:invoiceHistoryPrintBeanList){
				if(null != bean.getAssignPersonnelBean()){
					result.add(bean);
				}
			}
		}
		return result;
	}
	
	private List<InvoiceHistoryPrintBean> setDateDetailPrint(List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList, String lastDayOfMonth) {

		SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.US);
		int dayOfMonth = Integer.valueOf(lastDayOfMonth);
		List<InvoiceHistoryPrintBean> result = new ArrayList<InvoiceHistoryPrintBean>();
		if(null != invoiceHistoryPrintBeanList && invoiceHistoryPrintBeanList.size() > 0){
			int i = 0;
			Long initPersonnel = invoiceHistoryPrintBeanList.get(0).getAssignPersonnelBean().getId();
			InvoiceHistoryPrintBean inv = new InvoiceHistoryPrintBean();
			List<InvoiceDocumentBean> invoiceDocumentBeanList = new ArrayList<InvoiceDocumentBean>();
			for(int j = 0; j < dayOfMonth; j++){
				InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
				invoiceDocumentBean.setAmount(0);
				invoiceDocumentBean.setQuantityBill(0);
				invoiceDocumentBeanList.add(invoiceDocumentBean);
			}
			boolean lastLoop = false;
			for(InvoiceHistoryPrintBean bean:invoiceHistoryPrintBeanList){
				
				if(i++ == invoiceHistoryPrintBeanList.size() - 1){
					if(initPersonnel != bean.getAssignPersonnelBean().getId()){
				    	result.add(inv);
				    	inv = new InvoiceHistoryPrintBean();
				    	invoiceDocumentBeanList = new ArrayList<InvoiceDocumentBean>();
				    	for(int j = 0; j < dayOfMonth; j++){
							InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
							invoiceDocumentBean.setAmount(0);
							invoiceDocumentBean.setQuantityBill(0);
							invoiceDocumentBeanList.add(invoiceDocumentBean);
						}
				    }
			    	
					String billDate = dd.format(bean.getCreateDate());
					int index = Integer.valueOf(billDate);
					float amount = invoiceDocumentBeanList.get((index-1)).getAmount();
					int quantityBill = invoiceDocumentBeanList.get((index-1)).getQuantityBill();
					
					amount += bean.getInvoiceDocumentBean().getAmount();
					quantityBill += 1;
					
					invoiceDocumentBeanList.get((index-1)).setAmount(amount);
					invoiceDocumentBeanList.get((index-1)).setQuantityBill(quantityBill);

					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean.setPrefix(bean.getAssignPersonnelBean().getPrefix());
					personnelBean.setFirstName(bean.getAssignPersonnelBean().getFirstName());
					personnelBean.setLastName(bean.getAssignPersonnelBean().getLastName());
					personnelBean.setNickName(bean.getAssignPersonnelBean().getNickName());
					inv.setPersonnelBean(personnelBean);
					
					inv.setInvoiceDocumentBeanList(invoiceDocumentBeanList);
					result.add(inv);
					lastLoop = true;
			    }else if(initPersonnel != bean.getAssignPersonnelBean().getId()){
			    	result.add(inv);
			    	inv = new InvoiceHistoryPrintBean();
			    	initPersonnel = bean.getAssignPersonnelBean().getId();
			    	invoiceDocumentBeanList = new ArrayList<InvoiceDocumentBean>();
			    	for(int j = 0; j < dayOfMonth; j++){
						InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
						invoiceDocumentBean.setAmount(0);
						invoiceDocumentBean.setQuantityBill(0);
						invoiceDocumentBeanList.add(invoiceDocumentBean);
					}
			    }
				
				if(!lastLoop){
					String billDate = dd.format(bean.getCreateDate());
					int index = Integer.valueOf(billDate);
					float amount = invoiceDocumentBeanList.get((index-1)).getAmount();
					int quantityBill = invoiceDocumentBeanList.get((index-1)).getQuantityBill();
					
					amount += bean.getInvoiceDocumentBean().getAmount();
					quantityBill += 1;
					
					invoiceDocumentBeanList.get((index-1)).setAmount(amount);
					invoiceDocumentBeanList.get((index-1)).setQuantityBill(quantityBill);
					
					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean.setPrefix(bean.getAssignPersonnelBean().getPrefix());
					personnelBean.setFirstName(bean.getAssignPersonnelBean().getFirstName());
					personnelBean.setLastName(bean.getAssignPersonnelBean().getLastName());
					personnelBean.setNickName(bean.getAssignPersonnelBean().getNickName());
					inv.setPersonnelBean(personnelBean);
					
					inv.setInvoiceDocumentBeanList(invoiceDocumentBeanList);
			    }
			}
		}
		
		return result;
	}

	// search request
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("search") SearchBillScanBean searchBillScanBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : search][Type : Controller]");
		logger.info("[method : search][searchBillScanBean : " + searchBillScanBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(searchBillScanBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/printinvoice");
		return modelAndView;
	}
	
	// search request
	@RequestMapping(value = "cashier/search", method = RequestMethod.POST)
	public ModelAndView searchInvoiceCashier(@ModelAttribute("searchInvoiceCashier") SearchBillScanBean searchBillScanBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchInvoiceCashier][Type : Controller]");
		logger.info("[method : searchInvoiceCashier][searchBillScanBean : " + searchBillScanBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(searchBillScanBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/printinvoice/cashier");
		return modelAndView;
	}
	
	// create search session
	public void generateSearchSession(SearchBillScanBean searchBillScanBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("searchBillScanBeanPrint", searchBillScanBean);
	}
	
	//cashier_list
	@RequestMapping(value = "cashier", method = RequestMethod.GET)
	public ModelAndView cashier(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : cashier][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
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
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeanList", zoneBeans);

				List<Personnel> cashier = new ArrayList<Personnel>();
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				cashier = personnelService.findPersonnelCashier(true);
				if(cashier != null){
//					if(cashier.size() > 0){
//						List<Long> serviceApplicationIdList = serviceApplicationService.findServiceApplicationAssignCashierByCashier(cashier.get(0).getId());
//						modelAndView.addObject("serviceApplicationIdList", serviceApplicationIdList);
//					}
					
					for(Personnel personnel : cashier){
						PersonnelController person = new PersonnelController();
						PersonnelBean personnelBean = person.populateEntityToDto(personnel);
						personnelBeans.add(personnelBean);
					}
					modelAndView.addObject("personnelBeans", personnelBeans);
				}
				
				List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();
				SearchBillScanBean searchBillScanBean = (SearchBillScanBean) session.getAttribute("searchBillScanBeanPrint");
				if (searchBillScanBean != null) {
					modelAndView.addObject("searchBillScanBean", searchBillScanBean);
					invoiceBeanList = searchInvoiceProcess(searchBillScanBean);
				} else {
					searchBillScanBean = new SearchBillScanBean();
					if(cashier != null){
						if(cashier.size() > 0){
							searchBillScanBean.setPersonnelCashierId(cashier.get(0).getId());
						}
					}
					modelAndView.addObject("searchBillScanBean", searchBillScanBean);
//					SimpleDateFormat formatData_Th = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//					searchBillScanBean = new SearchBillScanBean();
//					searchBillScanBean.setExportDate(formatData_Th.format(new Date()));
					invoiceBeanList = searchInvoiceProcess(searchBillScanBean);
				}
				
				session.setAttribute("invoiceBeanList", invoiceBeanList);
				modelAndView.addObject("invoiceBeanLists", invoiceBeanList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + "cashier_list");
		return modelAndView;
	}
	
	//cashier_list
	@RequestMapping(value = "cashierconfig", method = RequestMethod.GET)
	public ModelAndView cashierConfig(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : cashier][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
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
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeanList", zoneBeans);
				
				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();

				ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
				servicePackageTypeController.setServicePackageTypeService(servicePackageTypeService);
				
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
				List<Personnel> cashier = new ArrayList<Personnel>();
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				cashier = personnelService.findPersonnelCashier(true);
				if(cashier != null){
					for(Personnel personnel : cashier){
						PersonnelController person = new PersonnelController();
						PersonnelBean personnelBean = person.populateEntityToDto(personnel);
						personnelBeans.add(personnelBean);
					}
					modelAndView.addObject("personnelBeans", personnelBeans);
				}
				
				List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();
				

//				long startTime = System.currentTimeMillis();

//				//populate
//				List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
//				for(ServiceApplication serviceApplication : serviceApplicationList){
//					ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
//					serviceApplicationBean = populateEntityToDtoV2(serviceApplication);
//					//add to list
//					serviceApplicationBeans.add(serviceApplicationBean);
//				}
//				
//				long endTime   = System.currentTimeMillis();
//				long totalTime = endTime - startTime;
//				int seconds = (int) (totalTime / 1000) % 60 ;
//				int minutes = (int) ((totalTime / (1000*60)) % 60);
//				int hours   = (int) ((totalTime / (1000*60*60)) % 24);
//				logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
//				
//				modelAndView.addObject("serviceApplicationBeans", serviceApplicationBeans);
				
				ApplicationSearchBean applicationSearchBean = (ApplicationSearchBean) session.getAttribute("searchBillCashierconfig");
				
				// set value search service application
				if (applicationSearchBean != null) {
					modelAndView.addObject("customerSearchBean", applicationSearchBean);
				} else {
					applicationSearchBean = new ApplicationSearchBean();
					if(null != cashier && cashier.size() > 0){
						applicationSearchBean.setPersonnelId(cashier.get(0).getId());
					}
					modelAndView.addObject("customerSearchBean", new ApplicationSearchBean());
				}
				modelAndView.addObject("applicationSearchBean", applicationSearchBean);
				
				//search process and pagination
				if(applicationSearchBean != null){
					Pagination pagination = createPagination(1, 10, "printinvoice/cashierconfig",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}
				
				List<ServiceApplication> serviceApplicationList = serviceApplicationService.searchServiceApplicationByApplicationSearchBean(applicationSearchBean);
				logger.info("serviceApplicationList : "+serviceApplicationList.size());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + "cashier_config");
		return modelAndView;
	}
	
	@RequestMapping(value = "cashierconfig/page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
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
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeanList", zoneBeans);
				
				// service package type
				List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();

				ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
				servicePackageTypeController.setServicePackageTypeService(servicePackageTypeService);
				
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean = servicePackageTypeController.populateEntityToDto(servicePackageType);
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
				modelAndView.addObject("servicePackageTypeBeans", servicePackageTypeBeans);
				
				List<Personnel> cashier = new ArrayList<Personnel>();
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				cashier = personnelService.findPersonnelCashier(true);
				if(cashier != null){
					for(Personnel personnel : cashier){
						PersonnelController person = new PersonnelController();
						PersonnelBean personnelBean = person.populateEntityToDto(personnel);
						personnelBeans.add(personnelBean);
					}
					modelAndView.addObject("personnelBeans", personnelBeans);
				}
				
				List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();
				

//				long startTime = System.currentTimeMillis();
//				List<ServiceApplication> serviceApplicationList = serviceApplicationService.searchServiceApplicationByApplicationSearchBean(applicationSearchBean);
//				
//				//populate
//				List<ServiceApplicationBean> serviceApplicationBeans = new ArrayList<ServiceApplicationBean>();
//				for(ServiceApplication serviceApplication : serviceApplicationList){
//					ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
//					serviceApplicationBean = populateEntityToDtoV2(serviceApplication);
//					//add to list
//					serviceApplicationBeans.add(serviceApplicationBean);
//				}
//				
//				long endTime   = System.currentTimeMillis();
//				long totalTime = endTime - startTime;
//				int seconds = (int) (totalTime / 1000) % 60 ;
//				int minutes = (int) ((totalTime / (1000*60)) % 60);
//				int hours   = (int) ((totalTime / (1000*60*60)) % 24);
//				logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
//				
//				modelAndView.addObject("serviceApplicationBeans", serviceApplicationBeans);
				
				ApplicationSearchBean applicationSearchBean = (ApplicationSearchBean) session.getAttribute("searchBillCashierconfig");
				
				// set value search service application
				if (applicationSearchBean != null) {
					modelAndView.addObject("customerSearchBean", applicationSearchBean);
				} else {
					applicationSearchBean = new ApplicationSearchBean();
					if(null != cashier && cashier.size() > 0){
						applicationSearchBean.setPersonnelId(cashier.get(0).getId());
					}
					modelAndView.addObject("customerSearchBean", new ApplicationSearchBean());
				}
				modelAndView.addObject("applicationSearchBean", applicationSearchBean);
				
				//search process and pagination
				if(applicationSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "printinvoice/cashierconfig",applicationSearchBean);
					modelAndView.addObject("pagination", pagination);
				}
				
				List<ServiceApplication> serviceApplicationList = serviceApplicationService.searchServiceApplicationByApplicationSearchBean(applicationSearchBean);
				logger.info("serviceApplicationList : "+serviceApplicationList.size());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + "cashier_config");
		return modelAndView;
	}
	
	//create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller,ApplicationSearchBean applicationSearchBean){
		logger.info("applicationSearchBean : "+applicationSearchBean.toString());
		long startTime = System.currentTimeMillis();
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(serviceApplicationService.getCountTotalByApplicationSearchBean(applicationSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = serviceApplicationService.getByPageByApplicationSearchBean(pagination, applicationSearchBean);
		
		//populate
		List<ServiceApplicationBean> serviceApplicationBeanList = new ArrayList<ServiceApplicationBean>();
		for(ServiceApplication serviceApplication : (List<ServiceApplication>)pagination.getDataList()){
			ServiceApplicationBean bean = new ServiceApplicationBean();
			bean = populateEntityToDtoV2(serviceApplication);
			serviceApplicationBeanList.add(bean);
		}
		pagination.setDataListBean(serviceApplicationBeanList);
		//end populate
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		
		return pagination;
	}
	
	public List<InvoiceDocumentBean> searchInvoiceProcessConfig(SearchBillScanBean searchBillScanBean) {
		List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();

		List<Invoice> invoiceList = financialService.findInvoiceTypeSearchConfig(searchBillScanBean);
		for (Invoice invoice : invoiceList) {
			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean = PoppulateInvoiceEntityToDto(invoice);
			invoiceBeanList.add(invoiceDocumentBean);
		}

		return invoiceBeanList;
	}
	
	@RequestMapping(value="searchCashierconfig", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse searchCashierconfig(@RequestBody final ApplicationSearchBean applicationSearchBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		HttpSession session = request.getSession();
		if(isPermission()){
			logger.info("ApplicationSearchBean : "+applicationSearchBean.toString());
			try{
				session.setAttribute("searchBillCashierconfig", applicationSearchBean);
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
	
	@RequestMapping(value="cashierchange", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse cashierchange(@RequestBody final ApplicationSearchBean applicationSearchBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		HttpSession session = request.getSession();
		if(isPermission()){
			try{
				List<Long> serviceApplicationIdList = serviceApplicationService.findServiceApplicationAssignCashierByCashier(applicationSearchBean.getPersonnelId());
				jsonResponse.setResult(serviceApplicationIdList);
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
	
	public List<InvoiceDocumentBean> searchInvoiceProcess(SearchBillScanBean searchBillScanBean) {
		List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();

		List<Invoice> invoiceList = financialService.findInvoiceTypeSearch(searchBillScanBean);
		for (Invoice invoice : invoiceList) {
			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean = PoppulateInvoiceEntityToDto(invoice);
			invoiceBeanList.add(invoiceDocumentBean);
		}

		return invoiceBeanList;
	}
	
	public InvoiceDocumentBean PoppulateInvoiceEntityToDto(Invoice invoice){
		InvoiceDocumentBean invoiceBean = new InvoiceDocumentBean();
		invoiceBean.setId(invoice.getId());
		invoiceBean.setAmount(invoice.getAmount());
		invoiceBean.setInvoiceCode(invoice.getInvoiceCode());
		invoiceBean.setInvoiceType(invoice.getInvoiceType());
		invoiceBean.setStatus(invoice.getStatus());
		invoiceBean.setVat(invoice.getVat());
		
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		invoiceBean.setCreateDateTh(
				null == invoice.getCreateDate() ? "" : formatDataTh.format(invoice.getCreateDate()));
		invoiceBean.setIssueDocDateTh(
				null == invoice.getIssueDocDate() ? "" : formatDataTh.format(invoice.getIssueDocDate()));
		
		invoiceBean.setPaymentDateTh(
				null == invoice.getPaymentDate() ? "" : formatDataTh.format(invoice.getPaymentDate()));
		//สำหรับการ scan bill
		if(invoice.getPersonnel() !=null){
			PersonnelController personnelController = new PersonnelController();
			 personnelController.setMessageSource(messageSource);
			PersonnelBean personnelBean = personnelController.populateEntityToDto(invoice.getPersonnel());
			invoiceBean.setPersonnelBean(personnelBean);
		}
		//scan out date
		if(invoice.getScanOutDate() !=null){
			invoiceBean.setScanOutDateTh(
				null == invoice.getCreateDate() ? "" : formatDataTh.format(invoice.getScanOutDate()));
		}
		invoiceBean.setBilling(invoice.isBilling());
		
		//service application
		ServiceApplicationBean serviceApplicationBean = populateEntityToDto(invoice.getServiceApplication());
		invoiceBean.setServiceApplication(serviceApplicationBean);
		
		//worksheet
//		if(invoice.getWorkSheet() != null){
//			AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
//			assignWorksheetController.setMessageSource(messageSource);
//			invoiceBean.setWorksheet(assignWorksheetController.populateEntityToDto(invoice.getWorkSheet()));
//		}
		
		//รอบบิลการใช้งาน
		if(invoice.getInvoiceType().equals("O")){
			SimpleDateFormat formateDateServiceReound = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			Date date = invoice.getCreateDate();
			Date startUseDate = date;
			if(null != date){
			Calendar c = Calendar.getInstance(new Locale("EN", "en"));
			c.setTime(date);
			if(invoice.getServiceApplication().getPaymentAfter()){
				c.add(Calendar.DATE, 30);
				Date endUseDate = c.getTime();
				
				invoiceBean.createServiceRoundDate(
						null == startUseDate ? ""
						: formateDateServiceReound.format(endUseDate),
						null == endUseDate ? ""
						: formateDateServiceReound.format(startUseDate));
			}else{
				c.add(Calendar.DATE, -30);
				Date endUseDate = c.getTime();
				
				invoiceBean.createServiceRoundDate(
						null == startUseDate ? ""
						: formateDateServiceReound.format(startUseDate),
						null == endUseDate ? ""
						: formateDateServiceReound.format(endUseDate));
			}
			
			}
		}
		
		return invoiceBean;
	}
	
	public ServiceApplicationBean populateEntityToDtoV2(ServiceApplication serviceApplication) {
		ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
		
		//service application 
		serviceApplicationBean.setId(serviceApplication.getId());
		serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
		
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		serviceApplicationTypeBean.setId(serviceApplication.getServiceApplicationType().getId());
		serviceApplicationTypeBean.setServiceApplicationTypeName(serviceApplication.getServiceApplicationType().getServiceApplicationTypeName());
		serviceApplicationTypeBean.setServiceApplicationTypeCode(serviceApplication.getServiceApplicationType().getServiceApplicationTypeCode());
		serviceApplicationBean.setServiceApplicationTypeBean(serviceApplicationTypeBean);
		
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
//		for (Address address : serviceApplication.getCustomer().getAddresses()) {
//			// เช็คที่อยู่ ลูกค้า ต้องอยู่ใน ใบสมัครนี้ด้วย
//			if(null != address ||
//					null != address.getServiceApplication() ||
//					null != address.getServiceApplication().getId() ||
//					address.getServiceApplication().getId() == serviceApplication.getId()){
//				AddressBean addressBean = new AddressBean();
//				addressBean.setMessageSource(messageSource);
//				addressBean.setId(address.getId());
//				addressBean.setDetail(address.getDetail());
//				addressBean.setNo(address.getNo());
//				addressBean.setAlley(address.getAlley());
//				addressBean.setRoad(address.getRoad());
//				addressBean.setRoom(address.getRoom());
//				addressBean.setFloor(address.getFloor());
//				addressBean.setBuilding(address.getBuilding());
//				addressBean.setSection(address.getSection());
//				addressBean.setPostcode(address.getPostcode());
//				addressBean.setAddressType(address.getAddressType());
//				addressBean.setVillage(address.getVillage());
//				addressBean.setNearbyPlaces(address.getNearbyPlaces());
//				addressBean.setOverrideAddressId(address.getOverrideAddressId());
//				addressBean.setOverrideAddressType(address.getOverrideAddressType());
//				// user bean
//				// province
//				if (address.getProvinceModel() != null) {
//					ProvinceBean provinceBean = new ProvinceBean();
//					provinceBean.setId(address.getProvinceModel().getId());
//					provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
//					addressBean.setProvinceBean(provinceBean);
//				}
//	
//				if (address.getAmphur() != null) {
//					// amphur
//					AmphurBean amphurBean = new AmphurBean();
//					amphurBean.setId(address.getAmphur().getId());
//					amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
//					amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
//					addressBean.setAmphurBean(amphurBean);
//				}
//	
//				if (address.getDistrictModel() != null) {
//					// district
//					DistrictBean districtBean = new DistrictBean();
//					districtBean.setId(address.getDistrictModel().getId());
//					districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
//					addressBean.setDistrictBean(districtBean);
//				}
//	
//				addressBean.collectAddress();
//				addressBeans.add(addressBean);
//			}
//		}
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
				break;
			}
		}
		serviceApplicationBean.setAddressList(addressApplicationBeans);
		//End address for service application
		
		return serviceApplicationBean;
	}
	
	public ServiceApplicationBean populateEntityToDto(ServiceApplication serviceApplication) {
		ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
		
		//service application 
		serviceApplicationBean.setId(serviceApplication.getId());
		serviceApplicationBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
		
		ServiceApplicationTypeBean serviceApplicationTypeBean = new ServiceApplicationTypeBean();
		serviceApplicationTypeBean.setId(serviceApplication.getServiceApplicationType().getId());
		serviceApplicationTypeBean.setServiceApplicationTypeName(serviceApplication.getServiceApplicationType().getServiceApplicationTypeName());
		serviceApplicationTypeBean.setServiceApplicationTypeCode(serviceApplication.getServiceApplicationType().getServiceApplicationTypeCode());
		serviceApplicationBean.setServiceApplicationTypeBean(serviceApplicationTypeBean);
		
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
		
		return serviceApplicationBean;
	}
	
	public List<InvoiceHistoryPrintBean> searchInvoiceHistoryPrintProcess(SearchBillScanBean searchBillScanBean) {
		List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList = new ArrayList<InvoiceHistoryPrintBean>();
		List<InvoiceHistoryPrint> invoiceHistoryPrintList = financialService.findInvoiceHistoryPrintSearch(searchBillScanBean);
		for (InvoiceHistoryPrint invoiceHistoryPrint : invoiceHistoryPrintList) {
			invoiceHistoryPrintBeanList.add(populateEntityToDto(invoiceHistoryPrint));
		}

		return invoiceHistoryPrintBeanList;
	}
	
	public InvoiceHistoryPrintBean populateEntityToDto(InvoiceHistoryPrint invoiceHistoryPrint){
		InvoiceHistoryPrintBean invoiceHistoryPrintBean = new InvoiceHistoryPrintBean();
		InvoiceController invoiceController = new InvoiceController();
		invoiceController.setMessageSource(messageSource);
		if(null != invoiceHistoryPrint){
			invoiceHistoryPrintBean.setId(invoiceHistoryPrint.getId());
			invoiceHistoryPrintBean.setPrintTime(invoiceHistoryPrint.getPrintTime());
			invoiceHistoryPrintBean.setCreateDate(invoiceHistoryPrint.getCreateDate());
			
			if(invoiceHistoryPrint.getPersonnel() !=null){
				PersonnelController personnelController = new PersonnelController();
				 personnelController.setMessageSource(messageSource);
				PersonnelBean personnelBean = personnelController.populateEntityToDto(invoiceHistoryPrint.getPersonnel());
				invoiceHistoryPrintBean.setPersonnelBean(personnelBean);
			}

			if(invoiceHistoryPrint.getAssignPersonnel() !=null){
				PersonnelController personnelController = new PersonnelController();
				 personnelController.setMessageSource(messageSource);
				PersonnelBean assignPersonnelBean = personnelController.populateEntityToDto(invoiceHistoryPrint.getAssignPersonnel());
				invoiceHistoryPrintBean.setAssignPersonnelBean(assignPersonnelBean);
			}

			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean = invoiceController.PoppulateInvoiceEntityToDto(invoiceHistoryPrint.getInvoice());
			invoiceHistoryPrintBean.setInvoiceDocumentBean(invoiceDocumentBean);
			
		}
		
		return invoiceHistoryPrintBean;
	}
	
	@RequestMapping(value="assigninvoice", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse assigninvoice(@RequestBody final List<InvoiceDocumentBean> invoiceDocumentBeans, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		HttpSession session = request.getSession();
		if(isPermission()){
			try{
				logger.info("[method : assigninvoice][Type : Controller]");
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
				if(null != invoiceDocumentBeans && invoiceDocumentBeans.size() > 0){
					session.setAttribute("invoiceDocumentBeanListForReport", invoiceDocumentBeans);
					
					if(null != invoiceDocumentBeans && invoiceDocumentBeans.size() > 0){
					Personnel assignPersonnel = personnelService.getPersonnelById(invoiceDocumentBeans.get(0).getPersonnelBean().getId());
						for(InvoiceDocumentBean invoiceDocumentBean:invoiceDocumentBeans){
							// load invoice
							Invoice invoice = financialService.getInvoiceById(invoiceDocumentBean.getId());
							if (invoice != null) {
								
								//update print time invoice
								List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
								InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
								invoiceHistoryPrint.setCreateDate(CURRENT_TIMESTAMP);
								invoiceHistoryPrint.setDeleted(Boolean.FALSE);
								invoiceHistoryPrint.setCreatedBy(getUserNameLogin());
								invoiceHistoryPrint.setPrintTime(0);
								invoiceHistoryPrint.setInvoice(invoice);
								invoiceHistoryPrint.setPersonnel(getPersonnelLogin());// พนักงานพิมพ์
								invoiceHistoryPrint.setAssignPersonnel(assignPersonnel); //มอบหมายผู้รับผิดชอบ
	
								invoiceHistoryPrintList.add(invoiceHistoryPrint);
								invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
								invoice.setPersonnel(assignPersonnel); //มอบหมายผู้รับผิดชอบล่าสุด
								invoice.setMobile(true);
								financialService.updateInvoice(invoice);
							}
						}
					}
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.assign.success", null, LocaleContextHolder.getLocale()),
				"");
		
		return jsonResponse;
	}
	
	@RequestMapping(value="setassigncashier", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse setassigncashier(@RequestBody final List<ServiceApplicationBean> serviceApplicationBeans, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		HttpSession session = request.getSession();
		if(isPermission()){
			try{
				logger.info("[method : setassigncashier][Type : Controller]");
				
				if(null != serviceApplicationBeans && serviceApplicationBeans.size() > 0){
					Personnel assignPersonnel = personnelService.getPersonnelById(serviceApplicationBeans.get(0).getPersonnelId());
//					session.setAttribute("serviceApplicationBeans", serviceApplicationBeans);
					
//					serviceApplicationService.deleteServiceApplicationAssignCashierByPersonnel(serviceApplicationBeans.get(0).getPersonnelId());
					
					if(null != serviceApplicationBeans && serviceApplicationBeans.size() > 1){
						int i = 0;
						for(ServiceApplicationBean serviceApplicationBean:serviceApplicationBeans){
							if((i++)==0) continue;
							ServiceApplicationAssignCashier serviceApplicationAssignCashier = new ServiceApplicationAssignCashier();
							ServiceApplicationAssignCashierId pk = new ServiceApplicationAssignCashierId();
							
							ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationBean.getId());
							
							pk.setPersonnel(assignPersonnel);
							pk.setServiceApplication(serviceApplication);
							
							serviceApplicationAssignCashier.setPk(pk);
							serviceApplicationService.save(serviceApplicationAssignCashier);
						}
					}
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.cashierconfig.success", null, LocaleContextHolder.getLocale()),
				"");
		
		return jsonResponse;
	}
	
	@RequestMapping(value="deleteAssignCashier", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse deleteAssignCashier(@RequestBody final ServiceApplicationBean serviceApplicationBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				logger.info("[method : deleteAssignCashier][Type : Controller]");

				if(null != serviceApplicationBean){					
					serviceApplicationService.deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId(serviceApplicationBean.getPersonnelId(),serviceApplicationBean.getId());
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
	
	@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
	public ModelAndView exportExcel(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nameReport = "cashier_excel";
		JasperRender jasperRender = new JasperRender();
		JasperJrxmlComponent jasperJrxmlComponent = new JasperJrxmlComponent();
		HttpSession httpSession = request.getSession(true);
		List<Object> resUse = new ArrayList<Object>();
		List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();
		invoiceBeanList = (List<InvoiceDocumentBean>) httpSession.getAttribute("invoiceBeanList");

			List<ReportCashierExcelAllTables> reportCashierExcelAllTables = new ArrayList<ReportCashierExcelAllTables>();

				if(null != invoiceBeanList  && invoiceBeanList.size() > 0){
					ReportCashierExcelBean reportCashierExcelBean = new ReportCashierExcelBean();
					for(InvoiceDocumentBean invoiceDocumentBean:invoiceBeanList){
						ReportCashierExcelAllTables reportCashierExcelAllTable = new ReportCashierExcelAllTables();
						ServiceApplicationBean serviceApplication = invoiceDocumentBean.getServiceApplication();
						reportCashierExcelAllTable.setInvoiceNo(invoiceDocumentBean.getInvoiceCode());
						if(null != serviceApplication){
							CustomerBean customerBean = serviceApplication.getCustomer();
							if(null != customerBean){
								reportCashierExcelAllTable.setCusName(customerBean.getFirstName()+" "+customerBean.getLastName());
								reportCashierExcelAllTable.setCusCode(customerBean.getCustCode());
								List<AddressBean> addressBeanList = customerBean.getAddressList();
								if(null != addressBeanList && addressBeanList.size() > 0){
									reportCashierExcelAllTable.setNo(addressBeanList.get(0).getNo());
									reportCashierExcelAllTable.setNearbyPlaces(addressBeanList.get(0).getNearbyPlaces());
								}
								
							}
							List<AddressBean> addressBeanList = serviceApplication.getAddressList();
							if(null != addressBeanList && addressBeanList.size() > 0){
								for(AddressBean addressBean:addressBeanList){
									if("4".equals(addressBean.getAddressType())){
										reportCashierExcelAllTable.setZoneName(addressBean.getZoneBean().getZoneDetail());
										reportCashierExcelAllTable.setZoneCode(addressBean.getZoneBean().getZoneName());
									}
								}
							}
							reportCashierExcelAllTable.setServiceRoundDate(invoiceDocumentBean.getServiceRoundDate());
							reportCashierExcelAllTables.add(reportCashierExcelAllTable);
						}
					}
					reportCashierExcelBean.setReportCashierExcelAllTables(reportCashierExcelAllTables);
					resUse.add(reportCashierExcelBean);

		}
		jasperRender.setBeanList(resUse);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.EXCEL,
			jasperJrxmlComponent.compileJasperReport(nameReport, request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=cashier.xls");
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
	
//	@RequestMapping(value = "exportdemo", method = RequestMethod.GET)
//	public ModelAndView exportdemo(Model model, HttpServletRequest request, HttpServletResponse response) {
//		logger.info("[method : exportdemo][Type : Controller]");
//		List<Object> resUse = new ArrayList<Object>();
//		
//		JasperRender jasperRender = new JasperRender();
//		ReportWorksheetMainBean reportWorksheetMainBean = new ReportWorksheetMainBean();
//		reportWorksheetMainBean.setWorkSheetCode("W000001");
//		List<BillDocumentBean> billDocumentList = new ArrayList<BillDocumentBean>();
//		BillDocumentBean bean = new BillDocumentBean();
//		bean.setReceiptCode("Tent");
//		billDocumentList.add(bean);
//		
//		bean = new BillDocumentBean();
//		bean.setReceiptCode("Jane");
//		billDocumentList.add(bean);
//		
//		reportWorksheetMainBean.setBillDocumentList(billDocumentList );
//		resUse.add(reportWorksheetMainBean);
//		
//		jasperRender.setBeanList(resUse);
//		
//		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
//		Map<String, Object> e = new HashMap<String, Object>();
//		e.put("workSheetCode", "เทส1");
//		
//		List<Map<String, Object>> billList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> o = new HashMap<String, Object>();
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("receiptCode", "tentjane");
//		billList.add(m);
//		o.put("billDocumentList", billList);
//		o.put("workSheetCode", "เทส2");
//		res.add(o);
////		e = new HashMap<String, Object>();
////		e.put("address", "เทส2");
////		res.add(e);
//		
//		try {
//			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("test",request));
////			byte[] bytes = jasperJrxmlComponent.renderJasperReportPDF("test", res, ParamsEnum.StreamType.PDF, request);
//			
//			response.reset();
//			response.resetBuffer();
//			response.setContentType("application/pdf");
//			response.setContentLength(bytes.length);
//			ServletOutputStream ouputStream = response.getOutputStream();
//			ouputStream.write(bytes, 0, bytes.length);
//			ouputStream.flush();
//			ouputStream.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		return null;
//	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	//getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
	}
}
