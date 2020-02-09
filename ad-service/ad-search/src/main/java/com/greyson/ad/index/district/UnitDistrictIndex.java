package com.greyson.ad.index.district;

import com.greyson.ad.index.IndexAware;
import com.greyson.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author greyson
 * @time 2020/2/9 21:29
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> districUnitMap;

    // <unitId, province-city set>
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before add: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districUnitMap, ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> interests = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            interests.add(key);
        }

        log.info("");
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("interest index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before delete: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districUnitMap, ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> interestTagSet = CommonUtils.getorCreate(
                    unitId, unitDistrictMap, ConcurrentSkipListSet::new
            );
            interestTagSet.remove(key);
        }
        log.info("UnitDistrictIndex, after delete: {}", unitDistrictMap);
    }

    public boolean match(Long unitId, List<String> interestTags) {
        if (unitDistrictMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitDistrictMap.get(unitId))) {
            Set<String> unitKeywords = unitDistrictMap.get(unitId);
            return CollectionUtils.isSubCollection(interestTags, unitKeywords);
        }
        return false;
    }
}
