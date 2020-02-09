package com.greyson.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author greyson
 * @time 2020/2/9 17:32
 */

public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
