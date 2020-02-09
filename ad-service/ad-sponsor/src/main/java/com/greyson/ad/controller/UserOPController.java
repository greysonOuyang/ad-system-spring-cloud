package com.greyson.ad.controller;

import com.alibaba.fastjson.JSON;
import com.greyson.ad.exception.AdException;
import com.greyson.ad.service.IUserService;
import com.greyson.ad.vo.CreateUserRequest;
import com.greyson.ad.vo.CreateUserResponse;
import com.greyson.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author greyson
 * @time 2020/2/8 14:49
 */
@Slf4j
@RestController
public class UserOPController {

    private final IUserService userService;

    @Autowired
    public UserOPController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor:createUser: createUser -> {}",
                JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
