package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.dto.CustomerSearchBean;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.utils.Pagination;

public interface CustomerService {
	public Customer getCustomerById(Long id);
	public Customer getCustomerByCustCode(String custCode);
	public List<Customer> findAll();
	public void update(Customer customer) throws Exception;
	public Long save(Customer customer) throws Exception;
	public void delete(Customer customer) throws Exception;
	public List<Customer> searchCustomer(String key,String custType,Long custFeature);
	public String genCustomerCode() throws Exception;
	public List<Career> findAllCareer();
	public List<CustomerFeature> findAllCustomerFeature();
	public Career findCareerById(Long id);
	public Career findCareerByCareerName(String careerName);
	public CustomerFeature findCustomerFeatureById(Long id);
	public CustomerFeature findCustomerFeatureByCustomerFeatureName(String customerFeatureName);
	public CustomerFeature findCustomerFeatureByCustomerFeatureCode(String customerFeatureCode);
	public List<Customer> getCustomerByConditionForReport(String reportrange,String zone,String career,String customerType);
	public List<ServiceApplication> getCustomerByServicetypeForReport(String reportrange, String servicePackageType, String customerFeature);
	public List<ServiceApplication> getCustomerByServiceAppTypeForReport(String reportrange,String serviceApplicationType);
	public List<WorksheetCut> getCustomerByWorksheetCancelForReport(String reportrange, int displayFormat,
			int sortingStyle);
	public List<WorksheetMove> getCustomerByWorksheetMoveForReport(String reportrange, int sortingStyle);
	public List<Customer> findAllisActive();
	
	public int getCountTotal(CustomerSearchBean customerSearchBean);
	public Pagination getByPage(Pagination pagination, CustomerSearchBean customerSearchBean);
	
	public Long save(CustomerFeature customerFeature) throws Exception;
	
}
