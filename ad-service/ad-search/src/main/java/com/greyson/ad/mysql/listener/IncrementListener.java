package com.greyson.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.greyson.ad.mysql.constant.Constant;
import com.greyson.ad.mysql.constant.OpType;
import com.greyson.ad.mysql.dto.BinlogRowData;
import com.greyson.ad.mysql.dto.MySqlRowData;
import com.greyson.ad.mysql.dto.TableTemplate;
import com.greyson.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource(name = "indexSender")     // 根据name选择注入
    private ISender sender;
    @Resource(name = "kafkaSender")
    private ISender senderKafka;    //kafka投递，方便后续业务

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    /**
     * 实现注册，把所有表注册给同一个Listener即IncrementListener，实现标量数据的投递功能；
     * 该方法在IncrementListener实例化（放入Sparing容器中）时运行，开始注册
     */
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db table info");
        // k：表名，V：数据库名，传入当前Listener
        Constant.table2Db.forEach((k, v)-> aggregationListener.register(v,k,this));
    }

    /**
     * OnEvent()将BinlogRowData转换成MySqlRowData，将binlogRowData投递出去
     * @param binlogRowData
     */
    @Override
    public void OnEvent(BinlogRowData binlogRowData) {
        TableTemplate tableTemplate = binlogRowData.getTableTemplate();
        EventType eventType = binlogRowData.getEventType();
        // 包装成最后需要填充的数据
        MySqlRowData mySqlRowData = new MySqlRowData();
        // 填充表名、层级
        mySqlRowData.setTableName(tableTemplate.getTableName());
        mySqlRowData.setLevel(tableTemplate.getLevel());

        OpType type = OpType.to(eventType);
        mySqlRowData.setOpType(type);

        //取出json模板对应的字段列表， 根据OpType 类型 可使用的列名
        List<String> fieldList = tableTemplate.getOpTypeFieldSetMap().get(type);
        if (null == fieldList) {    // 空则不处理
            log.warn("{} not support for {}" , type, tableTemplate.getTableName());
            return;
        }
        //因为MySqlRowData已经生成map对象 ， 所以遍历写入
        for (Map<String, String> afterMap : binlogRowData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
//           for (Map.Entry<String, String> entry : afterMap.entrySet()) {
//                String colName = entry.getKey();
//                String colValue = entry.getValue();
//                _afterMap.put(colName, colValue);
//            }
            afterMap.entrySet().forEach(x -> _afterMap.put(x.getKey(),x.getValue()));
            mySqlRowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(mySqlRowData);
        senderKafka.sender(mySqlRowData);//kafka投递，方便后续业务
    }
}
