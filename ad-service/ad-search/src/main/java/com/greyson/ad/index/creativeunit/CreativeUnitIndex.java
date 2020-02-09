package com.greyson.ad.index.creativeunit;

import com.greyson.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

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

    @Override
    public CreativeUnitObject get(String key) {
        return null;
    }

    @Override
    public void add(String key, CreativeUnitObject value) {

    }

    @Override
    public void update(String key, CreativeUnitObject value) {

    }

    @Override
    public void delete(String key, CreativeUnitObject value) {

    }
    // <adId-UnitId, creativeUnitObject>
}
