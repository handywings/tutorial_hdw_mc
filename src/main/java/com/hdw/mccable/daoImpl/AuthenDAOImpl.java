package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.AuthenDAO;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.utils.SecurityUtils;

@Repository
public class AuthenDAOImpl implements AuthenDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenDAOImpl.class);
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Authentication findByUserNamePassword(LoginForm loginForm) throws Exception {
		logger.info("[method : findByPersonnelCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Authentication authentication = null;
		try {
			if(loginForm!=null){
			authentication = (Authentication) session
					.createQuery("from Authentication where username = :username and password = :password")
					.setString("username", loginForm.getUsername())
					.setString("password", SecurityUtils.encrypt(loginForm.getPassword())).uniqueResult();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
		logger.info("Authen : "+ authentication);
		return authentication;
	}

	public void update(Authentication authentication) throws Exception {
		logger.info("[method : update][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(authentication);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

}
