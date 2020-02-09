package com.greyson.ad.utils;

import com.greyson.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author greyson
 * @time 2020/2/7 21:59
 */

public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdException {
        try {
            return DateUtils.parseDate(
                    dateString, parsePatterns
            );
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
