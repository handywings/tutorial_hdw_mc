package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;

public interface EquipmentProductService {
	
	public EquipmentProduct getEquipmentProductById(Long id);
	public EquipmentProduct getEquipmentProductByProductName(String productName);
	public EquipmentProduct getEquipmentProductByProductCode(String productCode);
	
	public EquipmentProduct getEquipmentProductByProductNameAndStock(String productName, Long stockId);
	public EquipmentProduct getEquipmentProductByProductCodeAndStock(String productCode, Long stockId);
	
	public void update(EquipmentProduct iquipmentProduct) throws Exception;
	public List<EquipmentProductItem> getEquipmentProductItemByParentId(Long equipmentProductId);
	public List<String> getSupplier();
}
