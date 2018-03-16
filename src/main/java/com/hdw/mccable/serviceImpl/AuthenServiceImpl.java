package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.AuthenDAO;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.service.AuthenService;

@Service
public class AuthenServiceImpl implements AuthenService{
	
	private AuthenDAO authenDAO;

	public void setAuthenDAO(AuthenDAO authenDAO) {
		this.authenDAO = authenDAO;
	}

	@Transactional
	public Authentication findByUserNamePassword(LoginForm loginForm) throws Exception {
		return authenDAO.findByUserNamePassword(loginForm);
	}

	@Transactional
	public void update(Authentication authentication) throws Exception {
		this.authenDAO.update(authentication);
	}

}
