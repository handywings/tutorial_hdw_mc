package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ServicePackageDAO;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.TemplateService;
import com.hdw.mccable.service.ServicePackageService;

@Service
public class ServicePackageServiceImpl implements ServicePackageService{
	
	private ServicePackageDAO servicePackageDAO;
	
	public void setServicePackageDAO(ServicePackageDAO servicePackageDAO) {
		this.servicePackageDAO = servicePackageDAO;
	}

	@Transactional
	public List<ServicePackage> searchBykey(String criteria, String servicePackageTypeCode, String status, Long companyId) {
		return servicePackageDAO.searchBykey(criteria, servicePackageTypeCode, status, companyId);
	}
	
	@Transactional
	public ServicePackage getServicePackageById(Long id) {
		return servicePackageDAO.getServicePackageById(id);
	}
	
	@Transactional
	public ServicePackage getServicePackageByCode(String packageCode) {
		return servicePackageDAO.getServicePackageByCode(packageCode);
	}

	@Transactional
	public Long save(ServicePackage servicePackage) throws Exception {
		return servicePackageDAO.save(servicePackage);
	}
	
	@Transactional
	public void update(ServicePackage servicePackage) throws Exception {
		servicePackageDAO.update(servicePackage);
	}
	
	@Transactional
	public void deleteTemplateItem(Long templateId) throws Exception {
		servicePackageDAO.deleteTemplateItem(templateId);
	}
	
	@Transactional
	public TemplateService getTemplateService(Long id) {
		return servicePackageDAO.getTemplateService(id);
	}

	@Transactional
	public void updateTemplate(TemplateService templateService) throws Exception {
		servicePackageDAO.updateTemplate(templateService);
	}

	@Transactional
	public List<ServicePackage> findServicePackageByServicePackageTypeId(Long servicePackageTypeId) {
		return servicePackageDAO.findServicePackageByServicePackageTypeId(servicePackageTypeId);
	}
	
	@Transactional
	public List<ServicePackage> findAll() {
		return servicePackageDAO.findAll();
	}

	@Transactional
	public String genServicePackageCode() {
		return servicePackageDAO.genServicePackageCode();
	}
	
	@Transactional
	public ServicePackage getServicePackageByName(String packageName) {
		return servicePackageDAO.getServicePackageByName(packageName);
	}
	
	@Transactional
	public void saveTemplate(TemplateService templateService) throws Exception {
		servicePackageDAO.saveTemplate(templateService);
	}

}
