package com.greyson.ad.mysql.dto;

import com.greyson.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//对外投递的对象，  BinlogRowData对象相对复杂
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {
    private String tableName;
    private String level;   // 数据表的层级关系
    private OpType opType;  // 自己定义的操作数据类型
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
