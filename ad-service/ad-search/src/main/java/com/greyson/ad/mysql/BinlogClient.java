package com.greyson.ad.mysql;


import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.greyson.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author greyson
 * @time 2020/2/12 9:16
 */

@Slf4j
@Component
public class BinlogClient {
    private final BinlogConfig config;
    private final AggregationListener aggregationListener;

    private BinaryLogClient client;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener aggregationListener) {
        this.config = config;
        this.aggregationListener = aggregationListener;
    }

    /**
     *  实现监听的启动
     */
    public void connet() {
        System.err.println("Binlog配置信息检查");
        System.err.println(JSON.toJSONString(config));
        System.err.println("Binlog配置信息检查");
        new Thread(() -> {
            // 完成MySQL的配置
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) &&
                    !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                // 制定具体位置开始监听
                client.setBinlogPosition(config.getPosition());
            }
            log.info("connection to mysql start");
            //注册监听器， 将Event对象转变为 BinlogRowData
            client.registerEventListener(aggregationListener);
            log.info("connection to mysql done");
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

