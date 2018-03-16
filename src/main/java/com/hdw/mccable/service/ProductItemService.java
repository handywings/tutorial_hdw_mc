package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;

public interface ProductItemService {
	
	public ProductItem getProductItemById(Long id);
	public Long save(ProductItem productItem) throws Exception;
	public void update(ProductItem productItem) throws Exception;
	public void deleteByServiceApplicationId(Long id);
	public void deleteByServiceApplicationIdAndWorksheet(Long id);
	public void deleteWorksheetIdAndProductType(Long worksheetId, String productType);
	public List<ProductItem> getProductItemByServiceApplicationId(Long serviceApplicationId) throws Exception;
	public ProductItemWorksheet getProductItemByEquipmentProductItemId(Long id);
	public void deleteProductItemById(Long productItemId) throws Exception;
}
