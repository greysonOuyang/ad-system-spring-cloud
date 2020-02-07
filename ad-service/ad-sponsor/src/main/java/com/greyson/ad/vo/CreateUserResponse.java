package com.greyson.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author greyson
 * @time 2020/2/7 21:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private Long userId;
    private String userName;
    private String token;
    private Date createTime;
    private Date updateTime;
}
