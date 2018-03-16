package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.FunctionDAO;
import com.hdw.mccable.entity.Function;

@Repository
public class FunctionDAOImpl implements FunctionDAO{
	private static final Logger logger = LoggerFactory.getLogger(FunctionDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public Function getFunctionById(Long id) {
		logger.info("[method : getPositionById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		Function function = (Function) session.get(Function.class, id); 
		return function;
	}

	@SuppressWarnings("unchecked")
	public List<Function> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Function> function = (List<Function>) session.createQuery("from Function where isDeleted = false").list();
		return function;
	}
	
	public void update(Function function) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(function);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(Function function) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(function);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(Function function) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(function);
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
