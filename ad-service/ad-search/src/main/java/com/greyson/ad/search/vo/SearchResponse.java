package com.greyson.ad.search.vo;

import com.greyson.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回给媒体方的广告创意
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative {

        public Long adId;
        public String adUrl;
        public Integer width;
        public Integer height;
        public Integer type;
        public Integer materialType;

        //展示监测url  广告位曝光此创意
        public List<String> showMonitorUrl = Arrays.asList("guyanliang.club", "guyanliang.club");
        //点击监测url  用户点击此创意
        public List<String> clickMonitorUrl = Arrays.asList("guyanliang.club", "guyanliang.club");
    }
    //对象转换
    public static Creative convert(CreativeObject creativeObject) {
        Creative creative = new Creative();
        creative.setAdId(creativeObject.getAdId());
        creative.setAdUrl(creativeObject.getAdUrl());
        creative.setWidth(creativeObject.getWidth());
        creative.setHeight(creativeObject.getHeight());
        creative.setType(creativeObject.getType());
        creative.setMaterialType(creativeObject.getMaterialtype());
        return creative;
    }
}
