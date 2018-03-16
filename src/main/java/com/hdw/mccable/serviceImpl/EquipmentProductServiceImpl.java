package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.EquipmentProductDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.service.EquipmentProductService;

@Service
public class EquipmentProductServiceImpl implements EquipmentProductService{

	private EquipmentProductDAO equipmentProductDAO;
	
	public void setEquipmentProductDAO(EquipmentProductDAO equipmentProductDAO) {
		this.equipmentProductDAO = equipmentProductDAO;
	}

	@Transactional
	public void update(EquipmentProduct equipmentProduct) throws Exception {
		this.equipmentProductDAO.update(equipmentProduct);
	}

	@Transactional
	public EquipmentProduct getEquipmentProductById(Long id) {
		return this.equipmentProductDAO.getEquipmentProductById(id);
	}
	
	@Transactional
	public EquipmentProduct getEquipmentProductByProductName(String productName) {
		return this.equipmentProductDAO.getEquipmentProductByProductName(productName);
	}
	
	@Transactional
	public EquipmentProduct getEquipmentProductByProductCode(String productCode) {
		return this.equipmentProductDAO.getEquipmentProductByProductCode(productCode);
	}

	@Transactional
	public EquipmentProduct getEquipmentProductByProductNameAndStock(String productName, Long stockId) {
		return this.equipmentProductDAO.getEquipmentProductByProductNameAndStock(productName,stockId);
	}

	@Transactional
	public EquipmentProduct getEquipmentProductByProductCodeAndStock(String productCode, Long stockId) {
		return this.equipmentProductDAO.getEquipmentProductByProductCodeAndStock(productCode,stockId);
	}

	@Transactional
	public List<EquipmentProductItem> getEquipmentProductItemByParentId(Long equipmentProductId) {
		return this.equipmentProductDAO.getEquipmentProductItemByParentId(equipmentProductId);
	}

	@Transactional
	public List<String> getSupplier() {
		return this.equipmentProductDAO.getSupplier();
	}

}
