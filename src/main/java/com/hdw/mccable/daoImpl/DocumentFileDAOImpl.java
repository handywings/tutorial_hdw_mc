package com.hdw.mccable.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.DocumentFileDAO;
import com.hdw.mccable.entity.DocumentFile;

@Repository
public class DocumentFileDAOImpl implements DocumentFileDAO{
	private static final Logger logger = LoggerFactory.getLogger(DocumentFileDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public DocumentFile getDocumentFileById(Long id) {
		logger.info("[method : getDocumentFileById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		DocumentFile documentFile = (DocumentFile) session.get(DocumentFile.class, id); 
		return documentFile;
	}
	
	public void update(DocumentFile documentFile) throws Exception {
		logger.info("[method : update][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(documentFile);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(DocumentFile documentFile) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(documentFile);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
