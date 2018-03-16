package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ServicePackageTypeDAO;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.service.ServicePackageTypeService;

@Service
public class ServicePackageTypeServiceImpl implements ServicePackageTypeService{

	private ServicePackageTypeDAO servicePackageTypeDAO;
	
	public void setServicePackageTypeDAO(ServicePackageTypeDAO servicePackageTypeDAO) {
		this.servicePackageTypeDAO = servicePackageTypeDAO;
	}
	
	@Transactional
	public ServicePackageType getServicePackageTypeById(Long id) {
		return this.servicePackageTypeDAO.getServicePackageTypeById(id);
	}

	@Transactional
	public List<ServicePackageType> findAll() {
		return this.servicePackageTypeDAO.findAll();
	}

	@Transactional
	public void update(ServicePackageType servicePackageType) throws Exception {
		this.servicePackageTypeDAO.update(servicePackageType);	
	}

	@Transactional
	public Long save(ServicePackageType servicePackageType) throws Exception {
		return this.servicePackageTypeDAO.save(servicePackageType);
	}

	@Transactional
	public void delete(ServicePackageType servicePackageType) throws Exception {
		this.servicePackageTypeDAO.delete(servicePackageType);
	}

	@Transactional
	public String genPackageTypeCode() throws Exception {
		return this.servicePackageTypeDAO.genPackageTypeCode();
	}

	@Transactional
	public int getNumberOfCountPackage(Long servicePackageTypeID) throws Exception {
		return this.servicePackageTypeDAO.getNumberOfCountPackage(servicePackageTypeID);
	}

	@Transactional
	public ServicePackageType getServicePackageTypeByPackageTypeCode(String packageTypeCode) throws Exception {
		return this.servicePackageTypeDAO.getServicePackageTypeByPackageTypeCode(packageTypeCode);
	}

	@Transactional
	public ServicePackageType getServicePackageTypeByPackageTypeName(String packageTypeName) throws Exception {
		return this.servicePackageTypeDAO.getServicePackageTypeByPackageTypeName(packageTypeName);
	}
	
}
