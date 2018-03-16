package com.hdw.mccable.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hdw.mccable.dao.ProductDAO;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.ServiceProduct;

@Repository
public class ProductDAOImpl implements ProductDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	// search with key(product name or product code and equip type id)
	@SuppressWarnings("unchecked")
	public List<EquipmentProduct> searchByTypeEquipment(String criteria, Long equipmentProductCategoryId,
			Long stockId) {
		logger.info("[method : findByTypeEquipment][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct ep where isDeleted = false ");

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append(" and (ep.productName like :criteria ");
			strQuery = strQuery.append(" or ep.productCode like :criteria) ");
		} else {
			strQuery = strQuery.append(" and 1=1 ");
		}

		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			strQuery = strQuery.append("and ep.equipmentProductCategory.id = :equipmentProductCategoryId ");
		}

		// check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append("and ep.stock.id = :stockId ");
		}

		logger.info("[method : findByTypeEquipment][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}
		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			query.setLong("equipmentProductCategoryId", equipmentProductCategoryId);
		}
		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}

		// execute
		List<EquipmentProduct> equipmentProducts = query.list();

		return equipmentProducts;

	}

	// load EquimentProduct One Model
	public EquipmentProduct findEquipmentProductById(Long id) {
		logger.info("[method : findEquipmentProductById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct ep where isDeleted = false and ep.id = :id");
		logger.info("[method : findEquipmentProductById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		EquipmentProduct equipmentProduct = (EquipmentProduct) query.uniqueResult();

		return equipmentProduct;
	}

	public Long save(EquipmentProductItem equipmentProductItem) throws Exception {
		logger.info("[method : save][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(equipmentProductItem);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Long saveMasterProduct(EquipmentProduct equipmentProduct) throws Exception {
		logger.info("[method : saveMasterProduct][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(equipmentProduct);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}

	public EquipmentProductItem findEquipmentProductItemById(Long id) {
		logger.info("[method : findEquipmentProductItemById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProductItem epItem where isDeleted = false and epItem.id = :id");
		logger.info("[method : findEquipmentProductItemById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		EquipmentProductItem equipmentProductItem = (EquipmentProductItem) query.uniqueResult();

		return equipmentProductItem;
	}

	public void update(EquipmentProduct equipmentProduct) throws Exception {
		logger.info("[method : update][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(equipmentProduct);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteProductItemGroup(EquipmentProduct equipmentProduct) throws Exception {
		logger.info("[method : deleteProductItemGroup][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();
		
		try{
			strQuery = strQuery.append("update EquipmentProductItem epItem set epItem.isDeleted = true where epItem.equipmentProduct.id = :id");
			logger.info("[method : deleteProductItemGroup][Query : " + strQuery.toString() + "]");

			// prepare statement
			Query query = session.createQuery(strQuery.toString());
			query.setLong("id", equipmentProduct.getId());
			//execute 
			query.executeUpdate();
			
		}catch(Exception ex){
			throw (ex);
		}
	}

	public void updateProductItem(EquipmentProductItem equipmentProductItem) throws Exception {
		logger.info("[method : updateProductItem][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(equipmentProductItem);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<EquipmentProduct> findByStockAndProductCode(Long stockId, String productCode) {
		logger.info("[method : findByStockAndProductCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct ep where isDeleted = false ");

		// check criteria
		if (productCode != null && (!productCode.isEmpty())) {
			strQuery = strQuery.append(" and ep.productCode = :productCode ");
		}

		// check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append("and ep.stock.id = :stockId ");
		}

		logger.info("[method : findByStockAndProductCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check criteria
		if (productCode != null && (!productCode.isEmpty())) {
			query.setString("productCode", productCode);
		}

		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}

		// execute
		List<EquipmentProduct> equipmentProducts = query.list();

		return equipmentProducts;
	}

	@SuppressWarnings("unchecked")
	public List<EquipmentProduct> searchByTypeEquipment(String criteria, String equipmentProductCategoryCode,
			Long stockId) {
		logger.info("[method : findByTypeEquipment][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct ep where isDeleted = false ");

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append(" and (ep.productName like :criteria ");
			strQuery = strQuery.append(" or ep.productCode like :criteria) ");
		} else {
			strQuery = strQuery.append(" and 1=1 ");
		}

		// check equipmentProductCategory
		if (equipmentProductCategoryCode != null && (!equipmentProductCategoryCode.isEmpty())) {
			if(equipmentProductCategoryCode.equals("00001")){
				strQuery = strQuery.append("and ep.equipmentProductCategory.equipmentProductCategoryCode <> '00002' and "
						+ " ep.equipmentProductCategory.equipmentProductCategoryCode <> '00003' ");
			}else{
				strQuery = strQuery.append("and ep.equipmentProductCategory.equipmentProductCategoryCode = :equipmentProductCategoryCode ");
			}
		}

		// check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append("and ep.stock.id = :stockId ");
		}

		logger.info("[method : findByTypeEquipment][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}
		// check equipmentProductCategory
		if (equipmentProductCategoryCode != null && (!equipmentProductCategoryCode.isEmpty()) && !equipmentProductCategoryCode.equals("00001")) {
			query.setString("equipmentProductCategoryCode", equipmentProductCategoryCode);
		}
		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}

		// execute
		List<EquipmentProduct> equipmentProducts = query.list();

		return equipmentProducts;
	}

	public InternetProduct findInternetProductById(Long id) {
		logger.info("[method : findInternetProductById][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProduct ip where isDeleted = false and ip.id = :id");
		logger.info("[method : findInternetProductById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		InternetProduct internetProduct = (InternetProduct) query.uniqueResult();

		return internetProduct;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceProduct> searchByTypeService(String criteria, Long equipmentProductCategoryId, Long stockId) {
		logger.info("[method : searchByTypeService][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ServiceProduct sp where sp.isDeleted = false ");

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append(" and (sp.serviceChargeName like :criteria ");
			strQuery = strQuery.append(" or sp.productCode like :criteria) ");
		} else {
			strQuery = strQuery.append(" and 1=1 ");
		}

		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			strQuery = strQuery.append("and sp.equipmentProductCategory.id = :equipmentProductCategoryId ");
		}

		// check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append("and sp.stock.id = :stockId ");
		}

		logger.info("[method : searchByTypeService][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}
		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			query.setLong("equipmentProductCategoryId", equipmentProductCategoryId);
		}
		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}

		// execute
		List<ServiceProduct> serviceProducts = query.list();

		return serviceProducts;
	}

	@SuppressWarnings("unchecked")
	public List<InternetProduct> searchByTypeInternet(String criteria, Long equipmentProductCategoryId, Long stockId) {
		logger.info("[method : searchByTypeInternet][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from InternetProduct i where i.isDeleted = false ");

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			strQuery = strQuery.append(" and (i.productName like :criteria ");
			strQuery = strQuery.append(" or i.productCode like :criteria) ");
		} else {
			strQuery = strQuery.append(" and 1=1 ");
		}

		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			strQuery = strQuery.append("and i.equipmentProductCategory.id = :equipmentProductCategoryId ");
		}

		// check stock
		if (stockId != null && stockId > 0) {
			strQuery = strQuery.append("and i.stock.id = :stockId ");
		}

		logger.info("[method : searchByTypeInternet][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// check criteria
		if (criteria != null && (!criteria.isEmpty())) {
			query.setString("criteria", "%" + criteria + "%");
		}
		// check equipmentProductCategory
		if (equipmentProductCategoryId != null && equipmentProductCategoryId > 0) {
			query.setLong("equipmentProductCategoryId", equipmentProductCategoryId);
		}
		// check stock
		if (stockId != null && stockId > 0) {
			query.setLong("stockId", stockId);
		}

		// execute
		List<InternetProduct> internetProducts = query.list();

		return internetProducts;
	}

	public List<EquipmentProduct> findAllSupplier() {
		logger.info("[method : findAllSupplier][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from EquipmentProduct ep where ep.isDeleted = false GROUP by ep.supplier ");

		logger.info("[method : findAllSupplier][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());

		// execute
		List<EquipmentProduct> equipmentProducts = query.list();

		return equipmentProducts;
	}

	public List<EquipmentProduct> getDataProductForReport(String reportrange, String purchasingNumber,
			String equipmentProductType, String equipmentStock, String supplier, String split, String sort) {
		logger.info("[method : getDataProductForReport][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM equipment_product ep ");
			strQuery = strQuery.append(" join equipment_product_item epi on epi.equipmentProductId = ep.id ");
			strQuery = strQuery.append(" WHERE ep.isDeleted = false and epi.isDeleted = false ");
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				strQuery = strQuery.append(" and (DATE(epi.importSystemDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(!"0".equals(purchasingNumber)){
				strQuery = strQuery.append(" and epi.reference = :purchasingNumber ");
			}
			
			if(!"0".equals(equipmentProductType)){
				strQuery = strQuery.append(" and ep.productCategoryId = :equipmentProductType ");
			}
			
			if(!"0".equals(equipmentStock)){
				strQuery = strQuery.append(" and ep.stockId = :equipmentStock ");
			}
			
			if(!"0".equals(supplier)){
				strQuery = strQuery.append(" and ep.supplier = :supplier ");
			}
			
			strQuery = strQuery.append(" order by ep.id ");
			
			logger.info("[method : getDataProductForReport][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(EquipmentProduct.class);
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				try {
					query.setDate("startDate", formatDataEngRange.parse(startDate));
					query.setDate("endDate", formatDataEngRange.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(!"0".equals(purchasingNumber)){
				query.setString("purchasingNumber", purchasingNumber);
			}
			
			if(!"0".equals(equipmentProductType)){
				query.setString("equipmentProductType", equipmentProductType);
			}
			
			if(!"0".equals(equipmentStock)){
				query.setString("equipmentStock", equipmentStock);
			}
			
			if(!"0".equals(supplier)){
				query.setString("supplier", supplier);
			}
			
			//execute
			List<EquipmentProduct> equipmentProductList = query.list();
			return equipmentProductList;
	}

	public List<RequisitionDocument> getDataRequisitionProductForReport(String reportrange, String equipmentProductType,
			String equipmentStock, String split, String sort) {
		logger.info("[method : getDataRequisitionProductForReport][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM requisition_document r ");
			strQuery = strQuery.append(" join requisition_item ri on ri.requisitionDocumentId = r.id ");
			strQuery = strQuery.append(" join equipment_product ep on ri.equipmentProductId = ep.id ");
			strQuery = strQuery.append(" join equipment_product_item epi on ri.equipmentProductItemId = epi.id ");
			strQuery = strQuery.append(" WHERE r.isDeleted = false and ri.isDeleted = false and ep.isDeleted = false and epi.isDeleted = false ");
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				strQuery = strQuery.append(" and (DATE(r.createDate) BETWEEN :startDate AND :endDate) ");
			}
			
			if(!"0".equals(equipmentProductType)){
				strQuery = strQuery.append(" and ep.productCategoryId = :equipmentProductType ");
			}
			
			if(!"0".equals(equipmentStock)){
				strQuery = strQuery.append(" and ep.stockId = :equipmentStock ");
			}
			
			strQuery = strQuery.append(" order by DATE(r.createDate) ");
			
//			if("1".equals(split)){
//				if("1".equals(sort)){
//					strQuery = strQuery.append(" order by DATE(r.createDate) desc ");
//				}else{
//					strQuery = strQuery.append(" order by DATE(r.createDate) asc ");
//				}
//			}
			
			logger.info("[method : getDataRequisitionProductForReport][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(RequisitionDocument.class);
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
				try {
					query.setDate("startDate", formatDataEngRange.parse(startDate));
					query.setDate("endDate", formatDataEngRange.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(!"0".equals(equipmentProductType)){
				query.setString("equipmentProductType", equipmentProductType);
			}
			
			if(!"0".equals(equipmentStock)){
				query.setString("equipmentStock", equipmentStock);
			}
			
			//execute
			List<RequisitionDocument> requisitionDocumentList = query.list();
			return requisitionDocumentList;
	}

	public List<EquipmentProduct> getDataStocksummaryForReport(String equipmentProductType, String equipmentStock,
			String split) {
		logger.info("[method : getDataStocksummaryForReport][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

			strQuery = strQuery.append("SELECT * FROM equipment_product ep ");
			strQuery = strQuery.append(" join equipment_product_item epi on epi.equipmentProductId = ep.id ");
			strQuery = strQuery.append(" WHERE ep.isDeleted = false and epi.isDeleted = false ");
			
			if(!"0".equals(equipmentProductType)){
				strQuery = strQuery.append(" and ep.productCategoryId = :equipmentProductType ");
			}
			
			if(!"0".equals(equipmentStock)){
				strQuery = strQuery.append(" and ep.stockId = :equipmentStock ");
			}
			
			strQuery = strQuery.append(" order by ep.id ");
			
			logger.info("[method : getDataStocksummaryForReport][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			
			query.addEntity(EquipmentProduct.class);
			
			if(!"0".equals(equipmentProductType)){
				query.setString("equipmentProductType", equipmentProductType);
			}
			
			if(!"0".equals(equipmentStock)){
				query.setString("equipmentStock", equipmentStock);
			}
			
			//execute
			List<EquipmentProduct> equipmentProductList = query.list();
			return equipmentProductList;
	}

	public String genProductCode() throws Exception {
		logger.info("[method : genProductCode][Type : DAO]");
		SimpleDateFormat formatDataThMMyyyy = new SimpleDateFormat("MMyyyy", Locale.US);
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT CONCAT('PROD-',LPAD((SELECT COUNT(id)+1 FROM equipment_product), 5, '0')) as serviceApplicationCode ");
		Object obj = query.uniqueResult();
		if(null != obj){
			logger.info("genProductCode : " + String.format("%1s%2s", obj.toString(),formatDataThMMyyyy.format(new Date())));
			return String.format("%1s%2s", obj.toString(),formatDataThMMyyyy.format(new Date()));
		}
		return null;
	}

}
