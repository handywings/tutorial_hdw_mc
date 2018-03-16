package com.hdw.mccable.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.InternetProductItemDAO;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;

@Repository
public class InternetProductItemDAOImpl implements InternetProductItemDAO{
	private static final Logger logger = LoggerFactory.getLogger(InternetProductItemDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public InternetProductItem getInternetProductItemById(Long id) {
		logger.info("[method : getInternetProductItemById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		InternetProductItem internetProductItem = (InternetProductItem) session.get(InternetProductItem.class, id); 
		return internetProductItem;
	}
	
	public void update(InternetProductItem internetProductItem) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(internetProductItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(InternetProductItem internetProductItem) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(internetProductItem);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	public InternetProductItem getInternetProductItemByUserNameOrPassword(String userName, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProductItem i where ( i.userName = :userName and i.password = :password ) ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("userName", userName);
		query.setString("password", password);

		// execute		
		InternetProductItem internetProductItem = (InternetProductItem) query.uniqueResult();

		return internetProductItem;
	}
	
}
