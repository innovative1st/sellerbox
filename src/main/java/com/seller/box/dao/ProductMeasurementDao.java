package com.seller.box.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seller.box.entities.ProductMeasurements;

public interface ProductMeasurementDao extends JpaRepository<ProductMeasurements, Long>{
	@Query(value="SELECT PC.PRODUCT_ID, PC.SKU_CODE, PC.EAN, PCD.ITEM_ID, PCD.FNSKU, PC.PRODUCT_NAME, PC.THUMBNAIL_URL, IFNULL(PC.HEIGHT, 0) AS HEIGHT, IFNULL(PC.LENGTH, 0) AS LENGTH, IFNULL(PC.WIDTH, 0) AS WIDTH, IFNULL(PC.WEIGHT, 0) AS WEIGHT FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE (PC.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value)", 
			countQuery = "SELECT COUNT(1) FROM CATALOGUE.PRODUCT_CATALOGUE PC LEFT JOIN CATALOGUE.PRODUCT_CHANNEL_DETAILS PCD ON PC.SKU_CODE = PCD.SKU_CODE AND PCD.ERETAILOR_ID = :eretailId WHERE (PC.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value)",
			nativeQuery=true)
	ProductMeasurements findProductByItemId(@Param("eretailId") int eretailId, @Param("value") String value);
}