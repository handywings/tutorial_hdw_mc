package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ServicePackageDAO;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.TemplateService;
import com.hdw.mccable.entity.Zone;

@Repository
public class ServicePackageDAOImpl implements ServicePackageDAO {

	private static final Logger logger = LoggerFactory.getLogger(ServicePackageDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	// ------------------------------------ implement ---------------------------------------------//

	@SuppressWarnings("unchecked")
	public List<ServicePackage> searchBykey(String criteria, String servicePackageTypeCode, String status,
			Long companyId) {
		logger.info("[method : searchBykey][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		// key
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery
					.append("from ServicePackage sp where sp.isDeleted = false and (sp.packageCode like :criteria ");
			strQuery = strQuery.append("or sp.packageName like :criteria) ");
		} else {
			strQuery = strQuery.append("from ServicePackage sp where sp.isDeleted = false ");
		}

		// service package type code
		if (servicePackageTypeCode != null && (!servicePackageTypeCode.isEmpty())) {
			strQuery = strQuery.append("and sp.servicePackageType.packageTypeCode = :servicePackageTypeCode ");
		}

		// status
		if (status != null && (!status.isEmpty())) {
			if (Integer.valueOf(status).equals(1)) {
				strQuery = strQuery.append("and sp.isActive = true ");

			} else if (Integer.valueOf(status).equals(0)) {
				strQuery = strQuery.append("and sp.isActive = false ");
			}

		}

		// company
		if (companyId > 0L) {
			strQuery = strQuery.append("and sp.company.id = :companyId ");
		}

		logger.info("[method : searchBykey][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());

		// prepare statement
		// key
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}

		// service package type code
		if (servicePackageTypeCode != null && (!servicePackageTypeCode.isEmpty())) {
			query.setString("servicePackageTypeCode", servicePackageTypeCode);
		}
		// company
		if (companyId > 0L) {
			query.setLong("companyId", companyId);
		}

		// execute
		List<ServicePackage> servicePackages = query.list();

		return servicePackages;
	}

	public ServicePackage getServicePackageById(Long id) {
		logger.info("[method : getServicePackageById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServicePackage sp where sp.isDeleted = false and sp.id = :id");
		logger.info("[method : getServicePackageById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		ServicePackage servicePackage = (ServicePackage) query.uniqueResult();
		return servicePackage;
	}

	public Long save(ServicePackage servicePackage) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(servicePackage);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void update(ServicePackage servicePackage) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(servicePackage);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteTemplateItem(Long templateId) throws Exception {
		logger.info("[method : deleteTemplateItem][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete TemplateServiceItem  where templateService.id = :id ");
			logger.info("[method : deleteTemplateItem][Query : " + strQuery.toString() + "]");

			// prepare statement
			Query query = session.createQuery(strQuery.toString());
			query.setLong("id", templateId);

			// execute
			query.executeUpdate();
		} catch (Exception ex) {
			throw (ex);
		}
	}

	public TemplateService getTemplateService(Long id) {
		logger.info("[method : getTemplateService][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from TemplateService where isDeleted = false and id = :id");
		logger.info("[method : getTemplateService][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		TemplateService templateService = (TemplateService) query.uniqueResult();
		return templateService;
	}

	public void updateTemplate(TemplateService templateService) throws Exception {
		logger.info("[method : updateTemplate][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(templateService);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ServicePackage> findServicePackageByServicePackageTypeId(Long servicePackageTypeId) {
		logger.info("[method : findServicePackageByServicePackageTypeId][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		// servicePackageTypeId
		if (servicePackageTypeId > 0L) {
			strQuery = strQuery.append("from ServicePackage sp where sp.isDeleted = false and ");
			strQuery = strQuery.append("sp.servicePackageType.id = :servicePackageTypeId and ");
			strQuery = strQuery.append("sp.isActive = 1 ");
		}

		logger.info("[method : findServicePackageByServicePackageTypeId][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());

		// prepare statement

		// key
		if (servicePackageTypeId > 0L) {
			query.setLong("servicePackageTypeId", servicePackageTypeId);
		}

		// execute
		List<ServicePackage> servicePackages = query.list();

		return servicePackages;
	}

	@SuppressWarnings("unchecked")
	public List<ServicePackage> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<ServicePackage> servicePackages = (List<ServicePackage>) session.createQuery("from ServicePackage where isDeleted = false").list();
		return servicePackages;
	}

	public ServicePackage getServicePackageByCode(String packageCode) {
		if(!"".equals(packageCode)){
			logger.info("[method : getServicePackageByCode][Type : DAO][Input : "+packageCode+"]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT sp.* FROM service_package sp where sp.packageCode = :packageCode ");
			
			logger.info("[method : getServicePackageByCode][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("packageCode", packageCode);	
			query.addEntity(ServicePackage.class);
			//execute
			List<ServicePackage> servicePackage = query.list();
			if(null != servicePackage && servicePackage.size() > 0){
				return servicePackage.get(0);
			}
		}
		return null;
	}

	public String genServicePackageCode() {
		logger.info("[method : genServicePackageCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('CABLE',LPAD((SELECT COUNT(id)+1 FROM service_package), 6, '0')) as serviceApplicationCode ");
		Object obj = query.uniqueResult();
		if(null != obj){		
			logger.info("genServicePackageCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}

	public ServicePackage getServicePackageByName(String packageName) {
		if(!"".equals(packageName)){
			logger.info("[method : getServicePackageByName][Type : DAO][Input : "+packageName+"]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT sp.* FROM service_package sp where sp.packageName = :packageName ");
			
			logger.info("[method : getServicePackageByName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("packageName", packageName);	
			query.addEntity(ServicePackage.class);
			//execute
			List<ServicePackage> servicePackage = query.list();
			if(null != servicePackage && servicePackage.size() > 0){
				return servicePackage.get(0);
			}
		}
		return null;
	}
	
	public void saveTemplate(TemplateService templateService) throws Exception {
		logger.info("[method : saveTemplate][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(templateService);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
