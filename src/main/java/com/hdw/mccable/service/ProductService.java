package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.ServiceProduct;

public interface ProductService {
	List<EquipmentProduct> searchByTypeEquipment(String criteria, Long equipmentProductCategoryId, Long stockId);
	List<ServiceProduct> searchByTypeService(String criteria, Long equipmentProductCategoryId, Long stockId);
	List<InternetProduct> searchByTypeInternet(String criteria, Long equipmentProductCategoryId, Long stockId);
	
	List<EquipmentProduct> findByStockAndProductCode(Long stockId, String productCode);
	EquipmentProduct findEquipmentProductById(Long id);
	InternetProduct findInternetProductById(Long id);
	public Long save(EquipmentProductItem equipmentProductItem) throws Exception;
	public Long saveMasterProduct(EquipmentProduct equipmentProduct) throws Exception;
	EquipmentProductItem findEquipmentProductItemById(Long id);
	public void update(EquipmentProduct equipmentProduct) throws Exception;
	public void deleteProductItemGroup(EquipmentProduct equipmentProduct) throws Exception;
	public void updateProductItem(EquipmentProductItem equipmentProductItem) throws Exception;
	List<EquipmentProduct> searchByTypeEquipment(String criteria, String equipmentProductCategoryCode, Long stockId);

	List<EquipmentProduct> findAllSupplier();
	
	List<EquipmentProduct> getDataProductForReport(String reportrange, String purchasingNumber, String equipmentProductType,
			String equipmentStock, String supplier, String split, String sort);
	List<RequisitionDocument> getDataRequisitionProductForReport(String reportrange, String equipmentProductType,
			String equipmentStock, String split, String sort);
	List<EquipmentProduct> getDataStocksummaryForReport(String equipmentProductType, String equipmentStock,
			String split);
	public String genProductCode() throws Exception;
}
