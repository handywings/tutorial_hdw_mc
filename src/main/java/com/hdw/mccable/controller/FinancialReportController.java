package com.hdw.mccable.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.Manager.PdfMergeUtils;
import com.hdw.mccable.Manager.ReportManager;
import com.hdw.mccable.dto.AddPointWorksheetBean;
import com.hdw.mccable.dto.AdditionMonthlyFee;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.ContactInternetReportBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.DocumentFileBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.ExpenseItemBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReportBilUnpaidBean;
import com.hdw.mccable.dto.ReportInvoiceByInvoiceTypeBean;
import com.hdw.mccable.dto.ReportInvoiceOrReceiptBean;
import com.hdw.mccable.dto.ReportWorksheetSetUpBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.SubWorksheetBean;
import com.hdw.mccable.dto.WorkSheetReportBeanSubTable;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ReceiptHistoryPrint;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.FilePathUtil;
import com.hdw.mccable.utils.MoneyReader;
import com.hdw.mccable.utils.NumberFormat;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import net.sf.jasperreports.engine.JRParameter;

@Controller
@RequestMapping("/financialreport")
public class FinancialReportController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(FinancialReportController.class);
	public static final String CONTROLLER_NAME = "financialreport/";
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
    MessageSource messageSource;
	
	@RequestMapping(value = "exportdemo", method = RequestMethod.GET)
	public ModelAndView exportdemo(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("[method : exportdemo][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		
		ReportManager reportManager = new ReportManager();
		//send parameter
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("id", 1);
		parameters.put(JRParameter.REPORT_LOCALE, new Locale("th")); 
		//send export pdf
		reportManager.generateReport("demo", parameters, request, response);
		return null;
	}
	
	@RequestMapping(value = "byserviceapplication", method = RequestMethod.GET)
	public ModelAndView byserviceapplication(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byserviceapplication][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if(isPermission()){
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "bycustomertype", method = RequestMethod.GET)
	public ModelAndView bycustomertype(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : bycustomertype][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if(isPermission()){
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "byinvoicetype", method = RequestMethod.GET)
	public ModelAndView byinvoicetype(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byinvoicetype][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){

		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "byinvoicetype/exportPdf/reportrange/{reportrange}/invoiceType/{invoiceType}/split/{split}/invoiceStatus/{invoiceStatus}", method = RequestMethod.GET)
	public ModelAndView byinvoicetypeExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String invoiceType,
			@PathVariable String split, 
			@PathVariable String invoiceStatus, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : byinvoicetypeExportPdf][Type : Controller]");
		
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, invoiceType : {}, split : {}, invoiceStatus : {}",reportrange,invoiceType,split,invoiceStatus);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<Invoice> invoiceList = financialService.getByInvoiceTypeForReport(reportrange,invoiceType,split,invoiceStatus);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		float sumServiceChargeTotal = 0f, sumServiceChargeTotalFinal = 0f;
		int billNumber = 0;
		if(null != invoiceList && invoiceList.size() > 0){
			Company company = companyService.getParentCompanyById();
			float company_vat = 0f;
			if(null != company){
				company_vat = company.getVat();
			}
			ReportInvoiceByInvoiceTypeBean reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
			float sumVat = 0f, sumServiceCharge = 0f;
			int no = 1;
			Long personnelId = 0L;
			if("0".equals(split)){ // (แยกตามพนักงานเก็บเงิน)
				Personnel personnel = invoiceList.get(0).getPersonnel();
				if(null != personnel){
					String cashierFullName =String.format("คุณ %1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getCompany().getCompanyName());
					reportInvoiceByInvoiceTypeBean.setHeaderTable(cashierFullName);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					personnelId = personnel.getId();
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("เขตชุมชน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				}

				for(int i = 0; invoiceList.size() > i; i++){
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(personnelId!=invoiceList.get(i).getPersonnel().getId()){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						personnel = invoiceList.get(i).getPersonnel();
						String cashierFullName =String.format("คุณ %1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getCompany().getCompanyName());
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(cashierFullName);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("เขตชุมชน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						List<Address> addressList = serApp.getAddresses();
						if(null != addressList){
							for(Address address:addressList){
								if("3".equals(address.getAddressType())){
									reportInvoiceByInvoiceTypeBean.setZoneOrCashier(address.getZone().getZoneDetail());
								}
							}
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("1".equals(split)){ // (แยกตามชุมชนพื้นที่ติดตั้ง)
				ServiceApplication serApp = invoiceList.get(0).getServiceApplication();
				List<Address> addressList = serApp.getAddresses();
				String zoneName = "";
				if(null != addressList){
					for(Address address:addressList){
						if("3".equals(address.getAddressType())){
							zoneName = address.getZone().getZoneDetail();
						}
					}
				}

					String headerTable =String.format("เขตพื้นที่ติดตั้งชุมชน%1s",zoneName);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					String zoneNew = "";
					serApp = invoiceList.get(i).getServiceApplication();
					addressList = serApp.getAddresses();
					if(null != addressList){
						for(Address address:addressList){
							if("3".equals(address.getAddressType())){
								zoneNew = address.getZone().getZoneDetail();
							}
						}
					}
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!zoneName.equals(zoneNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						zoneName = zoneNew;
						headerTable =String.format("เขตพื้นที่ติดตั้งชุมชน%1s",zoneName);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("2".equals(split)){ // (แยกตามประเภทบริการ)
				ServiceApplication serApp = invoiceList.get(0).getServiceApplication();
				ServicePackage servicePackage = serApp.getServicePackage();
				String packageTypeName = servicePackage.getServicePackageType().getPackageTypeName();

					String headerTable =String.format("บริการ%1s",packageTypeName);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					serApp = invoiceList.get(0).getServiceApplication();
					servicePackage = serApp.getServicePackage();
					String packageTypeNameNew = servicePackage.getServicePackageType().getPackageTypeName();
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!packageTypeName.equals(packageTypeNameNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						packageTypeName = packageTypeNameNew;
						headerTable =String.format("บริการ%1s",packageTypeName);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("3".equals(split)){ // (แยกตามรายเดือน)	
					Date date = invoiceList.get(0).getCreateDate();
					String month = formatMMMMyyyy.format(date);
					String headerTable =String.format("เดือน%1s",month);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					date = invoiceList.get(i).getCreateDate();
					String monthNew = formatMMMMyyyy.format(date);
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!month.equals(monthNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						month = monthNew;
						headerTable =String.format("เดือน%1s",month);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("4".equals(split)){ // (แยกตามลักษณะสมาชิก)
				ServiceApplication serviceApplication = invoiceList.get(0).getServiceApplication();
				String customerFeatureName = serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureName();
				reportInvoiceByInvoiceTypeBean.setHeaderTable(customerFeatureName);
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
				
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
				reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
				reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
				reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
				reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
				reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
				reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
				reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
				reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
			

			for(int i = 0; invoiceList.size() > i; i++){
				serviceApplication = invoiceList.get(i).getServiceApplication();
				String customerFeatureNameNew = serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureName();
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				if(!customerFeatureName.equals(customerFeatureNameNew)){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					sumVat = 0f;
					sumServiceCharge = 0f;
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					customerFeatureName = customerFeatureNameNew;
					personnelId = invoiceList.get(i).getPersonnel().getId();
					reportInvoiceByInvoiceTypeBean.setHeaderTable(customerFeatureName);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					no = 1;
					--i;
					continue;
				}
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				reportInvoiceByInvoiceTypeBean.setNo(no++);
				reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
				ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
				if(null != serApp){
					Customer customer =	serApp.getCustomer();
					if(null != customer){
						String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
						reportInvoiceByInvoiceTypeBean.setFullName(fullName);
					}
					Personnel per1 = invoiceList.get(i).getPersonnel();
					if(null != per1){
						String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
						reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
					}
				}
				reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
				reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
				reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
				reportInvoiceByInvoiceTypeBean.setVat("0.00");
				float vat = 0f;
				if(null != company){
//					${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
					company_vat = company.getVat();
					vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
					reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
				}
				reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
				sumVat += vat;
				sumServiceCharge += invoiceList.get(i).getAmount();
				sumServiceChargeTotal += sumServiceCharge;
				++billNumber;
				resUse.add(reportInvoiceByInvoiceTypeBean);
				
				if((invoiceList.size()-1) == i){
				sumServiceChargeTotalFinal += sumServiceCharge;
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
				reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
				reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(true);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
				}
				
			}
		}
			
			
			String total = String.format("( รวมค่าบริการทั้งสิ้น %1s บาท จากใบบิลทั้งสิ้น %2s ใบ )",new DecimalFormat("#,##0.00").format(sumServiceChargeTotalFinal),billNumber);
			reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
			reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
			reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
			reportInvoiceByInvoiceTypeBean.setCheckSum(false);
			reportInvoiceByInvoiceTypeBean.setCheckSumFinal(true);
			reportInvoiceByInvoiceTypeBean.setTotal(total);
			resUse.add(reportInvoiceByInvoiceTypeBean);
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		params.put("flagTitle", "1");
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byinvoicetype",request));
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
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "bybaddebt", method = RequestMethod.GET)
	public ModelAndView bybaddebt(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : bybaddebt][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){

		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "bybaddebt/exportPdf/reportrange/{reportrange}/split/{split}", method = RequestMethod.GET)
	public ModelAndView bybaddebtExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String split, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : bybaddebtExportPdf][Type : Controller]");
		String invoiceStatus = "0"; // fix O ค้างชำระ
		String invoiceType = "0"; // fix 0 ไม่ให้เข้า where
		
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, invoiceType : {}, split : {}, invoiceStatus : {}",reportrange,invoiceType,split,invoiceStatus);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<Invoice> invoiceList = financialService.getByInvoiceTypeBybaddebtForReport(reportrange,invoiceType,split,invoiceStatus);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		float sumServiceChargeTotal = 0f, sumServiceChargeTotalFinal = 0f;
		int billNumber = 0;
		if(null != invoiceList && invoiceList.size() > 0){
			Company company = companyService.getParentCompanyById();
			float company_vat = 0f;
			if(null != company){
				company_vat = company.getVat();
			}
			ReportInvoiceByInvoiceTypeBean reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
			float sumVat = 0f, sumServiceCharge = 0f;
			int no = 1;
			Long personnelId = 0L;
			if("0".equals(split)){ // (แยกตามพนักงานเก็บเงิน)
				Personnel personnel = invoiceList.get(0).getPersonnel();
				if(null != personnel){
					String cashierFullName =String.format("คุณ %1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getCompany().getCompanyName());
					reportInvoiceByInvoiceTypeBean.setHeaderTable(cashierFullName);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					personnelId = personnel.getId();
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("เขตชุมชน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				}

				for(int i = 0; invoiceList.size() > i; i++){
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(personnelId!=invoiceList.get(i).getPersonnel().getId()){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						personnel = invoiceList.get(i).getPersonnel();
						String cashierFullName =String.format("คุณ %1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getCompany().getCompanyName());
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(cashierFullName);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("เขตชุมชน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						List<Address> addressList = serApp.getAddresses();
						if(null != addressList){
							for(Address address:addressList){
								if("3".equals(address.getAddressType())){
									reportInvoiceByInvoiceTypeBean.setZoneOrCashier(address.getZone().getZoneDetail());
								}
							}
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("1".equals(split)){ // (แยกตามชุมชนพื้นที่ติดตั้ง)
				ServiceApplication serApp = invoiceList.get(0).getServiceApplication();
				List<Address> addressList = serApp.getAddresses();
				String zoneName = "";
				if(null != addressList){
					for(Address address:addressList){
						if("3".equals(address.getAddressType())){
							zoneName = address.getZone().getZoneDetail();
						}
					}
				}

					String headerTable =String.format("เขตพื้นที่ติดตั้งชุมชน%1s",zoneName);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					String zoneNew = "";
					serApp = invoiceList.get(i).getServiceApplication();
					addressList = serApp.getAddresses();
					if(null != addressList){
						for(Address address:addressList){
							if("3".equals(address.getAddressType())){
								zoneNew = address.getZone().getZoneDetail();
							}
						}
					}
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!zoneName.equals(zoneNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						zoneName = zoneNew;
						headerTable =String.format("เขตพื้นที่ติดตั้งชุมชน%1s",zoneName);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("2".equals(split)){ // (แยกตามประเภทบริการ)
				ServiceApplication serApp = invoiceList.get(0).getServiceApplication();
				ServicePackage servicePackage = serApp.getServicePackage();
				String packageTypeName = servicePackage.getServicePackageType().getPackageTypeName();

					String headerTable =String.format("บริการ%1s",packageTypeName);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					serApp = invoiceList.get(0).getServiceApplication();
					servicePackage = serApp.getServicePackage();
					String packageTypeNameNew = servicePackage.getServicePackageType().getPackageTypeName();
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!packageTypeName.equals(packageTypeNameNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						packageTypeName = packageTypeNameNew;
						headerTable =String.format("บริการ%1s",packageTypeName);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("3".equals(split)){ // (แยกตามรายเดือน)	
					Date date = invoiceList.get(0).getCreateDate();
					String month = formatMMMMyyyy.format(date);
					String headerTable =String.format("เดือน%1s",month);
					reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
				

				for(int i = 0; invoiceList.size() > i; i++){
					date = invoiceList.get(i).getCreateDate();
					String monthNew = formatMMMMyyyy.format(date);
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					if(!month.equals(monthNew)){
						sumServiceChargeTotalFinal += sumServiceCharge;
						reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
						reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
						reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(true);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						sumVat = 0f;
						sumServiceCharge = 0f;
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						month = monthNew;
						headerTable =String.format("เดือน%1s",month);
						personnelId = invoiceList.get(i).getPersonnel().getId();
						reportInvoiceByInvoiceTypeBean.setHeaderTable(headerTable);
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						
						reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
						reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
						reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
						reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
						reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
						reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
						reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
						reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
						reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
						reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
						reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
						reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
						reportInvoiceByInvoiceTypeBean.setCheckSum(false);
						reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
						resUse.add(reportInvoiceByInvoiceTypeBean);
						no = 1;
						--i;
						continue;
					}
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					reportInvoiceByInvoiceTypeBean.setNo(no++);
					reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
					ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
					if(null != serApp){
						Customer customer =	serApp.getCustomer();
						if(null != customer){
							String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
							reportInvoiceByInvoiceTypeBean.setFullName(fullName);
						}
						Personnel per1 = invoiceList.get(i).getPersonnel();
						if(null != per1){
							String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
							reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
						}
					}
					reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
					reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
					reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
					reportInvoiceByInvoiceTypeBean.setVat("0.00");
					float vat = 0f;
					if(null != company){
//						${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
						company_vat = company.getVat();
						vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
						reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
					}
					reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
					sumVat += vat;
					sumServiceCharge += invoiceList.get(i).getAmount();
					sumServiceChargeTotal += sumServiceCharge;
					++billNumber;
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					if((invoiceList.size()-1) == i){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					}
					
				}
			}else if("4".equals(split)){ // (แยกตามลักษณะสมาชิก)
				ServiceApplication serviceApplication = invoiceList.get(0).getServiceApplication();
				String customerFeatureName = serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureName();
				reportInvoiceByInvoiceTypeBean.setHeaderTable(customerFeatureName);
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
				
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
				reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
				reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
				reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
				reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
				reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
				reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
				reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
				reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
			

			for(int i = 0; invoiceList.size() > i; i++){
				serviceApplication = invoiceList.get(i).getServiceApplication();
				String customerFeatureNameNew = serviceApplication.getCustomer().getCustomerFeature().getCustomerFeatureName();
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				if(!customerFeatureName.equals(customerFeatureNameNew)){
					sumServiceChargeTotalFinal += sumServiceCharge;
					reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
					reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
					reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(true);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					sumVat = 0f;
					sumServiceCharge = 0f;
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					customerFeatureName = customerFeatureNameNew;
					personnelId = invoiceList.get(i).getPersonnel().getId();
					reportInvoiceByInvoiceTypeBean.setHeaderTable(customerFeatureName);
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(true);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					
					reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
					reportInvoiceByInvoiceTypeBean.setNoText("ลำดับ");
					reportInvoiceByInvoiceTypeBean.setInvoiceCodeText("เลขที่บิล");
					reportInvoiceByInvoiceTypeBean.setFullNameText("ชื่อ – สกุลลูกค้า");
					reportInvoiceByInvoiceTypeBean.setZoneOrCashierText("พนักงานเก็บเงิน");
					reportInvoiceByInvoiceTypeBean.setInvoiceTypeText("ประเภทบิล");
					reportInvoiceByInvoiceTypeBean.setCreateDateText("วันกำหนดชำระ");
					reportInvoiceByInvoiceTypeBean.setStatusText("สถานะชำระ");
					reportInvoiceByInvoiceTypeBean.setVatText("ภาษี "+company_vat+"%");
					reportInvoiceByInvoiceTypeBean.setServiceChargeText("ค่าบริการ");
					reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
					reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(true);
					reportInvoiceByInvoiceTypeBean.setCheckSum(false);
					reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
					resUse.add(reportInvoiceByInvoiceTypeBean);
					no = 1;
					--i;
					continue;
				}
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(false);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				reportInvoiceByInvoiceTypeBean.setNo(no++);
				reportInvoiceByInvoiceTypeBean.setInvoiceCode(invoiceList.get(i).getInvoiceCode());
				ServiceApplication serApp = invoiceList.get(i).getServiceApplication();
				if(null != serApp){
					Customer customer =	serApp.getCustomer();
					if(null != customer){
						String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
						reportInvoiceByInvoiceTypeBean.setFullName(fullName);
					}
					Personnel per1 = invoiceList.get(i).getPersonnel();
					if(null != per1){
						String cashierFullName =String.format("%1s  %2s (%3s) เจ้าหน้าที่เก็บเงิน %4s",per1.getFirstName(),per1.getLastName(),per1.getNickName(),per1.getCompany().getCompanyName());
						reportInvoiceByInvoiceTypeBean.setZoneOrCashier(cashierFullName);
					}
				}
				reportInvoiceByInvoiceTypeBean.setInvoiceType(invoiceList.get(i).getInvoiceType());
				reportInvoiceByInvoiceTypeBean.setCreateDate(formatDataTh.format(invoiceList.get(i).getCreateDate()));
				reportInvoiceByInvoiceTypeBean.setStatus(invoiceList.get(i).getStatus());
				reportInvoiceByInvoiceTypeBean.setVat("0.00");
				float vat = 0f;
				if(null != company){
//					${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}
					company_vat = company.getVat();
					vat = invoiceList.get(i).getAmount() - ((invoiceList.get(i).getAmount()*100) / (100+company_vat));
					reportInvoiceByInvoiceTypeBean.setVat(new DecimalFormat("#,##0.00").format(vat));
				}
				reportInvoiceByInvoiceTypeBean.setServiceCharge(new DecimalFormat("#,##0.00").format(invoiceList.get(i).getAmount()));
				sumVat += vat;
				sumServiceCharge += invoiceList.get(i).getAmount();
				sumServiceChargeTotal += sumServiceCharge;
				++billNumber;
				resUse.add(reportInvoiceByInvoiceTypeBean);
				
				if((invoiceList.size()-1) == i){
				sumServiceChargeTotalFinal += sumServiceCharge;
				reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
				reportInvoiceByInvoiceTypeBean.setSumText("รวมยอด");
				reportInvoiceByInvoiceTypeBean.setSumVat(new DecimalFormat("#,##0.00").format(sumVat));
				reportInvoiceByInvoiceTypeBean.setSumServiceCharge(new DecimalFormat("#,##0.00").format(sumServiceCharge));
				reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
				reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
				reportInvoiceByInvoiceTypeBean.setCheckSum(true);
				reportInvoiceByInvoiceTypeBean.setCheckSumFinal(false);
				resUse.add(reportInvoiceByInvoiceTypeBean);
				}
				
			}
		}
			
			
			String total = String.format("( รวมค่าบริการทั้งสิ้น %1s บาท จากใบบิลทั้งสิ้น %2s ใบ )",new DecimalFormat("#,##0.00").format(sumServiceChargeTotalFinal),billNumber);
			reportInvoiceByInvoiceTypeBean = new ReportInvoiceByInvoiceTypeBean();
			reportInvoiceByInvoiceTypeBean.setCheckCashiers(false);
			reportInvoiceByInvoiceTypeBean.setCheckHeaderTable(false);
			reportInvoiceByInvoiceTypeBean.setCheckSum(false);
			reportInvoiceByInvoiceTypeBean.setCheckSumFinal(true);
			reportInvoiceByInvoiceTypeBean.setTotal(total);
			resUse.add(reportInvoiceByInvoiceTypeBean);
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		params.put("flagTitle", "2");
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byinvoicetype",request));
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
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "invoiceOrReceipt/exportPdf/workSheetId/{workSheetId}/companyId/{companyId}", method = RequestMethod.GET)
	public ModelAndView invoiceOrReceiptByworkSheetId(Model model,
			@PathVariable Long workSheetId,
			@PathVariable Long companyId,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : invoiceOrReceiptByworkSheetId][Type : Controller][workSheetId : {}]",workSheetId);
		ModelAndView modelAndView = new ModelAndView();
		
		String nameReport = "invoiceOrReceiptA4_Old_V2"; // ใบแจ้งหนี้/ใบรับเงินชั่วคราว
		//check permission
		if(isPermission()){
		JasperRender jasperRender = new JasperRender();
		List<Object> resUse = new ArrayList<Object>();
		
		Worksheet worksheet = workSheetService.getWorksheetById(workSheetId);
		Invoice invoice = new Invoice();
		if("S".equals(worksheet.getStatus())){
			nameReport = "invoiceOrReceiptA4_Old_V2"; // ใบแจ้งหนี้(บริษัท)/ใบรับเงินชั่วคราว
			invoice = worksheet.getInvoice();
			if(null != invoice){
				if("S".equals(invoice.getStatus())){
					nameReport = "receipt"; // ใบเสร็จรับเงิน
				}
			}
		}
		
		Company company = companyService.getCompanyById(companyId);
		
		double vat = (invoice.getAmount()*(company.getVat()/100));
		ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
		reportInvoiceOrReceiptBean.setVat(vat);
		if(null != worksheet){
			setReportInvoiceOrReceiptBean(worksheet,reportInvoiceOrReceiptBean,request);
			invoice = worksheet.getInvoice();
			if(null != invoice){
				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/easynet.png");
		String scissors = request.getSession().getServletContext().getRealPath("/jasper/scissors.png");

		params.put("scissors", scissors);
		params.put("pathLogo", pathLogo);
		params.put("companyName", company.getCompanyName());
		AddressBean addressBean = new AddressBean();
		addressBean = setAddressBean(company.getAddress());
		params.put("companyAddress", addressBean.getDetail());
		params.put("companyTaxId", company.getTaxId());
		params.put("companyMobile", company.getContact().getMobile());
		params.put("companyFax", company.getContact().getFax());
		
		SimpleDateFormat formatDataThyyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.US);
		params.put("barCodeCustomer", invoice.getServiceApplication().getCustomer().getCustCode()+"\r"+formatDataThyyyyMMdd.format(new Date()));
		params.put("barCodeCustomerText", invoice.getServiceApplication().getCustomer().getCustCode()+" "+formatDataThyyyyMMdd.format(new Date()));
		
		String pattern = "(\\D)";

		// setBarCode
//		reportInvoiceOrReceiptBean.setBarCode(String.format("|%1s\r%2s\r%3s\r%4s",
//				company.getTaxId(),
//				invoice.getServiceApplication().getCustomer().getCustCode().replaceAll(pattern, ""),
//				invoice.getInvoiceCode().replaceAll(pattern, ""),
//				String.valueOf(invoice.getAmount()).replaceAll("\\.", "")));
//		reportInvoiceOrReceiptBean.setBarCodeText(String.format("|%1s %2s %3s %4s",
//				company.getTaxId(),
//				invoice.getServiceApplication().getCustomer().getCustCode().replaceAll(pattern, ""),
//				invoice.getInvoiceCode().replaceAll(pattern, ""),
//				String.valueOf(invoice.getAmount()).replaceAll("\\.", "")));
		
		reportInvoiceOrReceiptBean.setServiceCode(invoice.getServiceApplication().getServicePackage().getPackageCode());
		reportInvoiceOrReceiptBean.setPrivateVat(new DecimalFormat("#,##0.00").format(vat));
		reportInvoiceOrReceiptBean.setPrivateNotVat(new DecimalFormat("#,##0.00").format(invoice.getAmount()));
		reportInvoiceOrReceiptBean.setCompanyVat("VAT "+company.getVat()+"%");
		
		params.put("amount", invoice.getAmount()+vat);
		NumberFormat nf = new NumberFormat();
		String thaiBaht = nf.getText(invoice.getAmount()+vat);
		params.put("thaiBaht", thaiBaht);
		
		resUse.add(reportInvoiceOrReceiptBean);
		jasperRender.setBeanList(resUse);
		jasperRender.setParams(params);
			try {
				byte[] bytesFinal = null;
//				if(null != worksheet && !"S".equals(worksheet.getStatus())){
//					byte[] bytes1 = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
//					byte[] bytes2 = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport+"coppy",request));
//					List<ByteArrayInputStream> is = new ArrayList<ByteArrayInputStream>();
//					ByteArrayInputStream myInputStream1 = new ByteArrayInputStream(bytes1);
//					ByteArrayInputStream myInputStream2 = new ByteArrayInputStream(bytes2);
//					is.add(myInputStream1);
//					is.add(myInputStream2);
//					bytesFinal = PdfMergeUtils.mergeToByteArrayV2(is);
//				}else{
//					bytesFinal = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
//				}
				
				bytesFinal = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
				
				response.reset();
				response.resetBuffer();
				response.setContentType("application/pdf");
				response.setContentLength(bytesFinal.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytesFinal, 0, bytesFinal.length);
				ouputStream.flush();
				ouputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		
		return null;
	}
	
	@RequestMapping(value = "contactReceipt/exportPdf/workSheetId/{workSheetId}", method = RequestMethod.GET)
	public ModelAndView contactByworkSheetId(Model model,
			@PathVariable Long workSheetId,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : contactReceiptExportPdf][Type : Controller][workSheetId : {}]",workSheetId);
		ModelAndView modelAndView = new ModelAndView();
		String nameReport = EMPTY_STRING;
		Map<String, Object> params = new HashMap<String, Object>();
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/easynet.png");
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("- พิมพ์เมื่อวันที่ dd MMMM yyyy เวลา HH.mm น. -", new Locale("TH", "th"));
		params.put("pathLogo", pathLogo);
		//check permission
		if(isPermission()){
			Personnel personnel = getPersonnelLogin();
			String printBy = String.format("%1s%2s %3s",personnel.getPrefix(),personnel.getFirstName(),personnel.getLastName());
			JasperRender jasperRender = new JasperRender();
			List<Object> resUse = new ArrayList<Object>();
			ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(workSheetId);
			if(serviceApplication != null){
				ChangeServiceController changeServiceController = new ChangeServiceController();
				changeServiceController.setMessageSource(messageSource);
				ServiceApplicationBean serviceApplicationBean = populateEntityToDto(serviceApplication);
				
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
				ServicePackageType servicePackageType = servicePackageTypeService.getServicePackageTypeById(serviceApplication.getServicePackage().getServicePackageType().getId());
				DecimalFormat df = new DecimalFormat("#,###.##");
				if ("00003".equals(servicePackageType.getPackageTypeCode())) {
					nameReport = "contactInternetReceipt"; // หนังสือสัญญาอินเตอร์เน็ต
					
					ContactInternetReportBean contactInternetReportBean = new ContactInternetReportBean();
					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables = new ArrayList<WorkSheetReportBeanSubTable>();
					float sumDeposit = 0;
					String userName = "";
					String password = "";
					if (null != serviceApplicationBean.getProductitemList() && serviceApplicationBean.getProductitemList().size() > 0) {
						for (ProductItemBean productItemBean : serviceApplicationBean.getProductitemList()) {
							WorkSheetReportBeanSubTable report = new WorkSheetReportBeanSubTable();
							
							if ("E".equals(productItemBean.getProduct().getType())) {
								EquipmentProductBean equipmentProductBean = (EquipmentProductBean)productItemBean.getProduct();
								report.setUnitName(equipmentProductBean.getUnit().getUnitName());
								report.setListEquipment(equipmentProductBean.getProductName());
								report.setSerialNo(equipmentProductBean.getEquipmentProductItemBeans().get(0).getSerialNo());
							} else if ("I".equals(productItemBean.getProduct().getType())) {
								InternetProductBean internetProductBean = (InternetProductBean) productItemBean.getProduct();
								report.setUnitName(internetProductBean.getUnit().getUnitName());
								report.setListEquipment(internetProductBean.getProductName());
								report.setSerialNo("");
								if (internetProductBean.getInternetProductBeanItems().size() > 0) {
									for (InternetProductBeanItem internetProductBeanItem : internetProductBean.getInternetProductBeanItems()) {
										if ("5".equals(internetProductBeanItem.getStatus())) {
											userName = internetProductBeanItem.getUserName();
											password = internetProductBeanItem.getPassword();
										}
									}									
								}
							} else if ("S".equals(productItemBean.getProduct().getType())) {
								ServiceProductBean serviceProductBean = (ServiceProductBean) productItemBean.getProduct();
								report.setUnitName(serviceProductBean.getUnit().getUnitName());
								report.setListEquipment(serviceProductBean.getProductName());
								report.setSerialNo("");
							}
							String status = "";
							if (productItemBean.getProductItemWorksheetBeanList().size() == 0) {
								report.setQuantity(String.valueOf(productItemBean.getQuantity()));
								report.setFloatDeposit(productItemBean.getAmount());
								report.setPrice(df.format(productItemBean.getPrice()));
								report.setDeposit(df.format(productItemBean.getAmount()));
								sumDeposit += productItemBean.getAmount();
							} else {
								report.setQuantity(String.valueOf(productItemBean.getProductItemWorksheetBeanList().get(0).getQuantity()));
								report.setFloatDeposit(productItemBean.getProductItemWorksheetBeanList().get(0).getAmount());
								report.setPrice(df.format(productItemBean.getProductItemWorksheetBeanList().get(0).getPrice()));
								report.setDeposit(df.format(productItemBean.getProductItemWorksheetBeanList().get(0).getAmount()));
								sumDeposit += productItemBean.getProductItemWorksheetBeanList().get(0).getAmount();
							}
							
							if (productItemBean.getProductItemWorksheetBeanList().size() != 0) {
								if (productItemBean.getProductItemWorksheetBeanList().get(0).getLendStatus() == 1) {
									status = "ยืม";
								}
								if (productItemBean.getProductItemWorksheetBeanList().get(0).getFeeLend() == 1) {
									status = "ฟรี";
								}
							}
							report.setLendStatus(status);
							workSheetReportBeanSubTables.add(report);
						}
					}
					MoneyReader reader;
					reader = new MoneyReader(serviceApplication.getServicePackage().getMonthlyServiceFee());
		    		String servicePriceTxt = reader.Read();
		    		reader = new MoneyReader(serviceApplication.getServicePackage().getInstallationFee());
		    		String installationFeeTxt = reader.Read();
		    		reader = new MoneyReader(serviceApplication.getServicePackage().getDeposit());
		    		String depositTxt = reader.Read();
		    		float sum = serviceApplication.getServicePackage().getMonthlyServiceFee() 
		    				+ serviceApplication.getServicePackage().getInstallationFee() 
		    				+ serviceApplication.getServicePackage().getDeposit() + sumDeposit;
		    		reader = new MoneyReader(sum);
		    		String sumTxt = reader.Read();
					contactInternetReportBean.setWorkSheetReportBeanSubTables(workSheetReportBeanSubTables);
					contactInternetReportBean.setCustomer(serviceApplication.getCustomer().getPrefix()+""+serviceApplication.getCustomer().getFirstName()+"  "+serviceApplication.getCustomer().getLastName());
					contactInternetReportBean.setCreateDate(serviceApplicationBean.getCreateDateTh());
					contactInternetReportBean.setPlateNumber(serviceApplicationBean.getPlateNumber());
					
					contactInternetReportBean.setCodeService(servicePackageType.getPackageTypeCode());
					contactInternetReportBean.setTypeService(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
					contactInternetReportBean.setNameService(servicePackageType.getPackageTypeName());
					contactInternetReportBean.setPayment("ตามรอบบิล");
					contactInternetReportBean.setDuration(String.valueOf(serviceApplication.getPerMonth()));
					contactInternetReportBean.setServicePrice(df.format(serviceApplication.getServicePackage().getMonthlyServiceFee()));
					contactInternetReportBean.setServicePriceTxt(servicePriceTxt = serviceApplication.getServicePackage().getMonthlyServiceFee() == 0 ? "ศุนย์บาทถ้วน" : servicePriceTxt);
					contactInternetReportBean.setInstallationFee(df.format(serviceApplication.getServicePackage().getInstallationFee()));
					contactInternetReportBean.setInstallationFeeTxt(installationFeeTxt = serviceApplication.getServicePackage().getInstallationFee() == 0 ? "ศุนย์บาทถ้วน" :installationFeeTxt);
					contactInternetReportBean.setDeposit(df.format(serviceApplication.getServicePackage().getDeposit()));
					contactInternetReportBean.setDepositTxt(depositTxt = serviceApplication.getServicePackage().getDeposit()  == 0 ? "ศุนย์บาทถ้วน": depositTxt);
					contactInternetReportBean.setSum(df.format(sum));
					contactInternetReportBean.setSumTxt(sumTxt = sum == 0 ? "ศุนย์บาทถ้วน": sumTxt);
					contactInternetReportBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
					contactInternetReportBean.setHouseRegistrationDocuments(serviceApplicationBean.isHouseRegistrationDocuments());
					contactInternetReportBean.setIdentityCardDocuments(serviceApplicationBean.isIdentityCardDocuments());
					contactInternetReportBean.setOtherDocuments(serviceApplicationBean.isOtherDocuments());
					contactInternetReportBean.setRemarkOtherDocuments(serviceApplicationBean.getRemarkOtherDocuments());
					contactInternetReportBean.setUserName(userName);
					contactInternetReportBean.setPassword(password);
					contactInternetReportBean.setRecipient(printBy);
					contactInternetReportBean.setPrintDate(formatDataTimeTh.format(new Date()));
					resUse.add(contactInternetReportBean);
				}else {
					nameReport = "contactDigitalReceipt";
					
					ContactInternetReportBean contactInternetReportBean = new ContactInternetReportBean();
					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables = new ArrayList<WorkSheetReportBeanSubTable>();
					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables2 = new ArrayList<WorkSheetReportBeanSubTable>();
					float sumDeposit = 0;
					for (ProductItemBean productItemBean : serviceApplicationBean.getProductitemList()) {
						WorkSheetReportBeanSubTable report = new WorkSheetReportBeanSubTable();
						WorkSheetReportBeanSubTable report2 = new WorkSheetReportBeanSubTable();
						if ("E".equals(productItemBean.getProduct().getType())) {
							EquipmentProductBean equipmentProductBean = (EquipmentProductBean)productItemBean.getProduct();
							report.setUnitName(equipmentProductBean.getUnit().getUnitName());
							report.setListEquipment(equipmentProductBean.getProductName());
							report.setSerialNo(equipmentProductBean.getEquipmentProductItemBeans().get(0).getSerialNo());
						} else if ("I".equals(productItemBean.getProduct().getType())) {
							InternetProductBean internetProductBean = (InternetProductBean) productItemBean.getProduct();
							report.setUnitName(internetProductBean.getUnit().getUnitName());
							report.setListEquipment(internetProductBean.getProductName());
							report.setSerialNo("");
						} else if ("S".equals(productItemBean.getProduct().getType())) {
							ServiceProductBean serviceProductBean = (ServiceProductBean) productItemBean.getProduct();
							report.setUnitName(serviceProductBean.getUnit().getUnitName());
							report.setListEquipment(serviceProductBean.getProductName());
							report.setSerialNo("");
						}
						String status = "";
						if (productItemBean.getProductItemWorksheetBeanList().size() == 0) {
							report.setQuantity(String.valueOf(productItemBean.getQuantity()));
							report.setFloatDeposit(productItemBean.getAmount());
							report.setPrice(df.format(productItemBean.getPrice()));
							report.setDeposit(df.format(productItemBean.getAmount()));
							sumDeposit += productItemBean.getAmount();
						} else {
							report.setQuantity(String.valueOf(productItemBean.getProductItemWorksheetBeanList().get(0).getQuantity()));
							report.setFloatDeposit(productItemBean.getProductItemWorksheetBeanList().get(0).getAmount());
							report.setPrice(df.format(productItemBean.getProductItemWorksheetBeanList().get(0).getPrice()));
							report.setDeposit(df.format(productItemBean.getProductItemWorksheetBeanList().get(0).getAmount()));
							sumDeposit += productItemBean.getProductItemWorksheetBeanList().get(0).getAmount();
						}
						if (productItemBean.isLend()) {
							status = "ยืม";
							if ("E".equals(productItemBean.getProduct().getType())) {
								EquipmentProductBean equipmentProductBean = (EquipmentProductBean)productItemBean.getProduct();
								report2.setUnitName(equipmentProductBean.getUnit().getUnitName());
								report2.setListEquipment(equipmentProductBean.getProductName());
								report2.setSerialNo(equipmentProductBean.getEquipmentProductItemBeans().get(0).getSerialNo());
							} else if ("I".equals(productItemBean.getProduct().getType())) {
								InternetProductBean internetProductBean = (InternetProductBean) productItemBean.getProduct();
								report2.setUnitName(internetProductBean.getUnit().getUnitName());
								report2.setListEquipment(internetProductBean.getProductName());
								report2.setSerialNo("");
							} else if ("S".equals(productItemBean.getProduct().getType())) {
								ServiceProductBean serviceProductBean = (ServiceProductBean) productItemBean.getProduct();
								report2.setUnitName(serviceProductBean.getUnit().getUnitName());
								report2.setListEquipment(serviceProductBean.getProductName());
								report2.setSerialNo("");
							}
							if (productItemBean.getProductItemWorksheetBeanList().size() == 0) {
								report2.setQuantity(String.valueOf(productItemBean.getQuantity()));
								report2.setFloatDeposit(productItemBean.getAmount());
								report2.setPrice(String.valueOf(productItemBean.getPrice()));

							} else {
								report2.setQuantity(String.valueOf(productItemBean.getProductItemWorksheetBeanList().get(0).getQuantity()));
								report2.setFloatDeposit(productItemBean.getProductItemWorksheetBeanList().get(0).getAmount());
								report2.setPrice(String.valueOf(productItemBean.getProductItemWorksheetBeanList().get(0).getPrice()));
				
							}
							report2.setLendStatus(status);
							workSheetReportBeanSubTables2.add(report2);
						}
						if (productItemBean.isFree()) {
							status = "ฟรี";
						}
						report.setLendStatus(status);
						workSheetReportBeanSubTables.add(report);
					}
					MoneyReader reader;
					reader = new MoneyReader(serviceApplication.getServicePackage().getMonthlyServiceFee());
		    		String servicePriceTxt = reader.Read();
		    		reader = new MoneyReader(serviceApplication.getServicePackage().getInstallationFee());
		    		String installationFeeTxt = reader.Read();
		    		reader = new MoneyReader(serviceApplication.getServicePackage().getDeposit());
		    		String depositTxt = reader.Read();
		    		float sum = serviceApplication.getServicePackage().getMonthlyServiceFee() + serviceApplication.getServicePackage().getInstallationFee() 
		    				+ serviceApplication.getServicePackage().getDeposit() + sumDeposit;
		    		reader = new MoneyReader(sum);
		    		String sumTxt = reader.Read();
		    		
					contactInternetReportBean.setWorkSheetReportBeanSubTables(workSheetReportBeanSubTables);
					contactInternetReportBean.setWorkSheetReportBeanSubTables2(workSheetReportBeanSubTables2);
					contactInternetReportBean.setCustomer(serviceApplication.getCustomer().getPrefix()+""+serviceApplication.getCustomer().getFirstName()+"  "+serviceApplication.getCustomer().getLastName());
					contactInternetReportBean.setCreateDate(serviceApplicationBean.getCreateDateTh());
					
					contactInternetReportBean.setCodeService(servicePackageType.getPackageTypeCode());
					contactInternetReportBean.setTypeService(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
					contactInternetReportBean.setNameService("อินเตอร์เน็ต");
					contactInternetReportBean.setPayment("ตามรอบบิล");
					contactInternetReportBean.setDuration(String.valueOf(serviceApplication.getPerMonth()));
					contactInternetReportBean.setServicePrice(df.format(serviceApplication.getServicePackage().getMonthlyServiceFee()));
					contactInternetReportBean.setServicePriceTxt(servicePriceTxt = serviceApplication.getServicePackage().getMonthlyServiceFee() == 0 ? "ศุนย์บาทถ้วน" : servicePriceTxt);
					contactInternetReportBean.setInstallationFee(df.format(serviceApplication.getServicePackage().getInstallationFee()));
					contactInternetReportBean.setInstallationFeeTxt(installationFeeTxt = serviceApplication.getServicePackage().getInstallationFee() == 0 ? "ศุนย์บาทถ้วน" :installationFeeTxt);
					contactInternetReportBean.setDeposit(df.format(serviceApplication.getServicePackage().getDeposit()));
					contactInternetReportBean.setDepositTxt(depositTxt = serviceApplication.getServicePackage().getDeposit()  == 0 ? "ศุนย์บาทถ้วน": depositTxt);
					contactInternetReportBean.setSum(df.format(sum));
					contactInternetReportBean.setSumTxt(sumTxt = sum == 0 ? "ศุนย์บาทถ้วน": sumTxt);
					contactInternetReportBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
					
					contactInternetReportBean.setCusCode(serviceApplication.getCustomer().getCustCode());
					contactInternetReportBean.setMobile(serviceApplication.getCustomer().getContact().getMobile());
					contactInternetReportBean.setIdentityNumber(serviceApplication.getCustomer().getIdentityNumber());
					contactInternetReportBean.setServiceDate(serviceApplicationBean.getServiceDate());
					for (AddressBean addressBean : serviceApplicationBean.getAddressList()) {
						if ("3".equals(addressBean.getAddressType())) {
							contactInternetReportBean.setCollectDetail(addressBean.getCollectAddressDetail());
						}
					}
					contactInternetReportBean.setRecipient(printBy);
					contactInternetReportBean.setPrintDate(formatDataTimeTh.format(new Date()));
					
					contactInternetReportBean.setServiceType(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeName());
					contactInternetReportBean.setPackages(serviceApplication.getServicePackage().getPackageName());
					
					resUse.add(contactInternetReportBean);
				}
				
			}
			jasperRender.setParams(params);
			jasperRender.setBeanList(resUse);
			try {
				byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF,
						jasperJrxmlComponent.compileJasperReport(nameReport, request));
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
			
		}else{
			//no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		
		return null;
	}
	
	@RequestMapping(value = "invoiceOrReceipt/exportPdf/invoiceId/{invoiceId}/companyId/{companyId}/discount/{discount}", 
			method = RequestMethod.GET)
	public ModelAndView invoiceOrReceiptByinvoiceId(Model model,
			@PathVariable Long invoiceId,
			@PathVariable Long companyId,
			@PathVariable float discount,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : invoiceOrReceiptByinvoiceId][Type : Controller][invoiceId : {}][companyId : {}][discount : {}]",invoiceId,companyId,discount);
		ModelAndView modelAndView = new ModelAndView();
		
		String nameReport = "invoiceOrReceiptA4"; // ใบแจ้งหนี้/ใบรับเงินชั่วคราว
		//check permission
		if(isPermission()){
		JasperRender jasperRender = new JasperRender();
		List<Object> resUse = new ArrayList<Object>();
		Worksheet worksheet = new Worksheet();
		Invoice invoice = financialService.getInvoiceById(invoiceId);
		if(null != invoice){
			worksheet = invoice.getWorkSheet();
			if(!"O".equals(invoice.getInvoiceType())){// รายเดือน
				nameReport = "invoiceOrReceiptA4_Old_V2";
			}
			
			if("S".equals(invoice.getStatus())){
				nameReport = "receipt"; // ใบเสร็จรับเงิน
			}
			
			Personnel personnel = getPersonnelLogin();
//			if(null != personnel){
//				if(personnel.getId()!=1 && personnel.isCashier()){
//					nameReport = "invoice_3inches"; // ใบเสร็จรับเงินขนาด 3 นิ้ว
//					if(null != invoice){
//						if("S".equals(invoice.getStatus())){
//							nameReport = "receipt_3inches"; // ใบเสร็จรับเงินขนาด 3 นิ้ว
//						}
//					}
//				}
//			}
			
			Company company = companyService.getCompanyById(companyId);
			float amount = invoice.getAmount();
			if(discount > 0){
				amount = amount - discount;
			}
			double vat = (amount*(company.getVat()/100));
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
			reportInvoiceOrReceiptBean.setDiscountValue(discount);
			reportInvoiceOrReceiptBean.setVat(vat);
			reportInvoiceOrReceiptBean.setVatCompany(company.getVat());
			if(null != worksheet){
				setReportInvoiceOrReceiptBean(worksheet,reportInvoiceOrReceiptBean,request);
				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
			}else if(null != invoice){
				setReportInvoiceOrReceiptBean(invoice,reportInvoiceOrReceiptBean,request);
				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/easynet.png");
			String scissors = request.getSession().getServletContext().getRealPath("/jasper/scissors.png");
			String truemoneywallet = request.getSession().getServletContext().getRealPath("/jasper/truemoneywallet.jpg");
			String lotus = request.getSession().getServletContext().getRealPath("/jasper/lotus.png");
			
			params.put("truemoneywallet", truemoneywallet);
			params.put("scissors", scissors);
			params.put("pathLogo", pathLogo);
			params.put("lotus", lotus);
			params.put("companyName", company.getCompanyName());
			AddressBean addressBean = new AddressBean();
			addressBean = setAddressBean(company.getAddress());
			params.put("companyAddress", addressBean.getDetail());
			params.put("companyTaxId", company.getTaxId());
			params.put("companyMobile", company.getContact().getMobile());
			params.put("companyFax", company.getContact().getFax());
			if(null != personnel){
			params.put("personnelName", personnel.getFirstName()+" "+personnel.getLastName());
			params.put("personnelCode", personnel.getPersonnelCode());
			}
			SimpleDateFormat formatDataThyyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.US);
			params.put("barCodeCustomer", invoice.getServiceApplication().getCustomer().getCustCode()+"\r"+formatDataThyyyyMMdd.format(new Date()));
			params.put("barCodeCustomerText", invoice.getServiceApplication().getCustomer().getCustCode()+" "+formatDataThyyyyMMdd.format(new Date()));
			
			String pattern = "(\\D)";

			if("O".equals(invoice.getInvoiceType())){// รายเดือน
			// setBarCode
			reportInvoiceOrReceiptBean.setBarCode(String.format("|%1s\r%2s\r%3s\r%4s",
					company.getTaxId(),
					invoice.getServiceApplication().getCustomer().getCustCode().replaceAll(pattern, ""),
					invoice.getInvoiceCode().replaceAll(pattern, ""),
					new DecimalFormat("###0.00").format(amount+vat).replaceAll("\\.", "")));
			reportInvoiceOrReceiptBean.setBarCodeText(String.format("|%1s %2s %3s %4s",
					company.getTaxId(),
					invoice.getServiceApplication().getCustomer().getCustCode().replaceAll(pattern, ""),
					invoice.getInvoiceCode().replaceAll(pattern, ""),
					new DecimalFormat("###0.00").format(amount+vat).replaceAll("\\.", "")));
			}
			
			reportInvoiceOrReceiptBean.setServiceCode(invoice.getServiceApplication().getServicePackage().getPackageCode());
			reportInvoiceOrReceiptBean.setPrivateVat(new DecimalFormat("#,##0.00").format(vat));
			reportInvoiceOrReceiptBean.setPrivateNotVat(new DecimalFormat("#,##0.00").format(amount+discount));
			reportInvoiceOrReceiptBean.setCompanyVat("VAT "+company.getVat()+"%");
			reportInvoiceOrReceiptBean.setDiscount(new DecimalFormat("#,##0.00").format(discount));
			
			params.put("amount", new DecimalFormat("#,##0.00").format(amount+vat));
			NumberFormat nf = new NumberFormat();
			String thaiBaht = nf.getText(amount+vat);
			params.put("thaiBaht", thaiBaht);
			
			resUse.add(reportInvoiceOrReceiptBean);
			jasperRender.setBeanList(resUse);
			jasperRender.setParams(params);
		}
			try {
				byte[] bytesFinal = null;
				if("S".equals(invoice.getStatus())){
					byte[] bytes1 = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
					byte[] bytes2 = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport+"coppy",request));
					List<ByteArrayInputStream> is = new ArrayList<ByteArrayInputStream>();
					ByteArrayInputStream myInputStream1 = new ByteArrayInputStream(bytes1);
					ByteArrayInputStream myInputStream2 = new ByteArrayInputStream(bytes2);
					is.add(myInputStream1);
					is.add(myInputStream2);
					bytesFinal = PdfMergeUtils.mergeToByteArrayV2(is);
				}else{
					bytesFinal = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
				}

				response.reset();
				response.resetBuffer();
				response.setContentType("application/pdf");
				response.setContentLength(bytesFinal.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytesFinal, 0, bytesFinal.length);
				ouputStream.flush();
				ouputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		
		return null;
	}
	
	private void setExpenseItem(Invoice invoice, ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean) {
		if(null != invoice && null != reportInvoiceOrReceiptBean){
				InvoiceController invoiceController = new InvoiceController();
				invoiceController.setMessageSource(messageSource);
				invoiceController.setServiceApplicationService(serviceApplicationService);
				invoiceController.setUnitService(unitService);
				InvoiceDocumentBean invoiceBean = invoiceController.PoppulateInvoiceEntityToDto(invoice);
			
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
				c.add(Calendar.MONTH, perMounth);
//				c.add(Calendar.DAY_OF_MONTH, -1);
				Date startUseDate = c.getTime();
				String roundService = "";
				String startService = null == startUseDate ? "" : formateDateServiceReound.format(startUseDate);
				String endService = null == endUseDate ? "" : formateDateServiceReound.format(endUseDate);
//				if (null != startService) {
//					roundService = "(" + endService + " - " + startService + ")";
//				}
				
				formateDateServiceReound = new SimpleDateFormat(
						messageSource.getMessage("date.format.type7", null, LocaleContextHolder.getLocale()),
						new Locale("TH", "th"));
				roundService = null == endUseDate ? "" : formateDateServiceReound.format(endUseDate);
				
				if ("S".equals(invoice.getStatus()) && null != startService) {
					roundService = "(" + endService + " - " + startService + ")";
				}
				
				int diffYear = endUseDate.getYear() - startUseDate.getYear();
				int diffMonth = diffYear * 12 + endUseDate.getMonth() - startUseDate.getMonth();
				
				if(diffMonth < 0){
					diffMonth = diffMonth * -1;
				}
				
				formateDateServiceReound = new SimpleDateFormat(
						messageSource.getMessage("date.format.type5", null, LocaleContextHolder.getLocale()),
						new Locale("TH", "th"));
				String payment = null == date ? "" : formateDateServiceReound.format(date);
				
				
			List<ExpenseItemBean> expenseItemList = new ArrayList<ExpenseItemBean>();
				
			if("O".equals(invoiceBean.getInvoiceType())){// รายเดือน
				ExpenseItemBean expenseItem = new ExpenseItemBean();
				float calAmountAdditionNoVat = 0f, displayAmountAdditionNoVat = 0f;
				if("R".equals(invoiceBean.getAdditionMonthlyFee())){
					calAmountAdditionNoVat = invoiceBean.getAdditionMonthlyFee().getAmount();
					displayAmountAdditionNoVat = invoiceBean.getAdditionMonthlyFee().getAmount() * (-1);
				}else{
					calAmountAdditionNoVat = invoiceBean.getAdditionMonthlyFee().getAmount() * (-1);
					displayAmountAdditionNoVat = invoiceBean.getAdditionMonthlyFee().getAmount();
				}
				int countTbVatTypeO = 1;
				float calSpencialDiscountNoVat = 0f;
				if(invoiceBean.isCutting()){
					calSpencialDiscountNoVat = invoiceBean.getSpecialDiscount();
				}
				expenseItem.setNo(""+(countTbVatTypeO++));
				expenseItem.setDescription(roundService);
				expenseItem.setAmount(diffMonth + " เดือน");
//				expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat));
//				expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat));
				float price = invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat;
				float totalPrice = invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat;
				double vat = reportInvoiceOrReceiptBean.getVat();
				if ("S".equals(invoice.getStatus())) {
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(price));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalPrice));
				}else{
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(price+vat-reportInvoiceOrReceiptBean.getDiscountValue()));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalPrice+vat-reportInvoiceOrReceiptBean.getDiscountValue()));
				}
				
				expenseItem.setPayment(payment);
				
				// ยอดค้างชําระ
				float overdue = 0, prePrice = 0;  // ยอดรวม ชําระครั้งก่อน
				ServiceApplication serviceApplication = invoice.getServiceApplication();
				if(null != serviceApplication){
					List<Invoice> invoiceList = serviceApplication.getInvoices(); // ใบบิลทั้งหมดของใบสมัครนี้
					if(null != invoiceList){
						for(Invoice inv:invoiceList){
							if("O".equals(inv.getInvoiceType()) && 
									"O".equals(inv.getStatus())){  // O = เป็นบิลรอบเดือน && O = เกินวันนัดชำระ
								overdue += (inv.getAmount()+reportInvoiceOrReceiptBean.getVat());
							}
							if("O".equals(inv.getInvoiceType()) && 
									"S".equals(inv.getStatus())){  // O = เป็นบิลรอบเดือน && S = ชําระครั้งก่อน
								prePrice = inv.getAmount();
							}
						}
					}
				}
				expenseItem.setOverdue(new DecimalFormat("#,##0.00").format(overdue));
				expenseItem.setPrePrice(new DecimalFormat("#,##0.00").format(prePrice));
				
				expenseItemList.add(expenseItem);
				reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);// row1
				
				if("N".equals(invoiceBean.getAdditionMonthlyFee().getType())){
					if("A".equals(invoiceBean.getAdditionMonthlyFee().getType())){
						expenseItem = new ExpenseItemBean();
						expenseItem.setNo(""+(countTbVatTypeO++));
						expenseItem.setDescription("ค่าบริการเพิ่มจุดติดตั้ง");
						expenseItem.setAmount("1 หน่วย");
						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(displayAmountAdditionNoVat));
						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(displayAmountAdditionNoVat));
						expenseItemList.add(expenseItem);
						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);// row2
					}else if("R".equals(invoiceBean.getAdditionMonthlyFee().getType())){
						expenseItem = new ExpenseItemBean();
						expenseItem.setNo(""+(countTbVatTypeO++));
						expenseItem.setDescription("ค่าบริการลดจุดติดตั้ง");
						expenseItem.setAmount("1 หน่วย");
						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(displayAmountAdditionNoVat));
						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(displayAmountAdditionNoVat));
						expenseItemList.add(expenseItem);
						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);// row2
					}
				}else if(invoiceBean.isCutting()){
					expenseItem = new ExpenseItemBean();
					expenseItem.setNo(""+(countTbVatTypeO++));
					expenseItem.setDescription("ส่วนลดพิเศษตัดสาย");
					expenseItem.setAmount("1 หน่วย");
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getSpecialDiscount()*(-1)));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getSpecialDiscount()*(-1)));
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);// row3
				}

				
			}else{ // อื่นๆ
				ExpenseItemBean expenseItem = new ExpenseItemBean();
				int countTbNoVat = 1;
				float totalListNoVat = 0f;
				if("C_S".equals(invoiceBean.getWorksheet().getWorkSheetType())){
					if(!invoiceBean.getServiceApplication().getServicepackage().isMonthlyService()){
						if(invoiceBean.getServiceApplication().getOneServiceFee() > 0){
							expenseItem = new ExpenseItemBean();
							expenseItem.setNo(""+(countTbNoVat++));
							expenseItem.setDescription("ค่าบริการรายครั้ง");
							expenseItem.setAmount("1 ครั้ง");
							expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getOneServiceFee()));
							expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getOneServiceFee()));
							totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getOneServiceFee();
							expenseItemList.add(expenseItem);
							reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
						}
					}else{// รายเดือน
						if(invoiceBean.getServiceApplication().getMonthlyServiceFee() > 0){
							if(invoiceBean.getServiceApplication().getPerMonth() == 1){ // 1 เดือน
								expenseItem = new ExpenseItemBean();
								expenseItem.setNo(""+(countTbNoVat++));
								expenseItem.setDescription("ค่าบริการรอบบิลแรกเดือน "+roundService);
								
								SimpleDateFormat format = new SimpleDateFormat("dd", new Locale("en", "US"));
								// คำนวนค่าบริการคิดรอบบิลแรกเป็นรายวัน
								float price = invoiceBean.getServiceApplication().getMonthlyServiceFee();
								int lastDateOfmonth = new DateUtil().lastDateOfmonth(new Date());
								int currentDateOfmonth = Integer.parseInt(format.format(new Date()));
								
								int dateTotal = lastDateOfmonth - currentDateOfmonth;
								float pricePerDay = price/lastDateOfmonth;
								float totalPrice = pricePerDay*dateTotal;
								
								expenseItem.setAmount(dateTotal+" วัน");
								expenseItem.setPrice(new DecimalFormat("#,##0.00").format(pricePerDay));
								expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalPrice));
								totalListNoVat = totalListNoVat + totalPrice;
								expenseItemList.add(expenseItem);
								reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
								double vat = (totalPrice*(reportInvoiceOrReceiptBean.getVatCompany()/100));
								reportInvoiceOrReceiptBean.setVat(vat);
							}else if(invoiceBean.getServiceApplication().getPerMonth() > 0){ // มากกว่า 1 เดือน
								expenseItem = new ExpenseItemBean();
								expenseItem.setNo(""+(countTbNoVat++));
								expenseItem.setDescription("ค่าบริการรอบบิล "+roundService);
								expenseItem.setAmount(invoiceBean.getServiceApplication().getServicepackage().getPerMounth()+" เดือน");
								expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getMonthlyServiceFee()));
								expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getMonthlyServiceFee()));
								totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getMonthlyServiceFee();
								expenseItemList.add(expenseItem);
								reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
							}
						}
					}
					
					if(invoiceBean.getServiceApplication().getInstallationFee() > 0){
						expenseItem = new ExpenseItemBean();
						expenseItem.setNo(""+(countTbNoVat++));
						expenseItem.setDescription("ค่าติดตั้ง");
						expenseItem.setAmount("1 หน่วย");
						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getInstallationFee()));
						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getInstallationFee()));
						totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getInstallationFee();
						expenseItemList.add(expenseItem);
						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
					}
					
					if(invoiceBean.getServiceApplication().getDeposit() > 0){
						expenseItem = new ExpenseItemBean();
						expenseItem.setNo(""+(countTbNoVat++));
						expenseItem.setDescription("ค่ามัดจำอุปกรณ์");
						expenseItem.setAmount("1 หน่วย");
						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getDeposit()));
						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getDeposit()));
						totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getDeposit();
						expenseItemList.add(expenseItem);
						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
					}
				}
				
				// add point <<
				if("C_AP".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice() > 0){
					expenseItem = new ExpenseItemBean();
					expenseItem.setNo(""+(countTbNoVat++));
					expenseItem.setDescription("ค่าเพิ่มจุด");
					expenseItem.setAmount("1 หน่วย");
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice()));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice()));
					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice();
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				}
				// add point >>
				
				// move point <<
				if("C_MP".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice() > 0){
					expenseItem = new ExpenseItemBean();
					expenseItem.setNo(""+(countTbNoVat++));
					expenseItem.setDescription("ค่าย้ายจุด");
					expenseItem.setAmount("1 หน่วย");
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice()));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice()));
					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice();
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				}
				// move point >>
				
				// move cable <<
				if("C_M".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice() > 0){
					expenseItem = new ExpenseItemBean();
					expenseItem.setNo(""+(countTbNoVat++));
					expenseItem.setDescription("ค่าย้ายสาย");
					expenseItem.setAmount("1 หน่วย");
					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice()));
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice()));
					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice();
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				}
				// move cable >>
				
				// productItem  S<<
				WorksheetBean worksheetBean = invoiceBean.getWorksheet();
				if(null != worksheetBean){
					List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
					if(null != productItemBeanList && productItemBeanList.size() > 0){
						for(ProductItemBean productItemBean:productItemBeanList){
							if("S".equals(productItemBean.getType()) && productItemBean.getPrice() > 0){
								expenseItem = new ExpenseItemBean();
								expenseItem.setNo(""+(countTbNoVat++));
								expenseItem.setDescription(productItemBean.getServiceProductBean().getProductName());
								expenseItem.setAmount(productItemBean.getQuantity()+" "+productItemBean.getServiceProductBean().getUnit().getUnitName());
								expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemBean.getPrice()));
								expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemBean.getAmount()));
								totalListNoVat = totalListNoVat + productItemBean.getAmount();
								expenseItemList.add(expenseItem);
								reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
							}
						}
					}
				}
				// productItem S >>
				
				// deposit <<
				if(!"C_S".equals(invoiceBean.getWorksheet().getWorkSheetType())){
					if(null != worksheetBean){
						List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
						if(null != productItemBeanList && productItemBeanList.size() > 0){
							for(ProductItemBean productItemBean:productItemBeanList){
								if("E".equals(productItemBean.getType()) && productItemBean.getPrice() > 0){
									List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
									if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
										for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
											if(productItemWorksheetBean.isLend() && productItemWorksheetBean.getDeposit() > 0){
												expenseItem = new ExpenseItemBean();
												expenseItem.setNo(""+(countTbNoVat++));
												String description = productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName();
												if(!"".equals(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo())){
													description += "("+productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo()+")";
												}
												expenseItem.setDescription(description);
												expenseItem.setAmount(productItemWorksheetBean.getQuantity()+" "+productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
												expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getDeposit()));
												expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getDeposit()));
												totalListNoVat = totalListNoVat + productItemWorksheetBean.getDeposit();
												expenseItemList.add(expenseItem);
												reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
											}
										}
									}
								}
							}
						}
					}
				}
				// deposit >>
				
				// subworksheet <<
				List<SubWorksheetBean> subWorksheetBeanList = invoiceBean.getWorksheet().getSubWorksheetBeanList();
				if(null != subWorksheetBeanList && subWorksheetBeanList.size() > 0){
					for(SubWorksheetBean subWorksheetBean:subWorksheetBeanList){
						if(subWorksheetBean.getPrice() > 0){
							expenseItem = new ExpenseItemBean();
							expenseItem.setNo(""+(countTbNoVat++));
							expenseItem.setDescription(subWorksheetBean.getWorkSheetTypeText());
							expenseItem.setAmount("1 งาน");
							expenseItem.setPrice(new DecimalFormat("#,##0.00").format(subWorksheetBean.getPrice()));
							expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(subWorksheetBean.getPrice()));
							totalListNoVat = totalListNoVat + subWorksheetBean.getPrice();
							expenseItemList.add(expenseItem);
							reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
						}
					}
				}
				// subworksheet >>
				
				// productItem <<
				if(null != worksheetBean){
					List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
					if(null != productItemBeanList && productItemBeanList.size() > 0){
						for(ProductItemBean productItemBean:productItemBeanList){
							if("E".equals(productItemBean.getType())){
								List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
								if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
									for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
										if(productItemWorksheetBean.getPrice() > 0){
											expenseItem = new ExpenseItemBean();
											expenseItem.setNo(""+(countTbNoVat++));
											String description = productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName();
											if(!"".equals(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo())){
												description += "("+productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo()+")";
											}
											expenseItem.setDescription(description);
											expenseItem.setAmount(productItemWorksheetBean.getQuantity()+" "+productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
											expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()));
											expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getAmount()));
											totalListNoVat = totalListNoVat + productItemWorksheetBean.getAmount();
											expenseItemList.add(expenseItem);
											reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
										}
									}
								}
							}
						}
					}
				}
				// productItem >>
				
				// footer
				if("C_S".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getServiceApplication().getFirstBillFreeDisCount() > 0){
					expenseItem = new ExpenseItemBean();
					expenseItem.setPrice("ส่วนลด");
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getFirstBillFreeDisCount()));
					expenseItemList.add(expenseItem);
					totalListNoVat = totalListNoVat - invoiceBean.getServiceApplication().getFirstBillFreeDisCount();
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				}
				
				if (!"S".equals(invoice.getStatus())) {
					double vat = reportInvoiceOrReceiptBean.getVat();
					expenseItem = new ExpenseItemBean();
					expenseItem.setPrice("ราคารวมทั้งสิ้น /Sub Total");
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalListNoVat));
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
					
					expenseItem = new ExpenseItemBean();
					expenseItem.setPrice("จํานวนภาษีมูลค่าเพิ่ม /VAT");
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(vat));
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
					
					expenseItem = new ExpenseItemBean();
					expenseItem.setPrice("จํานวนเงินรวมทั้งสิ้น /NET");
					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalListNoVat+vat));
					expenseItemList.add(expenseItem);
					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				}
				
			}
			
		}
	}
	
	

	@RequestMapping(value = "invoiceOrReceiptList/exportPdf", method = RequestMethod.GET)
	public ModelAndView workSheetListExportPdf(Model model,HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView modelAndView = new ModelAndView(); 
		HttpSession session = request.getSession();
		String nameReport = "invoiceOrReceipt";
		//check permission
		if(isPermission()){
		JasperRender jasperRender = new JasperRender();
		List<Object> resUse = new ArrayList<Object>();
		
		List<InvoiceDocumentBean> invoiceDocumentBeanList = (List<InvoiceDocumentBean>) session.getAttribute("invoiceDocumentBeanListForReport");
		if(null != invoiceDocumentBeanList && invoiceDocumentBeanList.size() > 0){
			for(InvoiceDocumentBean invoiceDocumentBean:invoiceDocumentBeanList){
				Invoice invoice = financialService.getInvoiceById(invoiceDocumentBean.getId());
				if(null != invoice){	
					ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
					setReportInvoiceOrReceiptBean(invoice,reportInvoiceOrReceiptBean,request);
					resUse.add(reportInvoiceOrReceiptBean);
				}
			}
		}
		
		jasperRender.setBeanList(resUse);
			try {
				byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport(nameReport,request));
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
			
		}else{
			//no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		
		return null;
	}

	private void setReportInvoiceOrReceiptBean(Worksheet worksheet,
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean, HttpServletRequest request) {
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataThMMyyyy = new SimpleDateFormat("MMyyyy", Locale.US);
		Invoice invoice = worksheet.getInvoice();
		if(null != invoice){
			if("S".equals(invoice.getStatus())){
				reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้/ใบรับเงินชั่วคราว");
				Receipt receipt = invoice.getReceipt();
				if(null != receipt){
					reportInvoiceOrReceiptBean.setBarCode(String.format("%1s",receipt.getReceiptCode()));
					reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s",receipt.getReceiptCode()));
					//update print time receipt
					List<ReceiptHistoryPrint> receiptHistoryPrintList = new ArrayList<ReceiptHistoryPrint>();
					ReceiptHistoryPrint receiptHistoryPrint = new ReceiptHistoryPrint();
					receiptHistoryPrint.setCreateDate(new Date());
					receiptHistoryPrint.setDeleted(Boolean.FALSE);
					receiptHistoryPrint.setCreatedBy(getUserNameLogin());
					
					int printTime = 0;
					if(receipt.getReceiptHistoryPrints().size() > 0)
						printTime = receipt.getReceiptHistoryPrints().get(receipt.getReceiptHistoryPrints().size() - 1).getPrintTime();
					
					receiptHistoryPrint.setPrintTime(printTime+1);
					receiptHistoryPrint.setReceipt(receipt);
					receiptHistoryPrint.setPersonnel(getPersonnelLogin());
					receiptHistoryPrintList.add(receiptHistoryPrint);
					receipt.setReceiptHistoryPrints(receiptHistoryPrintList);
					try {
						financialService.updateReceipt(receipt);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else {
//				if("S".equals(worksheet.getStatus())){
//					reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้/ใบรับเงินชั่วคราว");
//				}else{
//					reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้/ใบรับเงินชั่วคราว");
//				}
				reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้/ใบรับเงินชั่วคราว");
				reportInvoiceOrReceiptBean.setBarCode(String.format("%1s",invoice.getInvoiceCode()));
				reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s",invoice.getInvoiceCode()));
				List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
				InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
				invoiceHistoryPrint.setCreateDate(new Date());
				invoiceHistoryPrint.setDeleted(Boolean.FALSE);
				invoiceHistoryPrint.setCreatedBy(getUserNameLogin());
				
				int printTime = 0;
				if(invoice.getInvoiceHistoryPrints().size() > 0)
					printTime = invoice.getInvoiceHistoryPrints().get(invoice.getInvoiceHistoryPrints().size() - 1).getPrintTime();
				
				invoiceHistoryPrint.setPrintTime(printTime + 1);
				invoiceHistoryPrint.setInvoice(invoice);
				invoiceHistoryPrint.setPersonnel(getPersonnelLogin());
				invoiceHistoryPrintList.add(invoiceHistoryPrint);
				invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
				try {
					financialService.updateInvoice(invoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			reportInvoiceOrReceiptBean.setNo("No. "+String.format("%07d",invoice.getId()));
			reportInvoiceOrReceiptBean.setCreatedDate(formatDataTh.format(new Date()));
			if(null != invoice.getCreateDate())
			reportInvoiceOrReceiptBean.setPayWithin(formatDataTh.format(invoice.getCreateDate()));
			ServiceApplication serviceApplication = worksheet.getServiceApplication();
			if(null != serviceApplication){
			reportInvoiceOrReceiptBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					String customerName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
					reportInvoiceOrReceiptBean.setCustomerName(customerName);
					reportInvoiceOrReceiptBean.setCustomerCode(customer.getCustCode());
					Contact contact = customer.getContact();
					if(null != contact){
						reportInvoiceOrReceiptBean.setCustomerTelephone(contact.getMobile());
					}
					List<Address> addressList = serviceApplication.getAddresses();
					if(null != addressList && addressList.size() > 0){
						String nearbyPlaces = " - ";
						for(Address address:addressList){
							if("4".equals(address.getAddressType())){
								AddressBean addressBean = setAddressBean(address);
								reportInvoiceOrReceiptBean.setCustomerAddress(addressBean.getCollectAddressDetail());
								nearbyPlaces = address.getNearbyPlaces();
								break;
							}
						}
						reportInvoiceOrReceiptBean.setCustomerNearbyPlaces(nearbyPlaces);
					}
				}
			}
			String invoiceDescription = " - ";
			InvoiceController invController = new InvoiceController();
			  invController.setMessageSource(messageSource);
			  invController.setUnitService(unitService);

			if(invoice.getCreateDate() != null) {
					InvoiceDocumentBean bean = invController.PoppulateInvoiceEntityToDto(invoice);
					//รอบบิลการใช้งาน
						if(bean.getInvoiceType().equals("O")){
							SimpleDateFormat formateDateServiceReound = new SimpleDateFormat(
									messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
									new Locale("TH", "th"));
							Date date = bean.getCreateDate();
							Date startUseDate = date;
							if(null != date){
								Calendar c = Calendar.getInstance(new Locale("EN", "en"));
								c.setTime(date);
								c.add(Calendar.DATE, -30);
								Date endUseDate = c.getTime();
								
								if(!StringUtils.isEmpty(startUseDate) && !StringUtils.isEmpty(endUseDate)){
									invoiceDescription = String.format("ค่าบริการรายเดือน ( %1s – %2s )",formateDateServiceReound.format(startUseDate),
										formateDateServiceReound.format(endUseDate));
								}
							}
						}else if(bean.getInvoiceType().equals("R")){
							invoiceDescription = bean.getWorksheet().getWorkSheetTypeText();
						}else if(bean.getInvoiceType().equals("S")){
							invoiceDescription = "ติดตั้งอุปกรณ์ใหม่";
						}
			}
			reportInvoiceOrReceiptBean.setList1(invoiceDescription);
			if(null != serviceApplication){
				AddPointWorksheetBean addPointWorksheetBean = loadPointAll(serviceApplication.getId());
				String list2_1 = String.format("จำนวน %1s จุด  ( Digital %2s จุด และ Analog %3s จุด )",
						(addPointWorksheetBean.getAnalogPoint()+addPointWorksheetBean.getDigitalPoint()),
						addPointWorksheetBean.getDigitalPoint(),
						addPointWorksheetBean.getAnalogPoint());
				reportInvoiceOrReceiptBean.setList2_1(list2_1);
			}
			
			List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();
			for(Invoice invo : worksheet.getServiceApplication().getInvoices()){
				  if(invo.getCreateDate() != null) {
					  if(invo.getStatus().equals("O")){
						  invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invo));
					  }
				  }						  
			}
			if(null != invoiceOverBill && invoiceOverBill.size() > 0){
				  float sumAmount = 0f;
				  for(InvoiceDocumentBean bean:invoiceOverBill){
					  sumAmount += bean.getAmount();
				  }
				  reportInvoiceOrReceiptBean.setList2_2("ยอดค้างชำระ "+new DecimalFormat("#,##0.00").format(sumAmount)+" บาท");
			}
			NumberFormat nf = new NumberFormat();
			String thaiBaht = nf.getText(invoice.getAmount());
			reportInvoiceOrReceiptBean.setTotalMoney(String.format("%1s (%2s)",new DecimalFormat("#,##0.00").format(invoice.getAmount()),thaiBaht));
		}
	}
	
	private void setReportInvoiceOrReceiptBean(Invoice invoice,
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean, HttpServletRequest request) {
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataThMMyyyy = new SimpleDateFormat("MMyyyy", Locale.US);
		if(null != invoice){
			if("S".equals(invoice.getStatus())){
				reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้/ใบรับเงินชั่วคราว");
				Receipt receipt = invoice.getReceipt();
				if(null != receipt){
					reportInvoiceOrReceiptBean.setBarCode(String.format("%1s%2s",receipt.getReceiptCode(),formatDataThMMyyyy.format(new Date())));
					reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s%2s",receipt.getReceiptCode(),formatDataThMMyyyy.format(new Date())));
					//update print time receipt
					List<ReceiptHistoryPrint> receiptHistoryPrintList = new ArrayList<ReceiptHistoryPrint>();
					ReceiptHistoryPrint receiptHistoryPrint = new ReceiptHistoryPrint();
					receiptHistoryPrint.setCreateDate(new Date());
					receiptHistoryPrint.setDeleted(Boolean.FALSE);
					receiptHistoryPrint.setCreatedBy(getUserNameLogin());
					
					int printTime = 0;
					if(receipt.getReceiptHistoryPrints().size() > 0)
						printTime = receipt.getReceiptHistoryPrints().get(receipt.getReceiptHistoryPrints().size() - 1).getPrintTime();
					
					receiptHistoryPrint.setPrintTime(printTime+1);
					receiptHistoryPrint.setReceipt(receipt);
					receiptHistoryPrint.setPersonnel(getPersonnelLogin());
					receiptHistoryPrintList.add(receiptHistoryPrint);
					receipt.setReceiptHistoryPrints(receiptHistoryPrintList);
					try {
						financialService.updateReceipt(receipt);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้(บริษัท)/ใบรับเงินชั่วคราว");
				reportInvoiceOrReceiptBean.setBarCode(String.format("%1s%2s",invoice.getInvoiceCode(),formatDataThMMyyyy.format(new Date())));
				reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s",invoice.getInvoiceCode()));
				List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
				InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
				invoiceHistoryPrint.setCreateDate(new Date());
				invoiceHistoryPrint.setDeleted(Boolean.FALSE);
				invoiceHistoryPrint.setCreatedBy(getUserNameLogin());
				
				int printTime = 0;
				if(invoice.getInvoiceHistoryPrints().size() > 0)
					printTime = invoice.getInvoiceHistoryPrints().get(invoice.getInvoiceHistoryPrints().size() - 1).getPrintTime();
				
				invoiceHistoryPrint.setPrintTime(printTime + 1);
				invoiceHistoryPrint.setInvoice(invoice);
				invoiceHistoryPrint.setPersonnel(getPersonnelLogin());
				invoiceHistoryPrintList.add(invoiceHistoryPrint);
				invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
				try {
					financialService.updateInvoice(invoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			reportInvoiceOrReceiptBean.setNo("No. "+String.format("%07d",invoice.getId()));
			reportInvoiceOrReceiptBean.setCreatedDate(formatDataTh.format(new Date()));
			if(null != invoice.getCreateDate())
			reportInvoiceOrReceiptBean.setPayWithin(formatDataTh.format(invoice.getCreateDate()));
			ServiceApplication serviceApplication = invoice.getServiceApplication();
			if(null != serviceApplication){
			reportInvoiceOrReceiptBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					String customerName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
					reportInvoiceOrReceiptBean.setCustomerName(customerName);
					reportInvoiceOrReceiptBean.setCustomerCode(customer.getCustCode());
					Contact contact = customer.getContact();
					if(null != contact){
						reportInvoiceOrReceiptBean.setCustomerTelephone(contact.getMobile());
					}
					List<Address> addressList = serviceApplication.getAddresses();
					if(null != addressList && addressList.size() > 0){
						String nearbyPlaces = " - ";
						for(Address address:addressList){
							if("4".equals(address.getAddressType())){
								AddressBean addressBean = setAddressBean(address);
								reportInvoiceOrReceiptBean.setCustomerAddress(addressBean.getCollectAddressDetail());
								nearbyPlaces = address.getNearbyPlaces();
								break;
							}
						}
						reportInvoiceOrReceiptBean.setCustomerNearbyPlaces(nearbyPlaces);
					}
				}
			}
			String invoiceDescription = " - ";
			InvoiceController invController = new InvoiceController();
			  invController.setMessageSource(messageSource);
			  

			if(invoice.getCreateDate() != null) {
					InvoiceDocumentBean bean = invController.PoppulateInvoiceEntityToDto(invoice);
					//รอบบิลการใช้งาน
						if(bean.getInvoiceType().equals("O")){
							SimpleDateFormat formateDateServiceReound = new SimpleDateFormat(
									messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
									new Locale("TH", "th"));
							Date date = bean.getCreateDate();
							Date startUseDate = date;
							if(null != date){
								Calendar c = Calendar.getInstance(new Locale("EN", "en"));
								c.setTime(date);
								c.add(Calendar.DATE, -30);
								Date endUseDate = c.getTime();
								
								if(!StringUtils.isEmpty(startUseDate) && !StringUtils.isEmpty(endUseDate)){
									invoiceDescription = String.format("ค่าบริการรายเดือน ( %1s – %2s )",formateDateServiceReound.format(startUseDate),
										formateDateServiceReound.format(endUseDate));
								}
							}
						}else if(bean.getInvoiceType().equals("R")){
							invoiceDescription = bean.getWorksheet().getWorkSheetTypeText();
						}else if(bean.getInvoiceType().equals("S")){
							invoiceDescription = "ติดตั้งอุปกรณ์ใหม่";
						}
			}
			reportInvoiceOrReceiptBean.setList1(invoiceDescription);
			if(null != serviceApplication){
				AddPointWorksheetBean addPointWorksheetBean = loadPointAll(serviceApplication.getId());
				String list2_1 = String.format("จำนวน %1s จุด  ( Digital %2s จุด และ Analog %3s จุด )",
						(addPointWorksheetBean.getAnalogPoint()+addPointWorksheetBean.getDigitalPoint()),
						addPointWorksheetBean.getDigitalPoint(),
						addPointWorksheetBean.getAnalogPoint());
				reportInvoiceOrReceiptBean.setList2_1(list2_1);
			}
			
			List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();
			for(Invoice invo : serviceApplication.getInvoices()){
				  if(invo.getCreateDate() != null) {
					  if(invo.getStatus().equals("O")){
						  invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invo));
					  }
				  }						  
			}
			if(null != invoiceOverBill && invoiceOverBill.size() > 0){
				  float sumAmount = 0f;
				  for(InvoiceDocumentBean bean:invoiceOverBill){
					  sumAmount += bean.getAmount();
				  }
				  reportInvoiceOrReceiptBean.setList2_2("ยอดค้างชำระ "+new DecimalFormat("#,##0.00").format(sumAmount)+" บาท");
			}
			NumberFormat nf = new NumberFormat();
			String thaiBaht = nf.getText(invoice.getAmount());
			reportInvoiceOrReceiptBean.setTotalMoney(String.format("%1s (%2s)",new DecimalFormat("#,##0.00").format(invoice.getAmount()),thaiBaht));
		}
	}
	
	public AddressBean setAddressBean(Address address){
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

		addressBean.collectAddressForReport();
		
		return addressBean;
	}
	
	public AddPointWorksheetBean loadPointAll( Long serviceApplicationId) {
		logger.info("[method : loadConnect][Type : Controller]");

		AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
		
			try {
				if (null != serviceApplicationId) {	

					List<ProductItem> productItems = productItemService
							.getProductItemByServiceApplicationId(serviceApplicationId);
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
							serviceApplicationId);

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
							serviceApplicationId);

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

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		return addPointWorksheetBean;
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
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		serviceApplicationBean.setCreateDateTh(
				null == serviceApplication.getCreateDate() ? "" : formatDataTh.format(serviceApplication.getCreateDate()));
		serviceApplicationBean.setRemark(serviceApplication.getRemark());
		serviceApplicationBean.setEasyInstallationDateTime(serviceApplication.getEasyInstallationDateTime());
		
		if(null != serviceApplication.getEasyInstallationDateTime() && !"".equals(serviceApplication.getEasyInstallationDateTime())){
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
}
