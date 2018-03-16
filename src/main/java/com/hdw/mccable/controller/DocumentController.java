package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Document;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.FilePathUtil;
import com.hdw.mccable.utils.UploadFileUtil;

@Controller
@RequestMapping("/document")
public class DocumentController extends BaseController{
	
	final static Logger logger = Logger.getLogger(DocumentController.class);
	public static final String CONTROLLER_NAME = "document/";
	
	//initial service
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
			
	@Autowired
    MessageSource messageSource;
	
	//init quartz
    //@Autowired
	//private SchedulerFactoryBean schedulerFactory;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initCompany(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initCompany][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		CompanyBean companyBean = new CompanyBean();
		
		//check authentication
		if(!isAuthentication()){
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		
		//check permission
		if(isPermission()){
			try{
				//call service
				List<Document> documentList = companyService.findDocumentAll();
				modelAndView.addObject("documentList", documentList);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("defaultImage", FilePathUtil.pathAvatarOnWeb(null));
		
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getDocument(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Document document = companyService.getDocumentById(Long.valueOf(id));
				if(document != null){
					jsonResponse.setError(false);
					jsonResponse.setResult(document);
				}else{
					jsonResponse.setError(true);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public JsonResponse updateCompany(@RequestParam("documentBean") final String documentBeanStr,
			@RequestParam("file_document") final MultipartFile file_document, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				
				// Convert JSON string to Object Java
				ObjectMapper mapper = new ObjectMapper();
				Document documentBean = mapper.readValue(documentBeanStr, Document.class);
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
				Document document = companyService.getDocumentById(documentBean.getId());
				if(document != null){
					document.setDocument_code(documentBean.getDocument_code());
					document.setDocument_name(documentBean.getDocument_name());
					document.setDocument_type(documentBean.getDocument_type());
					document.setPermission(documentBean.getPermission());
					if (!file_document.getOriginalFilename().equalsIgnoreCase("noPictureDocument")) {
						String fileName = UploadFileUtil.uploadFileToServer(
								FilePathUtil.pathAvatarOnLocal("document", "temp"), file_document);
						document.setDocument_path("document/temp/"+fileName);
					}
					document.setUpdatedBy(getUserNameLogin());
					document.setUpdatedDate(CURRENT_TIMESTAMP);
					//update
					companyService.update(document);
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("company.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public JsonResponse saveCompany(@RequestParam("documentBean") final String documentBean,
			@RequestParam("file_document") final MultipartFile file_document, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			// Convert JSON string to Object Java
			ObjectMapper mapper = new ObjectMapper();
			Document document = mapper.readValue(documentBean, Document.class);
			
			try{
				
				if (!file_document.getOriginalFilename().equalsIgnoreCase("noPictureDocument")) {
					String fileName = UploadFileUtil.uploadFileToServer(
							FilePathUtil.pathAvatarOnLocal("document", "temp"), file_document);
					document.setDocument_path("document/temp/"+fileName);
				}
				document.setCreateDate(CURRENT_TIMESTAMP);
				document.setCreatedBy(getUserNameLogin());
				companyService.save(document);
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("company.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteCompany(@PathVariable String id, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Company company = companyService.getCompanyById(Long.valueOf(id));
				company.setDeleted(Boolean.TRUE);
				company.getContact().setDeleted(Boolean.TRUE);
				company.getAddress().setDeleted(Boolean.TRUE);
				
				if(company != null){
					companyService.update(company);
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("company.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	public CompanyBean populateEntityToDto(Company company){
		CompanyBean companyBean = new CompanyBean();
		
		companyBean.setId(company.getId());
		companyBean.setCompanyName(company.getCompanyName());
		companyBean.setTaxId(company.getTaxId());
		companyBean.setVat(company.getVat());
		companyBean.setInvCredit(company.getInvCredit());
		//set contact
		ContactBean contactBean = new ContactBean();
		contactBean.setMobile(company.getContact().getMobile());
		contactBean.setEmail(company.getContact().getEmail());
		contactBean.setFax(company.getContact().getFax());
		companyBean.setContact(contactBean);
		//set address
		AddressBean addressBean = new AddressBean();
		addressBean.setDetail(company.getAddress().getDetail());
		companyBean.setAddress(addressBean);
		companyBean.setLogo(FilePathUtil.pathAvatarOnWeb(company.getLogo()));
		
		return companyBean;
	}
	
	public Company populateDtoToEntity(CompanyBean companyBean, Company company){
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		company.setCompanyName(companyBean.getCompanyName());
		company.setTaxId(companyBean.getTaxId());
		company.setVat(companyBean.getVat());
		company.setInvCredit(companyBean.getInvCredit());
		company.setUpdatedBy(getUserNameLogin());
		company.setUpdatedDate(CURRENT_TIMESTAMP);
		//address
		company.getAddress().setDetail(companyBean.getAddress().getDetail());
		company.getAddress().setUpdatedBy(getUserNameLogin());
		company.getAddress().setUpdatedDate(CURRENT_TIMESTAMP);
		//contact
		company.getContact().setMobile(companyBean.getContact().getMobile());
		company.getContact().setFax(companyBean.getContact().getFax());
		company.getContact().setEmail(companyBean.getContact().getEmail());
		company.getContact().setUpdatedBy(getUserNameLogin());
		company.getContact().setUpdatedDate(CURRENT_TIMESTAMP);
		
		return company;
	}
	
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	//getter or setter dependency
	public void setCompanyService(CompanyService companyService){
		this.companyService = companyService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
