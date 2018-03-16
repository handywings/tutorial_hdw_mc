package com.hdw.mccable.daoImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.DashboardDAO;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.Worksheet;

@Repository
public class DashboardDAOImpl implements DashboardDAO {

	private static final Logger logger = LoggerFactory.getLogger(DashboardDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> findAllInvoiceByDate(Map<String, Object> criteria) throws Exception {
		logger.info("[method : findAllInvoiceByDate][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoices = new ArrayList<Invoice>();
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("from Invoice inv WHERE MONTH(inv.paymentDate) = :month AND YEAR(inv.paymentDate) = :year ");
			strQuery = strQuery.append(" and inv.isDeleted = false ");
			
			Query query = session.createQuery(strQuery.toString());
			query.setString("month", month);
			query.setString("year", year);
			
			invoices = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return invoices;
	}

	@SuppressWarnings("unchecked")
	public CashierBean countBillAndAmountByDate(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CashierBean cashierBean = new CashierBean();
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append(" SELECT SUM(amount)sumAmount,COUNT(personnelId) totalBill");
			strQuery = strQuery.append(" FROM invoice");
			strQuery = strQuery.append(" WHERE status = 'S' AND personnelId = :personnelId ");
			strQuery = strQuery.append(" and MONTH(scanOutDate) = :month and YEAR(scanOutDate) = :year ");
			
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			String personnelId = criteria.get("personnelId").toString();
			query.setString("personnelId", personnelId);
			query.setString("month", month);
			query.setString("year", year);
			
			List<Object[]> obj = query.list();
			DecimalFormat df = new DecimalFormat("#,###");
			if (obj != null) {
				for (Object[] objects : obj) {
					// add value to bean
					cashierBean.setSumAmount(Float.valueOf(null==objects[0]?"0":df.format(Float.valueOf(objects[0].toString()))));
					cashierBean.setTotalBill(Long.valueOf(null==objects[1]?"0":objects[1].toString()));		
				}	
			}else {
				cashierBean.setSumAmount((float)0);
				cashierBean.setTotalBill(0L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cashierBean;
	}

	@SuppressWarnings("unchecked")
	public List<Worksheet> findAllWorksheet(Map<String, Object> criteria) throws Exception {
		List<Worksheet> worksheets = new ArrayList<Worksheet>();
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		try {
			strQuery = strQuery.append("from Worksheet w where w.isDeleted = false ");
			strQuery = strQuery.append(" and MONTH(createDate) = :month and YEAR(createDate) = :year ");
			
			Query query = session.createQuery(strQuery.toString());
			query.setString("month", month);
			query.setString("year", year);
			worksheets = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw(e);
		}
		logger.info("[method : findAllWorksheet][Query : " + strQuery.toString() + "]");

		return worksheets;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findAllCustomerByDate(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		List<Customer> customers = new ArrayList<Customer>();
		try {
			strQuery = strQuery.append("from Customer c where c.isDeleted = false and c.isActive = true ");
			
			Query query = session.createQuery(strQuery.toString());
			customers = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceApplication> findServiceAppByDate(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		List<ServiceApplication> serviceApplications = new ArrayList<ServiceApplication>();
		try {
			strQuery = strQuery.append("from ServiceApplication s where s.isDeleted = false ");
			strQuery = strQuery.append("and MONTH(s.createDate) = :month and YEAR(s.createDate) = :year ");
			
			Query query = session.createQuery(strQuery.toString());
			query.setString("month", month);
			query.setString("year", year);
			serviceApplications = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceApplications;
	}

	public int countCustomer(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		int count = 0;
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("select count(*) customer from customer c WHERE MONTH(c.createDate) = :month AND YEAR(c.createDate) = :year ");
			strQuery = strQuery.append(" and c.isDeleted = false ");
			
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("month", month);
			query.setString("year", year);
			
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return count;
	}

	public int countCustomerFromZone(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		int count = 0;
		String zone_id = criteria.get("zone_id").toString();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("SELECT count(*) c  FROM service_application sa ");
			strQuery = strQuery.append("JOIN address a ON sa.id = a.serviceApplicationId ");
			strQuery = strQuery.append("JOIN zone z ON a.zoneId = z.id ");
			strQuery = strQuery.append("WHERE z.id = :zone_id AND sa.isDeleted = false AND a.addressType = 3 ");
			strQuery = strQuery.append("GROUP BY z.id ");
			
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("zone_id", zone_id);
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return count;
	}

	public int countIncome(Map<String, Object> criteria) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		int count = 0;
		String code = criteria.get("code").toString();
		String month = criteria.get("month").toString();
		String year = criteria.get("year").toString();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("SELECT SUM(inv.amount) amount FROM invoice inv ");
			strQuery = strQuery.append("JOIN service_application sa ON inv.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("JOIN service_package sp ON sa.servicePackageId = sp.id ");
			strQuery = strQuery.append("JOIN service_package_type spt ON sp.ServicePackageTypeId = spt.id ");
			strQuery = strQuery.append("JOIN customer c ON sa.customerId = c.id ");
			strQuery = strQuery.append("WHERE spt.packageTypeCode = :code AND inv.isDeleted = false AND inv.status = 'S' ");
			strQuery = strQuery.append("and MONTH(inv.paymentDate) = :month AND YEAR(inv.paymentDate) = :year ");
			
			
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("code", code);
			query.setString("month", month);
			query.setString("year", year);
			
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString().trim().split("\\.")[0]);
			}else{
				count = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
		return count;
	}
}
