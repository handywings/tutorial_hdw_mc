package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Bank;

public interface BankService {
	public List<Bank> findAll();
	public Bank getBankById(Long id);
}
