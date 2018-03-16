package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.ServiceProduct;

public interface ServiceProductService {
	public ServiceProduct getServiceProductById(Long id);
	public List<ServiceProduct> findAll();
	public List<ServiceProduct> searchByStockOrCriteria(String criteria, Long stockId);
	public List<ServiceProduct> getSetUpPoint(String[] productCode); //ค่าติดตั้งจุด
	public void update(ServiceProduct serviceProduct) throws Exception;
	public Long save(ServiceProduct serviceProduct) throws Exception;
	public void delete(ServiceProduct serviceProduct) throws Exception;
	public ServiceProduct getSerivceProductByCode(String productCode);
}
