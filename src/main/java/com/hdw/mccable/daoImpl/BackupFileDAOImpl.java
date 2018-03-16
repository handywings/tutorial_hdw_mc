package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.BackupFileDAO;
import com.hdw.mccable.entity.BackupFile;

@Repository
public class BackupFileDAOImpl implements BackupFileDAO{
	
private static final Logger logger = LoggerFactory.getLogger(BackupFileDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	///////////////////////// implement method ///////////////////////////
	@SuppressWarnings("unchecked")
	public List<BackupFile> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<BackupFile> backupFileList = (List<BackupFile>) session.createQuery("from BackupFile ").list();
		return backupFileList;
	}

	public BackupFile findById(Long id) {
		logger.info("[method : findById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		BackupFile backupFile = (BackupFile) session.get(BackupFile.class, id);
		return backupFile;
	}

	public Long save(BackupFile backupFile) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(backupFile);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
}
