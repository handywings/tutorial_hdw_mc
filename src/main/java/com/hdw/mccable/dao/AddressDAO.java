package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ZipCode;

public interface AddressDAO {
	public Long save(Address address) throws Exception;
	public Province getProvinceById(Long id);
	public Province getProvinceByProvinceName(String provinceName);
	public List<Province> findAll();
	public Address getAddressById(Long id);
	public void updateAddress(Address address) throws Exception;
	public Amphur getAmphurById(Long id);
	public District getDistrictById(Long id);
	public District getDistrictByDistrictName(String districtName);
	public List<District> getDistrictByAmphurId(Long id);
	public void deleteByServiceApplicationId(Long serviceApplicationId);
	public ZipCode getZipCodeByDistrictCode(String districtCode);
	public Amphur getAmphurByAmphurName(String amphurName);
	public District getDistrictByDistrictNameANDAmphurId(String districtName, Long amphurId);
	public void deleteByCustomerId(Long customerId);
	public void update(Address address) throws Exception;
	
	
}
