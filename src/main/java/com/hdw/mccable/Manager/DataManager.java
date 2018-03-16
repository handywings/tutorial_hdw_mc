package com.hdw.mccable.Manager;

import java.io.File;

import com.hdw.mccable.utils.ConstantUtils;
import com.hdw.mccable.utils.FilePathUtils;

public class DataManager {
	
	public boolean isBackupData(String date) {
		String dbName = ConstantUtils.DB_NAME;
		String dbUsername = ConstantUtils.DB_USERNAME;
		String dbPassword = ConstantUtils.DB_PASSWORD;
		
		String fileName = "backup" +"_"+ date +".sql";
		String executeCmd = ConstantUtils.MYSQL_PATH + "mysqldump -u" + dbUsername + " -p" + dbPassword + " " + dbName + " --databases " + dbName + " > " + FilePathUtils.pathBackupOnLocal() + File.separator + fileName;
		Process runtimeProcess = null;
		
		try {
			System.out.println(executeCmd);
			// this out put works in mysql shell
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				System.out.println("Backup created successfully");
				return true;
			} else {
				System.out.println("Could not create the backup");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean isRestoreData(String fileName) {	
		String dbName = ConstantUtils.DB_NAME;
		String dbUsername = ConstantUtils.DB_USERNAME;
		String dbPassword = ConstantUtils.DB_PASSWORD;
		
		String executeCmd = ConstantUtils.MYSQL_PATH + "mysql -u" + dbUsername + " -p" + dbPassword + " " + dbName + " < " + FilePathUtils.pathBackupOnLocal() + File.separator + fileName;
		Process runtimeProcess = null;
		
		try {
			System.out.println(executeCmd);
			// this out put works in mysql shell
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				System.out.println("Restore Data successfully");
				return true;
			} else {
				System.out.println("Could not restore data");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
