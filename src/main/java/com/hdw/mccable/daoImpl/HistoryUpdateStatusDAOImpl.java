package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.HistoryUpdateStatusDAO;
import com.hdw.mccable.entity.HistoryUpdateStatus;

@Repository
public class HistoryUpdateStatusDAOImpl implements HistoryUpdateStatusDAO {

	private static final Logger logger = LoggerFactory.getLogger(HistoryUpdateStatusDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////

	public void update(HistoryUpdateStatus historyUpdateStatus) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(historyUpdateStatus);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(HistoryUpdateStatus historyUpdateStatus) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(historyUpdateStatus);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

}
