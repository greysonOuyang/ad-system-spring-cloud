package com.greyson.ad.index;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.dump.DConstant;
import com.greyson.ad.dump.table.*;
import com.greyson.ad.handler.AdLevelDataHandler;
import com.greyson.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 根据数据表导出的文件加载索引
 * @author greyson
 * @time 2020/2/10 15:30
 */
@Component
@DependsOn("DataTable")
public class IndexFileLoader {

    /**
     * 根据数据表导出的文件加载索引
     * 1. 实现一个文件IndexFileLoader
     * 2. 写一个LoadDumpData方法,此方法通过给定的文件名将数据表里的数据全部读入进来，构成一个List<String><
     * 3. init()方法按照层级（层级顺序不能乱，有可能存在依赖）对每个文件实现加载依次遍历，遍历过程中实现反序列化并实现add操作
     */

    private void init() {
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PlAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpType.ADD
        ));

        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdUnitTable.class),
                OpType.ADD
        ));

        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdCreativeUnitTable.class),
                OpType.ADD
        ));

        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_District)
        );
        adUnitDistrictStrings.forEach(d -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(d, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_Keyword)
        );
        adUnitKeywordStrings.forEach(k -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(k, AdUnitKeywordTable.class),
                OpType.ADD
        ));

        List<String> adUnitInterestStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_Interest)
        );
        adUnitInterestStrings.forEach(i -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(i, AdUnitInterestTable.class),
                OpType.ADD
        ));
    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
