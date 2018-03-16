package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.StockDAO;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.Stock;

@Repository
public class StockDAOImpl implements StockDAO {
	private static final Logger logger = LoggerFactory.getLogger(StockDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	public Stock getStockById(Long id) {
		logger.info("[method : getStockById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Stock stock = (Stock) session.get(Stock.class, id);
		return stock;
	}

	public Stock getStockByStockName(String stockName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Stock s where s.stockName = :stockName");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("stockName", stockName);

		// execute
		Stock stock = (Stock) query.uniqueResult();

		return stock;
	}

	@SuppressWarnings("unchecked")
	public List<Stock> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Stock> stock = (List<Stock>) session.createQuery("from Stock where isDeleted = false").list();
		return stock;
	}

	@SuppressWarnings("unchecked")
	public List<Stock> findByCompany(Long companyId) {
		logger.info("[method : findByCompany][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Stock where companyId = :companyId and isDeleted = false ");

		Query query = session.createQuery(strQuery.toString());

		if (null != companyId && companyId > 0) {
			query.setLong("companyId", companyId);
		} else {
			return null;
		}

		List<Stock> stocks = query.list();

		return stocks;
	}

	public void update(Stock stock) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(stock);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(Stock stock) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(stock);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void delete(Stock stock) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		stock.setDeleted(true);
		update(stock);
	}

	public String genStockCode() throws Exception {
		logger.info("[method : genStockCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(
				"SELECT LPAD((SELECT COUNT(id)+1 FROM stock), 7, '0') as stockCode");
		Object obj = query.uniqueResult();
		if (null != obj) {
			logger.info("genStockCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Stock> findAllOrderCompany() {
		logger.info("[method : findAllOrderCompany][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Stock> stock = (List<Stock>) session.createQuery("from Stock where isDeleted = false order by company.id").list();
		return stock;
	}

}
