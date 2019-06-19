package com.seller.box.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.VirtualInventorySearch;
import com.seller.box.utils.SBConstant;

@Repository
public interface VirtualInventorySearchDao extends JpaRepository<VirtualInventorySearch, Long> {
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode AND IL.ETAILOR_ID = :etailorId ORDER BY IL.SKU_CODE ",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode AND IL.ETAILOR_ID = :etailorId",
			nativeQuery=true)
	List<VirtualInventorySearch> findAllPagable(@Param("etailorId") Integer etailorId, @Param("warehouseCode") String warehouseCode);
	
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode AND IL.ETAILOR_ID = :etailorId AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value) ORDER BY IL.SKU_CODE ",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode AND IL.ETAILOR_ID = :etailorId AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value)",
			nativeQuery=true)
	List<VirtualInventorySearch> findAllWithSkuPagable(@Param("etailorId") Integer etailorId, @Param("warehouseCode") String warehouseCode, @Param("value") String value);
	
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.ETAILOR_ID = :etailorId",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE IL.ETAILOR_ID = :etailorId",
			nativeQuery=true)
	List<VirtualInventorySearch> findByEtailorId(int etailorId);

	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE IL.LOCATION_CODE = :warehouseCode",
			nativeQuery=true)
	List<VirtualInventorySearch> findByLocationCode(String warehouseCode);

	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE PC.SKU_CODE = :skuCode",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE PC.SKU_CODE = :skuCode",
			nativeQuery=true)
	List<VirtualInventorySearch> findBySkuCode(String skuCode);
	
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value) ORDER BY IL.SKU_CODE ",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value) ORDER BY IL.SKU_CODE ",
			nativeQuery=true)
	List<VirtualInventorySearch> findAllInventoryOnBasicSearch(@Param("value") String value);
	
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.ETAILOR_ID = :etailorId AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value) ORDER BY IL.SKU_CODE ",
			countQuery=SBConstant.VI_COUNT_QUERY +	" WHERE IL.ETAILOR_ID = :etailorId AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PCD.ITEM_ID = :value OR PCD.FNSKU = :value) ORDER BY IL.SKU_CODE ",
			nativeQuery=true)
	List<VirtualInventorySearch> findAllInventoryOnBasicSearchWithEtailor(@Param("etailorId") int etailorId, @Param("value") String value);

	
	@Query(value="SELECT IFNULL(SUM(QUANTITY), 0) FROM INVENTORY.EDI_INVENTORY_LEVEL WHERE ETAILOR_ID = :etailorId AND LOCATION_CODE = :warehouseCode AND SKU_CODE = :skuCode",
		   //countQuery="SELECT COUNT(1) FROM INVENTORY.INVENTORY_LEVEL IL, CATALOGUE.PRODUCT_CATALOGUE PC WHERE IL.SKU_CODE = PC.SKU_CODE AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PC.ASIN = :value OR PC.FNSKU = :value)",
		   nativeQuery=true)
	int getAvailableStock(@Param("etailorId") int etailorId, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);
	
	@Query(value="SELECT IFNULL(SUM(QUANTITY), 0) FROM INVENTORY.EDI_INVENTORY_IAN WHERE ETAILOR_ID = :etailorId AND LOCATION_CODE = :warehouseCode AND SKU_CODE = :skuCode AND ADJUSTMENT_TYPE = 'LOST' AND IS_INV_UPDATED = 0",
			   //countQuery="SELECT COUNT(1) FROM INVENTORY.INVENTORY_LEVEL IL, CATALOGUE.PRODUCT_CATALOGUE PC WHERE IL.SKU_CODE = PC.SKU_CODE AND (IL.SKU_CODE = :value OR PC.EAN = :value OR PC.ASIN = :value OR PC.FNSKU = :value)",
			   nativeQuery=true)
	int getQueueQuantityForLost(@Param("etailorId") int etailorId, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

}
