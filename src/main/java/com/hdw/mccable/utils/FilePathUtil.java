package com.hdw.mccable.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class FilePathUtil {
	
	
	// รูปภาพ เริ่มต้น
	public static String PATH_AVATAR_MALE = "male.png";
	public static String PATH_AVATAR_FEMALE = "female.png";
	public static String PATH_PRODUCT_DEFALUT = "product.png";
	public static String PATH_COMPANY_DEFALUT = "company.png";

	public static String pathAvatarOnLocal(String floder, String memberID) {
		String path = ConstantUtils.PATH_LOCAL + floder + File.separator + memberID + File.separator;
		return path;
	}		
	
	public static String pathAvatarOnLocalPDF(String floder) {
		String path = ConstantUtils.PATH_LOCAL + floder + File.separator;
		return path;
	}
	
	public static String pathAvatarOnWeb(String path) {
		String pathFile = ConstantUtils.PATH_WEB + "company.png";
		if(StringUtils.isNotEmpty(path)){
			pathFile = ConstantUtils.PATH_WEB + path;
		}
		return pathFile;
	}
	
	public static String pathAvatarOnWebPDF(String path) {
		String pathFile = "";
		if(StringUtils.isNotEmpty(path)){
			pathFile = ConstantUtils.PATH_WEB + "pdf/" + path;
		}
		return pathFile;
	}
	
	public static String pathAvatarOnWebCustomer(String path) {
		String pathFile = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(path)){
			pathFile = ConstantUtils.PATH_WEB + path;
		}
		return pathFile;
	}
	
	public static String pathAvatarOnWebCustomer(String path, String gender) {
		String pathFile = StringUtils.EMPTY;
		if(gender.equals("male")){
			pathFile = ConstantUtils.PATH_WEB + "male.png";
		}else{
			pathFile = ConstantUtils.PATH_WEB + "female.png";
		}
		
		if(StringUtils.isNotEmpty(path)){
			pathFile = ConstantUtils.PATH_WEB + path;
		}
		return pathFile;
	}
	
	public static String pathAvatarOnWeb(String path , String gender) {
		String pathFile = "";
		if(!TextUtil.isNotEmpty(path)){
			if(gender.equals("ชาย")){
				pathFile = ConstantUtils.PATH_WEB + "/male.png";
			}else{
				pathFile = ConstantUtils.PATH_WEB + "/female.png";
			}
		}else{
			pathFile = ConstantUtils.PATH_WEB + path;
			pathFile = pathFile.replace("\\", "/");
		}
		
		return pathFile;
	}

	public static String pathProductOnLocal(Long id) {
		String path = ConstantUtils.PATH_LOCAL + "product" + File.separator + id + File.separator;
		return path;
	}

	public static String pathProductOnWeb(String pathFile) {
		String path = "";
		if(!TextUtil.isNotEmpty(pathFile)){
			pathFile = ConstantUtils.PATH_WEB+"/product.png";
		}else{
			path = ConstantUtils.PATH_WEB + pathFile;
			path = path.replace("\\", "/");
		}
		return path;
	}
	public static String pathBackupOnLocal() {
		String path = ConstantUtils.PATH_LOCAL + "backup_file";
		return path;
	}

	public static String pathBackupOnWeb() {
		String path = ConstantUtils.PATH_WEB + "backup_file";
		path = path.replace("\\", "/");
		return path;
	}

	public static String getPATH_LOCAL() {
		return ConstantUtils.PATH_LOCAL;
	}
	
	public static String pathWorksheetOnLocal(String taskId, String imageType){
		String pathType = "";
		if("B".equals(imageType)){
			pathType = "before";
		}else if("A".equals(imageType)){
			pathType = "after";
		}
		String path = ConstantUtils.PATH_LOCAL + "worksheet" + File.separator + pathType +File.separator + taskId;
		path = path.replace("\\", "/");
		return path; 
	}
	
	public static String pathWorksheetOnWeb(String pathFile) {
		String path = ConstantUtils.PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}
	
	public static String pathEvidencePaymentOnLocal(String id) {
		String path = ConstantUtils.PATH_LOCAL + "evidencePayment" + File.separator + id + File.separator;
		return path;
	}

	public static String pathEvidencePaymentOnWeb(String pathFile) {
		String path = "";
		path = ConstantUtils.PATH_WEB + "evidencePayment" + File.separator + pathFile;
		path = path.replace("\\", "/");
		return path;
	}
	
}