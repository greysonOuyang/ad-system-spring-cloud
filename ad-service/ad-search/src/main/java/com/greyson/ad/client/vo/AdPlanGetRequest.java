package com.greyson.ad.client.vo;

import lombok.Data;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/9 14:26
 */
@Data
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;
}
