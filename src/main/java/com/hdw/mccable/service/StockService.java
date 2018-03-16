package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Stock;

public interface StockService {
	public Stock getStockById(Long id);
	public Stock getStockByStockName(String stockName);
	public List<Stock> findAll();
	public List<Stock> findByCompany(Long companyId);
	public void update(Stock stock) throws Exception;
	public Long save(Stock stock) throws Exception;
	public void delete(Stock stock) throws Exception;
	public String genStockCode() throws Exception;
	public List<Stock> findAllOrderCompany();
}
