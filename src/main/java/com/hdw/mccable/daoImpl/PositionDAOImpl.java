package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.PositionDAO;
import com.hdw.mccable.entity.Position;

@Repository
public class PositionDAOImpl implements PositionDAO{
	private static final Logger logger = LoggerFactory.getLogger(PositionDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public Position getPositionById(Long id) {
		logger.info("[method : getPositionById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		Position position = (Position) session.get(Position.class, id); 
		return position;
	}

	@SuppressWarnings("unchecked")
	public List<Position> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Position> position = (List<Position>) session.createQuery("from Position where isDeleted = false").list();
		return position;
	}
	
	public void update(Position position) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(position);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(Position position) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.save(position);
			session.flush();
			return 0l;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(Position position) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		
		position.setDeleted(true);
		update(position);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Position> findByCompanyId(Long companyId) {
		logger.info("[method : findByCompanyId][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Position> positions = (List<Position>) session.createQuery("from Position where companyId = :companyId and isDeleted = false")
				.setLong("companyId", companyId).list();
		
		return positions;
	}
	@SuppressWarnings("unchecked")
	public List<String> findByDuplicatePositionName() {
		logger.info("[method : findByDuplicatePositionName][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("select distinct positionName from Position where isDeleted = false");
		
		Query query = session.createQuery(strQuery.toString());
		List<String> positions = query.list();
		return positions;
	}
	
}
