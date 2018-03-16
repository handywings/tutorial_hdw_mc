package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.DecimalFormat;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.CustomerAmountBean;
import com.hdw.mccable.dto.DashboardBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServicePackageType;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.DashboardService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.DateUtil;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController{
	
	final static Logger logger = Logger.getLogger(DashboardController.class);
	public static final String CONTROLLER_NAME = "dashboard/";
	public static final String PAGE_INVOICE = "invoice";
	public static final String PAGE_STOCK = "stock";
	public static final String PAGE_WORKSHEET = "worksheet";
	public static final String PAGE_CUSTOMER = "customer";
	public static final String PAGE_SERVICE = "service";
	public static final String PAGE_INCOME = "income";
	
	@Autowired(required = true)
	@Qualifier(value = "dashboardService")
	private DashboardService dashboardService;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required=true)
	@Qualifier(value="stockService")
	private StockService stockService;
	
	@Autowired(required=true)
	@Qualifier(value="zoneService")
	private ZoneService zoneService;
	
	@Autowired(required=true)
	@Qualifier(value="servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
		
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initInvoice(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initLogin][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			Map<String,Object> criteria = new HashMap<String, Object>();
			List<CashierBean> cashierIsTrue = new ArrayList<CashierBean>();
			List<Personnel> cashier = new ArrayList<Personnel>();
			try {
				String[] months = DateUtil.createMonth();
				int[] years = DateUtil.createYearThreePrevious();
				
				modelAndView.addObject("months", months);
				modelAndView.addObject("years", years);
				
				Calendar c = Calendar.getInstance(new Locale("TH", "th"));
				Map<String, Object> map = new HashMap<String, Object>();
				try{
					DashboardBean dashboardBean = (DashboardBean) session.getAttribute("dashboardBean");
					if (dashboardBean == null) {
						dashboardBean = new DashboardBean();
						dashboardBean.setMonthTabBill(c.get(Calendar.MONTH) + 1);
						dashboardBean.setYearTabBill(c.getWeekYear());					
					} else {
						if (dashboardBean.getMonthTabBill() == 0) {
							dashboardBean = new DashboardBean();
							dashboardBean.setMonthTabBill(c.get(Calendar.MONTH) + 1);
							dashboardBean.setYearTabBill(c.getWeekYear());	
						}
					}
					
					criteria.put("year", dashboardBean.getYearTabBill() - 543);
					criteria.put("month", dashboardBean.getMonthTabBill());
					List<Invoice> invoice = dashboardService.findAllInvoiceByDate(criteria);
					if (invoice != null) {
						int countKeep = 0;
						int countNotKeep = 0;
						int badDebt = 0;
						int countMonth = 0;
						int countHalfYear = 0;
						int countYear = 0;
						int countCut = 0;
						int countTime = 0;
						int other = 0;
						for (Invoice obj : invoice) {
							// count amount bill from type
							if ("S".equals(obj.getStatus().trim().toString())) {
								countKeep++;
							} else if (!"S".equals(obj.getStatus().trim().toString()) && !"I".equals(obj.getServiceApplication().getStatus().trim().toString())) {
								countNotKeep++;
							} else if (!"S".equals(obj.getStatus().trim().toString()) && "I".equals(obj.getServiceApplication().getStatus().trim().toString())) {
								badDebt++;
							}

							// count amount bill from Application type
							if ("0001".equals(obj.getServiceApplication().getServiceApplicationType().getServiceApplicationTypeCode().trim().toString())) {
								countMonth++;
							} else if ("0002".equals(obj.getServiceApplication().getServiceApplicationType().getServiceApplicationTypeCode().trim().toString())) {
								countHalfYear++;
							} else if ("0003".equals(obj.getServiceApplication().getServiceApplicationType().getServiceApplicationTypeCode().trim().toString())) {
								countYear++;
							} else if ("0004".equals(obj.getServiceApplication().getServiceApplicationType().getServiceApplicationTypeCode().trim().toString())) {
								countCut++;
							} else if ("0005".equals(obj.getServiceApplication().getServiceApplicationType().getServiceApplicationTypeCode().trim().toString())) {
								countTime++;
							} else {
								other++;
							}
							
						}
						cashier = personnelService.findPersonnelCashier(true);
						if (cashier != null) {
							for (Personnel personnel : cashier) {
								Long personnelId = personnel.getId();
								criteria.put("personnelId", personnelId);
								CashierBean cashierBean = dashboardService.countBillAndAmountByDate(criteria);							
								cashierBean.setId(personnelId);
								cashierBean.setFullName(personnel.getPrefix()+""+personnel.getFirstName()+" "+personnel.getLastName());
								cashierIsTrue.add(cashierBean);
							}						
						}
						map.put("countKeep", countKeep);
						map.put("countNotKeep", countNotKeep);
						map.put("badDebt", badDebt);
						map.put("countMonth", countMonth);
						map.put("countHalfYear", countHalfYear);
						map.put("countYear", countYear);
						map.put("countCut", countCut);
						map.put("countTime", countTime);
						map.put("other", other);
					}
					modelAndView.addObject("billDashboard", map);
					modelAndView.addObject("cashierIsTrue", cashierIsTrue);
					modelAndView.addObject("dashboardBean", dashboardBean);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME + PAGE_INVOICE);
		return modelAndView;
	}
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("search") DashboardBean dashboardBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : search][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSession(dashboardBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/dashboard");
		return modelAndView;
	}
	
	@RequestMapping(value = "stock", method = RequestMethod.GET)
	public ModelAndView initStock(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initLogin][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		List<StockBean> stockBeans = new ArrayList<StockBean>();
		StockBean stockBeanFinal = new StockBean();
		int allProducts = 0;		// รายการสินค้าทั้งหมด
		int lowInStock = 0;			// รายการสินค้าใกล้หมด
		int outOfStock = 0;			// รายการสินค้าหมด
		int insuranceExpire = 0; 	// รายการสินค้าหมดประกัน
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				List<Stock> stocks = stockService.findAll();
				if(null != stocks && !stocks.isEmpty()){
					for(Stock stock:stocks){
						StockBean stockBean = populateEntityToDto(stock);
						allProducts += stockBean.getAllProducts();
						lowInStock += stockBean.getLowInStock();
						outOfStock += stockBean.getOutOfStock();
						insuranceExpire += stockBean.getInsuranceExpire();
						stockBeans.add(stockBean);
					}
					stockBeanFinal.setAllProducts(allProducts);
					stockBeanFinal.setLowInStock(lowInStock);
					stockBeanFinal.setOutOfStock(outOfStock);
					stockBeanFinal.setInsuranceExpire(insuranceExpire);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.addObject("stocks", stockBeans);
		modelAndView.addObject("stock", stockBeanFinal);
		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME+PAGE_STOCK);
		return modelAndView;
	}	
	
	@RequestMapping(value = "worksheet", method = RequestMethod.GET)
	public ModelAndView initWorksheet(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : worksheet][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Map<String, Object> criteria = new HashMap<String, Object>();
		Map<String, Object> WorkReport = new HashMap<String, Object>();
		int success = 0;		// สำเร็จ
		int inProcess = 0;			// กำลังดำเนินการ
		int fail = 0;			// ไม่สำเร็จ
		
		int worksheetAddPoint = 0,worksheetAddPoint1 = 0; // เสริมจุดบริการ
		int worksheetConnect = 0,worksheetConnect1 = 0; // ซ่อมสัญญาณ
		int worksheetAddSetTopBox = 0,worksheetAddSetTopBox1 = 0; // ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี
		int worksheetCut = 0,worksheetCut1 = 0; // ตัดสาย
		int worksheetBorrow = 0,worksheetBorrow1 = 0; // แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี
		int worksheetMove = 0,worksheetMove1 = 0; // ย้ายสาย
		int worksheetMovePoint = 0,worksheetMovePoint1 = 0; // ย้ายจุด
		int worksheetReducePoint = 0,worksheetReducePoint1 = 0; // ลดจุด
		int worksheetRepairConnection = 0,worksheetRepairConnection1 = 0; // ซ่อมสัญญาณ
		int worksheetSetup = 0,worksheetSetup1 = 0; // ติดตั้ง
		int worksheetTune = 0,worksheetTune1 = 0; // การจูนทีวี
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				String[] months = DateUtil.createMonth();
				int[] years = DateUtil.createYearThreePrevious();
				
				modelAndView.addObject("months", months);
				modelAndView.addObject("years", years);
				
				Calendar c = Calendar.getInstance(new Locale("TH", "th"));
				DashboardBean dashboardBean = (DashboardBean) session.getAttribute("dashboardBean");
				if (dashboardBean == null) {
					dashboardBean = new DashboardBean();
					dashboardBean.setMonthTabWorksheet(c.get(Calendar.MONTH) + 1);
					dashboardBean.setYearTabWorksheet(c.getWeekYear());					
				} else {
					if (dashboardBean.getMonthTabWorksheet() == 0) {
						dashboardBean = new DashboardBean();
						dashboardBean.setMonthTabWorksheet(c.get(Calendar.MONTH) + 1);
						dashboardBean.setYearTabWorksheet(c.getWeekYear());			
					}
				}
					
				criteria.put("year", dashboardBean.getYearTabWorksheet() - 543);
				criteria.put("month", dashboardBean.getMonthTabWorksheet());
				List<Worksheet> worksheets = dashboardService.findAllWorksheet(criteria);
				if (worksheets != null) {
					for (Worksheet worksheet : worksheets) {
						if ("S".equals(worksheet.getStatus().trim().toString())) {
							success++;
						} else if ("R".equals(worksheet.getStatus().trim().toString())) {
							inProcess++;
						} else if ("C".equals(worksheet.getStatus().trim().toString())) {
							fail++;
						}
						
						worksheetAddPoint = worksheet.getWorksheetAddPoint() == null ? 0 + worksheetAddPoint : 1 + worksheetAddPoint;
						worksheetConnect = worksheet.getWorksheetConnect() == null ? 0 + worksheetConnect : 1 + worksheetConnect;
						worksheetAddSetTopBox = worksheet.getWorksheetAddSetTopBox() == null ? 0 + worksheetAddSetTopBox : 1 + worksheetAddSetTopBox;
						worksheetCut = worksheet.getWorksheetCut() == null ? 0 + worksheetCut : 1 + worksheetCut;
						worksheetBorrow = worksheet.getWorksheetBorrow() == null ? 0 + worksheetBorrow : 1 + worksheetBorrow;
						worksheetMove = worksheet.getWorksheetMove() == null ? 0 + worksheetMove : 1 + worksheetMove;
						worksheetMovePoint = worksheet.getWorksheetMovePoint() == null ? 0 + worksheetMovePoint : 1 + worksheetMovePoint;
						worksheetReducePoint = worksheet.getWorksheetReducePoint() == null ? 0 + worksheetReducePoint : 1 + worksheetReducePoint;
						worksheetRepairConnection = worksheet.getWorksheetRepairConnection() == null ? 0 + worksheetRepairConnection : 1 + worksheetRepairConnection;
						worksheetSetup = worksheet.getWorksheetSetup() == null ? 0 + worksheetSetup : 1 + worksheetSetup;
						worksheetTune = worksheet.getWorksheetTune() == null ? 0 + worksheetTune : 1 + worksheetTune;
						
						worksheetAddPoint1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetAddPoint() != null
								? 1 + worksheetAddPoint1 : 0 + worksheetAddPoint1;
						worksheetConnect1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetConnect() != null
								? 1 + worksheetConnect1 : 0 + worksheetConnect1;
						worksheetAddSetTopBox1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetAddSetTopBox() != null
								? 1 + worksheetAddSetTopBox1 : 0 + worksheetAddSetTopBox1;
						worksheetCut1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetCut() != null
								? 1 + worksheetCut1 : 0 + worksheetCut1;
						worksheetBorrow1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetBorrow() != null
								? 1 + worksheetBorrow1 : 0 + worksheetBorrow1;
						worksheetMove1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetMove() != null
								? 1 + worksheetMove1 : 0 + worksheetMove1;
						worksheetMovePoint1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetMovePoint() != null 
								? 1 + worksheetMovePoint1 : 0 + worksheetMovePoint1;
						worksheetReducePoint1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetMovePoint() != null 
								? 1 + worksheetReducePoint1 : 0 + worksheetReducePoint1;
						worksheetRepairConnection1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetRepairConnection() != null
								? 1 + worksheetRepairConnection1 : 0 + worksheetRepairConnection1;
						worksheetSetup1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetSetup()!= null 
								? 1 + worksheetSetup1 : 0 + worksheetSetup1;
						worksheetTune1 = worksheet.getHistoryTechnicianGroupWorks().size() > 1 && worksheet.getWorksheetAddPoint() != null
								? 1 + worksheetTune1 : 0 + worksheetTune1;
						
					}
					WorkReport.put("worksheetAddPoint", worksheetAddPoint);
					WorkReport.put("worksheetConnect", worksheetConnect);
					WorkReport.put("worksheetAddSetTopBox", worksheetAddSetTopBox);
					WorkReport.put("worksheetCut", worksheetCut);
					WorkReport.put("worksheetBorrow", worksheetBorrow);
					WorkReport.put("worksheetMove", worksheetMove);
					WorkReport.put("worksheetMovePoint", worksheetMovePoint);
					WorkReport.put("worksheetReducePoint", worksheetReducePoint);
					WorkReport.put("worksheetRepairConnection", worksheetRepairConnection);
					WorkReport.put("worksheetSetup", worksheetSetup);
					WorkReport.put("worksheetTune", worksheetTune);
					
					WorkReport.put("worksheetAddPoint1", worksheetAddPoint1);
					WorkReport.put("worksheetConnect1", worksheetConnect1);
					WorkReport.put("worksheetAddSetTopBox1", worksheetAddSetTopBox1);
					WorkReport.put("worksheetCut1", worksheetCut1);
					WorkReport.put("worksheetBorrow1", worksheetBorrow1);
					WorkReport.put("worksheetMove1", worksheetMove1);
					WorkReport.put("worksheetMovePoint1", worksheetMovePoint1);
					WorkReport.put("worksheetReducePoint1", worksheetReducePoint1);
					WorkReport.put("worksheetRepairConnection1", worksheetRepairConnection1);
					WorkReport.put("worksheetSetup1", worksheetSetup1);
					WorkReport.put("worksheetTune1", worksheetTune1);
					
					WorkReport.put("success", success);
					WorkReport.put("inProcess", inProcess);
					WorkReport.put("fail", fail);
				}
				modelAndView.addObject("WorkReport", WorkReport);
				modelAndView.addObject("dashboardBean", dashboardBean);
				logger.info("month = "+dashboardBean.getMonthTabWorksheet());
				logger.info("year = "+dashboardBean.getYearTabWorksheet());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME+PAGE_WORKSHEET);
		return modelAndView;
	}
	
	@RequestMapping(value = "worksheet/search", method = RequestMethod.POST)
	public ModelAndView searchWorksheet(@ModelAttribute("worksheet/search") DashboardBean dashboardBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchWorksheet][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSession(dashboardBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/dashboard/worksheet");
		return modelAndView;
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.GET)
	public ModelAndView initCustomer(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initCustomer][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Map<String, Object> criteria = new HashMap<String, Object>();
		Map<String, Object> criteria2 = new HashMap<String, Object>();
		Map<String, Object> customerDashboard = new HashMap<String, Object>();
		List<CustomerAmountBean> customerAmountBeanList = new ArrayList<CustomerAmountBean>();
		List<CustomerAmountBean> customerAmountBeanList2 = new ArrayList<CustomerAmountBean>();
		int individual = 0; // บุคคลธรรมดา
		int corporate = 0;  // นิติบุคคล
		int homeMember = 0;  // สมาชิกบ้าน (ที่พักอาศัยส่วนตัว )
		int projectMember = 0;  // สมาชิกโครงการ (ราคาเหมาจ่าย)
		int memberVIP = 0;  // สมาชิก VIP (ไม่เสียค่าบริการ)
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				
				Calendar c = Calendar.getInstance(new Locale("TH", "th"));
				SimpleDateFormat formatDataTh = new SimpleDateFormat("MMMM", new Locale("TH", "th"));
				SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.US);
				int month_current = c.get(Calendar.MONTH);
				int year_current = c.getWeekYear();
				Date dateForMonth = mm.parse("01");
				for (int i = 0; i < 12; i++) {
					if (month_current < 0) {
						month_current = 11;
					}
					if (month_current == 11) {
						year_current -= 1;
					}
					c.setTime(dateForMonth);
					c.add(Calendar.MONTH, +(month_current));
					criteria2.put("month", month_current + 1);
					criteria2.put("year", year_current - 543);
					int count = dashboardService.countCustomer(criteria2);
					CustomerAmountBean customerAmountBean = new CustomerAmountBean();
					customerAmountBean.setTitle("เดือน"+formatDataTh.format(c.getTime())+" ปี"+year_current);
					customerAmountBean.setAmount(count);
					customerAmountBeanList.add(customerAmountBean);
					month_current -= 1;

				}
				List<Zone> zones = zoneService.findAll();
				for (Zone zone : zones) {
					CustomerAmountBean customerAmountBean2 = new CustomerAmountBean();
					criteria2.put("zone_id", zone.getId());
					int countZone = dashboardService.countCustomerFromZone(criteria2);
					customerAmountBean2.setAmount2(countZone);
					customerAmountBean2.setTitle2(zone.getZoneDetail());
					customerAmountBeanList2.add(customerAmountBean2);
				}
				List<Customer> customers = dashboardService.findAllCustomerByDate(criteria);
				for (Customer customer : customers) {
					// count style customer
					homeMember = "0001".equals(customer.getCustomerFeature().getCustomerFeatureCode().toString()) ? homeMember + 1 : homeMember + 0;
					projectMember = "0002".equals(customer.getCustomerFeature().getCustomerFeatureCode().toString()) ? projectMember + 1 : projectMember + 0;
					memberVIP = "0003".equals(customer.getCustomerFeature().getCustomerFeatureCode().toString()) ? memberVIP + 1 : memberVIP + 0;
					// count type customer
					individual = "I".equals(customer.getCustType().toString()) ? individual + 1 : individual + 0;
					corporate = "C".equals(customer.getCustType().toString()) ? corporate + 1 : corporate + 0;
				}
				customerDashboard.put("homeMember", homeMember);
				customerDashboard.put("projectMember", projectMember);
				customerDashboard.put("memberVIP", memberVIP);
				customerDashboard.put("individual", individual);
				customerDashboard.put("corporate", corporate);
				modelAndView.addObject("customerDashboard", customerDashboard);
				modelAndView.addObject("customerAmountBeanList", customerAmountBeanList);
				modelAndView.addObject("customerAmountBeanList2", customerAmountBeanList2);
				modelAndView.addObject("sizeCusByZone", customerAmountBeanList2.size());
				
				Collections.sort(customerAmountBeanList2, new Comparator<CustomerAmountBean>() {

					public int compare(final CustomerAmountBean o1, final CustomerAmountBean o2) {
						Integer a1 = o1.getAmount2();
						Integer a2 = o2.getAmount2();
						return a2.compareTo(a1);
					}
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+PAGE_CUSTOMER);
		return modelAndView;
	}
	
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public ModelAndView initProduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initLogin][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Map<String, Object> criteria = new HashMap<String, Object>();
		Map<String, Object> customerDashboard = new HashMap<String, Object>();
		int cable = 0;
		int cctv = 0;
		int internet = 0;
		int cableAndCctvAndInternet = 0;
		int cableAndInternet = 0;
		int cableAndCctv = 0;
		int cctvAndInternet = 0;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				String[] months = DateUtil.createMonth();
				int[] years = DateUtil.createYearThreePrevious();
				
				modelAndView.addObject("months", months);
				modelAndView.addObject("years", years);
				
				Calendar c = Calendar.getInstance(new Locale("TH", "th"));
				DashboardBean dashboardBean = (DashboardBean) session.getAttribute("dashboardBean");
				if (dashboardBean == null) {
					dashboardBean = new DashboardBean();
					dashboardBean.setMonthTabService(c.get(Calendar.MONTH) + 1);
					dashboardBean.setYearTabService(c.getWeekYear());					
				} else {
					if (dashboardBean.getMonthTabService() == 0) {
						dashboardBean = new DashboardBean();
						dashboardBean.setMonthTabService(c.get(Calendar.MONTH) + 1);
						dashboardBean.setYearTabService(c.getWeekYear());			
					}
				}
				criteria.put("year", dashboardBean.getYearTabService() - 543);
				criteria.put("month", dashboardBean.getMonthTabService());
				List<ServiceApplication> serviceApplications = dashboardService.findServiceAppByDate(criteria);
				for (ServiceApplication serviceApplication : serviceApplications) {
					if (null != serviceApplication.getServicePackage().getServicePackageType()) {
						cable = "00001".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cable + 1 : cable + 0;
						cctv = "00002".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cctv + 1 : cctv + 0;
						internet = "00003".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? internet + 1 : internet + 0;
						cableAndCctvAndInternet = "00004".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cableAndCctvAndInternet + 1 : cableAndCctvAndInternet + 0;
						cableAndInternet = "00005".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cableAndInternet + 1 : cableAndInternet + 0;
						cableAndCctv = "00006".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cableAndCctv + 1 : cableAndCctv + 0;
						cctvAndInternet = "00007".equals(serviceApplication.getServicePackage().getServicePackageType().getPackageTypeCode().toString()) && "A".equals(serviceApplication.getStatus()) ? cctvAndInternet + 1 : cctvAndInternet + 0;
					}	
				}
				customerDashboard.put("cable", cable);
				customerDashboard.put("cctv", cctv);
				customerDashboard.put("internet", internet);
				customerDashboard.put("cableAndCctvAndInternet", cableAndCctvAndInternet);
				customerDashboard.put("cableAndInternet", cableAndInternet);
				customerDashboard.put("cableAndCctv", cableAndCctv);
				customerDashboard.put("cctvAndInternet", cctvAndInternet);
				
				modelAndView.addObject("customerDashboard", customerDashboard);
				modelAndView.addObject("dashboardBean", dashboardBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME+PAGE_SERVICE);
		return modelAndView;
	}
	
	@RequestMapping(value = "service/search", method = RequestMethod.POST)
	public ModelAndView searchService(@ModelAttribute("service/search") DashboardBean dashboardBean, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchService][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSession(dashboardBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/dashboard/service");
		return modelAndView;
	}
	
	@RequestMapping(value = "income", method = RequestMethod.GET)
	public ModelAndView initIncome(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initLogin][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Map<String, Object> criteria = new HashMap<String, Object>();
		Map<String, Object> customerDashboard = new HashMap<String, Object>();
		List<CustomerAmountBean> customerAmountBeanList = new ArrayList<CustomerAmountBean>();
		int cable = 0;
		int cctv = 0;
		int internet = 0;
		int cableAndCctvAndInternet = 0;
		int cableAndInternet = 0;
		int cableAndCctv = 0;
		int cctvAndInternet = 0;
		int transferMoney = 0;
		int payByCounter = 0;
		int cashiers = 0;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				String[] months = DateUtil.createMonth();
				int[] years = DateUtil.createYearThreePrevious();
				
				modelAndView.addObject("months", months);
				modelAndView.addObject("years", years);
				
				Calendar c = Calendar.getInstance(new Locale("TH", "th"));
				DashboardBean dashboardBean = (DashboardBean) session.getAttribute("dashboardBean");
				if (dashboardBean == null) {
					dashboardBean = new DashboardBean();
					dashboardBean.setMonthTabIncome(c.get(Calendar.MONTH) + 1);
					dashboardBean.setYearTabIncome(c.getWeekYear());					
				} else {
					if (dashboardBean.getMonthTabIncome() == 0) {
						dashboardBean = new DashboardBean();
						dashboardBean.setMonthTabIncome(c.get(Calendar.MONTH) + 1);
						dashboardBean.setYearTabIncome(c.getWeekYear());			
					}
				}
				criteria.put("year", dashboardBean.getYearTabIncome() - 543);
				criteria.put("month", dashboardBean.getMonthTabIncome());
				List<Invoice> invoices = dashboardService.findAllInvoiceByDate(criteria);
				for (Invoice invoice : invoices) {
					if ("S".equals(invoice.getStatus().toString())) {
						
						if (null != invoice.getReceipt() && null != invoice.getReceipt().getPaymentType()) {
							transferMoney = "T".equals(invoice.getReceipt().getPaymentType().toString()) ? transferMoney + 1 : transferMoney + 0;
							payByCounter = "C".equals(invoice.getReceipt().getPaymentType().toString()) ? payByCounter + 1 : payByCounter + 0;
							cashiers = "P".equals(invoice.getReceipt().getPaymentType().toString()) ? cashiers + 1 : cashiers + 0;
						}
					} else if ("O".equals(invoice.getStatus().toString())) {
						if (null != invoice.getServiceApplication().getServicePackage().getServicePackageType()) {
							cable = "00001".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cable + 1 : cable + 0;
							cctv = "00002".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cctv + 1 : cctv + 0;
							internet = "00003".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? internet + 1 : internet + 0;
							cableAndCctvAndInternet = "00004".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cableAndCctvAndInternet + 1 : cableAndCctvAndInternet + 0;
							cableAndInternet = "00005".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cableAndInternet + 1 : cableAndInternet + 0;
							cableAndCctv = "00006".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cableAndCctv + 1 : cableAndCctv + 0;
							cctvAndInternet = "00007".equals(invoice.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeCode().toString()) ? cctvAndInternet + 1 : cctvAndInternet + 0;
							
						}
					}
				}
				List<ServicePackageType> servicePackageTypes = servicePackageTypeService.findAll();
				for (ServicePackageType servicePackageType : servicePackageTypes) {
					CustomerAmountBean customerAmountBean = new CustomerAmountBean();
					criteria.put("code", servicePackageType.getPackageTypeCode());
					int count = dashboardService.countIncome(criteria);
					customerAmountBean.setTitle(servicePackageType.getPackageTypeName());
					customerAmountBean.setAmount(count);
					customerAmountBeanList.add(customerAmountBean);
				}
				Collections.sort(customerAmountBeanList, new Comparator<CustomerAmountBean>() {

					public int compare(final CustomerAmountBean o1, final CustomerAmountBean o2) {
						Integer a1 = o1.getAmount();
						Integer a2 = o2.getAmount();
						return a2.compareTo(a1);
					}
					
				});
				
				customerDashboard.put("cable", cable);
				customerDashboard.put("cctv", cctv);
				customerDashboard.put("internet", internet);
				customerDashboard.put("cableAndCctvAndInternet", cableAndCctvAndInternet);
				customerDashboard.put("cableAndInternet", cableAndInternet);
				customerDashboard.put("cableAndCctv", cableAndCctv);
				customerDashboard.put("cctvAndInternet", cctvAndInternet);
				
				customerDashboard.put("transferMoney", transferMoney);
				customerDashboard.put("payByCounter", payByCounter);
				customerDashboard.put("cashiers", cashiers);
				
				modelAndView.addObject("customerDashboard", customerDashboard);
				modelAndView.addObject("dashboardBean", dashboardBean);
				modelAndView.addObject("customerAmountBeanList", customerAmountBeanList);
				modelAndView.addObject("sizeIncome", customerAmountBeanList.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// add session to bean
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		modelAndView.setViewName(CONTROLLER_NAME+PAGE_INCOME);
		return modelAndView;
	}
	
	@RequestMapping(value = "income/search", method = RequestMethod.POST)
	public ModelAndView searchIncome(@ModelAttribute("income/search") DashboardBean dashboardBean, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchIncome][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSession(dashboardBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/dashboard/income");
		return modelAndView;
	}
	
	// create search tab bill session
	public void generateSession(DashboardBean dashboardBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("dashboardBean", dashboardBean);
	}
	
	public StockBean populateEntityToDto(Stock stock) {

		StockBean stockBean = new StockBean();
		int allProducts = 0;		// รายการสินค้าทั้งหมด
		int lowInStock = 0;			// รายการสินค้าใกล้หมด
		int outOfStock = 0;			// รายการสินค้าหมด
		int insuranceExpire = 0; 	// รายการสินค้าหมดประกัน
		
		if(null != stock){
			stockBean.setId(stock.getId());
			stockBean.setStockCode(stock.getStockCode());
			stockBean.setStockName(stock.getStockName());
			stockBean.setStockDetail(stock.getStockDetail());
			
			CompanyController companyController = new CompanyController();
			CompanyBean companyBean = companyController.populateEntityToDto(stock.getCompany());
			stockBean.setCompany(companyBean);
			
			AddressBean addressBean = new AddressBean();
			Address address = stock.getAddress();
			addressBean.setDetail(null == address?"":address.getDetail());
			stockBean.setAddress(addressBean);
			
			List<EquipmentProduct> equipmentProducts = stock.getEquipmentProducts();
			if(null != equipmentProducts && !equipmentProducts.isEmpty()){
				for(EquipmentProduct equipmentProduct:equipmentProducts){
					List<EquipmentProductItem> equipmentProductItem = equipmentProduct.getEquipmentProductItems();
					if(null != equipmentProductItem && !equipmentProductItem.isEmpty()){
						int countItem = 0;
						int countguaranteeDate = 0;
						for(EquipmentProductItem eq:equipmentProductItem){
							if(!eq.isDeleted()){
								countItem++;
								if (eq.getGuaranteeDate() != null) {
									if (eq.getGuaranteeDate().compareTo(removeTime(new Date())) < 0) {
										countguaranteeDate++;
									}
								}
							}
						}
						
						if(countguaranteeDate > 0){
							insuranceExpire++;
						}
						
						if(equipmentProduct.isMinimum()){
							if(equipmentProduct.getMinimumNumber() <= countItem ){
								lowInStock++;
							}
						}
						
						if(countItem > 0){
							allProducts ++;
						}else{
							outOfStock++;
						}
					}
				}
			}

			stockBean.setAllProducts(allProducts);
			stockBean.setLowInStock(lowInStock);
			stockBean.setOutOfStock(outOfStock);
			stockBean.setInsuranceExpire(insuranceExpire);
		}
		
		return stockBean;
	}
	
	public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
