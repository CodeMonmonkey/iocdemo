package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文对象
 * 用于保存应用运行中的信息
 */
public class Context {

    /**
     * 根据bean名称存储Bean的Map对象
     */
    private Map<String, Object> nameBeanMap = new HashMap<>();

    /**
     * 根据bean的类型存储Bean的Map对象
     */
    private Map<Class, Object> typeBeanMap = new HashMap<>();

    /**
     * 根据bean的名称获取bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    public Object getBean(String beanName) {
        return nameBeanMap.get(beanName);
    }

    /**
     * 根据bean的类型获取bean
     */
    public Object getBean(Class clz) {
        return typeBeanMap.get(clz);
    }

    public void putBean(String beanName, Object bean) {
        nameBeanMap.put(beanName, bean);
    }

    public void putBean(Class beanType, Object bean) {
        typeBeanMap.put(beanType, bean);
    }
}
