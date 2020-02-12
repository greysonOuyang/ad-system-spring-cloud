package com.greyson.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {
    private List<ProvinceAndCity> districtFeature ;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {
        private String province;
        private String city;
    }

}
