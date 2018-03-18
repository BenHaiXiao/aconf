package com.github.bh.aconf.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BeanUtils {
    /**
     * 将一个 JavaBean 对象转化为一个  Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map<String, Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String, Object> returnMap = Maps.newHashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    if (result instanceof Date) {
                        returnMap.put(propertyName, ((Date) result).getTime());
                    } else {
                        returnMap.put(propertyName, result);
                    }
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    public static List<Map<String, Object>> batchConvertBean(List beans) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<Map<String, Object>> result = Lists.newArrayList();
        for (Object bean : beans) {
            Map<String, Object> map = convertBean(bean);
            result.add(map);
        }
        return result;
    }

    /**
     * 将Bean平铺在Map中
     * Bean中的Map将全部展开
     *
     * @param bean
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> tileOpenBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (bean instanceof Map) {
            return (Map<String, Object>) bean;
        }
        Class type = bean.getClass();
        Map<String, Object> returnMap = Maps.newHashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    if (result instanceof Map) {
                        returnMap.putAll((Map<? extends String, ?>) result);
                    } else {
                        returnMap.put(propertyName, result);
                    }
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}  
