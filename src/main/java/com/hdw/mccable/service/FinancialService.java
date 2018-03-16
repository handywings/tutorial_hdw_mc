package com.hdw.mccable.service;

import java.util.Date;
import java.util.List;

import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.OrderBillSearchBean;
import com.hdw.mccable.dto.ReceiptSearchBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.utils.Pagination;

public interface FinancialService {
	public List<Invoice> findAllInvoice();
	public Long saveInvoice(Invoice invoice) throws Exception;
	public Long saveReceipt(Receipt receipt) throws Exception;
	public String genInVoiceCode();
	public String genReceiptCode();
	public void updateInvoice(Invoice invoice) throws Exception;
	public int getCountTotalOrderBill(OrderBillSearchBean orderBillSearchBean);
	public Pagination getByPageForOrderBill(Pagination pagination,OrderBillSearchBean orderBillSearchBean, Boolean isAuto);
	public int getCountTotalOrderInvoice(InvoiceSearchBean invoiceSearchBean, Boolean isAuto);
	public Pagination getByPageForOrderInvoice(Pagination pagination,InvoiceSearchBean invoiceSearchBean, Boolean isAuto);
	public int getCountTotalOrderReceipt(ReceiptSearchBean receiptSearchBean,String status);
	public Pagination getByPageForOrderReceipt(Pagination pagination,ReceiptSearchBean receiptSearchBean,String status);
	public Invoice getInvoiceById(Long id);
	public Receipt getReceiptById(Long id);
	public Date getPaymentOrderDate(Long serviceApplicationId);
	public boolean updateOverDue() throws Exception;
	public void updateReceipt(Receipt receipt) throws Exception;
	public Invoice getInvoiceByInvoiceCodeScan(String invoiceCode,String status, String NotstatusPayment);
	public List<Invoice> findInvoiceSearch(SearchBillScanBean searchBillScanBean);
	public List<InvoiceHistoryPrint> findInvoiceHistoryPrintSearch(SearchBillScanBean searchBillScanBean);
	public List<Invoice> findInvoiceTypeSearch(SearchBillScanBean searchBillScanBean);
	public List<Invoice> getByInvoiceTypeBybaddebtForReport(String reportrange, String invoiceType, String split, String invoiceStatus);
	public List<Invoice> getByInvoiceTypeForReport(String reportrange, String invoiceType, String split, String invoiceStatus);
	public List<Invoice> findInvoiceByServiceApplication(Long ServiceApplicationId, String TypeInvoice);
	public Invoice getInvoiceByCode(String invoiceCode);
	
	public List<Invoice> findInvoiceTypeSearchConfig(SearchBillScanBean searchBillScanBean);
	
	public List<Invoice> findInvoiceByCreateDate();
	
	
	
}
