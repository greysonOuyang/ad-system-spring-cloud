package com.greyson.ad.controller;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.annotation.IgnoreReponseAdvice;
import com.greyson.ad.client.SponsorClient;
import com.greyson.ad.client.vo.AdPlan;
import com.greyson.ad.client.vo.AdPlanGetRequest;
import com.greyson.ad.search.ISearch;
import com.greyson.ad.search.vo.SearchRequest;
import com.greyson.ad.search.vo.SearchResponse;
import com.greyson.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/9 16:02
 */
@Slf4j
@RestController
public class SearchController {

    private final ISearch search;

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(ISearch search, RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.search = search;
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}",
                JSON.toJSONString(request));
        return search.fetchAds(request);
    }

    @IgnoreReponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}",
                JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreReponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(
            @RequestBody AdPlanGetRequest request
            ) {
        log.info("ad-search: getAdPlansByRibbon -> {}",
                JSON.toJSONString(request));
        return restTemplate.postForEntity(
          "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,CommonResponse.class
        ).getBody();
    }
}
