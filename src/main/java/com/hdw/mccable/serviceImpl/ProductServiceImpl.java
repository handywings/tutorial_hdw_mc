package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ProductDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Transactional
	public List<EquipmentProduct> searchByTypeEquipment(String criteria, Long equipmentProductCategoryId, Long stockId) {
		return productDAO.searchByTypeEquipment(criteria, equipmentProductCategoryId, stockId);
	}
	
	@Transactional
	public EquipmentProduct findEquipmentProductById(Long id) {
		return productDAO.findEquipmentProductById(id);
	}
	
	@Transactional
	public Long save(EquipmentProductItem equipmentProductItem) throws Exception {
		return productDAO.save(equipmentProductItem);
	}
	
	@Transactional
	public Long saveMasterProduct(EquipmentProduct equipmentProduct) throws Exception {
		return productDAO.saveMasterProduct(equipmentProduct);
	}
	
	@Transactional
	public EquipmentProductItem findEquipmentProductItemById(Long id) {
		return productDAO.findEquipmentProductItemById(id);
	}
	
	@Transactional
	public void update(EquipmentProduct equipmentProduct) throws Exception {
		productDAO.update(equipmentProduct);
	}
	
	@Transactional
	public void deleteProductItemGroup(EquipmentProduct equipmentProduct) throws Exception {
		productDAO.deleteProductItemGroup(equipmentProduct);
		
	}
	
	@Transactional
	public void updateProductItem(EquipmentProductItem equipmentProductItem) throws Exception {
		productDAO.updateProductItem(equipmentProductItem);
	}

	@Transactional
	public List<EquipmentProduct> findByStockAndProductCode(Long stockId, String productCode) {
		return productDAO.findByStockAndProductCode(stockId,productCode);
	}
	
	@Transactional
	public List<EquipmentProduct> searchByTypeEquipment(String criteria, String equipmentProductCategoryCode,
			Long stockId) { 
		
		return productDAO.searchByTypeEquipment(criteria, equipmentProductCategoryCode, stockId);
	}

	@Transactional
	public InternetProduct findInternetProductById(Long id) {
		return productDAO.findInternetProductById(id);
	}

	@Transactional
	public List<ServiceProduct> searchByTypeService(String criteria, Long equipmentProductCategoryId, Long stockId) {
		return productDAO.searchByTypeService(criteria, equipmentProductCategoryId, stockId);
	}

	@Transactional
	public List<InternetProduct> searchByTypeInternet(String criteria, Long equipmentProductCategoryId, Long stockId) {
		return productDAO.searchByTypeInternet(criteria, equipmentProductCategoryId, stockId);
	}

	@Transactional
	public List<EquipmentProduct> findAllSupplier() {
		return productDAO.findAllSupplier();
	}

	@Transactional
	public List<EquipmentProduct> getDataProductForReport(String reportrange, String purchasingNumber,
			String equipmentProductType, String equipmentStock, String supplier, String split, String sort) {
		return productDAO.getDataProductForReport(reportrange,purchasingNumber,equipmentProductType,equipmentStock,supplier,split,sort);
	}

	@Transactional
	public List<RequisitionDocument> getDataRequisitionProductForReport(String reportrange, String equipmentProductType,
			String equipmentStock, String split, String sort) {
		return productDAO.getDataRequisitionProductForReport(reportrange,equipmentProductType,equipmentStock,split,sort);
	}

	@Transactional
	public List<EquipmentProduct> getDataStocksummaryForReport(String equipmentProductType, String equipmentStock,
			String split) {
		return productDAO.getDataStocksummaryForReport(equipmentProductType,equipmentStock,split);
	}

	@Transactional
	public String genProductCode() throws Exception {
		return productDAO.genProductCode();
	}
	
	

}
