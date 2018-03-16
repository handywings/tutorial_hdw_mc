package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.ProductItemWorksheet;

public interface ProductItemWorksheetDAO {

	public ProductItemWorksheet getProductItemWorksheetById(Long id);
	public Long save(ProductItemWorksheet productItemWorksheet) throws Exception;
	public void update(ProductItemWorksheet productItemWorksheet) throws Exception;
	public void deleteByProductItemId(Long productItemId);
	public List<ProductItemWorksheet> getProductItemWorksheetByProductItemId(Long productItemId) throws Exception;

}
