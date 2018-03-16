package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ZipCode;

public interface AddressService {
	public Long save(Address address) throws Exception;
	public void update(Address address) throws Exception;
	public List<Province> findAll();
	public Province getProvinceById(Long id);
	public Province getProvinceByProvinceName(String provinceName);
//	public List<Amphur> findAmphurByProvince(Long provinceId);
//	public List<District> findDistrictByAmphure(Long amphurId);
	public Address getAddressById(Long id);
	public void updateAddress(Address address) throws Exception;
	public Amphur getAmphurById(Long id);
	public District getDistrictById(Long id);
	public District getDistrictByDistrictName(String districtName);
	public ZipCode getZipCodeByDistrictCode(String DistrictCode);
	public List<District> getDistrictByAmphurId(Long id);
	
	public void deleteByServiceApplicationId(Long serviceApplicationId) throws Exception;
	
	public Amphur getAmphurByAmphurName(String amphurName);
	public District getDistrictByDistrictNameANDAmphurId(String districtName, Long amphurId);
	
	public void deleteByCustomerId(Long customerId) throws Exception;
	
}
