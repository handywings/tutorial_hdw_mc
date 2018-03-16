package com.hdw.mccable.dto;

public class DocumentFileBean extends DSTPUtilityBean{
	
	private Long id;
	// รหัสไฟล์ 
	private String fileID; 
	// ชื่อไฟล์ 
	private String fileName; 
	// คำอธิบายไฟล์ 
	private String fileDescription; 
	// ประเภทของไฟล์ (รูปภาพ, เอกสาร) 
	private String fileType; 
	// พาธไฟล์ 
	private String filePath; 
	// สถานะการตรวจสอบ 
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "DocumentFileBean [id=" + id + ", fileID=" + fileID + ", fileName=" + fileName + ", fileDescription="
				+ fileDescription + ", fileType=" + fileType + ", filePath=" + filePath + ", status=" + status
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	} 
}
