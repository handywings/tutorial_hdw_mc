package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.CompanyDAO;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Document;

@Repository
public class CompanyDAOImpl implements CompanyDAO{
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public Company getCompanyById(Long id) {
		logger.info("[method : getCompanyById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		Company company = (Company) session.get(Company.class, id); 
		return company;
	}

	@SuppressWarnings("unchecked")
	public List<Company> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Company> companys = (List<Company>) session.createQuery("from Company where isDeleted = false").list();
		return companys;
	}
	
	public void update(Company company) throws Exception {
		logger.info("[method : update][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(company);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(Company company) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(company);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(Company company) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(company);
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Company getParentCompanyById() {
		logger.info("[method : getParentCompanyById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Company c where c.isDeleted = false and c.parent = 0");
		logger.info("[method : getParentCompanyById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// execute
		Company company = (Company) query.uniqueResult();

		return company;
	}
	
	public List<Document> findDocumentAll() {
		logger.info("[method : findDocumentAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Document> documentList = (List<Document>) session.createQuery("from Document ").list();
		return documentList;
	}
	
	public void save(Document document) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.save(document);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Document getDocumentById(Long documentId) {
		logger.info("[method : getDocumentById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		Document document = (Document) session.get(Document.class, documentId); 
		return document;
	}
	
	public void update(Document document) throws Exception {
		logger.info("[method : update][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(document);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
}
