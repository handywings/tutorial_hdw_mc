package com.hdw.mccable.dto;

public class AdditionMonthlyFee {
	private float amount;
	
	//หมายเหตุ หาใบงานเพิ่มจุดทั้งหมด และ ลดจุดทั้งหมด มาคำนวณเพื่อหาว่าเป็น type อะไร
	private String type; //A=เพิ่มรายเดือน,R=ลดรายเดือน ,N=ไม่มีเพิ่มลด

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
