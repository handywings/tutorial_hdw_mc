package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.PermissionGroup;


public interface PermissionGroupService {
	public PermissionGroup getPermissionGroupById(Long id);
	public List<PermissionGroup> findAll();
	public void update(PermissionGroup permissionGroup) throws Exception;
	public Long save(PermissionGroup permissionGroup) throws Exception;
	public void delete(PermissionGroup permissionGroup) throws Exception;
	public PermissionGroup findByPermissionTypeDefault();
	public List<PermissionGroup> findByPermissionNotTypeDefault();
}
