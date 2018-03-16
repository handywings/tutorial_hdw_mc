package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.PermissionGroupDAO;
import com.hdw.mccable.entity.PermissionGroup;
import com.hdw.mccable.service.PermissionGroupService;


@Service
public class PermissionGroupServiceImpl implements PermissionGroupService{

	private PermissionGroupDAO permissionGroupDAO;
	
	public void setPermissionGroupDAO(PermissionGroupDAO permissionGroupDAO) {
		this.permissionGroupDAO = permissionGroupDAO;
	}

	@Transactional
	public PermissionGroup getPermissionGroupById(Long id) {
		return permissionGroupDAO.getPermissionGroupById(id);
	}

	@Transactional
	public List<PermissionGroup> findAll() {
		return permissionGroupDAO.findAll();
	}

	@Transactional
	public void update(PermissionGroup permissionGroup) throws Exception {
		permissionGroupDAO.update(permissionGroup);
	}

	@Transactional
	public Long save(PermissionGroup permissionGroup) throws Exception {
		return permissionGroupDAO.save(permissionGroup);
	}

	@Transactional
	public void delete(PermissionGroup permissionGroup) throws Exception {
		permissionGroupDAO.delete(permissionGroup);
	}

	@Transactional
	public PermissionGroup findByPermissionTypeDefault() {
		return permissionGroupDAO.findByPermissionTypeDefault();
	}

	@Transactional
	public List<PermissionGroup> findByPermissionNotTypeDefault() {
		return permissionGroupDAO.findByPermissionNotTypeDefault();
	}
	


}
