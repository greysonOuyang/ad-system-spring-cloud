package com.greyson.ad.index.creativeunit;

import com.greyson.ad.index.IndexAware;
import com.greyson.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author greyson
 * @time 2020/2/9 21:55
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {
    // <adId-unitId, CreativeUnitObject>
    private static Map<String, CreativeUnitObject> objectMap;
    // <adId, unitId Set>
    private static Map<Long, Set<Long>> creativeUnitMap;
    // <unitId, adId set>
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("before add: {}", objectMap);

        objectMap.put(key, value);

        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), creativeSet);
        }
        creativeSet.add(value.getUnitId());

        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before delete: {}", objectMap);

        objectMap.remove(key);

        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.remove(value.getAdId());
        }

        Set<Long> creativeSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.remove(value.getAdId());
        }
    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects) {
        if (CollectionUtils.isEmpty(unitObjects)) return Collections.emptyList();
        List<Long> result = new ArrayList<>();
        unitObjects.forEach(x->{
            Set<Long> longs = unitCreativeMap.get(x.getUnitId());
            if (CollectionUtils.isNotEmpty(longs)) {
                result.addAll(longs);
            }
        });
        return result;
    }
}
