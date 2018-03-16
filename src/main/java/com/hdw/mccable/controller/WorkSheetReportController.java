package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.AddPointWorksheetBean;
import com.hdw.mccable.dto.AddSetTopBoxWorksheetBean;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.AnalyzeProblemsWorksheetBean;
import com.hdw.mccable.dto.BorrowWorksheetBean;
import com.hdw.mccable.dto.ConnectWorksheetBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CutWorksheetBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.MovePointWorksheetBean;
import com.hdw.mccable.dto.MoveWorksheetBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReducePointWorksheetBean;
import com.hdw.mccable.dto.RepairConnectionWorksheetBean;
import com.hdw.mccable.dto.RepairMatchItemBean;
import com.hdw.mccable.dto.ReportBilUnpaidBean;
import com.hdw.mccable.dto.ReportDailyWork;
import com.hdw.mccable.dto.ReportMaterialsUsedBean;
import com.hdw.mccable.dto.ReportStockSummaryBean;
import com.hdw.mccable.dto.ReportTechnicianGroupBean;
import com.hdw.mccable.dto.ReportWorksheetAddPointBean;
import com.hdw.mccable.dto.ReportWorksheetAddSettopBean;
import com.hdw.mccable.dto.ReportWorksheetAnalyzeProblemsBean;
import com.hdw.mccable.dto.ReportWorksheetBean;
import com.hdw.mccable.dto.ReportWorksheetBorrowBean;
import com.hdw.mccable.dto.ReportWorksheetCutBean;
import com.hdw.mccable.dto.ReportWorksheetMainBean;
import com.hdw.mccable.dto.ReportWorksheetMoveBean;
import com.hdw.mccable.dto.ReportWorksheetMovePointBean;
import com.hdw.mccable.dto.ReportWorksheetReducePointBean;
import com.hdw.mccable.dto.ReportWorksheetRepairBean;
import com.hdw.mccable.dto.ReportWorksheetSetUpBean;
import com.hdw.mccable.dto.ReportWorksheetTuneBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TechnicainGroupWorkLoad;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.TuneWorksheetBean;
import com.hdw.mccable.dto.WorkSheetReportBeanSubTable;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryRepair;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;

@Controller
@RequestMapping("/worksheetreport")
public class WorkSheetReportController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkSheetReportController.class);
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired(required = true)
	@Qualifier(value = "technicianGroupService")
	private TechnicianGroupService technicianGroupService;

	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "worksheetlist", method = RequestMethod.GET)
	public ModelAndView worksheetlist(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : worksheetlist][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			
			List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
			List<Zone> zones = zoneService.findAll();
			if(null != zones && !zones.isEmpty()){
				ZoneController zoneController = new ZoneController();
				for(Zone zone:zones){
					zoneBeans.add(zoneController.populateEntityToDto(zone));
				}
			}
			modelAndView.addObject("zones", zoneBeans);

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	public List<ReportWorksheetBean> setReportWorksheetBean(List<Worksheet> worksheetList, final String split) {
		final SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		List<ReportWorksheetBean> reportWorksheetBeanList = new ArrayList<ReportWorksheetBean>();
		if(null != worksheetList && worksheetList.size() > 0){

			for(Worksheet worksheet:worksheetList){
				ReportWorksheetBean reportWorksheetBean = new ReportWorksheetBean();
				reportWorksheetBean.setWorkSheetCode(worksheet.getWorkSheetCode());
				ServiceApplication serviceApplication = worksheet.getServiceApplication();
				String custName = " - ", tel = " - ";
				if(null != serviceApplication){
				Customer customer = serviceApplication.getCustomer();
					if(null != customer){
						custName = String.format("%1s %2s",customer.getFirstName(),customer.getLastName());
						reportWorksheetBean.setCustName(custName);
						Contact contact = customer.getContact();
						if(null != contact){
							tel = contact.getMobile();
							reportWorksheetBean.setTel(tel);
						}
					}
					List<Address> addressList = serviceApplication.getAddresses();
					String zoneValue = " - ";
					for(Address address:addressList){
						if("3".equals(address.getAddressType())){
							Zone zone = address.getZone();
							if(null != zone) zoneValue = zone.getZoneDetail();
						}
					}
					reportWorksheetBean.setZone(zoneValue);
					String personnelName = " - ", workDate = " - ";
					List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = worksheet.getHistoryTechnicianGroupWorks();
					if(null != historyTechnicianGroupWorkList && historyTechnicianGroupWorkList.size() > 0){
						TechnicianGroup technicianGroup = historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size() - 1).getTechnicianGroup();
						if(null != technicianGroup){
							Personnel personnel = technicianGroup.getPersonnel();
							if(null != personnel){
								personnelName = String.format("%1s %2s (%3s)",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName());
							}
						}
						workDate = formatDataTh.format(historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size() - 1).getAssignDate());
					}
					reportWorksheetBean.setPersonnelName(personnelName);
					reportWorksheetBean.setWorkDate(workDate);
				}
				reportWorksheetBean.setWorkSheetType(worksheet.getWorkSheetType());
				reportWorksheetBean.setStatus(worksheet.getStatus());
				
				reportWorksheetBeanList.add(reportWorksheetBean);
			}
			
			if (reportWorksheetBeanList.size() > 0) {
				  Collections.sort(reportWorksheetBeanList, new Comparator<ReportWorksheetBean>() {
				      public int compare(final ReportWorksheetBean object1, final ReportWorksheetBean object2) {
						if("1".equals(split)){
							return object2.getWorkSheetType().compareTo(object1.getWorkSheetType());
						}else if("2".equals(split)){
							return object2.getStatus().compareTo(object1.getStatus());
						}else if("3".equals(split)){
							return object2.getZone().compareTo(object1.getZone());
						}else if("4".equals(split)){
							try {
								if(" - ".equals(object1.getWorkDate()) || " - ".equals(object2.getWorkDate())) return 1;
								return formatDataTh.parse(object2.getWorkDate()).compareTo(formatDataTh.parse(object1.getWorkDate()));
							} catch (ParseException e) {
								e.printStackTrace();
								return 0;
							}
						}else{
							return object2.getPersonnelName().compareTo(object1.getPersonnelName());
						}
				      }
				  });

			}
			
		}

		return reportWorksheetBeanList;
	}
	
	@RequestMapping(value = "worksheetlist/exportPdf/"
			+ "reportrange/{reportrange}/jobType/{jobType}/worksheetStatus/{worksheetStatus}/"
			+ "zone/{zone}/split/{split}", method = RequestMethod.GET)
	public ModelAndView worksheetlistExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String jobType,
			@PathVariable String worksheetStatus,
			@PathVariable String zone,
			@PathVariable String split, 
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : worksheetlistExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, jobType : {}, worksheetStatus : {}, "
				+ "zone : {}, split : {}",reportrange,jobType,worksheetStatus,zone,split);
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		List<Worksheet> worksheetList = workSheetService.getDataWorksheetForReport(reportrange,jobType,worksheetStatus,zone,split);
		logger.info("worksheetList size : {}",worksheetList.size());

		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != worksheetList && worksheetList.size() > 0){
			ReportWorksheetBean reportWorksheetBean = new ReportWorksheetBean();
			if("1".equals(split)){
				int no = 1;
				title = "ข้อมูลใบงาน (รายการใบงานแยกตามรูปแบบใบงาน)";
				List<ReportWorksheetBean> reportWorksheetBeanList = setReportWorksheetBean(worksheetList,split);
				logger.info("reportWorksheetBeanList size : {}",reportWorksheetBeanList.size());
				if(null != reportWorksheetBeanList && reportWorksheetBeanList.size() > 0){
					String workSheetTypeMain = reportWorksheetBeanList.get(0).getWorkSheetType();
					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setGroupData("ประเภทใบงาน "+workSheetTypeMain);
					reportWorksheetBean.setCheckHeaderTable(false);
					reportWorksheetBean.setCheckGroupData(true);
					resUse.add(reportWorksheetBean);

					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setNoText("ลำดับ");
					reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
					reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
					reportWorksheetBean.setTelText("โทรศัพท์");
					reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
					reportWorksheetBean.setZoneText("เขตชุมชน");
					reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
					reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
					reportWorksheetBean.setStatusText("สถานะ");

					reportWorksheetBean.setCheckHeaderTable(true);
					reportWorksheetBean.setCheckGroupData(false);
					resUse.add(reportWorksheetBean);

					for(int i = 0; reportWorksheetBeanList.size() > i; i++){
						if(!workSheetTypeMain.equals(reportWorksheetBeanList.get(i).getWorkSheetType())){
							
							workSheetTypeMain = reportWorksheetBeanList.get(i).getWorkSheetType();
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setGroupData("ประเภทใบงาน "+workSheetTypeMain);
							reportWorksheetBean.setCheckHeaderTable(false);
							reportWorksheetBean.setCheckGroupData(true);
							resUse.add(reportWorksheetBean);
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setNoText("ลำดับ");
							reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
							reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
							reportWorksheetBean.setTelText("โทรศัพท์");
							reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
							reportWorksheetBean.setZoneText("เขตชุมชน");
							reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
							reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
							reportWorksheetBean.setStatusText("สถานะ");
							reportWorksheetBean.setCheckHeaderTable(true);
							reportWorksheetBean.setCheckGroupData(false);
							resUse.add(reportWorksheetBean);

							no = 1;
							--i;
							
							continue;
						}
						
						reportWorksheetBean = new ReportWorksheetBean();
						reportWorksheetBean.setNo(no++);
						reportWorksheetBean.setWorkSheetCode(reportWorksheetBeanList.get(i).getWorkSheetCode());
						reportWorksheetBean.setCustName(reportWorksheetBeanList.get(i).getCustName());
						reportWorksheetBean.setTel(reportWorksheetBeanList.get(i).getTel());
						reportWorksheetBean.setWorkSheetType(reportWorksheetBeanList.get(i).getWorkSheetType());
						reportWorksheetBean.setZone(reportWorksheetBeanList.get(i).getZone());
						reportWorksheetBean.setPersonnelName(reportWorksheetBeanList.get(i).getPersonnelName());
						reportWorksheetBean.setWorkDate(reportWorksheetBeanList.get(i).getWorkDate());
						reportWorksheetBean.setStatus(reportWorksheetBeanList.get(i).getStatus());

						reportWorksheetBean.setCheckHeaderTable(false);
						reportWorksheetBean.setCheckGroupData(false);
						resUse.add(reportWorksheetBean);

					}
					
				}
				
			}else if("2".equals(split)){
				int no = 1;
				title = "ข้อมูลใบงาน (รายการใบงานแยกตามสถานะ)";
				List<ReportWorksheetBean> reportWorksheetBeanList = setReportWorksheetBean(worksheetList,split);
				logger.info("reportWorksheetBeanList size : {}",reportWorksheetBeanList.size());
				if(null != reportWorksheetBeanList && reportWorksheetBeanList.size() > 0){
					String statusMain = reportWorksheetBeanList.get(0).getStatus();
					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setGroupData("สถานะ "+statusMain);
					reportWorksheetBean.setCheckHeaderTable(false);
					reportWorksheetBean.setCheckGroupData(true);
					resUse.add(reportWorksheetBean);

					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setNoText("ลำดับ");
					reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
					reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
					reportWorksheetBean.setTelText("โทรศัพท์");
					reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
					reportWorksheetBean.setZoneText("เขตชุมชน");
					reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
					reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
					reportWorksheetBean.setStatusText("สถานะ");

					reportWorksheetBean.setCheckHeaderTable(true);
					reportWorksheetBean.setCheckGroupData(false);
					resUse.add(reportWorksheetBean);

					for(int i = 0; reportWorksheetBeanList.size() > i; i++){
						if(!statusMain.equals(reportWorksheetBeanList.get(i).getStatus())){
							
							statusMain = reportWorksheetBeanList.get(i).getStatus();
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setGroupData("สถานะ "+statusMain);
							reportWorksheetBean.setCheckHeaderTable(false);
							reportWorksheetBean.setCheckGroupData(true);
							resUse.add(reportWorksheetBean);
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setNoText("ลำดับ");
							reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
							reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
							reportWorksheetBean.setTelText("โทรศัพท์");
							reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
							reportWorksheetBean.setZoneText("เขตชุมชน");
							reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
							reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
							reportWorksheetBean.setStatusText("สถานะ");
							reportWorksheetBean.setCheckHeaderTable(true);
							reportWorksheetBean.setCheckGroupData(false);
							resUse.add(reportWorksheetBean);

							no = 1;
							--i;
							
							continue;
						}
						
						reportWorksheetBean = new ReportWorksheetBean();
						reportWorksheetBean.setNo(no++);
						reportWorksheetBean.setWorkSheetCode(reportWorksheetBeanList.get(i).getWorkSheetCode());
						reportWorksheetBean.setCustName(reportWorksheetBeanList.get(i).getCustName());
						reportWorksheetBean.setTel(reportWorksheetBeanList.get(i).getTel());
						reportWorksheetBean.setWorkSheetType(reportWorksheetBeanList.get(i).getWorkSheetType());
						reportWorksheetBean.setZone(reportWorksheetBeanList.get(i).getZone());
						reportWorksheetBean.setPersonnelName(reportWorksheetBeanList.get(i).getPersonnelName());
						reportWorksheetBean.setWorkDate(reportWorksheetBeanList.get(i).getWorkDate());
						reportWorksheetBean.setStatus(reportWorksheetBeanList.get(i).getStatus());

						reportWorksheetBean.setCheckHeaderTable(false);
						reportWorksheetBean.setCheckGroupData(false);
						resUse.add(reportWorksheetBean);

					}
					
				}
			}else if("3".equals(split)){
				int no = 1;
				title = "ข้อมูลใบงาน (รายการใบงานแยกตามเขตชุมชน)";
				List<ReportWorksheetBean> reportWorksheetBeanList = setReportWorksheetBean(worksheetList,split);
				logger.info("reportWorksheetBeanList size : {}",reportWorksheetBeanList.size());
				if(null != reportWorksheetBeanList && reportWorksheetBeanList.size() > 0){
					String zoneMain = reportWorksheetBeanList.get(0).getZone();
					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setGroupData("เขตชุมชน "+zoneMain);
					reportWorksheetBean.setCheckHeaderTable(false);
					reportWorksheetBean.setCheckGroupData(true);
					resUse.add(reportWorksheetBean);

					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setNoText("ลำดับ");
					reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
					reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
					reportWorksheetBean.setTelText("โทรศัพท์");
					reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
					reportWorksheetBean.setZoneText("เขตชุมชน");
					reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
					reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
					reportWorksheetBean.setStatusText("สถานะ");

					reportWorksheetBean.setCheckHeaderTable(true);
					reportWorksheetBean.setCheckGroupData(false);
					resUse.add(reportWorksheetBean);

					for(int i = 0; reportWorksheetBeanList.size() > i; i++){
						if(!zoneMain.equals(reportWorksheetBeanList.get(i).getZone())){
							
							zoneMain = reportWorksheetBeanList.get(i).getZone();
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setGroupData("เขตชุมชน "+zoneMain);
							reportWorksheetBean.setCheckHeaderTable(false);
							reportWorksheetBean.setCheckGroupData(true);
							resUse.add(reportWorksheetBean);
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setNoText("ลำดับ");
							reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
							reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
							reportWorksheetBean.setTelText("โทรศัพท์");
							reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
							reportWorksheetBean.setZoneText("เขตชุมชน");
							reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
							reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
							reportWorksheetBean.setStatusText("สถานะ");
							reportWorksheetBean.setCheckHeaderTable(true);
							reportWorksheetBean.setCheckGroupData(false);
							resUse.add(reportWorksheetBean);

							no = 1;
							--i;
							
							continue;
						}
						
						reportWorksheetBean = new ReportWorksheetBean();
						reportWorksheetBean.setNo(no++);
						reportWorksheetBean.setWorkSheetCode(reportWorksheetBeanList.get(i).getWorkSheetCode());
						reportWorksheetBean.setCustName(reportWorksheetBeanList.get(i).getCustName());
						reportWorksheetBean.setTel(reportWorksheetBeanList.get(i).getTel());
						reportWorksheetBean.setWorkSheetType(reportWorksheetBeanList.get(i).getWorkSheetType());
						reportWorksheetBean.setZone(reportWorksheetBeanList.get(i).getZone());
						reportWorksheetBean.setPersonnelName(reportWorksheetBeanList.get(i).getPersonnelName());
						reportWorksheetBean.setWorkDate(reportWorksheetBeanList.get(i).getWorkDate());
						reportWorksheetBean.setStatus(reportWorksheetBeanList.get(i).getStatus());

						reportWorksheetBean.setCheckHeaderTable(false);
						reportWorksheetBean.setCheckGroupData(false);
						resUse.add(reportWorksheetBean);

					}
					
				}
			}else if("4".equals(split)){
				int no = 1;
				title = "ข้อมูลใบงาน (รายการใบงานแยกตามเดือน)";
				List<ReportWorksheetBean> reportWorksheetBeanList = setReportWorksheetBean(worksheetList,split);
				logger.info("reportWorksheetBeanList size : {}",reportWorksheetBeanList.size());
				if(null != reportWorksheetBeanList && reportWorksheetBeanList.size() > 0){
					String workDateMain = " - ";
					if(!" - ".equals(reportWorksheetBeanList.get(0).getWorkDate())){
						workDateMain = formatMMMMyyyy.format(formatDataTh.parse(reportWorksheetBeanList.get(0).getWorkDate()));
					}
					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setGroupData("เดือน "+workDateMain);
					reportWorksheetBean.setCheckHeaderTable(false);
					reportWorksheetBean.setCheckGroupData(true);
					resUse.add(reportWorksheetBean);

					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setNoText("ลำดับ");
					reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
					reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
					reportWorksheetBean.setTelText("โทรศัพท์");
					reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
					reportWorksheetBean.setZoneText("เขตชุมชน");
					reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
					reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
					reportWorksheetBean.setStatusText("สถานะ");

					reportWorksheetBean.setCheckHeaderTable(true);
					reportWorksheetBean.setCheckGroupData(false);
					resUse.add(reportWorksheetBean);

					for(int i = 0; reportWorksheetBeanList.size() > i; i++){
						String workDate = " - ";
						if(!" - ".equals(reportWorksheetBeanList.get(i).getWorkDate())){
							workDate = formatMMMMyyyy.format(formatDataTh.parse(reportWorksheetBeanList.get(i).getWorkDate()));
						}
						if(!workDateMain.equals(workDate)){
							
							workDateMain = workDate;
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setGroupData("เดือน "+workDateMain);
							reportWorksheetBean.setCheckHeaderTable(false);
							reportWorksheetBean.setCheckGroupData(true);
							resUse.add(reportWorksheetBean);
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setNoText("ลำดับ");
							reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
							reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
							reportWorksheetBean.setTelText("โทรศัพท์");
							reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
							reportWorksheetBean.setZoneText("เขตชุมชน");
							reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
							reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
							reportWorksheetBean.setStatusText("สถานะ");
							reportWorksheetBean.setCheckHeaderTable(true);
							reportWorksheetBean.setCheckGroupData(false);
							resUse.add(reportWorksheetBean);

							no = 1;
							--i;
							
							continue;
						}
						
						reportWorksheetBean = new ReportWorksheetBean();
						reportWorksheetBean.setNo(no++);
						reportWorksheetBean.setWorkSheetCode(reportWorksheetBeanList.get(i).getWorkSheetCode());
						reportWorksheetBean.setCustName(reportWorksheetBeanList.get(i).getCustName());
						reportWorksheetBean.setTel(reportWorksheetBeanList.get(i).getTel());
						reportWorksheetBean.setWorkSheetType(reportWorksheetBeanList.get(i).getWorkSheetType());
						reportWorksheetBean.setZone(reportWorksheetBeanList.get(i).getZone());
						reportWorksheetBean.setPersonnelName(reportWorksheetBeanList.get(i).getPersonnelName());
						reportWorksheetBean.setWorkDate(reportWorksheetBeanList.get(i).getWorkDate());
						reportWorksheetBean.setStatus(reportWorksheetBeanList.get(i).getStatus());

						reportWorksheetBean.setCheckHeaderTable(false);
						reportWorksheetBean.setCheckGroupData(false);
						resUse.add(reportWorksheetBean);

					}
					
				}
			}
		}
		
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", "");
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("worksheetlist",request));
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
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		return null;
	}
	
	@RequestMapping(value = "bytechnician", method = RequestMethod.GET)
	public ModelAndView bytechnician(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : bytechnician][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if(isPermission()){
			//technician group
			List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
			List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>(); 
			TechnicianGroupController technicianGroupController = new TechnicianGroupController();
			for(TechnicianGroup technicianGroup : technicianGroups){
				TechnicianGroupBean TechnicianGroupBean = technicianGroupController.populateEntityToDto(technicianGroup);
				technicianGroupBeans.add(TechnicianGroupBean);
			}
			modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
			
			List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
			List<Zone> zones = zoneService.findAll();
			if(null != zones && !zones.isEmpty()){
				ZoneController zoneController = new ZoneController();
				for(Zone zone:zones){
					zoneBeans.add(zoneController.populateEntityToDto(zone));
				}
			}
			modelAndView.addObject("zones", zoneBeans);
			
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
	}
	
	public List<ReportWorksheetBean> setReportTechnicianBean(List<Worksheet> worksheetList, final String split, String technician) {
		final SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		List<ReportWorksheetBean> reportWorksheetBeanList = new ArrayList<ReportWorksheetBean>();
		if(null != worksheetList && worksheetList.size() > 0){

			for(Worksheet worksheet:worksheetList){
				if("0".equals(split) && "W".equals(worksheet.getStatus())){
					continue;
				}
				ReportWorksheetBean reportWorksheetBean = new ReportWorksheetBean();
				reportWorksheetBean.setWorkSheetCode(worksheet.getWorkSheetCode());
				ServiceApplication serviceApplication = worksheet.getServiceApplication();
				String custName = " - ", tel = " - ";
				if(null != serviceApplication){
				Customer customer = serviceApplication.getCustomer();
					if(null != customer){
						custName = String.format("%1s %2s",customer.getFirstName(),customer.getLastName());
						reportWorksheetBean.setCustName(custName);
						Contact contact = customer.getContact();
						if(null != contact){
							tel = contact.getMobile();
							reportWorksheetBean.setTel(tel);
						}
					}
					List<Address> addressList = serviceApplication.getAddresses();
					String zoneValue = " - ";
					for(Address address:addressList){
						if("3".equals(address.getAddressType())){
							Zone zone = address.getZone();
							if(null != zone) zoneValue = zone.getZoneDetail();
						}
					}
					reportWorksheetBean.setZone(zoneValue);
					String personnelName = " - ", workDate = " - ";
					List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = worksheet.getHistoryTechnicianGroupWorks();
					if(null != historyTechnicianGroupWorkList && historyTechnicianGroupWorkList.size() > 0){
						TechnicianGroup technicianGroup = historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size() - 1).getTechnicianGroup();
						if(null != technicianGroup){
							Personnel personnel = technicianGroup.getPersonnel();
							if(!"0".equals(technician) && personnel.getId() != Long.parseLong(technician)){
								continue;
							}
							if(null != personnel){
								personnelName = String.format("%1s %2s (%3s)",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName());
							}
						}
						workDate = formatDataTh.format(historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size() - 1).getAssignDate());
					}
					reportWorksheetBean.setPersonnelName(personnelName);
					reportWorksheetBean.setWorkDate(workDate);
				}
				reportWorksheetBean.setWorkSheetType(worksheet.getWorkSheetType());
				reportWorksheetBean.setStatus(worksheet.getStatus());
				
				reportWorksheetBeanList.add(reportWorksheetBean);
			}
			
			if (reportWorksheetBeanList.size() > 0) {
				  Collections.sort(reportWorksheetBeanList, new Comparator<ReportWorksheetBean>() {
				      public int compare(final ReportWorksheetBean object1, final ReportWorksheetBean object2) {
						if("1".equals(split)){
							return object2.getWorkSheetType().compareTo(object1.getWorkSheetType());
						}else if("2".equals(split)){
							return object2.getStatus().compareTo(object1.getStatus());
						}else if("3".equals(split)){
							return object2.getZone().compareTo(object1.getZone());
						}else if("4".equals(split)){
							try {
								if(" - ".equals(object1.getWorkDate()) || " - ".equals(object2.getWorkDate())) return 1;
								return formatDataTh.parse(object2.getWorkDate()).compareTo(formatDataTh.parse(object1.getWorkDate()));
							} catch (ParseException e) {
								e.printStackTrace();
								return 0;
							}
						}else{
							return object2.getPersonnelName().compareTo(object1.getPersonnelName());
						}
				      }
				  });

			}
			
		}

		return reportWorksheetBeanList;
	}
	
	@RequestMapping(value = "bytechnician/exportPdf/"
			+ "reportrange/{reportrange}/technician/{technician}/worksheetStatus/{worksheetStatus}/"
			+ "zone/{zone}", method = RequestMethod.GET)
	public ModelAndView bytechnicianExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String technician,
			@PathVariable String worksheetStatus,
			@PathVariable String zone,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : worksheetlistExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, technician : {}, worksheetStatus : {}, "
				+ "zone : {}",reportrange,technician,worksheetStatus,zone);
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		String jobType = "0", split = "0";
		List<Worksheet> worksheetList = workSheetService.getDataWorksheetForReport(reportrange,jobType,worksheetStatus,zone,split);
		logger.info("worksheetList size : {}",worksheetList.size());

		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != worksheetList && worksheetList.size() > 0){
			ReportWorksheetBean reportWorksheetBean = new ReportWorksheetBean();
			if("0".equals(split)){
				int no = 1;
				title = "ข้อมูลใบงาน (รายการใบงานแยกตามรูปแบบใบงาน)";
				List<ReportWorksheetBean> reportWorksheetBeanList = setReportTechnicianBean(worksheetList,split,technician);
				logger.info("reportWorksheetBeanList size : {}",reportWorksheetBeanList.size());
				if(null != reportWorksheetBeanList && reportWorksheetBeanList.size() > 0){
					String personnelNameMain = reportWorksheetBeanList.get(0).getPersonnelName();
					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setGroupData("พนักงานช่าง "+personnelNameMain);
					reportWorksheetBean.setCheckHeaderTable(false);
					reportWorksheetBean.setCheckGroupData(true);
					resUse.add(reportWorksheetBean);

					reportWorksheetBean = new ReportWorksheetBean();
					reportWorksheetBean.setNoText("ลำดับ");
					reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
					reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
					reportWorksheetBean.setTelText("โทรศัพท์");
					reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
					reportWorksheetBean.setZoneText("เขตชุมชน");
					reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
					reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
					reportWorksheetBean.setStatusText("สถานะ");

					reportWorksheetBean.setCheckHeaderTable(true);
					reportWorksheetBean.setCheckGroupData(false);
					resUse.add(reportWorksheetBean);

					for(int i = 0; reportWorksheetBeanList.size() > i; i++){
						if(!personnelNameMain.equals(reportWorksheetBeanList.get(i).getPersonnelName())){
							
							personnelNameMain = reportWorksheetBeanList.get(i).getPersonnelName();
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setGroupData("พนักงานช่าง "+personnelNameMain);
							reportWorksheetBean.setCheckHeaderTable(false);
							reportWorksheetBean.setCheckGroupData(true);
							resUse.add(reportWorksheetBean);
							
							reportWorksheetBean = new ReportWorksheetBean();
							reportWorksheetBean.setNoText("ลำดับ");
							reportWorksheetBean.setWorkSheetCodeText("รหัสใบงาน");
							reportWorksheetBean.setCustNameText("ชื่อ-สกุลลูกค้า");
							reportWorksheetBean.setTelText("โทรศัพท์");
							reportWorksheetBean.setWorkSheetTypeText("ประเภทใบงาน");
							reportWorksheetBean.setZoneText("เขตชุมชน");
							reportWorksheetBean.setPersonnelNameText("ผู้รับมอบหมาย");
							reportWorksheetBean.setWorkDateText("วันปฏิบัติงาน");
							reportWorksheetBean.setStatusText("สถานะ");
							reportWorksheetBean.setCheckHeaderTable(true);
							reportWorksheetBean.setCheckGroupData(false);
							resUse.add(reportWorksheetBean);

							no = 1;
							--i;
							
							continue;
						}
						
						reportWorksheetBean = new ReportWorksheetBean();
						reportWorksheetBean.setNo(no++);
						reportWorksheetBean.setWorkSheetCode(reportWorksheetBeanList.get(i).getWorkSheetCode());
						reportWorksheetBean.setCustName(reportWorksheetBeanList.get(i).getCustName());
						reportWorksheetBean.setTel(reportWorksheetBeanList.get(i).getTel());
						reportWorksheetBean.setWorkSheetType(reportWorksheetBeanList.get(i).getWorkSheetType());
						reportWorksheetBean.setZone(reportWorksheetBeanList.get(i).getZone());
						reportWorksheetBean.setPersonnelName(reportWorksheetBeanList.get(i).getPersonnelName());
						reportWorksheetBean.setWorkDate(reportWorksheetBeanList.get(i).getWorkDate());
						reportWorksheetBean.setStatus(reportWorksheetBeanList.get(i).getStatus());

						reportWorksheetBean.setCheckHeaderTable(false);
						reportWorksheetBean.setCheckGroupData(false);
						resUse.add(reportWorksheetBean);

					}
					
				}
				
			}
		}
		
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", "");
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("worksheetlist",request));
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
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		return null;
	}
	

	@RequestMapping(value = "bydailywork", method = RequestMethod.GET)
	public ModelAndView bydailywork(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : bydailywork][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if(isPermission()){
			//technician group
			List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
			List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>(); 
			TechnicianGroupController technicianGroupController = new TechnicianGroupController();
			for(TechnicianGroup technicianGroup : technicianGroups){
				TechnicianGroupBean TechnicianGroupBean = technicianGroupController.populateEntityToDto(technicianGroup);
				technicianGroupBeans.add(TechnicianGroupBean);
			}
			modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
			
			List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
			List<Zone> zones = zoneService.findAll();
			if(null != zones && !zones.isEmpty()){
				ZoneController zoneController = new ZoneController();
				for(Zone zone:zones){
					zoneBeans.add(zoneController.populateEntityToDto(zone));
				}
			}
			modelAndView.addObject("zones", zoneBeans);
			
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "bydailywork/exportPdf/"
			+ "reportrange/{reportrange}/technician/{technician}/"
			+ "zone/{zone}", method = RequestMethod.GET)
	public ModelAndView bydailyworkExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String technician,
			@PathVariable String zone,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("[method : bydailyworkExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, technician : {}, zone : {}",reportrange,technician,zone);
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		String jobType = "0", split = "0", worksheetStatus = "0";
		List<Worksheet> worksheetList = workSheetService.getDataWorksheetForReport(reportrange,jobType,worksheetStatus,zone,split,technician);
		logger.info("worksheetList size : {}",worksheetList.size());

		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeThV2 = new SimpleDateFormat("วันที่ dd เดือนMMMM พุทธศักราช yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeThHHmm = new SimpleDateFormat("dd MMMM yyyy เวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != worksheetList && worksheetList.size() > 0){
			int i = 1;
			for(Worksheet worksheet:worksheetList){
				ServiceApplication serviceApplication = worksheet.getServiceApplication();
				ReportDailyWork reportDailyWork = new ReportDailyWork();
				reportDailyWork.setNo("ลำดับที่ "+(i++));
				String numberPoints = "";
				if(null != serviceApplication){
					AddPointWorksheetBean addPointWorksheetBean = loadPointAll(serviceApplication.getId());
					int sumPoint = addPointWorksheetBean.getDigitalPoint()+addPointWorksheetBean.getAnalogPoint();
					numberPoints = String.format("%1s จุด ( Digital %2s จุด และ Analog %3s จุด )",sumPoint,addPointWorksheetBean.getDigitalPoint(),addPointWorksheetBean.getAnalogPoint());
					Customer customer = serviceApplication.getCustomer();
					if(null != customer){
						String custName = String.format("%1s%2s %3s",customer.getPrefix(),customer.getFirstName(),customer.getLastName());
						reportDailyWork.setCustName(custName);
					}
					Contact contact = customer.getContact();
					if(null != customer){
						reportDailyWork.setCustTel(contact.getMobile());
					}
					
					List<Address> addressList = serviceApplication.getAddresses();
					if(null != addressList && addressList.size() > 0){
						String nearbyPlaces = " - ";
						for(Address address:addressList){
							if("3".equals(address.getAddressType())){
								AddressBean addressBean = setAddressBean(address);
								reportDailyWork.setAddress(addressBean.getCollectAddressDetail());
								nearbyPlaces = address.getNearbyPlaces();
								break;
							}
						}
					}
				}
				reportDailyWork.setWorkSheetType(worksheet.getWorkSheetType(),numberPoints);
				Invoice invoice = worksheet.getInvoice();
				if(null != invoice){
					reportDailyWork.setCraetedBy(invoice.getCreatedBy());
					reportDailyWork.setCraetedDate(formatDataTimeThHHmm.format(invoice.getCreateDate()));
					reportDailyWork.setInvoiceCode(invoice.getInvoiceCode());
					reportDailyWork.setAmount(""+invoice.getAmount());
				}
				resUse.add(reportDailyWork);
			}
		}
		jasperRender.setBeanList(resUse);
		title = "ใบงานประจำวัน";
		Map<String, Object> params = new HashMap<String, Object>();
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", formatDataTimeThV2.format(new Date()));
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("bydailywork",request));
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
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		return null;
	}
	
	@RequestMapping(value = "workSheet/exportPdf/workSheetId/{workSheetId}", method = RequestMethod.GET)
	public ModelAndView workSheetExportPdf(Model model, @PathVariable Long workSheetId, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		logger.info("[method : workSheetExportPdf][Type : Controller][workSheetId : {}]", workSheetId);
		ModelAndView modelAndView = new ModelAndView();
		String nameReport = EMPTY_STRING;
		// check permission
		if (isPermission()) {
			JasperRender jasperRender = new JasperRender();
			List<Object> resUse = new ArrayList<Object>();
			Worksheet worksheet = workSheetService.getWorksheetById(workSheetId);
			if (null != worksheet) {
				SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
				SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
				SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
				SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy",
						new Locale("TH", "th"));
				SimpleDateFormat formatDataTimeTh = new SimpleDateFormat(
						"พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));

				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				// get data worksheet
				Worksheet worksheetDetail = workSheetService.getWorksheetById(workSheetId);
				WorksheetBean worksheetBean = new WorksheetBean() {};
				if (worksheetDetail != null) {
					worksheetBean = loadWorksheetBean(worksheetDetail);
					// current bill
					List<InvoiceDocumentBean> invoiceCurrentBill = new ArrayList<InvoiceDocumentBean>();
					List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();

					InvoiceController invController = new InvoiceController();
					invController.setMessageSource(messageSource);

					for (Invoice invoice : worksheetDetail.getServiceApplication().getInvoices()) {
						if (invoice.getCreateDate() != null) {
							// InvoiceDocumentBean invoiceDocumentBean = new
							// InvoiceDocumentBean();
							if (invoice.getStatus().equals("W")) {
								invoiceCurrentBill.add(invController.PoppulateInvoiceEntityToDto(invoice));

							} else if (invoice.getStatus().equals("O")) {
								invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
							}
						}
					}
					worksheetBean.getServiceApplication().setInvoiceCurrentBill(invoiceCurrentBill);
					worksheetBean.getServiceApplication().setInvoiceOverBill(invoiceOverBill);

				}
				
				int digitalPoint = 0;
				int analogPoint = 0;
				// ใบงานเสริมจุด
				
				List<ProductItem> productItems;
				try {
					productItems = productItemService.getProductItemByServiceApplicationId(worksheetBean.getServiceApplication().getId());
					if (null != productItems && productItems.size() > 0) {
						for (ProductItem productItem : productItems) {

							Worksheet worksheetMain = productItem.getWorkSheet();
							ServiceProduct serviceProduct = productItem.getServiceProduct();
							// ใบงานติดตั้ง
							if (null != worksheetMain) {
								WorksheetSetup worksheetSetup = worksheetMain.getWorksheetSetup();
								if (null != worksheetSetup && null != serviceProduct) {
									String productCode = serviceProduct.getProductCode();
									if ("00002".equals(productCode)) {
										digitalPoint += productItem.getQuantity();
									}
									if ("00003".equals(productCode)) {
										analogPoint += productItem.getQuantity();
									}
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<Worksheet> worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId("C_AP",
						worksheetBean.getServiceApplication().getId());

				if (null != worksheets && worksheets.size() > 0) {
					for (Worksheet worksheetR : worksheets) {
						WorksheetAddPoint worksheetAddPoint = worksheetR.getWorksheetAddPoint();
						if (null != worksheetAddPoint && worksheetAddPoint.isActive()) {
							digitalPoint += worksheetAddPoint.getDigitalPoint();
							analogPoint += worksheetAddPoint.getAnalogPoint();
						}
					}
				}

				// ใบงานลดจุด
				worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId("C_RP",
						worksheetBean.getServiceApplication().getId());

				if (null != worksheets && worksheets.size() > 0) {
					for (Worksheet worksheetR : worksheets) {
						WorksheetReducePoint worksheetReducePoint = worksheetR.getWorksheetReducePoint();
						if (null != worksheetReducePoint && worksheetReducePoint.isActive()) {
							digitalPoint -= worksheetReducePoint.getDigitalPoint();
							analogPoint -= worksheetReducePoint.getAnalogPoint();
						}
					}
				}
				Map<String, Object> params = new HashMap<String, Object>();
				String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-login.png");
				params.put("pathLogo", pathLogo);
				// workSheetSetup
				if ("C_S".equals(worksheet.getWorkSheetType())) {// ใบงานติดตั้ง
					nameReport = "workSheetSetup";
					ReportWorksheetSetUpBean reportWorksheetSetUpBean = new ReportWorksheetSetUpBean();
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetSetUpBean, request);

					resUse.add(reportWorksheetSetUpBean);
				} else if ("C_AP".equals(worksheet.getWorkSheetType())) {// ใบงานเสริมจุดบริการ
					nameReport = "workSheetAddPoint";
					ReportWorksheetAddPointBean reportWorksheetAddPointBean = new ReportWorksheetAddPointBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetAddPointBean, request);
					
					// set detail
					reportWorksheetAddPointBean.setAnalogPointReduce(worksheetBean.getAddPointWorksheetBean().getAnalogPoint());
					reportWorksheetAddPointBean.setDigitalPointReduce(worksheetBean.getAddPointWorksheetBean().getDigitalPoint());
					reportWorksheetAddPointBean.setAnalogPoint(analogPoint);
					reportWorksheetAddPointBean.setDigitalPoint(digitalPoint);
					reportWorksheetAddPointBean.setMonthlyFree(String.valueOf(worksheetBean.getAddPointWorksheetBean().getMonthlyFree()));
					
					resUse.add(reportWorksheetAddPointBean);
				} else if ("C_C".equals(worksheet.getWorkSheetType())) {// ใบงานการจั้มสาย

				} else if ("C_TTV".equals(worksheet.getWorkSheetType())) {// ใบงานการจูน TV
					nameReport = "workSheetTune";
					ReportWorksheetTuneBean reportWorksheetTuneBean = new ReportWorksheetTuneBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetTuneBean, request);
					
					// set detail
					reportWorksheetTuneBean.setTuneType(String.valueOf(worksheetBean.getTuneWorksheetBean().getTuneType()));
					resUse.add(reportWorksheetTuneBean);
				} else if ("C_RC".equals(worksheet.getWorkSheetType())) {// ใบงานการซ่อมสัญญาณ
					nameReport = "workSheetRepair";
					ReportWorksheetRepairBean reportWorksheetRepairBean = new ReportWorksheetRepairBean();
					
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetRepairBean, request);
					
					resUse.add(reportWorksheetRepairBean);
				} else if ("C_ASTB".equals(worksheet.getWorkSheetType())) {// ใบงานเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี
					nameReport = "workSheetAddSettop";
					ReportWorksheetAddSettopBean reportWorksheetAddSettopBean = new ReportWorksheetAddSettopBean(); 

					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables = new ArrayList<WorkSheetReportBeanSubTable>();
					if (null != worksheetBean.getProductItemList()) {
						for (ProductItemBean productItemBean : worksheetBean.getProductItemList()) {
							if (null != productItemBean.getProductItemWorksheetBeanList() && "E".equals(productItemBean.getType()) && 
									"A".equals(productItemBean.getProductTypeMatch())) {
								for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean.getProductItemWorksheetBeanList()) {
									WorkSheetReportBeanSubTable workReport = new WorkSheetReportBeanSubTable();
									workReport.setDeposit(String.valueOf(productItemWorksheetBean.getAmount()));
									workReport.setPrice(String.valueOf(productItemWorksheetBean.getPrice()));
									workReport.setQuantity(String.valueOf(productItemWorksheetBean.getQuantity()));
									workReport.setFloatDeposit(productItemWorksheetBean.getAmount());
									workReport.setListEquipment(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName());
									workReport.setSerialNo(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo());
									workReport.setUnitName(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
								
									workSheetReportBeanSubTables.add(workReport);
								}
							}
						}
					}
					CustomerBean customer = worksheetBean.getServiceApplication().getCustomer();
					List<AddressBean> address = worksheetBean.getServiceApplication().getAddressList();
					for (AddressBean address2 : address) {
						reportWorksheetAddSettopBean.setNo(address2.getNo());
						reportWorksheetAddSettopBean.setAlley(address2.getAlley());
						reportWorksheetAddSettopBean.setRoad(address2.getRoad());
						reportWorksheetAddSettopBean.setDistrictName(address2.getDistrictBean().getDISTRICT_NAME());
						reportWorksheetAddSettopBean.setAmphurName(address2.getAmphurBean().getAMPHUR_NAME());
						reportWorksheetAddSettopBean.setProvinceName(address2.getProvinceBean().getPROVINCE_NAME());
						reportWorksheetAddSettopBean.setPostcode(address2.getPostcode());
					}
					
					reportWorksheetAddSettopBean.setCustomer(customer.getFirstName() +"  "+customer.getLastName());
					reportWorksheetAddSettopBean.setIdentityNumber(customer.getIdentityNumber());
					reportWorksheetAddSettopBean.setMobile(customer.getContact().getMobile());
					reportWorksheetAddSettopBean.setCreateDate(worksheetBean.getCreateDateTh());
					reportWorksheetAddSettopBean.setWorkCode(worksheetBean.getWorkSheetCode());
					reportWorksheetAddSettopBean.setCusCode(customer.getCustCode());
					reportWorksheetAddSettopBean.setWorkSheetReportBeanSubTables(workSheetReportBeanSubTables);
					
					ServiceApplication serviceApplication = worksheet.getServiceApplication();
					if(null != serviceApplication){
						Company company = serviceApplication.getCompany();
						if(null != company){
							reportWorksheetAddSettopBean.setCompanyName(company.getCompanyName());
							reportWorksheetAddSettopBean.setCompanyAddress(company.getAddress().getDetail());
							reportWorksheetAddSettopBean.setCompanyMobile(null == company.getContact()?"-":company.getContact().getMobile());
							reportWorksheetAddSettopBean.setCompanyFax(null == company.getContact()?"-":company.getContact().getFax());
						}
					}
					
					resUse.add(reportWorksheetAddSettopBean);
					jasperRender.setParams(params);
				} else if ("C_MP".equals(worksheet.getWorkSheetType())) {// ใบงานการย้ายจุด
					nameReport = "workSheetMovePoint";
					ReportWorksheetMovePointBean reportWorksheetMovePointBean = new ReportWorksheetMovePointBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetMovePointBean, request);
					
					// set detail
					reportWorksheetMovePointBean.setAnalogPointReduce(worksheetBean.getMovePointWorksheetBean().getAnalogPoint());
					reportWorksheetMovePointBean.setDigitalPointReduce(worksheetBean.getMovePointWorksheetBean().getDigitalPoint());
					reportWorksheetMovePointBean.setAnalogPoint(analogPoint);
					reportWorksheetMovePointBean.setDigitalPoint(digitalPoint);
					
					resUse.add(reportWorksheetMovePointBean);
				} else if ("C_RP".equals(worksheet.getWorkSheetType())) {// ใบงานการลดจุด
					nameReport = "workSheetReducePoint";
					ReportWorksheetReducePointBean reportWorksheetReducePointBean = new ReportWorksheetReducePointBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetReducePointBean, request);
					
					// set detail
					if (worksheetBean.getReducePointWorksheetBean().getAnalogPoint() > analogPoint) {
						reportWorksheetReducePointBean.setAnalogPointReduce(analogPoint);
					} else {
						reportWorksheetReducePointBean.setAnalogPointReduce(worksheetBean.getReducePointWorksheetBean().getAnalogPoint());
					}
					if (worksheetBean.getReducePointWorksheetBean().getDigitalPoint()  > digitalPoint) {
						reportWorksheetReducePointBean.setDigitalPointReduce(digitalPoint);						
					} else {
						reportWorksheetReducePointBean.setDigitalPointReduce(worksheetBean.getReducePointWorksheetBean().getDigitalPoint());
					}
					reportWorksheetReducePointBean.setAnalogPoint(analogPoint);
					reportWorksheetReducePointBean.setDigitalPoint(digitalPoint);
					reportWorksheetReducePointBean.setMonthlyFree(String.valueOf(worksheetBean.getReducePointWorksheetBean().getMonthlyFree()));
					
					resUse.add(reportWorksheetReducePointBean);
				} else if ("C_CU".equals(worksheet.getWorkSheetType())) {// ใบงานการตัดสาย
					nameReport = "workSheetCut";
					ReportWorksheetCutBean reportWorksheetCutBean = new ReportWorksheetCutBean();

					// set header footer
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetCutBean, request);

					// set detail	
					reportWorksheetCutBean.setReporter(worksheetBean.getCutWorksheetBean().getReporter());
					reportWorksheetCutBean.setMobile(worksheetBean.getCutWorksheetBean().getMobile());
					reportWorksheetCutBean.setCancelDate(worksheetBean.getCutWorksheetBean().getCancelDate());
					reportWorksheetCutBean.setEndPackageDate(worksheetBean.getCutWorksheetBean().getEndPackageDate());						
					reportWorksheetCutBean.setCutWorkType(String.valueOf(worksheetBean.getCutWorksheetBean().getCutWorkType()));
					
					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables = new ArrayList<WorkSheetReportBeanSubTable>();
					int count = 0;
					if (null != worksheetBean.getServiceApplication().getProductitemList()) {
						for (ProductItemBean productitemList : worksheetBean.getServiceApplication().getProductitemList()) {
							if (null != productitemList.getProductItemWorksheetBeanList()) {
								for (ProductItemWorksheetBean productItemWorksheetBean : productitemList.getProductItemWorksheetBeanList()) {
									if (productItemWorksheetBean.isLend()) {
										WorkSheetReportBeanSubTable workReport = new WorkSheetReportBeanSubTable();
										workReport.setDeposit(String.valueOf(productItemWorksheetBean.getDeposit()) + " บาท");
										workReport.setQuantity(String.valueOf(productItemWorksheetBean.getQuantity()));
										workReport.setUnitName(String.valueOf(productitemList.getProduct().getUnit().getUnitName()));
										workReport.setListEquipment(String.valueOf(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean()));
										String check = "0";
										if (productItemWorksheetBean.isReturnEquipment()) {
											check = "1";
										}
										EquipmentProductBean equipmentProductBean = (EquipmentProductBean)productitemList.getProduct();
										if (null != equipmentProductBean.getEquipmentProductItemBeans()) {
											workReport.setListEquipment(equipmentProductBean.getProductName()+" ("+equipmentProductBean.getProductCode()+") / "+equipmentProductBean.getEquipmentProductItemBeans().get(count).getSerialNo());
											count++;
										}										
										
										workReport.setReturnEquipment(check);
										if (productItemWorksheetBean.getLendStatus() == 1) {
											workReport.setLendStatus("1");
										} else if (productItemWorksheetBean.getLendStatus() == 0) {
											workReport.setLendStatus("0");
										} else if (productItemWorksheetBean.getLendStatus() == 5) {
											workReport.setLendStatus("5");
										} else if (productItemWorksheetBean.getLendStatus() == 7) {
											workReport.setLendStatus("7");
										}										
										workSheetReportBeanSubTables.add(workReport);
									}										
								}								
								
							}
							
						}
					}
					
					reportWorksheetCutBean.setWorkSheetReportBeanSubTables(workSheetReportBeanSubTables);
					resUse.add(reportWorksheetCutBean);

				} else if ("C_M".equals(worksheet.getWorkSheetType())) {// ใบงานการย้ายสาย
					nameReport = "workSheetMove";
					ReportWorksheetMoveBean reportWorksheetMoveBean = new ReportWorksheetMoveBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetMoveBean, request);
					
					// set detail
					reportWorksheetMoveBean.setCollectAddressDetail(worksheetBean.getMoveWorksheetBean().getAddress().getCollectAddressDetail());
					reportWorksheetMoveBean.setNearbyPlacess(worksheetBean.getMoveWorksheetBean().getAddress().getNearbyPlaces());
					reportWorksheetMoveBean.setZoneDetail(worksheetBean.getMoveWorksheetBean().getAddress().getZoneBean().getZoneDetail());
					reportWorksheetMoveBean.setOriginalPointAll(analogPoint + digitalPoint);
					reportWorksheetMoveBean.setMovePointAll(worksheetBean.getMoveWorksheetBean().getAnalogPoint() + worksheetBean.getMoveWorksheetBean().getDigitalPoint());
					
					resUse.add(reportWorksheetMoveBean);
				} else if ("C_B".equals(worksheet.getWorkSheetType())) {// ใบงานยืมอุปกรณ์รับสัญญาณเคเบิลทีวี
					nameReport = "workSheetBorrow";
					ReportWorksheetBorrowBean reportWorksheetBorrowBean = new ReportWorksheetBorrowBean(); 

					List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables = new ArrayList<WorkSheetReportBeanSubTable>();
					if (null != worksheetBean.getProductItemList()) {
						for (ProductItemBean productItemBean : worksheetBean.getProductItemList()) {
							if (null != productItemBean.getProductItemWorksheetBeanList() && "E".equals(productItemBean.getType()) &&
									"B".equals(productItemBean.getProductTypeMatch())) {
								for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean.getProductItemWorksheetBeanList()) {
									WorkSheetReportBeanSubTable workReport = new WorkSheetReportBeanSubTable();
									workReport.setDeposit(String.valueOf(productItemWorksheetBean.getDeposit()));
									workReport.setPrice(String.valueOf(productItemWorksheetBean.getPrice()));
									workReport.setQuantity(String.valueOf(productItemWorksheetBean.getQuantity()));
									workReport.setFloatDeposit(productItemWorksheetBean.getDeposit());
									workReport.setListEquipment(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName());
									workReport.setSerialNo(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo());
									workReport.setUnitName(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
								
									workSheetReportBeanSubTables.add(workReport);
								}
							}
						}
					}
					CustomerBean customer = worksheetBean.getServiceApplication().getCustomer();
					List<AddressBean> address = worksheetBean.getServiceApplication().getAddressList();
					for (AddressBean address2 : address) {
						reportWorksheetBorrowBean.setNo(address2.getNo());
						reportWorksheetBorrowBean.setAlley(address2.getAlley());
						reportWorksheetBorrowBean.setRoad(address2.getRoad());
						reportWorksheetBorrowBean.setDistrictName(address2.getDistrictBean().getDISTRICT_NAME());
						reportWorksheetBorrowBean.setAmphurName(address2.getAmphurBean().getAMPHUR_NAME());
						reportWorksheetBorrowBean.setProvinceName(address2.getProvinceBean().getPROVINCE_NAME());
						reportWorksheetBorrowBean.setPostcode(address2.getPostcode());
					}
					
					reportWorksheetBorrowBean.setCustomer(customer.getFirstName() +"  "+customer.getLastName());
					reportWorksheetBorrowBean.setIdentityNumber(customer.getIdentityNumber());
					reportWorksheetBorrowBean.setMobile(customer.getContact().getMobile());
					reportWorksheetBorrowBean.setCreateDate(worksheetBean.getCreateDateTh());
					reportWorksheetBorrowBean.setWorkCode(worksheetBean.getWorkSheetCode());
					reportWorksheetBorrowBean.setCusCode(customer.getCustCode());
					reportWorksheetBorrowBean.setWorkSheetReportBeanSubTables(workSheetReportBeanSubTables);
					
					ServiceApplication serviceApplication = worksheet.getServiceApplication();
					if(null != serviceApplication){
						Company company = serviceApplication.getCompany();
						if(null != company){
							reportWorksheetBorrowBean.setCompanyName(company.getCompanyName());
							reportWorksheetBorrowBean.setCompanyAddress(company.getAddress().getDetail());
							reportWorksheetBorrowBean.setCompanyMobile(null == company.getContact()?"-":company.getContact().getMobile());
							reportWorksheetBorrowBean.setCompanyFax(null == company.getContact()?"-":company.getContact().getFax());
						}
					}
					
					resUse.add(reportWorksheetBorrowBean);
					jasperRender.setParams(params);
				} else if ("I_AP".equals(worksheet.getWorkSheetType())) {// วิเคราะห์ปัญหา
					nameReport = "workSheetAnalyzeProblems";
					ReportWorksheetAnalyzeProblemsBean reportWorksheetAnalyzeProblemsBean = new ReportWorksheetAnalyzeProblemsBean();														
					// set header footer ของใบงาน
					setHeaderFooterWorkSheetReport(worksheet, reportWorksheetAnalyzeProblemsBean, request);
					
					// set detail
					reportWorksheetAnalyzeProblemsBean.setMenuReportName(worksheetBean.getAnalyzeProblemsWorksheetBean().getMenuReportName());
					resUse.add(reportWorksheetAnalyzeProblemsBean);
				}

				jasperRender.setBeanList(resUse);
				try {
					byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF,
							jasperJrxmlComponent.compileJasperReport(nameReport, request));
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

			} else {
				// no permission
				modelAndView.setViewName(PERMISSION_DENIED);
				return modelAndView;
			}
		}
		return null;
	}

	private void setHeaderFooterWorkSheetReport(Worksheet worksheet,ReportWorksheetMainBean reportWorksheetMainBean,HttpServletRequest request) {
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("- พิมพ์เมื่อวันที่ dd MMMM yyyy เวลา HH.mm น. -", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("%1s%2s %3s",personnel.getPrefix(),personnel.getFirstName(),personnel.getLastName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		if(null != worksheet){
			// begin Header
			reportWorksheetMainBean.setBarCode(worksheet.getWorkSheetCode());
			reportWorksheetMainBean.setWorkSheetName(worksheet.getWorkSheetType());
			reportWorksheetMainBean.setWorkSheetCode(String.format("เลขที่ใบงาน %1s",worksheet.getWorkSheetCode()));
			reportWorksheetMainBean.setRecipient(printBy);
			// end Header
			
			// -> ข้อมูลลูกค้าและบริการ
			ServiceApplication serviceApplication = worksheet.getServiceApplication();
			if(null != serviceApplication){
				Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					reportWorksheetMainBean.setCustomerName(String.format("%1s %2s (%3s)",customer.getFirstName(),customer.getLastName(),customer.getCustCode()));
					reportWorksheetMainBean.setCustomerType(customer.getCustType());
					
					List<Address> addressList = serviceApplication.getAddresses();
					if(null != addressList && addressList.size() > 0){
						String nearbyPlaces = " - ";
						for(Address address:addressList){
							if("3".equals(address.getAddressType())){
								AddressBean addressBean = setAddressBean(address);
								reportWorksheetMainBean.setPlace(addressBean.getCollectAddressDetail());
								nearbyPlaces = address.getNearbyPlaces();
								break;
							}
						}
						reportWorksheetMainBean.setNearbyPlaces(nearbyPlaces);
					}
					
					Contact contact = customer.getContact();
					if(null != contact){
					reportWorksheetMainBean.setTelephone(contact.getMobile());
					}
				}
				String serviceDate = " - ";
				List<Worksheet> worksheetList = serviceApplication.getWorksheets();
				if(null != worksheetList){
					for(Worksheet ws:worksheetList){
						WorksheetSetup wsSetUp = ws.getWorksheetSetup();
						if(null != wsSetUp){
							List<HistoryTechnicianGroupWork> hisList = ws.getHistoryTechnicianGroupWorks();
							if(null != hisList && hisList.size() > 0 ){
								serviceDate = formatDataTh.format(hisList.get(hisList.size()-1).getAssignDate());
							}
						}
					}
				}
				
				String packageName = " - ";
				ServicePackage servicePackage = serviceApplication.getServicePackage();
				if(null != servicePackage){
					packageName = servicePackage.getPackageName();
					servicePackage.getCreateDate();
				}
				reportWorksheetMainBean.setServiceContract(String.format("%1s [ %2s ]",serviceApplication.getServiceApplicationNo(),packageName));
				reportWorksheetMainBean.setServiceDate(serviceDate);
				reportWorksheetMainBean.setStatus(serviceApplication.getStatus());
				
				AddPointWorksheetBean addPointWorksheetBean = loadPointAll(serviceApplication.getId());
				int sumPoint = addPointWorksheetBean.getDigitalPoint()+addPointWorksheetBean.getAnalogPoint();
				String numberPoints = String.format("%1s จุด ( Digital %2s จุด และ Analog %3s จุด )",sumPoint,addPointWorksheetBean.getDigitalPoint(),addPointWorksheetBean.getAnalogPoint());
				reportWorksheetMainBean.setNumberPoints(numberPoints);
				
			}
			// <- ข้อมูลลูกค้าและบริการ
			
			// -> รอบบิลค้างชาระ
			List<ReportBilUnpaidBean> reportBilUnpaidBeanList = getInvoiceOverBill(worksheet);
			reportWorksheetMainBean.setReportBilUnpaidBeanList(reportBilUnpaidBeanList);
			String sumPayment = "0.00 บาท";
			if(null != reportBilUnpaidBeanList && reportBilUnpaidBeanList.size() > 0){
				sumPayment = reportBilUnpaidBeanList.get(reportBilUnpaidBeanList.size()-1).getSumPayment();
			}
			reportWorksheetMainBean.setBillCycleText(String.format("รอบบิลค้างชำระ ( รวมทั้งสิ้น %1s)",sumPayment));
			// <- รอบบิลค้างชาระ
			
			// -> รายการวัสดุอุปกรณ์ที่ใช้
			List<ReportMaterialsUsedBean> reportMaterialsUsedBeanList = getMaterialsUsed(worksheet);
			reportWorksheetMainBean.setReportMaterialsUsedBeanList(reportMaterialsUsedBeanList);
			// <- รายการวัสดุอุปกรณ์ที่ใช้
			
			// -> ทีมพนักงานช่างปฏิบัติงาน
			List<ReportTechnicianGroupBean> reportTechnicianGroupBeanList = getTechnicianGroup(worksheet);
			reportWorksheetMainBean.setReportTechnicianGroupBeanList(reportTechnicianGroupBeanList);
			// <- ทีมพนักงานช่างปฏิบัติงาน
			
			reportWorksheetMainBean.setLine(worksheet.getWorkSheetType());
			
			// begin Footer
			// begin หาทีมช่างที่รับผิดชอบ
			List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = worksheet.getHistoryTechnicianGroupWorks();
			if(null != historyTechnicianGroupWorkList && historyTechnicianGroupWorkList.size() > 0){
				HistoryTechnicianGroupWork historyTechnicianGroupWork = historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size() - 1);
				TechnicianGroup technicianGroup = historyTechnicianGroupWork.getTechnicianGroup();
				if(null != technicianGroup){
					Personnel person = technicianGroup.getPersonnel();
					String technician = String.format("%1s%2s %3s",person.getPrefix(),person.getFirstName(),person.getLastName());
					reportWorksheetMainBean.setTechnician(technician);
					String workDate = formatDataTh.format(historyTechnicianGroupWork.getAssignDate());
					reportWorksheetMainBean.setWorkDate(workDate);
					String responsiblePerson = technicianGroup.getTechnicianGroupName();
					reportWorksheetMainBean.setResponsiblePerson(responsiblePerson);
				}
			
			}
			// end หาทีมช่างที่รับผิดชอบ
			reportWorksheetMainBean.setPersonnelRecords(printBy);
			reportWorksheetMainBean.setPrintDate(formatDataTimeTh.format(new Date()));
			reportWorksheetMainBean.setPathLogo(pathLogo);
			reportWorksheetMainBean.setJobDetails(worksheet.getJobDetails());
			// end Footer
		}
	}
	
	private List<ReportTechnicianGroupBean> getTechnicianGroup(Worksheet worksheet) {
		List<ReportTechnicianGroupBean> reportTechnicianGroupBeanList = new ArrayList<ReportTechnicianGroupBean>();
		if(null != worksheet){
			List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = worksheet.getHistoryTechnicianGroupWorks();
			if(null != historyTechnicianGroupWorkList && historyTechnicianGroupWorkList.size() > 0){
				TechnicianGroup tech = historyTechnicianGroupWorkList.get(historyTechnicianGroupWorkList.size()-1).getTechnicianGroup();
				if(null != tech){
					List<Personnel> personnelList= tech.getPersonnels();
					if(null != personnelList && personnelList.size() > 0){
						int no = 1;
						for(Personnel personnel:personnelList){
							ReportTechnicianGroupBean bean = new ReportTechnicianGroupBean();
							bean.setNo(""+(no++));
							bean.setPersonnelCode(personnel.getPersonnelCode());
							bean.setPersonnelName(String.format("%1s%2s %3s",personnel.getPrefix(),personnel.getFirstName(),personnel.getLastName()));
							reportTechnicianGroupBeanList.add(bean);
						}
					}
				}
			}
		}
		return reportTechnicianGroupBeanList;
	}

	private List<ReportMaterialsUsedBean> getMaterialsUsed(Worksheet worksheet) {
		List<ReportMaterialsUsedBean> reportMaterialsUsedBeanList = new ArrayList<ReportMaterialsUsedBean>();
		WorkSheetListController workSheetListController = new WorkSheetListController();
		workSheetListController.setMessageSource(messageSource);
		workSheetListController.setEquipmentProductItemService(equipmentProductItemService);
		workSheetListController.setProductService(productService);
		WorksheetBean worksheetBean = workSheetListController.loadWorksheetBean(worksheet);
		if(null != worksheetBean){
		List<ProductItemBean> productItemBeanList = worksheetBean.getProductItemList();
			if(null != productItemBeanList){
				int i = 1;
				for(ProductItemBean productItemBean:productItemBeanList){
					if("E".equals(productItemBean.getType())){
						List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
						if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
							for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
								ReportMaterialsUsedBean bean = new ReportMaterialsUsedBean();
								bean.setNo(""+(i++));
								bean.setCode(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductCode());
								bean.setList(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getProductName());
								bean.setSn(productItemWorksheetBean.getEquipmentProductItemBean().getSerialNo());
								bean.setAmount(""+productItemWorksheetBean.getQuantity());
								bean.setUnit(productItemWorksheetBean.getEquipmentProductItemBean().getEquipmentProductBean().getUnit().getUnitName());
								bean.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()));
								bean.setSum(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()*productItemWorksheetBean.getQuantity()));
								String note = "";
								if(productItemWorksheetBean.isFree()){
									note = "ฟรี";
								}else if(productItemWorksheetBean.isLend()){
									note = "ยืม";
								}
								bean.setNote(note);
								
								reportMaterialsUsedBeanList.add(bean);
							}
							
						}
					}else if("S".equals(productItemBean.getType())){
						ReportMaterialsUsedBean bean = new ReportMaterialsUsedBean();
						bean.setNo(""+(i++));
						bean.setCode(productItemBean.getServiceProductBean().getProductCode());
						bean.setList(productItemBean.getServiceProductBean().getProductName());
						bean.setSn("");
						bean.setAmount(""+productItemBean.getQuantity());
						bean.setUnit(productItemBean.getServiceProductBean().getUnit().getUnitName());
						bean.setPrice(new DecimalFormat("#,##0.00").format(productItemBean.getPrice()));
						bean.setSum(new DecimalFormat("#,##0.00").format(productItemBean.getPrice()*productItemBean.getQuantity()));
						bean.setNote("");
						
						reportMaterialsUsedBeanList.add(bean);
					}else if("I".equals(productItemBean.getType())){
						List<ProductItemWorksheetBean> productItemWorksheetBeanList = productItemBean.getProductItemWorksheetBeanList();
						if(null != productItemWorksheetBeanList && productItemWorksheetBeanList.size() > 0){
							for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeanList){
								ReportMaterialsUsedBean bean = new ReportMaterialsUsedBean();
								bean.setNo(""+(i++));
								bean.setCode(productItemWorksheetBean.getInternetProductBeanItem().getInternetProductBean().getProductCode());
								bean.setList(productItemWorksheetBean.getInternetProductBeanItem().getInternetProductBean().getProductName());
								bean.setSn("");
								bean.setAmount(""+productItemWorksheetBean.getQuantity());
								bean.setUnit(productItemWorksheetBean.getInternetProductBeanItem().getInternetProductBean().getUnit().getUnitName());
								bean.setPrice(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()));
								bean.setSum(new DecimalFormat("#,##0.00").format(productItemWorksheetBean.getPrice()*productItemWorksheetBean.getQuantity()));
								bean.setNote("");
								
								reportMaterialsUsedBeanList.add(bean);
							}
							
						}
					}
				}
			}
		}
		return reportMaterialsUsedBeanList;
	}

	public AddressBean setAddressBean(Address address){
		AddressBean addressBean = new AddressBean();
		addressBean.setMessageSource(messageSource);
		addressBean.setId(address.getId());
		addressBean.setDetail(address.getDetail());
		addressBean.setNo(address.getNo());
		addressBean.setAlley(address.getAlley());
		addressBean.setRoad(address.getRoad());
		addressBean.setRoom(address.getRoom());
		addressBean.setFloor(address.getFloor());
		addressBean.setBuilding(address.getBuilding());
		addressBean.setSection(address.getSection());
		addressBean.setPostcode(address.getPostcode());
		addressBean.setAddressType(address.getAddressType());
		addressBean.setVillage(address.getVillage());
		addressBean.setNearbyPlaces(address.getNearbyPlaces());
		addressBean.setOverrideAddressId(address.getOverrideAddressId());
		addressBean.setOverrideAddressType(address.getOverrideAddressType());
		// user bean
		// province
		if (address.getProvinceModel() != null) {
			ProvinceBean provinceBean = new ProvinceBean();
			provinceBean.setId(address.getProvinceModel().getId());
			provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
			addressBean.setProvinceBean(provinceBean);
		}

		if (address.getAmphur() != null) {
			// amphur
			AmphurBean amphurBean = new AmphurBean();
			amphurBean.setId(address.getAmphur().getId());
			amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
			amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
			addressBean.setAmphurBean(amphurBean);
		}

		if (address.getDistrictModel() != null) {
			// district
			DistrictBean districtBean = new DistrictBean();
			districtBean.setId(address.getDistrictModel().getId());
			districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
			addressBean.setDistrictBean(districtBean);
		}

		addressBean.collectAddressForReport();
		
		return addressBean;
	}

	public List<ReportBilUnpaidBean> getInvoiceOverBill(Worksheet worksheet){
		List<ReportBilUnpaidBean> reportBilUnpaidBeanList = new ArrayList<ReportBilUnpaidBean>();
		List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();
		  
		  InvoiceController invController = new InvoiceController();
		  invController.setMessageSource(messageSource);
		  
		  for(Invoice invoice : worksheet.getServiceApplication().getInvoices()){
			  if(invoice.getCreateDate() != null) {
				  if(invoice.getStatus().equals("O")){
					  invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
				  }
			  }						  
		  }
		  
		  if(null != invoiceOverBill && invoiceOverBill.size() > 0){
			  float sumAmount = 0f;
			  for(InvoiceDocumentBean bean:invoiceOverBill){
				  ReportBilUnpaidBean reportBilUnpaidBean = new ReportBilUnpaidBean();
				  String invoiceDescription = "";
				//รอบบิลการใช้งาน
					if(bean.getInvoiceType().equals("O")){
						SimpleDateFormat formateDateServiceReound = new SimpleDateFormat(
								messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
								new Locale("TH", "th"));
						Date date = bean.getCreateDate();
						Date startUseDate = date;
						if(null != date){
							Calendar c = Calendar.getInstance(new Locale("EN", "en"));
							c.setTime(date);
							c.add(Calendar.DATE, -30);
							Date endUseDate = c.getTime();
							
							if(!StringUtils.isEmpty(startUseDate) && !StringUtils.isEmpty(endUseDate)){
								invoiceDescription = String.format("ค่าบริการรายเดือน ( %1s – %2s )",formateDateServiceReound.format(startUseDate),
									formateDateServiceReound.format(endUseDate));
							}
						}
					}else if(bean.getInvoiceType().equals("R")){
						invoiceDescription = bean.getWorksheet().getWorkSheetTypeText();
					}else if(bean.getInvoiceType().equals("S")){
						invoiceDescription = "ติดตั้งอุปกรณ์ใหม่";
					}
					
				  sumAmount += bean.getAmount();
				  reportBilUnpaidBean.setInvoiceNo(bean.getInvoiceCode());
				  reportBilUnpaidBean.setInvoiceDescription(invoiceDescription);
				  reportBilUnpaidBean.setPayment(new DecimalFormat("#,##0.00").format(bean.getAmount()));
				  reportBilUnpaidBean.setStatus("ค้างชำระ");
				  reportBilUnpaidBean.setSumPayment(new DecimalFormat("#,##0.00").format(sumAmount)+" บาท");
				  
				  reportBilUnpaidBeanList.add(reportBilUnpaidBean);
				  
			  }
		  }
		  
		  return reportBilUnpaidBeanList;
	}
	
	public AddPointWorksheetBean loadPointAll( Long serviceApplicationId) {
		logger.info("[method : loadConnect][Type : Controller]");

		AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
		
			try {
				if (null != serviceApplicationId) {	

					List<ProductItem> productItems = productItemService
							.getProductItemByServiceApplicationId(serviceApplicationId);
					int digitalPoint = 0;
					int analogPoint = 0;
					if (null != productItems && productItems.size() > 0) {
						for (ProductItem productItem : productItems) {

							Worksheet worksheet = productItem.getWorkSheet();
							ServiceProduct serviceProduct = productItem.getServiceProduct();
							// ใบงานติดตั้ง
							if (null != worksheet) {
								WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
//								WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
//								WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
								if (null != worksheetSetup && null != serviceProduct) {
									String productCode = serviceProduct.getProductCode();
									if ("00002".equals(productCode)) {
										digitalPoint += productItem.getQuantity();
									}
									if ("00003".equals(productCode)) {
										analogPoint += productItem.getQuantity();
									}
								}
							}
						}
					}

					// ใบงานเสริมจุด
					List<Worksheet> worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(
							messageSource.getMessage("worksheet.type.cable.addpoint", null,
									LocaleContextHolder.getLocale()),
							serviceApplicationId);

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetAddPoint worksheetAddPoint = worksheet.getWorksheetAddPoint();
							if (null != worksheetAddPoint && worksheetAddPoint.isActive()) {
								digitalPoint += worksheetAddPoint.getDigitalPoint();
								analogPoint += worksheetAddPoint.getAnalogPoint();
							}
						}
					}

					// ใบงานลดจุด
					worksheets = workSheetService.searchByWorksheetCodeAndserviceApplicationId(messageSource
							.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()),
							serviceApplicationId);

					if (null != worksheets && worksheets.size() > 0) {
						for (Worksheet worksheet : worksheets) {
							WorksheetReducePoint worksheetReducePoint = worksheet.getWorksheetReducePoint();
							if (null != worksheetReducePoint && worksheetReducePoint.isActive()) {
								digitalPoint -= worksheetReducePoint.getDigitalPoint();
								analogPoint -= worksheetReducePoint.getAnalogPoint();
							}
						}
					}

					addPointWorksheetBean.setDigitalPoint(digitalPoint);
					addPointWorksheetBean.setAnalogPoint(analogPoint);

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		return addPointWorksheetBean;
	}
	
	// ----------------------------------- none request method
	// ------------------------------------------//

	// load worksheet
	public WorksheetBean loadWorksheetBean(Worksheet worksheet) {
		String workSheetType = worksheet.getWorkSheetType();
		WorksheetBean worksheetBean = null;
		AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
		assignWorksheetController.setMessageSource(messageSource);
		worksheetBean = assignWorksheetController.populateEntityToDto(worksheet);
		// for cable
		if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
			addPointWorksheetBean.setId(worksheet.getWorksheetAddPoint().getId());
			addPointWorksheetBean.setAnalogPoint(worksheet.getWorksheetAddPoint().getAnalogPoint());
			addPointWorksheetBean.setDigitalPoint(worksheet.getWorksheetAddPoint().getDigitalPoint());
			addPointWorksheetBean.setAddPointPrice(worksheet.getWorksheetAddPoint().getAddPointPrice());
			addPointWorksheetBean.setMonthlyFree(worksheet.getWorksheetAddPoint().getMonthlyFree());
			worksheetBean.setAddPointWorksheetBean(addPointWorksheetBean);
		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()))) {
			// connect bean
			ConnectWorksheetBean connectWorksheetBean = new ConnectWorksheetBean();
			connectWorksheetBean.setId(worksheet.getWorksheetConnect().getId());
			worksheetBean.setConnectWorksheetBean(connectWorksheetBean);

		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {

		} else if (workSheetType
				.equals(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()))) {
			// tune bean
			TuneWorksheetBean tuneWorksheetBean = new TuneWorksheetBean();
			tuneWorksheetBean.setId(worksheet.getWorksheetTune().getId());
			tuneWorksheetBean.setTuneType(worksheet.getWorksheetTune().getTuneType());
			worksheetBean.setTuneWorksheetBean(tuneWorksheetBean);

		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.repair", null, LocaleContextHolder.getLocale()))) {
			RepairConnectionWorksheetBean repairConnectionWorksheetBean = new RepairConnectionWorksheetBean();
			repairConnectionWorksheetBean.setId(worksheet.getWorksheetRepairConnection().getId());

			// load all product under service applicatio id
			ServiceApplication serviceApplication = new ServiceApplication();
			serviceApplication.setId(worksheet.getServiceApplication().getId());
			List<ProductItemWorksheet> productItemWorksheets = equipmentProductItemService
					.loadEquipmentProductItemHasSNAllStatus(serviceApplication);

			WorkSheetAddController wsa = new WorkSheetAddController();
			wsa.setMessageSource(messageSource);

			// check with current status
			String statusCurrentHistory = "";
			if(null != worksheet.getHistoryTechnicianGroupWorks() && worksheet.getHistoryTechnicianGroupWorks().size() > 0){
				statusCurrentHistory = worksheet.getHistoryTechnicianGroupWorks()
					.get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getStatusHistory();
			}

			List<RepairMatchItemBean> repairMatchItemBeanList = new ArrayList<RepairMatchItemBean>();
			for (ProductItemWorksheet productItemWorksheet : productItemWorksheets) {
				RepairMatchItemBean repairMatchItemBean = new RepairMatchItemBean();
				if (productItemWorksheet.getParent() == null) {
					repairMatchItemBean.setEquipmentProductItemOld(
							wsa.populateProductItemIgnoreDeleted(productItemWorksheet.getEquipmentProductItem()));
					repairMatchItemBeanList.add(repairMatchItemBean);
				}
			}

			for (int i = 0; i < repairMatchItemBeanList.size(); i++) {
				for (ProductItemWorksheet productItemWorksheet : productItemWorksheets) {
					if (productItemWorksheet.getParent() != null) {
						if (productItemWorksheet.getParent() == repairMatchItemBeanList.get(i)
								.getEquipmentProductItemOld().getId()) {
							repairMatchItemBeanList.get(i).setEquipmentProductItemNew(wsa
									.populateProductItemIgnoreDeleted(productItemWorksheet.getEquipmentProductItem()));
							repairMatchItemBeanList.get(i).setComment(productItemWorksheet.getComment());
						}
					}
				}
			}

			List<RepairMatchItemBean> repairMatchItemBeanListTemp = new ArrayList<RepairMatchItemBean>();
			if (!statusCurrentHistory.equals("R")) {
				for (HistoryRepair historyRepair : worksheet.getHistoryRepairList()) {
					RepairMatchItemBean repairMatchItemBean = new RepairMatchItemBean();
					repairMatchItemBean.setEquipmentProductItemNew(
							wsa.populateProductItemIgnoreDeleted(historyRepair.getEquipmentProductItem()));
					repairMatchItemBean.setComment(historyRepair.getComment());

					// load product old
					EquipmentProductItem equipmentProductItemOld = productService
							.findEquipmentProductItemById(historyRepair.getParent());
					repairMatchItemBean
							.setEquipmentProductItemOld(wsa.populateProductItemIgnoreDeleted(equipmentProductItemOld));
					// add temp
					repairMatchItemBeanListTemp.add(repairMatchItemBean);
				}

				repairMatchItemBeanList = repairMatchItemBeanListTemp;
			}

			repairConnectionWorksheetBean.setRepairMatchItemBeanList(repairMatchItemBeanList);
			worksheetBean.setRepairConnectionWorksheetBean(repairConnectionWorksheetBean);

		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.addsettop", null, LocaleContextHolder.getLocale()))) {
			AddSetTopBoxWorksheetBean addSetTopBoxWorksheetBean = new AddSetTopBoxWorksheetBean();
			addSetTopBoxWorksheetBean.setId(worksheet.getWorksheetAddSetTopBox().getId());
			worksheetBean.setAddSetTopBoxWorksheetBean(addSetTopBoxWorksheetBean);
		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			MovePointWorksheetBean movePointWorksheetBean = new MovePointWorksheetBean();
			movePointWorksheetBean.setId(worksheet.getWorksheetMovePoint().getId());
			movePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetMovePoint().getAnalogPoint());
			movePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetMovePoint().getDigitalPoint());
			movePointWorksheetBean.setMovePointPrice(worksheet.getWorksheetMovePoint().getMovePointPrice());
			worksheetBean.setMovePointWorksheetBean(movePointWorksheetBean);
		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()))) {
			ReducePointWorksheetBean reducePointWorksheetBean = new ReducePointWorksheetBean();
			reducePointWorksheetBean.setId(worksheet.getWorksheetReducePoint().getId());
			reducePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetReducePoint().getAnalogPoint());
			reducePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetReducePoint().getDigitalPoint());
			reducePointWorksheetBean.setMonthlyFree(worksheet.getWorksheetReducePoint().getMonthlyFree());
			worksheetBean.setReducePointWorksheetBean(reducePointWorksheetBean);
		} else if (workSheetType
				.equals(messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()))) {
			// cut bean
			SimpleDateFormat formatDate3US = new SimpleDateFormat("dd MMMM yyyy", new Locale("TH", "th"));

			CutWorksheetBean cutWorksheetBean = new CutWorksheetBean();
			cutWorksheetBean.setId(worksheet.getWorksheetCut().getId());
			cutWorksheetBean.setReporter(worksheet.getWorksheetCut().getReporter());
			cutWorksheetBean.setMobile(worksheet.getWorksheetCut().getMobile());
			cutWorksheetBean.setCutWorkType(worksheet.getWorksheetCut().getCutWorkType());
			cutWorksheetBean.setReturnEquipment(worksheet.getWorksheetCut().isReturnEquipment());
			cutWorksheetBean.setSubmitCA(worksheet.getWorksheetCut().isSubmitCA());
			cutWorksheetBean.setCancelDate(null == worksheet.getWorksheetCut().getCancelDate() ? ""
					: formatDate3US.format(worksheet.getWorksheetCut().getCancelDate()));
			cutWorksheetBean.setEndPackageDate(null == worksheet.getWorksheetCut().getEndPackageDate() ? ""
					: formatDate3US.format(worksheet.getWorksheetCut().getEndPackageDate()));
			cutWorksheetBean.setSpecialDiscount(worksheet.getWorksheetCut().getSpecialDiscount());
			worksheetBean.setCutWorksheetBean(cutWorksheetBean);

		} else if (workSheetType
				.equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			MoveWorksheetBean moveWorksheetBean = new MoveWorksheetBean();
			moveWorksheetBean.setId(worksheet.getWorksheetMove().getId());
			moveWorksheetBean.setAnalogPoint(worksheet.getWorksheetMove().getAnalogPoint());
			moveWorksheetBean.setDigitalPoint(worksheet.getWorksheetMove().getDigitalPoint());
			moveWorksheetBean.setMoveCablePrice(worksheet.getWorksheetMove().getMoveCablePrice());
			List<Address> addressList = worksheet.getWorksheetMove().getAddresss();
			if (null != addressList && addressList.size() > 0) {
				for (Address address : addressList) {
					AddressBean addressBean = new AddressBean();
					addressBean.setMessageSource(messageSource);
					addressBean.setId(address.getId());
					addressBean.setDetail(address.getDetail());
					addressBean.setNo(address.getNo());
					addressBean.setAlley(address.getAlley());
					addressBean.setRoad(address.getRoad());
					addressBean.setRoom(address.getRoom());
					addressBean.setFloor(address.getFloor());
					addressBean.setBuilding(address.getBuilding());
					addressBean.setSection(address.getSection());
					addressBean.setPostcode(address.getPostcode());
					addressBean.setAddressType(address.getAddressType());
					addressBean.setVillage(address.getVillage());
					addressBean.setNearbyPlaces(address.getNearbyPlaces());
					addressBean.setOverrideAddressId(address.getOverrideAddressId());

					// province
					if (address.getProvinceModel() != null) {
						ProvinceBean provinceBean = new ProvinceBean();
						provinceBean.setId(address.getProvinceModel().getId());
						provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
						addressBean.setProvinceBean(provinceBean);
					}

					if (address.getAmphur() != null) {
						// amphur
						AmphurBean amphurBean = new AmphurBean();
						amphurBean.setId(address.getAmphur().getId());
						amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
						amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
						addressBean.setAmphurBean(amphurBean);
					}

					if (address.getDistrictModel() != null) {
						// district
						DistrictBean districtBean = new DistrictBean();
						districtBean.setId(address.getDistrictModel().getId());
						districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
						addressBean.setDistrictBean(districtBean);
					}

					addressBean.collectAddress();
					// zone
					ZoneBean zoneBean = new ZoneBean();
					if (address.getZone() != null) {
						zoneBean.setId(address.getZone().getId());
						zoneBean.setZoneName(address.getZone().getZoneName());
						zoneBean.setZoneDetail(address.getZone().getZoneDetail());
					}
					addressBean.setZoneBean(zoneBean);
					moveWorksheetBean.setAddress(addressBean);
				}
			}
			worksheetBean.setMoveWorksheetBean(moveWorksheetBean);
		} else if (workSheetType.equals(
				messageSource.getMessage("worksheet.type.cable.borrow", null, LocaleContextHolder.getLocale()))) {
			BorrowWorksheetBean borrowWorksheetBean = new BorrowWorksheetBean();
			borrowWorksheetBean.setId(worksheet.getWorksheetBorrow().getId());
			worksheetBean.setBorrowWorksheetBean(borrowWorksheetBean);
		} else if (workSheetType.equals(messageSource.getMessage("worksheet.type.internet.analyzeproblems", 
				null, LocaleContextHolder.getLocale()))) {
			AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean = new AnalyzeProblemsWorksheetBean();
			analyzeProblemsWorksheetBean.setMenuReportId(worksheet.getWorksheetAnalyzeProblems().getMenuReport().getId());
			analyzeProblemsWorksheetBean.setMenuReportName(worksheet.getWorksheetAnalyzeProblems().getMenuReport().getMenuReportName());
			worksheetBean.setAnalyzeProblemsWorksheetBean(analyzeProblemsWorksheetBean);
		}
		// for internet

		return worksheetBean;
	}
}