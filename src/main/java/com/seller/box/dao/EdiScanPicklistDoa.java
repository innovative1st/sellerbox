package com.seller.box.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seller.box.entities.VirtualInventorySearch;
import com.seller.box.utils.SBConstant;

public interface EdiScanPicklistDoa extends JpaRepository<VirtualInventorySearch, Long>{
	@Query(value=SBConstant.VI_SEARCH_QUERY + " WHERE IL.LOCATION_CODE = :locationCode AND IL.ETAILOR_ID = :etailorId AND PC.SKU_CODE LIKE :skuCode ORDER BY IL.SKU_CODE ",
			countQuery=SBConstant.VI_COUNT_QUERY + " WHERE IL.LOCATION_CODE = :locationCode AND IL.ETAILOR_ID = :etailorId AND PC.SKU_CODE LIKE :skuCode",
			nativeQuery=true)
	List<VirtualInventorySearch> findAllPicklist(@Param("etailorId") Integer etailorId, @Param("locationCode") String locationCode, @Param("skuCode") String skuCode);
	
}
