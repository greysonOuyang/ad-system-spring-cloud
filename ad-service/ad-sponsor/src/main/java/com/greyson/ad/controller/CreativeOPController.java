package com.greyson.ad.controller;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.exception.AdException;
import com.greyson.ad.service.ICreativeService;
import com.greyson.ad.vo.CreativeRequest;
import com.greyson.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author greyson
 * @time 2020/2/9 9:47
 */
@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse creativeResponse(
            @RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: createCreative -> {}",
                JSON.toJSONString(request));
        return creativeService.creatCreative(request);
    }
}
