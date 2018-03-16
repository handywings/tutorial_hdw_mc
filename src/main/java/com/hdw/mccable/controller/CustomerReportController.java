package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.ReportCustomerByConditionBean;
import com.hdw.mccable.dto.ReportCustomerByServiceAppTypeBean;
import com.hdw.mccable.dto.ReportCustomerByServiceTypeBean;
import com.hdw.mccable.dto.ReportCustomerByworksheetCancelBean;
import com.hdw.mccable.dto.ReportCustomerByworksheetMoveBean;
import com.hdw.mccable.dto.ServiceApplicationTypeBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ZoneService;

@Controller
@RequestMapping("/customerreport")
public class CustomerReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerReportController.class);
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@RequestMapping(value = "bycondition", method = RequestMethod.GET)
	public ModelAndView bycondition(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : bycondition][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				
				// เขตชุมชนที่ติดตั้ง
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				List<Zone> zones = zoneService.findAll();
				if(null != zones && !zones.isEmpty()){
					ZoneController zoneController = new ZoneController();
					for(Zone zone:zones){
						zoneBeans.add(zoneController.populateEntityToDto(zone));
					}
				}
				modelAndView.addObject("zones", zoneBeans);
				
				// อาชีพ
				List<CareerBean> careerBeans = new ArrayList<CareerBean>();
				List<Career> careers = customerService.findAllCareer();
				ServiceApplicationController serviceAppController = new ServiceApplicationController();
				if(null != careers && !careers.isEmpty()){
					for(Career career:careers){
						careerBeans.add(serviceAppController.populateEntityToDto(career));
					}
				}
				modelAndView.addObject("careers", careerBeans);

			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "bycondition/exportPdf/reportrange/{reportrange}/zone/{zone}/career/{career}/customerType/{customerType}", method = RequestMethod.GET)
	public ModelAndView byconditionExportPdf(Model model,@PathVariable String reportrange,
			@PathVariable String zone,
			@PathVariable String career,
			@PathVariable String customerType, HttpServletRequest request, HttpServletResponse response, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byconditionExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange :{}, zone : {}, career : {}, customerType : {}, ",reportrange,zone,career,customerType);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<Customer> customerList = customerService.getCustomerByConditionForReport(reportrange,zone,career,customerType);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != customerList && customerList.size() > 0){
			for(Customer customer:customerList){
				ReportCustomerByConditionBean reportCustomerByConditionBean = new ReportCustomerByConditionBean();
				reportCustomerByConditionBean.setCustCode(customer.getCustCode());
				String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
				reportCustomerByConditionBean.setFullName(fullName);
				reportCustomerByConditionBean.setCreateDate(formatDataTh.format(customer.getCreateDate()));
				String customerTypeValue = customer.getCustType().equals("I")?"บุคคลธรรมดา":"นิติบุคคล";
				reportCustomerByConditionBean.setCustomerType(customerTypeValue);
				
				Career career1 = customer.getCareer();
				if(null != career1){
				reportCustomerByConditionBean.setCareer(career1.getCareerName());
				}
				Contact contact = customer.getContact();
				if(null != contact){
				reportCustomerByConditionBean.setMobile(contact.getMobile());
				}
				resUse.add(reportCustomerByConditionBean);
			}
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("bycondition",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "byserviceapptype", method = RequestMethod.GET)
	public ModelAndView byserviceapptype(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byserviceapptype][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			ServiceApplicationController serviceAppController = new ServiceApplicationController();
			List<ServiceApplicationTypeBean> serviceApplicationTypeBeans = new ArrayList<ServiceApplicationTypeBean>();
			List<ServiceApplicationType> serviceApplicationTypes = serviceApplicationService.findAllServiceApplicationType();
			if(null != serviceApplicationTypes && !serviceApplicationTypes.isEmpty()){
				for(ServiceApplicationType serviceApplicationType:serviceApplicationTypes){
					serviceApplicationTypeBeans.add(serviceAppController.populateEntityToDto(serviceApplicationType));
				}
			}
			modelAndView.addObject("serviceApplicationTypes", serviceApplicationTypeBeans);
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "byserviceapptype/exportPdf/reportrange/{reportrange}/serviceApplicationType/{serviceApplicationType}", method = RequestMethod.GET)
	public ModelAndView byserviceapptypeExportPdf(Model model,@PathVariable String reportrange,
			@PathVariable String serviceApplicationType, HttpServletRequest request, HttpServletResponse response, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byserviceapptypeExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, serviceApplicationType : {}",reportrange,serviceApplicationType);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<ServiceApplication> serviceApplicationList = customerService.getCustomerByServiceAppTypeForReport(reportrange,serviceApplicationType);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != serviceApplicationList && serviceApplicationList.size() > 0){
			int no = 1;
			String serviceApplicationTypeStr = serviceApplicationList.get(0).getServiceApplicationType().getServiceApplicationTypeName();
			ReportCustomerByServiceAppTypeBean reportCustomerByServiceAppTypeBean = new ReportCustomerByServiceAppTypeBean();
			reportCustomerByServiceAppTypeBean.setServiceApplicationType(serviceApplicationTypeStr);
			resUse.add(reportCustomerByServiceAppTypeBean);
			for(int i=0; i < serviceApplicationList.size(); i++){
				Customer customer = serviceApplicationList.get(i).getCustomer();
				reportCustomerByServiceAppTypeBean = new ReportCustomerByServiceAppTypeBean();
				if(!serviceApplicationTypeStr.equals(serviceApplicationList.get(i).getServiceApplicationType().getServiceApplicationTypeName())){
					serviceApplicationTypeStr = serviceApplicationList.get(i).getServiceApplicationType().getServiceApplicationTypeName();
					reportCustomerByServiceAppTypeBean.setServiceApplicationType(serviceApplicationTypeStr);
					resUse.add(reportCustomerByServiceAppTypeBean);
					no = 1;
					--i;
					continue;
				}
				reportCustomerByServiceAppTypeBean.setNo(no++);
				reportCustomerByServiceAppTypeBean.setServiceApplicationType("");
				reportCustomerByServiceAppTypeBean.setCustCode(customer.getCustCode());
				String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
				reportCustomerByServiceAppTypeBean.setFullName(fullName);
				reportCustomerByServiceAppTypeBean.setCreateDate(formatDataTh.format(serviceApplicationList.get(i).getStartDate()));
				String customerTypeValue = customer.getCustType().equals("I")?"บุคคลธรรมดา":"นิติบุคคล";
				reportCustomerByServiceAppTypeBean.setCustomerType(customerTypeValue);
				Career career1 = customer.getCareer();
				if(null != career1){
					reportCustomerByServiceAppTypeBean.setCareer(career1.getCareerName());
				}
				Contact contact = customer.getContact();
				if(null != contact){
					reportCustomerByServiceAppTypeBean.setMobile(contact.getMobile());
				}
				resUse.add(reportCustomerByServiceAppTypeBean);
			}
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byserviceapptype",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "byworksheetstatus", method = RequestMethod.GET)
	public ModelAndView byworksheetstatus(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byworksheetstatus][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		
		if(isPermission()){
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "byworksheetcancel", method = RequestMethod.GET)
	public ModelAndView byworksheetcancel(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byworksheetcancel][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){

		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "byworksheetcancel/exportPdf/reportrange/{reportrange}/displayFormat/{displayFormat}/sortingStyle/{sortingStyle}", method = RequestMethod.GET)
	public ModelAndView byworksheetcancelExportPdf(Model model,@PathVariable String reportrange,
			@PathVariable int displayFormat,@PathVariable int sortingStyle, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : byworksheetcancelExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, displayFormat : {}, sortingStyle : {}",reportrange,displayFormat,sortingStyle);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<WorksheetCut> worksheetCutList = customerService.getCustomerByWorksheetCancelForReport(reportrange,displayFormat,sortingStyle);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != worksheetCutList && worksheetCutList.size() > 0){
			ReportCustomerByworksheetCancelBean reportCustomerByworksheetCancelBean = new ReportCustomerByworksheetCancelBean();
			String zoneValue = "";
			int no = 1;
			if(displayFormat==2){
			Worksheet worksheet = worksheetCutList.get(0).getWorkSheet();
			ServiceApplication serviceApplication = worksheet.getServiceApplication();
			List<Address> addressList = serviceApplication.getAddresses();
			if(null != addressList && addressList.size() > 0){
				for(Address address:addressList){
					if("3".equals(address.getAddressType())){
						Zone zone = address.getZone();
						if(null != zone)zoneValue = zone.getZoneDetail();
					}
				}
			}
			reportCustomerByworksheetCancelBean.setZone(zoneValue);
			resUse.add(reportCustomerByworksheetCancelBean);
			}
			for(int i=0; i < worksheetCutList.size(); i++){
				reportCustomerByworksheetCancelBean = new ReportCustomerByworksheetCancelBean();
				Worksheet worksheet = worksheetCutList.get(i).getWorkSheet();
				if(null != worksheet){
					ServiceApplication serviceApplication = worksheet.getServiceApplication();
					if(null != serviceApplication){
						if(displayFormat==2){
							String zoneValueCurren = "";
							List<Address> addressList = serviceApplication.getAddresses();
							if(null != addressList && addressList.size() > 0){
								for(Address address:addressList){
									if("3".equals(address.getAddressType())){
										Zone zone = address.getZone();
										if(null != zone)zoneValueCurren = zone.getZoneDetail();
									}
								}
							}
							
							if(!zoneValue.equals(zoneValueCurren)){
								zoneValue = zoneValueCurren;
								reportCustomerByworksheetCancelBean.setZone(zoneValue);
								resUse.add(reportCustomerByworksheetCancelBean);
								no = 1;
								--i;
								continue;
							}
						}
						Customer customer = serviceApplication.getCustomer();
						reportCustomerByworksheetCancelBean.setZone("");
						reportCustomerByworksheetCancelBean.setNo(no++);
						reportCustomerByworksheetCancelBean.setCustCode(customer.getCustCode());
						String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
						reportCustomerByworksheetCancelBean.setFullName(fullName);	
						reportCustomerByworksheetCancelBean.setCreateDate(formatDataTh.format(worksheet.getCreateDate()));
						reportCustomerByworksheetCancelBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
						ServicePackage servicePackage = serviceApplication.getServicePackage();
						if(null != servicePackage){
							ServicePackageType servicePackageType = servicePackage.getServicePackageType();
							if(null != servicePackageType){
							reportCustomerByworksheetCancelBean.setServicePackageType(servicePackageType.getPackageTypeName());
							}
						}
						Contact contact = customer.getContact();
						if(null != contact){
							reportCustomerByworksheetCancelBean.setMobile(contact.getMobile());
						}
						reportCustomerByworksheetCancelBean.setRemark(""+worksheetCutList.get(i).getCutWorkType());
						resUse.add(reportCustomerByworksheetCancelBean);
					}
				}
			}
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byworksheetcancel",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "byworksheetmove", method = RequestMethod.GET)
	public ModelAndView byworksheetmove(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byworksheetmove][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){

		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "byworksheetmove/exportPdf/reportrange/{reportrange}/sortingStyle/{sortingStyle}", method = RequestMethod.GET)
	public ModelAndView byworksheetmoveExportPdf(Model model,@PathVariable String reportrange,
			@PathVariable int sortingStyle, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : byworksheetmoveExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, sortingStyle : {}",reportrange,sortingStyle);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<WorksheetMove> worksheetMoveList = customerService.getCustomerByWorksheetMoveForReport(reportrange,sortingStyle);
		logger.info("worksheetMoveList size : {}",worksheetMoveList.size());
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != worksheetMoveList && worksheetMoveList.size() > 0){
			int no = 1;
			for(WorksheetMove worksheetMove:worksheetMoveList){
				ReportCustomerByworksheetMoveBean reportCustomerByworksheetMoveBean = new ReportCustomerByworksheetMoveBean();
				Worksheet worksheet = worksheetMove.getWorkSheet();
				if(null != worksheet){
					ServiceApplication serviceApplication = worksheet.getServiceApplication();
					if(null != serviceApplication){
						Customer customer = serviceApplication.getCustomer();
						List<Address> addressList = serviceApplication.getAddresses();
						if(null != addressList && addressList.size() > 0){
							for(Address address:addressList){
								if("3".equals(address.getAddressType())){
									Zone zoneNew = address.getZone();
									Zone zoneOld = address.getZoneOld();
									if(null != zoneNew){
										reportCustomerByworksheetMoveBean.setZoneNew(zoneNew.getZoneDetail());
									}else{
										reportCustomerByworksheetMoveBean.setZoneNew(" - ");
									}
									if(null != zoneOld){
										reportCustomerByworksheetMoveBean.setZoneOld(zoneOld.getZoneDetail());
									}else{
										reportCustomerByworksheetMoveBean.setZoneOld(" - ");
									}
								}
							}
						}
						
						reportCustomerByworksheetMoveBean.setNo(no++);
						reportCustomerByworksheetMoveBean.setCustCode(customer.getCustCode());
						String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
						reportCustomerByworksheetMoveBean.setFullName(fullName);	
						reportCustomerByworksheetMoveBean.setCreateDate(formatDataTh.format(worksheet.getCreateDate()));
						reportCustomerByworksheetMoveBean.setServiceApplicationNo(serviceApplication.getServiceApplicationNo());
						Contact contact = customer.getContact();
						if(null != contact){
							reportCustomerByworksheetMoveBean.setMobile(contact.getMobile());
						}
						
						// หาจำนวนครั้งที่ย้าย
						int countMove = 0;
						List<Worksheet> worksheetList = serviceApplication.getWorksheets();
						if(null != worksheetList && worksheetList.size() > 0){
							for(Worksheet worksheets:worksheetList){
								WorksheetMove worksheetMoves = worksheets.getWorksheetMove();
								if(null != worksheetMoves && worksheets.getStatus().equals("S")){
									countMove++;
								}
							}
						}
						reportCustomerByworksheetMoveBean.setCountMove(countMove);
						
						resUse.add(reportCustomerByworksheetMoveBean);
					}
				}
			}
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byworksheetmove",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "byservicetype", method = RequestMethod.GET)
	public ModelAndView byservicetype(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : byservicetype][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			// service type
			ServicePackageTypeController servicePackageTypeController = new ServicePackageTypeController();
			List<ServicePackageTypeBean> servicePackageTypeBeans = new ArrayList<ServicePackageTypeBean>();
			List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
			for (ServicePackageType servicePackageType : servicePackageTypes) {
				ServicePackageTypeBean servicePackageTypeBean = servicePackageTypeController
						.populateEntityToDto(servicePackageType);
				if (null != servicePackageTypeBean) {
					servicePackageTypeBeans.add(servicePackageTypeBean);
				}
			}
			modelAndView.addObject("servicePackageTypes", servicePackageTypeBeans);
			
			// CustomerFeature
			ServiceApplicationController serviceAppController = new ServiceApplicationController();
			List<CustomerFeatureBean> customerFeatureBeans = new ArrayList<CustomerFeatureBean>();
			List<CustomerFeature> customerFeatures = customerService.findAllCustomerFeature();
			if(null != customerFeatures && !customerFeatures.isEmpty()){
				for(CustomerFeature customerFeature:customerFeatures){
					customerFeatureBeans.add(serviceAppController.populateEntityToDto(customerFeature));
				}
			}
			modelAndView.addObject("customerFeatures", customerFeatureBeans);
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "byservicetype/exportPdf/reportrange/{reportrange}/servicePackageType/{servicePackageType}/customerFeature/{customerFeature}", method = RequestMethod.GET)
	public ModelAndView byservicetypeExportPdf(Model model,@PathVariable String reportrange,
			@PathVariable String customerFeature,
			@PathVariable String servicePackageType, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : byservicetypeExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, servicePackageType : {}, customerFeature : {} ",reportrange,servicePackageType,customerFeature);
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<ServiceApplication> serviceApplicationList = customerService.getCustomerByServicetypeForReport(reportrange,servicePackageType,customerFeature);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != serviceApplicationList && serviceApplicationList.size() > 0){
			int no = 1;
			String packageTypeName = serviceApplicationList.get(0).getServicePackage().getServicePackageType().getPackageTypeName();
			ReportCustomerByServiceTypeBean reportCustomerByServiceTypeBean = new ReportCustomerByServiceTypeBean();
			reportCustomerByServiceTypeBean.setServicePackageType(packageTypeName);
			resUse.add(reportCustomerByServiceTypeBean);
			for(int i=0; i < serviceApplicationList.size(); i++){
				Customer customer = serviceApplicationList.get(i).getCustomer();
				ServicePackage servicePackage = serviceApplicationList.get(i).getServicePackage();
				reportCustomerByServiceTypeBean = new ReportCustomerByServiceTypeBean();
				if(!packageTypeName.equals(servicePackage.getServicePackageType().getPackageTypeName())){
					packageTypeName = servicePackage.getServicePackageType().getPackageTypeName();
					reportCustomerByServiceTypeBean.setServicePackageType(packageTypeName);
					resUse.add(reportCustomerByServiceTypeBean);
					no = 1;
					--i;
					continue;
				}
				reportCustomerByServiceTypeBean.setNo(no++);
				reportCustomerByServiceTypeBean.setServicePackageType("");
				reportCustomerByServiceTypeBean.setCustCode(customer.getCustCode());
				String fullName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
				reportCustomerByServiceTypeBean.setFullName(fullName);
				reportCustomerByServiceTypeBean.setCreateDate(formatDataTh.format(serviceApplicationList.get(i).getCreateDate()));
				String customerTypeValue = customer.getCustType().equals("I")?"บุคคลธรรมดา":"นิติบุคคล";
				reportCustomerByServiceTypeBean.setCustomerType(customerTypeValue);
				Career career1 = customer.getCareer();
				if(null != career1){
					reportCustomerByServiceTypeBean.setCareer(career1.getCareerName());
				}
				Contact contact = customer.getContact();
				if(null != contact){
					reportCustomerByServiceTypeBean.setMobile(contact.getMobile());
				}
				resUse.add(reportCustomerByServiceTypeBean);
			}
		}
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("byservicetype",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
}
