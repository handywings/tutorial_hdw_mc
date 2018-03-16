package com.hdw.mccable.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.DashboardDAO;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	private DashboardDAO dashboardDAO;
	
	public void setDashboardDAO(DashboardDAO dashboardDAO) {
		this.dashboardDAO = dashboardDAO;
	}

	@Transactional
	public List<Invoice> findAllInvoiceByDate(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.findAllInvoiceByDate(criteria);
	}

	@Transactional
	public CashierBean countBillAndAmountByDate(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.countBillAndAmountByDate(criteria);
	}

	@Transactional
	public List<Worksheet> findAllWorksheet(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.findAllWorksheet(criteria);
	}

	@Transactional
	public List<Customer> findAllCustomerByDate(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.findAllCustomerByDate(criteria);
	}

	@Transactional
	public List<ServiceApplication> findServiceAppByDate(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.findServiceAppByDate(criteria);
	}

	@Transactional
	public int countCustomer(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.countCustomer(criteria);
	}

	@Transactional
	public int countCustomerFromZone(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.countCustomerFromZone(criteria);
	}

	@Transactional
	public int countIncome(Map<String, Object> criteria) throws Exception {
		return dashboardDAO.countIncome(criteria);
	}
	
}
