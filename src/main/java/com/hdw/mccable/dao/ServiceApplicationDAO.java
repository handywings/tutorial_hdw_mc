package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.DigitalAnalogBean;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationAssignCashier;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.utils.Pagination;

public interface ServiceApplicationDAO {
	
	public ServiceApplication getServiceApplicationById(Long id);
	public List<ServiceApplication> findAll();
	public void update(ServiceApplication serviceApplication) throws Exception;
	public Long save(ServiceApplication serviceApplication) throws Exception;
	public int getCountTotalByApplicationSearchBean(ApplicationSearchBean applicationSearchBean);
	public int getCountTotal(ApplicationSearchBean applicationSearchBean);
	public int getChangeserviceCountTotal(ApplicationSearchBean applicationSearchBean);
	public Pagination getByPageByApplicationSearchBean(Pagination pagination,ApplicationSearchBean applicationSearchBean);
	public Pagination getByPage(Pagination pagination,ApplicationSearchBean applicationSearchBean);
	public Pagination getChangeserviceByPage(Pagination pagination,ApplicationSearchBean applicationSearchBean);
	public String genServiceApplicationCode();
	public List<ServiceApplication> search(String key, String customerType, Long customerFeatures);
	public List<ServiceApplicationType> findAllServiceApplicationType();
	public ServiceApplicationType getServiceApplicationTypeById(Long id);
	public List<ServiceApplication> searchServiceApplicationByStatus(ApplicationSearchBean applicationSearchBean);
	public ServiceApplication getServiceApplicationByHouseNumber(String houseNumber);
	public List<ServiceApplication> getListDigitalAnalogByCustomerId(Long id);
	public void deleteByCustomerId(Long customerId);
	public List<ServiceApplication> searchServiceApplicationByApplicationSearchBean(ApplicationSearchBean applicationSearchBean);
	public void save(ServiceApplicationAssignCashier serviceApplicationAssignCashier) throws Exception;
	public void deleteServiceApplicationAssignCashierByPersonnel(Long personnelId);
	public List<Long> findServiceApplicationAssignCashierByCashier(Long cashierId);
	public void deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId(Long personnelId,
			Long serviceApplicationId);
	public List<ServiceApplication> searchServiceApplicationByStatusAndRefund(
			ApplicationSearchBean applicationSearchBean);
	public ServiceApplicationType getServiceApplicationTypeByName(String serviceApplicationType);
	public Long save(ServiceApplicationType serviceApplicationType) throws Exception;
	public List<ServiceApplication> findAlls();
}
