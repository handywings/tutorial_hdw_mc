package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.utils.Pagination;

public interface UnitDAO {
	public Unit getUnitById(Long id);
	public Unit getUnitByUnitName(String unitName);
	public List<Unit> findAll();
	public void update(Unit unit) throws Exception;
	public Long save(Unit unit) throws Exception;
	public void delete(Unit unit) throws Exception;
	public Pagination getByPage(Pagination pagination);
	public int getCountTotal();
	
	public List<MenuReport> findAllMenuReport();
	public Long save(MenuReport menuReport) throws Exception;
	public MenuReport getMenuReportById(Long id);
	public void delete(MenuReport menuReport) throws Exception;
	public void update(MenuReport menuReport) throws Exception;
	public MenuReport getMenuReportByName(String menuReportName);
	
}
