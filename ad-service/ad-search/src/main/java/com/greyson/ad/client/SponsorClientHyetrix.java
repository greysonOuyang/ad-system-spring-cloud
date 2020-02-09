package com.greyson.ad.client;

import com.greyson.ad.client.vo.AdPlan;
import com.greyson.ad.client.vo.AdPlanGetRequest;
import com.greyson.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/9 16:22
 */
@Component
public class SponsorClientHyetrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
