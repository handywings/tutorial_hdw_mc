package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hdw.mccable.dao.ProductItemWorksheetDAO;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.service.ProductItemWorksheetService;

@Service
public class ProductItemWorksheetServiceImpl implements ProductItemWorksheetService{

	private ProductItemWorksheetDAO productItemWorksheetDAO;
	
	public void setProductItemWorksheetDAO(ProductItemWorksheetDAO productItemWorksheetDAO) {
		this.productItemWorksheetDAO = productItemWorksheetDAO;
	}

	@Transactional
	public ProductItemWorksheet getProductItemWorksheetById(Long id) {
		return productItemWorksheetDAO.getProductItemWorksheetById(id);
	}

	@Transactional
	public Long save(ProductItemWorksheet productItemWorksheet) throws Exception {
		return productItemWorksheetDAO.save(productItemWorksheet);
	}

	@Transactional
	public void update(ProductItemWorksheet productItemWorksheet) throws Exception {
		productItemWorksheetDAO.update(productItemWorksheet);
	}

	@Transactional
	public void deleteByProductItemId(Long id) {
		productItemWorksheetDAO.deleteByProductItemId(id);
	}

	@Transactional
	public List<ProductItemWorksheet> getProductItemWorksheetByProductItemId(Long productItemId) throws Exception {
		return productItemWorksheetDAO.getProductItemWorksheetByProductItemId(productItemId);
	}


}
