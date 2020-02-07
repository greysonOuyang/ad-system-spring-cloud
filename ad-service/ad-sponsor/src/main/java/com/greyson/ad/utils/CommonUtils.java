package com.greyson.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author greyson
 * @time 2020/2/7 21:59
 */

public class CommonUtils {
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
