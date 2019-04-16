package com.seller.box.utils;

public class SBConstant {
	public static final int DEFAULT_PAGE_SIZE = 100;
	public static final String VI_SEARCH_QUERY = "SELECT IL.IL_ID, IL.SKU_CODE, PC.EAN, PCD.ITEM_ID, PCD.FNSKU, PC.PRODUCT_NAME, PC.BRAND_NAME, IL.LOCATION_CODE, IL.ETAILOR_ID, IL.QUANTITY, IL.INVENTORY_STATUS, PCD.SALE_PRICE, PCD.SHIPPING_CHARGE, IL.INVENTORY_SOURCE_LOCATION, IL.ITEM_LOCATION, PC.THUMBNAIL_URL, IFNULL(PC.IS_SALEBLE, 0) AS IS_SALEBLE, IFNULL(PCD.IS_ACTIVE, 0) AS IS_ACTIVE, IFNULL(PC.AUTO_SYNC_INVENTORY, 0) AS AUTO_SYNC_INVENTORY FROM INVENTORY.EDI_INVENTORY_LEVEL IL LEFT JOIN CATALOGUE.PRODUCT_CATALOGUE PC ON IL.SKU_CODE = PC.SKU_CODE LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON IL.SKU_CODE = PCD.SKU_CODE AND IL.ETAILOR_ID = PCD.ERETAILOR_ID ";
	public static final String VI_COUNT_QUERY  = "SELECT COUNT(1) FROM INVENTORY.EDI_INVENTORY_LEVEL IL LEFT JOIN CATALOGUE.PRODUCT_CATALOGUE PC ON IL.SKU_CODE = PC.SKU_CODE LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON IL.SKU_CODE = PCD.SKU_CODE AND IL.ETAILOR_ID = PCD.ERETAILOR_ID ";
	
	public static final String PICKLIST_SEARCH_QUERY = "SELECT PICKLIST_ID, PICKLIST_NUMBER, WAREHOUSE_CODE, ERETAILOR_ID, CREATED_ON, CREATED_BY, ASSIGNED_TO, EXSD_AFTER, EXSD_BEFORE, PICKUP_DATE, IS_FASTTRACK, IS_GIFT_MESSAGE, IS_GIFTWRAPE, SINGLE_MULTI_TYPE, FULFILMENT_TYPE, NO_OF_TOTAL_ORDER, NO_OF_PACKED_ORDER, NO_OF_CANCELLED_ORDER, NO_OF_SIDELINE_ORDER, STATUS, IS_ASN, ASN_STATUS, IS_NOTIFY FROM SELLER.EDI_PICKLIST ";
	public static final String PICKLIST_COUNT_QUERY  = "SELECT COUNT(1) FROM SELLER.EDI_PICKLIST ";

	public static final String PICKLIST_STATUS_QUERY= "SELECT PICKLIST_ID, PICKLIST_NUMBER, WAREHOUSE_CODE, ERETAILOR_ID, TOTAL_ORDER_COUNT, PACKED_ORDER_COUNT, CANCEL_ORDER_COUNT, STATUS FROM SELLER.PICKLIST_STATUS ";
	
	public static final String NEW_ORDER_SEARCH_QUERY= "SELECT EDI_ORDER_ID, ERETAILOR_ID, PURCHASE_ORDER_NUMBER, CUSTOMER_ORDER_NUMBER, BILL_TO_ENTITY_ID, WAREHOUSE_CODE, ORDERE_DATE, EXPECTED_SHIP_DATE, ORDER_RECEIVE_DATE, BOX_TYPE, IS_GIFT, IS_GIFT_WRAP, IS_PRIORITY_SHIPMENT, IS_COD, PAYMENT_TYPE, SHIP_CHARGE_AMOUNT, ORDER_TOTAL, BALANCE_DUE, ORDER_SITE_ID, BATCH_ID, ORDER_STATUS, NO_OF_ITEMS, FULFILMENT_TYPE, LINE_ITEM_SEQ, ITEM_ID, SKU_CODE, EAN, PRODUCT_NAME, QUANTITY, QNT_FROM_INV, QNT_FROM_VIR, BAY_NO, RACK_NO, THUMBNAIL_URL, IS_CANCELED, IS_ITEM_UPLOAD, REF_ORDER_ID, IS_OMS_UPLOAD, OMS_UPLOAD_ERROR_MSG FROM SELLER.SHIPMENT_FOR_PICKLIST ";
	public static final String NEW_ORDER_COUNT_QUERY = "SELECT COUNT(1) FROM SELLER.SHIPMENT_FOR_PICKLIST ";
	
	public static final String READY_TO_SHIP_SEARCH_QUERY = "SELECT EDI_ORDER_ID, ERETAILOR_ID, WAREHOUSE_CODE, PURCHASE_ORDER_NUMBER, CUSTOMER_ORDER_NUMBER, PICKLIST_NUMBER, TRACKING_ID, CARRIER_NAME, PICKUP_DATE, EXPECTED_SHIP_DATE, ORDER_STATUS FROM SELLER.EDI_SHIPMENT_HDR ";
	public static final String READY_TO_SHIP_COUNT_QUERY  = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_HDR ";
	
	public static final String PICKLIST_FOR_ALL 		= "ALL";
	public static final String PICKLIST_FOR_TODAY 		= "TODAY";
	public static final String PICKLIST_FOR_YESTERDAY 	= "YESTERDAY";
	public static final String PICKLIST_FOR_LAST7DAYS 	= "LAST7DAYS";

	public static final String PICKUP_DATE_ALL 			= "ALL";
	public static final String PICKUP_DATE_TODAY 		= "TODAY";
	public static final String PICKUP_DATE_YESTERDAY 	= "YESTERDAY";
	public static final String PICKUP_DATE_LAST7DAYS 	= "LAST7DAYS";
	public static final String PICKUP_DATE_OTHER 		= "OTHER";
	
	public static final String PICKLIST_STATUS_ACTIVE	= "ACTIVE";
	public static final String PICKLIST_STATUS_COMPLETED= "COMPLETED";
	public static final String PICKLIST_STATUS_INVALID  = "INVALID";
	public static final String PICKLIST_STATUS_ALL	 	= "ALL";
	
	public static final String PICKLIST_FASTTRACK_TYPE_YES	= "Y";
	public static final String PICKLIST_FASTTRACK_TYPE_NO	= "N";
	public static final String PICKLIST_FASTTRACK_TYPE_BOTH	= "B";
	
	public static final String PICKLIST_SINGLE_MULTY_TYPE_SINGLE = "S";
	public static final String PICKLIST_SINGLE_MULTY_TYPE_MULTI  = "M";
	public static final String PICKLIST_SINGLE_MULTY_TYPE_BOTH   = "B";
	
	public static final String PICKLIST_FULFILMENT_TYPE_ACTUAL = "A";
	public static final String PICKLIST_FULFILMENT_TYPE_VIRTUAL= "V";
	public static final String PICKLIST_FULFILMENT_TYPE_BOTH   = "B";

	public static final String PICKLIST_GIFTLABEL_TYPE_YES 	= "Y";
	public static final String PICKLIST_GIFTLABEL_TYPE_NO 	= "N";
	public static final String PICKLIST_GIFTLABEL_TYPE_BOTH	= "B";

	public static final String PICKLIST_GIFTWRAP_TYPE_YES 	= "Y";
	public static final String PICKLIST_GIFTWRAP_TYPE_NO 	= "N";
	public static final String PICKLIST_GIFTWRAP_TYPE_BOTH	= "B";

	public static final String PICKLIST_HAZMAT_TYPE_YES 	= "Y";
	public static final String PICKLIST_HAZMAT_TYPE_NO 		= "N";
	
	public static final String PICKLIST_LIQUID_CONTAINS_TYPE_YES 	= "Y";
	public static final String PICKLIST_LIQUID_CONTAINS_TYPE_NO		= "N";
	
	
	public static final String TXN_STATUS_SUCCESS		= "SUCCESS";
	public static final String TXN_STATUS_EXCEPTION		= "EXCEPTION";
	public static final String TXN_STATUS_DUPLICATE		= "DUPLICATE";
	public static final String TXN_STATUS_FAILURE		= "FAILURE";
	public static final String TXN_STATUS_NO_DATA		= "NO_DATA_FOUND";
	public static final String TXN_STATUS_PART_SUCCESS  = "PART_SUCCESS";
	public static final String TXN_STATUS_WARNING   	= "WARN";
	public static final String TXN_STATUS_INIT  		= "INIT";

	public static final int TXN_RESPONSE_CODE_SUCCESS		= 0;
	public static final int TXN_RESPONSE_CODE_EXCEPTION		= 1;
	public static final int TXN_RESPONSE_CODE_DUPLICATE		= 2;
	public static final int TXN_RESPONSE_CODE_FAILURE		= 3;
	public static final int TXN_RESPONSE_CODE_NO_DATA		= 4;
	public static final int TXN_RESPONSE_CODE_PART_SUCCESS  = 5;
	public static final int TXN_RESPONSE_CODE_WARNING   	= 6;
	public static final int TXN_RESPONSE_CODE_INIT  		= 7;
	
	
	public static final String TXN_RESPONSE_STATUS			= "STATUS";
	public static final String TXN_RESPONSE_REQUEST_ID		= "REQUEST_ID";
	public static final String TXN_RESPONSE_PICKLIST_ID		= "PICKLIST_ID";
	public static final String TXN_RESPONSE_PICKLIST_NUMBER	= "PICKLIST_NUMBER";

	
	public static final String MESSAGE_TYPE_IAN 	= "IAN";
	public static final String MESSAGE_TYPE_ILN 	= "ILN";
	public static final String MESSAGE_TYPE_OFR 	= "OFR";
	public static final String MESSAGE_TYPE_ASN 	= "ASN";
	public static final String MESSAGE_TYPE_OCR 	= "OCR";
	public static final String MESSAGE_TYPE_CRN 	= "CRN";
	public static final String MESSAGE_TYPE_OC  	= "OC";
	public static final String MESSAGE_TYPE_OF  	= "OF";
	public static final String MESSAGE_TYPE_INV  	= "INV";
	public static final String MESSAGE_TYPE_PICKLIST= "PICKLIST";
	
	public static final String IAN_ADUSTMENT_TYPE_FOUND = "FOUND";
	public static final String IAN_ADUSTMENT_TYPE_LOST  = "LOST";
	
	
	public static final String IAN_LOCATION_TYPE_PRIME 		  		= "PRIME";
	public static final String IAN_LOCATION_TYPE_UNAVAILABLE  		= "UNAVAILABLE";
	public static final String IAN_LOCATION_TYPE_CUSTOMER_RETURN  	= "CUSTOMER-RETURN";
	public static final String IAN_LOCATION_TYPE_PENDING_CANCELLED  = "PENDING-CANCELLED";
	
	
	
	
	
	
	public static enum ErrorType {
	    CLIENT_ERROR,  SERVICE_ERROR,  UNKNOWN, DATA_NOT_FOUND;
	}
	public static final String ERROR_CODE_CLIENT_ERROR		= "101";
	public static final String ERROR_CODE_SERVICE_ERROR		= "102";
	public static final String ERROR_CODE_UNKNOWN			= "103";
	public static final String ERROR_CODE_DATA_NOT_FOUND	= "0";
	
	
	public static final String TXN_PARAM_STATUS		= "status";

	public static final String VAR_ETAILOR_ID		= "etailorId";
	public static final String VAR_REQUEST_ID		= "requestId";
	public static final String VAR_LOCATION_CODE	= "locationCode";
	public static final String VAR_WAREHOUSE_CODE	= "warehouseCode";
	public static final String VAR_EDI_ORDER_ID		= "ediOrderId";
	public static final String VAR_PICKLIST_FOR		= "picklistFor";
	public static final String VAR_PICKLIST_ID		= "picklistId";
	public static final String VAR_PICKLIST_NUMBER	= "picklistNumber";
	public static final String VAR_PICKLIST_STATUS	= "picklistStatus";
	public static final String VAR_PICKUP_DATE		= "pickupDate";
	public static final String VAR_PICKUP_OTHER_DATE= "pickupOtherDate";
	public static final String VAR_CARRIER_NAME		= "carrierName";
	public static final String VAR_ORDER_IDS		= "orderIds";
	public static final String VAR_BATCH_SIZE		= "batchSize";
	public static final String VAR_USERNAME			= "username";
	public static final String VAR_SCANED_VALUE		= "scanedValue";
	public static final String VAR_SKU_CODE			= "skuCode";
	public static final String VAR_EAN 				= "ean";
	
	public static final String LOG_SEPRATOR 		    = "---> ";
	
	
}
