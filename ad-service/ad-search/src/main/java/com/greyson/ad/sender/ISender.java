package com.greyson.ad.sender;


import com.greyson.ad.mysql.dto.MySqlRowData;

//将MySqlRowData对象投递出去 的接口
public interface ISender {
    void sender(MySqlRowData mySqlRowData);
}
