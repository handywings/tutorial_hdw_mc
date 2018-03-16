package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.EquipmentProductCategoryDAO;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.service.EquipmentProductCategoryService;

@Service
public class EquipmentProductCategoryServiceImpl implements EquipmentProductCategoryService{

	private EquipmentProductCategoryDAO equipmentProductCategoryDAO;
	
	public void setEquipmentProductCategoryDAO(EquipmentProductCategoryDAO equipmentProductCategoryDAO) {
		this.equipmentProductCategoryDAO = equipmentProductCategoryDAO;
	}

	@Transactional
	public EquipmentProductCategory getEquipmentProductCategoryById(Long id) {
		return equipmentProductCategoryDAO.getEquipmentProductCategoryById(id);
	}

	@Transactional
	public List<EquipmentProductCategory> findAll() {
		return equipmentProductCategoryDAO.findAll();
	}

	@Transactional
	public void update(EquipmentProductCategory equipmentProductCategory) throws Exception {
		equipmentProductCategoryDAO.update(equipmentProductCategory);
	}

	@Transactional
	public Long save(EquipmentProductCategory equipmentProductCategory) throws Exception {
		return equipmentProductCategoryDAO.save(equipmentProductCategory);
	}

	@Transactional
	public void delete(EquipmentProductCategory equipmentProductCategory) throws Exception {
		equipmentProductCategoryDAO.delete(equipmentProductCategory);
	}

	@Transactional
	public String genEquipmentProductCategoryCode() throws Exception {
		return equipmentProductCategoryDAO.genEquipmentProductCategoryCode();
	}
	
	@Transactional
	public List<EquipmentProductCategory> findTypeEquipmentOnly() {
		return equipmentProductCategoryDAO.findTypeEquipmentOnly();
	}
	
	@Transactional
	public EquipmentProductCategory getEquipmentProductCategoryByCode(String equipmentProductCategoryCode) {
		return equipmentProductCategoryDAO.getEquipmentProductCategoryByCode(equipmentProductCategoryCode);
	}
	
	@Transactional
	public EquipmentProductCategory getEquipmentProductCategoryByEquipmentProductCategoryName(
			String equipmentProductCategoryName) {
		return equipmentProductCategoryDAO.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategoryName);
	}

	@Transactional
	public List<EquipmentProductCategory> findTypeEquipmentAndService() {
		return equipmentProductCategoryDAO.findTypeEquipmentAndService();
	}

}
