package com.hdw.mccable.dao;

import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.security.LoginForm;

public interface AuthenDAO {
	public Authentication findByUserNamePassword(LoginForm loginForm) throws Exception;
	public void update(Authentication authentication) throws Exception;
}
