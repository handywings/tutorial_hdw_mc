package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.EquipmentProductCategory;


public interface EquipmentProductCategoryService {
	public EquipmentProductCategory getEquipmentProductCategoryById(Long id);
	public List<EquipmentProductCategory> findAll();
	public void update(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public Long save(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public void delete(EquipmentProductCategory equipmentProductCategory) throws Exception;
	public String genEquipmentProductCategoryCode() throws Exception;
	public List<EquipmentProductCategory> findTypeEquipmentOnly();
	public List<EquipmentProductCategory> findTypeEquipmentAndService();
	public EquipmentProductCategory getEquipmentProductCategoryByCode(String equipmentProductCategoryCode);
	public EquipmentProductCategory getEquipmentProductCategoryByEquipmentProductCategoryName(
			String equipmentProductCategoryName);
}
