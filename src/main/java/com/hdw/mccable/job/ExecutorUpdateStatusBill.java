package com.hdw.mccable.job;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.service.FinancialService;

public class ExecutorUpdateStatusBill {
	final static Logger logger = Logger.getLogger(ExecutorUpdateStatusBill.class);
	// initial service
		@Autowired(required = true)
		@Qualifier(value = "financialService")
		private FinancialService financialService;
	
	public void updateStatusBillInvoice(){
		logger.info("Init update status Overdue");
		try {
			//update รายเดือน
			financialService.updateOverDue();
			
			//update ติดตั้ง และ ซ่อม
			Calendar now = Calendar.getInstance(new Locale("EN","en"));
			int currentDay = now.get(Calendar.DAY_OF_MONTH);
			int currentMonth = now.get(Calendar.MONTH) + 1;
			int currentYear = now.get(Calendar.YEAR);
			logger.info("[current : ][day : " + currentDay + "]");
			logger.info("[current : ][month : " + currentMonth + "]");
			logger.info("[current : ][year : " + currentYear + "]");
			
			List<Invoice> invoiceList = financialService.findAllInvoice();
			for(Invoice invoice : invoiceList){
				if(invoice.getCreateDate() != null && !invoice.getStatus().equals("S")){
					
					boolean flagCompareDay = false;
					if(invoice.getInvoiceType().equals("O")){
						flagCompareDay = true;
						
					}else if(!invoice.getInvoiceType().equals("O") 
							&& !invoice.getWorkSheet().getStatus().equals("W") && !invoice.getWorkSheet().getStatus().equals("H")){
						flagCompareDay = true;
					}
					
					if(flagCompareDay){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(invoice.getCreateDate());
						String[] dateSplit = date.split("-");
						int DayDb = Integer.valueOf(dateSplit[2]);
						int MonthDb = Integer.valueOf(dateSplit[1]);
						int YearDb = Integer.valueOf(dateSplit[0]);
						
						logger.info("[db : ][day : " + DayDb + "]");
						logger.info("[db : ][month : " + MonthDb + "]");
						logger.info("[db : ][year : " + YearDb + "]");
						
						if((DayDb < currentDay && MonthDb==currentMonth && YearDb==currentYear)
							|| (MonthDb < currentMonth && YearDb==currentYear)
							|| (YearDb < currentYear)){
							
								if(invoice.getAmount() > 0){
									invoice.setStatus("O");
									
								}else if(invoice.getAmount() == 0){
									invoice.setStatus("S");
								}
							
								financialService.updateInvoice(invoice);
							} //end check over date
					}//end check flag compare day
					
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		logger.info("End update status Overdue");
	}
}
