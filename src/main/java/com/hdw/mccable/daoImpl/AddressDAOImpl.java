package com.hdw.mccable.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hdw.mccable.dao.AddressDAO;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.ZipCode;

@Repository
public class AddressDAOImpl implements AddressDAO {
	private static final Logger logger = LoggerFactory.getLogger(AddressDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	///////////////////////// implement method ///////////////////////////
	public Province getProvinceById(Long id) {
		logger.info("[method : getProvinceById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		Province province = (Province) session.get(Province.class, id);
		return province;
	}

	public Province getProvinceByProvinceName(String provinceName) {
		if(!"".equals(provinceName)){
			logger.info("[method : getProvinceByProvinceName][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT p.* FROM province p where p.PROVINCE_NAME = :provinceName ");
			
			logger.info("[method : getProvinceByProvinceName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("provinceName", provinceName);	
			query.addEntity(Province.class);
			//execute
			List<Province> province = query.list();
			if(null != province && province.size() > 0){
				return province.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Province> findAll() {
		logger.info("[method : findAll][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		List<Province> province = (List<Province>) session.createQuery("from Province ").list();
		return province;
	}

	public Address getAddressById(Long id) {
		logger.info("[method : getAddressById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Address a where a.isDeleted = false and a.id = :id");
		logger.info("[method : getAddressById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Address address = (Address) query.uniqueResult();
		return address;
	}

	public void updateAddress(Address address) throws Exception {
		logger.info("[method : updateAddress][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(address);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public Amphur getAmphurById(Long id) {
		logger.info("[method : getAmphurById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from Amphur a where a.id = :id");
		logger.info("[method : getAmphurById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		Amphur amphur = (Amphur) query.uniqueResult();
		return amphur;
	}

	public District getDistrictById(Long id) {
		logger.info("[method : getDistrictById][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from District d where d.id = :id");
		logger.info("[method : getDistrictById][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		District district = (District) query.uniqueResult();
		return district;
	}

	public District getDistrictByDistrictName(String districtName) {
		if(!"".equals(districtName)){
			logger.info("[method : getDistrictByDistrictName][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT d.* FROM district d where d.DISTRICT_NAME = :districtName ");
			
			logger.info("[method : getDistrictByDistrictName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("districtName", districtName);	
			query.addEntity(District.class);
			//execute
			List<District> district = query.list();
			if(null != district && district.size() > 0){
				return district.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<District> getDistrictByAmphurId(Long id) {
		logger.info("[method : getDistrictByAmphurId][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from District d where d.amphur.id = :id");
		logger.info("[method : getDistrictByAmphurId][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("id", id);

		// execute
		List<District> districts = (List<District>) query.list();
		return districts;
	}

	public Long save(Address address) throws Exception {
		logger.info("[method : save][Type : DAO]");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			Object object = session.save(address);
			session.flush();
			return (Long) object;
		}catch(Exception ex){
			throw(ex);
		}
	}
	
	public void update(Address address) throws Exception {
		logger.info("[method : update][Type : DAO]");
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(address);
			session.flush();
		}catch(Exception ex){
			throw(ex);
		}
	}

	public void deleteByServiceApplicationId(Long serviceApplicationId) {
		logger.info("[method : deleteByServiceApplicationId][Type : DAO]");
		logger.info("[serviceApplicationId : "+serviceApplicationId+"]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete Address a where a.serviceApplication.id = :serviceApplicationId and a.addressType in (3,4,5) ");
		logger.info("[method : deleteByServiceApplicationId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("serviceApplicationId", serviceApplicationId);

		// execute
		query.executeUpdate();

	}

	public void deleteByCustomerId(Long customerId) {
		logger.info("[method : deleteByCustomerId][Type : DAO]");
		logger.info("[customerId : "+customerId+"]");
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("delete Address a where a.customer.id = :customerId");
		logger.info("[method : deleteByCustomerId][Query : " + strQuery.toString() + "]");
		
		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setLong("customerId", customerId);

		// execute
		query.executeUpdate();
	}

	public ZipCode getZipCodeByDistrictCode(String districtCode) {
		logger.info("[method : getZipCodeByDistrictCode][Type : DAO]");

		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strQuery = new StringBuffer();

		strQuery = strQuery.append("from ZipCode z where z.DISTRICT_CODE = :districtCode");
		logger.info("[method : getZipCodeByDistrictCode][Query : " + strQuery.toString() + "]");

		// prepare statement
		Query query = session.createQuery(strQuery.toString());
		query.setString("districtCode", districtCode);

		// execute
		ZipCode zipCode = (ZipCode) query.uniqueResult();
		return zipCode;
	}

	public Amphur getAmphurByAmphurName(String amphurName) {
		if(!"".equals(amphurName)){
			logger.info("[method : getAmphurByAmphurName][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT a.* FROM amphur a where a.AMPHUR_NAME = :amphurName ");
			
			logger.info("[method : getAmphurByAmphurName][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("amphurName", amphurName);	
			query.addEntity(Amphur.class);
			//execute
			List<Amphur> amphur = query.list();
			if(null != amphur && amphur.size() > 0){
				return amphur.get(0);
			}
		}
		return null;
	}

	public District getDistrictByDistrictNameANDAmphurId(String districtName, Long amphurId) {
		if(!"".equals(districtName)){
			logger.info("[method : getDistrictByDistrictNameANDAmphurId][Type : DAO]");
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery = strQuery.append("SELECT d.* FROM district d where d.DISTRICT_NAME = :districtName AND d.AMPHUR_ID = :amphurId ");
			
			logger.info("[method : getDistrictByDistrictNameANDAmphurId][Query : " + strQuery.toString() + "]");
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			query.setString("districtName", districtName);	
			query.setLong("amphurId", amphurId);	
			query.addEntity(District.class);
			//execute
			List<District> district = query.list();
			if(null != district && district.size() > 0){
				return district.get(0);
			}
		}
		return null;
	}

	
}
