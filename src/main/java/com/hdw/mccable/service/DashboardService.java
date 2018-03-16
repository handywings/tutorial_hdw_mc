package com.hdw.mccable.service;

import java.util.List;
import java.util.Map;

import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.Worksheet;

public interface DashboardService {

	List<Invoice> findAllInvoiceByDate(Map<String, Object> criteria) throws Exception;
	CashierBean countBillAndAmountByDate(Map<String, Object> criteria) throws Exception;
	List<Worksheet> findAllWorksheet(Map<String, Object> criteria) throws Exception;
	List<Customer> findAllCustomerByDate(Map<String, Object> criteria) throws Exception;
	List<ServiceApplication> findServiceAppByDate(Map<String, Object> criteria) throws Exception;
	int countCustomer(Map<String, Object> criteria) throws Exception;
	int countCustomerFromZone(Map<String, Object> criteria) throws Exception;
	int countIncome(Map<String, Object> criteria) throws Exception;
}
