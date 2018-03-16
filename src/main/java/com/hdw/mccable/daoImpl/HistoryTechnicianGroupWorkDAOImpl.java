package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.HistoryTechnicianGroupWorkDAO;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;

@Repository
public class HistoryTechnicianGroupWorkDAOImpl implements HistoryTechnicianGroupWorkDAO{
	
private static final Logger logger = LoggerFactory.getLogger(HistoryTechnicianGroupWorkDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	
	public Long save(HistoryTechnicianGroupWork historyTechnicianGroupWork) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(historyTechnicianGroupWork);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void update(HistoryTechnicianGroupWork historyTechnicianGroupWork) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(historyTechnicianGroupWork);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

}
