package com.greyson.ad.dao;

import com.greyson.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/7 21:11
 */

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {
    AdPlan findByIdAndUserId(Long id, Long userId);
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);
}
