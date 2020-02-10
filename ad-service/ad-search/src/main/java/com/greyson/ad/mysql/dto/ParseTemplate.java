package com.greyson.ad.mysql.dto;

import com.greyson.ad.mysql.constant.OpType;
import lombok.Data;
import org.apache.commons.collections4.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author greyson
 * @time 2020/2/10 17:19
 */
@Data
public class ParseTemplate {
    private String database;
    private Map<String, TableTemplate> tableTemplateMap = new HashedMap<>();

    public static ParseTemplate parse(Template template) {
        ParseTemplate template1 = new ParseTemplate();
        template1.setDatabase(template.getDatabase());

        for (JsonTable table : template.getTableList()) {
            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setLevel(level.toString());
            template1.tableTemplateMap.put(name, tableTemplate);
            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap =
                    tableTemplate.getOpTypeFilterMap();
             for (JsonTable.Column column : table.getInsert()) {
                 getAndCreateIfNeed(
                         OpType.ADD,
                         opTypeFieldSetMap,
                         ArrayList::new
                 ).add(column.getColumn());
             }
             for (JsonTable.Column column : table.getInsert()) {
                 getAndCreateIfNeed(
                         OpType.UPDATE,
                         opTypeFieldSetMap,
                         ArrayList::new
                 ).add(column.getColumn());
             }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template1;
    }
    private static <T, R> R getAndCreateIfNeed(T key,
                                       Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
