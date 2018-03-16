package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ProductItemWorksheetDAO;
import com.hdw.mccable.entity.ProductItemWorksheet;

@Repository
public class ProductItemWorksheetDAOImpl implements ProductItemWorksheetDAO{
	private static final Logger logger = LoggerFactory.getLogger(ProductItemWorksheetDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public ProductItemWorksheet getProductItemWorksheetById(Long id) {
		logger.info("[method : getProductItemWorksheetById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		ProductItemWorksheet productItemWorksheet = (ProductItemWorksheet) session.get(ProductItemWorksheet.class, id); 
		return productItemWorksheet;
	}
	
	public void update(ProductItemWorksheet productItemWorksheet) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(productItemWorksheet);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(ProductItemWorksheet productItemWorksheet) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(productItemWorksheet);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void deleteByProductItemId(Long productItemId) {
		logger.info("[method : deleteByProductItemId][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ProductItemWorksheet p where p.productItem.id = :productItemId");
		logger.info("[method : deleteByProductItemId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("productItemId", productItemId);

		// execute
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductItemWorksheet> getProductItemWorksheetByProductItemId(Long productItemId) throws Exception {
		logger.info("[method : getProductItemWorksheetByProductItemId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("from ProductItemWorksheet p where p.isDeleted = false ");
		strQuery = strQuery.append("and p.productItem.id = :productItemId ");
		
		logger.info("[method : getProductItemWorksheetByProductItemId][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());
		
		query.setLong("productItemId", productItemId);
		
		//execute
		List<ProductItemWorksheet> productItemWorksheets = query.list();
		return productItemWorksheets;
	}
	
}
