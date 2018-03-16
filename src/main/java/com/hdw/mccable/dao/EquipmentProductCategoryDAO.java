package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.EquipmentProductCategory;

public interface EquipmentProductCategoryDAO {
	public EquipmentProductCategory getEquipmentProductCategoryById(Long id);
	public List<EquipmentProductCategory> findAll();
	public void update(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public Long save(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public void delete(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public String genEquipmentProductCategoryCode() throws Exception;
	public List<EquipmentProductCategory> findTypeEquipmentOnly();
	public EquipmentProductCategory getEquipmentProductCategoryByCode(String equipmentProductCategoryCode);
	public EquipmentProductCategory getEquipmentProductCategoryByEquipmentProductCategoryName(
			String equipmentProductCategoryName);
	public List<EquipmentProductCategory> findTypeEquipmentAndService();
}
