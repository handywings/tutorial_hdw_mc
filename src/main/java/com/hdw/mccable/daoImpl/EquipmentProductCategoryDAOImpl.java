package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.EquipmentProductCategoryDAO;
import com.hdw.mccable.entity.EquipmentProductCategory;

@Repository
public class EquipmentProductCategoryDAOImpl implements EquipmentProductCategoryDAO {
	private static final Logger logger = LoggerFactory.getLogger(EquipmentProductCategoryDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	public EquipmentProductCategory getEquipmentProductCategoryById(Long id) {
		logger.info("[method : getEquipmentProductCategoryById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		EquipmentProductCategory equipmentProductCategory = (EquipmentProductCategory) session
				.get(EquipmentProductCategory.class, id);
		return equipmentProductCategory;
	}

	@SuppressWarnings("unchecked")
	public List<EquipmentProductCategory> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<EquipmentProductCategory> equipmentProductCategory = (List<EquipmentProductCategory>) session
				.createQuery("from EquipmentProductCategory where isDeleted = false").list();
		return equipmentProductCategory;
	}

	public void update(EquipmentProductCategory equipmentProductCategory) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(equipmentProductCategory);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(EquipmentProductCategory equipmentProductCategory) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(equipmentProductCategory);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void delete(EquipmentProductCategory equipmentProductCategory) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		equipmentProductCategory.setDeleted(true);
		update(equipmentProductCategory);
	}

	public String genEquipmentProductCategoryCode() throws Exception {
		logger.info("[method : genEquipmentProductCategoryCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT LPAD((SELECT COUNT(id)+1 FROM equipment_product_category), 5, '0') as productCategoryCode ");
		Object obj = query.uniqueResult();
		if (null != obj) {
			logger.info("ProductCategoryCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<EquipmentProductCategory> findTypeEquipmentOnly() {
		logger.info("[method : findTypeEquipmentOnly][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append(" from EquipmentProductCategory where isDeleted = false ");
		strQuery = strQuery.append(" and equipmentProductCategoryCode not in('00002','00003') ");
		
		List<EquipmentProductCategory> equipmentProductCategory = (List<EquipmentProductCategory>) session.createQuery(strQuery.toString()).list();
		
		return equipmentProductCategory;
	}

	public EquipmentProductCategory getEquipmentProductCategoryByCode(String equipmentProductCategoryCode) {
		logger.info("[method : getEquipmentProductCategoryByCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProductCategory epc where epc.isDeleted = false and epc.equipmentProductCategoryCode = :equipmentProductCategoryCode");
		logger.info("[method : getEquipmentProductCategoryByCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("equipmentProductCategoryCode", equipmentProductCategoryCode);

		// execute
		EquipmentProductCategory equipmentProductCategory = (EquipmentProductCategory) query.uniqueResult();

		return equipmentProductCategory;
	}

	public EquipmentProductCategory getEquipmentProductCategoryByEquipmentProductCategoryName(
			String equipmentProductCategoryName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProductCategory epc where epc.equipmentProductCategoryName = :equipmentProductCategoryName");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("equipmentProductCategoryName", equipmentProductCategoryName);

		// execute
		EquipmentProductCategory equipmentProductCategory = (EquipmentProductCategory) query.uniqueResult();

		return equipmentProductCategory;
	}

	@SuppressWarnings("unchecked")
	public List<EquipmentProductCategory> findTypeEquipmentAndService() {
		logger.info("[method : findTypeEquipmentOnly][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append(" from EquipmentProductCategory where isDeleted = false ");
		strQuery = strQuery.append(" and equipmentProductCategoryCode not in('00002') ");
		
		List<EquipmentProductCategory> equipmentProductCategory = (List<EquipmentProductCategory>) session.createQuery(strQuery.toString()).list();
		
		return equipmentProductCategory;
	}

}
