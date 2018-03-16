package com.hdw.mccable.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hdw.mccable.dao.FinancialDAO;
import com.hdw.mccable.dto.InvoiceSearchBean;
import com.hdw.mccable.dto.OrderBillSearchBean;
import com.hdw.mccable.dto.ReceiptSearchBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;
import com.hdw.mccable.utils.TextUtil;

public class FinancialDAOImpl implements FinancialDAO{
	private static final Logger logger = LoggerFactory.getLogger(FinancialDAOImpl.class);
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
///////////////////////// implement method ///////////////////////////
	@SuppressWarnings("unchecked")
	public List<Invoice> findAllInvoice() {
		logger.info("[method : findAllInvoice][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoiceList = (List<Invoice>) session.createQuery("from Invoice inv where inv.isDeleted = false ").list();
		return invoiceList;
	}

	public Long saveInvoice(Invoice invoice) throws Exception {
		logger.info("[method : saveInvoice][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		Object object = session.save(invoice);
		session.flush();
		return (Long) object;
	}

	public Long saveReceipt(Receipt receipt) throws Exception {
		logger.info("[method : saveReceipt][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		Object object = session.save(receipt);
		session.flush();
		return (Long) object;
	}

	public String genInVoiceCode() {
		logger.info("[method : genInVoiceCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('IV',LPAD((SELECT COUNT(id)+1 FROM invoice), 6, '0')) as invoiceCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("genInVoiceCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}

	public String genReceiptCode() {
		logger.info("[method : genReceiptCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('R',LPAD((SELECT COUNT(id)+1 FROM receipt), 6, '0')) as receiptCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("genReceiptCode : " + obj.toString());
			return obj.toString();
		}
		return null;
	}

	public void updateInvoice(Invoice invoice) throws Exception {
		logger.info("[method : updateInvoice][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(invoice);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public int getCountTotalOrderBill(OrderBillSearchBean orderBillSearchBean) {
		logger.info("[method : getCountTotalOrderBill][Type : DAO]");
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if(orderBillSearchBean != null){
			strQuery = strQuery.append("select count(sa.id) from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join worksheet ws on ws.serviceApplicationId = sa.id ");
			
			//key
			if (orderBillSearchBean.getKey() != null && (!orderBillSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where sa.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}
			//zone for append
			if(orderBillSearchBean.getZone() != null && (orderBillSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
			//worksheet 
			strQuery = strQuery.append(" and ws.workSheetType = 'C_S' and ws.status = 'S' ");
			
			//type monthly service
			strQuery = strQuery.append(" and  sa.isMonthlyService = true ");
			strQuery = strQuery.append(" and  sa.status = 'A' ");
			
			// date assign
			int currentDay;
			int currentMonth;
			int currentYear;
			int lastDayCurrentMonth;
			
			if (orderBillSearchBean.getOrderBillDate() != null && !orderBillSearchBean.getOrderBillDate().isEmpty()) {
				String[] splitDate = orderBillSearchBean.getOrderBillDate().split("-");
				currentDay = Integer.valueOf(splitDate[0]);
				currentMonth = Integer.valueOf(splitDate[1]);
				currentYear = Integer.valueOf(splitDate[2]);
			} else {
				Calendar now = Calendar.getInstance(new Locale("EN", "en"));
				currentDay = now.get(Calendar.DAY_OF_MONTH);
				currentMonth = now.get(Calendar.MONTH) + 1;
				currentYear = now.get(Calendar.YEAR);
			}

			strQuery = strQuery.append(" and date(ws.dateOrderBill) not in "
					+ " (select date(inv.paymentDate) from invoice inv "
					+ " where inv.serviceApplicationId = sa.id and inv.invoiceType ='O' " + "and month(inv.createDate)="
					+ currentMonth + " and year(inv.createDate)=" + currentYear + ") ");
			
			//check last day of month
			Date dateCheck = new DateUtil().convertStringToDateTimeDb(currentDay+"-"+currentMonth+"-"+currentYear);
			lastDayCurrentMonth = new DateUtil().lastDateOfmonth(dateCheck);
			
			if(currentDay == lastDayCurrentMonth){
				strQuery = strQuery.append(" and day(ws.dateOrderBill) >= "+ currentDay);
			}else{
				strQuery = strQuery.append(" and "+ currentDay +" = day(ws.dateOrderBill)");
			}
			
			//block before date
			Date dateCheckBefore = new DateUtil().convertStringToDateTimeDb(currentDay+"-"+currentMonth+"-"+currentYear);
			strQuery = strQuery.append(" and (date(ws.dateOrderBill) = date(:dateCheckBefore) or date(ws.dateOrderBill) < date(:dateCheckBefore))");
			
			//prepare statement
			logger.info("[method : getCountTotalOrderBill][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			// key
			if (orderBillSearchBean.getKey() != null && (!orderBillSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + orderBillSearchBean.getKey() + "%");
			}
			
			// zone
			if (orderBillSearchBean.getZone() != null && (orderBillSearchBean.getZone() > 0)) {
				query.setLong("zoneId", orderBillSearchBean.getZone());
			}
			//block before date
			query.setDate("dateCheckBefore", dateCheckBefore);
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
			
			logger.info("[method : getCountTotalOrderBill][count : " + count + "]");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPageForOrderBill(Pagination pagination, OrderBillSearchBean orderBillSearchBean,Boolean isAuto) {
		logger.info("[method : getByPageForOrderBill][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if (orderBillSearchBean != null) {
			strQuery = strQuery.append("select * from service_application sa ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" join worksheet ws on ws.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join service_package sp on sp.id = sa.servicePackageId ");
			// key
			if (orderBillSearchBean.getKey() != null && (!orderBillSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where sa.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ) ");
			} else {
				strQuery = strQuery.append(" where 1=1 ");
			}
			
			// zone for append
			if (orderBillSearchBean.getZone() != null && (orderBillSearchBean.getZone() > 0)) {
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			} else {
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
			if(orderBillSearchBean.getServicePackageTypeId() != null && (orderBillSearchBean.getServicePackageTypeId() > 0)){
				strQuery = strQuery.append(" and  sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			//worksheet 
			strQuery = strQuery.append(" and ws.workSheetType = 'C_S' and ws.status = 'S' ");
			
			//type monthly service
			strQuery = strQuery.append(" and  sa.isMonthlyService = true ");
			strQuery = strQuery.append(" and  sa.status = 'A' ");
			// date assign
			int currentDay;
			int currentMonth;
			int currentYear;
			int lastDayCurrentMonth;
			
			if(orderBillSearchBean.getOrderBillDate()!= null && !orderBillSearchBean.getOrderBillDate().isEmpty()){
				String[] splitDate = orderBillSearchBean.getOrderBillDate().split("-");
				currentDay = Integer.valueOf(splitDate[0]);
				currentMonth = Integer.valueOf(splitDate[1]);
				currentYear = Integer.valueOf(splitDate[2]);
			} else {
				Calendar now = Calendar.getInstance(new Locale("EN", "en"));
				currentDay = now.get(Calendar.DAY_OF_MONTH);
				currentMonth = now.get(Calendar.MONTH) + 1;
				currentYear = now.get(Calendar.YEAR);
			}
			//check last day of month
			Date dateCheck = new DateUtil().convertStringToDateTimeDb(currentDay+"-"+currentMonth+"-"+currentYear);
			lastDayCurrentMonth = new DateUtil().lastDateOfmonth(dateCheck);
			
			strQuery = strQuery.append(" and date(ws.dateOrderBill) not in "
			+ " (select date(inv.paymentDate) from invoice inv "
			+ " where inv.serviceApplicationId = sa.id and inv.invoiceType ='O' "
			+ "and month(inv.createDate)="+ currentMonth +" and year(inv.createDate)="+ currentYear +") ");
			
			if(currentDay == lastDayCurrentMonth){
				strQuery = strQuery.append(" and day(ws.dateOrderBill) >= "+ currentDay);
			}else{
				strQuery = strQuery.append(" and "+ currentDay +" = day(ws.dateOrderBill)");
			}
			
			//block before date
			Date dateCheckBefore = new DateUtil().convertStringToDateTimeDb(currentDay+"-"+currentMonth+"-"+currentYear);
			logger.info("[dateCheckBefore : " + currentDay+"-"+currentMonth+"-"+currentYear + "]");
//			strQuery = strQuery.append(" and (date(ws.dateOrderBill) = date(:dateCheckBefore) or date(ws.dateOrderBill) < date(:dateCheckBefore))");
			strQuery = strQuery.append(" and date(ws.dateOrderBill) < date(:dateCheckBefore)");
			
			// prepare statement
			logger.info("[method : getByPageForOrderBill][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(ServiceApplication.class);
			
			// key
			if (orderBillSearchBean.getKey() != null && (!orderBillSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + orderBillSearchBean.getKey() + "%");
			}

			// zone
			if (orderBillSearchBean.getZone() != null && (orderBillSearchBean.getZone() > 0)) {
				query.setLong("zoneId", orderBillSearchBean.getZone());
			}
			//block before date
			query.setDate("dateCheckBefore", dateCheckBefore);
			
			if (orderBillSearchBean.getServicePackageTypeId() != null && (orderBillSearchBean.getServicePackageTypeId() > 0)) {
				query.setLong("servicePackageTypeId", orderBillSearchBean.getServicePackageTypeId());
			}
			
			if (pagination != null && !isAuto) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			
			//execute
			List<ServiceApplication> serviceApplications = query.list(); 
			pagination.setDataList(serviceApplications);
		}
		return pagination;
	}

	public int getCountTotalOrderInvoice(InvoiceSearchBean invoiceSearchBean,Boolean isAuto) {
		logger.info("[method : getCountTotalOrderInvoice][Type : DAO]");
		Date startDate = null;
		Date endDate = null;
		Date startDate1 = null;
		Date endDate1 = null;
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if(invoiceSearchBean != null){
			strQuery = strQuery.append("select count(DISTINCT i.id) FROM invoice i ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append("join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append("join receipt r on r.invoiceId = i.id ");
			strQuery = strQuery.append("LEFT JOIN invoice_history_print ihp ON ihp.invoiceId = i.id ");
			strQuery = strQuery.append(" where i.createDate is not null ");
			
			//key
			if (invoiceSearchBean.getKey() != null && (!invoiceSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and i.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or i.invoiceCode like :criteria ) ");
			}
			//status for append
			if(invoiceSearchBean.getStatus() != null && (!invoiceSearchBean.getStatus().isEmpty())){
				strQuery = strQuery.append(" and i.status = :status ");
			}
			//invoiceType for append
			if(invoiceSearchBean.getInvoiceType() != null && (!invoiceSearchBean.getInvoiceType().isEmpty())){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}
			//zone for append
			if(invoiceSearchBean.getZone() != null && (invoiceSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
			if(invoiceSearchBean.getPersonnelId() != null && (invoiceSearchBean.getPersonnelId() > 0)){
				strQuery = strQuery.append(" and  ihp.assignPersonnelId = :assignPersonnelId ");
			}
			
			if(invoiceSearchBean.getServicePackageTypeId() != null && (invoiceSearchBean.getServicePackageTypeId() > 0)){
				strQuery = strQuery.append(" and  sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			//paymentDate for append
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if(invoiceSearchBean.getDaterange() != null && (!invoiceSearchBean.getDaterange().isEmpty())){
				String paymentDate = invoiceSearchBean.getDaterange();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate = (formatDataEn2.parse(paymentDates[0]));
							endDate = (formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(null != startDate && null != endDate){
				strQuery = strQuery.append(" and  (DATE(i.createDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(invoiceSearchBean.getDaterange1() != null && (!invoiceSearchBean.getDaterange1().isEmpty())){
				String paymentDate = invoiceSearchBean.getDaterange1();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate1 = (formatDataEn2.parse(paymentDates[0]));
							endDate1 = (formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(null != startDate1 && null != endDate1){
				strQuery = strQuery.append(" and  (DATE(r.paymentDate) BETWEEN :startDate1 AND :endDate1) ");
			}
			
			// search by billing
			if(TextUtil.isNotEmpty(invoiceSearchBean.getSearchbill())) {
				strQuery = strQuery.append(" and  i.billing = 1 ");
			}
			
			// search by isBadDebt
			if (TextUtil.isNotEmpty(invoiceSearchBean.getSearchbadDebt())) {
				strQuery = strQuery.append(" and  i.isBadDebt = 1 ");
			}
			
			//date assign
			
			//prepare statement
			logger.info("[method : getCountTotalOrderInvoice][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			// key
			if (invoiceSearchBean.getKey() != null && (!invoiceSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + invoiceSearchBean.getKey() + "%");
			}
			// status
			if (invoiceSearchBean.getStatus() != null && (!invoiceSearchBean.getStatus().isEmpty())) {
				query.setString("status", invoiceSearchBean.getStatus());
			}
			// invoiceType
			if (invoiceSearchBean.getInvoiceType() != null && (!invoiceSearchBean.getInvoiceType().isEmpty())) {
				query.setString("invoiceType", invoiceSearchBean.getInvoiceType());
			}
			// zone
			if (invoiceSearchBean.getZone() != null && (invoiceSearchBean.getZone() > 0)) {
				query.setLong("zoneId", invoiceSearchBean.getZone());
			}
			
			if (invoiceSearchBean.getPersonnelId() != null && (invoiceSearchBean.getPersonnelId() > 0)) {
				query.setLong("assignPersonnelId", invoiceSearchBean.getPersonnelId());
			}
			
			if (invoiceSearchBean.getServicePackageTypeId() != null && (invoiceSearchBean.getServicePackageTypeId() > 0)) {
				query.setLong("servicePackageTypeId", invoiceSearchBean.getServicePackageTypeId());
			}
			
			if(null != startDate && null != endDate){
				query.setDate("startDate", startDate);
				query.setDate("endDate", endDate);
			}
		
			if(null != startDate1 && null != endDate1){
				query.setDate("startDate1", startDate1);
				query.setDate("endDate1", endDate1);
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
			
			logger.info("[method : getCountTotalOrderInvoice][count : " + count + "]");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPageForOrderInvoice(Pagination pagination, InvoiceSearchBean invoiceSearchBean,Boolean isAuto) {
		logger.info("[method : getByPageForOrderInvoice][Type : DAO]");
		logger.info("[method : getByPageForOrderInvoice][invoiceSearchBean : "+ invoiceSearchBean.toString() +"]");
		Date startDate = null;
		Date endDate = null;
		Date startDate1 = null;
		Date endDate1 = null;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if (invoiceSearchBean != null) {
			strQuery = strQuery.append("select * FROM invoice i ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cust.id = cont.customerId ");
			strQuery = strQuery.append("join service_package sp on sp.id = sa.servicePackageId ");
			strQuery = strQuery.append("join receipt r on r.invoiceId = i.id ");
			strQuery = strQuery.append("LEFT JOIN invoice_history_print ihp ON ihp.invoiceId = i.id ");
			strQuery = strQuery.append(" where i.createDate is not null ");
			
			//key
			if (invoiceSearchBean.getKey() != null && (!invoiceSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" and i.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or i.invoiceCode like :criteria ) ");
			}
			//status for append
			if(invoiceSearchBean.getStatus() != null && (!invoiceSearchBean.getStatus().isEmpty())){
				strQuery = strQuery.append(" and i.status = :status ");
			}
			//invoiceType for append
			if(invoiceSearchBean.getInvoiceType() != null && (!invoiceSearchBean.getInvoiceType().isEmpty())){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}
			//zone for append
			if(invoiceSearchBean.getZone() != null && (invoiceSearchBean.getZone() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
			if(invoiceSearchBean.getPersonnelId() != null && (invoiceSearchBean.getPersonnelId() > 0)){
				strQuery = strQuery.append(" and  ihp.assignPersonnelId = :assignPersonnelId ");
			}
			
			if(invoiceSearchBean.getServicePackageTypeId() != null && (invoiceSearchBean.getServicePackageTypeId() > 0)){
				strQuery = strQuery.append(" and  sp.ServicePackageTypeId = :servicePackageTypeId ");
			}
			
			//paymentDate for append
			//SimpleDateFormat formatDataEn1 = new SimpleDateFormat("yyyy-MM-dd", new Locale("EN", "en"));
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if(invoiceSearchBean.getDaterange() != null && (!invoiceSearchBean.getDaterange().isEmpty())){
				String paymentDate = invoiceSearchBean.getDaterange();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate = (formatDataEn2.parse(paymentDates[0]));
							endDate = (formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(null != startDate && null != endDate){
				strQuery = strQuery.append(" and  (DATE(i.createDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(invoiceSearchBean.getDaterange1() != null && (!invoiceSearchBean.getDaterange1().isEmpty())){
				String paymentDate = invoiceSearchBean.getDaterange1();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate1 = (formatDataEn2.parse(paymentDates[0]));
							endDate1 = (formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(null != startDate1 && null != endDate1){
				strQuery = strQuery.append(" and  (DATE(r.paymentDate) BETWEEN :startDate1 AND :endDate1) ");
			}
			
			// search by billing
			if(TextUtil.isNotEmpty(invoiceSearchBean.getSearchbill())) {
				strQuery = strQuery.append(" and  i.billing = 1 ");
			}
			
			// search by isBadDebt
			if (TextUtil.isNotEmpty(invoiceSearchBean.getSearchbadDebt())) {
				strQuery = strQuery.append(" and  i.isBadDebt = 1 ");
			}
			
			strQuery = strQuery.append(" GROUP by i.invoiceCode ");
			
			//date assign
			
			strQuery = strQuery.append(" order by i.id desc ");
			
			//prepare statement
			logger.info("[method : getByPageForOrderInvoice][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);
			
			// key
			if (invoiceSearchBean.getKey() != null && (!invoiceSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + invoiceSearchBean.getKey() + "%");
			}
			// status
			if (invoiceSearchBean.getStatus() != null && (!invoiceSearchBean.getStatus().isEmpty())) {
				query.setString("status", invoiceSearchBean.getStatus());
			}
			// invoiceType
			if (invoiceSearchBean.getInvoiceType() != null && (!invoiceSearchBean.getInvoiceType().isEmpty())) {
				query.setString("invoiceType", invoiceSearchBean.getInvoiceType());
			}
			// zone
			if (invoiceSearchBean.getZone() != null && (invoiceSearchBean.getZone() > 0)) {
				query.setLong("zoneId", invoiceSearchBean.getZone());
			}
			
			if (invoiceSearchBean.getPersonnelId() != null && (invoiceSearchBean.getPersonnelId() > 0)) {
				query.setLong("assignPersonnelId", invoiceSearchBean.getPersonnelId());
			}
			
			if (invoiceSearchBean.getServicePackageTypeId() != null && (invoiceSearchBean.getServicePackageTypeId() > 0)) {
				query.setLong("servicePackageTypeId", invoiceSearchBean.getServicePackageTypeId());
			}
			
			if(null != startDate && null != endDate){
				query.setDate("startDate", startDate);
				query.setDate("endDate", endDate);
			}
			
			if(null != startDate1 && null != endDate1){
				query.setDate("startDate1", startDate1);
				query.setDate("endDate1", endDate1);
			}
			
			// date
			if (pagination != null && isAuto) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			
			//execute
			List<Invoice> invoices = query.list(); 
			pagination.setDataList(invoices);
		}
		return pagination;
	}

	public Invoice getInvoiceById(Long id) {
		logger.info("[method : getInvoiceById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Invoice iv where iv.isDeleted = false and iv.id = :id");
		logger.info("[method : getInvoiceById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Invoice invoice = (Invoice) query.uniqueResult();

		return invoice;
	}

	public int getCountTotalOrderReceipt(ReceiptSearchBean receiptSearchBean, String status) {
		logger.info("[method : getCountTotalOrderReceipt][Type : DAO]");
		logger.info("[method : getCountTotalOrderReceipt][receiptSearchBean : "+ receiptSearchBean.toString() +"]");
		String startDate = "";
		String endDate = "";
		int count = 0;
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if (receiptSearchBean != null) {
			strQuery = strQuery.append("SELECT COUNT(countTotal) FROM (select count(r.id) as countTotal FROM receipt r ");
			strQuery = strQuery.append("join invoice i on i.id = r.invoiceId ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cust.id = cont.customerId ");
			
			//key
			if (receiptSearchBean.getKey() != null && (!receiptSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where i.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or r.receiptCode like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}

			//invoiceType for append
			if(receiptSearchBean.getInvoiceType() != null && (!receiptSearchBean.getInvoiceType().isEmpty())){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}

			//paymentDate for append
			SimpleDateFormat formatDataEn1 = new SimpleDateFormat("yyyy-MM-dd", new Locale("EN", "en"));
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if(receiptSearchBean.getDaterange() != null && (!receiptSearchBean.getDaterange().isEmpty())){
				String paymentDate = receiptSearchBean.getDaterange();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[0]));
							endDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
//				try {
//					startDate = formatDataUs.format(new Date());
//					endDate = formatDataUs.format(new Date());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
			
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(r.paymentDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(status != null && !status.isEmpty()){
				strQuery = strQuery.append(" and r.status = :status  ");
			}else{
				strQuery = strQuery.append(" and r.status = 'P'  ");
			}
			
			strQuery = strQuery.append(" GROUP by r.id HAVING countTotal > 1) as totalReceipt ");
			
			//date assign
			
			//prepare statement
			logger.info("[method : getByPageForOrderReceipt][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			// key
			if (receiptSearchBean.getKey() != null && (!receiptSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + receiptSearchBean.getKey() + "%");
			}
			// invoiceType
			if (receiptSearchBean.getInvoiceType() != null && (!receiptSearchBean.getInvoiceType().isEmpty())) {
				query.setString("invoiceType", receiptSearchBean.getInvoiceType());
			}
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			

			if(status != null && !status.isEmpty()){
				query.setString("status", status);
			}
			
			//execute
			Object obj = query.uniqueResult();
			if(obj != null){
				count = Integer.valueOf(obj.toString());
			}else{
				count = 0;
			}
			
			logger.info("[method : getCountTotalOrderReceipt][count : " + count + "]");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public Pagination getByPageForOrderReceipt(Pagination pagination, ReceiptSearchBean receiptSearchBean,String status) {
		logger.info("[method : getByPageForOrderReceipt][Type : DAO]");
		logger.info("[method : getByPageForOrderReceipt][receiptSearchBean : "+ receiptSearchBean.toString() +"]");
		String startDate = "";
		String endDate = "";
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if (receiptSearchBean != null) {
			strQuery = strQuery.append("select * FROM receipt r ");
			strQuery = strQuery.append("join invoice i on i.id = r.invoiceId ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cust.id = cont.customerId ");
			
			//key
			if (receiptSearchBean.getKey() != null && (!receiptSearchBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where i.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or r.receiptCode like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where 1=1 ");
			}

			//invoiceType for append
			if(receiptSearchBean.getInvoiceType() != null && (!receiptSearchBean.getInvoiceType().isEmpty())){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}

			//paymentDate for append
			SimpleDateFormat formatDataEn1 = new SimpleDateFormat("yyyy-MM-dd", new Locale("EN", "en"));
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if(receiptSearchBean.getDaterange() != null && (!receiptSearchBean.getDaterange().isEmpty())){
				String paymentDate = receiptSearchBean.getDaterange();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[0]));
							endDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
//				try {
//					startDate = formatDataUs.format(new Date());
//					endDate = formatDataUs.format(new Date());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
						
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(r.paymentDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(status != null && !status.isEmpty()){
				strQuery = strQuery.append(" and r.status = :status  ");
			}else{
				strQuery = strQuery.append(" and r.status = 'P'  ");
			}
			
			strQuery = strQuery.append(" GROUP by r.receiptCode ORDER BY r.receiptCode DESC ");
			
			//date assign
			
			//prepare statement
			logger.info("[method : getByPageForOrderReceipt][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Receipt.class);
			
			// key
			if (receiptSearchBean.getKey() != null && (!receiptSearchBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + receiptSearchBean.getKey() + "%");
			}
			// invoiceType
			if (receiptSearchBean.getInvoiceType() != null && (!receiptSearchBean.getInvoiceType().isEmpty())) {
				query.setString("invoiceType", receiptSearchBean.getInvoiceType());
			}
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			
			// date
			if (pagination != null) {
				query.setFirstResult(pagination.getLimitStart());
				query.setMaxResults(pagination.getLimitEnd());
			}
			
			if(status != null && !status.isEmpty()){
				query.setString("status", status);
			}
			
			//execute
			List<Receipt> receipts = query.list(); 
			pagination.setDataList(receipts);
		}
		return pagination;
	}

	public Receipt getReceiptById(Long id) {
		logger.info("[method : getReceiptById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Receipt r where r.isDeleted = false and r.id = :id");
		logger.info("[method : getReceiptById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Receipt receipt = (Receipt) query.uniqueResult();

		return receipt;
	}

	public Date getPaymentOrderDate(Long serviceApplicationId) {
		logger.info("[method : getPaymentOrderDate][Type : DAO]");
		logger.info("[method : getPaymentOrderDate][worksheet id : "+ serviceApplicationId +"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		if(serviceApplicationId != null){
			strQuery = strQuery.append("select htg.assignDate from history_technician_groupwork htg ");
			strQuery = strQuery.append(" left join worksheet ws on htg.worksheetId = ws.id ");
			strQuery = strQuery.append(" left join service_application sa on sa.id = ws.serviceApplicationId ");
			strQuery = strQuery.append(" where 1=1 and ws.workSheetType = 'C_S' and sa.id = :id order by htg.id desc limit 1");
			
			// prepare statement
			logger.info("[method : getPaymentOrderDate][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setLong("id", serviceApplicationId);
			Date date = (Date) query.uniqueResult();
			return date;
		}
		return null;
	}

	public boolean updateOverDue() throws Exception {
		logger.info("[method : updateOverDue][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		boolean success = false;
		
		//get day current form current datetime
		Calendar now = Calendar.getInstance(new Locale("EN","en"));
		int currentDay = now.get(Calendar.DAY_OF_MONTH);
		int currentMonth = now.get(Calendar.MONTH) + 1;
		int currentYear = now.get(Calendar.YEAR);
		logger.info("[method : updateOverDue][day : " + currentDay + "]");
		logger.info("[method : updateOverDue][month : " + currentMonth + "]");
		logger.info("[method : updateOverDue][year : " + currentYear + "]");
		try{
			strQuery = strQuery.append("update Invoice inv set inv.status = 'O' where inv.isDeleted = false "
					+ " and inv.status = 'W' and inv.invoiceType = 'O' "
					+ " and ((day(inv.createDate) < :currentDay and month(inv.createDate)=:currentMonth and year(inv.createDate)=:currentYear) "
					+ " or (month(inv.createDate) < :currentMonth and year(inv.createDate)=:currentYear) "
					+ " or (year(inv.createDate)  < :currentYear))");
			
			// prepare statement
			logger.info("[method : updateOverDue][Query : " + strQuery.toString() + "]");
			Query query = session.createQuery(strQuery.toString());
			query.setInteger("currentDay", currentDay);
			query.setInteger("currentMonth", currentMonth);
			query.setInteger("currentYear", currentYear);
			
			query.executeUpdate();
			success = true;
		}catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		}
		return success;
	}

	public void updateReceipt(Receipt receipt) throws Exception {
		logger.info("[method : updateReceipt][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(receipt);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Invoice getInvoiceByInvoiceCodeScan(String invoiceCode,String status, String statusPayment) {
		logger.info("[method : getInvoiceByInvoiceCode][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Invoice inv where inv.isDeleted = false and inv.invoiceCode = :invoiceCode");
		strQuery = strQuery.append(" and inv.statusScan = :statusScan ");
		strQuery = strQuery.append(" and inv.status <> :statusPayment ");
		logger.info("[method : getInvoiceByInvoiceCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("invoiceCode", invoiceCode);
		query.setString("statusScan", status);
		query.setString("statusPayment", statusPayment);
		// execute
		Invoice invoice = (Invoice) query.uniqueResult();

		return invoice;
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> findInvoiceSearch(SearchBillScanBean searchBillScanBean) {
		logger.info("[method : findInvoiceSearch][Type : DAO]");
		logger.info("[method : findInvoiceSearch][searchBillScanBean : "+ searchBillScanBean.toString() +"]");
		String startDate = "";
		String endDate = "";
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("select * FROM invoice i ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append("join personnel per on i.personnelId = per.id ");
			
			//key
			if (searchBillScanBean.getKey() != null && (!searchBillScanBean.getKey().isEmpty())) {
				strQuery = strQuery.append(" where i.isDeleted = false and ( ");
				strQuery = strQuery.append(" cust.custCode like :criteria or cust.identityNumber like :criteria or cust.firstName like :criteria or cust.lastName like :criteria or cont.email like :criteria or cont.mobile like :criteria or sa.serviceApplicationNo like :criteria ");
				strQuery = strQuery.append(" or i.invoiceCode like :criteria ) ");
			}else{
				strQuery = strQuery.append(" where i.isDeleted = false ");
			}

			strQuery = strQuery.append(" and i.statusScan = 'E' ");

			//zone for append
			if(searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
			//createDate for append
			SimpleDateFormat formatDataEn1 = new SimpleDateFormat("yyyy-MM-dd", new Locale("EN", "en"));
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if(searchBillScanBean.getDateExportRange() != null && (!searchBillScanBean.getDateExportRange().isEmpty())){
				String paymentDate = searchBillScanBean.getDateExportRange();
				if(!"".equals(paymentDate)){
					String[] paymentDates = paymentDate.split(" - ");
					if(paymentDates.length > 1){
						try {
							startDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[0]));
							endDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[1]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
//				try {
//					startDate = formatDataUs.format(new Date());
//					endDate = formatDataUs.format(new Date());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
			
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(i.scanOutDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
				strQuery = strQuery.append(" and  (DATE(i.paymentDate) = :exportDate) ");
			}
			
			// ผู้รับผิดชอบ / พนักงานเก็บเงิน
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				strQuery = strQuery.append(" and per.id = :personnelCashierId ");
			}
			
			strQuery = strQuery.append(" GROUP by i.invoiceCode ");
			strQuery = strQuery.append(" order by i.id desc ");
			
			//prepare statement
			logger.info("[method : getByPageForOrderInvoice][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);
			
			// key
			if (searchBillScanBean.getKey() != null && (!searchBillScanBean.getKey().isEmpty())) {
				query.setString("criteria", "%" + searchBillScanBean.getKey() + "%");
			}
			// zone
			if (searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)) {
				query.setLong("zoneId", searchBillScanBean.getZoneId());
			}
			// createDate
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			
			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
				try {
				SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				SimpleDateFormat formatData_Th = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
				query.setString("exportDate", formatDataUs.format(formatData_Th.parse(searchBillScanBean.getExportDate())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// ผู้รับผิดชอบ / พนักงานเก็บเงิน
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				query.setLong("personnelCashierId", searchBillScanBean.getPersonnelCashierId());
			}
						
			//execute
			List<Invoice> Invoices = query.list();
			return Invoices;

	}

	public List<Invoice> findInvoiceTypeSearch(SearchBillScanBean searchBillScanBean) {
		logger.info("[method : findInvoiceTypeSearch][Type : DAO]");
		logger.info("[method : findInvoiceTypeSearch][searchBillScanBean : "+ searchBillScanBean.toString() +"]");
		
		String startDate = "";
		String endDate = "";
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("select * FROM invoice i ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cont.customerId = cust.id ");
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				strQuery = strQuery.append("join service_application_assign_cashier saac on saac.serviceApplicationId = sa.id ");
			}
			strQuery = strQuery.append(" where i.isDeleted = false and i.status in ('W','O') and i.invoiceType = 'O' ");
			
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				strQuery = strQuery.append(" and saac.personnelId = :personnelId ");
			}
			
			//zone for append
			if(searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)){
				strQuery = strQuery.append(" and  ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and  ad.addressType = '4' ");
			}
			
//			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
//				strQuery = strQuery.append(" and  (DATE(i.paymentDate) = :exportDate) ");
//			}

			SimpleDateFormat formatDataEn1 = new SimpleDateFormat("yyyy-MM-dd", new Locale("EN", "en"));
			SimpleDateFormat formatDataEn2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("EN", "en"));
			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
				try {
					if(searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())){
						String paymentDate = searchBillScanBean.getExportDate();
						if(!"".equals(paymentDate)){
							String[] paymentDates = paymentDate.split(" - ");
							if(paymentDates.length > 1){
								try {
									startDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[0]));
									endDate = formatDataEn1.format(formatDataEn2.parse(paymentDates[1]));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				startDate = formatDataEn1.format(new Date());
				endDate = formatDataEn1.format(new Date());
			}
			
			if(!"".equals(startDate) && !"".equals(endDate)){
				strQuery = strQuery.append(" and  (DATE(i.paymentDate) BETWEEN :startDate AND :endDate) ");
			}
			
			strQuery = strQuery.append(" GROUP by i.invoiceCode ");
			strQuery = strQuery.append(" order by z.id asc ");
			
			//prepare statement
			logger.info("[method : findInvoiceTypeSearch][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);

			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				query.setLong("personnelId", searchBillScanBean.getPersonnelCashierId());
			}
			
			// zone
			if (searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)) {
				query.setLong("zoneId", searchBillScanBean.getZoneId());
			}
				
			if(!"".equals(startDate) && !"".equals(endDate)){
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
			}
			
			//execute
			List<Invoice> Invoices = query.list();
			return Invoices;

	}
	
	public List<InvoiceHistoryPrint> findInvoiceHistoryPrintSearch(SearchBillScanBean searchBillScanBean) {
		logger.info("[method : findInvoiceHistoryPrintSearch][Type : DAO]");
		logger.info("[method : findInvoiceHistoryPrintSearch][searchBillScanBean : "+ searchBillScanBean.toString() +"]");
		
		String createDate = null;
		// ปี-เดือน
		if((searchBillScanBean.getYear() != null && (!searchBillScanBean.getYear().isEmpty())) &&
			(searchBillScanBean.getMonth() != null && (!searchBillScanBean.getMonth().isEmpty()))){
			String yyyymm = searchBillScanBean.getYear()+"-"+searchBillScanBean.getMonth();
			SimpleDateFormat sdfYYYYMMTH = new SimpleDateFormat("yyyy-MM", new Locale("TH", "th"));
			SimpleDateFormat sdfYYYYMMENG = new SimpleDateFormat("yyyy-MM", Locale.US);
			try {
				createDate = sdfYYYYMMENG.format(sdfYYYYMMTH.parse(yyyymm));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM invoice_history_print ih ");
			strQuery = strQuery.append("join invoice i on i.id = ih.invoiceId ");
			strQuery = strQuery.append("join personnel per on per.id = ih.personnelId ");
			strQuery = strQuery.append("where i.isDeleted = false and per.isDeleted = false ");
			
			// ผู้รับผิดชอบ / พนักงานเก็บเงิน
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				strQuery = strQuery.append(" and ih.assignPersonnelId = :personnelCashierId ");
			}
			
			if(TextUtil.isNotEmpty(createDate)) {
				strQuery = strQuery.append(" and DATE_FORMAT(ih.createDate, '%Y-%m') = :createDate ");
			}			
			
			strQuery = strQuery.append(" order by ih.assignPersonnelId,ih.createDate ");
			
			//prepare statement
			logger.info("[method : findInvoiceHistoryPrintSearch][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(InvoiceHistoryPrint.class);
			
			// ผู้รับผิดชอบ / พนักงานเก็บเงิน
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				query.setLong("personnelCashierId", searchBillScanBean.getPersonnelCashierId());
			}
			
			if(TextUtil.isNotEmpty(createDate)) {
				query.setString("createDate", createDate);
			}
						
			//execute
			List<InvoiceHistoryPrint> invoiceHistoryPrint = query.list();
			return invoiceHistoryPrint;
	}

	public List<Invoice> getByInvoiceTypeForReport(String reportrange, String invoiceType, String split, String invoiceStatus) {
		logger.info("[method : getByInvoiceTypeForReport][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM invoice i ");
			strQuery = strQuery.append(" join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" WHERE i.personnelId is not null and i.isDeleted = false ");
			strQuery = strQuery.append(" and ad.addressType = '4' ");
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				strQuery = strQuery.append(" and  (DATE(i.createDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(!"0".equals(invoiceType)){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}
			
			if(!"0".equals(invoiceStatus)){
				strQuery = strQuery.append(" and i.status = :invoiceStatus ");
			}
			
			if(split.equals("0")){
				strQuery = strQuery.append(" order by i.personnelId ");
			}else if(split.equals("1")){
				strQuery = strQuery.append(" order by z.id ");
			}else if(split.equals("3")){
				strQuery = strQuery.append(" order by i.createDate ");
			}else if(split.equals("4")){
				strQuery = strQuery.append(" order by cust.customerFeatureTypeId ");
			}
			
			logger.info("[method : getByInvoiceTypeForReport][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				try {
					query.setDate("startDate", formatDataEngRange.parse(startDate));
					query.setDate("endDate", formatDataEngRange.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(!"0".equals(invoiceType)){
				query.setString("invoiceType", invoiceType);
			}
			
			if(!"0".equals(invoiceStatus)){
				query.setString("invoiceStatus", invoiceStatus);
			}
		
			//execute
			List<Invoice> invoiceList = query.list();
			return invoiceList;
	}
	
	public List<Invoice> getByInvoiceTypeBybaddebtForReport(String reportrange, String invoiceType, String split, String invoiceStatus) {
		logger.info("[method : getByInvoiceTypeBybaddebtForReport][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM invoice i ");
			strQuery = strQuery.append(" join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append(" join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append(" join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append(" WHERE i.personnelId is not null and i.isDeleted = false ");
			strQuery = strQuery.append(" and ad.addressType = '4' ");
			strQuery = strQuery.append(" and cust.isActive = false ");
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				strQuery = strQuery.append(" and  (DATE(i.createDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(!"0".equals(invoiceType)){
				strQuery = strQuery.append(" and i.invoiceType = :invoiceType ");
			}
			
			strQuery = strQuery.append(" and i.status != 'S' ");
			
			if(split.equals("0")){
				strQuery = strQuery.append(" order by i.personnelId ");
			}else if(split.equals("1")){
				strQuery = strQuery.append(" order by z.id ");
			}else if(split.equals("3")){
				strQuery = strQuery.append(" order by i.createDate ");
			}else if(split.equals("4")){
				strQuery = strQuery.append(" order by cust.customerFeatureTypeId ");
			}
			
			logger.info("[method : getByInvoiceTypeBybaddebtForReport][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				try {
					query.setDate("startDate", formatDataEngRange.parse(startDate));
					query.setDate("endDate", formatDataEngRange.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(!"0".equals(invoiceType)){
				query.setString("invoiceType", invoiceType);
			}
			
			if(!"0".equals(invoiceStatus)){
				query.setString("invoiceStatus", invoiceStatus);
			}
		
			//execute
			List<Invoice> invoiceList = query.list();
			return invoiceList;
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> findInvoiceByServiceApplication(Long ServiceApplicationId, String TypeInvoice) {
		logger.info("[method : findInvoiceByServiceApplication][Type : DAO]");
		logger.info("ServiceApplicationId : "+ServiceApplicationId);
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Invoice> invoiceList = (List<Invoice>) session.createQuery("from Invoice inv where inv.isDeleted = false "
				+ " and inv.serviceApplication.id = "+ServiceApplicationId + " and inv.invoiceType = 'O' ").list();
		
		return invoiceList;
	}

	public Invoice getInvoiceByCode(String invoiceCode) {
		logger.info("[method : getInvoiceByCode][Type : DAO]");
		logger.info("[method : getInvoiceByCode][invoiceCode : "+invoiceCode+"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Invoice inv where inv.invoiceCode = :invoiceCode");
		logger.info("[method : getInvoiceByCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("invoiceCode", invoiceCode);
		// execute
		Invoice invoice = (Invoice) query.uniqueResult();

		return invoice;
	}
	
	public List<Invoice> findInvoiceTypeSearchConfig(SearchBillScanBean searchBillScanBean) {
		logger.info("[method : findInvoiceTypeSearchConfig][Type : DAO]");
		logger.info("[method : findInvoiceTypeSearchConfig][searchBillScanBean : "+ searchBillScanBean.toString() +"]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("select * FROM invoice i ");
			strQuery = strQuery.append("join service_application sa on sa.id = i.serviceApplicationId ");
			strQuery = strQuery.append("join address ad on ad.serviceApplicationId = sa.id ");
			strQuery = strQuery.append("join zone z on z.id = ad.zoneId ");
			strQuery = strQuery.append("join customer cust on sa.customerId = cust.id ");
			strQuery = strQuery.append("join contact cont on cont.customerId = cust.id ");
			strQuery = strQuery.append("left join service_application_assign_cashier saac on saac.serviceApplicationId = sa.id ");
			strQuery = strQuery.append(" where i.isDeleted = false and i.status in ('W','O') and i.invoiceType = 'O' ");
			
			//zone for append
			if(searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)){
				strQuery = strQuery.append(" and ad.addressType = '4' and z.id = :zoneId ");
			}else{
				strQuery = strQuery.append(" and ad.addressType = '4' ");
			}
			
			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
				strQuery = strQuery.append(" and (DATE(i.paymentDate) = :exportDate) ");
			}
			
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				strQuery = strQuery.append(" and saac.personnelId = :personnelId ");
			}
			
			if(searchBillScanBean.isMobile()){
				strQuery = strQuery.append(" and i.isMobile = :isMobile ");
			}
			
			strQuery = strQuery.append(" GROUP by i.invoiceCode ");
			strQuery = strQuery.append(" order by i.id desc ");
			
			//prepare statement
			logger.info("[method : findInvoiceTypeSearchConfig][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);

			// zone
			if (searchBillScanBean.getZoneId() != null && (searchBillScanBean.getZoneId() > 0)) {
				query.setLong("zoneId", searchBillScanBean.getZoneId());
			}

			if (searchBillScanBean.getExportDate() != null && (!searchBillScanBean.getExportDate().isEmpty())) {
				try {
				SimpleDateFormat formatDataUs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				SimpleDateFormat formatData_Th = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
				query.setString("exportDate", formatDataUs.format(formatData_Th.parse(searchBillScanBean.getExportDate())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			if(searchBillScanBean.getPersonnelCashierId() != null && (searchBillScanBean.getPersonnelCashierId() > 0)){
				query.setLong("personnelId", searchBillScanBean.getPersonnelCashierId());
			}
			
			if(searchBillScanBean.isMobile()){
				query.setBoolean("isMobile", searchBillScanBean.isMobile());
			}
			
			//execute
			List<Invoice> Invoices = query.list();
			return Invoices;

	}

	public List<Invoice> findInvoiceByCreateDate() {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM `invoice` where createDate = CAST('2018-03-05' AS DATE) and status = 'W' ");

			//prepare statement
			logger.info("[method : findInvoiceByCreateDate][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(Invoice.class);
			
			//execute
			List<Invoice> Invoices = query.list();
			return Invoices;
	}
	
}
