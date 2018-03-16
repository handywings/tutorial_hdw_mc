package com.hdw.mccable.dto;

import java.util.Vector;

public class JobScheduleBean extends DSTPUtilityBean{
	private Vector<PersonnelBean> personnelList;

	public Vector<PersonnelBean> getPersonnelList() {
		return personnelList;
	}

	public void setPersonnelList(Vector<PersonnelBean> personnelList) {
		this.personnelList = personnelList;
	}
}
