package com.example.demo;

import lombok.Data;

/**
 * Bean的信息
 */
@Data
public class BeanInfo {

    /**
     * Bean的类型
     */
    private Class clz;

    /**
     * 保存在IOC容器中的bean名称
     */
    private String beanName;

    /**
     * 保存在IOC容器中的bean类型
     */
    private Class beanType;

    /**
     * 保存在IOC容器中的bean对象实例
     */
    private Object bean;

    /**
     * 保存在IOC容器中的bean代理对象实例
     */
    private Object proxyBean;

}
