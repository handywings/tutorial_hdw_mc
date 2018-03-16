package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.InternetProductDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.mysql.jdbc.StringUtils;

@Repository
public class InternetProductDAOImpl implements InternetProductDAO{
	private static final Logger logger = LoggerFactory.getLogger(InternetProductDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public InternetProduct getInternetProductById(Long id) {
		logger.info("[method : getInternetProductById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		InternetProduct internetProduct = (InternetProduct) session.get(InternetProduct.class, id); 
		return internetProduct;
	}

	public InternetProduct getInternetProductByProductName(String productName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProduct i where i.productName = :productName ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productName", productName);

		// execute		
		InternetProduct internetProduct = (InternetProduct) query.uniqueResult();

		return internetProduct;
	}
	
	@SuppressWarnings("unchecked")
	public List<InternetProduct> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InternetProduct> zone = (List<InternetProduct>) session.createQuery("from InternetProduct where isDeleted = false").list();
		return zone;
	}
	
	@SuppressWarnings("unchecked")
	public List<InternetProduct> searchByStockOrCriteria(String criteria, Long stockId){
		logger.info("[method : searchByStockOrCriteria][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProduct internet where isDeleted = false ");

		// check criteria
		if (criteria != null && !StringUtils.isNullOrEmpty(criteria)) {
			strQuery = strQuery.append(" and (internet.productName like :criteria ");
			strQuery = strQuery.append(" or internet.productCode like :criteria) ");
		}
		
		//check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append(" and internet.stock.id = :stockId ");
		}

		logger.info("[method : searchByStockOrCriteria][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		
		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}

		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}
		
		// execute
		List<InternetProduct> internetProducts = query.list();

		return internetProducts;
	}
	
	public void update(InternetProduct internetProduct) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(internetProduct);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(InternetProduct internetProduct) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(internetProduct);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(InternetProduct internetProduct) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		internetProduct.setDeleted(true);
		update(internetProduct);
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkDubplicateUsername(String userName) throws Exception {
		logger.info("[method : checkDubplicateUsername][Type : DAO]");
		boolean checkDup = false;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProductItem item where item.userName = :userName");

		logger.info("[method : checkDubplicateUsername][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("userName",userName);
		// execute
		List<InternetProductItem> internetProductItemList = query.list();
		if(internetProductItemList.size() > 0) checkDup = true;
			
		return checkDup;
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkDuplicateUsernameForUpdate(String userName, Long id) throws Exception {
		logger.info("[method : checkDuplicateUsernameForUpdate][Type : DAO]");
		boolean checkDup = false;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProductItem item where item.userName = :userName and item.id <> :id ");

		logger.info("[method : checkDubplicateUsername][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("userName",userName);
		query.setLong("id",id);
		// execute
		List<InternetProductItem> internetProductItemList = query.list();
		if(internetProductItemList.size() > 0) checkDup = true;
			
		return checkDup;
	}
	
}
