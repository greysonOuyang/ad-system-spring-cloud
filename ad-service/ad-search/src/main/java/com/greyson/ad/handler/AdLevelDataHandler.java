package com.greyson.ad.handler;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.dump.table.*;
import com.greyson.ad.index.DataTable;
import com.greyson.ad.index.IndexAware;
import com.greyson.ad.index.adplan.AdPlanIndex;
import com.greyson.ad.index.adplan.AdPlanObject;
import com.greyson.ad.index.adunit.AdUnitIndex;
import com.greyson.ad.index.adunit.AdUnitObject;
import com.greyson.ad.index.creative.CreativeIndex;
import com.greyson.ad.index.creative.CreativeObject;
import com.greyson.ad.index.creativeunit.CreativeUnitIndex;
import com.greyson.ad.index.creativeunit.CreativeUnitObject;
import com.greyson.ad.index.district.UnitDistrictIndex;
import com.greyson.ad.index.interest.UnitInterestIndex;
import com.greyson.ad.mysql.constant.OpType;
import com.greyson.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 索引之间存在着层级之间的划分，也就是依赖关系的划分
 * 加载全量索引其实是增量索引 “添加”的是一种特殊实现
 * @author greyson
 * @time 2020/2/10 10:57
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType type) {
        AdPlanObject adPlanObject = DataTable.of(
                AdPlanIndex.class
        ).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("handleLevel3 found AdPlanObject error: {}",
                unitTable.getPlanId());
            return;
        }
        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );
        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitTable.getUnitId(),
                unitObject,
                type
        );
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable,
                                    OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeIndex not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(creativeUnitTable.getUnidId());
        CreativeObject creativeObject = DataTable.of(
                CreativeIndex.class
        ).get(creativeUnitTable.getAdId());

        if (null == unitObject || null == creativeObject) {
            log.error("AdCreativeUnitTable Index error: {}",
                    JSON.toJSONString(creativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnidId()
        );
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable,
                                    OpType type) {
        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitDistrictTable.getUnitId());
        if (unitObject == null ) {
            log.error("AdUnitDistrictTable index error: {}",
                    unitDistrictTable.getUnitId());
            return;
        }
        String key = CommonUtils.stringConcat(
                unitDistrictTable.getProvince(),
                unitDistrictTable.getCity()
        );
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key, value, type
        );
    }

    public static void handleLevel4(AdUnitInterestTable unitInterestTable,
                                    OpType type) {
        if (type == OpType.UPDATE) {
            log.error("interest index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitInterestTable.getUnitId());
        if (unitObject == null ) {
            log.error("AdUnitInterestTable index error: {}",
                    unitInterestTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitInterestTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitInterestIndex.class),
                unitInterestTable.getInterestTag(), value, type
        );
    }

    public static void handleLevel4(AdUnitKeywordTable keywordTable,
                                    OpType type) {
        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(keywordTable.getUnitId());

        if (unitObject == null ) {
            log.error("AdUnitInterestTable index error: {}",
                    keywordTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(keywordTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitInterestIndex.class),
                keywordTable.getKeyword(), value, type
        );
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index,
                                                 K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
