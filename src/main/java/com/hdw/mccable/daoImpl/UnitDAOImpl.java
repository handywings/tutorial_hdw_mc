package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.UnitDAO;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.utils.Pagination;

@Repository
public class UnitDAOImpl implements UnitDAO {
	private static final Logger logger = LoggerFactory.getLogger(UnitDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	public Unit getUnitById(Long id) {
		logger.info("[method : getUnitById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Unit unit = (Unit) session.get(Unit.class, id);
		return unit;
	}

	public Unit getUnitByUnitName(String unitName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Unit u where u.unitName = :unitName");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("unitName", unitName);

		// execute
		Unit unit = (Unit) query.uniqueResult();

		return unit;
	}

	@SuppressWarnings("unchecked")
	public List<Unit> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Unit> unit = (List<Unit>) session.createQuery("from Unit where isDeleted = false").list();
		return unit;
	}

	public void update(Unit unit) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(unit);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long save(Unit unit) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(unit);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void delete(Unit unit) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		unit.setDeleted(true);
		update(unit);
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPage(Pagination pagination) {
		logger.info("[method : getByPage][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		strQuery = strQuery.append(" from Unit unit where unit.isDeleted = false order by unit.id ");

		Query query = session.createQuery(strQuery.toString());
		if (pagination != null) {
			query.setFirstResult(pagination.getLimitStart());
			query.setMaxResults(pagination.getLimitEnd());
		}
		// execute
		List<Unit> units = query.list();
		pagination.setDataList(units);

		return pagination;
	}

	public int getCountTotal() {
		logger.info("[method : getCountTotal][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		int count = ((Number) session.createQuery("select count(*) from  Unit").uniqueResult()).intValue();
		logger.info("[method : getCountTotal][Type : DAO][size : "+ count +"]");
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<MenuReport> findAllMenuReport() {
		logger.info("[method : findAllMenuReport][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<MenuReport> menuReports = (List<MenuReport>) session.createQuery("from MenuReport").list();
		return menuReports;
	}

	public Long save(MenuReport menuReport) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(menuReport);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public MenuReport getMenuReportById(Long id) {
		logger.info("[method : getMenuReportById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		MenuReport menuReport = (MenuReport) session.get(MenuReport.class, id);
		return menuReport;
	}

	public void delete(MenuReport menuReport) throws Exception {
		logger.info("[method : delete][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.delete(menuReport);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void update(MenuReport menuReport) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(menuReport);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public MenuReport getMenuReportByName(String menuReportName) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from MenuReport mr where mr.menuReportName = :menuReportName");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("menuReportName", menuReportName);

		// execute
		MenuReport menuReport = (MenuReport) query.uniqueResult();

		return menuReport;
	}

}
