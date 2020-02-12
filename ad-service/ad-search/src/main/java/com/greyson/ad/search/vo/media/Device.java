package com.greyson.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//请求基本信息 -  设备
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    // 设备 id
    private String deviceCode;

    // mac
    private String mac;

    // ip
    private String ip;

    // 机型编码
    private String model;

    // 分辨率尺寸
    private String displaySize;

    // 屏幕尺寸
    private String screenSize;

    // 设备序列号
    private String serialName;
}


