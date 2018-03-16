package com.hdw.mccable.dto;

public class BackupFileBean extends DSTPUtilityBean{
	
	private Long id;
	private String fileName;
	private float fileSize;
	private String fileSizeConv;
	private String pathDownload;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public float getFileSize() {
		return fileSize;
	}
	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileSizeConv() {
		return fileSizeConv;
	}
	public void setFileSizeConv(String fileSizeConv) {
		this.fileSizeConv = fileSizeConv;
	}
	public String getPathDownload() {
		return pathDownload;
	}
	public void setPathDownload(String pathDownload) {
		this.pathDownload = pathDownload;
	}
}
