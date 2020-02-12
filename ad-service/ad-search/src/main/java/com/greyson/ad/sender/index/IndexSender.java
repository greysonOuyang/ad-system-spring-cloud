package com.greyson.ad.sender.index;


import com.alibaba.fastjson.JSON;
import com.greyson.ad.DataLevel;
import com.greyson.ad.dump.table.*;
import com.greyson.ad.handler.AdLevelDataHandler;
import com.greyson.ad.mysql.constant.Constant;
import com.greyson.ad.mysql.dto.MySqlRowData;
import com.greyson.ad.sender.ISender;
import com.greyson.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**将增量数据转换成增量索引的过程：
 * 1.ISender接口传入参数MySqlRowDat rowData
 * 2.使用AdLevelHandler将表转换成Table对象，实现增量数据的加载，构建增量索引
 * 3.IndexSender()实现增量数据（增量索引）的投递
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {
    /**
     * 根据不同层级进行投递
     * @param mySqlRowData
     */
    @Override
    public void sender(MySqlRowData mySqlRowData) {
        String level = mySqlRowData.getLevel();

        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            Level2RowData(mySqlRowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            Level3RowData(mySqlRowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            Level4RowData(mySqlRowData);
        } else {
            log.error("MySqlRowData erroe :{}", JSON.toJSONString(mySqlRowData));
        }
    }

    private void Level2RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> adplanTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdPlanTable adPlanTable = new AdPlanTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            adPlanTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            adPlanTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            adPlanTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            adPlanTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            adPlanTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                adplanTables.add(adPlanTable);
            }
            adplanTables.forEach(p ->
                    AdLevelDataHandler.handleLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {

            List<AdCreativeTable> creativeTables = new ArrayList<>();

            for (Map<String, String> fieldValeMap : rowData.getFieldValueMap()) {

                AdCreativeTable creativeTable = new AdCreativeTable();

                fieldValeMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2(c, rowData.getOpType()));
        }
    }

    private void Level3RowData(MySqlRowData rowData) {

        if (rowData.getTableName().equals(
                Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)) {

            List<AdUnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {

                AdUnitTable unitTable = new AdUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });

                unitTables.add(unitTable);
            }
            unitTables.forEach(u -> AdLevelDataHandler.handleLevel3(u, rowData.getOpType()));

        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {

            List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(
                    u -> AdLevelDataHandler.handleLevel3(u, rowData.getOpType())
            );
        }
    }

    private void Level4RowData(MySqlRowData rowData) {

        switch (rowData.getTableName()) {

            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                List<AdUnitDistrictTable> districtTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {

                    AdUnitDistrictTable districtTable = new AdUnitDistrictTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                        }
                    });

                    districtTables.add(districtTable);
                }

                districtTables.forEach(d -> AdLevelDataHandler.handleLevel4(d, rowData.getOpType())
                );
                break;
            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                List<AdUnitInterestTable> itTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {

                    AdUnitInterestTable itTable = new AdUnitInterestTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                itTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                itTable.setInterestTag(v);
                                break;
                        }
                    });
                    itTables.add(itTable);
                }
                itTables.forEach(i -> AdLevelDataHandler.handleLevel4(i, rowData.getOpType())
                );
                break;
            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:

                List<AdUnitKeywordTable> keywordTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }

                keywordTables.forEach(k -> AdLevelDataHandler.handleLevel4(k, rowData.getOpType())
                );
                break;
        }
    }
}
