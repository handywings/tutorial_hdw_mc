package com.hdw.mccable.job;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.hdw.mccable.dto.OrderBillSearchBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;

public class ExecutorCreateBill{
	final static Logger logger = Logger.getLogger(ExecutorCreateBill.class);
	public static final String EMPTY = "";
	public static final Timestamp CURRENT_TIMESTAMP = new Timestamp(System.currentTimeMillis());
	// initial service
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired
	private MessageSource messageSource;
	// End initial service
	
	@SuppressWarnings("unchecked")
	public void generateInvoiceAuto(){
		System.out.println();
		logger.info("===Start generateInvoiceAuto===");
		try{
			Pagination pagination = new Pagination();
			//set bean search
			OrderBillSearchBean orderBillSearchBean = new OrderBillSearchBean();
			orderBillSearchBean.setKey(EMPTY);
			orderBillSearchBean.setZone(0L);
			//calculate date order + 15 day
			orderBillSearchBean.setOrderBillDate(genOrderDate());
			
			pagination = financialService.getByPageForOrderBill(pagination, orderBillSearchBean, Boolean.TRUE);
			if(pagination != null){
				List<ServiceApplication> serviceApplicationList = (List<ServiceApplication>) pagination.getDataList();
				
				// -------------------------------check กรณีรอบบิลมากกว่า 1 เดือน-------------------------------//
				List<ServiceApplication> serviceApplicationTempList = new ArrayList<ServiceApplication>();
				for(ServiceApplication serviceApplication : serviceApplicationList){
					if(serviceApplication.getServicePackage().getPerMounth() == 1){
						serviceApplicationTempList.add(serviceApplication);
						
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
						System.out.println("day more 1 perMonth : "+calDateGenBill.get(Calendar.DAY_OF_MONTH));
						System.out.println("mounth more 1 perMonth : "+calDateGenBill.get(Calendar.MONTH));
						System.out.println("year more 1 perMonth : "+calDateGenBill.get(Calendar.YEAR));
						
						int currentDay = 0;
						int currentMonth = 0;
						int currentYear = 0;
						
						if(orderBillSearchBean.getOrderBillDate()!= null && !orderBillSearchBean.getOrderBillDate().isEmpty()){
							String[] splitDate = orderBillSearchBean.getOrderBillDate().split("-");
							currentDay = Integer.valueOf(splitDate[0]);
							currentMonth = Integer.valueOf(splitDate[1]);
							currentYear = Integer.valueOf(splitDate[2]);
						}  
						
						System.out.println("populate orderBill day : "+currentDay);
						System.out.println("populate orderBill month : "+currentMonth);
						System.out.println("populate orderBill year : "+currentYear);
						
						if((calDateGenBill.get(Calendar.DAY_OF_MONTH) == currentDay)
							&&(calDateGenBill.get(Calendar.MONTH) == currentMonth)
							&&(calDateGenBill.get(Calendar.YEAR) == currentYear)){
							
							//add to list service application
							System.out.println("append true sa id : "+serviceApplication.getId());
							serviceApplicationTempList.add(serviceApplication);
						}
						
					}//end else if
				}//end for
				//pagination.setTotalItem(serviceApplicationTempList.size());
				//-------------------------------End check กรณีรอบบิลมากกว่า 1 เดือน-------------------------------
				
				for(ServiceApplication serviceApplication : serviceApplicationTempList){
					logger.info("service application id : " + serviceApplication.getId());
					//invoice set value
					Invoice invoice = new Invoice();
					invoice.setInvoiceCode(financialService.genInVoiceCode());
					invoice.setAmount(serviceApplication.getMonthlyServiceFee());
					invoice.setServiceApplication(serviceApplication);
					//invoice type
					invoice.setInvoiceType(messageSource.getMessage("financial.invoice.type.order", null, LocaleContextHolder.getLocale()));
					//invoice status
					invoice.setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale()));
					invoice.setDeleted(Boolean.FALSE);
					
					invoice.setCreatedBy("System");
					if(orderBillSearchBean.getOrderBillDate() != null && !orderBillSearchBean.getOrderBillDate().isEmpty()){
						invoice.setCreateDate(new DateUtil().convertStringToDateTimeDb(orderBillSearchBean.getOrderBillDate()));
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
					receipt.setDeleted(Boolean.FALSE);
					receipt.setCreatedBy("System");
					receipt.setCreateDate(CURRENT_TIMESTAMP);
					receipt.setIssueDocDate(CURRENT_TIMESTAMP);
					//match invoice receipt
					receipt.setInvoice(invoice);
					invoice.setReceipt(receipt);
					
					//save invoice and receipt
					financialService.saveInvoice(invoice);
					logger.info("generate auto : success");
					
//					try {
//						financialService.saveInvoice(invoice);
//						logger.info("generate auto : success");
//					} catch (Exception ex) {
//						logger.info("generate auto : fail");
//						throw (ex);
//					} 
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		logger.info("===End generateInvoiceAuto===");
		System.out.println();
	}
		
	public void rollBackAssignInvoice() throws Exception{
		logger.info("===Start rollBackAssignInvoice===");
		SearchBillScanBean searchBillScanBean = new SearchBillScanBean();
		searchBillScanBean.setMobile(true);
		List<Invoice> invoiceList = financialService.findInvoiceTypeSearchConfig(searchBillScanBean);
		if(null != invoiceList && invoiceList.size() > 0){
		logger.info("invoiceList size : "+invoiceList.size());
			for(Invoice invoice:invoiceList){
				invoice.setPersonnel(null);
				invoice.setMobile(false);
				invoice.setUpdatedBy("System");
				invoice.setUpdatedDate(CURRENT_TIMESTAMP);
				financialService.updateInvoice(invoice);
			}
		}else{
			logger.info("invoiceList size : 0");
		}
		logger.info("===End rollBackAssignInvoice===");
	}
	
	public String genOrderDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDate = new Date();
		Date orderDate = DateUtil.addDay(currentDate, 15);
		String orderDateString = sdf.format(orderDate);
		logger.info("Order Date : " + orderDateString);
		return orderDateString;
	}
	
	//getter and setter
	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
