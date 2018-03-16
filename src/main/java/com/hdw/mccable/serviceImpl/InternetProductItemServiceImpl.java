package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.InternetProductItemDAO;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.service.InternetProductItemService;

@Service
public class InternetProductItemServiceImpl implements InternetProductItemService{

	private InternetProductItemDAO internetProductItemDAO;
	
	public void setInternetProductItemDAO(InternetProductItemDAO internetProductItemDAO) {
		this.internetProductItemDAO = internetProductItemDAO;
	}

	@Transactional
	public InternetProductItem getInternetProductItemById(Long id) {
		return internetProductItemDAO.getInternetProductItemById(id);
	}

	@Transactional
	public void update(InternetProductItem internetProductItem) throws Exception {
		internetProductItemDAO.update(internetProductItem);
	}

	@Transactional
	public Long save(InternetProductItem internetProductItem) throws Exception {
		return internetProductItemDAO.save(internetProductItem);
	}

	@Transactional
	public InternetProductItem getInternetProductItemByUserNameOrPassword(String userName, String password) {
		return internetProductItemDAO.getInternetProductItemByUserNameOrPassword(userName,password);
	}

}
