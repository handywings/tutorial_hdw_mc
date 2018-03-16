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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.BankBean;
import com.hdw.mccable.dto.BillHistoryPrintBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ReceiptBean;
import com.hdw.mccable.dto.ReceiptSearchBean;
import com.hdw.mccable.entity.Bank;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ReceiptHistoryPrint;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/receipt")
public class ReceiptController extends BaseController{
	final static Logger logger = Logger.getLogger(ReceiptController.class);
	public static final String CONTROLLER_NAME = "receipt/";
	
	// initial service
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
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
				// ------ เซต ค้นหาจากวัน ปัจจุบัน + ไปอีก 7 วัน
				String daterange = "";
				String startDate = "";
				String endDate = "";
				SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", new Locale("TH", "th"));
				Date currentDate = new Date();
				startDate = formatDataTh.format(currentDate);
				
		        // convert date to calendar
		        Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DAY_OF_MONTH, 7);
		        
		        Date currentDatePlus7 = c.getTime();
		        endDate = formatDataTh.format(currentDatePlus7);
		        daterange = String.format("%1s - %2s", startDate,endDate);
		        
		        daterange = ""; // แก้ไขให้เป็น null ก่อน
		        
				// ------
				ReceiptSearchBean receiptSearchBean = new ReceiptSearchBean();
				receiptSearchBean.setDaterange(daterange);
				Pagination pagination = createPagination(1, 10, "receipt", receiptSearchBean);
				modelAndView.addObject("receiptSearchBean",receiptSearchBean);
				modelAndView.addObject("pagination",pagination);
				
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

				// search worksheet bean
				ReceiptSearchBean receiptSearchBean = (ReceiptSearchBean) session.getAttribute("receiptSearchBean");
				//search process and pagination
				Pagination pagination = null;
				
				if(receiptSearchBean != null){
					pagination = createPagination(id, itemPerPage, "receipt",receiptSearchBean);
				}else{
					receiptSearchBean = new ReceiptSearchBean();
					
					pagination = createPagination(id, itemPerPage, "receipt",receiptSearchBean);					
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
				
				modelAndView.addObject("receiptSearchBean",receiptSearchBean);
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
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	// create pagination
	Pagination createPagination(int currentPage, int itemPerPage, String controller,
			ReceiptSearchBean receiptSearchBean) {
		if (itemPerPage == 0) itemPerPage = 10;
		receiptSearchBean.setItemPerPage(""+itemPerPage);
		Pagination pagination = new Pagination();
		pagination.setTotalItem(financialService.getCountTotalOrderReceipt(receiptSearchBean,"P"));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = financialService.getByPageForOrderReceipt(pagination, receiptSearchBean,"P");
		List<ReceiptBean> receiptBeanValidateSize = (List<ReceiptBean>) pagination.getDataList();

		if (receiptBeanValidateSize.size() <= 0) {
			pagination.setCurrentPage(1);
			pagination = financialService.getByPageForOrderReceipt(pagination, receiptSearchBean,"P");
		}
		
//		InvoiceBean invoiceBean
		List<ReceiptBean> receiptBean = new ArrayList<ReceiptBean>();
		for(Receipt receipt : (List<Receipt>)pagination.getDataList()){
			receiptBean.add(poppulateReceiptEntityToDto(receipt));
		}
		pagination.setDataListBean(receiptBean);
		// end populate
		return pagination;
	}
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchReceipt(
			@ModelAttribute("searchReceipt") ReceiptSearchBean receiptSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchReceipt][Type : Controller]");
		logger.info("[method : searchReceipt][receiptSearchBean : " + receiptSearchBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(receiptSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/receipt/page/1/itemPerPage/"+receiptSearchBean.getItemPerPage());
		return modelAndView;
	}
	
	// create search session
	public void generateSearchSession(ReceiptSearchBean receiptSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("receiptSearchBean", receiptSearchBean);
	}
	
	// view receipt
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

				Receipt receipt = financialService.getReceiptById(id);
				ReceiptBean receiptBean = poppulateReceiptEntityToDtoV2(receipt);
				modelAndView.addObject("receiptBean",receiptBean);
				
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
	
	
	// print receipt
	@RequestMapping(value = "print/{id}", method = RequestMethod.GET)
	public ModelAndView print(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : print][Type : Controller]");
		logger.info("[method : print][id : " + id + "]");

		ModelAndView modelAndView = new ModelAndView();
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				Receipt receipt = financialService.getReceiptById(id);
				if(receipt != null){
					//update print time receipt
					List<ReceiptHistoryPrint> receiptHistoryPrintList = new ArrayList<ReceiptHistoryPrint>();
					ReceiptHistoryPrint receiptHistoryPrint = new ReceiptHistoryPrint();
					receiptHistoryPrint.setCreateDate(CURRENT_TIMESTAMP);
					receiptHistoryPrint.setDeleted(Boolean.FALSE);
					receiptHistoryPrint.setCreatedBy(getUserNameLogin());
					receiptHistoryPrint.setPrintTime(receipt.getReceiptHistoryPrints().size() + 1);
					receiptHistoryPrint.setReceipt(receipt);
					receiptHistoryPrint.setPersonnel(getPersonnelLogin());
					receiptHistoryPrintList.add(receiptHistoryPrint);
					receipt.setReceiptHistoryPrints(receiptHistoryPrintList);
					financialService.updateReceipt(receipt);
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

	public ReceiptBean poppulateReceiptEntityToDto(Receipt receipt){
		ReceiptBean receiptBean = new ReceiptBean();
		if(null != receipt){
			receiptBean.setId(receipt.getId());
			receiptBean.setReceiptCode(receipt.getReceiptCode());
			receiptBean.setAmount(receipt.getAmount());
			receiptBean.setReductAmount(receipt.getReductAmount());
			Bank bank = receipt.getBank();
			if(null != bank){
				BankBean bankBean = new BankBean();
				bankBean.setId(bank.getId());
				bankBean.setBankCode(bank.getBankCode());
				bankBean.setBankNameEn(bank.getBankNameEn());
				bankBean.setBankNameTh(bank.getBankNameTh());
				bankBean.setBankShortName(bank.getBankShortName());
				receiptBean.setBankBean(bankBean);
			}
			Invoice invoice = receipt.getInvoice();
			if(null != invoice){
				InvoiceController invoiceController = new InvoiceController();
				invoiceController.setMessageSource(messageSource);
				receiptBean.setInvoiceDocumentBean(invoiceController.PoppulateInvoiceEntityToDto(invoice));
			}
			
			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type6", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			
			receiptBean.setCreateDateTh(
					null == receipt.getCreateDate() ? "" : formatDataTh.format(receipt.getCreateDate()));
			receiptBean.setIssueDocDateTh(
					null == receipt.getIssueDocDate() ? "" : formatDataTh.format(receipt.getIssueDocDate()));
			receiptBean.setPaymentDateTh(
					null == receipt.getPaymentDate() ? "" : formatDataTh.format(receipt.getPaymentDate()));
			
			receiptBean.setPaymentType(receipt.getPaymentType());
			receiptBean.setStatus(receipt.getStatus());
			List<ReceiptHistoryPrint> receiptHistoryPrints = receipt.getReceiptHistoryPrints();
			int lastPrint = 0;
			if(null != receiptHistoryPrints && receiptHistoryPrints.size() > 0){
				for(ReceiptHistoryPrint receiptHisPrint:receiptHistoryPrints){
					ReceiptHistoryPrint hisPrint = new ReceiptHistoryPrint();
					hisPrint.setPrintTime(receiptHisPrint.getPrintTime());
					lastPrint = receiptHisPrint.getPrintTime();
				}
			}
			receiptBean.setLastPrint(lastPrint);
		}
		
		return receiptBean;
	}
	
	public ReceiptBean poppulateReceiptEntityToDtoV2(Receipt receipt){
		ReceiptBean receiptBean = new ReceiptBean();
		if(null != receipt){
			receiptBean.setId(receipt.getId());
			receiptBean.setReceiptCode(receipt.getReceiptCode());
			receiptBean.setAmount(receipt.getAmount());
			receiptBean.setReductAmount(receipt.getReductAmount());
			Bank bank = receipt.getBank();
			if(null != bank){
				BankBean bankBean = new BankBean();
				bankBean.setId(bank.getId());
				bankBean.setBankCode(bank.getBankCode());
				bankBean.setBankNameEn(bank.getBankNameEn());
				bankBean.setBankNameTh(bank.getBankNameTh());
				bankBean.setBankShortName(bank.getBankShortName());
				receiptBean.setBankBean(bankBean);
			}
			Invoice invoice = receipt.getInvoice();
			if(null != invoice){
				InvoiceController invoiceController = new InvoiceController();
				invoiceController.setMessageSource(messageSource);
				receiptBean.setInvoiceDocumentBean(invoiceController.PoppulateInvoiceEntityToDto(invoice));
			}
			
			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			SimpleDateFormat formatDataTimeTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			
			receiptBean.setCreateDateTh(
					null == receipt.getCreateDate() ? "" : formatDataTimeTh.format(receipt.getCreateDate()));
			receiptBean.setIssueDocDateTh(
					null == receipt.getIssueDocDate() ? "" : formatDataTh.format(receipt.getIssueDocDate()));
			receiptBean.setPaymentDateTh(
					null == receipt.getPaymentDate() ? "" : formatDataTimeTh.format(receipt.getPaymentDate()));
			
			receiptBean.setPaymentType(receipt.getPaymentType());
			receiptBean.setStatus(receipt.getStatus());
			
			String createdById = receipt.getCreatedBy();
			if(!"".equals(createdById)){
//				Personnel personnel = personnelService.getPersonnelById(Long.parseLong(createdById));
//				if(null != personnel){
//					String firstName = personnel.getFirstName();
//					String lastName = personnel.getLastName();
//					String code = personnel.getPersonnelCode();
//					String personnelFullName = String.format(" %1s %2s [%3s] ", firstName,lastName,code);
//					receiptBean.setCreateByTh(personnelFullName);
//				}
				receiptBean.setCreateByTh(getUserNameLogin()); // temp
			}
			
			//print invoice history
			List<BillHistoryPrintBean> billHistoryPrintBeanList = new ArrayList<BillHistoryPrintBean>();
			for(ReceiptHistoryPrint receiptHistoryPrint : receipt.getReceiptHistoryPrints()){
				BillHistoryPrintBean billHistoryPrintBean = new BillHistoryPrintBean();
				billHistoryPrintBean.setId(receiptHistoryPrint.getId());
				billHistoryPrintBean.setPrintTime(receiptHistoryPrint.getPrintTime());
				billHistoryPrintBean.setCreateDateTh(
						null == receiptHistoryPrint.getCreateDate() ? "" : formatDataTimeTh.format(receiptHistoryPrint.getCreateDate()));
				
				if(receiptHistoryPrint.getPersonnel() != null){
					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean.setId(receiptHistoryPrint.getPersonnel().getId());
					personnelBean.setFirstName(receiptHistoryPrint.getPersonnel().getFirstName());
					personnelBean.setLastName(receiptHistoryPrint.getPersonnel().getLastName());
					personnelBean.setNickName(receiptHistoryPrint.getPersonnel().getNickName());
					billHistoryPrintBean.setPersonnelBean(personnelBean);
				}
				billHistoryPrintBeanList.add(billHistoryPrintBean);
			}
			receiptBean.setBillHistoryPrintBeanList(billHistoryPrintBeanList);
			
		}
		
		return receiptBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	//getter setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
