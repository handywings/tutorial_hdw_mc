package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.Bank;

public interface BankDAO {
	public List<Bank> findAll();
	public Bank getBankById(Long id);
}
