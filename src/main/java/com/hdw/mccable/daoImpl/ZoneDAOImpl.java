package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ZoneDAO;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.Zone;

@Repository
public class ZoneDAOImpl implements ZoneDAO{
	private static final Logger logger = LoggerFactory.getLogger(ZoneDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public Zone getZoneById(Long id) {
		logger.info("[method : getZoneById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		Zone zone = (Zone) session.get(Zone.class, id); 
		return zone;
	}

	@SuppressWarnings("unchecked")
	public List<Zone> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Zone> zone = (List<Zone>) session.createQuery("from Zone where isDeleted = false").list();
		return zone;
	}
	
	public void update(Zone zone) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(zone);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(Zone zone) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(zone);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(Zone zone) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		zone.setDeleted(true);
		update(zone);
	}
	
	public Zone getZoneByZoneDetail(String zoneDetail) {
		if(!"".equals(zoneDetail)){
			logger.info("[method : getZoneByZoneDetail][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT z.* FROM zone z where z.zoneDetail = :zoneDetail ");
			
			logger.info("[method : getZoneByZoneDetail][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("zoneDetail", zoneDetail);	
			query.addEntity(Zone.class);
			//execute
			List<Zone> zone = query.list();
			if(null != zone && zone.size() > 0){
				return zone.get(0);
			}
		}
		return null;
	}
	
}
