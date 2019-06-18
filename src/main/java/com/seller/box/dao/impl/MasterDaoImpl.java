package com.seller.box.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.seller.box.dao.MasterDao;
import com.seller.box.utils.SBConstant;

@Repository
public class MasterDaoImpl implements MasterDao {
	private static final Logger logger = LogManager.getLogger(MasterDaoImpl.class);
	@PersistenceContext
    EntityManager em;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<Integer, String> getOrderStatus() {
		logger.info("getOrderStatus()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<Integer, String> orderStatusMap = new HashMap<Integer, String>();
		try {
			String query = "SELECT ORD_STATUS_CODE, ORD_STATUS_LABEL FROM SELLER.ORDER_STATUS WHERE IS_ENABLE = 1";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				orderStatusMap.put(rs.getInt("ORD_STATUS_CODE"), rs.getString("ORD_STATUS_LABEL"));			
			}
		} catch (DataAccessException e) {
			logger.error("DataAccessException :: getOrderStatus()", e);
		}
		logger.info("getOrderStatus()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return orderStatusMap;
	}

	@Override
	public Map<String, String> getWarehouseCodeList() {
		logger.info("getWarehouseCodeList()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<String, String> warehouseCodeMap = new HashMap<String, String>();
		try {
			String query = "SELECT LOCATION_CODE, LOCATION_NAME FROM INVENTORY.INVENTORY_LOCATION WHERE IS_ENABLE = 1";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				warehouseCodeMap.put(rs.getString("LOCATION_CODE"), rs.getString("LOCATION_NAME"));			
			}
		} catch (DataAccessException e) {
			logger.error("DataAccessException :: getWarehouseCodeList()", e);
		}
		logger.info("getWarehouseCodeList()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return warehouseCodeMap;
	}

	@Override
	public Map<Integer, String> getEtailorList() {
		logger.info("getEtailorList()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<Integer, String> etailorList = new HashMap<Integer, String>();
		/****
		try {
			String query = "SELECT ORD_STATUS_CODE, ORD_STATUS_LABEL FROM SELLER.ORDER_STATUS WHERE IS_ENABLE = 1";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				orderStatusMap.put(rs.getInt("ORD_STATUS_CODE"), rs.getString("ORD_STATUS_LABEL"));			
			} 
		} catch (DataAccessException e) {
			logger.error("DataAccessException :: getEtailorList()", e);
		}
		****/
		etailorList.put(0, "Flipkart");
		etailorList.put(94, "Amazon SF");
		logger.info("getEtailorList()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return etailorList;
	}
}
