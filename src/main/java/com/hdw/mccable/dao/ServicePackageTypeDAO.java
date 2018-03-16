package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.ServicePackageType;


public interface ServicePackageTypeDAO {
	public ServicePackageType getServicePackageTypeById(Long id);
	public List<ServicePackageType> findAll();
	public void update(ServicePackageType servicePackageType) throws Exception;
	public Long save(ServicePackageType servicePackageType) throws Exception;
	public void delete(ServicePackageType servicePackageType) throws Exception;
	public String genPackageTypeCode() throws Exception;
	public int getNumberOfCountPackage(Long servicePackageTypeID) throws Exception;
	public ServicePackageType getServicePackageTypeByPackageTypeCode(String packageTypeCode) throws Exception;
	public ServicePackageType getServicePackageTypeByPackageTypeName(String packageTypeName) throws Exception;
}
