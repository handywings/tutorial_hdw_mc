package com.hdw.mccable.dao;

import com.hdw.mccable.entity.InternetProductItem;

public interface InternetProductItemDAO {
	public InternetProductItem getInternetProductItemById(Long id);
	public void update(InternetProductItem internetProductItem) throws Exception;
	public Long save(InternetProductItem internetProductItem) throws Exception;
	public InternetProductItem getInternetProductItemByUserNameOrPassword(String userName, String password);
}
