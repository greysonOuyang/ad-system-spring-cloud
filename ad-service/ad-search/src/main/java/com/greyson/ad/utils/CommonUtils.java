package com.greyson.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author greyson
 * @time 2020/2/9 17:32
 */
@Slf4j
public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringContact(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static Date parseStringDate(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm: ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8
            );
        } catch (ParseException e) {
            log.error("parseStringDate Error: {}");
            return null;
        }
    }
}
