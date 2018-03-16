package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.ServiceProduct;

public interface ServiceProductDAO {
	
	public ServiceProduct getServiceProductById(Long id);
	public List<ServiceProduct> findAll();
	public List<ServiceProduct> searchByStockOrCriteria(String criteria, Long stockId);
	public void update(ServiceProduct serviceProduct) throws Exception;
	public Long save(ServiceProduct serviceProduct) throws Exception;
	public void delete(ServiceProduct serviceProduct) throws Exception;
	public ServiceProduct getSerivceProductByCode(String productCode);
	public List<ServiceProduct> getSetUpPoint(String[] productCode);

}
