package com.greyson.ad.index;

import com.greyson.ad.index.creativeunit.CreativeUnitIndex;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author greyson
 * @time 2020/2/10 8:41
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> dataTableMap =
            new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;

    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {
        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }
        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }
}
