package com.greyson.ad.dao;

import com.greyson.ad.entity.AdUnit;
import com.greyson.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreativeRepository extends JpaRepository<Creative, Long> {
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
