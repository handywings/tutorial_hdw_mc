package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ProductItemDAO;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;

@Repository
public class ProductItemDAOImpl implements ProductItemDAO{
	private static final Logger logger = LoggerFactory.getLogger(ProductItemDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public ProductItem getProductItemById(Long id) {
		logger.info("[method : getProductItemById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		ProductItem productItem = (ProductItem) session.get(ProductItem.class, id); 
		return productItem;
	}
	
	public void update(ProductItem productItem) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(productItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(ProductItem productItem) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(productItem);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void deleteByServiceApplicationId(Long serviceApplicationId) {
		logger.info("[method : deleteByServiceApplicationId][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ProductItem p where p.serviceApplication.id = :serviceApplicationId");
		logger.info("[method : deleteByServiceApplicationId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("serviceApplicationId", serviceApplicationId);

		// execute
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductItem> getProductItemByServiceApplicationId(Long serviceApplicationId) throws Exception {
		logger.info("[method : getProductItemByServiceApplicationId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("from ProductItem p where p.isDeleted = false ");
		strQuery = strQuery.append("and p.serviceApplication.id = :serviceApplicationId ");
		
		logger.info("[method : getProductItemByServiceApplicationId][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());
		
		query.setLong("serviceApplicationId", serviceApplicationId);
		
		//execute
		List<ProductItem> productItems = query.list();
		return productItems;
	}
	public void deleteByServiceApplicationIdAndWorksheet(Long serviceApplicationId) {
		logger.info("[method : deleteByServiceApplicationIdAndWorksheet][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ProductItem p where p.serviceApplication.id = :serviceApplicationId ");
		strQuery = strQuery.append("and p.workSheet == null or p.workSheet.workSheetType == 'C_S' ");
		logger.info("[method : deleteByServiceApplicationIdAndWorksheet][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("serviceApplicationId", serviceApplicationId);

		// execute
		query.executeUpdate();
	}

	public ProductItemWorksheet getProductItemWorksheetByEquipmentProductItemId(Long id) {
		logger.info("[method : getProductItemWorksheetByEquipmentProductItemId][Type : DAO][id : "+id+"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("from ProductItemWorksheet p where p.isDeleted = false ");
		strQuery = strQuery.append("and p.equipmentProductItem.id = :id and p.productType='E' ");
		
		logger.info("[method : getProductItemWorksheetByEquipmentProductItemId][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());
		
		query.setLong("id", id);
		
		//execute
		ProductItemWorksheet productItemWorksheet = (ProductItemWorksheet) query.uniqueResult();
		return productItemWorksheet;
	}

	
	public void deleteWorksheetIdAndProductType(Long worksheetId, String productType) {
		logger.info("[method : deleteWorksheetIdAndProductType][Type : DAO]");
		logger.info("[worksheetId : "+worksheetId+"]");
		logger.info("[productType : "+productType+"]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ProductItem p where p.workSheet.id = :worksheetId ");
		strQuery = strQuery.append("and p.productType = :productType ");
		logger.info("[method : deleteWorksheetIdAndProductType][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("worksheetId", worksheetId);
		query.setString("productType", productType);

		// execute
		int rows = query.executeUpdate();
		logger.info("deleteWorksheetIdAndProductType rows : "+rows);
	}
	
	public void deleteProductItemById(Long productItemId) throws Exception {
		logger.info("[method : deleteProductItemById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ProductItem p where p.id = :productItemId ");
		logger.info("[method : deleteProductItemById][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("productItemId", productItemId);

		// execute
		query.executeUpdate();
		
	}
	
}
