package com.greyson.ad.index.interest;

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
 * @time 2020/2/9 20:21
 */
@Slf4j
@Component
public class UnitInterestIndex implements IndexAware<String, Set<Long>> {

    // <interestTag, adUnitId set>
    private static Map<String, Set<Long>> interestUnitMap;

    // <unitId, interestTag set
    private static Map<Long, Set<String>> unitInterestMap;

    static {
        interestUnitMap = new ConcurrentHashMap<>();
        unitInterestMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return interestUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitInterestIndex, before add: {}", unitInterestMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, interestUnitMap, ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> interests = CommonUtils.getorCreate(
                    unitId, unitInterestMap,
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
        log.info("InterestTagIndex, before delete: {}", unitInterestMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, interestUnitMap, ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> interestTagSet = CommonUtils.getorCreate(
                    unitId, unitInterestMap, ConcurrentSkipListSet::new
            );
            interestTagSet.remove(key);
        }
        log.info("InterestTagIndex, after delete: {}", unitInterestMap);
    }

    public boolean match(Long unitId, List<String> interestTags) {
        if (unitInterestMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitInterestMap.get(unitId))) {
            Set<String> unitKeywords = unitInterestMap.get(unitId);
            return CollectionUtils.isSubCollection(interestTags, unitKeywords);
        }
        return false;
    }
}








