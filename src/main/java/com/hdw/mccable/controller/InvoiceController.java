package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.AdditionMonthlyFee;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.BankBean;
import com.hdw.mccable.dto.BillDocumentBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerAmountBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.InvoiceHistoryPrintBean;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.JsonRequestAssignWorksheet;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReceiptBean;
import com.hdw.mccable.dto.ReportInvoiceExcelAllTables;
import com.hdw.mccable.dto.ReportInvoiceExcelBean;
import com.hdw.mccable.dto.ReportInvoiceExcelCTables;
import com.hdw.mccable.dto.ReportInvoiceExcelOTables;
import com.hdw.mccable.dto.ReportInvoiceExcelSTables;
import com.hdw.mccable.dto.ReportInvoiceExcelWTables;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Bank;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.BankService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.NumberFormat;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BaseController{
	final static Logger logger = Logger.getLogger(InvoiceController.class);
	public static final String CONTROLLER_NAME = "invoice/";
	public static final String PAGE_PAYMENT = "payment";
	public static final String key = "";
	public static final String position = "";
	public static final Long permissionGroup = 0L;
	
	// initial service

	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "bankService")
	private BankService bankService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
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
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				// ------ เซต ค้นหาจากวัน ปัจจุบัน + ไปอีก 7 วัน
				String daterange = "";
				String startDate = "";
				String endDate = "";
				SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
				SimpleDateFormat formatDataEn = new SimpleDateFormat("dd/MM/yyyy", new Locale("en", "EN"));
				Date currentDate = new Date();
				startDate = formatDataEn.format(currentDate);
				
		        // convert date to calendar
		        Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DAY_OF_MONTH, 7);
		        
		        Date currentDatePlus7 = c.getTime();
		        endDate = formatDataEn.format(currentDatePlus7);
		        daterange = String.format("%1s - %2s", startDate,endDate);
		        
		        daterange = ""; // แก้ไขให้เป็น null ก่อน
				// ------
		        
				InvoiceSearchBean invoiceSearchBean = new InvoiceSearchBean();
				
				invoiceSearchBean.setDaterange(daterange);
				invoiceSearchBean.setDaterange1(daterange);
				invoiceSearchBean.setStatus("");
				
				session.setAttribute("invoiceSearchBeanExcel", invoiceSearchBean);
				
				Pagination pagination = createPagination(1, 10, "All", "invoice", invoiceSearchBean);
				
				invoiceSearchBean.setStatus("W");
				Pagination pagination_w = createPagination(1, 10, "All", "invoice", invoiceSearchBean);
				
				invoiceSearchBean.setStatus("O");
				Pagination pagination_o = createPagination(1, 10, "All", "invoice", invoiceSearchBean);
				
				invoiceSearchBean.setStatus("S");
				Pagination pagination_s = createPagination(1, 10, "All", "invoice", invoiceSearchBean);
				
				invoiceSearchBean.setStatus("C");
				Pagination pagination_c = createPagination(1, 10, "All", "invoice", invoiceSearchBean);
				
				modelAndView.addObject("invoiceSearchBean",invoiceSearchBean);
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("pagination_w",pagination_w);
				modelAndView.addObject("pagination_o",pagination_o);
				modelAndView.addObject("pagination_s",pagination_s);
				modelAndView.addObject("pagination_c",pagination_c);
				
				List<Company> companys = companyService.findAll();
				List<CompanyBean> companyList = new ArrayList<CompanyBean>();
				if(companys != null){
					for(Company company : companys){
						CompanyBean companySubBean = new CompanyBean();
						companySubBean = populateEntityToDto(company);
						companyList.add(companySubBean);
					}
				}
				modelAndView.addObject("companyList", companyList);
				
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
	
	@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
	public ModelAndView exportExcel(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nameReport = "invoice_excel";
		JasperRender jasperRender = new JasperRender();
		JasperJrxmlComponent jasperJrxmlComponent = new JasperJrxmlComponent();
		HttpSession httpSession = request.getSession(true);
		List<Object> resUse = new ArrayList<Object>();
		InvoiceSearchBean invoiceSearchBean = (InvoiceSearchBean) httpSession.getAttribute("invoiceSearchBeanExcel");
		if(null != invoiceSearchBean){
			invoiceSearchBean.setStatus("");
			Pagination pagination = new Pagination();
			pagination.setTotalItem(9999);
			pagination.setCurrentPage(1);
			pagination.setItemPerPage(9999);
			pagination = financialService.getByPageForOrderInvoice(pagination, invoiceSearchBean,Boolean.TRUE);
			
			// ALL
			List<ReportInvoiceExcelAllTables> reportInvoiceExcelAllTablesList = new ArrayList<ReportInvoiceExcelAllTables>();
			for(Invoice invoice : (List<Invoice>)pagination.getDataList()){
				InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
				if(null != invoiceDocumentBean){
					ReportInvoiceExcelAllTables reportInvoiceExcelBean = new ReportInvoiceExcelAllTables();
					reportInvoiceExcelBean.setInvoiceNo(invoice.getInvoiceCode());
					String cusName = "";
					Customer customer = invoice.getServiceApplication().getCustomer();
					if(null != customer){
						cusName = customer.getPrefix()+customer.getFirstName()+" "+customer.getLastName();
					}
					reportInvoiceExcelBean.setCusName(cusName);
					reportInvoiceExcelBean.setCusCode(customer.getCustCode());
					
					String zoneName = "",zoneCode = "";
					List<Address> addressList = invoice.getServiceApplication().getAddresses();
					if(null != addressList && addressList.size() > 0){
						for(Address address:addressList){
							if("4".equals(address.getAddressType())){
								zoneName = address.getZone().getZoneDetail();
								zoneCode = address.getZone().getZoneName();
								break;
							}
						}
					}
					reportInvoiceExcelBean.setZoneName(zoneName);
					reportInvoiceExcelBean.setZoneCode(zoneCode);
					reportInvoiceExcelBean.setPointTotal(invoiceDocumentBean.getQuantityTotalPoint());
					reportInvoiceExcelBean.setPrice(invoiceDocumentBean.getAmount());
					String status = invoiceDocumentBean.getStatus();
					reportInvoiceExcelBean.setStatus(status);
					if("C".equals(status)){
						status = "ยกเลิกบิล";
					}else if("W".equals(status)){
						status = "รอลูกค้าชำระเงิน";
					}else if("O".equals(status)){
						status = "เกินวันนัดชำระเงิน";
					}else if("S".equals(status)){
						status = "ชำระเงินเรียบร้อย";
					}
					reportInvoiceExcelBean.setStatus(status);

					if(invoiceDocumentBean.isBadDebt()){
						String badDebt = "หนี้สูญ";
						reportInvoiceExcelBean.setBadDebt(badDebt);
					}else if(invoiceDocumentBean.isBilling()){
						String billing = "วางบิลแล้ว";
						reportInvoiceExcelBean.setBilling(billing);
					}
					
					reportInvoiceExcelBean.setPaymentDate(invoiceDocumentBean.getCreateDateTh());
					
					reportInvoiceExcelBean.setPackageService(invoiceDocumentBean.getServiceApplication().getServicepackage().getPackageName());
					reportInvoiceExcelBean.setServiceApplicationType(invoiceDocumentBean.getServiceApplication().getServiceApplicationTypeBean().getServiceApplicationTypeName());
					
					reportInvoiceExcelAllTablesList.add(reportInvoiceExcelBean);
				}
			}
			Collections.sort(reportInvoiceExcelAllTablesList, new Comparator<ReportInvoiceExcelAllTables>() {
				public int compare(final ReportInvoiceExcelAllTables o1, final ReportInvoiceExcelAllTables o2) {
					String a1 = o1.getCusCode();
					String a2 = o2.getCusCode();
					return a1.compareTo(a2); // น้อยไปมาก
				}
			});
			int i = 1;
			for(ReportInvoiceExcelAllTables reportInvoiceExcelBean : reportInvoiceExcelAllTablesList){
				reportInvoiceExcelBean.setNo(""+(i++));
			}
			// ALL
			
			// W
			List<ReportInvoiceExcelWTables> reportInvoiceExcelWTablesList = new ArrayList<ReportInvoiceExcelWTables>();
			for(Invoice invoice : (List<Invoice>)pagination.getDataList()){
				InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
				if(null != invoiceDocumentBean){
					String status = invoiceDocumentBean.getStatus();
					if("W".equals(status)){
						ReportInvoiceExcelWTables reportInvoiceExcelBean = new ReportInvoiceExcelWTables();
						reportInvoiceExcelBean.setInvoiceNo(invoice.getInvoiceCode());
						String cusName = "";
						Customer customer = invoice.getServiceApplication().getCustomer();
						if(null != customer){
							cusName = customer.getPrefix()+customer.getFirstName()+" "+customer.getLastName();
						}
						reportInvoiceExcelBean.setCusName(cusName);
						reportInvoiceExcelBean.setCusCode(customer.getCustCode());
						
						String zoneName = "",zoneCode = "";
						List<Address> addressList = invoice.getServiceApplication().getAddresses();
						if(null != addressList && addressList.size() > 0){
							for(Address address:addressList){
								if("4".equals(address.getAddressType())){
									zoneName = address.getZone().getZoneDetail();
									zoneCode = address.getZone().getZoneName();
									break;
								}
							}
						}
						reportInvoiceExcelBean.setZoneName(zoneName);
						reportInvoiceExcelBean.setZoneCode(zoneCode);
						reportInvoiceExcelBean.setPointTotal(invoiceDocumentBean.getQuantityTotalPoint());
						reportInvoiceExcelBean.setPrice(invoiceDocumentBean.getAmount());
						reportInvoiceExcelBean.setStatus(status);
						if("C".equals(status)){
							status = "ยกเลิกบิล";
						}else if("W".equals(status)){
							status = "รอลูกค้าชำระเงิน";
						}else if("O".equals(status)){
							status = "เกินวันนัดชำระเงิน";
						}else if("S".equals(status)){
							status = "ชำระเงินเรียบร้อย";
						}
						reportInvoiceExcelBean.setStatus(status);
						
						if(invoiceDocumentBean.isBadDebt()){
							String badDebt = "หนี้สูญ";
							reportInvoiceExcelBean.setBadDebt(badDebt);
						}else if(invoiceDocumentBean.isBilling()){
							String billing = "วางบิลแล้ว";
							reportInvoiceExcelBean.setBilling(billing);
						}
						
						reportInvoiceExcelBean.setPaymentDate(invoiceDocumentBean.getCreateDateTh());
						
						reportInvoiceExcelBean.setPackageService(invoiceDocumentBean.getServiceApplication().getServicepackage().getPackageName());
						reportInvoiceExcelBean.setServiceApplicationType(invoiceDocumentBean.getServiceApplication().getServiceApplicationTypeBean().getServiceApplicationTypeName());
						
						reportInvoiceExcelWTablesList.add(reportInvoiceExcelBean);
					}
				}
			}
			Collections.sort(reportInvoiceExcelWTablesList, new Comparator<ReportInvoiceExcelWTables>() {
				public int compare(final ReportInvoiceExcelWTables o1, final ReportInvoiceExcelWTables o2) {
					String a1 = o1.getCusCode();
					String a2 = o2.getCusCode();
					return a1.compareTo(a2); // น้อยไปมาก
				}
			});
			i = 1;
			for(ReportInvoiceExcelWTables reportInvoiceExcelBean : reportInvoiceExcelWTablesList){
				reportInvoiceExcelBean.setNo(""+(i++));
			}
			// W
			
			// O
			List<ReportInvoiceExcelOTables> reportInvoiceExcelOTablesList = new ArrayList<ReportInvoiceExcelOTables>();
			for(Invoice invoice : (List<Invoice>)pagination.getDataList()){
				InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
				if(null != invoiceDocumentBean){
					String status = invoiceDocumentBean.getStatus();
					if("O".equals(status)){
						ReportInvoiceExcelOTables reportInvoiceExcelBean = new ReportInvoiceExcelOTables();
						reportInvoiceExcelBean.setInvoiceNo(invoice.getInvoiceCode());
						String cusName = "";
						Customer customer = invoice.getServiceApplication().getCustomer();
						if(null != customer){
							cusName = customer.getPrefix()+customer.getFirstName()+" "+customer.getLastName();
						}
						reportInvoiceExcelBean.setCusName(cusName);
						reportInvoiceExcelBean.setCusCode(customer.getCustCode());
						
						String zoneName = "",zoneCode = "";
						List<Address> addressList = invoice.getServiceApplication().getAddresses();
						if(null != addressList && addressList.size() > 0){
							for(Address address:addressList){
								if("4".equals(address.getAddressType())){
									zoneName = address.getZone().getZoneDetail();
									zoneCode = address.getZone().getZoneName();
									break;
								}
							}
						}
						reportInvoiceExcelBean.setZoneName(zoneName);
						reportInvoiceExcelBean.setZoneCode(zoneCode);
						reportInvoiceExcelBean.setPointTotal(invoiceDocumentBean.getQuantityTotalPoint());
						reportInvoiceExcelBean.setPrice(invoiceDocumentBean.getAmount());
						reportInvoiceExcelBean.setStatus(status);
						if("C".equals(status)){
							status = "ยกเลิกบิล";
						}else if("W".equals(status)){
							status = "รอลูกค้าชำระเงิน";
						}else if("O".equals(status)){
							status = "เกินวันนัดชำระเงิน";
						}else if("S".equals(status)){
							status = "ชำระเงินเรียบร้อย";
						}
						reportInvoiceExcelBean.setStatus(status);
						
						if(invoiceDocumentBean.isBadDebt()){
							String badDebt = "หนี้สูญ";
							reportInvoiceExcelBean.setBadDebt(badDebt);
						}else if(invoiceDocumentBean.isBilling()){
							String billing = "วางบิลแล้ว";
							reportInvoiceExcelBean.setBilling(billing);
						}
						
						reportInvoiceExcelBean.setPaymentDate(invoiceDocumentBean.getCreateDateTh());
						
						reportInvoiceExcelBean.setPackageService(invoiceDocumentBean.getServiceApplication().getServicepackage().getPackageName());
						reportInvoiceExcelBean.setServiceApplicationType(invoiceDocumentBean.getServiceApplication().getServiceApplicationTypeBean().getServiceApplicationTypeName());
						
						reportInvoiceExcelOTablesList.add(reportInvoiceExcelBean);
					}
				}
			}
			Collections.sort(reportInvoiceExcelOTablesList, new Comparator<ReportInvoiceExcelOTables>() {
				public int compare(final ReportInvoiceExcelOTables o1, final ReportInvoiceExcelOTables o2) {
					String a1 = o1.getCusCode();
					String a2 = o2.getCusCode();
					return a1.compareTo(a2); // น้อยไปมาก
				}
			});
			i = 1;
			for(ReportInvoiceExcelOTables reportInvoiceExcelBean : reportInvoiceExcelOTablesList){
				reportInvoiceExcelBean.setNo(""+(i++));
			}
			// O
			
			// S
			List<ReportInvoiceExcelSTables> reportInvoiceExcelSTablesList = new ArrayList<ReportInvoiceExcelSTables>();
			for (Invoice invoice : (List<Invoice>) pagination.getDataList()) {
				InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
				if (null != invoiceDocumentBean) {
					String status = invoiceDocumentBean.getStatus();
					if ("S".equals(status)) {
						ReportInvoiceExcelSTables reportInvoiceExcelBean = new ReportInvoiceExcelSTables();
						reportInvoiceExcelBean.setInvoiceNo(invoice.getInvoiceCode());
						String cusName = "";
						Customer customer = invoice.getServiceApplication().getCustomer();
						if (null != customer) {
							cusName = customer.getPrefix() + customer.getFirstName() + " " + customer.getLastName();
						}
						reportInvoiceExcelBean.setCusName(cusName);
						reportInvoiceExcelBean.setCusCode(customer.getCustCode());

						String zoneName = "", zoneCode = "";
						List<Address> addressList = invoice.getServiceApplication().getAddresses();
						if (null != addressList && addressList.size() > 0) {
							for (Address address : addressList) {
								if ("4".equals(address.getAddressType())) {
									zoneName = address.getZone().getZoneDetail();
									zoneCode = address.getZone().getZoneName();
									break;
								}
							}
						}
						reportInvoiceExcelBean.setZoneName(zoneName);
						reportInvoiceExcelBean.setZoneCode(zoneCode);
						reportInvoiceExcelBean.setPointTotal(invoiceDocumentBean.getQuantityTotalPoint());
						reportInvoiceExcelBean.setPrice(invoiceDocumentBean.getAmount());
						reportInvoiceExcelBean.setStatus(status);
						if ("C".equals(status)) {
							status = "ยกเลิกบิล";
						} else if ("W".equals(status)) {
							status = "รอลูกค้าชำระเงิน";
						} else if ("O".equals(status)) {
							status = "เกินวันนัดชำระเงิน";
						} else if ("S".equals(status)) {
							status = "ชำระเงินเรียบร้อย";
						}
						reportInvoiceExcelBean.setStatus(status);
						
						if(invoiceDocumentBean.isBadDebt()){
							String badDebt = "หนี้สูญ";
							reportInvoiceExcelBean.setBadDebt(badDebt);
						}else if(invoiceDocumentBean.isBilling()){
							String billing = "วางบิลแล้ว";
							reportInvoiceExcelBean.setBilling(billing);
						}
						
						reportInvoiceExcelBean.setPaymentDate(invoiceDocumentBean.getCreateDateTh());

						reportInvoiceExcelBean.setPackageService(
								invoiceDocumentBean.getServiceApplication().getServicepackage().getPackageName());
						reportInvoiceExcelBean.setServiceApplicationType(invoiceDocumentBean.getServiceApplication()
								.getServiceApplicationTypeBean().getServiceApplicationTypeName());

						reportInvoiceExcelSTablesList.add(reportInvoiceExcelBean);
					}
				}
			}
			Collections.sort(reportInvoiceExcelSTablesList, new Comparator<ReportInvoiceExcelSTables>() {
				public int compare(final ReportInvoiceExcelSTables o1, final ReportInvoiceExcelSTables o2) {
					String a1 = o1.getCusCode();
					String a2 = o2.getCusCode();
					return a1.compareTo(a2); // น้อยไปมาก
				}
			});
			i = 1;
			for (ReportInvoiceExcelSTables reportInvoiceExcelBean : reportInvoiceExcelSTablesList) {
				reportInvoiceExcelBean.setNo("" + (i++));
			}
			// S
			
			// C
			List<ReportInvoiceExcelCTables> reportInvoiceExcelCTablesList = new ArrayList<ReportInvoiceExcelCTables>();
			for (Invoice invoice : (List<Invoice>) pagination.getDataList()) {
				InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
				if (null != invoiceDocumentBean) {
					String status = invoiceDocumentBean.getStatus();
					if ("C".equals(status)) {
						ReportInvoiceExcelCTables reportInvoiceExcelBean = new ReportInvoiceExcelCTables();
						reportInvoiceExcelBean.setInvoiceNo(invoice.getInvoiceCode());
						String cusName = "";
						Customer customer = invoice.getServiceApplication().getCustomer();
						if (null != customer) {
							cusName = customer.getPrefix() + customer.getFirstName() + " " + customer.getLastName();
						}
						reportInvoiceExcelBean.setCusName(cusName);
						reportInvoiceExcelBean.setCusCode(customer.getCustCode());

						String zoneName = "", zoneCode = "";
						List<Address> addressList = invoice.getServiceApplication().getAddresses();
						if (null != addressList && addressList.size() > 0) {
							for (Address address : addressList) {
								if ("4".equals(address.getAddressType())) {
									zoneName = address.getZone().getZoneDetail();
									zoneCode = address.getZone().getZoneName();
									break;
								}
							}
						}
						reportInvoiceExcelBean.setZoneName(zoneName);
						reportInvoiceExcelBean.setZoneCode(zoneCode);
						reportInvoiceExcelBean.setPointTotal(invoiceDocumentBean.getQuantityTotalPoint());
						reportInvoiceExcelBean.setPrice(invoiceDocumentBean.getAmount());
						reportInvoiceExcelBean.setStatus(status);
						if ("C".equals(status)) {
							status = "ยกเลิกบิล";
						} else if ("W".equals(status)) {
							status = "รอลูกค้าชำระเงิน";
						} else if ("O".equals(status)) {
							status = "เกินวันนัดชำระเงิน";
						} else if ("S".equals(status)) {
							status = "ชำระเงินเรียบร้อย";
						}
						reportInvoiceExcelBean.setStatus(status);
						
						if(invoiceDocumentBean.isBadDebt()){
							String badDebt = "หนี้สูญ";
							reportInvoiceExcelBean.setBadDebt(badDebt);
						}else if(invoiceDocumentBean.isBilling()){
							String billing = "วางบิลแล้ว";
							reportInvoiceExcelBean.setBilling(billing);
						}
						
						reportInvoiceExcelBean.setPaymentDate(invoiceDocumentBean.getCreateDateTh());

						reportInvoiceExcelBean.setPackageService(
								invoiceDocumentBean.getServiceApplication().getServicepackage().getPackageName());
						reportInvoiceExcelBean.setServiceApplicationType(invoiceDocumentBean.getServiceApplication()
								.getServiceApplicationTypeBean().getServiceApplicationTypeName());

						reportInvoiceExcelCTablesList.add(reportInvoiceExcelBean);
					}
				}
			}
			Collections.sort(reportInvoiceExcelCTablesList, new Comparator<ReportInvoiceExcelCTables>() {
				public int compare(final ReportInvoiceExcelCTables o1, final ReportInvoiceExcelCTables o2) {
					String a1 = o1.getCusCode();
					String a2 = o2.getCusCode();
					return a1.compareTo(a2); // น้อยไปมาก
				}
			});
			i = 1;
			for (ReportInvoiceExcelCTables reportInvoiceExcelBean : reportInvoiceExcelCTablesList) {
				reportInvoiceExcelBean.setNo("" + (i++));
			}
			// C
			
			List<ReportInvoiceExcelBean> reportInvoiceExcelBeanList = new ArrayList<ReportInvoiceExcelBean>();
			ReportInvoiceExcelBean reportInvoiceExcelBean = new ReportInvoiceExcelBean();
			// ALL
			reportInvoiceExcelBean.setReportInvoiceExcelAllTables(reportInvoiceExcelAllTablesList);
			// W
			reportInvoiceExcelBean.setReportInvoiceExcelWTables(reportInvoiceExcelWTablesList);
			// O
			reportInvoiceExcelBean.setReportInvoiceExcelOTables(reportInvoiceExcelOTablesList);
			// S
			reportInvoiceExcelBean.setReportInvoiceExcelSTables(reportInvoiceExcelSTablesList);
			// C
			reportInvoiceExcelBean.setReportInvoiceExcelCTables(reportInvoiceExcelCTablesList);
			
			reportInvoiceExcelBeanList.add(reportInvoiceExcelBean);
			
			resUse.addAll(reportInvoiceExcelBeanList);
		}
		jasperRender.setBeanList(resUse);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.EXCEL,
			jasperJrxmlComponent.compileJasperReport(nameReport, request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=invoice.xls");
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
	
	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}/tab/{tab}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, 
			@PathVariable String tab, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
		ModelAndView modelAndView = new ModelAndView();
		ZoneController zoneController = new ZoneController();

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
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				// search worksheet bean
				InvoiceSearchBean invoiceSearchBean = (InvoiceSearchBean) session.getAttribute("invoiceSearchBean");
				//search process and pagination
				Pagination pagination = null;
				Pagination pagination_w = null;
				Pagination pagination_o = null;
				Pagination pagination_s = null;
				Pagination pagination_c = null;
				
				if(invoiceSearchBean != null){
					
					invoiceSearchBean.setStatus("");
					session.setAttribute("invoiceSearchBeanExcel", invoiceSearchBean);
					pagination = createPagination(id, itemPerPage, tab, "invoice",invoiceSearchBean);
					
					invoiceSearchBean.setStatus("W");
					pagination_w = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("O");
					pagination_o = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("S");
					pagination_s = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("C");
					pagination_c = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);					
				}else{
					invoiceSearchBean = new InvoiceSearchBean();
					session.setAttribute("invoiceSearchBeanExcel", invoiceSearchBean);
					pagination = createPagination(id, itemPerPage, tab, "invoice",invoiceSearchBean);
					
					invoiceSearchBean.setStatus("W");
					pagination_w = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("O");
					pagination_o = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("S");
					pagination_s = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
					
					invoiceSearchBean.setStatus("C");
					pagination_c = createPagination(id, itemPerPage, tab, "invoice", invoiceSearchBean);
				}
				
				modelAndView.addObject("invoiceSearchBean",invoiceSearchBean);
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("pagination_w",pagination_w);
				modelAndView.addObject("pagination_o",pagination_o);
				modelAndView.addObject("pagination_s",pagination_s);
				modelAndView.addObject("pagination_c",pagination_c);
				
				List<Company> companys = companyService.findAll();
				List<CompanyBean> companyList = new ArrayList<CompanyBean>();
				if(companys != null){
					for(Company company : companys){
						CompanyBean companySubBean = new CompanyBean();
						companySubBean = populateEntityToDto(company);
						companyList.add(companySubBean);
					}
				}
				modelAndView.addObject("companyList", companyList);
				
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
				
				HttpSession httpSession = request.getSession(true);
				httpSession.setAttribute("invoiceSearchBean", invoiceSearchBean);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	// create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String tab, String controller, InvoiceSearchBean invoiceSearchBean) {
		if (itemPerPage == 0) itemPerPage = 10;
		if("".equals(tab)) tab = "All";
		invoiceSearchBean.setItemPerPage(""+itemPerPage);
		invoiceSearchBean.setTab(tab);
		
//		Personnel personnel = getPersonnelLogin();
//		if(null != personnel){
//			if(personnel.getId()!=1 && personnel.isCashier()){
//				invoiceSearchBean.setPersonnelId(personnel.getId());
//			}else{
//				invoiceSearchBean.setPersonnelId(null);
//			}
//		}else{
//			invoiceSearchBean.setPersonnelId(null);
//		}
		
		invoiceSearchBean.setPersonnelId(null);
		
		Pagination pagination = new Pagination();
		pagination.setTotalItem(financialService.getCountTotalOrderInvoice(invoiceSearchBean,Boolean.TRUE));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = financialService.getByPageForOrderInvoice(pagination, invoiceSearchBean,Boolean.TRUE);
		List<InvoiceDocumentBean> invoiceBeansValidateSize = (List<InvoiceDocumentBean>) pagination.getDataList();

		if (invoiceBeansValidateSize.size() <= 0) {
			pagination.setCurrentPage(1);
			pagination = financialService.getByPageForOrderInvoice(pagination, invoiceSearchBean,Boolean.TRUE);
		}
		
//		InvoiceBean invoiceBean
		List<InvoiceDocumentBean> invoiceBeans = new ArrayList<InvoiceDocumentBean>();
		for(Invoice invoice : (List<Invoice>)pagination.getDataList()){
			InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
			if(null != invoiceDocumentBean)invoiceBeans.add(invoiceDocumentBean);
		}
		pagination.setDataListBean(invoiceBeans);
		// end populate
		return pagination;
	}
		
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchInvoice(
			@ModelAttribute("searchInvoice") InvoiceSearchBean invoiceSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchInvoice][Type : Controller]");
		logger.info("[method : searchInvoice][invoiceSearchBean : " + invoiceSearchBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(invoiceSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/invoice/page/1/itemPerPage/"+invoiceSearchBean.getItemPerPage()+"/tab/"+invoiceSearchBean.getTab());
		return modelAndView;
	}
	
	// search request with key search
	@RequestMapping(value = "/search/{key}", method = RequestMethod.GET)
	public ModelAndView searchInvoiceWithKeySearch(
			@ModelAttribute("searchInvoice") InvoiceSearchBean invoiceSearchBean,
			HttpServletRequest request, @PathVariable String key, HttpServletResponse httpResponse) throws IOException {

		invoiceSearchBean.setKey(key);
		
		logger.info("[method : searchInvoiceWithKeySearch][Type : Controller]");
		logger.info("[method : searchInvoiceWithKeySearch][invoiceSearchBean : " + invoiceSearchBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(invoiceSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/invoice/page/1/itemPerPage/10/tab/All");
		return modelAndView;
	}
	
	// create search session
	public void generateSearchSession(InvoiceSearchBean invoiceSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("invoiceSearchBean", invoiceSearchBean);
	}
	
	//view invoice
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : view][Type : Controller]");
		logger.info("[method : view][id : " + id + "]");
		
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
				// set isCashier
				Boolean isCashier = false;
				Personnel personnel = getPersonnelLogin();
				if(null != personnel){
					if(personnel.isCashier()){
						isCashier = true;
					}
				}
				modelAndView.addObject("isCashier", isCashier);
				
				//load invoice
				Invoice invoice = financialService.getInvoiceById(id);				
				
				if(invoice != null){
					InvoiceDocumentBean invoiceBean = PoppulateInvoiceEntityToDto(invoice);
					
					//หาค่าบริการเพิ่ม และลด
					float addPointAmount = 0.0f;
					float reductPointAmount = 0.0f;
					for(Worksheet ws : invoice.getServiceApplication().getWorksheets()){
						if(ws.getUpdatedDate() != null && ws.getUpdatedDate().before(invoice.getCreateDate())){
						//เพิ่มจุด
						if("S".equals(ws.getStatus()) && "C_AP".equals(ws.getWorkSheetType())){
							if(ws.getWorksheetAddPoint() != null)addPointAmount = addPointAmount + ws.getWorksheetAddPoint().getMonthlyFree();
						}
						//ลดจุด
						else if("S".equals(ws.getStatus()) && "C_RP".equals(ws.getWorkSheetType())){
							if(ws.getWorksheetReducePoint() != null)reductPointAmount = reductPointAmount + ws.getWorksheetReducePoint().getMonthlyFree();
						}
					  }
					}
					
					AdditionMonthlyFee additionMonthlyFee = new AdditionMonthlyFee();
					if(addPointAmount == reductPointAmount){
						additionMonthlyFee.setType("N");
						
					}else if(addPointAmount > reductPointAmount){
						additionMonthlyFee.setType("A");
						additionMonthlyFee.setAmount(addPointAmount - reductPointAmount);
						
					}else if(addPointAmount < reductPointAmount){
						additionMonthlyFee.setType("R");
						additionMonthlyFee.setAmount(reductPointAmount - addPointAmount);
					}
					invoiceBean.setAdditionMonthlyFee(additionMonthlyFee);
					//End หาค่าบริการเพิ่ม และลด
					
					//ส่วนลดพิเศษ
					invoiceBean.setCutting(invoice.isCutting());
					if(invoice.isCutting()){
						for(Worksheet ws : invoice.getServiceApplication().getWorksheets()){
							if("C_CU".equals(ws.getWorkSheetType())){
								invoiceBean.setSpecialDiscount(ws.getWorksheetCut().getSpecialDiscount());
							}
						}
					}		
					
					modelAndView.addObject("invoiceBean",invoiceBean);
										
					CompanyController companyController = new CompanyController();
					companyController.setMessageSource(messageSource);
					CompanyBean companyBean = companyController.populateEntityToDto(invoice.getServiceApplication().getServicePackage().getCompany());
					modelAndView.addObject("companyBean",companyBean);
					
					//install date success
					SimpleDateFormat formatDataTh = new SimpleDateFormat(
							messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					String dateSuccessInstall = null;
					for(Worksheet ws : invoice.getServiceApplication().getWorksheets()){
						if("S".equals(ws.getStatus()) && "C_S".equals(ws.getWorkSheetType())){
							
							dateSuccessInstall = null == ws.getDateOrderBill() ? 
							messageSource.getMessage("financial.invoice.inprocess", null, LocaleContextHolder.getLocale()) 
							: formatDataTh.format(ws.getDateOrderBill());
						}
					}
					modelAndView.addObject("dateSuccessInstall",dateSuccessInstall);
					

					// Set Round Service
					SimpleDateFormat formateDateServiceReound = new SimpleDateFormat(
							messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					Date date = invoice.getCreateDate();
					Date endUseDate = date;
					
					Calendar c = Calendar.getInstance(new Locale("EN", "en"));
					c.setTime(date);
					int perMounth = 0;
					if(invoice.getServiceApplication().isMonthlyService()){
						perMounth = invoice.getServiceApplication().getServicePackage().getPerMounth();
					}
					System.out.println("perMounth : "+perMounth);
					if(invoice.getServiceApplication().getPaymentAfter()){
						perMounth = -perMounth;
					}
					c.add(Calendar.MONTH, perMounth);
//					c.add(Calendar.DAY_OF_MONTH, -1);
					Date startUseDate = c.getTime();
					
					String startService = null == startUseDate ? "" : formateDateServiceReound.format(startUseDate);
					String endService = null == endUseDate ? "" : formateDateServiceReound.format(endUseDate);
					if (null != startService) {
						if(invoice.getServiceApplication().getPaymentAfter()){
							String roundService = "(" + startService + " - " + endService + ")";
							modelAndView.addObject("roundService",roundService);
						}else{
							String roundService = "(" + endService + " - " + startService + ")";
							modelAndView.addObject("roundService",roundService);
						}
						
						if(null != invoice.getWorkSheet() && "C_S".equals(invoice.getWorkSheet().getWorkSheetType())){
							SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", new Locale("TH", "th"));
							modelAndView.addObject("roundService",format.format(new Date()));
							
							format = new SimpleDateFormat("dd", new Locale("en", "US"));
							// คำนวนค่าบริการคิดรอบบิลแรกเป็นรายวัน
							float price = invoiceBean.getServiceApplication().getMonthlyServiceFee();
							int lastDateOfmonth = new DateUtil().lastDateOfmonth(new Date());
							int currentDateOfmonth = Integer.parseInt(format.format(new Date()));
							
							int dateTotal = lastDateOfmonth - currentDateOfmonth;
							float pricePerDay = price/lastDateOfmonth;
							float totalPrice = pricePerDay*dateTotal;
							
							modelAndView.addObject("dateTotal",dateTotal);
							modelAndView.addObject("pricePerDay",pricePerDay);
							modelAndView.addObject("totalPrice",totalPrice);
						}
						
					}
					
					//รอบบิลกรณีรายเดือน
					String roundServiceTypeO = "";
					if("O".equals(invoice.getInvoiceType())){
						if(invoice.getCreateDate()!=null){
							Calendar cTypeO = Calendar.getInstance(new Locale("EN", "en"));
							cTypeO.setTime(invoice.getCreateDate());
							int perMounthTypeO = 0;
							if(invoice.getServiceApplication().isMonthlyService()){
								perMounthTypeO = invoice.getServiceApplication().getServicePackage().getPerMounth();
							}
							System.out.println("perMounthTypeO : " + perMounthTypeO);
							if(invoice.getServiceApplication().getPaymentAfter()){
								perMounthTypeO = -perMounthTypeO;
							}
							cTypeO.add(Calendar.MONTH,perMounthTypeO);
//							cTypeO.add(Calendar.DAY_OF_MONTH, -1);
							Date endUseDateTypeO = cTypeO.getTime();
							
							String startServiceTypeO = null == invoice.getCreateDate() ? "" : formateDateServiceReound.format(invoice.getCreateDate());
							String endServiceTypeO = null == endUseDateTypeO ? "" : formateDateServiceReound.format(endUseDateTypeO);
							
							if(invoice.getServiceApplication().getPaymentAfter()){
								roundServiceTypeO = "(" + endServiceTypeO + " - " + startServiceTypeO + ")";
								modelAndView.addObject("roundServiceTypeO",roundServiceTypeO);
							}else{
								roundServiceTypeO = "(" + startServiceTypeO + " - " + endServiceTypeO + ")";
								modelAndView.addObject("roundServiceTypeO",roundServiceTypeO);
							}
						}
					}
				}
				
				List<Company> companys = companyService.findAll();
				List<CompanyBean> companyList = new ArrayList<CompanyBean>();
				if(companys != null){
					for(Company company : companys){
						CompanyBean companySubBean = new CompanyBean();
						companySubBean = populateEntityToDto(company);
						companyList.add(companySubBean);
					}
				}
				modelAndView.addObject("companyList", companyList);
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

		modelAndView.setViewName(CONTROLLER_NAME + VIEW);
		return modelAndView;
	}
	
	public CompanyBean populateEntityToDto(Company company){
		CompanyBean companyBean = new CompanyBean();
		
		companyBean.setId(company.getId());
		companyBean.setCompanyName(company.getCompanyName());
		companyBean.setTaxId(company.getTaxId());
		companyBean.setVat(company.getVat());
		companyBean.setInvCredit(company.getInvCredit());
		//set contact
		ContactBean contactBean = new ContactBean();
		contactBean.setMobile(company.getContact().getMobile());
		contactBean.setEmail(company.getContact().getEmail());
		contactBean.setFax(company.getContact().getFax());
		companyBean.setContact(contactBean);
		//set address
		AddressBean addressBean = new AddressBean();
		addressBean.setDetail(company.getAddress().getDetail());
		companyBean.setAddress(addressBean);
		
		return companyBean;
	}
	
	//payment invoice
	@RequestMapping(value = "payment/{id}", method = RequestMethod.GET)
	public ModelAndView payment(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : payment][Type : Controller]");
		logger.info("[method : payment][id : " + id + "]");

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
				//bank
				List<Bank> bankList = bankService.findAll();
				List<BankBean> bankBeanList = convertBankEntityToDto(bankList);
				modelAndView.addObject("bankBeanList",bankBeanList);
				
				//load invoice
				Invoice invoice = financialService.getInvoiceById(id);
				if(invoice != null){
					InvoiceDocumentBean invoiceBean = PoppulateInvoiceEntityToDto(invoice);
					if(null != invoiceBean.getWorksheet() && "C_S".equals(invoiceBean.getWorksheet().getWorkSheetType())){
						SimpleDateFormat format = new SimpleDateFormat("dd", new Locale("en", "US"));
						// คำนวนค่าบริการคิดรอบบิลแรกเป็นรายวัน
						float price = invoiceBean.getServiceApplication().getMonthlyServiceFee();
						int lastDateOfmonth = new DateUtil().lastDateOfmonth(new Date());
						int currentDateOfmonth = Integer.parseInt(format.format(new Date()));
						
						int dateTotal = lastDateOfmonth - currentDateOfmonth;
						float pricePerDay = price/lastDateOfmonth;
						float amount = pricePerDay*dateTotal;
						invoiceBean.setAmount(amount);
						modelAndView.addObject("invoiceBean",invoiceBean);
					}else{
						modelAndView.addObject("invoiceBean",invoiceBean);
					}
				}
				
				CompanyController companyController = new CompanyController();
				companyController.setMessageSource(messageSource);
				CompanyBean companyBean = companyController.populateEntityToDto(invoice.getServiceApplication().getServicePackage().getCompany());
				modelAndView.addObject("companyBean",companyBean);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		SimpleDateFormat formateDate = new SimpleDateFormat(
				messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		modelAndView.addObject("paymentDate", formateDate.format(new Date()));
		
		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + PAGE_PAYMENT);
		return modelAndView;
	}
	
	
	// save payment
	@RequestMapping(value = "savePayment", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse savePayment(@RequestBody final BillDocumentBean billDocumentBean, HttpServletRequest request) {
		logger.info("[method : savePayment][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				 Invoice invoice = financialService.getInvoiceById(billDocumentBean.getInvoiceDocumentBean().getId());
				 if(invoice != null){
					 invoice.setStatus(messageSource.getMessage("financial.invoice.status.payed", 
							 null, LocaleContextHolder.getLocale()));
					 invoice.getReceipt().setAmount(billDocumentBean.getAmount());
					 
					 if(billDocumentBean.getPaymentType().equals("T")){
						 Bank bank = bankService.getBankById(billDocumentBean.getBankBean().getId());
						 invoice.getReceipt().setBank(bank);
						 invoice.getReceipt().setPaymentDate(
								 new DateUtil().convertStringToDateTimeDb(billDocumentBean.getPaymentDateTh(), billDocumentBean.getHour(), billDocumentBean.getMinute()));
					 }else{
						 SimpleDateFormat formateDate = new SimpleDateFormat(
									messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),
									new Locale("TH", "th")); 
						 invoice.getReceipt().setPaymentDate(formateDate.parse(billDocumentBean.getPaymentDateTh()));
					 }
					 invoice.getReceipt().setReductAmount(billDocumentBean.getReductAmount());
					 invoice.getReceipt().setPaymentType(billDocumentBean.getPaymentType());
					 invoice.getReceipt().setStatus(messageSource.getMessage("financial.receipt.status.perm", 
							 null, LocaleContextHolder.getLocale()));
					 
					 //set personnel
					 Personnel personnel = personnelService.getPersonnelById(getPersonnelLogin().getId());
					 invoice.getReceipt().setPersonnel(personnel);
					 //set vat type
					 invoice.setVat(billDocumentBean.getVat());
					 
					 financialService.updateInvoice(invoice);
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
				messageSource.getMessage("financial.invoice.payment.transaction.title.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("financial.invoice.payment.transaction.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "convertNumbertoText", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse convertNumbertoText(@RequestBody final String amount, HttpServletRequest request) {
		logger.info("[method : convertNumbertoText][Type : Controller]");
		logger.info("amount : "+amount);
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try { 
				String amountTemp = amount.replace("\"", "");
				jsonResponse.setResult(new NumberFormat().getText(amountTemp));
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
	
	
	// print invoice
	@RequestMapping(value = "print/{id}", method = RequestMethod.GET)
	public ModelAndView print(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : print][Type : Controller]");
		logger.info("[method : print][id : " + id + "]");

		ModelAndView modelAndView = new ModelAndView();
		// get current session
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// load invoice
				Invoice invoice = financialService.getInvoiceById(id);
				if (invoice != null) {
					//update print time invoice
//					List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
//					InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
//					invoiceHistoryPrint.setCreateDate(BaseController.CURRENT_TIMESTAMP);
//					invoiceHistoryPrint.setDeleted(Boolean.FALSE);
//					invoiceHistoryPrint.setCreatedBy(getUserNameLogin());
//					invoiceHistoryPrint.setPrintTime(invoice.getInvoiceHistoryPrints().size() + 1);
//					invoiceHistoryPrint.setInvoice(invoice);
//					invoiceHistoryPrintList.add(invoiceHistoryPrint);
//					invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
//					financialService.updateInvoice(invoice);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		modelAndView.setViewName(CONTROLLER_NAME + VIEW);
		return modelAndView;
	}
	
	public void updateAmountInvoiceTypeWorksheet(Long worksheetId){
		Worksheet worksheet = workSheetService.getWorksheetById(worksheetId);
		if(worksheet != null){
			Invoice invoice = worksheet.getInvoice();
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			WorkSheetAddController wAdd = new WorkSheetAddController();
			wAdd.setMessageSource(messageSource);
			wAdd.setWorkSheetService(workSheetService);
			wAdd.setFinancialService(financialService);
			invoice.setAmount(wAdd.calculateAmountInvoice(worksheet));
			invoice.getReceipt().setAmount(invoice.getAmount());
			
			if("S".equals(worksheet.getStatus()) && invoice.getAmount()<=0){
				Date date = worksheet.getHistoryTechnicianGroupWorks().get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getAssignDate();
				invoice.setCreateDate(date);
				invoice.setStatus("S");
				invoice.getReceipt().setPaymentDate(CURRENT_TIMESTAMP);
			}

			if("H".equals(worksheet.getStatus())){
				invoice.setCreateDate(null);
			}
			
			try {
				financialService.updateInvoice(invoice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<BankBean> convertBankEntityToDto(List<Bank> bankList){
		List<BankBean> bankBeanList = new ArrayList<BankBean>();
		for(Bank bank : bankList){
			BankBean bankBean = new BankBean();
			bankBean.setId(bank.getId());
			bankBean.setBankNameEn(bank.getBankNameEn());
			bankBean.setBankNameTh(bank.getBankNameTh());
			bankBean.setBankShortName(bank.getBankShortName());
			bankBean.setBankCode(bank.getBankCode());
			bankBeanList.add(bankBean);
		}
		return bankBeanList;
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
		ServiceApplicationListController sac = new ServiceApplicationListController();
		sac.setMessageSource(messageSource);
		sac.setServiceApplicationService(serviceApplicationService);
		sac.setUnitService(unitService);
		ServiceApplicationBean serviceApplicationBean = sac.populateEntityToDto(invoice.getServiceApplication());
		invoiceBean.setServiceApplication(serviceApplicationBean);
		
		//worksheet
		if(invoice.getWorkSheet() != null){
			AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
			assignWorksheetController.setMessageSource(messageSource);
			assignWorksheetController.setUnitService(unitService);
			invoiceBean.setWorksheet(assignWorksheetController.populateEntityToDto(invoice.getWorkSheet()));
		}
		
		//print invoice history
		SimpleDateFormat formatDataThTime = new SimpleDateFormat(
				messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList = new ArrayList<InvoiceHistoryPrintBean>();
		invoiceBean.setPrintLastDate("-");
		for(InvoiceHistoryPrint invoiceHistoryPrint : invoice.getInvoiceHistoryPrints()){
			InvoiceHistoryPrintBean invoiceHistoryPrintBean = new InvoiceHistoryPrintBean();
			if(invoiceHistoryPrint.getPrintTime() == 0){
				continue;
			}
			invoiceHistoryPrintBean.setId(invoiceHistoryPrint.getId());
			invoiceHistoryPrintBean.setPrintTime(invoiceHistoryPrint.getPrintTime());
			invoiceHistoryPrintBean.setCreateDateTh(
					null == invoiceHistoryPrint.getCreateDate() ? "" : formatDataThTime.format(invoiceHistoryPrint.getCreateDate()));
			if(invoiceHistoryPrint.getPersonnel() != null){
				PersonnelBean personnelBean = new PersonnelBean();
				personnelBean.setId(invoiceHistoryPrint.getPersonnel().getId());
				personnelBean.setFirstName(invoiceHistoryPrint.getPersonnel().getFirstName());
				personnelBean.setLastName(invoiceHistoryPrint.getPersonnel().getLastName());
				personnelBean.setNickName(invoiceHistoryPrint.getPersonnel().getNickName());
				invoiceHistoryPrintBean.setPersonnelBean(personnelBean);
			}
			invoiceHistoryPrintBeanList.add(invoiceHistoryPrintBean);

			invoiceBean.setPrintCount(invoiceHistoryPrint.getPrintTime());
			invoiceBean.setPrintLastDate(
					null == invoiceHistoryPrint.getCreateDate() ? "-" : formatDataTh.format(invoiceHistoryPrint.getCreateDate()));
		}
		invoiceBean.setInvoiceHistoryPrintBeanList(invoiceHistoryPrintBeanList);
		
		
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
	
	public InvoiceDocumentBean PoppulateInvoiceEntityToDtoV2(Invoice invoice){
		InvoiceDocumentBean invoiceBean = new InvoiceDocumentBean();
		if(!"O".equals(invoice.getInvoiceType())){ // ไม่ใช่ invoice แบบใบรายเดือน
			Worksheet worksheet = invoice.getWorkSheet();
			if("W".equals(worksheet.getStatus()) || "H".equals(worksheet.getStatus())){
				return null;
			}
		}
		invoiceBean.setId(invoice.getId());
		invoiceBean.setAmount(invoice.getAmount());
		invoiceBean.setInvoiceCode(invoice.getInvoiceCode());
		invoiceBean.setInvoiceType(invoice.getInvoiceType());
		invoiceBean.setStatus(invoice.getStatus());
		invoiceBean.setBilling(invoice.isBilling());
		
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type6", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		invoiceBean.setCreateDateTh(
				null == invoice.getCreateDate() ? "" : formatDataTh.format(invoice.getCreateDate()));
		invoiceBean.setIssueDocDateTh(
				null == invoice.getIssueDocDate() ? "" : formatDataTh.format(invoice.getIssueDocDate()));
		
		invoiceBean.setPaymentDateTh(
				null == invoice.getPaymentDate() ? "" : formatDataTh.format(invoice.getPaymentDate()));
		
		//service application
		ServiceApplicationListController sac = new ServiceApplicationListController();
		sac.setMessageSource(messageSource);
		sac.setServiceApplicationService(serviceApplicationService);
		ServiceApplicationBean serviceApplicationBean = sac.populateEntityToDto(invoice.getServiceApplication());
		invoiceBean.setServiceApplication(serviceApplicationBean);
		
		return invoiceBean;
	}
	
	public InvoiceDocumentBean PoppulateInvoiceEntityToDtoForList(Invoice invoice){
		InvoiceDocumentBean invoiceBean = new InvoiceDocumentBean();
		if(!"O".equals(invoice.getInvoiceType())){ // ไม่ใช่ invoice แบบใบรายเดือน
			Worksheet worksheet = invoice.getWorkSheet();
			if("W".equals(worksheet.getStatus()) || "H".equals(worksheet.getStatus())){
				return null;
			}
		}
		invoiceBean.setId(invoice.getId());
		invoiceBean.setAmount(invoice.getAmount());
		invoiceBean.setInvoiceCode(invoice.getInvoiceCode());
		invoiceBean.setInvoiceType(invoice.getInvoiceType());
		invoiceBean.setStatus(invoice.getStatus());
		invoiceBean.setBilling(invoice.isBilling());
		invoiceBean.setBadDebt(invoice.isBadDebt());
		
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type6", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		
		invoiceBean.setCreateDateTh(
				null == invoice.getCreateDate() ? "" : formatDataTh.format(invoice.getCreateDate()));
		invoiceBean.setIssueDocDateTh(
				null == invoice.getIssueDocDate() ? "" : formatDataTh.format(invoice.getIssueDocDate()));
		
		invoiceBean.setPaymentDateTh(
				null == invoice.getPaymentDate() ? "" : formatDataTh.format(invoice.getPaymentDate()));

		ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
		ServiceApplication serviceApplication = invoice.getServiceApplication();
		if(null != serviceApplication){
			StatusBean status = new StatusBean();
			status.setStringValue(serviceApplication.getStatus());
			serviceApplicationBean.setStatus(status);
			
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
			
			CustomerBean customerBean = new CustomerBean();
			customerBean.setId(serviceApplication.getCustomer().getId());
			customerBean.setSex(serviceApplication.getCustomer().getSex());
			customerBean.setPrefix(serviceApplication.getCustomer().getPrefix());
			customerBean.setFirstName(serviceApplication.getCustomer().getFirstName());
			customerBean.setLastName(serviceApplication.getCustomer().getLastName());
			customerBean.setCustCode(serviceApplication.getCustomer().getCustCode());
			customerBean.setIdentityNumber(serviceApplication.getCustomer().getIdentityNumber());
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
			
			//จำนวนจุดรวม
			int quantityTotalPoint = 0;
			List<Worksheet> worksheetList = serviceApplication.getWorksheets();
			if(null != worksheetList  && worksheetList.size() > 0){
				for(Worksheet worksheet:worksheetList){
					WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
					if(null != worksheetSetup && worksheet.getProductItems().size() > 0){
						List<ProductItem> productItemList = worksheet.getProductItems();
						if(null != productItemList && productItemList.size() > 0){
							for(ProductItem productItem:productItemList){
								String productType = productItem.getProductType();
								if("S".equals(productType)){
									quantityTotalPoint += productItem.getQuantity();
								}
							}
						}
					}
				}
			}
			invoiceBean.setQuantityTotalPoint(quantityTotalPoint);
			
		}
		invoiceBean.setServiceApplication(serviceApplicationBean);
		
		Receipt receipt = invoice.getReceipt();
		if(null != receipt){
			ReceiptBean receiptBean = new ReceiptBean();
			receiptBean.setId(receipt.getId());
			receiptBean.setAmount(receipt.getAmount());
			receiptBean.setPaymentDate(receipt.getPaymentDate());
			receiptBean.setPaymentDateTh(null == receipt.getPaymentDate() ? "" : formatDataTh.format(receipt.getPaymentDate()));
			invoiceBean.setReceiptBean(receiptBean);
		}
		return invoiceBean;
	}
	
	// updateBillingStatus
	@RequestMapping(value = "updateBillingStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateBillingStatus(
			@RequestBody final InvoiceDocumentBean invoiceDocumentBean,HttpServletRequest request) {
		logger.info("[method : updateBillingStatus][Type : Controller]");
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try { 
				if(null != invoiceDocumentBean){
					//load invoice
					Invoice invoice = financialService.getInvoiceById(invoiceDocumentBean.getId());	
					if(null != invoice){
						Date currentDate = new Date();
						Date paymentDate = new Date();
				        Calendar cal = Calendar.getInstance();
				        cal.setTime(currentDate);
				        // Set time fields to zero
				        cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
				        currentDate = cal.getTime();
				        
				        cal = Calendar.getInstance();
				        cal.setTime(invoice.getCreateDate());
				        // Set time fields to zero
				        cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
				        paymentDate = cal.getTime();
				        
				        logger.info("createDate : "+invoice.getCreateDate());
				        logger.info("paymentDate : "+paymentDate);
				        logger.info("currentDate : "+currentDate);
				        logger.info("CreateDate before currentDate : "+paymentDate.before(currentDate)); // true เกินวันนัดชำระเงิน
						
				        if(paymentDate.before(currentDate)){
				        	invoice.setStatus("O");
				        }else{
				        	invoice.setStatus("W");
				        }

						if("Y".equals(invoiceDocumentBean.getBillingStatus())){
							invoice.setStatus("S");  //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
							invoice.setBilling(false);
							invoice.getReceipt().setPaymentDate(CURRENT_TIMESTAMP);
							invoice.getReceipt().setPaymentType("P");
							invoice.getReceipt().setStatus(messageSource.getMessage("financial.receipt.status.perm", 
									 null, LocaleContextHolder.getLocale()));
						}else if("N".equals(invoiceDocumentBean.getBillingStatus())){
							invoice.setBilling(false);
							invoice.getReceipt().setPaymentType(null);
							invoice.getReceipt().setStatus(messageSource.getMessage("financial.receipt.status.hold", 
									 null, LocaleContextHolder.getLocale()));
						}else if("A".equals(invoiceDocumentBean.getBillingStatus())){
							invoice.setBilling(true);
							invoice.getReceipt().setPaymentType(null);
							invoice.getReceipt().setStatus(messageSource.getMessage("financial.receipt.status.hold", 
									 null, LocaleContextHolder.getLocale()));
						}
						invoice.setUpdatedBy(getUserNameLogin());
						invoice.setUpdatedDate(CURRENT_TIMESTAMP);
						invoice.getReceipt().setUpdatedBy(getUserNameLogin());
						invoice.getReceipt().setUpdatedDate(CURRENT_TIMESTAMP);
						financialService.updateInvoice(invoice);
						
						jsonResponse.setError(false);
					}else{
						jsonResponse.setError(true);
					}
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
	
	@SuppressWarnings("unused")
	@RequestMapping(value="cancelInvoice/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse cancelInvoice(@PathVariable String id, HttpServletRequest request) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Invoice invoice = financialService.getInvoiceById(Long.valueOf(id));	
				if(null != invoice){
					invoice.setStatus("C");
					
					invoice.setUpdatedBy(getUserNameLogin());
					invoice.setUpdatedDate(CURRENT_TIMESTAMP);
					invoice.getReceipt().setUpdatedBy(getUserNameLogin());
					invoice.getReceipt().setUpdatedDate(CURRENT_TIMESTAMP);
					financialService.updateInvoice(invoice);
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
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
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	//getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}

	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
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
