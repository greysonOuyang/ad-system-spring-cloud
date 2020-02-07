package com.greyson.ad.service;

import com.greyson.ad.exception.AdException;
import com.greyson.ad.vo.CreateUserRequest;
import com.greyson.ad.vo.CreateUserResponse;

public interface IUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
