package com.greyson.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.mysql.dto.MySqlRowData;
import com.greyson.ad.sender.ISender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String,String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(
                topic,"ad_search_binlog_sender", JSON.toJSONString(rowData)
        );
    }

    /**
     * 监听topic消费binlog
     */
/*    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            System.out.println("kafka processMysqlRowData: " +
                    JSON.toJSONString(rowData));
        }
    }*/
}
