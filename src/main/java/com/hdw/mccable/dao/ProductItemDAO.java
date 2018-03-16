package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;

public interface ProductItemDAO {

	public ProductItem getProductItemById(Long id);
	public Long save(ProductItem productItem) throws Exception;
	public void update(ProductItem productItem) throws Exception;
	public void deleteByServiceApplicationId(Long serviceApplicationId);
	public List<ProductItem> getProductItemByServiceApplicationId(Long serviceApplicationId) throws Exception;
	public void deleteByServiceApplicationIdAndWorksheet(Long serviceApplicationId);
	public ProductItemWorksheet getProductItemWorksheetByEquipmentProductItemId(Long id);
	public void deleteWorksheetIdAndProductType(Long worksheetId, String productType);
	public void deleteProductItemById(Long productItemId) throws Exception;
}
