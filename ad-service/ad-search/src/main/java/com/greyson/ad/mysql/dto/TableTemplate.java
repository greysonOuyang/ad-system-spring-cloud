package com.greyson.ad.mysql.dto;

import com.greyson.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;

import java.util.List;
import java.util.Map;

/**
 * @author greyson
 * @time 2020/2/10 17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    private Map<OpType, List<String>> opTypeFilterMap = new HashedMap<>();

    // 字段索引 -> 字段名
    private Map<Integer, String> posMap = new HashedMap<>();
}
