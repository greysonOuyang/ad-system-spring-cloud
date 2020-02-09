package com.greyson.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/8 11:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitInterestRequest {

    private List<UnitInterest> unitInterests;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitInterest {

        private Long unitId;
        private String interestTag;
    }
}
