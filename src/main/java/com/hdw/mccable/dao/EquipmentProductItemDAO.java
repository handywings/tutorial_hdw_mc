package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;


public interface EquipmentProductItemDAO {

	public EquipmentProductItem getEquipmentProductItemById(Long id);
	public EquipmentProductItem getEquipmentProductItemBySerialNo(String serialNo);
	public void update(EquipmentProductItem iquipmentProductItem) throws Exception;
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSN(ServiceApplication serviceApplication);
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSNAllStatus(ServiceApplication serviceApplication);
	public List<ProductItemWorksheet> loadInternetProductItemByserviceApplicationId(ServiceApplication serviceApplication);
	public void save(EquipmentProductItem equipmentProductItem) throws Exception;
}
