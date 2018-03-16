package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.HistoryUseEquipmentDAO;
import com.hdw.mccable.entity.HistoryUseEquipment;

@Repository
public class HistoryUseEquipmentDAOImpl implements HistoryUseEquipmentDAO{
	private static final Logger logger = LoggerFactory.getLogger(HistoryUseEquipmentDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////

	public HistoryUseEquipment getHistoryUseEquipmentById(Long id) {
		logger.info("[method : getHistoryUseEquipmentById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		HistoryUseEquipment historyUseEquipment = (HistoryUseEquipment) session.get(HistoryUseEquipment.class, id); 
		return historyUseEquipment;
	}

	public void update(HistoryUseEquipment historyUseEquipment) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(historyUseEquipment);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(HistoryUseEquipment historyUseEquipment) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(historyUseEquipment);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
