package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hdw.mccable.dao.UnitDAO;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.Pagination;

@Service
public class UnitServiceImpl implements UnitService{

	private UnitDAO unitDAO;
	
	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}
	
	@Transactional
	public Unit getUnitById(Long id) {
		return this.unitDAO.getUnitById(id);
	}

	@Transactional
	public Unit getUnitByUnitName(String unitName) {
		return this.unitDAO.getUnitByUnitName(unitName);
	}

	@Transactional
	public List<Unit> findAll() {
		return this.unitDAO.findAll();
	}

	@Transactional
	public void update(Unit unit) throws Exception {
		this.unitDAO.update(unit);	
	}

	@Transactional
	public Long save(Unit unit) throws Exception {
		return this.unitDAO.save(unit);
	}

	@Transactional
	public void delete(Unit unit) throws Exception {
		this.unitDAO.delete(unit);
	}
	
	@Transactional
	public Pagination getByPage(Pagination pagination) {
		return unitDAO.getByPage(pagination);
	}
	
	@Transactional
	public int getCountTotal() {
		return unitDAO.getCountTotal();
	}

	@Transactional
	public List<MenuReport> findAllMenuReport() {
		return unitDAO.findAllMenuReport();
	}

	@Transactional
	public MenuReport getMenuReportById(Long id) {
		return unitDAO.getMenuReportById(id);
	}

	@Transactional
	public Long save(MenuReport menuReport) throws Exception {
		return this.unitDAO.save(menuReport);
	}

	@Transactional
	public void delete(MenuReport menuReport) throws Exception {
		this.unitDAO.delete(menuReport);
	}

	@Transactional
	public void update(MenuReport menuReport) throws Exception {
		this.unitDAO.update(menuReport);
	}

	@Transactional
	public MenuReport getMenuReportByName(String menuReportName) {
		return this.unitDAO.getMenuReportByName(menuReportName);
	}

}
