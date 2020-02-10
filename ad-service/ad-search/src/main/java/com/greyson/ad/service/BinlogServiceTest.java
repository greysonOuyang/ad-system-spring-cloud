package com.greyson.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * @author greyson
 * @time 2020/2/10 16:08
 */

public class BinlogServiceTest {
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "HotteMYSQL"
        );
        client.setBinlogFilename("binlog.0001");
        client.registerEventListener(event -> {
            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update--------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write---------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete----------");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
