package com.greyson.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//请求基本信息 -- 地理位置
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    //维度
    private Float latitude;
    //经度
    private Float longitude;
    //城市
    private String city;
    //省份
    private String province;
}
