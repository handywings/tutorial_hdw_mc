package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.BankDAO;
import com.hdw.mccable.entity.Bank;
import com.hdw.mccable.service.BankService;

@Service
public class BankServiceImpl implements BankService{
	
	private BankDAO bankDAO;
	
	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	@Transactional
	public List<Bank> findAll() {
		return bankDAO.findAll();
	}
	
	@Transactional
	public Bank getBankById(Long id) {
		return bankDAO.getBankById(id);
	}
}
