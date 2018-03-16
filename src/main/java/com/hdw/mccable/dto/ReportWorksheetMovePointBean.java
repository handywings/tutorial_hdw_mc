package com.hdw.mccable.dto;

public class ReportWorksheetMovePointBean extends ReportWorksheetMainBean{

	private int analogPoint;
	private int digitalPoint;
	private int digitalPointReduce;
	private int analogPointReduce;
	
	public int getAnalogPoint() {
		return analogPoint;
	}
	public void setAnalogPoint(int analogPoint) {
		this.analogPoint = analogPoint;
	}
	public int getDigitalPoint() {
		return digitalPoint;
	}
	public void setDigitalPoint(int digitalPoint) {
		this.digitalPoint = digitalPoint;
	}
	public int getDigitalPointReduce() {
		return digitalPointReduce;
	}
	public void setDigitalPointReduce(int digitalPointReduce) {
		this.digitalPointReduce = digitalPointReduce;
	}
	public int getAnalogPointReduce() {
		return analogPointReduce;
	}
	public void setAnalogPointReduce(int analogPointReduce) {
		this.analogPointReduce = analogPointReduce;
	}
}