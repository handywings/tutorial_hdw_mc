package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ServicePackageTypeDAO;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServicePackageType;

@Repository
public class ServicePackageTypeDAOImpl implements ServicePackageTypeDAO{
	private static final Logger logger = LoggerFactory.getLogger(ServicePackageTypeDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public ServicePackageType getServicePackageTypeById(Long id) {
		logger.info("[method : getServicePackageTypeById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		ServicePackageType servicePackageType = (ServicePackageType) session.get(ServicePackageType.class, id); 
		return servicePackageType;
	}

	@SuppressWarnings("unchecked")
	public List<ServicePackageType> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ServicePackageType> servicePackageType = (List<ServicePackageType>) session.createQuery("from ServicePackageType where isDeleted = false").list();
		return servicePackageType;
	}
	
	public void update(ServicePackageType servicePackageType) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(servicePackageType);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(ServicePackageType servicePackageType) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(servicePackageType);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void delete(ServicePackageType servicePackageType) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		servicePackageType.setDeleted(true);
		update(servicePackageType);
	}
	
	public String genPackageTypeCode() throws Exception {
		logger.info("[method : genPackageTypeCode][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT LPAD((SELECT COUNT(id)+1 FROM service_package_type), 5, '0') as packageTypeCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("PackageTypeCode : "+obj.toString());
			return obj.toString();
		}
		return null;
	}
	
	public int getNumberOfCountPackage(Long servicePackageTypeID) throws Exception {
		logger.info("[method : getNumberOfCountPackage][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT COUNT(*) AS count_num FROM `cabledb`.`service_package` WHERE ServicePackageTypeId = :param");
		query.setLong("param", servicePackageTypeID);
		
		int count = 0;
		
		Object obj = query.uniqueResult();
		if(obj != null){
			count = Integer.valueOf(obj.toString());
		}
		logger.info("[method : getNumberOfCountPackage : "+count+"]");
		return count;
	}
	
	public ServicePackageType getServicePackageTypeByPackageTypeCode(String packageTypeCode) throws Exception {
		logger.info("[method : getServicePackageTypeByPackageTypeCode][Type : DAO][packageTypeCode : "+packageTypeCode+"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("from ServicePackageType spt where spt.isDeleted = false and spt.packageTypeCode = :packageTypeCode ");
		
		logger.info("[method : getServicePackageTypeByPackageTypeCode][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());
		
		query.setString("packageTypeCode", packageTypeCode);
		
		//execute
		ServicePackageType servicePackageType = (ServicePackageType) query.uniqueResult();
		return servicePackageType;
	}
	
	public ServicePackageType getServicePackageTypeByPackageTypeName(String packageTypeName) throws Exception {
		logger.info("[method : getServicePackageTypeByPackageTypeName][Type : DAO][packageTypeName : "+packageTypeName+"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("from ServicePackageType spt where spt.isDeleted = false and spt.packageTypeName = :packageTypeName ");
		
		logger.info("[method : getServicePackageTypeByPackageTypeName][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());
		
		query.setString("packageTypeName", packageTypeName);
		
		//execute
		ServicePackageType servicePackageType = (ServicePackageType) query.uniqueResult();
		return servicePackageType;
	}
	
}
