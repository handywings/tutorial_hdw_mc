package com.hdw.mccable.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.WorkSheetDAO;
import com.hdw.mccable.dto.AssignWorksheetSearchBean;
import com.hdw.mccable.dto.WorksheetSearchBean;
import com.hdw.mccable.entity.HistoryRepair;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetAddSetTopBox;
import com.hdw.mccable.entity.WorksheetAnalyzeProblems;
import com.hdw.mccable.entity.WorksheetBorrow;
import com.hdw.mccable.entity.WorksheetConnect;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.entity.WorksheetMovePoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetRepairConnection;
import com.hdw.mccable.entity.WorksheetTune;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;

@Repository
public class WorkSheetDAOImpl implements WorkSheetDAO{
	
private static final Logger logger = LoggerFactory.getLogger(WorkSheetDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void update(Worksheet worksheet) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheet);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(Worksheet worksheet) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(worksheet);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}


	public void delete(Worksheet worksheet) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(worksheet);
		}catch(Exception ex){
			throw(ex);
		}
	}

	public String genWorkSheetCode() {
		logger.info("[method : genWorkSheetCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('WORK-',LPAD((SELECT COUNT(id)+1 FROM worksheet), 6, '0')) as workSheetCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("genWorkSheetCode : "+obj.toString());
			return obj.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Worksheet> searchByWorksheetCodeAndserviceApplicationId(String workSheetType, Long serviceApplicationId) {
		logger.info("[method : searchByWorksheetCodeAnd][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Worksheet w where w.isDeleted = false ");

		// check criteria
		if (workSheetType != null && !StringUtils.isEmpty(workSheetType)) {
			strQuery = strQuery.append(" and w.workSheetType = :workSheetType ");
		}
		
		//check stock
		if (serviceApplicationId != null && serviceApplicationId > 0) {
			strQuery = strQuery.append(" and w.serviceApplication.id = :serviceApplicationId ");
		}

		logger.info("[method : searchByWorksheetCodeAnd][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		
		// check workSheetType
		if (workSheetType != null && !StringUtils.isEmpty(workSheetType)) {
			query.setString("workSheetType", workSheetType);
		}

		// check serviceApplicationId
		if (serviceApplicationId != null && serviceApplicationId > 0) {
			query.setLong("serviceApplicationId", serviceApplicationId);
		}
		
		// execute
		List<Worksheet> worksheets = query.list();

		return worksheets;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPageForAssign(Pagination pagination, AssignWorksheetSearchBean assignWorksheetSearchBean) {
		logger.info("[method : getByPageForAssign][Type : DAO]//////");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(assignWorksheetSearchBean != null){
			strQuery = strQuery.append(" select ws.*, sa.easyInstallationDateTime from worksheet ws ");
			strQuery = strQuery.append(" join service_application sa on ws.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			
			if (assignWorksheetSearchBean.getKey() != null && (!assignWorksheetSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where ws.isDeleted = false and (ws.workSheetCode like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}
			
			//job type
			if(assignWorksheetSearchBean.getJobType() != null && (!assignWorksheetSearchBean.getJobType().isEmpty())){
				strQuery = strQuery.append(" and ws.workSheetType = :workSheetType ");
			}
			
			//job status
			if(assignWorksheetSearchBean.getJobStatus() != null && (!assignWorksheetSearchBean.getJobStatus().isEmpty())){
				strQuery = strQuery.append(" and ws.status = :status ");
			}else{
				strQuery = strQuery.append(" and (ws.status = 'W' or ws.status = 'H') ");
			}
			//zone for append
			if(assignWorksheetSearchBean.getZone() != null && (assignWorksheetSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			//order by
			if(assignWorksheetSearchBean.getOrderByType() != null && (!assignWorksheetSearchBean.getOrderByType().isEmpty())){
				if(assignWorksheetSearchBean.getOrderByType().equals("W")){
					strQuery = strQuery.append(" order by  ws.workSheetCode ");
					
				}else if(assignWorksheetSearchBean.getOrderByType().equals("S")){
					strQuery = strQuery.append(" order by  ws.status ");
					
				}else if(assignWorksheetSearchBean.getOrderByType().equals("T")){
					strQuery = strQuery.append(" order by  ws.workSheetType ");
					
				}else if(assignWorksheetSearchBean.getOrderByType().equals("Z")){
					strQuery = strQuery.append(" order by  z.zoneName ");
				}
			}else{
				strQuery = strQuery.append(" order by  ws.id desc");
			}
			
			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Worksheet.class);
			// key
			if (assignWorksheetSearchBean.getKey() != null && (!assignWorksheetSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + assignWorksheetSearchBean.getKey() + "%");
			}  
			// job type
			if (assignWorksheetSearchBean.getJobType() != null && (!assignWorksheetSearchBean.getJobType().isEmpty())) {
				query.setString("workSheetType", assignWorksheetSearchBean.getJobType());
			}
			// job status
			if (assignWorksheetSearchBean.getJobStatus() != null && (!assignWorksheetSearchBean.getJobStatus().isEmpty())) {
				query.setString("status", assignWorksheetSearchBean.getJobStatus());
			}
			// zone
			if (assignWorksheetSearchBean.getZone() != null && (assignWorksheetSearchBean.getZone() > 0)) {
				query.setLong("zoneId", assignWorksheetSearchBean.getZone());
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			
			//execute
			List<Worksheet> worksheets = query.list(); 
			pagination.setDataList(worksheets);
		} 
		
		return pagination;
	}

	public int getCountTotal(AssignWorksheetSearchBean assignWorksheetSearchBean) {
		logger.info("[method : getCountTotal][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(assignWorksheetSearchBean != null){
			strQuery = strQuery.append("select count(ws.id) from worksheet ws ");
			strQuery = strQuery.append(" join service_application sa on ws.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			
			if (assignWorksheetSearchBean.getKey() != null && (!assignWorksheetSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where ws.isDeleted = false and (ws.workSheetCode like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}
			
			//job type
			if(assignWorksheetSearchBean.getJobType() != null && (!assignWorksheetSearchBean.getJobType().isEmpty())){
				strQuery = strQuery.append(" and ws.workSheetType = :workSheetType ");
			}
			
			//job status
			if(assignWorksheetSearchBean.getJobStatus() != null && (!assignWorksheetSearchBean.getJobStatus().isEmpty())){
				strQuery = strQuery.append(" and ws.status = :status ");
			}else{
				strQuery = strQuery.append(" and (ws.status = 'W' or ws.status = 'H') ");
			}
			//zone for append
			if(assignWorksheetSearchBean.getZone() != null && (assignWorksheetSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			// key
			if (assignWorksheetSearchBean.getKey() != null && (!assignWorksheetSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + assignWorksheetSearchBean.getKey() + "%");
			}  
			// job type
			if (assignWorksheetSearchBean.getJobType() != null && (!assignWorksheetSearchBean.getJobType().isEmpty())) {
				query.setString("workSheetType", assignWorksheetSearchBean.getJobType());
			}
			// job status
			if (assignWorksheetSearchBean.getJobStatus() != null && (!assignWorksheetSearchBean.getJobStatus().isEmpty())) {
				query.setString("status", assignWorksheetSearchBean.getJobStatus());
			}
			// zone
			if (assignWorksheetSearchBean.getZone() != null && (assignWorksheetSearchBean.getZone() > 0)) {
				query.setLong("zoneId", assignWorksheetSearchBean.getZone());
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
			logger.info("[method : getCountTotal][count : " + count + "]");
		}
		
		return count;
	}
	
	public Worksheet getWorksheetById(Long id) {
		logger.info("[method : getWorksheetById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Worksheet ws where ws.isDeleted = false and ws.id = :id");
		logger.info("[method : getWorksheetById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Worksheet worksheet = (Worksheet) query.uniqueResult();
		return worksheet;
	}

	public HistoryTechnicianGroupWork findHistoryTechnicianGroupWork(Long id) {
		logger.info("[method : findHistoryTechnicianGroupWork][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from HistoryTechnicianGroupWork ht where ht.isDeleted = false and ht.id = :id");
		logger.info("[method : findHistoryTechnicianGroupWork][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		HistoryTechnicianGroupWork historyTechnicianGroupWork = (HistoryTechnicianGroupWork) query.uniqueResult();
		return historyTechnicianGroupWork;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPageForWorksheet(Pagination pagination, WorksheetSearchBean worksheetSearchBean) {
		logger.info("[method : getByPageForAssign][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(worksheetSearchBean != null){
			strQuery = strQuery.append(" select ws.* from worksheet ws ");
			strQuery = strQuery.append(" join service_application sa on ws.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");

			// key
			if (worksheetSearchBean.getKey() != null && (!worksheetSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where ws.isDeleted = false and (ws.workSheetCode like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}
			
			//status
			if(worksheetSearchBean.getStatus() != null && (!worksheetSearchBean.getStatus().isEmpty())){
				strQuery = strQuery.append(" and ws.status = :status ");
			}
			
			//WorkSheetType
			if(worksheetSearchBean.getWorkSheetType() != null && (!worksheetSearchBean.getWorkSheetType().isEmpty())){
				strQuery = strQuery.append(" and ws.workSheetType = :workSheetType ");
			}
			
			//zone for append
			if(worksheetSearchBean.getZone() != null && (worksheetSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			strQuery = strQuery.append(" order by ws.id desc ");
			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Worksheet.class);
			// key
			if (worksheetSearchBean.getKey() != null && (!worksheetSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + worksheetSearchBean.getKey() + "%");
			}  
			//status
			if(worksheetSearchBean.getStatus() != null && (!worksheetSearchBean.getStatus().isEmpty())){
				query.setString("status", worksheetSearchBean.getStatus());
			}
			// WorkSheetType
			if (worksheetSearchBean.getWorkSheetType() != null && (!worksheetSearchBean.getWorkSheetType().isEmpty())) {
				query.setString("workSheetType", worksheetSearchBean.getWorkSheetType());
			}
			// zone
			if (worksheetSearchBean.getZone() != null && (worksheetSearchBean.getZone() > 0)) {
				query.setLong("zoneId", worksheetSearchBean.getZone());
			}
			
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			
			//execute
			List<Worksheet> worksheets = query.list(); 
			pagination.setDataList(worksheets);
		} 
		
		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	public List<Worksheet> getCAList() {
		logger.info("[method : unchecked][Type : DAO]");
		
		logger.info("[method : getCAList][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("select * from worksheet_cut AS wc join worksheet AS ws ON (wc.worksheetId = ws.id) where (ws.status = 'S' AND wc.submitCA = 1 AND wc.isConfirmCA = 0)");
		
		//prepare statement
		logger.info("[method : getCAList][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(Worksheet.class);
		
		//execute
		List<Worksheet> worksheets = query.list(); 
		
		logger.info("[method : getCAList][count : " + count + "]");		
		
		
		
		return worksheets;
	}


	public int getCountTotal(WorksheetSearchBean worksheetSearchBean) {
		logger.info("[method : getCountTotal][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		if(worksheetSearchBean != null){
			strQuery = strQuery.append("select count(ws.id) from worksheet ws ");
			strQuery = strQuery.append(" join service_application sa on ws.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			
			if (worksheetSearchBean.getKey() != null && (!worksheetSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where ws.isDeleted = false and (ws.workSheetCode like :criteria ");
				strQuery = strQuery.append(" or cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}
			
			//status
			if(worksheetSearchBean.getStatus() != null && (!worksheetSearchBean.getStatus().isEmpty())){
				strQuery = strQuery.append(" and ws.status = :status ");
			}
			
			//WorkSheetType
			if(worksheetSearchBean.getWorkSheetType() != null && (!worksheetSearchBean.getWorkSheetType().isEmpty())){
				strQuery = strQuery.append(" and ws.workSheetType = :workSheetType ");
			}
			
			//zone for append
			if(worksheetSearchBean.getZone() != null && (worksheetSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '3' ");
			}
			
			//prepare statement
			logger.info("[method : getCountTotal][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			// key
			if (worksheetSearchBean.getKey() != null && (!worksheetSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + worksheetSearchBean.getKey() + "%");
			}  
			
			//status
			if(worksheetSearchBean.getStatus() != null && (!worksheetSearchBean.getStatus().isEmpty())){
				query.setString("status", worksheetSearchBean.getStatus());
			}
			
			// WorkSheetType
			if (worksheetSearchBean.getWorkSheetType() != null && (!worksheetSearchBean.getWorkSheetType().isEmpty())) {
				query.setString("workSheetType", worksheetSearchBean.getWorkSheetType());
			}
			// zone
			if (worksheetSearchBean.getZone() != null && (worksheetSearchBean.getZone() > 0)) {
				query.setLong("zoneId", worksheetSearchBean.getZone());
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
			logger.info("[method : getCountTotal][count : " + count + "]");
		}
		
		return count;
	}

	public void deleteMemberAssignByHistoryTechnicianGroupWork(Long historyTechnicainGroupWork) throws Exception {
		logger.info("[method : deleteMemberAssignByHistoryTechnicianGroupWork][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		try {
			strQuery = strQuery.append("delete from PersonnelAssign pa where pa.historyTechnicianGroupWork.id = :historyTechnicainGroupWork ");
			Query query = session.createQuery(strQuery.toString()); 
			query.setLong("historyTechnicainGroupWork", historyTechnicainGroupWork);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}

	public Long saveMemberAssign(PersonnelAssign personnelAssign) throws Exception {
		logger.info("[method : saveMemberAssign][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(personnelAssign);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteProductItemWithWorksheetId(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S') ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetId][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}
	
	public void deleteProductItemWithWorksheetIdAll(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdAll][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S' or productType = 'I') ");
			strQuery = strQuery.append("and productTypeMatch != 'R' and productTypeMatch != 'A' and productTypeMatch != 'B' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetId][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}

	public WorksheetTune getWorksheetTuneById(Long id) {
		logger.info("[method : getWorksheetTuneById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetTune wst where wst.isDeleted = false and wst.id = :id");
		logger.info("[method : getWorksheetTuneById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetTune worksheetTune = (WorksheetTune) query.uniqueResult();
		return worksheetTune;
	}

	
	public void updateWorksheetTune(WorksheetTune worksheetTune) throws Exception {
		logger.info("[method : updateWorksheetTune][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetTune);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteProductItemWorksheet(ProductItemWorksheet productItemWorksheet) throws Exception {
		logger.info("[method : deleteProductItemWorksheet][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(productItemWorksheet);
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void updateWorksheetConnect(WorksheetConnect worksheetConnect) throws Exception {
		logger.info("[method : updateWorksheetConnect][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetConnect);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetConnect getWorksheetConnectById(Long id) {
		logger.info("[method : getWorksheetConnectById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetConnect ws where ws.isDeleted = false and ws.id = :id");
		logger.info("[method : getWorksheetConnectById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetConnect worksheetConnect = (WorksheetConnect) query.uniqueResult();
		return worksheetConnect;
	}

	@SuppressWarnings("unchecked")
	public List<HistoryTechnicianGroupWork> findHistoryTechnicianGroupWorkByDateAssign(String assignDate) {
		logger.info("[method : findHistoryTechnicianGroupWorkByDateAssign][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from HistoryTechnicianGroupWork ht where ht.isDeleted = false and DATE(ht.assignDate) = DATE(:assignDate)");
		logger.info("[method : findHistoryTechnicianGroupWorkByDateAssign][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		Date cDate = new DateUtil().convertStringToDateTimeDb(assignDate);
		query.setDate("assignDate", cDate);

		// execute
		List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList =  query.list();
		return historyTechnicianGroupWorkList;
	}

	public void deleteSubWorksheetByWorksheetId(Long id) throws Exception {
		logger.info("[method : deleteSubWorksheetByWorksheetId][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		try {
			strQuery = strQuery.append("delete from SubWorksheet sws where sws.workSheet.id = :worksheetId ");
			Query query = session.createQuery(strQuery.toString()); 
			query.setLong("worksheetId", id);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}
	
	//worksheet cut
	
	public WorksheetCut getWorksheetCutById(Long id) {
		logger.info("[method : getWorksheetCutById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetCut wsc where wsc.isDeleted = false and wsc.id = :id");
		logger.info("[method : getWorksheetCutById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetCut worksheetCut = (WorksheetCut) query.uniqueResult();
		return worksheetCut;
	}

	
	public void updateWorksheetCut(WorksheetCut worksheetCut) throws Exception {
		logger.info("[method : updateWorksheetCut][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetCut);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetAddPoint getWorksheetAddPointById(Long id) {
		logger.info("[method : getWorksheetAddPointById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetAddPoint wsap where wsap.isDeleted = false and wsap.id = :id");
		logger.info("[method : getWorksheetAddPointById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetAddPoint worksheetAddPoint = (WorksheetAddPoint) query.uniqueResult();
		return worksheetAddPoint;
	}

	public void updateWorksheetAddPoint(WorksheetAddPoint worksheetAddPoint) throws Exception {
		logger.info("[method : updateWorksheetAddPoint][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetAddPoint);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetAddSetTopBox getWorksheetAddSetTopBoxById(Long id) {
		logger.info("[method : getWorksheetAddSetTopBoxById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetAddSetTopBox wsastb where wsastb.isDeleted = false and wsastb.id = :id");
		logger.info("[method : getWorksheetAddSetTopBoxById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetAddSetTopBox worksheetAddSetTopBox = (WorksheetAddSetTopBox) query.uniqueResult();
		return worksheetAddSetTopBox;
	}

	public void updateWorksheetAddSetTopBox(WorksheetAddSetTopBox worksheetAddSetTopBox) throws Exception {
		logger.info("[method : updateWorksheetAddSetTopBox][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetAddSetTopBox);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetBorrow getWorksheetBorrowById(Long id) {
		logger.info("[method : getWorksheetBorrowById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetBorrow wsbr where wsbr.isDeleted = false and wsbr.id = :id");
		logger.info("[method : getWorksheetBorrowById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetBorrow worksheetBorrow = (WorksheetBorrow) query.uniqueResult();
		return worksheetBorrow;
	}

	public void updateWorksheetBorrow(WorksheetBorrow worksheetBorrow) throws Exception {
		logger.info("[method : updateWorksheetBorrow][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetBorrow);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetMove getWorksheetMoveById(Long id) {
		logger.info("[method : getWorksheetMoveById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetMove wsm where wsm.isDeleted = false and wsm.id = :id");
		logger.info("[method : getWorksheetMoveById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetMove worksheetMove = (WorksheetMove) query.uniqueResult();
		return worksheetMove;
	}

	public void updateWorksheetMove(WorksheetMove worksheetMove) throws Exception {
		logger.info("[method : updateWorksheetMove][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetMove);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetMovePoint getWorksheetMovePointById(Long id) {
		logger.info("[method : getWorksheetMovePointById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetMovePoint wsmp where wsmp.isDeleted = false and wsmp.id = :id");
		logger.info("[method : getWorksheetMovePointById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetMovePoint worksheetMovePoint = (WorksheetMovePoint) query.uniqueResult();
		return worksheetMovePoint;
	}

	public void updateWorksheetMovePoint(WorksheetMovePoint worksheetMovePoint) throws Exception {
		logger.info("[method : updateWorksheetMovePoint][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetMovePoint);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetReducePoint getWorksheetReducePointById(Long id) {
		logger.info("[method : getWorksheetReducePointById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetReducePoint wsrd where wsrd.isDeleted = false and wsrd.id = :id");
		logger.info("[method : getWorksheetReducePointById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetReducePoint worksheetReducePoint = (WorksheetReducePoint) query.uniqueResult();
		return worksheetReducePoint;
	}

	public void updateWorksheetReducePoint(WorksheetReducePoint worksheetReducePoint) throws Exception {
		logger.info("[method : updateWorksheetReducePoint][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetReducePoint);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public WorksheetRepairConnection getWorksheetRepairConnectionById(Long id) {
		logger.info("[method : getWorksheetRepairConnectionById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetRepairConnection wsrp where wsrp.isDeleted = false and wsrp.id = :id");
		logger.info("[method : getWorksheetRepairConnectionById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetRepairConnection worksheetRepairConnection = (WorksheetRepairConnection) query.uniqueResult();
		return worksheetRepairConnection;
	}

	public void updateWorksheetRepairConnection(WorksheetRepairConnection worksheetRepairConnection) throws Exception {
		logger.info("[method : updateWorksheetRepairConnection][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetRepairConnection);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteProductItemWithWorksheetIdTypeO(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdTypeO][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S' or productType = 'I') and productTypeMatch='O' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetIdTypeO][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}

	public void deleteProductItemWithWorksheetIdTypeN(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdTypeN][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S')  and productTypeMatch='N' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetIdTypeN][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}

	public void deleteProductItemWithWorksheetIdTypeR(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdTypeR][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S' or productType = 'I')  and productTypeMatch='R' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetIdTypeR][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}
	
	public void deleteProductItemWithWorksheetIdTypeA(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdTypeA][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S' or productType = 'I')  and productTypeMatch='A' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetIdTypeA][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}
	
	public void deleteProductItemWithWorksheetIdTypeB(Long worksheetId) throws Exception {
		logger.info("[method : deleteProductItemWithWorksheetIdTypeB][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("delete from product_item where worksheetId = :worksheetId and (productType = 'E' or productType = 'S' or productType = 'I')  and productTypeMatch='B' ");
			//prepare statement
			logger.info("[method : deleteProductItemWithWorksheetIdTypeB][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("worksheetId", worksheetId);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}
	
	public void saveHistoryRepair(HistoryRepair historyRepair) throws Exception {
		logger.info("[method : saveHistoryRepair][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(historyRepair);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void updateStatusCA(Long id) throws Exception  {
		logger.info("[method : updateStatusCA][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		try {
			strQuery = strQuery.append("update worksheet_cut set isConfirmCA = 1 where id = :id");
			//prepare statement
			logger.info("[method : updateStatusCA][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("id", id);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
	}

	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split) {
		logger.info("[method : getDataWorksheetForReport][Type : DAO]");
		logger.info("reportrange :"+reportrange);
		reportrange = reportrange.substring(1);
		logger.info("reportrange :"+reportrange);
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		strQuery = strQuery.append(" select w.* from worksheet w ");
		strQuery = strQuery.append(" join service_application sa on sa.id = w.serviceApplicationId ");
		strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
		strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
		strQuery = strQuery.append(" join history_technician_groupwork htg on z.id = ad.zoneId ");
		strQuery = strQuery.append(" where w.isDeleted = false and w.createdBy != 'MigrateEasyNet' ");

		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and htg.statusHistory = 'S' ");
			strQuery = strQuery.append(" and (DATE(htg.assignDate) BETWEEN :startDate AND :endDate) ");
		}
		
		if (!"0".equals(jobType)) {
			strQuery = strQuery.append(" and w.workSheetType = :workSheetType ");
		}
		
		if (!"0".equals(worksheetStatus)) {
			strQuery = strQuery.append(" and w.status = :worksheetStatus ");
		}
		
		if (!"0".equals(zone)) {
			strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
		}else{
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
		}
		
		strQuery = strQuery.append(" GROUP by w.id ");

		logger.info("[method : getDataWorksheetForReport][Query : " + strQuery.toString() + "]");

		// prepare statement
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(Worksheet.class);
		
		if (!"0".equals(jobType)) {
			query.setString("workSheetType", jobType);
		}
		
		if (!"0".equals(worksheetStatus)) {
			query.setString("worksheetStatus", worksheetStatus);
		}
		
		if (!"0".equals(zone)) {
			query.setLong("zoneId", Long.parseLong(zone));
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate.trim()));
				query.setDate("endDate", formatDataEngRange.parse(endDate.trim()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<Worksheet> worksheets = query.list();

		return worksheets;
	}
	
	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split, String technician) {
		logger.info("[method : getDataWorksheetForReport][Type : DAO]");
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		reportrange = reportrange.substring(1);
		String startDate = reportrange.trim().split(" - ")[0];
		String endDate = reportrange.trim().split(" - ")[1];
		strQuery = strQuery.append(" select w.* from worksheet w ");
		strQuery = strQuery.append(" join service_application sa on sa.id = w.serviceApplicationId ");
		strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
		strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
		strQuery = strQuery.append(" join history_technician_groupwork htg on z.id = ad.zoneId ");
		strQuery = strQuery.append(" where w.isDeleted = false and w.createdBy != 'MigrateEasyNet' ");

		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			strQuery = strQuery.append(" and htg.statusHistory = 'S' ");
			strQuery = strQuery.append(" and (DATE(htg.assignDate) BETWEEN :startDate AND :endDate) ");
		}
		
		if (!"0".equals(technician)) {
			strQuery = strQuery.append(" and htg.technicianGroupId = :technician ");
		}
		
		if (!"0".equals(jobType)) {
			strQuery = strQuery.append(" and w.workSheetType = :workSheetType ");
		}
		
		if (!"0".equals(worksheetStatus)) {
			strQuery = strQuery.append(" and w.status = :worksheetStatus ");
		}
		
		if (!"0".equals(zone)) {
			strQuery = strQuery.append(" and  ad.addressType = '3' and z.id = :zoneId ");
		}else{
			strQuery = strQuery.append(" and  ad.addressType = '3' ");
		}
		
		strQuery = strQuery.append(" GROUP by w.id ");

		logger.info("[method : getDataWorksheetForReport][Query : " + strQuery.toString() + "]");

		// prepare statement
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		
		query.addEntity(Worksheet.class);
		
		if (!"0".equals(technician)) {
			query.setString("technician", technician);
		}
		
		if (!"0".equals(jobType)) {
			query.setString("workSheetType", jobType);
		}
		
		if (!"0".equals(worksheetStatus)) {
			query.setString("worksheetStatus", worksheetStatus);
		}
		
		if (!"0".equals(zone)) {
			query.setLong("zoneId", Long.parseLong(zone));
		}
		
		if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
			try {
				query.setDate("startDate", formatDataEngRange.parse(startDate.trim()));
				query.setDate("endDate", formatDataEngRange.parse(endDate.trim()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// execute
		List<Worksheet> worksheets = query.list();

		return worksheets;
	}

	public WorksheetAnalyzeProblems getWorksheetAnalyzeProblemsById(Long id) {
		logger.info("[method : getWorksheetAnalyzeProblemsById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from WorksheetAnalyzeProblems wap where wap.isDeleted = false and wap.id = :id");
		logger.info("[method : getWorksheetAnalyzeProblemsById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		WorksheetAnalyzeProblems worksheetAnalyzeProblems = (WorksheetAnalyzeProblems) query.uniqueResult();
		return worksheetAnalyzeProblems;
	}

	public void updateWorksheetAnalyzeProblems(WorksheetAnalyzeProblems worksheetAnalyzeProblems) throws Exception {
		logger.info("[method : updateWorksheetAnalyzeProblems][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(worksheetAnalyzeProblems);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}
	
}
