package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.AddressDAO;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ZipCode;
import com.hdw.mccable.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	
	private AddressDAO addressDAO;

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Transactional
	public Province getProvinceById(Long id) {
		return addressDAO.getProvinceById(id);
	}

	@Transactional
	public Province getProvinceByProvinceName(String provinceName) {
		return addressDAO.getProvinceByProvinceName(provinceName);
	}

	@Transactional
	public List<Province> findAll() {
		return addressDAO.findAll();
	}
	
	@Transactional
	public Address getAddressById(Long id) {
		return addressDAO.getAddressById(id);
	}

	@Transactional
	public void updateAddress(Address address) throws Exception {
		addressDAO.updateAddress(address);
	}
	
	@Transactional
	public Amphur getAmphurById(Long id) {
		return addressDAO.getAmphurById(id);
	}
	
	@Transactional
	public District getDistrictById(Long id) {
		return addressDAO.getDistrictById(id);
	}
	
	@Transactional
	public District getDistrictByDistrictName(String districtName) {
		return addressDAO.getDistrictByDistrictName(districtName);
	}

	@Transactional
	public List<District> getDistrictByAmphurId(Long id) {
		return addressDAO.getDistrictByAmphurId(id);
	}

	@Transactional
	public Long save(Address address) throws Exception {
		return addressDAO.save(address);
	}

	@Transactional
	public void update(Address address) throws Exception {
		addressDAO.update(address);
	}

	@Transactional
	public void deleteByServiceApplicationId(Long serviceApplicationId) throws Exception {
		addressDAO.deleteByServiceApplicationId(serviceApplicationId);
	}

	@Transactional
	public void deleteByCustomerId(Long customerId) throws Exception {
		addressDAO.deleteByCustomerId(customerId);
	}

	@Transactional
	public ZipCode getZipCodeByDistrictCode(String DistrictCode) {
		return addressDAO.getZipCodeByDistrictCode(DistrictCode);
	}

	@Transactional
	public Amphur getAmphurByAmphurName(String amphurName) {
		return addressDAO.getAmphurByAmphurName(amphurName);
	}

	@Transactional
	public District getDistrictByDistrictNameANDAmphurId(String districtName, Long amphurId) {
		return addressDAO.getDistrictByDistrictNameANDAmphurId(districtName,amphurId);
	}
 
}
