package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.PermissionGroupDAO;
import com.hdw.mccable.entity.PermissionGroup;

@Repository
public class PermissionGroupDAOImpl implements PermissionGroupDAO{
	private static final Logger logger = LoggerFactory.getLogger(PermissionGroupDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public PermissionGroup getPermissionGroupById(Long id) {
		logger.info("[method : getPermissionGroupById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		PermissionGroup permissionGroup = (PermissionGroup) session.get(PermissionGroup.class, id); 
		return permissionGroup;
	}

	@SuppressWarnings("unchecked")
	public List<PermissionGroup> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PermissionGroup> permissionGroup = (List<PermissionGroup>) session.createQuery("from PermissionGroup where isDeleted = false").list();
		return permissionGroup;
	}
	
	public void update(PermissionGroup permissionGroup) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(permissionGroup);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(PermissionGroup permissionGroup) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(permissionGroup);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(PermissionGroup permissionGroup) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		permissionGroup.setDeleted(true);
		update(permissionGroup);
	}
	
	public PermissionGroup findByPermissionTypeDefault() {
		logger.info("[method : findByPermissionTypeDefault][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		PermissionGroup permissionGroups = (PermissionGroup) session.createQuery("from PermissionGroup where permissionType = 'Default' and isDeleted = false ").uniqueResult();
		return permissionGroups;
	}
	
	@SuppressWarnings("unchecked")
	public List<PermissionGroup> findByPermissionNotTypeDefault() {
		logger.info("[method : findByPermissionNotTypeDefault][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PermissionGroup> permissionGroups = (List<PermissionGroup>) session.createQuery("from PermissionGroup where (permissionType is null or permissionType != 'Default') and isDeleted = false").list();
		
		return permissionGroups;
	}
	
}
