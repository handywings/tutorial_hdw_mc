package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.RequisitionDocumentDAO;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.utils.Pagination;

@Repository
public class RequisitionDocumentDAOImpl implements RequisitionDocumentDAO {

	private static final Logger logger = LoggerFactory.getLogger(RequisitionDocumentDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	public List<RequisitionDocument> searchByWithdraw(String criteria, String withdraw, String startDate, String endDate) {
		logger.info("[method : searchByWithdraw][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		// key
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append("from RequisitionDocument r where r.isDeleted = false and (r.requisitionDocumentCode like :criteria ");
			strQuery = strQuery.append("or r.technicianGroup.personnel.firstName like :criteria ");
			strQuery = strQuery.append("or r.technicianGroup.personnel.lastName like :criteria) ");
		}
		// withdraw
		if (withdraw != null && !withdraw.isEmpty()) {
			strQuery = strQuery.append("and r.withdraw = :withdraw ");
		}

		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			strQuery = strQuery.append("and DATE(r.createDate) BETWEEN :startDate AND :endDate ");
		}

		logger.info("[method : searchByWithdraw][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());


		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}

		if (withdraw != null && !withdraw.isEmpty()) {
			query.setString("withdraw", withdraw);
		}

		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			query.setString("startDate", startDate);
			query.setString("endDate", endDate);
		}
		
		// execute
		List<RequisitionDocument> requisitionDocuments = query.list();

		return requisitionDocuments;
	}
	
	public RequisitionDocument getRequisitionDocumentById(Long id) {
		logger.info("[method : getRequisitionDocumentById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		RequisitionDocument requisitionDocument = (RequisitionDocument) session.get(RequisitionDocument.class, id);
		return requisitionDocument;
	}

	public Long save(RequisitionDocument requisitionDocument) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Transaction tx = null;
		Session session = this.sessionFactory.openSession();
		Object object = null;
		try {
			tx = session.beginTransaction();
			object = session.save(requisitionDocument);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw (ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return (Long) object;
	}

	public void update(RequisitionDocument requisitionDocument) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(requisitionDocument);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void delete(RequisitionDocument requisitionDocument) throws Exception {
		logger.info("[method : delete][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(requisitionDocument);
		}catch(Exception ex){
			throw(ex);
		}
	}

	public String genRequisitionDocumentCode() throws Exception {
		logger.info("[method : genRequisitionDocumentCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('REQ-',LPAD((SELECT COUNT(id)+1 FROM requisition_document), 4, '0'),DATE_FORMAT(NOW(),'%m%Y')) as requisitionDocumentCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("PackageTypeCode : "+obj.toString());
			return obj.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPage(Pagination pagination) {
		logger.info("[method : getByPage][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append(" from RequisitionDocument req where req.isDeleted = false order by req.id ");

		Query query = session.createQuery(strQuery.toString());
		if (pagination != null) {
			query.setFirstResult(pagination.getLimitStart());
			query.setMaxResults(pagination.getLimitEnd());
		}
		// execute
		List<RequisitionDocument> requisitionDocument = query.list();
		pagination.setDataList(requisitionDocument);

		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	public Pagination getByPageCriteria(Pagination pagination, String criteria, String withdraw, String startDate, String endDate) {
		logger.info("[method : getByPageCriteria][Type : DAO]");
		logger.info("[method : getByPageCriteria][Input : criteria="+criteria+", withdraw="+withdraw+", startDate="+startDate+", endDate="+endDate+"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		// key
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append("from RequisitionDocument r where r.isDeleted = false and (r.requisitionDocumentCode like :criteria ");
			strQuery = strQuery.append("or r.technicianGroup.personnel.firstName like :criteria ");
			strQuery = strQuery.append("or r.technicianGroup.personnel.lastName like :criteria ");
			strQuery = strQuery.append("or r.technicianGroup.personnel.personnelCode like :criteria) ");
		}else{
			strQuery = strQuery.append("from RequisitionDocument r where r.isDeleted = false ");
		}
		// withdraw
		if (withdraw != null && !withdraw.isEmpty()) {
			strQuery = strQuery.append("and r.withdraw = :withdraw ");
		}

		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			strQuery = strQuery.append("and DATE(r.createDate) BETWEEN :startDate AND :endDate ");
		}

		logger.info("[method : searchByWithdraw][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());


		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}

		if (withdraw != null && !withdraw.isEmpty()) {
			query.setString("withdraw", withdraw);
		}

		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			query.setString("startDate", startDate);
			query.setString("endDate", endDate);
		}
		
		// execute
		
		if (pagination != null) {
			query.setFirstResult(pagination.getLimitStart());
			query.setMaxResults(pagination.getLimitEnd());
		}
		// execute
		List<RequisitionDocument> requisitionDocument = query.list();
		pagination.setDataList(requisitionDocument);

		return pagination;
	}

	public int getCountTotal() {
		logger.info("[method : getCountTotal][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		int count = ((Number) session.createQuery("select count(*) from RequisitionDocument").uniqueResult()).intValue();
		logger.info("[method : getCountTotal][Type : DAO][size : "+ count +"]");
		return count;
	}

}
