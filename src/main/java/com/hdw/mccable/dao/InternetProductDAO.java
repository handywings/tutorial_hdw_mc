package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.InternetProduct;

public interface InternetProductDAO {
	public InternetProduct getInternetProductById(Long id);
	public InternetProduct getInternetProductByProductName(String productName);
	public List<InternetProduct> findAll();
	public List<InternetProduct> searchByStockOrCriteria(String criteria, Long stockId);
	public void update(InternetProduct internetProduct) throws Exception;
	public Long save(InternetProduct internetProduct) throws Exception;
	public void delete(InternetProduct internetProduct) throws Exception;
	public boolean checkDubplicateUsername(String userName) throws Exception;
	public boolean checkDuplicateUsernameForUpdate(String userName,Long id) throws Exception;
}
