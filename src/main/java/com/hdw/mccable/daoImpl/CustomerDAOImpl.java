package com.hdw.mccable.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hdw.mccable.dao.CustomerDAO;
import com.hdw.mccable.dto.CustomerSearchBean;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.utils.Pagination;

@Repository
public class CustomerDAOImpl implements CustomerDAO{
	private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	///////////////////////// implement method ///////////////////////////
	public Customer getCustomerById(Long id) {
		logger.info("[method : getCustomerById][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		Customer customer = (Customer) session.get(Customer.class, id); 
		return customer;
	}

	public Customer getCustomerByCustCode(String custCode) {
		logger.info("[method : getCustomerByCustCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("SELECT c.* FROM customer c where c.custCode = :custCode ");
		
		logger.info("[method : getCustomerByCustCode][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());

		query.setString("custCode",custCode);		
		query.addEntity(Customer.class);
		//execute
		List<Customer> customers = query.list();
		if(null != customers && customers.size() > 0){
			return customers.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Customer> customers = (List<Customer>) session.createQuery("from Customer where isDeleted = false").list();
		return customers;
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAllisActive() {
		logger.info("[method : findAll][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Customer> customers = (List<Customer>) session.createQuery("from Customer where isDeleted = false and isActive = true ORDER BY firstName ").list();
		return customers;
	}
	
	public void update(Customer customer) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(customer);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public Long save(Customer customer) throws Exception {
		logger.info("[method : save][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(customer);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	
	public void delete(Customer customer) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(customer);
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> searchCustomer(String key, String custType, Long custFeature) {
		logger.info("[method : searchCustomer][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("SELECT DISTINCT c.* FROM customer c INNER JOIN address a ON (a.customerId = c.id) INNER JOIN contact co ON (co.customerId = c.id) INNER JOIN customer_feature cf ON (c.customerFeatureTypeId = cf.id) where c.isDeleted = false ");
		
		//key
		if(key!=null &&(!key.isEmpty())){
			strQuery = strQuery.append("and (c.custCode like :key ");
			strQuery = strQuery.append("or c.firstName like :key or c.lastName like :key or c.identityNumber like :key or co.email like :key or co.mobile like :key or a.no like :key ) ");
		}
		
		//cust type
		if(custType!=null &&(!custType.isEmpty())){
			strQuery = strQuery.append(" and c.custType = :custType ");
		}
		
		//cust feature
		if(custFeature > 0){
			strQuery = strQuery.append(" and cf.id = :custFeature ");
		}
		
		logger.info("[method : searchCustomer][Query : " + strQuery.toString() + "]");

		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		//prepare statement
		//key
		if(key!=null &&(!key.isEmpty())){
			query.setString("key", "%" + key + "%");
		}
		//cust type
		if(custType!=null &&(!custType.isEmpty())){
			query.setString("custType",custType);
		}
		//cust feature
		if(custFeature > 0){
			query.setLong("custFeature",custFeature);
		}
		
		query.addEntity(Customer.class);
		//execute
		List<Customer> customers = query.list();
		return customers;
	}
	public String genCustomerCode() {
		logger.info("[method : genCustomerCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('CUST-',LPAD((SELECT COUNT(id)+1 FROM customer), 6, '0')) as customerCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("genCustomerCode : "+obj.toString());
			return obj.toString();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Career> findAllCareer() {
		logger.info("[method : findAllCareer][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Career> careers = (List<Career>) session.createQuery("from Career").list();
		return careers;
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerFeature> findAllCustomerFeature() {
		logger.info("[method : findAllCustomerFeature][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<CustomerFeature> customerFeatures = (List<CustomerFeature>) session.createQuery("from CustomerFeature").list();
		return customerFeatures;
	}
	
	public Career findCareerById(Long id) {
		logger.info("[method : findCareerById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Career career = (Career) session.get(Career.class, id);
		return career;
	}
	
	public Career findCareerByCareerName(String careerName) {
		if(!"".equals(careerName)){
			logger.info("[method : findCareerByCareerName][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT c.* FROM career c where c.careerName like :careerName ");
			
			logger.info("[method : findCareerByCareerName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("careerName", "%" + careerName + "%");	
			query.addEntity(Career.class);
			//execute
			List<Career> careers = query.list();
			if(null != careers && careers.size() > 0){
				return careers.get(0);
			}
		}
		return null;
	}
	public CustomerFeature findCustomerFeatureById(Long id) {
		logger.info("[method : findCustomerFeatureById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		CustomerFeature customerFeature = (CustomerFeature) session.get(CustomerFeature.class, id);
		return customerFeature;
	}
	
	public CustomerFeature findCustomerFeatureByCustomerFeatureName(String customerFeatureName) {
		if(!"".equals(customerFeatureName)){
			logger.info("[method : findCustomerFeatureByCustomerFeatureName][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT c.* FROM customer_feature c where c.customerFeatureName like :customerFeatureName ");
			
			logger.info("[method : findCustomerFeatureByCustomerFeatureName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("customerFeatureName", "%" + customerFeatureName + "%");	
			query.addEntity(CustomerFeature.class);
			//execute
			List<CustomerFeature> customerFeatures = query.list();
			if(null != customerFeatures && customerFeatures.size() > 0){
				return customerFeatures.get(0);
			}
		}
		return null;
	}
	
	public CustomerFeature findCustomerFeatureByCustomerFeatureCode(String customerFeatureCode) {
		if(!"".equals(customerFeatureCode)){
			logger.info("[method : findCustomerFeatureByCustomerFeatureCode][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT c.* FROM customer_feature c where c.customerFeatureCode = :customerFeatureCode ");
			
			logger.info("[method : findCustomerFeatureByCustomerFeatureCode][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("customerFeatureCode",customerFeatureCode);	
			query.addEntity(CustomerFeature.class);
			//execute
			List<CustomerFeature> customerFeatures = query.list();
			if(null != customerFeatures && customerFeatures.size() > 0){
				return customerFeatures.get(0);
			}
		}
		return null;
	}
	
	public List<Customer> getCustomerByConditionForReport(String reportrange, String zone, String career,
			String customerType) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" select cust.* from customer cust ");
		strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
		strQuery = strQuery.append(" join service_application sa on sa.customerId = cust.id ");
		strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
		strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
		strQuery = strQuery.append(" where cust.isDeleted = false ");
		
		if(!StringUtils.isEmpty(zone) && !"0".equals(zone)){
			strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
		}else{
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
		}
		
		if(!StringUtils.isEmpty(career) && !"0".equals(career)){
			strQuery = strQuery.append(" and cust.careerId = :careerId ");
		}
		
		if(!StringUtils.isEmpty(customerType) && !"0".equals(customerType)){
			strQuery = strQuery.append(" and cust.custType = :customerType ");
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and  (DATE(cust.createDate) BETWEEN :startDate AND :endDate) ");
		}
		
		strQuery = strQuery.append(" group by cust.id ");
		
		logger.info("[method : getCustomerByConditionForReport][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(Customer.class);
		
		if(!StringUtils.isEmpty(zone) && !"0".equals(zone)){
			query.setLong("zoneId", Long.parseLong(zone));
		}
		
		if(!StringUtils.isEmpty(career) && !"0".equals(career)){
			query.setLong("careerId", Long.parseLong(career));
		}
		
		if(!StringUtils.isEmpty(customerType) && !"0".equals(customerType)){
			query.setString("customerType", customerType);
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate));
				query.setDate("endDate", formatDataEngRange.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<Customer> customerList = query.list();
					
		return customerList;
	}
	public List<ServiceApplication> getCustomerByServicetypeForReport(String reportrange, String servicePackageType, String customerFeature) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" select sa.* from service_application sa ");
		strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
		strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
		strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
		strQuery = strQuery.append(" join service_package_type spt on sp.ServicePackageTypeId = spt.id ");
		strQuery = strQuery.append(" where cust.isDeleted = false and sa.isDeleted = false and sa.status = 'A' ");
		
		if(!StringUtils.isEmpty(servicePackageType) && !"0".equals(servicePackageType)){
			strQuery = strQuery.append(" and  spt.id = :servicePackageType ");
		}

		if(!StringUtils.isEmpty(customerFeature) && !"0".equals(customerFeature)){
			strQuery = strQuery.append(" and  cust.customerFeatureTypeId = :customerFeature ");
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and  (DATE(sa.createDate) BETWEEN :startDate AND :endDate) ");
		}
		
		strQuery = strQuery.append(" order by spt.id ");
		
		logger.info("[method : getCustomerByServicetypeForReport][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(ServiceApplication.class);
		
		if(!StringUtils.isEmpty(servicePackageType) && !"0".equals(servicePackageType)){
			query.setLong("servicePackageType", Long.parseLong(servicePackageType));
		}
		
		if(!StringUtils.isEmpty(customerFeature) && !"0".equals(customerFeature)){
			query.setLong("customerFeature", Long.parseLong(customerFeature));
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate));
				query.setDate("endDate", formatDataEngRange.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<ServiceApplication> serviceApplicationList = query.list();
					
		return serviceApplicationList;
	}
	public List<ServiceApplication> getCustomerByServiceAppTypeForReport(String reportrange,
			String serviceApplicationType) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" select sa.* from service_application sa ");
		strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
		strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
		strQuery = strQuery.append(" where cust.isDeleted = false and sa.isDeleted = false and sa.status = 'A' ");
		
		if(!StringUtils.isEmpty(serviceApplicationType) && !"0".equals(serviceApplicationType)){
			strQuery = strQuery.append(" and  sa.serviceApplicationTypeId = :serviceApplicationType ");
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and  (DATE(sa.startDate) BETWEEN :startDate AND :endDate) ");
		}
		
		strQuery = strQuery.append(" order by sa.serviceApplicationTypeId ");
		
		logger.info("[method : getCustomerByServiceAppTypeForReport][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(ServiceApplication.class);
		
		if(!StringUtils.isEmpty(serviceApplicationType) && !"0".equals(serviceApplicationType)){
			query.setLong("serviceApplicationType", Long.parseLong(serviceApplicationType));
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate));
				query.setDate("endDate", formatDataEngRange.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<ServiceApplication> serviceApplicationList = query.list();
					
		return serviceApplicationList;
	}
	public List<WorksheetCut> getCustomerByWorksheetCancelForReport(String reportrange, int displayFormat,
			int sortingStyle) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" SELECT wc.* FROM worksheet_cut wc ");
		strQuery = strQuery.append(" JOIN worksheet w on w.id = wc.worksheetId ");
		strQuery = strQuery.append(" JOIN service_application sapp ON sapp.id = w.serviceApplicationId ");
		strQuery = strQuery.append(" JOIN service_package sp ON sp.id = sapp.servicePackageId ");
		strQuery = strQuery.append(" JOIN service_package_type spt ON spt.id = sp.ServicePackageTypeId ");
		strQuery = strQuery.append(" JOIN address ad ON ad.serviceApplicationId = sapp.id ");
		strQuery = strQuery.append(" JOIN zone z ON z.id = ad.zoneId ");
		strQuery = strQuery.append(" JOIN customer cust ON sapp.customerId = cust.id ");
		strQuery = strQuery.append(" JOIN contact cont ON cust.id = cont.customerId ");
		strQuery = strQuery.append(" where cust.isDeleted = false and ad.addressType = '3' ");
		strQuery = strQuery.append(" and w.status = 'S' ");
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and  (DATE(w.createDate) BETWEEN :startDate AND :endDate) ");
		}
		
		if(sortingStyle==1){
			strQuery = strQuery.append(" order by cust.custCode ");
		}else{
			strQuery = strQuery.append(" order by w.createDate ");
		}
		
		logger.info("[method : getCustomerByWorksheetCancelForReport][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(WorksheetCut.class);
		

		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate));
				query.setDate("endDate", formatDataEngRange.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<WorksheetCut> worksheetCutList = query.list();
					
		return worksheetCutList;
	}
	public List<WorksheetMove> getCustomerByWorksheetMoveForReport(String reportrange, int sortingStyle) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append(" SELECT wm.* FROM worksheet_move wm ");
		strQuery = strQuery.append(" JOIN worksheet w on w.id = wm.worksheetId ");
		strQuery = strQuery.append(" JOIN service_application sapp ON sapp.id = w.serviceApplicationId ");
		strQuery = strQuery.append(" JOIN service_package sp ON sp.id = sapp.servicePackageId ");
		strQuery = strQuery.append(" JOIN service_package_type spt ON spt.id = sp.ServicePackageTypeId ");
		strQuery = strQuery.append(" JOIN address ad ON ad.serviceApplicationId = sapp.id ");
		strQuery = strQuery.append(" JOIN zone z ON z.id = ad.zoneId ");
		strQuery = strQuery.append(" JOIN customer cust ON sapp.customerId = cust.id ");
		strQuery = strQuery.append(" JOIN contact cont ON cust.id = cont.customerId ");
		strQuery = strQuery.append(" where cust.isDeleted = false and ad.addressType = '3' ");
		strQuery = strQuery.append(" and w.status = 'S' ");
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and  (DATE(w.createDate) BETWEEN :startDate AND :endDate) ");
		}
		
		strQuery = strQuery.append(" group by sapp.id ");
		
		if(sortingStyle==1){
			strQuery = strQuery.append(" order by cust.custCode ");
		}else{
			strQuery = strQuery.append(" order by w.createDate ");
		}
		
		logger.info("[method : getCustomerByWorksheetMoveForReport][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(WorksheetMove.class);
		

		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate));
				query.setDate("endDate", formatDataEngRange.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<WorksheetMove> worksheetMoveList = query.list();
					
		return worksheetMoveList;
	}
	public int getCountTotal(CustomerSearchBean customerSearchBean) {
		logger.info("[method : getCountTotal][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(customerSearchBean != null){
			
			strQuery = strQuery.append("SELECT count(DISTINCT c.id) FROM customer c INNER JOIN address a ON (a.customerId = c.id) INNER JOIN contact co ON (co.customerId = c.id) INNER JOIN customer_feature cf ON (c.customerFeatureTypeId = cf.id) where c.isDeleted = false ");
			
			//key
			if(null != customerSearchBean.getKey() &&(!customerSearchBean.getKey().isEmpty())){
				strQuery = strQuery.append("and (c.custCode like :key ");
				strQuery = strQuery.append("or c.firstName like :key or c.lastName like :key or c.identityNumber like :key or co.email like :key or co.mobile like :key or a.no like :key ) ");
			}
			
			//cust type
			if(null != customerSearchBean.getCustType() &&(!customerSearchBean.getCustType().isEmpty())){
				strQuery = strQuery.append(" and c.custType = :custType ");
			}
			
			//cust feature
			if(null != customerSearchBean.getCustomerFeatures() && customerSearchBean.getCustomerFeatures() > 0){
				strQuery = strQuery.append(" and cf.id = :custFeature ");
			}
			
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");

			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			//prepare statement
			//key
			if(null != customerSearchBean.getKey() &&(!customerSearchBean.getKey().isEmpty())){
				query.setString("key", "%" + customerSearchBean.getKey() + "%");
			}
			//cust type
			if(null != customerSearchBean.getCustType() &&(!customerSearchBean.getCustType().isEmpty())){
				query.setString("custType",customerSearchBean.getCustType());
			}
			//cust feature
			if(null != customerSearchBean.getCustomerFeatures() && customerSearchBean.getCustomerFeatures() > 0){
				query.setLong("custFeature",customerSearchBean.getCustomerFeatures());
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
	public Pagination getByPage(Pagination pagination, CustomerSearchBean customerSearchBean) {
		logger.info("[method : getByPage][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		if (customerSearchBean != null) {
			
			strQuery = strQuery.append("SELECT DISTINCT c.* FROM customer c INNER JOIN address a ON (a.customerId = c.id) INNER JOIN contact co ON (co.customerId = c.id) INNER JOIN customer_feature cf ON (c.customerFeatureTypeId = cf.id) where c.isDeleted = false ");
			
			//key
			if(null != customerSearchBean.getKey() &&(!customerSearchBean.getKey().isEmpty())){
				strQuery = strQuery.append("and (c.custCode like :key ");
				strQuery = strQuery.append("or c.firstName like :key or c.lastName like :key or c.identityNumber like :key or co.email like :key or co.mobile like :key or a.no like :key ) ");
			}
			
			//cust type
			if(null != customerSearchBean.getCustType() &&(!customerSearchBean.getCustType().isEmpty())){
				strQuery = strQuery.append(" and c.custType = :custType ");
			}
			
			//cust feature
			if(null != customerSearchBean.getCustomerFeatures() && customerSearchBean.getCustomerFeatures() > 0){
				strQuery = strQuery.append(" and cf.id = :custFeature ");
			}
			
			logger.info("[method : getByPage][Query : " + strQuery.toString() + "]");

			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.addEntity(Customer.class);
			
			//prepare statement
			//key
			if(null != customerSearchBean.getKey() &&(!customerSearchBean.getKey().isEmpty())){
				query.setString("key", "%" + customerSearchBean.getKey() + "%");
			}
			//cust type
			if(null != customerSearchBean.getCustType() &&(!customerSearchBean.getCustType().isEmpty())){
				query.setString("custType",customerSearchBean.getCustType());
			}
			//cust feature
			if(null != customerSearchBean.getCustomerFeatures() && customerSearchBean.getCustomerFeatures() > 0){
				query.setLong("custFeature",customerSearchBean.getCustomerFeatures());
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			// execute
			List<Customer> customers = query.list();
			pagination.setDataList(customers);
			
		} else {
			return pagination;
		}
		return pagination;
	}
	
	
	public Long save(CustomerFeature customerFeature) throws Exception {
		logger.info("[method : saveCustomerFeature][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(customerFeature);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	
}
