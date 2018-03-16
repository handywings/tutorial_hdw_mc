package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.CustomerDAO;
import com.hdw.mccable.dto.CustomerSearchBean;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.utils.Pagination;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDAO customerDAO;

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Transactional
	public Customer getCustomerById(Long id) {
		return customerDAO.getCustomerById(id);
	}

	@Transactional
	public Customer getCustomerByCustCode(String custCode) {
		return customerDAO.getCustomerByCustCode(custCode);
	}

	@Transactional
	public List<Customer> findAll() {
		return customerDAO.findAll();
	}
	
	@Transactional
	public List<Customer> findAllisActive() {
		return customerDAO.findAllisActive();
	}

	@Transactional
	public void update(Customer customer) throws Exception {
		customerDAO.update(customer);
	}

	@Transactional
	public Long save(Customer customer) throws Exception {
		return customerDAO.save(customer);
	}

	@Transactional
	public void delete(Customer customer) throws Exception {
		customerDAO.delete(customer);
	}
	
	@Transactional
	public List<Customer> searchCustomer(String key, String custType, Long custFeature) {
		return customerDAO.searchCustomer(key, custType, custFeature);
	}

	@Transactional
	public String genCustomerCode() throws Exception {
		return customerDAO.genCustomerCode();
	}
	
	@Transactional
	public List<Career> findAllCareer() {
		return customerDAO.findAllCareer();
	}

	@Transactional
	public List<CustomerFeature> findAllCustomerFeature() {
		return customerDAO.findAllCustomerFeature();
	}
	
	@Transactional
	public Career findCareerById(Long id) {
		return customerDAO.findCareerById(id);
	}

	@Transactional
	public Career findCareerByCareerName(String careerName) {
		return customerDAO.findCareerByCareerName(careerName);
	}

	@Transactional
	public CustomerFeature findCustomerFeatureById(Long id) {
		return customerDAO.findCustomerFeatureById(id);
	}

	@Transactional
	public CustomerFeature findCustomerFeatureByCustomerFeatureName(String customerFeatureName) {
		return customerDAO.findCustomerFeatureByCustomerFeatureName(customerFeatureName);
	}

	@Transactional
	public CustomerFeature findCustomerFeatureByCustomerFeatureCode(String customerFeatureCode) {
		return customerDAO.findCustomerFeatureByCustomerFeatureCode(customerFeatureCode);
	}
	
	@Transactional
	public List<Customer> getCustomerByConditionForReport(String reportrange, String zone, String career,
			String customerType) {
		return customerDAO.getCustomerByConditionForReport(reportrange,zone,career,customerType);
	}

	@Transactional
	public List<ServiceApplication> getCustomerByServicetypeForReport(String reportrange, String servicePackageType, String customerFeature) {
		return customerDAO.getCustomerByServicetypeForReport(reportrange,servicePackageType,customerFeature);
	}

	@Transactional
	public List<ServiceApplication> getCustomerByServiceAppTypeForReport(String reportrange,
			String serviceApplicationType) {
		return customerDAO.getCustomerByServiceAppTypeForReport(reportrange,serviceApplicationType);
	}

	@Transactional
	public List<WorksheetCut> getCustomerByWorksheetCancelForReport(String reportrange, int displayFormat,
			int sortingStyle) {
		return customerDAO.getCustomerByWorksheetCancelForReport(reportrange,displayFormat,sortingStyle);
	}

	@Transactional
	public List<WorksheetMove> getCustomerByWorksheetMoveForReport(String reportrange, int sortingStyle) {
		return customerDAO.getCustomerByWorksheetMoveForReport(reportrange,sortingStyle);
	}

	@Transactional
	public int getCountTotal(CustomerSearchBean customerSearchBean) {
		return customerDAO.getCountTotal(customerSearchBean);
	}

	@Transactional
	public Pagination getByPage(Pagination pagination, CustomerSearchBean customerSearchBean) {
		return customerDAO.getByPage(pagination,customerSearchBean);
	}

	@Transactional
	public Long save(CustomerFeature customerFeature) throws Exception {
		return customerDAO.save(customerFeature);
	}
	
}
