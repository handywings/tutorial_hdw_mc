package com.hdw.mccable.service;

import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.security.LoginForm;

public interface AuthenService {
	public Authentication findByUserNamePassword(LoginForm loginForm) throws Exception;
	public void update(Authentication authentication) throws Exception;
}
