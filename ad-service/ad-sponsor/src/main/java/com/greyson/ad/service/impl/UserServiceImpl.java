package com.greyson.ad.service.impl;

import com.greyson.ad.constant.Constants;
import com.greyson.ad.dao.AdUserRepository;
import com.greyson.ad.entity.AdUser;
import com.greyson.ad.exception.AdException;
import com.greyson.ad.service.IUserService;
import com.greyson.ad.utils.CommonUtils;
import com.greyson.ad.vo.CreateUserRequest;
import com.greyson.ad.vo.CreateUserResponse;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author greyson
 * @time 2020/2/7 21:41
 */

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {


        if (!request.validate()) {  // 验证传入参数是否正确
             throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = userRepository.findByUsername(request.getUsername());


        if (oldUser != null) {  // 判断当前系统是否有重名用户
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(), CommonUtils.md5(request.getUsername())
        ));
            return new CreateUserResponse(
                    newUser.getId(), newUser.getUsername(), newUser.getToken(),
                    newUser.getCreateTime(), newUser.getUpdateTime()
            );
    }
}
