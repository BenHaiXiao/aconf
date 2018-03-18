package com.github.bh.aconf.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author houzhenjing on 11/25/15.
 */
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return (T) context.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
