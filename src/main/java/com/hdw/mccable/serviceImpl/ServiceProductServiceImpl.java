package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ServiceProductDAO;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.service.ServiceProductService;

@Service
public class ServiceProductServiceImpl implements ServiceProductService{

	private ServiceProductDAO serviceProductDAO;
	
	public void setServiceProductDAO(ServiceProductDAO serviceProductDAO) {
		this.serviceProductDAO = serviceProductDAO;
	}

	@Transactional
	public ServiceProduct getServiceProductById(Long id) {
		return serviceProductDAO.getServiceProductById(id);
	}

	@Transactional
	public List<ServiceProduct> findAll() {
		return serviceProductDAO.findAll();
	}

	@Transactional
	public List<ServiceProduct> searchByStockOrCriteria(String criteria, Long stockId) {
		return serviceProductDAO.searchByStockOrCriteria(criteria, stockId);
	}

	@Transactional
	public void update(ServiceProduct serviceProduct) throws Exception {
		serviceProductDAO.update(serviceProduct);
	}

	@Transactional
	public Long save(ServiceProduct serviceProduct) throws Exception {
		return serviceProductDAO.save(serviceProduct);
	}

	@Transactional
	public void delete(ServiceProduct serviceProduct) throws Exception {
		serviceProductDAO.delete(serviceProduct);
	}
	
	@Transactional
	public ServiceProduct getSerivceProductByCode(String productCode) {
		return serviceProductDAO.getSerivceProductByCode(productCode);
	}

	@Transactional
	public List<ServiceProduct> getSetUpPoint(String[] productCode) {
		return serviceProductDAO.getSetUpPoint(productCode);
	}

}
