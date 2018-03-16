package com.hdw.mccable.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.FinancialDAO;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.OrderBillSearchBean;
import com.hdw.mccable.dto.ReceiptSearchBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.utils.Pagination;

@Service
public class FinancialServiceImpl implements FinancialService{
	
	private FinancialDAO financialDAO;
	
	public void setFinancialDAO(FinancialDAO financialDAO) {
		this.financialDAO = financialDAO;
	}
	
	
	@Transactional
	public List<Invoice> findAllInvoice() {	
		return financialDAO.findAllInvoice();
	}

	@Transactional
	public Long saveInvoice(Invoice invoice) throws Exception {
		return financialDAO.saveInvoice(invoice);
	}

	@Transactional
	public Long saveReceipt(Receipt receipt) throws Exception {
		return financialDAO.saveReceipt(receipt);
	}

	@Transactional
	public String genInVoiceCode() {
		return financialDAO.genInVoiceCode();
	}

	@Transactional
	public String genReceiptCode() {
		return financialDAO.genReceiptCode();
	}

	@Transactional
	public void updateInvoice(Invoice invoice) throws Exception {
		financialDAO.updateInvoice(invoice);
	}

	@Transactional
	public int getCountTotalOrderBill(OrderBillSearchBean orderBillSearchBean) {
		return financialDAO.getCountTotalOrderBill(orderBillSearchBean);
	}

	@Transactional
	public Pagination getByPageForOrderBill(Pagination pagination, OrderBillSearchBean orderBillSearchBean,Boolean isAuto) {
		return financialDAO.getByPageForOrderBill(pagination, orderBillSearchBean,isAuto);
	}

	@Transactional
	public Pagination getByPageForOrderInvoice(Pagination pagination, InvoiceSearchBean invoiceSearchBean,Boolean isAuto) {
		return financialDAO.getByPageForOrderInvoice(pagination, invoiceSearchBean, isAuto);
	}

	@Transactional
	public int getCountTotalOrderInvoice(InvoiceSearchBean invoiceSearchBean,Boolean isAuto) {
		return financialDAO.getCountTotalOrderInvoice(invoiceSearchBean, isAuto);
	}

	@Transactional
	public Invoice getInvoiceById(Long id) {
		return financialDAO.getInvoiceById(id);
	}

	@Transactional
	public int getCountTotalOrderReceipt(ReceiptSearchBean receiptSearchBean,String status) {
		return financialDAO.getCountTotalOrderReceipt(receiptSearchBean,status);
	}

	@Transactional
	public Pagination getByPageForOrderReceipt(Pagination pagination, ReceiptSearchBean receiptSearchBean,String status) {
		return financialDAO.getByPageForOrderReceipt(pagination,receiptSearchBean,status);
	}

	@Transactional
	public Receipt getReceiptById(Long id) {
		return financialDAO.getReceiptById(id);
	}

	@Transactional
	public Date getPaymentOrderDate(Long serviceApplicationId) {
		return financialDAO.getPaymentOrderDate(serviceApplicationId);
	}

	@Transactional
	public boolean updateOverDue() throws Exception {
		return financialDAO.updateOverDue();
	}

	@Transactional
	public void updateReceipt(Receipt receipt) throws Exception {
		financialDAO.updateReceipt(receipt);
	}

	@Transactional
	public Invoice getInvoiceByInvoiceCodeScan(String invoiceCode,String status, String NotstatusPayment) {
		return financialDAO.getInvoiceByInvoiceCodeScan(invoiceCode,status,NotstatusPayment);
	}

	@Transactional
	public List<Invoice> findInvoiceSearch(SearchBillScanBean searchBillScanBean) {
		return financialDAO.findInvoiceSearch(searchBillScanBean);
	}

	@Transactional
	public List<Invoice> findInvoiceTypeSearch(SearchBillScanBean searchBillScanBean) {
		return financialDAO.findInvoiceTypeSearch(searchBillScanBean);
	}
	
	@Transactional
	public List<InvoiceHistoryPrint> findInvoiceHistoryPrintSearch(SearchBillScanBean searchBillScanBean) {
		return financialDAO.findInvoiceHistoryPrintSearch(searchBillScanBean);
	}

	@Transactional
	public List<Invoice> getByInvoiceTypeForReport(String reportrange, String invoiceType, String split, String invoiceStatus) {
		return financialDAO.getByInvoiceTypeForReport(reportrange,invoiceType,split,invoiceStatus);
	}
	
	@Transactional
	public List<Invoice> getByInvoiceTypeBybaddebtForReport(String reportrange, String invoiceType, String split, String invoiceStatus) {
		return financialDAO.getByInvoiceTypeBybaddebtForReport(reportrange,invoiceType,split,invoiceStatus);
	}

	@Transactional
	public List<Invoice> findInvoiceByServiceApplication(Long ServiceApplicationId, String TypeInvoice) {
		return financialDAO.findInvoiceByServiceApplication(ServiceApplicationId, TypeInvoice);
	}

	@Transactional
	public Invoice getInvoiceByCode(String invoiceCode) {
		return financialDAO.getInvoiceByCode(invoiceCode);
	}

	@Transactional
	public List<Invoice> findInvoiceTypeSearchConfig(SearchBillScanBean searchBillScanBean) {
		return financialDAO.findInvoiceTypeSearchConfig(searchBillScanBean);
	}

	@Transactional
	public List<Invoice> findInvoiceByCreateDate() {
		return financialDAO.findInvoiceByCreateDate();
	}
	
}
