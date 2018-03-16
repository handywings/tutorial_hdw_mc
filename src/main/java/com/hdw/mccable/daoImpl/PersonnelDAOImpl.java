package com.hdw.mccable.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.PersonnelDAO;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;

@Repository
public class PersonnelDAOImpl implements PersonnelDAO {

	private static final Logger logger = LoggerFactory.getLogger(PersonnelDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> searchBykey(String criteria, String position, Long permissionGroup) {
		logger.info("[method : searchBykey][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		// key
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append("from Personnel p where isDeleted = false and (p.firstName like :criteria ");
			strQuery = strQuery.append("or p.lastName like :criteria or p.personnelCode like :criteria or p.nickName like :criteria) and p.id != 1 ");
		} else {
			strQuery = strQuery.append("from Personnel p where 1=1 and p.isDeleted = false and p.id != 1 ");
		}
		// permisstion group
		if (permissionGroup != null && permissionGroup > 0) {
			strQuery = strQuery.append("and p.permissionGroup.id = :permissionGroup ");
		}
		// position
		if (position != null && (!position.isEmpty())) {
			strQuery = strQuery.append("and p.position.positionName = :positionName ");
		}

		logger.info("[method : searchBykey][Query : " + strQuery.toString() + "]");

		Query query = session.createQuery(strQuery.toString());

		// key
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}
		// permisstion group
		if (permissionGroup != null && permissionGroup > 0) {
			query.setLong("permissionGroup", permissionGroup);
		}
		// position
		if (position != null && (!position.isEmpty())) {
			query.setString("positionName", position);
		}
		// execute
		List<Personnel> personnels = query.list();

		return personnels;
	}

	public Personnel findByPersonnelCode(String personnelCode) throws Exception {
		logger.info("[method : findByPersonnelCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Personnel personnel = new Personnel();
		try {
			personnel = (Personnel) session.createQuery("from Personnel where personnelCode = :personnelCode and isDeleted = false and id != 1")
					.setString("personnelCode", personnelCode).uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
		return personnel;
	}

	public Long save(Personnel personnel) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(personnel);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Personnel getPersonnelById(Long id) {
		logger.info("[method : getPersonnelById][Type : DAO]");
		
		Personnel personnel = null;
		
		if(id != 1) {
			Session session = this.sessionFactory.getCurrentSession();
			personnel = (Personnel) session.get(Personnel.class, id);
		}
		
		return personnel;
	}

	public void update(Personnel personnel) throws Exception {
		logger.info("[method : update][Type : DAO]");

		if(personnel.getId() != 1) {
			Session session = this.sessionFactory.getCurrentSession();
			try{
				session.update(personnel);
				session.flush();
			}catch(Exception ex){
				throw(ex);
			}
		}		
	}

	public void delete(Personnel personnel) throws Exception {
		logger.info("[method : delete][Type : DAO]");

		if(personnel.getId() != 1) {
			Session session = this.sessionFactory.getCurrentSession();
			try{
				session.delete(personnel);
			}catch(Exception ex){
				throw(ex);
			}
		}		
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> getPersonnelNotMember() {
		logger.info("[method : getPersonnelNotMember][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Personnel> personnels = (List<Personnel>) session
				.createQuery("from Personnel where technicianGroupSub.id is null and isDeleted = false and id != 1").list();
		return personnels;
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> getPersonnelNotMember(Long id) {
		logger.info("[method : getPersonnelNotMember(id)][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Personnel where technicianGroupSub.id is null and isDeleted = false and id != 1");
		if (id != null && id > 0) {
			strQuery = strQuery.append(" or id = :id");
		}
		Query query = session.createQuery(strQuery.toString());

		// technician group id
		if (id != null && id > 0) {
			query.setLong("id", id);
		}

		// execute
		List<Personnel> personnels = query.list();

		return personnels;
	}

	public int removePersonnelRefTechnicianGroup(Long technicianGroupId) throws Exception {
		logger.info("[method : removePersonnelRefTechnicianGroup(technicianGroupId)][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		int result;
		try {
			if (technicianGroupId != null) {
				strQuery = strQuery.append("update Personnel set technicianGroupSub.id = null ");
				strQuery = strQuery.append("where technicianGroupSub.id = :technicianGroupId and isDeleted = false");
				Query query = session.createQuery(strQuery.toString());
				query.setLong("technicianGroupId", technicianGroupId);
				result = query.executeUpdate();
			} else {
				// input text for message exception
				throw new Exception("");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw (ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> findPersonnelCashier(Boolean condition) throws Exception {
		logger.info("[method : findPersonnelChasier][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Personnel where isCashier = :condition and isDeleted = false and id != 1 ");
		Query query = session.createQuery(strQuery.toString());

		// set condition
		query.setBoolean("condition", condition);

		// execute
		List<Personnel> personnels = query.list();
		return personnels;
	}

	@SuppressWarnings("unchecked")
	public CashierBean searchCashierByCriteria(Map<String, Object> cretiria) throws Exception {
		logger.info("[method : searchCashierByCriteria][Type : DAO]//////");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		// split date
		String startDate = "";
		String endDate = "";
		SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		if (!cretiria.get("daterange").equals("noDate")) {
			String[] dateCal = cretiria.get("daterange").toString().split(" - ");
			try {
				startDate = formatDataUs.format(formatDataTh.parse(dateCal[0]));
				endDate = formatDataUs.format(formatDataTh.parse(dateCal[1]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		strQuery = strQuery.append(" SELECT SUM(amount)sumAmount,COUNT(personnelId) totalBill");
		strQuery = strQuery.append(" FROM invoice");
		strQuery = strQuery.append(" WHERE status = 'S' AND personnelId = :personnelId ");
		if (!cretiria.get("daterange").equals("noDate")) {
			strQuery = strQuery.append(" AND (DATE(scanOutDate) BETWEEN :startDate AND :endDate)");
		}
		strQuery = strQuery.append(" GROUP BY personnelId");
		
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		String personnelId = cretiria.get("personnelId").toString();
		query.setString("personnelId", personnelId);
		if (!cretiria.get("daterange").equals("noDate")) {
			query.setString("startDate", startDate);
			query.setString("endDate", endDate);
		}
		
		logger.info("searchCashierByCriteria SQL : "+strQuery.toString());
		
		List<Object[]> obj = query.list();
		CashierBean cash = new CashierBean();
		Float sumAmount = (float) 0;
		Float commission = (float) 0;
		Long totalBil = 0L;
		if (obj.isEmpty()) {
			cash.setSumAmount(sumAmount);
			cash.setTotalBill(totalBil);
			cash.setCommission(commission);
		} else {
			for (Object[] objects : obj) {
				// add value to bean
				cash.setSumAmount(Float.valueOf(null==objects[0]?"0":objects[0].toString()));
				cash.setTotalBill(Long.valueOf(null==objects[1]?"0":objects[1].toString()));		
				cash.setCommission(commission);
			}	
		}						
		
		return cash;
	}
	
	public List<PersonnelAssign> findPersonnelAssign(Long personnelId, String dateAssign) {
		logger.info("[method : findPersonnelAssign][Type : DAO]");
		logger.info("[method : findPersonnelAssign][personnelId : "+ personnelId.toString() +"]");
		logger.info("[method : findPersonnelAssign][dateAssign : "+ dateAssign.toString() +"]");
		
		String startDate = "";
		String endDate = "";
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery = strQuery.append("select * FROM personnel_assign pa ");
		strQuery = strQuery.append(" where pa.personnelId = :personnelId ");
		
		SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		if(dateAssign != null && !dateAssign.isEmpty()){
			if(!"".equals(dateAssign)){
				String[] dateAssigns = dateAssign.split(" - ");
				if(dateAssigns.length > 1){
					try {
						startDate = formatDataUs.format(formatDataTh.parse(dateAssigns[0]));
						endDate = formatDataUs.format(formatDataTh.parse(dateAssigns[1]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			try {
				startDate = formatDataUs.format(new Date());
				endDate = formatDataUs.format(new Date());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!"".equals(startDate) && !"".equals(endDate)){
			strQuery = strQuery.append(" and  (DATE(pa.createDate) BETWEEN :startDate AND :endDate) ");
		}
		
		//prepare statement
		logger.info("[method : findPersonnelAssign][Query : " + strQuery.toString() + "]");
		SQLQuery query = session.createSQLQuery(strQuery.toString());
		query.addEntity(PersonnelAssign.class);
		
		//personnel id
		query.setLong("personnelId", personnelId);
		
		// createDate
		if (!"".equals(startDate) && !"".equals(endDate)) {
			query.setString("startDate", startDate);
			query.setString("endDate", endDate);
		}
		
		//execute
		List<PersonnelAssign> personnelAssignList = query.list();
		
		return personnelAssignList;
	}

}
