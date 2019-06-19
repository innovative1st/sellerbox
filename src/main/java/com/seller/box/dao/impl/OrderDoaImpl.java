package com.seller.box.dao.impl;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiPicklistDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.OrderDao;
import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.exception.SellerServiceException;
import com.seller.box.form.PicklistSearchForm;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Repository
public class OrderDoaImpl implements OrderDao {
	@Autowired
	EdiConfigDao configDao;
	@Autowired
	EdiPicklistDao picklistDao;
	@Autowired
	EdiShipmentDao ediShipmentHdrDao;
	@PersistenceContext
    EntityManager em;

	@Override
	public List<EdiPicklist> findReadyToPick(String etailorId, String warehouseCode, String picklistFor, String picklistStatus) {
		StringBuffer query = new StringBuffer(SBConstant.PICKLIST_SEARCH_QUERY);
		query.append("WHERE WAREHOUSE_CODE = '").append(warehouseCode).append("'");
		if(!etailorId.equalsIgnoreCase(SBConstant.DATA_FOR_ALL)) {
			try {
				int etailId = Integer.parseInt(etailorId);
				query.append(" AND ERETAILOR_ID = ").append(etailId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(picklistFor.equalsIgnoreCase(SBConstant.PICKLIST_FOR_ALL)) {
			//not applicable
		} else if(picklistFor.equalsIgnoreCase(SBConstant.PICKLIST_FOR_TODAY)) {
			query.append(" AND DATE_FORMAT(CREATED_ON, '%Y%m%d')").append("=").append("DATE_FORMAT(NOW(), '%Y%m%d')");//For Today
		} else if(picklistFor.equalsIgnoreCase(SBConstant.PICKLIST_FOR_YESTERDAY)) {
			query.append(" AND DATE_FORMAT(CREATED_ON, '%Y%m%d')").append("=").append("DATE_FORMAT(NOW() - INTERVAL 1 DAY, '%Y%m%d')");//For Yesterday
		} else if(picklistFor.equalsIgnoreCase(SBConstant.PICKLIST_FOR_LAST7DAYS)) {
			query.append(" AND DATE_FORMAT(CREATED_ON, '%Y%m%d')").append(">=").append("DATE_FORMAT(NOW() - INTERVAL 7 DAY, '%Y%m%d')");//For Last 7 days
		}
		
		if(!picklistStatus.equalsIgnoreCase(SBConstant.PICKLIST_STATUS_ALL)) {
			//if(picklistStatus.equalsIgnoreCase(SBConstant.PICKLIST_STATUS_ACTIVE) || picklistStatus.equalsIgnoreCase(SBConstant.PICKLIST_STATUS_COMPLETED)) {
			query.append(" AND STATUS ='").append(picklistStatus).append("'");
			//}
		}

		
		Query q = em.createNativeQuery(query.toString(), EdiPicklist.class);
		
		@SuppressWarnings("unchecked")
		List<EdiPicklist> picklist = q.getResultList();
		return picklist;
	}

	@Override
	public List<ReadyToShip> findReadyToShip(String etailorId, String warehouseCode, String pickupDate, String pickupOtherDate, String carrierName) {
		StringBuffer query = new StringBuffer(SBConstant.READY_TO_SHIP_SEARCH_QUERY);
		String readyToShipStatus = SBUtils.getPropertyValue("sb.ready.to.ship.status");
		query.append("WHERE ORDER_STATUS IN ("+readyToShipStatus+")");
		query.append(" AND WAREHOUSE_CODE = '").append(warehouseCode).append("'");
		if(!etailorId.equalsIgnoreCase(SBConstant.DATA_FOR_ALL)) {
			try {
				int etailId = Integer.parseInt(etailorId);
				query.append(" AND ERETAILOR_ID = ").append(etailId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		if(pickupDate.equalsIgnoreCase(SBConstant.PICKUP_DATE_ALL)) {
			//not applicable
		} else if(pickupDate.equalsIgnoreCase(SBConstant.PICKUP_DATE_TODAY)) {
			query.append(" AND DATE_FORMAT(EXPECTED_SHIP_DATE, '%d-%m-%Y')").append("=").append("DATE_FORMAT(NOW(), '%d-%m-%Y')");//For Today
		} else if(pickupDate.equalsIgnoreCase(SBConstant.PICKUP_DATE_YESTERDAY)) {
			query.append(" AND DATE_FORMAT(EXPECTED_SHIP_DATE, '%d-%m-%Y')").append("=").append("DATE_FORMAT(NOW() - INTERVAL 1 DAY, '%d-%m-%Y')");//For Yesterday
		} else if(pickupDate.equalsIgnoreCase(SBConstant.PICKUP_DATE_LAST7DAYS)) {
			query.append(" AND DATE_FORMAT(EXPECTED_SHIP_DATE, '%d-%m-%Y')").append(" BETWEEN DATE_FORMAT(NOW(), '%d-%m-%Y') AND ").append("DATE_FORMAT(NOW() - INTERVAL 7 DAY, '%d-%m-%Y')");//For Last 7 days
		} else if(pickupDate.equalsIgnoreCase(SBConstant.PICKUP_DATE_OTHER)) {
			if(SBUtils.isNotNull(pickupOtherDate)) { 
				query.append(" AND DATE_FORMAT(EXPECTED_SHIP_DATE, '%d-%m-%Y')").append(" = '").append(pickupOtherDate).append("'");//For Other date formated value dd-MM-yyyy
			}
		}
		
		if(SBUtils.isNotNull(carrierName)) {
			if (!carrierName.equalsIgnoreCase("ALL")) {
				query.append(" AND CARRIER_NAME ='").append(carrierName).append("'");
			}
		}
		
		Query q = em.createNativeQuery(query.toString(), ReadyToShip.class);

		@SuppressWarnings("unchecked")
		List<ReadyToShip> readyShipment = q.getResultList();
		return readyShipment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShipmentHdrWithItem> findNewShipments(PicklistSearchForm criteria) {
		List<ShipmentHdrWithItem> newShipments = null;
		try {
			StringBuffer query = new StringBuffer(SBConstant.NEW_ORDER_SEARCH_QUERY);
			query.append("WHERE WAREHOUSE_CODE = '").append(criteria.getWarehouseCode()).append("'");
			query.append(" AND ERETAILOR_ID = ").append(criteria.getEtailorId());
			query.append(" AND DATE_FORMAT(EXPECTED_SHIP_DATE, '%d-%m-%Y %k:%i:%s') BETWEEN '").append(criteria.getExSDAfter()).append("' AND '").append(criteria.getExSDBefore()).append("'");
			
			if(SBUtils.isNotNull(criteria.getSingleMultiType())) {
				if(criteria.getSingleMultiType().equalsIgnoreCase(SBConstant.PICKLIST_SINGLE_MULTY_TYPE_SINGLE)) {
					query.append(" AND NO_OF_ITEMS = 1");
					if (SBUtils.isNotNull(criteria.getFulfilmentType())) {
						if ((criteria.getFulfilmentType()) .equalsIgnoreCase(SBConstant.PICKLIST_FULFILMENT_TYPE_ACTUAL)) {
							query.append(" AND FULFILMENT_TYPE = 'A'");
						} else if ((criteria.getFulfilmentType()).equalsIgnoreCase(SBConstant.PICKLIST_FULFILMENT_TYPE_VIRTUAL)) {
							query.append(" AND FULFILMENT_TYPE = 'V'");
						}
					}
					if (SBUtils.isNotNull(criteria.getFnsku())) {
						query.append(" AND FNSKU = '").append(criteria.getFnsku()).append("'");
					}
				} else if((criteria.getSingleMultiType()).equalsIgnoreCase(SBConstant.PICKLIST_SINGLE_MULTY_TYPE_MULTI)) {
					query.append(" AND NO_OF_ITEMS > 1");
				}
			}
			if(SBUtils.isNotNull(criteria.getFastTrackType())) {
				if(criteria.getFastTrackType().equalsIgnoreCase(SBConstant.PICKLIST_FASTTRACK_TYPE_YES)) {
					query.append(" AND IS_PRIORITY_SHIPMENT = 1");
				} else if(criteria.getFastTrackType().equalsIgnoreCase(SBConstant.PICKLIST_FASTTRACK_TYPE_NO)) {
					query.append(" AND IS_PRIORITY_SHIPMENT = 0");
				}
			}
			if(SBUtils.isNotNull(criteria.getGiftLabelType())) {
				if(criteria.getGiftLabelType().equalsIgnoreCase(SBConstant.PICKLIST_GIFTLABEL_TYPE_YES)) {
					query.append(" AND IS_GIFT = 1");
				} else if(criteria.getGiftLabelType().equalsIgnoreCase(SBConstant.PICKLIST_GIFTLABEL_TYPE_NO)) {
					query.append(" AND IS_GIFT = 0");
				}
			}
			if(SBUtils.isNotNull(criteria.getGiftWrapType())) {
				if(criteria.getGiftWrapType().equalsIgnoreCase(SBConstant.PICKLIST_GIFTWRAP_TYPE_YES)) {
					query.append(" AND IS_GIFT_WRAP = 1");
				} else if(criteria.getGiftWrapType().equalsIgnoreCase(SBConstant.PICKLIST_GIFTWRAP_TYPE_NO)) {
					query.append(" AND IS_GIFT_WRAP = 0");
				}
			}
			Query q = em.createNativeQuery(query.toString(), ShipmentHdrWithItem.class);

			newShipments = q.getResultList();
		} catch (Exception e) {
			throw new SellerServiceException(HttpStatus.INTERNAL_SERVER_ERROR.name()+" : Seller Service Exception : Unable to fetch data based on provided criteria.", e);
		}
		return newShipments;
	}

	@Override
	@Transactional
	public Map<String, String> createPicklist(PicklistSearchForm criteria) {
		Map<String, String> response = new HashMap<String, String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		response.put(SBConstant.TXN_RESPONSE_REQUEST_ID, UUID.randomUUID().toString());
		response.put(SBConstant.TXN_RESPONSE_STATUS, SBConstant.TXN_STATUS_INIT);
		EdiPicklist picklist = new EdiPicklist();
		picklist.setPicklistId(configDao.getId(SBConstant.MESSAGE_TYPE_PICKLIST));
		picklist.setPicklistNumber("EDI"+String.format("%010d", Integer.parseInt(picklist.getPicklistId().toString())));
		picklist.setWarehouseCode(criteria.getWarehouseCode());
		picklist.setEtailorId(criteria.getEtailorId());
		picklist.setCreatedDate(new Date());
		picklist.setCreatedBy(criteria.getUsername());
		try {
			if (criteria.getExSDAfter() != null) {
				picklist.setExsdAfter(formatter.parse(criteria.getExSDAfter()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (criteria.getExSDBefore() != null) {
				picklist.setExsdBefore(formatter.parse(criteria.getExSDBefore()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (criteria.getPickupDate() != null) {
				picklist.setPickupDate(formatter.parse(criteria.getPickupDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picklist.setIsFasttrack(criteria.getFastTrackType());
		picklist.setIsGiftMessage(criteria.getGiftLabelType());
		picklist.setIsGiftWrape(criteria.getGiftWrapType());
		picklist.setSingleMultiType(criteria.getSingleMultiType());
		picklist.setFulfilmentType(criteria.getFulfilmentType());
		picklist.setNoOfTotalOrder(criteria.getBatchSize());
		picklist.setNoOfPackedOrder(0);
		picklist.setNoOfCancelledOrder(0);
		picklist.setStatus(SBConstant.PICKLIST_STATUS_ACTIVE);
		
		/**Update picklistNumber in EDI_SHIPMENT_HDR table against EDI_ORDER_ID**/
		StringBuffer query = new StringBuffer("UPDATE SELLER.EDI_SHIPMENT_HDR ");
		query.append("SET PICKLIST_NUMBER = '").append(picklist.getPicklistNumber()).append("' ");
		query.append("WHERE EDI_ORDER_ID IN (").append(criteria.getOrderIds()).append(")");
		Query sql = em.createNativeQuery(query.toString());
		
		int count = sql.executeUpdate();
		if(count == criteria.getBatchSize()) {
			response.put(SBConstant.TXN_RESPONSE_STATUS, SBConstant.TXN_STATUS_SUCCESS);
		} else {
			picklist.setNoOfTotalOrder(count);
			response.put(SBConstant.TXN_RESPONSE_STATUS, SBConstant.TXN_STATUS_PART_SUCCESS);
		}
		if(count > 0) {
			picklist = picklistDao.save(picklist);
			response.put(SBConstant.TXN_RESPONSE_PICKLIST_ID, picklist.getPicklistId().toString());
			response.put(SBConstant.TXN_RESPONSE_PICKLIST_NUMBER, picklist.getPicklistNumber());
		} else {
			response.put(SBConstant.TXN_RESPONSE_STATUS, SBConstant.TXN_STATUS_FAILURE);
		}
		return response;
	}

	@Override
	@Transactional
	public Map<String, Object> findPicklistStatus(String etailorId, String warehouseCode, String scanedValue) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		StringBuffer query = new StringBuffer(SBConstant.PICKLIST_STATUS_QUERY);
		query.append("WHERE WAREHOUSE_CODE = '").append(warehouseCode).append("'");
		
		if(!etailorId.equalsIgnoreCase(SBConstant.DATA_FOR_ALL)) {
			try {
				int etailId = Integer.parseInt(etailorId);
				query.append(" AND ERETAILOR_ID = ").append(etailId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		query.append(" AND (PICKLIST_ID = '").append(scanedValue).append("' OR PICKLIST_NUMBER = '").append(scanedValue).append("')");
		Query sql = em.createNativeQuery(query.toString());
		List<?> result = sql.getResultList();
		if(!result.isEmpty()) {
			if(result.size() > 0) {
				Object[] rows = (Object[]) result.get(0);
				response.put("picklistId", rows[0]);
				response.put("picklistNumber", rows[1]);
				response.put("warehouseCode", rows[2]);
				response.put("etailorId", rows[3]);
				response.put("total", rows[4]);
				response.put("packed", rows[5]);
				response.put("canceled", rows[6]);
				response.put("status", rows[7]);
			}
		}
		
		if(response.size() > 0) {
			boolean isUpdateRequired = true;
			if(response.get("total").equals(BigInteger.ZERO)) {
				response.put("status", SBConstant.PICKLIST_STATUS_0_ORDERS);
				isUpdateRequired = true;
			} else {
				BigInteger completed = (BigInteger) response.get("packed");
				completed.add((BigInteger) response.get("canceled"));
				if(response.get("total").equals(completed)) {
					if(!response.get("status").equals(SBConstant.PICKLIST_STATUS_COMPLETED)) {
						response.put("status", SBConstant.PICKLIST_STATUS_COMPLETED);
						isUpdateRequired = true;
					}
				}
			}
			
			if(isUpdateRequired) {
				EdiPicklist pl = new EdiPicklist();
				pl.setPicklistId(((BigInteger)response.get("picklistId")).longValue());
				pl.setPicklistNumber((String)response.get("picklistNumber"));
				pl.setWarehouseCode((String)response.get("warehouseCode"));
				pl.setEtailorId((int)response.get("etailorId"));
				pl.setNoOfTotalOrder(((BigInteger)response.get("total")).intValue());
				pl.setNoOfPackedOrder(((BigInteger)response.get("packed")).intValue());
				pl.setNoOfCancelledOrder(((BigInteger)response.get("canceled")).intValue());
				//pl.setNoOfSidelineOrder((int)response.get("sideline"));
				pl.setStatus((String)response.get("status"));
				picklistDao.save(pl);
				/***
				StringBuffer update = new StringBuffer("UPDATE SELLER.EDI_PICKLIST ");
				update.append("SET STATUS = '").append(response.get("status")).append("' ");
				update.append("WHERE PICKLIST_ID = '").append(scanedValue).append("' OR PICKLIST_NUMBER = '").append(scanedValue).append("'");
				sql = em.createNativeQuery(update.toString());
				sql.executeUpdate();
				***/
			}
		} else {
			response.put("picklistId", "");
			response.put("picklistNumber", "");
			response.put("warehouseCode", warehouseCode);
			response.put("etailorId", etailorId);
			response.put("total", 0);
			response.put("packed", 0);
			response.put("canceled", 0);
			response.put("status", SBConstant.PICKLIST_STATUS_INVALID);
		}
		return response;
	}

	@Override
	public Long getEdiOrderIdForPacking(int etailorId, String warehouseCode, String picklistNumber, String ean) {
		Long ediOrderId = 0L;
		try {
			StringBuffer query = new StringBuffer("SELECT EDI_ORDER_ID FROM SELLER.EDI_SCAN_EAN ");
			query.append("WHERE WAREHOUSE_CODE = '").append(warehouseCode).append("'");
			query.append(" AND ERETAILOR_ID = ").append(etailorId);
			query.append(" AND PICKLIST_NUMBER = '").append(picklistNumber).append("'");
			query.append(" AND EAN = '").append(ean).append("' ORDER BY EDI_ORDER_ID LIMIT 0, 1");
			Query sql = em.createNativeQuery(query.toString());
			List<?> result = sql.getResultList();
			if(!result.isEmpty()) {
				if(result.size() > 0) {
					ediOrderId = ((BigInteger) result.get(0)).longValue();
					//Object[] rows = (Object[]) result.get(0);
					//ediOrderId = (Long) rows[0];
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ediOrderId;
	}

	@Override
	public String getInvoiceFilepath(String shipmentId) {
		String invoceFilepath = null;
		try {
			StringBuffer query = new StringBuffer("SELECT INVOICE_FILE_PATH FROM SELLER.EDI_SHIPMENT_INVOICE ");
			query.append("WHERE PURCHASE_ORDER_NUMBER = '").append(shipmentId).append("'");
			Query sql = em.createNativeQuery(query.toString());
			List<?> result = sql.getResultList();
			if(!result.isEmpty()) {
				if(result.size() > 0) {
					Object[] rows = (Object[]) result.get(0);
					invoceFilepath = (String) rows[0];
				}
			} else {
				if(SBUtils.isNull(invoceFilepath)) {
					invoceFilepath = this.getInvoiceFilepathFromSource(shipmentId);
				}
			}
		} catch (Exception e) {
			invoceFilepath = this.getInvoiceFilepathFromSource(shipmentId);
		}
		return invoceFilepath;
	}

    private String getInvoiceFilepathFromSource(String shipmentId) {
        String invFilepath 		= null;
        String absInvFilepath 	= null;
        try {
            invFilepath = SBUtils.getPropertyValue("seller.edi.invoice.path");
            if (!invFilepath.endsWith("\\")) {
                invFilepath = invFilepath + "\\";
            }
            boolean isAvailable = false;
            File invoice = new File(invFilepath);
            if (invoice.exists()) {
                File[] invoiceList = invoice.listFiles();
                for (int i = 0; i < invoiceList.length; i++) {
                    if (invoiceList[i].getName().startsWith(shipmentId)) {
                        isAvailable = true;
                        invFilepath = invFilepath + invoiceList[i].getName();
                        break;
                    }
                }
                if (!isAvailable) {
                    invFilepath = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if(SBUtils.isNotNull(invFilepath)) {
        		absInvFilepath = invFilepath;
        	}
        }
        return absInvFilepath;
    }
}
