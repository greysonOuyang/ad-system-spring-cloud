package com.greyson.ad.search.vo;

import com.greyson.ad.search.vo.feature.DistrictFeature;
import com.greyson.ad.search.vo.feature.FeatureRelation;
import com.greyson.ad.search.vo.feature.ItFeature;
import com.greyson.ad.search.vo.feature.KeywordFeature;
import com.greyson.ad.search.vo.media.AdSlot;
import com.greyson.ad.search.vo.media.App;
import com.greyson.ad.search.vo.media.Device;
import com.greyson.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//广告位请求对象
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    // 媒体方请求标识
    private String mediaId;
    // 请求基本信息
    private RequestInfo reqestInfo;
    // 请求匹配信息     关键字、兴趣、地域
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {

        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }
    //匹配信息的
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        private FeatureRelation relation = FeatureRelation.AND;
    }
}
