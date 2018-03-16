package com.hdw.mccable.daoImpl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.EquipmentProductItemDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;

@Repository
public class EquipmentProductItemDAOImpl implements EquipmentProductItemDAO{
	private static final Logger logger = LoggerFactory.getLogger(EquipmentProductItemDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public EquipmentProductItem getEquipmentProductItemById(Long id) {
		logger.info("[method : getEquipmentProductItemById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		EquipmentProductItem equipmentProductItem = (EquipmentProductItem) session.get(EquipmentProductItem.class, id); 
		return equipmentProductItem;
	}
	
	public EquipmentProductItem getEquipmentProductItemBySerialNo(String serialNo) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProductItem eqi where eqi.serialNo = :serialNo ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("serialNo", serialNo);

		// execute		
		EquipmentProductItem equipmentProductItem = (EquipmentProductItem) query.uniqueResult();

		return equipmentProductItem;
	}
	
	public void update(EquipmentProductItem iquipmentProductItem) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(iquipmentProductItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void save(EquipmentProductItem equipmentProductItem) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.save(equipmentProductItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSN(ServiceApplication serviceApplication) {
		logger.info("[method : loadEquipmentProductItemHasSN][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("SELECT piw.* FROM product_item_worksheet piw JOIN product_item pi on piw.productItemId = pi.id ");
		strQuery = strQuery.append(" JOIN worksheet ws on ws.id = pi.worksheetId ");
		strQuery = strQuery.append(" JOIN service_application sa on sa.id = ws.serviceApplicationId ");
		strQuery = strQuery.append(" JOIN equipment_product_item epi on epi.id = piw.equipmentProductItemId ");
		strQuery = strQuery.append(" WHERE sa.id = :serviceApplicationId and piw.productType = 'E' and  epi.isDeleted = false and epi.serialNo <> '' ");
		logger.info("[method : loadEquipmentProductItemHasSN][Query : " + strQuery.toString() + "]");
		
		SQLQuery query = session.createSQLQuery(strQuery.toString());
	
		// prepare statement
		query.addEntity(ProductItemWorksheet.class);
		query.setLong("serviceApplicationId", serviceApplication.getId());
		
		// execute
		List<ProductItemWorksheet> productItemWorksheets = query.list();
				
		return productItemWorksheets;
	}

	@SuppressWarnings("unchecked")
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSNAllStatus(ServiceApplication serviceApplication) {
		logger.info("[method : loadEquipmentProductItemHasSN][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("SELECT piw.* FROM product_item_worksheet piw JOIN product_item pi on piw.productItemId = pi.id ");
		strQuery = strQuery.append(" JOIN worksheet ws on ws.id = pi.worksheetId ");
		strQuery = strQuery.append(" JOIN service_application sa on sa.id = ws.serviceApplicationId ");
		strQuery = strQuery.append(" JOIN equipment_product_item epi on epi.id = piw.equipmentProductItemId ");
		strQuery = strQuery.append(" WHERE sa.id = :serviceApplicationId and piw.productType = 'E' and epi.serialNo <> '' ");
		strQuery = strQuery.append(" AND epi.status in (1,2,3,5) ");
		logger.info("[method : loadEquipmentProductItemHasSN][Query : " + strQuery.toString() + "]");
		
		SQLQuery query = session.createSQLQuery(strQuery.toString());
	
		// prepare statement
		query.addEntity(ProductItemWorksheet.class);
		query.setLong("serviceApplicationId", serviceApplication.getId());
		
		// execute
		List<ProductItemWorksheet> productItemWorksheets = query.list();
				
		return productItemWorksheets;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductItemWorksheet> loadInternetProductItemByserviceApplicationId(ServiceApplication serviceApplication) {
		logger.info("[method : loadInternetProductItemByserviceApplicationId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("SELECT piw.* FROM product_item_worksheet piw JOIN product_item pi on piw.productItemId = pi.id ");
		strQuery = strQuery.append(" JOIN worksheet ws on ws.id = pi.worksheetId ");
		strQuery = strQuery.append(" JOIN service_application sa on sa.id = ws.serviceApplicationId ");
		strQuery = strQuery.append(" JOIN internet_product_item ipi on ipi.id = piw.internetProductItemId ");
		strQuery = strQuery.append(" WHERE sa.id = :serviceApplicationId and piw.productType = 'I' ");
		logger.info("[method : loadInternetProductItemByserviceApplicationId][Query : " + strQuery.toString() + "]");
		
		SQLQuery query = session.createSQLQuery(strQuery.toString());
	
		// prepare statement
		query.addEntity(ProductItemWorksheet.class);
		query.setLong("serviceApplicationId", serviceApplication.getId());
		
		// execute
		List<ProductItemWorksheet> productItemWorksheets = query.list();
				
		return productItemWorksheets;
	}
	
}
