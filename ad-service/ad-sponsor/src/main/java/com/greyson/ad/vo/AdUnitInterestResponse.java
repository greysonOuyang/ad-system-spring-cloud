package com.greyson.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/8 11:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitInterestResponse {

    private List<Long> ids;
}
