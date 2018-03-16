package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.StockDAO;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.service.StockService;

@Service
public class StockServiceImpl implements StockService{

	private StockDAO stockDAO;
	
	public void setStockDAO(StockDAO stockDAO) {
		this.stockDAO = stockDAO;
	}

	@Transactional
	public Stock getStockById(Long id) {
		return stockDAO.getStockById(id);
	}

	@Transactional
	public Stock getStockByStockName(String stockName) {
		return stockDAO.getStockByStockName(stockName);
	}

	@Transactional
	public List<Stock> findAll() {
		return stockDAO.findAll();
	}

	@Transactional
	public List<Stock> findByCompany(Long companyId) {
		return stockDAO.findByCompany(companyId);
	}

	@Transactional
	public void update(Stock stock) throws Exception {
		stockDAO.update(stock);
	}

	@Transactional
	public Long save(Stock stock) throws Exception {
		return stockDAO.save(stock);
	}

	@Transactional
	public void delete(Stock stock) throws Exception {
		stockDAO.delete(stock);
	}
	
	@Transactional
	public String genStockCode() throws Exception {
		return stockDAO.genStockCode();
	}
	
	@Transactional
	public List<Stock> findAllOrderCompany() {
		return stockDAO.findAllOrderCompany();
	}

}
