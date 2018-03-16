package com.hdw.mccable.service;

import java.util.List;
import java.util.Map;

import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;

public interface PersonnelService {
	public List<Personnel> searchBykey(String criteria, String position, Long permissionGroup);
	public boolean checkDuplicatePersonnelCode(String personnelCode) throws Exception;
	public Long save(Personnel personnel) throws Exception;
	public void delete(Personnel personnel) throws Exception;
	public Personnel getPersonnelById(Long id);
	public void update(Personnel personnel) throws Exception;
	public List<Personnel> getPersonnelNotMember();
	public List<Personnel> getPersonnelNotMember(Long id);
	public int removePersonnelRefTechnicianGroup(Long technicianGroupId) throws Exception;
	public List<Personnel> findPersonnelCashier(Boolean condition) throws Exception;
	public CashierBean searchCashierByCriteria(Map<String, Object> cretiria) throws Exception;
	public List<PersonnelAssign> findPersonnelAssign(Long personnelId, String dateAssign);
}
