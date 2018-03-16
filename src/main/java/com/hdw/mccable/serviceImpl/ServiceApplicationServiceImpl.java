package com.hdw.mccable.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ServiceApplicationDAO;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.DigitalAnalogBean;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationAssignCashier;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.utils.Pagination;

@Service
public class ServiceApplicationServiceImpl implements ServiceApplicationService{

	private ServiceApplicationDAO serviceApplicationDAO;
	
	public void setServiceApplicationDAO(ServiceApplicationDAO serviceApplicationDAO) {
		this.serviceApplicationDAO = serviceApplicationDAO;
	}

	@Transactional
	public ServiceApplication getServiceApplicationById(Long id) {
		return serviceApplicationDAO.getServiceApplicationById(id);
	}

	@Transactional
	public List<ServiceApplication> findAll() {
		return serviceApplicationDAO.findAll();
	}

	@Transactional
	public void update(ServiceApplication serviceApplication) throws Exception {
		serviceApplicationDAO.update(serviceApplication);
	}

	@Transactional
	public Long save(ServiceApplication serviceApplication) throws Exception {
		return serviceApplicationDAO.save(serviceApplication);
	}
	
	@Transactional
	public int getCountTotalByApplicationSearchBean(ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getCountTotalByApplicationSearchBean(applicationSearchBean);
	}
	
	@Transactional
	public int getCountTotal(ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getCountTotal(applicationSearchBean);
	}
	
	@Transactional
	public int getChangeserviceCountTotal(ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getChangeserviceCountTotal(applicationSearchBean);
	}
	
	@Transactional
	public Pagination getByPageByApplicationSearchBean(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getByPageByApplicationSearchBean(pagination, applicationSearchBean);
	}
	
	@Transactional
	public Pagination getByPage(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getByPage(pagination, applicationSearchBean);
	}

	@Transactional
	public Pagination getChangeserviceByPage(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.getChangeserviceByPage(pagination, applicationSearchBean);
	}
	
	@Transactional
	public String genServiceApplicationCode() throws Exception {
		return serviceApplicationDAO.genServiceApplicationCode();
	}
	
	@Transactional
	public List<ServiceApplication> search(String key, String customerType, Long customerFeatures) {
		return serviceApplicationDAO.search(key, customerType, customerFeatures);
	}
	
	@Transactional
	public List<ServiceApplicationType> findAllServiceApplicationType() {
		return serviceApplicationDAO.findAllServiceApplicationType();
	}

	@Transactional
	public ServiceApplicationType getServiceApplicationTypeById(Long id) {
		return serviceApplicationDAO.getServiceApplicationTypeById(id);
	}

	@Transactional
	public List<ServiceApplication> searchServiceApplicationByStatus(ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.searchServiceApplicationByStatus(applicationSearchBean);
	}

	@Transactional
	public ServiceApplication getServiceApplicationByHouseNumber(String houseNumber) {
		return serviceApplicationDAO.getServiceApplicationByHouseNumber(houseNumber);
	}

	@Transactional
	public List<ServiceApplication> getListDigitalAnalogByCustomerId(Long id) {
		 return serviceApplicationDAO.getListDigitalAnalogByCustomerId(id);
	}

	@Transactional
	public void deleteByCustomerId(Long customerId) {
		serviceApplicationDAO.deleteByCustomerId(customerId);
	}

	@Transactional
	public List<ServiceApplication> searchServiceApplicationByApplicationSearchBean(
			ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.searchServiceApplicationByApplicationSearchBean(applicationSearchBean);
	}

	@Transactional
	public void save(ServiceApplicationAssignCashier serviceApplicationAssignCashier) throws Exception {
		serviceApplicationDAO.save(serviceApplicationAssignCashier);
	}

	@Transactional
	public void deleteServiceApplicationAssignCashierByPersonnel(Long personnelId) {
		serviceApplicationDAO.deleteServiceApplicationAssignCashierByPersonnel(personnelId);
	}

	@Transactional
	public List<Long> findServiceApplicationAssignCashierByCashier(Long cashierId) {
		return serviceApplicationDAO.findServiceApplicationAssignCashierByCashier(cashierId);
	}
	
	@Transactional
	public void deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId(Long personnelId,Long serviceApplicationId) {
		serviceApplicationDAO.deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId(personnelId,serviceApplicationId);
	}

	@Transactional
	public List<ServiceApplication> searchServiceApplicationByStatusAndRefund(
			ApplicationSearchBean applicationSearchBean) {
		return serviceApplicationDAO.searchServiceApplicationByStatusAndRefund(applicationSearchBean);
	}
	
	@Transactional
	public ServiceApplicationType getServiceApplicationTypeByName(String serviceApplicationType) {
		return serviceApplicationDAO.getServiceApplicationTypeByName(serviceApplicationType);
	}

	@Transactional
	public Long save(ServiceApplicationType serviceApplicationType) throws Exception {
		return serviceApplicationDAO.save(serviceApplicationType);
	}
	
	@Transactional
	public List<ServiceApplication> findAlls() {
		return serviceApplicationDAO.findAlls();
	}
}
