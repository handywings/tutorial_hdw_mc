package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.TemplateService;

public interface ServicePackageDAO {
	public List<ServicePackage> searchBykey(String criteria, String servicePackageTypeCode, String status, Long companyId);
	public ServicePackage getServicePackageById(Long id);
	public Long save(ServicePackage servicePackage) throws Exception;
	public void update(ServicePackage servicePackage) throws Exception;
	public void deleteTemplateItem(Long templateId) throws Exception;
	public TemplateService getTemplateService(Long id);
	public void updateTemplate(TemplateService templateService) throws Exception;
	public List<ServicePackage> findServicePackageByServicePackageTypeId(Long servicePackageTypeId);
	public List<ServicePackage> findAll();
	public ServicePackage getServicePackageByCode(String packageCode);
	public String genServicePackageCode();
	public ServicePackage getServicePackageByName(String packageName);
	public void saveTemplate(TemplateService templateService) throws Exception;
}
