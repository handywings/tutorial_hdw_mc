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
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.FilePathUtil;
import com.hdw.mccable.utils.UploadFileUtil;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController{
	
	final static Logger logger = Logger.getLogger(CompanyController.class);
	public static final String CONTROLLER_NAME = "company/";
	
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
				List<Company> companys = companyService.findAll();
				
				if(companys != null){
					List<CompanyBean> subcompanyList = new ArrayList<CompanyBean>();
					//Begin populate entity to dto
					for(Company company : companys){
						if(company.getParent() == COMPANY_PARENT){
							//set master companyBean
							companyBean = populateEntityToDto(company);
							
						}else if(company.getParent() != COMPANY_PARENT){
							CompanyBean companySubBean = new CompanyBean();
							//set sub companyBean
							companySubBean = populateEntityToDto(company);
							//add to company sub list
							subcompanyList.add(companySubBean);
						}
					}
					
					//add sub company list to master company
					companyBean.setSubcompanyList(subcompanyList);
					//End populate entity to dto
				}
				
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
		modelAndView.addObject("company", companyBean);
		modelAndView.addObject("defaultImage", FilePathUtil.pathAvatarOnWeb(null));
		
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getCompany(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		CompanyBean companyBean = new CompanyBean();
		
		if(isPermission()){
			try{
				Company company = companyService.getCompanyById(Long.valueOf(id));
				if(company != null){
					companyBean = populateEntityToDto(company);
					jsonResponse.setError(false);
					jsonResponse.setResult(companyBean);
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
	public JsonResponse updateCompany(@RequestParam("companyBean") final String companyBeanStr,
			@RequestParam("file_company") final MultipartFile file_company, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				
				// Convert JSON string to Object Java
				ObjectMapper mapper = new ObjectMapper();
				CompanyBean companyBean = mapper.readValue(companyBeanStr, CompanyBean.class);
				
				Company company = companyService.getCompanyById(Long.valueOf(companyBean.getId()));
				if(company != null){
					//set value company
					company = populateDtoToEntity(companyBean, company);
					
					if (!file_company.getOriginalFilename().equalsIgnoreCase("noPictureCompany")) {
						String fileName = UploadFileUtil.uploadFileToServer(
								FilePathUtil.pathAvatarOnLocal("company", ""+companyBean.getId()), file_company);
						company.setLogo("company/"+companyBean.getId()+"/"+fileName);
					}
					
					//update
					companyService.update(company);
					jsonResponse.setError(false);
					jsonResponse.setResult(populateEntityToDto(company));
					
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
	public JsonResponse saveCompany(@RequestParam("companyBean") final String companyBeanStr,
			@RequestParam("file_company") final MultipartFile file_company, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			
			// Convert JSON string to Object Java
			ObjectMapper mapper = new ObjectMapper();
			CompanyBean companyBean = mapper.readValue(companyBeanStr, CompanyBean.class);
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				//save
				Company company = new Company();
				Address address = new Address();
				//---> change when LoginController success
				address.setCreatedBy(getUserNameLogin());
				address.setCreateDate(CURRENT_TIMESTAMP);
				address.setDeleted(Boolean.FALSE);
				address.setCompany(company);
				company.setAddress(address);
				
				Contact contact = new Contact();
				//---> change when LoginController success
				contact.setCreatedBy(getUserNameLogin());
				contact.setCreateDate(CURRENT_TIMESTAMP);
				contact.setDeleted(Boolean.FALSE);
				contact.setCompany(company);
				company.setContact(contact);
				
				company = populateDtoToEntity(companyBean, company);
				
				company.setParent(companyBean.getParent());
				company.setCreateDate(CURRENT_TIMESTAMP);
				//---> change when LoginController success
				company.setCreatedBy(getUserNameLogin());
				company.setDeleted(Boolean.FALSE);
				
				if (!file_company.getOriginalFilename().equalsIgnoreCase("noPictureCompany")) {
					String fileName = UploadFileUtil.uploadFileToServer(
							FilePathUtil.pathAvatarOnLocal("company", ""+companyBean.getId()), file_company);
					company.setLogo("company/"+companyBean.getId()+"/"+fileName);
				}
				
				Long companyId = companyService.save(company);
				if(companyId != null){
					jsonResponse.setError(false);
					//jsonResponse.setResult(populateEntityToDto(company));
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
