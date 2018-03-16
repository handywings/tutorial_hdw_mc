package com.hdw.mccable.controller.service;

import java.io.ByteArrayInputStream;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.Manager.PdfMergeUtils;
import com.hdw.mccable.controller.InvoiceController;
import com.hdw.mccable.dto.AddPointWorksheetBean;
import com.hdw.mccable.dto.AdditionMonthlyFee;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.ExpenseItemBean;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReportInvoiceOrReceiptBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.SubWorksheetBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ReceiptHistoryPrint;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.security.AuthenticationTokenFilter;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.security.TokenUtil;
import com.hdw.mccable.service.AuthenService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.utils.FilePathUtil;
import com.hdw.mccable.utils.NumberFormat;
import com.hdw.mccable.utils.Pagination;
import com.hdw.mccable.utils.UploadFileUtil;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/invoice", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceService {

	private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/getInvoiceByPersonnel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, List<InvoiceDocumentBean>>> getInvoiceByPersonnel(HttpServletRequest request) throws Exception {
		 HashMap<String, List<InvoiceDocumentBean>> hmap = new HashMap<String, List<InvoiceDocumentBean>>();
		List<InvoiceDocumentBean> invoiceBeans = new ArrayList<InvoiceDocumentBean>();
		String personnelId = AuthenticationTokenFilter.validateToken(request);
		if(StringUtils.isEmpty(personnelId)){
			return new ResponseEntity<HashMap<String, List<InvoiceDocumentBean>>>(hmap, HttpStatus.OK);
		}
		
		Personnel personnel = personnelService.getPersonnelById(Long.valueOf(personnelId));
		
		log.info("personnelId : {}",personnelId);
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
		invoiceSearchBean.setPersonnelId(Long.valueOf(personnelId));
		invoiceSearchBean.setStatus("");
		List<InvoiceDocumentBean> invoiceDocumentBean = createPagination(1, 10, "All", "invoice", invoiceSearchBean, personnel);
		List<InvoiceDocumentBean> invoiceDocumentBeanW = new ArrayList<InvoiceDocumentBean>();
		List<InvoiceDocumentBean> invoiceDocumentBeanO = new ArrayList<InvoiceDocumentBean>();
		List<InvoiceDocumentBean> invoiceDocumentBeanS = new ArrayList<InvoiceDocumentBean>();
		List<InvoiceDocumentBean> invoiceDocumentBeanC = new ArrayList<InvoiceDocumentBean>();
		if(null != invoiceDocumentBean && invoiceDocumentBean.size() > 0){
			for(InvoiceDocumentBean bean:invoiceDocumentBean){
				if("W".equals(bean.getStatus())){
					invoiceDocumentBeanW.add(bean);
				}else if("O".equals(bean.getStatus())){
					invoiceDocumentBeanO.add(bean);
				}else if("S".equals(bean.getStatus())){
					invoiceDocumentBeanS.add(bean);
				}else if("C".equals(bean.getStatus())){
					invoiceDocumentBeanC.add(bean);
				}
			}
		}
		
//		invoiceSearchBean.setStatus("W");
//		invoiceDocumentBean = createPagination(1, 10, "All", "invoice", invoiceSearchBean, personnel);
		hmap.put("W", invoiceDocumentBeanW);
		
//		invoiceSearchBean.setStatus("O");
//		invoiceDocumentBean = createPagination(1, 10, "All", "invoice", invoiceSearchBean, personnel);
		
		hmap.put("O", invoiceDocumentBeanO);
		
//		invoiceSearchBean.setStatus("S");
//		invoiceDocumentBean = createPagination(1, 10, "All", "invoice", invoiceSearchBean, personnel);
		hmap.put("S", invoiceDocumentBeanS);
		
//		invoiceSearchBean.setStatus("C");
//		invoiceDocumentBean = createPagination(1, 10, "All", "invoice", invoiceSearchBean, personnel);
		hmap.put("C", invoiceDocumentBeanC);
		
		return new ResponseEntity<HashMap<String, List<InvoiceDocumentBean>>>(hmap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CompanyBean>> getCompany(HttpServletRequest request) throws Exception {
		List<Company> companys = companyService.findAll();
		List<CompanyBean> companyList = new ArrayList<CompanyBean>();
		if(companys != null){
			for(Company company : companys){
				CompanyBean companySubBean = new CompanyBean();
				companySubBean = populateEntityToDto(company);
				companyList.add(companySubBean);
			}
		}
		
		return new ResponseEntity<List<CompanyBean>>(companyList, HttpStatus.OK);
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
	
	// create pagination
	@SuppressWarnings("unchecked")
	List<InvoiceDocumentBean> createPagination(int currentPage, int itemPerPage, String tab, String controller, 
			InvoiceSearchBean invoiceSearchBean, Personnel personnel) {
		if (itemPerPage == 0) itemPerPage = 10;
		if("".equals(tab)) tab = "All";
		invoiceSearchBean.setItemPerPage(""+itemPerPage);
		invoiceSearchBean.setTab(tab);
		
		if(null != personnel){
			if(personnel.getId()!=1 && personnel.isCashier()){
				invoiceSearchBean.setPersonnelId(personnel.getId());
			}else{
				invoiceSearchBean.setPersonnelId(null);
			}
		}else{
			invoiceSearchBean.setPersonnelId(null);
		}
		
		Pagination pagination = new Pagination();
		pagination.setTotalItem(financialService.getCountTotalOrderInvoice(invoiceSearchBean,Boolean.FALSE));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = financialService.getByPageForOrderInvoice(pagination, invoiceSearchBean,Boolean.FALSE);
		List<InvoiceDocumentBean> invoiceBeansValidateSize = (List<InvoiceDocumentBean>) pagination.getDataList();

		if (invoiceBeansValidateSize.size() <= 0) {
			pagination.setCurrentPage(1);
			pagination = financialService.getByPageForOrderInvoice(pagination, invoiceSearchBean,Boolean.FALSE);
		}
		
//		InvoiceBean invoiceBean
		List<InvoiceDocumentBean> invoiceBeans = new ArrayList<InvoiceDocumentBean>();
		for(Invoice invoice : (List<Invoice>)pagination.getDataList()){
			InvoiceDocumentBean invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
//			if(null != invoiceDocumentBean){
//				ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
//				setReportInvoiceOrReceiptBean(invoice,reportInvoiceOrReceiptBean,personnel);
//				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
//				invoiceDocumentBean.setReportInvoiceOrReceiptBean(reportInvoiceOrReceiptBean);
				invoiceBeans.add(invoiceDocumentBean);
//			}
		}
		pagination.setDataListBean(invoiceBeans);
		// end populate
		return invoiceBeans;
	}
	
	@RequestMapping(value = "/getDataForReport/invoiceId/{invoiceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InvoiceDocumentBean> getDataForReport(@PathVariable Long invoiceId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("[method : printInvoice][Type : Controller][invoiceId : {}]",invoiceId);
		HashMap<String, InvoiceDocumentBean> hmap = new HashMap<String, InvoiceDocumentBean>();
		String result = "";
		String personnelId = AuthenticationTokenFilter.validateToken(request);
		Personnel personnel = personnelService.getPersonnelById(Long.valueOf(personnelId));
		String createdBy = personnel.getPrefix()+personnel.getFirstName()+" "+personnel.getLastName();
		Invoice invoice = financialService.getInvoiceById(invoiceId);
		InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
		if(null != invoice){
			invoiceDocumentBean = PoppulateInvoiceEntityToDtoForList(invoice);
			if(null != invoiceDocumentBean){
				ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
				setReportInvoiceOrReceiptBean(invoice,reportInvoiceOrReceiptBean,personnel);
				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
				invoiceDocumentBean.setReportInvoiceOrReceiptBean(reportInvoiceOrReceiptBean);
			}
		}
		return new ResponseEntity<InvoiceDocumentBean>(invoiceDocumentBean, HttpStatus.OK);
	}
	
	private void setReportInvoiceOrReceiptBean(Invoice invoice,
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean, Personnel personnel) {
		String createdBy = personnel.getPrefix()+personnel.getFirstName()+" "+personnel.getLastName();
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataThMMyyyy = new SimpleDateFormat("MMyyyy", Locale.US);
		if(null != invoice){
//			if("S".equals(invoice.getStatus())){
//				reportInvoiceOrReceiptBean.setTitle("ใบเสร็จรับเงิน/ ใบแจ้งหนี้/ ใบกำกับภาษี\n"
//						+ "RECEIPT/ INVOICE/ TEX INVOICE");
//				Receipt receipt = invoice.getReceipt();
//				if(null != receipt){
//					//update print time receipt
//					List<ReceiptHistoryPrint> receiptHistoryPrintList = new ArrayList<ReceiptHistoryPrint>();
//					ReceiptHistoryPrint receiptHistoryPrint = new ReceiptHistoryPrint();
//					receiptHistoryPrint.setCreateDate(new Date());
//					receiptHistoryPrint.setDeleted(Boolean.FALSE);
//					receiptHistoryPrint.setCreatedBy(createdBy);
//					
//					int printTime = 0;
//					if(receipt.getReceiptHistoryPrints().size() > 0)
//						printTime = receipt.getReceiptHistoryPrints().get(receipt.getReceiptHistoryPrints().size() - 1).getPrintTime();
//					
//					receiptHistoryPrint.setPrintTime(printTime+1);
//					receiptHistoryPrint.setReceipt(receipt);
//					receiptHistoryPrint.setPersonnel(personnel);
//					receiptHistoryPrintList.add(receiptHistoryPrint);
//					receipt.setReceiptHistoryPrints(receiptHistoryPrintList);
//					try {
//						financialService.updateReceipt(receipt);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}else{
//				reportInvoiceOrReceiptBean.setTitle("ใบแจ้งหนี้(บริษัท)/ใบรับเงินชั่วคราว");
//				List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
//				InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
//				invoiceHistoryPrint.setCreateDate(new Date());
//				invoiceHistoryPrint.setDeleted(Boolean.FALSE);
//				invoiceHistoryPrint.setCreatedBy(createdBy);
//				
//				int printTime = 0;
//				if(invoice.getInvoiceHistoryPrints().size() > 0)
//					printTime = invoice.getInvoiceHistoryPrints().get(invoice.getInvoiceHistoryPrints().size() - 1).getPrintTime();
//				
//				invoiceHistoryPrint.setPrintTime(printTime + 1);
//				invoiceHistoryPrint.setInvoice(invoice);
//				invoiceHistoryPrint.setPersonnel(personnel);
//				invoiceHistoryPrintList.add(invoiceHistoryPrint);
//				invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
//				try {
//					financialService.updateInvoice(invoice);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			reportInvoiceOrReceiptBean.setNo("No. "+String.format("%07d",invoice.getId()));
			reportInvoiceOrReceiptBean.setBarCode(String.format("%1s%2s",invoice.getInvoiceCode(),formatDataThMMyyyy.format(new Date())));
			reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s%2s",invoice.getInvoiceCode(),formatDataThMMyyyy.format(new Date())));
			reportInvoiceOrReceiptBean.setCreatedDate(formatDataTh.format(new Date()));
			if(null != invoice.getCreateDate())
			reportInvoiceOrReceiptBean.setPayWithin(formatDataTh.format(invoice.getCreateDate()));
			ServiceApplication serviceApplication = invoice.getServiceApplication();
			if(null != serviceApplication){
			reportInvoiceOrReceiptBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					String customerName = String.format("%1s คุณ%2s %3s",customer.getCustCode(),customer.getFirstName(),customer.getLastName());
					reportInvoiceOrReceiptBean.setCustomerName(customerName);
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
			
		}
		invoiceBean.setServiceApplication(serviceApplicationBean);
		
		return invoiceBean;
	}
	
	@RequestMapping(value = "/printInvoice/invoiceId/{invoiceId}/companyId/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> printInvoice(@PathVariable Long invoiceId,
			@PathVariable Long companyId,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		log.info("[method : printInvoice][Type : Controller][invoiceId : {}][companyId : {}]",invoiceId,companyId);
		String result = "";
		String personnelId = AuthenticationTokenFilter.validateToken(request);
		String nameReport = "invoiceOrReceiptA4"; // ใบแจ้งหนี้/ใบรับเงินชั่วคราว
		
		JasperRender jasperRender = new JasperRender();
		List<Object> resUse = new ArrayList<Object>();
		Worksheet worksheet = new Worksheet();
		Invoice invoice = financialService.getInvoiceById(invoiceId);
		if(null != invoice){
			worksheet = invoice.getWorkSheet();
			if(null != worksheet && "S".equals(worksheet.getStatus())){
				nameReport = "invoiceOrReceipt"; // ใบแจ้งหนี้(บริษัท)/ใบรับเงินชั่วคราว
				if(null != invoice){
					if("S".equals(invoice.getStatus())){
						nameReport = "receipt"; // ใบเสร็จรับเงิน
					}
				}
			}
			
			nameReport = "invoice_3inches"; // ใบเสร็จรับเงินขนาด 3 นิ้ว
			if(null != invoice){
				if("S".equals(invoice.getStatus())){
					nameReport = "receipt_3inches"; // ใบเสร็จรับเงินขนาด 3 นิ้ว
				}
			}
			
			Personnel personnel = personnelService.getPersonnelById(Long.valueOf(personnelId));
			
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean = new ReportInvoiceOrReceiptBean();
			if(null != invoice){
				setReportInvoiceOrReceiptBean(invoice,reportInvoiceOrReceiptBean,request, personnel);
				setExpenseItem(invoice,reportInvoiceOrReceiptBean);
			}
			resUse.add(reportInvoiceOrReceiptBean);
			jasperRender.setBeanList(resUse);
			
			Map<String, Object> params = new HashMap<String, Object>();
			String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
			Company company = companyService.getCompanyById(companyId);
			params.put("pathLogo", FilePathUtil.pathAvatarOnWeb(company.getLogo()));
			params.put("companyName", company.getCompanyName());
			log.info("pathLogo : ",FilePathUtil.pathAvatarOnWeb(company.getLogo()));
			jasperRender.setParams(params);
		}
			try {
				byte[] bytesFinal = null;
				if(null != worksheet && !"S".equals(worksheet.getStatus())){
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
//				result = new String(bytesFinal,"TIS-620");
				
				result = UploadFileUtil.uploadFileToServerPDF(
						FilePathUtil.pathAvatarOnLocalPDF("pdf"), bytesFinal);
				log.info("result1 : ",result);
				result = FilePathUtil.pathAvatarOnWebPDF(result);
				log.info("result2 : ",result);
//				response.reset();
//				response.resetBuffer();
//				response.setContentType("application/pdf");
//				response.setContentLength(bytesFinal.length);
//				ServletOutputStream ouputStream = response.getOutputStream();
//				ouputStream.write(bytesFinal, 0, bytesFinal.length);
//				ouputStream.flush();
//				ouputStream.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
			return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateStatusInvoice/invoiceId/{invoiceId}/status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStatusInvoice(@PathVariable Long invoiceId,
			@PathVariable String status,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("[method : printInvoice][Type : Controller][invoiceId : {}][status : {}]",invoiceId,status);
		String result = "";
		String personnelId = AuthenticationTokenFilter.validateToken(request);
		Personnel personnel = personnelService.getPersonnelById(Long.valueOf(personnelId));
		String createdBy = personnel.getPrefix()+personnel.getFirstName()+" "+personnel.getLastName();
		Invoice invoice = financialService.getInvoiceById(invoiceId);
		if(null != invoice){
			if("B".equals(status)){
				invoice.setBilling(true);
				invoice.setUpdatedBy(createdBy);
				invoice.setUpdatedDate(new Date());
				financialService.updateInvoice(invoice);
			}else if("S".equals(status)){
				invoice.setStatus(status);
				invoice.setUpdatedBy(createdBy);
				invoice.setUpdatedDate(new Date());
				financialService.updateInvoice(invoice);
				
				List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
				InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
				invoiceHistoryPrint.setCreateDate(new Date());
				invoiceHistoryPrint.setDeleted(Boolean.FALSE);
				invoiceHistoryPrint.setCreatedBy(createdBy);
				
				int printTime = 0;
				if(invoice.getInvoiceHistoryPrints().size() > 0)
					printTime = invoice.getInvoiceHistoryPrints().get(invoice.getInvoiceHistoryPrints().size() - 1).getPrintTime();
				
				invoiceHistoryPrint.setPrintTime(printTime + 1);
				invoiceHistoryPrint.setInvoice(invoice);
				invoiceHistoryPrint.setPersonnel(personnel);
				invoiceHistoryPrintList.add(invoiceHistoryPrint);
				invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
				try {
					financialService.updateInvoice(invoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				invoice.setStatus(status);
				invoice.setUpdatedBy(createdBy);
				invoice.setUpdatedDate(new Date());
				financialService.updateInvoice(invoice);
			}
			
			result = "success";
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	private void setExpenseItem(Invoice invoice, ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean) {
		if(null != invoice && null != reportInvoiceOrReceiptBean){
				InvoiceController invoiceController = new InvoiceController();
				invoiceController.setMessageSource(messageSource);
				invoiceController.setServiceApplicationService(serviceApplicationService);
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
				if (null != startService) {
					roundService = "(" + endService + " - " + startService + ")";
				}
				
				int diffYear = endUseDate.getYear() - startUseDate.getYear();
				int diffMonth = diffYear * 12 + endUseDate.getMonth() - startUseDate.getMonth();
				
				if(diffMonth < 0){
					diffMonth = diffMonth * -1;
				}
				
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
				expenseItem.setDescription("ค่าบริการรอบบิล "+roundService);
				expenseItem.setAmount(diffMonth + " เดือน");
				expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat));
				expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getAmount()+calAmountAdditionNoVat+calSpencialDiscountNoVat));
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
//				ExpenseItemBean expenseItem = new ExpenseItemBean();
//				int countTbNoVat = 1;
//				float totalListNoVat = 0f;
//				if("C_S".equals(invoiceBean.getWorksheet().getWorkSheetType())){
//					if(!invoiceBean.getServiceApplication().getServicepackage().isMonthlyService()){
//						if(invoiceBean.getServiceApplication().getOneServiceFee() > 0){
//							expenseItem = new ExpenseItemBean();
//							expenseItem.setNo(""+(countTbNoVat++));
//							expenseItem.setDescription("ค่าบริการรายครั้ง");
//							expenseItem.setAmount("1 ครั้ง");
//							expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getOneServiceFee()));
//							expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getOneServiceFee()));
//							totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getOneServiceFee();
//							expenseItemList.add(expenseItem);
//							reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//						}
//					}else{// รายเดือน
//						if(invoiceBean.getServiceApplication().getMonthlyServiceFee() > 0){
//							expenseItem = new ExpenseItemBean();
//							expenseItem.setNo(""+(countTbNoVat++));
//							expenseItem.setDescription("ค่าบริการรอบบิล "+roundService);
//							expenseItem.setAmount(invoiceBean.getServiceApplication().getServicepackage().getPerMounth()+" เดือน");
//							expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getMonthlyServiceFee()));
//							expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getMonthlyServiceFee()));
//							totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getMonthlyServiceFee();
//							expenseItemList.add(expenseItem);
//							reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//						}
//					}
//					
//					if(invoiceBean.getServiceApplication().getInstallationFee() > 0){
//						expenseItem = new ExpenseItemBean();
//						expenseItem.setNo(""+(countTbNoVat++));
//						expenseItem.setDescription("ค่าติดตั้ง");
//						expenseItem.setAmount("1 หน่วย");
//						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getInstallationFee()));
//						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getInstallationFee()));
//						totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getInstallationFee();
//						expenseItemList.add(expenseItem);
//						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//					}
//					
//					if(invoiceBean.getServiceApplication().getDeposit() > 0){
//						expenseItem = new ExpenseItemBean();
//						expenseItem.setNo(""+(countTbNoVat++));
//						expenseItem.setDescription("ค่ามัดจำอุปกรณ์");
//						expenseItem.setAmount("1 หน่วย");
//						expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getDeposit()));
//						expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getDeposit()));
//						totalListNoVat = totalListNoVat + invoiceBean.getServiceApplication().getDeposit();
//						expenseItemList.add(expenseItem);
//						reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//					}
//				}
//				
//				// add point <<
//				if("C_AP".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice() > 0){
//					expenseItem = new ExpenseItemBean();
//					expenseItem.setNo(""+(countTbNoVat++));
//					expenseItem.setDescription("ค่าเพิ่มจุด");
//					expenseItem.setAmount("1 หน่วย");
//					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice()));
//					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice()));
//					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getAddPointWorksheetBean().getAddPointPrice();
//					expenseItemList.add(expenseItem);
//					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//				}
//				// add point >>
//				
//				// move point <<
//				if("C_MP".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice() > 0){
//					expenseItem = new ExpenseItemBean();
//					expenseItem.setNo(""+(countTbNoVat++));
//					expenseItem.setDescription("ค่าย้ายจุด");
//					expenseItem.setAmount("1 หน่วย");
//					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice()));
//					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice()));
//					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getMovePointWorksheetBean().getMovePointPrice();
//					expenseItemList.add(expenseItem);
//					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//				}
//				// move point >>
//				
//				// move cable <<
//				if("C_M".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice() > 0){
//					expenseItem = new ExpenseItemBean();
//					expenseItem.setNo(""+(countTbNoVat++));
//					expenseItem.setDescription("ค่าย้ายสาย");
//					expenseItem.setAmount("1 หน่วย");
//					expenseItem.setPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice()));
//					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice()));
//					totalListNoVat = totalListNoVat + invoiceBean.getWorksheet().getMoveWorksheetBean().getMoveCablePrice();
//					expenseItemList.add(expenseItem);
//					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//				}
//				// move cable >>
//				
//				// productItem  S<<
//				WorksheetBean worksheetBean = invoiceBean.getWorksheet();
//				if(null != worksheetBean){
//					List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
//					if(null != productItemBeanList && productItemBeanList.size() > 0){
//						for(ProductItemBean productItemBean:productItemBeanList){
//							if("S".equals(productItemBean.getType()) && productItemBean.getPrice() > 0){
//								expenseItem = new ExpenseItemBean();
//								expenseItem.setNo(""+(countTbNoVat++));
//								expenseItem.setDescription(productItemBean.getServiceProductBean().getProductName());
//								expenseItem.setAmount(productItemBean.getQuantity()+" "+productItemBean.getServiceProductBean().getUnit().getUnitName());
//								expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemBean.getPrice()));
//								expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemBean.getAmount()));
//								totalListNoVat = totalListNoVat + productItemBean.getAmount();
//								expenseItemList.add(expenseItem);
//								reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//							}
//						}
//					}
//				}
//				// productItem S >>
//				
//				// deposit <<
//				if(!"C_S".equals(invoiceBean.getWorksheet().getWorkSheetType())){
//					if(null != worksheetBean){
//						List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
//						if(null != productItemBeanList && productItemBeanList.size() > 0){
//							for(ProductItemBean productItemBean:productItemBeanList){
//								if("E".equals(productItemBean.getType()) && productItemBean.getPrice() > 0){
//									List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
//									if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
//										for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
//											if(productItemWorksheetBean.isLend() && productItemWorksheetBean.getDeposit() > 0){
//												expenseItem = new ExpenseItemBean();
//												expenseItem.setNo(""+(countTbNoVat++));
//												String description = productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName();
//												if(!"".equals(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo())){
//													description += "("+productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo()+")";
//												}
//												expenseItem.setDescription(description);
//												expenseItem.setAmount(productItemWorksheetBean.getQuantity()+" "+productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
//												expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getDeposit()));
//												expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getDeposit()));
//												totalListNoVat = totalListNoVat + productItemWorksheetBean.getDeposit();
//												expenseItemList.add(expenseItem);
//												reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				// deposit >>
//				
//				// subworksheet <<
//				List<SubWorksheetBean> subWorksheetBeanList = invoiceBean.getWorksheet().getSubWorksheetBeanList();
//				if(null != subWorksheetBeanList && subWorksheetBeanList.size() > 0){
//					for(SubWorksheetBean subWorksheetBean:subWorksheetBeanList){
//						if(subWorksheetBean.getPrice() > 0){
//							expenseItem = new ExpenseItemBean();
//							expenseItem.setNo(""+(countTbNoVat++));
//							expenseItem.setDescription(subWorksheetBean.getWorkSheetTypeText());
//							expenseItem.setAmount("1 งาน");
//							expenseItem.setPrice(new DecimalFormat("#,##0.00").format(subWorksheetBean.getPrice()));
//							expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(subWorksheetBean.getPrice()));
//							totalListNoVat = totalListNoVat + subWorksheetBean.getPrice();
//							expenseItemList.add(expenseItem);
//							reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//						}
//					}
//				}
//				// subworksheet >>
//				
//				// productItem <<
//				if(null != worksheetBean){
//					List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
//					if(null != productItemBeanList && productItemBeanList.size() > 0){
//						for(ProductItemBean productItemBean:productItemBeanList){
//							if("E".equals(productItemBean.getType())){
//								List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
//								if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
//									for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
//										if(productItemWorksheetBean.getPrice() > 0){
//											expenseItem = new ExpenseItemBean();
//											expenseItem.setNo(""+(countTbNoVat++));
//											String description = productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName();
//											if(!"".equals(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo())){
//												description += "("+productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo()+")";
//											}
//											expenseItem.setDescription(description);
//											expenseItem.setAmount(productItemWorksheetBean.getQuantity()+" "+productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
//											expenseItem.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()));
//											expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getAmount()));
//											totalListNoVat = totalListNoVat + productItemWorksheetBean.getAmount();
//											expenseItemList.add(expenseItem);
//											reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				// productItem >>
//				
//				// footer
//				if("C_S".equals(invoiceBean.getWorksheet().getWorkSheetType()) && invoiceBean.getServiceApplication().getFirstBillFreeDisCount() > 0){
//					expenseItem = new ExpenseItemBean();
//					expenseItem.setPrice("ส่วนลด");
//					expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(invoiceBean.getServiceApplication().getFirstBillFreeDisCount()));
//					expenseItemList.add(expenseItem);
//					totalListNoVat = totalListNoVat - invoiceBean.getServiceApplication().getFirstBillFreeDisCount();
//					reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
//				}
//				expenseItem = new ExpenseItemBean();
//				expenseItem.setPrice("ยอดรวมสุทธิ");
//				expenseItem.setTotalPrice(new DecimalFormat("#,##0.00").format(totalListNoVat));
//				expenseItemList.add(expenseItem);
//				reportInvoiceOrReceiptBean.setExpenseItemList(expenseItemList);
				
			}
			
		}
	}
	
	private void setReportInvoiceOrReceiptBean(Invoice invoice,
			ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean, HttpServletRequest request, Personnel personnel) {
		String createdBy = personnel.getPrefix()+personnel.getFirstName()+" "+personnel.getLastName();
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataThMMyyyy = new SimpleDateFormat("MMyyyy", Locale.US);
		if(null != invoice){
			if("S".equals(invoice.getStatus())){
				reportInvoiceOrReceiptBean.setTitle("ใบเสร็จรับเงิน/ ใบแจ้งหนี้/ ใบกำกับภาษี\n"
						+ "RECEIPT/ INVOICE/ TEX INVOICE");
				Receipt receipt = invoice.getReceipt();
				if(null != receipt){
					//update print time receipt
					List<ReceiptHistoryPrint> receiptHistoryPrintList = new ArrayList<ReceiptHistoryPrint>();
					ReceiptHistoryPrint receiptHistoryPrint = new ReceiptHistoryPrint();
					receiptHistoryPrint.setCreateDate(new Date());
					receiptHistoryPrint.setDeleted(Boolean.FALSE);
					receiptHistoryPrint.setCreatedBy(createdBy);
					
					int printTime = 0;
					if(receipt.getReceiptHistoryPrints().size() > 0)
						printTime = receipt.getReceiptHistoryPrints().get(receipt.getReceiptHistoryPrints().size() - 1).getPrintTime();
					
					receiptHistoryPrint.setPrintTime(printTime+1);
					receiptHistoryPrint.setReceipt(receipt);
					receiptHistoryPrint.setPersonnel(personnel);
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
				List<InvoiceHistoryPrint> invoiceHistoryPrintList = new ArrayList<InvoiceHistoryPrint>();
				InvoiceHistoryPrint invoiceHistoryPrint = new InvoiceHistoryPrint();
				invoiceHistoryPrint.setCreateDate(new Date());
				invoiceHistoryPrint.setDeleted(Boolean.FALSE);
				invoiceHistoryPrint.setCreatedBy(createdBy);
				
				int printTime = 0;
				if(invoice.getInvoiceHistoryPrints().size() > 0)
					printTime = invoice.getInvoiceHistoryPrints().get(invoice.getInvoiceHistoryPrints().size() - 1).getPrintTime();
				
				invoiceHistoryPrint.setPrintTime(printTime + 1);
				invoiceHistoryPrint.setInvoice(invoice);
				invoiceHistoryPrint.setPersonnel(personnel);
				invoiceHistoryPrintList.add(invoiceHistoryPrint);
				invoice.setInvoiceHistoryPrints(invoiceHistoryPrintList);
				try {
					financialService.updateInvoice(invoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			reportInvoiceOrReceiptBean.setNo("No. "+String.format("%07d",invoice.getId()));
			reportInvoiceOrReceiptBean.setBarCode(String.format("%1s%2s",invoice.getInvoiceCode(),formatDataThMMyyyy.format(new Date())));
			reportInvoiceOrReceiptBean.setInvoiceCode(String.format("%1s%2s",invoice.getInvoiceCode(),formatDataThMMyyyy.format(new Date())));
			reportInvoiceOrReceiptBean.setCreatedDate(formatDataTh.format(new Date()));
			if(null != invoice.getCreateDate())
			reportInvoiceOrReceiptBean.setPayWithin(formatDataTh.format(invoice.getCreateDate()));
			ServiceApplication serviceApplication = invoice.getServiceApplication();
			if(null != serviceApplication){
			reportInvoiceOrReceiptBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
			Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					String customerName = String.format("%1s คุณ%2s %3s",customer.getCustCode(),customer.getFirstName(),customer.getLastName());
					reportInvoiceOrReceiptBean.setCustomerName(customerName);
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
	
}
