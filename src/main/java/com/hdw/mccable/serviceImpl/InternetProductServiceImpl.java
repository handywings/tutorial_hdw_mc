package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.InternetProductDAO;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.service.InternetProductService;

@Service
public class InternetProductServiceImpl implements InternetProductService{

	private InternetProductDAO internetProductDAO;
	
	public void setInternetProductDAO(InternetProductDAO internetProductDAO) {
		this.internetProductDAO = internetProductDAO;
	}

	@Transactional
	public InternetProduct getInternetProductById(Long id) {
		return internetProductDAO.getInternetProductById(id);
	}

	@Transactional
	public InternetProduct getInternetProductByProductName(String productName) {
		return internetProductDAO.getInternetProductByProductName(productName);
	}

	@Transactional
	public List<InternetProduct> findAll() {
		return internetProductDAO.findAll();
	}

	@Transactional
	public List<InternetProduct> searchByStockOrCriteria(String criteria, Long stockId) {
		return internetProductDAO.searchByStockOrCriteria(criteria, stockId);
	}

	@Transactional
	public void update(InternetProduct internetProduct) throws Exception {
		internetProductDAO.update(internetProduct);
	}

	@Transactional
	public Long save(InternetProduct internetProduct) throws Exception {
		return internetProductDAO.save(internetProduct);
	}

	@Transactional
	public void delete(InternetProduct internetProduct) throws Exception {
		internetProductDAO.delete(internetProduct);
	}
	
	@Transactional
	public boolean checkDubplicateUsername(String userName) throws Exception {
		return internetProductDAO.checkDubplicateUsername(userName);
	}
	
	@Transactional
	public boolean checkDuplicateUsernameForUpdate(String userName, Long id) throws Exception {
		return internetProductDAO.checkDuplicateUsernameForUpdate(userName, id);
	}

}
