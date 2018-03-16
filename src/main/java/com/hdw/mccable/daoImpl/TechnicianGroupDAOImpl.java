package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.TechnicianGroupDAO;
import com.hdw.mccable.entity.TechnicianGroup;

@Repository
public class TechnicianGroupDAOImpl implements TechnicianGroupDAO {
	private static final Logger logger = LoggerFactory.getLogger(TechnicianGroupDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	@SuppressWarnings("unchecked")
	public List<TechnicianGroup> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<TechnicianGroup> technicianGroups = (List<TechnicianGroup>) session.createQuery("from TechnicianGroup where isDeleted = false")
				.list();
		return technicianGroups;
	}

	public Long save(TechnicianGroup technicianGroup) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(technicianGroup);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public TechnicianGroup getTechnicianGroupById(Long id) {
		logger.info("[method : getTechnicianGroupById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		TechnicianGroup technicianGroup = (TechnicianGroup) 
				session.createQuery("from TechnicianGroup tc where tc.isDeleted = false and tc.id = :id")
				.setLong("id", id).uniqueResult();
		
		return technicianGroup;
	}

	public void update(TechnicianGroup technicianGroup) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(technicianGroup);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void delete(TechnicianGroup technicianGroup) throws Exception {
		logger.info("[method : delete][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(technicianGroup);
		}catch(Exception ex){
			throw(ex);
		}
	}

}
