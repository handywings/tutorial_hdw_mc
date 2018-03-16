package com.hdw.mccable.utils;

import java.io.File;

import com.hdw.mccable.utils.ConstantUtils;

public class FilePathUtils {
	private static String PATH_WEB = "http://" + ConstantUtils.DOMAIN + "/dstpartner/DSTP/";
	private static String PATH_LOCAL = "/var/cablebms/";

	public static String pathAvatarOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "avatar" + File.separator + pathFile;
		return path;
	}

	public static String pathAvatarOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}

	public static String pathProductOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "product" + File.separator + pathFile;
		return path;
	}

	public static String pathProductOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}
	
	public static String pathProductCategoryOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "product_category" + File.separator + pathFile;
		return path;
	}

	public static String pathProductCategoryOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}

	public static String pathDocumentOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "document" + File.separator + pathFile;
		return path;
	}

	public static String pathDocumentOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}

	public static String pathShopOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "shop" + File.separator + pathFile;
		return path;
	}

	public static String pathShopOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}

	public static String pathSupplierOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "supplier" + File.separator + pathFile;
		return path;
	}

	public static String pathSupplierOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}
	
	public static String pathInformPaymentOnLocal(String memberID, String pathFile) {
		String path = PATH_LOCAL + memberID + File.separator + "informpayment" + File.separator + pathFile;
		return path;
	}

	public static String pathInformPaymentOnWeb(String pathFile) {
		String path = PATH_WEB + pathFile;
		path = path.replace("\\", "/");
		return path;
	}
	
	public static String pathBackupOnLocal() {
		String path = PATH_LOCAL + "backupfile";
		return path;
	}

	public static String pathBackupOnWeb() {
		String path = PATH_WEB + "backupfile";
		path = path.replace("\\", "/");
		return path;
	}

	public static String getPATH_LOCAL() {
		return PATH_LOCAL;
	}
}
