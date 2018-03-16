package com.hdw.mccable.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.ServiceApplicationDAO;
import com.hdw.mccable.dto.ApplicationSearchBean;
import com.hdw.mccable.dto.DigitalAnalogBean;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationAssignCashier;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.utils.Pagination;

@Repository
public class ServiceApplicationDAOImpl implements ServiceApplicationDAO{
	private static final Logger logger = LoggerFactory.getLogger(ServiceApplicationDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	public ServiceApplication getServiceApplicationById(Long id) {
		logger.info("[method : getServiceApplicationById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceApplication sa where sa.isDeleted = false and sa.id = :id and sa.customer.isDeleted = false");
		logger.info("[method : getServiceApplicationById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		ServiceApplication serviceApplication = (ServiceApplication) query.uniqueResult();
		return serviceApplication;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceApplication> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ServiceApplication> serviceApplication = (List<ServiceApplication>) session.createQuery("from ServiceApplication where isDeleted = false and ServiceApplication.customer.isDeleted = false").list();
		return serviceApplication;
	}
	
	public void update(ServiceApplication serviceApplication) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(serviceApplication);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(ServiceApplication serviceApplication) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(serviceApplication);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public int getChangeserviceCountTotal(ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getChangeserviceCountTotal][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(applicationSearchBean != null){			
			strQuery = strQuery.append(" select count(sa.id) from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" join worksheet w on w.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where sa.isDeleted = false and w.workSheetType = 'C_S' and cust.isDeleted = false ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			// dateOrderBill
			if (applicationSearchBean.getBillPaymentDateSearch() != null && (!applicationSearchBean.getBillPaymentDateSearch().isEmpty())) {
				strQuery = strQuery.append(" and DATE(w.dateOrderBill) = :dateOrderBill ");
			}

			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			Query query = session.createSQLQuery(strQuery.toString());
			
			//key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			//package service type
			if(applicationSearchBean.getServicePackageType() != null 
					&& applicationSearchBean.getServicePackageType() > 0){
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			//package service
			if (applicationSearchBean.getServicePackage() != null
					&& applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			// getBillPaymentDateSearch
			if (applicationSearchBean.getBillPaymentDateSearch() != null && (!applicationSearchBean.getBillPaymentDateSearch().isEmpty())) {
				SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
				try {
					query.setDate("dateOrderBill", formatDataTh.parse(applicationSearchBean.getBillPaymentDateSearch()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
		}else{
			return count;
		}
		return count;
	}
	
	public int getCountTotalByApplicationSearchBean(ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getCountTotalByApplicationSearchBean][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(applicationSearchBean != null){
			strQuery = strQuery.append(" select count(sa.id) from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" left join service_application_assign_cashier spac on spac.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			if(null == applicationSearchBean.getPersonnelId() || (applicationSearchBean.getPersonnelId() <= 0)){
				strQuery = strQuery.append(" and spac.personnelId is null ");
			}
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}

			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				strQuery = strQuery.append(" and spac.personnelId = :personnelId ");
			}
			
			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			Query query = session.createSQLQuery(strQuery.toString());
			
			//key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			//package service type
			if(applicationSearchBean.getServicePackageType() != null 
					&& applicationSearchBean.getServicePackageType() > 0){
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			//package service
			if (applicationSearchBean.getServicePackage() != null
					&& applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				query.setLong("personnelId", applicationSearchBean.getPersonnelId());
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
		}else{
			return count;
		}
		return count;
	}
	
	public int getCountTotal(ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getCountTotal][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(applicationSearchBean != null){			
			strQuery = strQuery.append(" select count(sa.id) from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}

			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			Query query = session.createSQLQuery(strQuery.toString());
			
			//key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			//package service type
			if(applicationSearchBean.getServicePackageType() != null 
					&& applicationSearchBean.getServicePackageType() > 0){
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			//package service
			if (applicationSearchBean.getServicePackage() != null
					&& applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
		}else{
			return count;
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public Pagination getChangeserviceByPage(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getChangeserviceByPage][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		if (applicationSearchBean != null) {
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" join worksheet w on w.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where sa.isDeleted = false and w.workSheetType = 'C_S' and cust.isDeleted = false ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			// dateOrderBill
			if (applicationSearchBean.getBillPaymentDateSearch() != null && (!applicationSearchBean.getBillPaymentDateSearch().isEmpty())) {
				strQuery = strQuery.append(" and DATE(w.dateOrderBill) = :dateOrderBill ");
			}
			
			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : getChangeserviceByPage][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			// getBillPaymentDateSearch
			if (applicationSearchBean.getBillPaymentDateSearch() != null && (!applicationSearchBean.getBillPaymentDateSearch().isEmpty())) {
				SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
				try {
					query.setDate("dateOrderBill", formatDataTh.parse(applicationSearchBean.getBillPaymentDateSearch()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			// execute
			List<ServiceApplication> serviceApplications = query.list();
			pagination.setDataList(serviceApplications);
			
		} else {
			return pagination;
		}
		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	public Pagination getByPageByApplicationSearchBean(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getByPageByApplicationSearchBean][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		if (applicationSearchBean != null) {
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" left join service_application_assign_cashier spac on spac.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			if(null == applicationSearchBean.getPersonnelId() || (applicationSearchBean.getPersonnelId() <= 0)){
				strQuery = strQuery.append(" and spac.personnelId is null ");
			}
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}

			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				strQuery = strQuery.append(" and spac.personnelId = :personnelId ");
			}
			
			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : getByPage][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				query.setLong("personnelId", applicationSearchBean.getPersonnelId());
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			// execute
			List<ServiceApplication> serviceApplications = query.list();
			pagination.setDataList(serviceApplications);
			
		} else {
			return pagination;
		}
		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	public Pagination getByPage(Pagination pagination, ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getByPage][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		if (applicationSearchBean != null) {
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}

			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : getByPage][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			// execute
			List<ServiceApplication> serviceApplications = query.list();
			pagination.setDataList(serviceApplications);
			
		} else {
			return pagination;
		}
		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceApplication> searchServiceApplicationByApplicationSearchBean(ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : searchServiceApplicationByApplicationSearchBean][Type : DAO]");
		logger.info("applicationSearchBean : "+applicationSearchBean.toString());
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" left join service_application_assign_cashier spac on spac.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			if(null == applicationSearchBean.getPersonnelId() || (applicationSearchBean.getPersonnelId() <= 0)){
				strQuery = strQuery.append(" and spac.personnelId is null ");
			}
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria or ad.no like :criteria) ");
			}

			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				strQuery = strQuery.append(" and sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				strQuery = strQuery.append(" and sp.id = :servicePackageId ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			//zone for append
			if(applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				strQuery = strQuery.append(" and spac.personnelId = :personnelId ");
			}

			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : searchServiceApplicationByApplicationSearchBean][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			// package service type
			if (applicationSearchBean.getServicePackageType() != null
					&& applicationSearchBean.getServicePackageType() > 0) {
				query.setLong("servicePackageTypeId", applicationSearchBean.getServicePackageType());
			}
			
			// package servicePackageId
			if (applicationSearchBean.getServicePackage() != null && applicationSearchBean.getServicePackage() > 0) {
				query.setLong("servicePackageId", applicationSearchBean.getServicePackage());
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			// zone
			if (applicationSearchBean.getZone() != null && (applicationSearchBean.getZone() > 0)) {
				query.setLong("zoneId", applicationSearchBean.getZone());
			}

			//service_application_assign_cashier
			if(applicationSearchBean.getPersonnelId() != null && (applicationSearchBean.getPersonnelId() > 0)){
				query.setLong("personnelId", applicationSearchBean.getPersonnelId());
			}
			
		// execute
		return query.list();
	}
	
	public String genServiceApplicationCode() {
		logger.info("[method : genServiceApplicationCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('CONT-',LPAD((SELECT COUNT(id)+1 FROM service_application), 6, '0')) as serviceApplicationCode ");
		Object obj = query.uniqueResult();
		if(null != obj){			
			logger.info("genServiceApplicationCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceApplication> search(String key, String customerType, Long customerFeatures) {
		logger.info("[method : search][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		//key 
//		if (key != null && (!key.isEmpty())) {
//			strQuery = strQuery.append("from ServiceApplication s where ");
//			strQuery = strQuery.append(" s.isDeleted = false and s.customer.isDeleted = false and s.status = 'A' and (s.serviceApplicationNo like :criteria ");
//			strQuery = strQuery.append("or s.customer.custCode like :criteria ");
//			strQuery = strQuery.append("or s.customer.firstName like :criteria or s.customer.lastName like :criteria or s.customer.identityNumber like :criteria or s.customer.contact.email like :criteria or s.customer.contact.mobile like :criteria ) ");	
//		} else {
//			strQuery = strQuery.append(" from ServiceApplication s where 1=1 and s.isDeleted = false and (s.status = 'A' or s.status = 'I') ");
//		}
		
		strQuery = strQuery.append(" select sa.* from service_application sa ");
		strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
		strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
		strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
		strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
//		strQuery = strQuery.append(" and sa.status = 'A' ");
		//customer type
		if (customerType != null && (!customerType.isEmpty())) {
			strQuery = strQuery.append(" and cust.custType = :customerType ");
		}
		
		//cust feature
		if(customerFeatures > 0){
			strQuery = strQuery.append(" and cust.customerFeatureTypeId = :custFeature ");
		}
		
		//zone for append
		if (key != null && (!key.isEmpty())) {
			strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
			strQuery = strQuery.append(" or cust.custCode like :criteria ");
			strQuery = strQuery.append(" or cust.firstName like :criteria ");	
			strQuery = strQuery.append(" or cust.lastName like :criteria ");
			strQuery = strQuery.append(" or cust.identityNumber like :criteria ");
			strQuery = strQuery.append(" or cont.email like :criteria ");
			strQuery = strQuery.append(" or cont.mobile like :criteria ");
			strQuery = strQuery.append(" or ad.no like :criteria ) ");
		}
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
		
		//prepare statement
		logger.info("[method : search][Query : " + strQuery.toString() + "]");
//		Query query = session.createQuery(strQuery.toString());
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		query.addEntity(ServiceApplication.class);
		// key
		if (key != null && (!key.isEmpty())) {
			query.setString("criteria", "%" + key + "%");
		}
		
		//customer type
		if (customerType != null && (!customerType.isEmpty())) {
			query.setString("customerType", customerType);
		}
		
		//cust feature
		if(customerFeatures > 0){
			query.setLong("custFeature", customerFeatures);
		}
		
		//execute
		List<ServiceApplication> serviceApplications = query.list();
		
		return serviceApplications;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceApplicationType> findAllServiceApplicationType() {
		logger.info("[method : ServiceApplicationType][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<ServiceApplicationType> serviceApplicationTypes = (List<ServiceApplicationType>) session.createQuery("from ServiceApplicationType").list();
		return serviceApplicationTypes;
	}
	
	
	public ServiceApplicationType getServiceApplicationTypeById(Long id) {
		logger.info("[method : getServiceApplicationTypeById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceApplicationType sat where sat.id = :id");
		logger.info("[method : getServiceApplicationTypeById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		ServiceApplicationType serviceApplicationType = (ServiceApplicationType) query.uniqueResult();
		return serviceApplicationType;
	}
	
	public List<ServiceApplication> searchServiceApplicationByStatus(ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : getByPage][Type : DAO]");
		String startDate = "";
		String endDate = "";
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria) ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			// cancel service date
			SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", new Locale("TH", "th"));
			if(applicationSearchBean.getDateRangeRefund() != null && (!applicationSearchBean.getDateRangeRefund().isEmpty())){
				String cancelServiceDate = applicationSearchBean.getDateRangeRefund();
				if(!"".equals(cancelServiceDate)){
					String[] cancelServiceDates = cancelServiceDate.split(" - ");
					if(cancelServiceDates.length > 1){
						try {
							startDate = formatDataUs.format(formatDataTh.parse(cancelServiceDates[0]));
							endDate = formatDataUs.format(formatDataTh.parse(cancelServiceDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(sa.cancelServiceDate) BETWEEN :startDate AND :endDate) ");
			}
			
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			//cancel service date
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			
			// execute
			List<ServiceApplication> serviceApplications = query.list();

		return serviceApplications;
	}
	@SuppressWarnings("unchecked")
	public ServiceApplication getServiceApplicationByHouseNumber(String houseNumber) {
		logger.info("[method : getServiceApplicationByHouseNumber][Type : DAO]");
	
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on cust.id = sa.customerId ");
			strQuery = strQuery.append(" join address addr on addr.customerId = cust.id ");
			strQuery = strQuery.append(" where addr.no = :houseNumber and addr.addressType = 1 ");

			// prepare statement
			logger.info("[method : getServiceApplicationByHouseNumber][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// houseNumber
			query.setString("houseNumber", houseNumber);
			
			// execute
			List<ServiceApplication> serviceApplications = query.list();
			if(null != serviceApplications && serviceApplications.size() > 0){
				return serviceApplications.get(0);
			}
			
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceApplication> getListDigitalAnalogByCustomerId(Long id) {
		logger.info("[method : getListDigitalAnalogByCustomerId][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" from ServiceApplication sa ");
		strQuery = strQuery.append(" where sa.customer.id = :customerId ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// customerId
		query.setLong("customerId", id);

		// execute
		List<ServiceApplication> serviceApplications = query.list();
			
		return serviceApplications;
	}


	public void deleteByCustomerId(Long customerId) {
		logger.info("[method : deleteByCustomerId][Type : DAO]");
		logger.info("[customerId : "+customerId+"]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ServiceApplication s where s.customer.id = :customerId");
		logger.info("[method : deleteByCustomerId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("customerId", customerId);

		// execute
		query.executeUpdate();
	}

	public void save(ServiceApplicationAssignCashier serviceApplicationAssignCashier) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.save(serviceApplicationAssignCashier);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteServiceApplicationAssignCashierByPersonnel(Long personnelId) {
		logger.info("[method : deleteServiceApplicationAssignCashierByPersonnel][Type : DAO]");
		logger.info("[personnelId : "+personnelId+"]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

//		strQuery = strQuery.append("delete FROM service_application_assign_cashier where personnelId = :personnelId");
		
		strQuery = strQuery.append("delete ServiceApplicationAssignCashier s where s.pk.personnel.id = :personnelId");
		logger.info("[method : deleteServiceApplicationAssignCashierByPersonnel][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("personnelId", personnelId);

		// execute
		int rowDelete = query.executeUpdate();
		
		logger.info("rowDelete : "+rowDelete);
	}

	public void deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId(Long personnelId,
			Long serviceApplicationId) {
		logger.info("[method : deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId][Type : DAO]");
		logger.info("[personnelId : "+personnelId+"]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete ServiceApplicationAssignCashier s where s.pk.personnel.id = :personnelId ");
		strQuery = strQuery.append("and s.pk.serviceApplication.id = :serviceApplicationId ");
		logger.info("[method : deleteServiceApplicationAssignCashierByPersonnelAndServiceApplicationId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("personnelId", personnelId);
		query.setLong("serviceApplicationId", serviceApplicationId);
		
		// execute
		int rowDelete = query.executeUpdate();
		
		logger.info("rowDelete : "+rowDelete);
	}
	
	public List<Long> findServiceApplicationAssignCashierByCashier(Long cashierId) {
		logger.info("[method : getListDigitalAnalogByCustomerId][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" from ServiceApplicationAssignCashier s ");
		strQuery = strQuery.append(" where s.pk.personnel.id = :personnelId ");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// customerId
		query.setLong("personnelId", cashierId);

		// execute
		List<Long> result = new ArrayList<Long>();
		List<ServiceApplicationAssignCashier> serviceApplicationAssignCashiers = query.list();
		if(null != serviceApplicationAssignCashiers && serviceApplicationAssignCashiers.size() > 0){
			for(ServiceApplicationAssignCashier saac:serviceApplicationAssignCashiers){
				result.add(saac.getPk().getServiceApplication().getId());
			}
		}
		
		return result;
	}

	public List<ServiceApplication> searchServiceApplicationByStatusAndRefund(
			ApplicationSearchBean applicationSearchBean) {
		logger.info("[method : searchServiceApplicationByStatusAndRefund][Type : DAO]");
		String startDate = "";
		String endDate = "";
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append(" select sa.* from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append(" where sa.isDeleted = false and cust.isDeleted = false ");
			strQuery = strQuery.append(" and sa.refund > 0 ");
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and (sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cust.identityNumber like :criteria ");
				strQuery = strQuery.append(" or cont.email like :criteria or cont.mobile like :criteria) ");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				strQuery = strQuery.append(" and sa.status = :status ");
			}
			
			// cancel service date
			SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", new Locale("TH", "th"));
			if(applicationSearchBean.getDateRangeRefund() != null && (!applicationSearchBean.getDateRangeRefund().isEmpty())){
				String cancelServiceDate = applicationSearchBean.getDateRangeRefund();
				if(!"".equals(cancelServiceDate)){
					String[] cancelServiceDates = cancelServiceDate.split(" - ");
					if(cancelServiceDates.length > 1){
						try {
							startDate = formatDataUs.format(formatDataTh.parse(cancelServiceDates[0]));
							endDate = formatDataUs.format(formatDataTh.parse(cancelServiceDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(sa.cancelServiceDate) BETWEEN :startDate AND :endDate) ");
			}
			
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
			strQuery = strQuery.append(" order by sa.id desc ");
			// prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());

			query.addEntity(ServiceApplication.class);
			
			// key
			if (applicationSearchBean.getKey() != null && (!applicationSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + applicationSearchBean.getKey() + "%");
			}
			
			// status
			if (applicationSearchBean.getStatus() != null && (!applicationSearchBean.getStatus().isEmpty())) {
				query.setString("status", applicationSearchBean.getStatus());
			}
			
			//cancel service date
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			
			// execute
			List<ServiceApplication> serviceApplications = query.list();

		return serviceApplications;
	}
	
	public ServiceApplicationType getServiceApplicationTypeByName(String serviceApplicationType) {
		logger.info("[method : getServiceApplicationTypeByName][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceApplicationType sat where sat.serviceApplicationTypeName = :serviceApplicationTypeName");
		logger.info("[method : getServiceApplicationTypeByName][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("serviceApplicationTypeName", serviceApplicationType);

		// execute
		ServiceApplicationType sa = (ServiceApplicationType) query.uniqueResult();
		return sa;
	}

	public Long save(ServiceApplicationType serviceApplicationType) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(serviceApplicationType);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceApplication> findAlls() {
		logger.info("[method : findAlls][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		List<ServiceApplication> serviceApplication = (List<ServiceApplication>) session.createQuery("from ServiceApplication").list();
		return serviceApplication;
	}
	
}
