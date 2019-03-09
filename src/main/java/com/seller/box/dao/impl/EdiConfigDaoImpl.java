package com.seller.box.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.seller.box.dao.EdiConfigDao;

@Repository
public class EdiConfigDaoImpl implements EdiConfigDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public Long getId(String pSeqName) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_SEQUENCE").withSchemaName("SELLER");
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("P_SEQUENCE_NAME", pSeqName);
		Long id = call.executeFunction(Long.class, paramMap);
		return id;
	}
	
	@Override
	public String getTransControlNumber() {
		String transControlNumber = null;
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_SEQUENCE").withSchemaName("SELLER");
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("P_SEQUENCE_NAME", "TXN_CONTROL_NUMBER");
		Long id = call.executeFunction(Long.class, paramMap);
		
		transControlNumber = String.format("%010d", Integer.parseInt(id.toString()));
		return transControlNumber;
	}
	
	@Override
	public String getMessageControlNumber() {
		String msgControlNumber = null;
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_SEQUENCE").withSchemaName("SELLER");
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("P_SEQUENCE_NAME", "MSG_CONTROL_NUMBER");
		Long id = call.executeFunction(Long.class, paramMap);
		
		msgControlNumber = String.format("%010d", Integer.parseInt(id.toString()));
		return msgControlNumber;
	}
	
	@Override
	public String getInventoryControlNumber() {
		String invControlNumber = null;
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_SEQUENCE").withSchemaName("SELLER");
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("P_SEQUENCE_NAME", "INV_CONTROL_NUMBER");
		Long id = call.executeFunction(Long.class, paramMap);
		
		invControlNumber = String.format("%010d", Integer.parseInt(id.toString()));
		return invControlNumber;
	}
	
	@Override
	public String getAdjustmentControlId() {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_ADJUSTMENT_CONTROL_NUMBER").withSchemaName("SELLER");
		return call.executeFunction(String.class);
	}
	
	@Override
	public String getMarketplaceId(@Param("locationCode") String locationCode) {
		String sql = "SELECT MARKETPLACE_ID FROM INVENTORY.INVENTORY_LOCATION WHERE LOCATION_CODE = '"+locationCode+"'";
	    return jdbcTemplate.queryForObject(sql, String.class);
	}
	
	@Override
	public String getReceivingPartyId(String locationCode) {
		String sql = "SELECT RECEIVING_PARTY_ID FROM INVENTORY.INVENTORY_LOCATION WHERE LOCATION_CODE = '"+locationCode+"'";
	    return jdbcTemplate.queryForObject(sql, String.class);
	}
	
	@Override
	public String getSendingPartyId(String locationCode) {
		String sql = "SELECT CONCAT(LOCATION_CODE, SENDING_PARTY_ID) FROM INVENTORY.INVENTORY_LOCATION WHERE LOCATION_CODE = '"+locationCode+"'";
	    return jdbcTemplate.queryForObject(sql, String.class);
	}
	
//	@Override
//	public String getVendorPartyId(String locationCode) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
