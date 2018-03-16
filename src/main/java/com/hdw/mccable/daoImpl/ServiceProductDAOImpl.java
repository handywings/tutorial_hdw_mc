package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ServiceProductDAO;
import com.hdw.mccable.entity.ServiceProduct;
import com.mysql.jdbc.StringUtils;

@Repository
public class ServiceProductDAOImpl implements ServiceProductDAO{
	private static final Logger logger = LoggerFactory.getLogger(ServiceProductDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public ServiceProduct getServiceProductById(Long id) {
		logger.info("[method : getServiceProductById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		ServiceProduct serviceProduct = (ServiceProduct) session.get(ServiceProduct.class, id); 
		return serviceProduct;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceProduct> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ServiceProduct> serviceProduct = (List<ServiceProduct>) session.createQuery("from ServiceProduct where isDeleted = false").list();
		return serviceProduct;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceProduct> searchByStockOrCriteria(String criteria, Long stockId){
		logger.info("[method : searchByStockOrCriteria][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceProduct internet where isDeleted = false ");

		// check criteria
		if (criteria != null && !StringUtils.isNullOrEmpty(criteria)) {
			strQuery = strQuery.append(" and internet.serviceChargeName like :criteria ");
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
		List<ServiceProduct> serviceProducts = query.list();

		return serviceProducts;
	}
	
	public void update(ServiceProduct serviceProduct) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(serviceProduct);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(ServiceProduct serviceProduct) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(serviceProduct);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(ServiceProduct serviceProduct) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		serviceProduct.setDeleted(true);
		update(serviceProduct);
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceProduct> getSetUpPoint(String[] productCode) {
		logger.info("[method : getSetUpPoint][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceProduct internet where isDeleted = false ");

		// check criteria
		if (null != productCode && productCode.length > 0) {
			strQuery = strQuery.append(" and internet.productCode in :productCode ");
		}

		logger.info("[method : getSetUpPoint][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check stock
		if (null != productCode && productCode.length > 0) {
			query.setParameterList("productCode", productCode);
		}
		
		// execute
		List<ServiceProduct> serviceProducts = query.list();

		return serviceProducts;
	}
	
	public ServiceProduct getSerivceProductByCode(String productCode) {
		logger.info("[method : delete][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append("from ServiceProduct internet where isDeleted = false and productCode = :productCode ");
		
		logger.info("[method : getSerivceProductByCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("productCode", productCode);
		ServiceProduct serviceProduct = (ServiceProduct) query.uniqueResult();
				
		return serviceProduct;
	}
	
}
