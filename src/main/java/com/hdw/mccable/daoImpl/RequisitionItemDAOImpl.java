package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.RequisitionItemDAO;
import com.hdw.mccable.entity.RequisitionItem;

@Repository
public class RequisitionItemDAOImpl implements RequisitionItemDAO{
	private static final Logger logger = LoggerFactory.getLogger(RequisitionItemDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public RequisitionItem getRequisitionItemById(Long id) {
		logger.info("[method : getRequisitionItemById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		RequisitionItem requisitionItem = (RequisitionItem) session.get(RequisitionItem.class, id); 
		return requisitionItem;
	}
	
	public void update(RequisitionItem requisitionItem) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(requisitionItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(RequisitionItem requisitionItem) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(requisitionItem);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void delete(RequisitionItem requisitionItem) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(requisitionItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
