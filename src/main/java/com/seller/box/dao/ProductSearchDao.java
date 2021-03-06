package com.seller.box.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.ProductDetails;

@Repository
public interface ProductSearchDao extends JpaRepository<ProductDetails, Long>{

	@Query(value="SELECT PC.PRODUCT_ID, PC.SKU_CODE, PC.EAN, PCD.ITEM_ID, PCD.FNSKU, PC.PRODUCT_NAME, PC.THUMBNAIL_URL, IFNULL(PC.IS_SALEBLE, 0) AS IS_SALEBLE, IFNULL(PCD.IS_ACTIVE, 0) AS IS_ACTIVE, IFNULL(PC.AUTO_SYNC_INVENTORY, 0) AS AUTO_SYNC_INVENTORY FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE (PC.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value)", 
			countQuery = "SELECT COUNT(1) FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE (PC.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value)",
			nativeQuery=true)
	ProductDetails findProduct(@Param("eretailId") int eretailId, @Param("value") String value);
	
	@Query(value="SELECT PC.PRODUCT_ID, PC.SKU_CODE, PC.EAN, PCD.ITEM_ID, PCD.FNSKU, PC.PRODUCT_NAME, PC.THUMBNAIL_URL, IFNULL(PC.IS_SALEBLE, 0) AS IS_SALEBLE, IFNULL(PCD.IS_ACTIVE, 0) AS IS_ACTIVE, IFNULL(PC.AUTO_SYNC_INVENTORY, 0) AS AUTO_SYNC_INVENTORY FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE PC.SKU_CODE = :sku", 
			countQuery = "SELECT COUNT(1) FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE PC.SKU_CODE = :sku",
			nativeQuery=true)
	
	ProductDetails findProductBySku(@Param("eretailId") int eretailId, @Param("sku") String sku);
	
	@Query(value="SELECT PC.PRODUCT_ID, PC.SKU_CODE, PC.EAN, PCD.ITEM_ID, PCD.FNSKU, PC.PRODUCT_NAME, PC.THUMBNAIL_URL, IFNULL(PC.IS_SALEBLE, 0) AS IS_SALEBLE, IFNULL(PCD.IS_ACTIVE, 0) AS IS_ACTIVE, IFNULL(PC.AUTO_SYNC_INVENTORY, 0) AS AUTO_SYNC_INVENTORY FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE PC.EAN = :ean", 
			countQuery = "SELECT COUNT(1) FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE PC.EAN = :ean",
			nativeQuery=true)
	ProductDetails findProductByEan(@Param("eretailId") int eretailId, @Param("ean") String ean);
}
