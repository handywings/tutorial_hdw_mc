package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ProductItemDAO;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.service.ProductItemService;

@Service
public class ProductItemServiceImpl implements ProductItemService{

	private ProductItemDAO productItemDAO;
	
	public void setProductItemDAO(ProductItemDAO productItemDAO) {
		this.productItemDAO = productItemDAO;
	}

	@Transactional
	public ProductItem getProductItemById(Long id) {
		return productItemDAO.getProductItemById(id);
	}

	@Transactional
	public Long save(ProductItem productItem) throws Exception {
		return productItemDAO.save(productItem);
	}

	@Transactional
	public void update(ProductItem productItem) throws Exception {
		productItemDAO.update(productItem);
	}

	@Transactional
	public void deleteByServiceApplicationId(Long serviceApplicationId) {
		productItemDAO.deleteByServiceApplicationId(serviceApplicationId);
	}

	@Transactional
	public List<ProductItem> getProductItemByServiceApplicationId(Long serviceApplicationId) throws Exception {
		return productItemDAO.getProductItemByServiceApplicationId(serviceApplicationId);
	}

	@Transactional
	public void deleteByServiceApplicationIdAndWorksheet(Long serviceApplicationId) {
		productItemDAO.deleteByServiceApplicationIdAndWorksheet(serviceApplicationId);
	}
	
	@Transactional
	public ProductItemWorksheet getProductItemByEquipmentProductItemId(Long id) {
		return productItemDAO.getProductItemWorksheetByEquipmentProductItemId(id);
	}

	@Transactional
	public void deleteWorksheetIdAndProductType(Long worksheetId, String productType) {
		productItemDAO.deleteWorksheetIdAndProductType(worksheetId,productType);
	}

	@Transactional
	public void deleteProductItemById(Long productItemId) throws Exception {
		productItemDAO.deleteProductItemById(productItemId);
	}
	
	
}
