package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. 使用@interface关键词定义注解
 * 2. @Retention表示该类如何保留。即注解类的声明周期。RetentionPolicy为枚举类，
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface MyBean {

    /**
     * 待存入IOC容器的bean名称
     *
     * @return Bean名称
     */
    String value() default "";
}
