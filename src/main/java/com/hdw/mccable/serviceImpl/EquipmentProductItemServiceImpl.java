package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.EquipmentProductItemDAO;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.service.EquipmentProductItemService;

@Service
public class EquipmentProductItemServiceImpl implements EquipmentProductItemService{

	private EquipmentProductItemDAO equipmentProductItemDAO;
	
	public void setEquipmentProductItemDAO(EquipmentProductItemDAO equipmentProductItemDAO) {
		this.equipmentProductItemDAO = equipmentProductItemDAO;
	}

	@Transactional
	public void save(EquipmentProductItem equipmentProductItem) throws Exception {
		this.equipmentProductItemDAO.save(equipmentProductItem);
	}

	@Transactional
	public void update(EquipmentProductItem iquipmentProductItem) throws Exception {
		this.equipmentProductItemDAO.update(iquipmentProductItem);
	}

	@Transactional
	public EquipmentProductItem getEquipmentProductItemById(Long id) {
		return this.equipmentProductItemDAO.getEquipmentProductItemById(id);
	}
	
	@Transactional
	public EquipmentProductItem getEquipmentProductItemBySerialNo(String serialNo) {
		return this.equipmentProductItemDAO.getEquipmentProductItemBySerialNo(serialNo);
	}

	@Transactional
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSN(ServiceApplication serviceApplication) {
		return this.equipmentProductItemDAO.loadEquipmentProductItemHasSN(serviceApplication);
	}
	
	@Transactional
	public List<ProductItemWorksheet> loadEquipmentProductItemHasSNAllStatus(ServiceApplication serviceApplication) {
		return this.equipmentProductItemDAO.loadEquipmentProductItemHasSNAllStatus(serviceApplication);
	}
	
	@Transactional
	public List<ProductItemWorksheet> loadInternetProductItemByserviceApplicationId(ServiceApplication serviceApplication) {
		return this.equipmentProductItemDAO.loadInternetProductItemByserviceApplicationId(serviceApplication);
	}
}
