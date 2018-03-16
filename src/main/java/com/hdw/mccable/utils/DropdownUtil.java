package com.hdw.mccable.utils;

import java.util.ArrayList;
import java.util.List;

import com.hdw.mccable.dto.DropdownBean;

public class DropdownUtil {
	//get master mouth
	List<DropdownBean> loadMounth(){
		List<DropdownBean> mounthDropdowns = new ArrayList<DropdownBean>();
		mounthDropdowns.add(new DropdownBean(1,"มกราคม"));
		mounthDropdowns.add(new DropdownBean(2,"กุมภาพันธ์"));
		mounthDropdowns.add(new DropdownBean(3,"มีนาคม"));
		mounthDropdowns.add(new DropdownBean(4,"เมษายน"));
		mounthDropdowns.add(new DropdownBean(5,"พฤษภาคม"));
		mounthDropdowns.add(new DropdownBean(6,"มิถุนายน"));
		mounthDropdowns.add(new DropdownBean(7,"กรกฎาคม"));
		mounthDropdowns.add(new DropdownBean(8,"สิงหาคม"));
		mounthDropdowns.add(new DropdownBean(9,"กันยายน"));
		mounthDropdowns.add(new DropdownBean(10,"ตุลาคม"));
		mounthDropdowns.add(new DropdownBean(11,"พฤศจิกายน"));
		mounthDropdowns.add(new DropdownBean(12,"ธันวาคม"));
		return null;
	}
}
