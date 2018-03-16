package com.hdw.mccable.daoImpl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.EquipmentProductDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;

@Repository
public class EquipmentProductDAOImpl implements EquipmentProductDAO{
	private static final Logger logger = LoggerFactory.getLogger(EquipmentProductDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public EquipmentProduct getEquipmentProductById(Long id) {
		logger.info("[method : getEquipmentProductById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		EquipmentProduct equipmentProduct = (EquipmentProduct) session.get(EquipmentProduct.class, id); 
		return equipmentProduct;
	}
	
	public EquipmentProduct getEquipmentProductByProductName(String productName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct eq where eq.productName = :productName ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productName", productName);

		// execute		
		EquipmentProduct equipmentProduct = (EquipmentProduct) query.uniqueResult();

		return equipmentProduct;
	}
	
	public EquipmentProduct getEquipmentProductByProductCode(String productCode) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct eq where eq.productCode = :productCode ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productCode", productCode);

		// execute		
		EquipmentProduct equipmentProduct = (EquipmentProduct) query.uniqueResult();

		return equipmentProduct;
	}
	
	public EquipmentProduct getEquipmentProductByProductNameAndStock(String productName,Long stockId) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct eq where eq.productName = :productName and eq.stock.id = :stockId ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productName", productName);
		query.setLong("stockId", stockId);
		// execute		
		EquipmentProduct equipmentProduct = (EquipmentProduct) query.uniqueResult();

		return equipmentProduct;
	}
	
	public EquipmentProduct getEquipmentProductByProductCodeAndStock(String productCode,Long stockId) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct eq where eq.productCode = :productCode and eq.stock.id = :stockId ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productCode", productCode);
		query.setLong("stockId", stockId);
		// execute		
		EquipmentProduct equipmentProduct = (EquipmentProduct) query.uniqueResult();

		return equipmentProduct;
	}
	
	public void update(EquipmentProduct equipmentProduct) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(equipmentProduct);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public List<EquipmentProductItem> getEquipmentProductItemByParentId(Long equipmentProductId) {
		logger.info("[method : getEquipmentProductItemByParentId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProductItem eqi where eqi.isDeleted = false and (eqi.status = 1 or eqi.status = 4) "
				+ " and eqi.equipmentProduct.id=:id");
		
		logger.info("[method : getEquipmentProductItemByParentId][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", equipmentProductId);

		// execute
		List<EquipmentProductItem> equipmentProductItemList = (List<EquipmentProductItem>) query.list();
		return equipmentProductItemList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSupplier() {
		logger.info("[method : getSupplier][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append("select distinct ep.supplier from equipment_product ep ");
		
		logger.info("[method : getSupplier][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		List<String> supplierList = query.list(); 
		
		return supplierList;
	}
}
