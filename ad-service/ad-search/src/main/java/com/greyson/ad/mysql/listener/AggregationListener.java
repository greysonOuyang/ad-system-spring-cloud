package com.greyson.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.greyson.ad.mysql.TemplateHolder;
import com.greyson.ad.mysql.dto.BinlogRowData;
import com.greyson.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    // 对应到数据库、表
    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    //为所有可能使用增量数据的表建立监听器，实现特定onevent方法
    public void register(String _dbName, String _tableName, IListener iListener) {
        log.info("register: {}-{}", _dbName, _tableName);
        this.listenerMap.put(_dbName + ":" + _tableName, iListener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();
        log.debug("evnet type :{}", eventType);

        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }
        // 不是以下类型不处理 增量更新只对应到数据的写入更新删除才需要更新
        if (eventType != EventType.EXT_UPDATE_ROWS
                && eventType != EventType.EXT_DELETE_ROWS
                && eventType != EventType.EXT_WRITE_ROWS) {
            return;
        }

        // 判断表名、数据库名是否已经填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        IListener listener = this.listenerMap.get(this.dbName + ":" + this.tableName);
        if (null == listener) {
            log.debug("skip {}", this.dbName + ":" + this.tableName);
            return;
        }
        log.info("trigger event : {}", eventType.name());
        try {
            BinlogRowData binlogRowData = buildRowData(event.getData());
            if (binlogRowData == null) {
                return;
            }
            binlogRowData.setEventType(eventType);
            listener.OnEvent(binlogRowData);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = null;
            this.tableName = null;
        }

    }

    /**
     * 将EventData转换为BinlogData
     * @param eventData
     * @return rowData
     */
    private BinlogRowData buildRowData(EventData eventData) {
        //1.取得序号-列名表 ， 即json解析表
        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table) {
            log.warn("table {} not found", tableName);
            return null;
        }

        //2.填充bimlogRowData中after属性
        List<Map<String, String>> afterMapList = new ArrayList<>();
        //getAfterValues(eventData) = [6, 10, 奔驰],[7, 10, 测试]  转换成  Map<String, String>
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int collen = after.length;

            //这个循环 感觉有问题， 应该是去遍历includedColumns 数组的
            // 1.PosMap是 1 = 序号，2= 名字，3=年龄。。。的吗，中间可能丢失吗？？
            // 2.collen只是after数组的大小，includedColumns={0，1，2...}  可能是{0，3，5...}??
            for (int i = 0; i < collen; ++i) {
                //取出序号对应列名
                String colName = table.getPosMap().get(i);
                //如果json中没有定义， 则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position : {}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTableTemplate(table);
        return rowData;
    }

    //1.由于EventData 整个对象复杂，我们仅需要row部分，并且row部分分为list的值还有map对象。
//2。将所有的row值 ，转变为list对象中的值。 list = { 2, 10, 测试}  其他信息不要
    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }
}
