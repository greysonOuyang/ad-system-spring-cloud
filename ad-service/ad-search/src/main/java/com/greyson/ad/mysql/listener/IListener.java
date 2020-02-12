package com.greyson.ad.mysql.listener;

import com.greyson.ad.mysql.dto.BinlogRowData;

public interface IListener {
    public void register();

    public void OnEvent(BinlogRowData binlogRowData);

}
