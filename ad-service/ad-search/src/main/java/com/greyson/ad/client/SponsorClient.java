package com.greyson.ad.client;

import com.greyson.ad.client.vo.AdPlan;
import com.greyson.ad.client.vo.AdPlanGetRequest;
import com.greyson.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/9 16:13
 */
@FeignClient(value = "eureka-client-ad-sponsor")
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(
            @RequestBody AdPlanGetRequest request);
}
