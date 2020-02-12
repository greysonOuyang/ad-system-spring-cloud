package com.greyson.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author greyson
 * @time 2020/2/12 9:20
 */
@Data
public class BinlogRowData {

    private TableTemplate tableTemplate;

    private EventType eventType;

    private List<Map<String, String>> after;

    private List<Map<String, String>> before;
}
