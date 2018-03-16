package com.hdw.mccable.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.PersonnelDAO;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.service.PersonnelService;

@Service
public class PersonnelServiceImpl implements PersonnelService{
	
	private PersonnelDAO personnelDAO;

	public void setPersonnelDAO(PersonnelDAO personnelDAO) {
		this.personnelDAO = personnelDAO;
	}
	
	@Transactional
	public List<Personnel> searchBykey(String criteria, String position, Long permissionGroup) {
		return personnelDAO.searchBykey(criteria, position, permissionGroup);
	}
	
	@Transactional
	public boolean checkDuplicatePersonnelCode(String personnelCode) throws Exception {
		Personnel personnel = personnelDAO.findByPersonnelCode(personnelCode);
		if(personnel != null){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Transactional
	public Long save(Personnel personnel) throws Exception {
//		Long personnelId = null;
//		if(!personnel.getPersonnelCode().isEmpty()){
//			return this.personnelDAO.save(personnel);
//		}else{
//			personnelId = this.personnelDAO.save(personnel);
//			Personnel personnelTemp = this.personnelDAO.getPersonnelById(personnelId);
//			if(personnelTemp != null){
//				personnelTemp.setPersonnelCode(generatePersonnelCode(personnelId));
//				this.personnelDAO.update(personnelTemp);
//			}
//		}
//		return personnelId;
		
		return this.personnelDAO.save(personnel);
	}
	
	@Transactional
	public void delete(Personnel personnel) throws Exception {
		this.personnelDAO.delete(personnel);
	}
	
	@Transactional
	public Personnel getPersonnelById(Long id) {
		return this.personnelDAO.getPersonnelById(id);
	}
	
	@Transactional
	public void update(Personnel personnel) throws Exception {
		this.personnelDAO.update(personnel);
	}
	
	@Transactional
	public List<Personnel> getPersonnelNotMember() {
		return this.personnelDAO.getPersonnelNotMember();
	}
	
	@Transactional
	public List<Personnel> getPersonnelNotMember(Long id) {
		return this.personnelDAO.getPersonnelNotMember(id);
	}
	
	@Transactional
	public int removePersonnelRefTechnicianGroup(Long technicianGroupId) throws Exception {
		return this.personnelDAO.removePersonnelRefTechnicianGroup(technicianGroupId);
		
	}

	@Transactional
	public List<Personnel> findPersonnelCashier(Boolean condition) throws Exception {
		return this.personnelDAO.findPersonnelCashier(condition);
	}
	
	@Transactional
	public List<PersonnelAssign> findPersonnelAssign(Long personnelId, String dateAssign) {
		return this.personnelDAO.findPersonnelAssign(personnelId, dateAssign);
	}

	@Transactional
	public CashierBean searchCashierByCriteria(Map<String, Object> cretiria) throws Exception {
		return this.personnelDAO.searchCashierByCriteria(cretiria);
	}

}
