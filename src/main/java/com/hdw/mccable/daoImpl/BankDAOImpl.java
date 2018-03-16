package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdw.mccable.dao.BankDAO;
import com.hdw.mccable.entity.Bank;

public class BankDAOImpl implements BankDAO{
	
private static final Logger logger = LoggerFactory.getLogger(BankDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
///////////////////////// implement method ///////////////////////////
	@SuppressWarnings("unchecked")
	public List<Bank> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		List<Bank> bankList = (List<Bank>) session.createQuery("from Bank where 1=1 ").list();
		return bankList;
	}

	public Bank getBankById(Long id) {
		logger.info("[method : getBankById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Bank b where b.id = :id");
		logger.info("[method : getBankById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Bank bank = (Bank) query.uniqueResult();

		return bank;
	}

}
