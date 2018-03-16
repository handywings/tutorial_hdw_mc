package com.hdw.mccable.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.DataManager;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.BackupFileBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.entity.BackupFile;
import com.hdw.mccable.service.BackupFileService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.FilePathUtils;

@Controller
@RequestMapping("/backup")
public class BackupController extends BaseController{
	final static Logger logger = Logger.getLogger(UnitController.class);
	public static final String CONTROLLER_NAME = "backup/";
	 private static final int BUFFER_SIZE = 4096;
	 
	@Autowired(required=true)
	@Qualifier(value="backupFileService")
	private BackupFileService backupFileService;	
	
	@Autowired
    MessageSource messageSource;

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initBackup(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initBackup][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}

		// check permission
		if (isPermission()) {
			try {
				// call service
				List<BackupFile> backupFileList = backupFileService.findAll();
				
				List<BackupFileBean> backupFileBeanList = new ArrayList<BackupFileBean>();
				for(BackupFile backupFile : backupFileList){
					BackupFileBean backupFileBean = populateEntityToDto(backupFile);
					backupFileBeanList.add(backupFileBean);
				}
				
				modelAndView.addObject("backupFileBeanList",backupFileBeanList);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}	
	
	@RequestMapping(value = "restoredb/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse restoredb(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : restoredb][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				BackupFile backupFile = backupFileService.findById(Long.valueOf(id));
				
				DataManager dataManager = new DataManager();
				if(dataManager.isRestoreData(backupFile.getFileName())){
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.restore.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("data.restore.transaction.success", null,
						LocaleContextHolder.getLocale()));
		
		return jsonResponse;
	}
	
		@RequestMapping(value = "download/{id}", method = RequestMethod.GET)
		public void download(@PathVariable Long id, Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
			logger.info("[method : download][Type : Controller]");
			logger.info("[method : download][id : " + id + "]");
			
			// check permission
			if (isPermission()) {
				try { 
					BackupFile backupFile = backupFileService.findById(Long.valueOf(id));
					String pathDownload = FilePathUtils.pathBackupOnLocal() + File.separator + backupFile.getFileName();
					File downloadFile = new File(pathDownload);
					FileInputStream inputStream = new FileInputStream(downloadFile);
				
			        // set content attributes for the response
			        response.setContentType("text/plain");
			        response.setContentLength((int) downloadFile.length());
			 
			        // set headers for the response
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"",
			                downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			 
			        // get output stream of the response
			        OutputStream outStream = response.getOutputStream();
			 
			        byte[] buffer = new byte[BUFFER_SIZE];
			        int bytesRead = -1;
			 
			        // write bytes read from the input stream into the output stream
			        while ((bytesRead = inputStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			 
			        inputStream.close();
			        outStream.close();
			        
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				//no permission
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		
	public 	BackupFileBean populateEntityToDto(BackupFile backupFile){
		BackupFileBean backupFileBean = new BackupFileBean();
		backupFileBean.setId(backupFile.getId());
		backupFileBean.setFileName(backupFile.getFileName());
		backupFileBean.setFileSize(backupFile.getFileSize());
		backupFileBean.setFileSizeConv(backupFile.getFileSizeConv());
		
		SimpleDateFormat formatDataTh = new SimpleDateFormat(
				messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
				new Locale("TH", "th"));
		backupFileBean.setCreateDateTh(null == backupFile.getCreateDate() ? "" : formatDataTh.format(backupFile.getCreateDate()));
		return backupFileBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setBackupFileService(BackupFileService backupFileService) {
		this.backupFileService = backupFileService;
	}
}
