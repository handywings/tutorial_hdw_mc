package com.hdw.mccable.job;

import java.io.File;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import com.hdw.mccable.Manager.DataManager;
import com.hdw.mccable.entity.BackupFile;
import com.hdw.mccable.service.BackupFileService;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.FilePathUtils;

public class ExecutorBackupDB {
	final static Logger logger = Logger.getLogger(ExecutorBackupDB.class);
	
	@Autowired(required=true)
	@Qualifier(value="backupFileService")
	private BackupFileService backupFileService;	
	
	@Autowired
    MessageSource messageSource;
	
	@SuppressWarnings("static-access")
	public void processBackupDB(){
		logger.info("===Start processBackupDB===");
		DataManager dataManager = new DataManager();
		
		String filePath = FilePathUtils.pathBackupOnLocal();

		File fileSaveDir = new File(filePath);
		if (!fileSaveDir.exists()) {

			if(fileSaveDir.mkdirs()){
				System.out.println("Multiple directories are created!");
			}else{
				System.out.println("Failed to create multiple directories!");
			}
			
			fileSaveDir.setExecutable(true, false);
			fileSaveDir.setReadable(true, false);
			fileSaveDir.setWritable(true, false);
		}

		
		try{
			String currentDate = new DateUtil().getCurrentCeString("dd-MM-yyyy");
			if(dataManager.isBackupData(currentDate)){
				//save filename
				String fileName = "backup" +"_"+ currentDate +".sql";
				
				//get size
				File tempFile = new File(filePath + File.separator + fileName);
				long fileSize = tempFile.length();
				String fileSizeReadable = FileUtils.byteCountToDisplaySize(tempFile.length());
				
				BackupFile backupFile = new BackupFile();
				backupFile.setFileSizeConv(fileSizeReadable);
				backupFile.setFileSize(fileSize);
				Timestamp CURRENT_TIMESTAMP = new Timestamp(System.currentTimeMillis());
				backupFile.setCreateDate(CURRENT_TIMESTAMP);
				backupFile.setCreatedBy("SYSTEM");
				backupFile.setFileName(fileName);
				
				backupFileService.save(backupFile);
			}else{
				logger.info("===Backup not success===");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		logger.info("===End processBackupDB===");
	}

	
	public BackupFileService getBackupFileService() {
		return backupFileService;
	}

	public void setBackupFileService(BackupFileService backupFileService) {
		this.backupFileService = backupFileService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
