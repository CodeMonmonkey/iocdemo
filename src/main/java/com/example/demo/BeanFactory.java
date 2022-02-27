package com.example.demo;

import com.example.demo.annotation.MyBean;
import com.example.demo.utils.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * bean工厂
 */
public class BeanFactory {

    /**
     * 基础包路径
     */
    private String basePackage;

    /**
     * 上下文对象
     */
    private Context context = new Context();

    /**
     * 构造方法
     */
    public BeanFactory(String basePackage) {
        this.basePackage = basePackage;
        init();
    }

    /**
     * 初始化方法。首先拿到所有bean（打了MyBean注解的类），然后给bean注入其依赖的bean
     */
    private void init() {
        //扫描包和加载bean到IOC容器
        List<BeanInfo> beanInfoList = scanPackageAndLoadBeans();
        //给bean注入依赖对象
        injectBeans(beanInfoList);

    }

    /**
     * 扫描包和加载bean到IOC容器
     *
     * @return
     */
    private List<BeanInfo> scanPackageAndLoadBeans() {

        List<BeanInfo> myBeanList = new ArrayList<>();

        //根据包名递归地找到所有类名
        Set<String> classNames = ClassUtils.getClassName(basePackage, true);
        for (String className : classNames) {
            try {
                //根据类名获取到类
                Class<?> clz = Class.forName(className);
                //判断类上是否有MyBean注解
                if (clz.isAnnotationPresent(MyBean.class)) {
                    //如果有MyBean注解，获取MyBean注解
                    MyBean myBeanAnnotation = clz.getAnnotation(MyBean.class);
                    //获取注解上的value
                    String beanName = myBeanAnnotation.value();
                    //获取类继承的相关接口
                    Class<?>[] interfaces = clz.getInterfaces();
                    //判断类是否可以采用JDK动态代理（有接口方可进行动态代理，创建代理对象？）
                    boolean canJdkProxyBean = interfaces != null && interfaces.length > 0;

                    //获取待注入IOC容器的bean类型
                    Class beanType = getBeanType(clz, canJdkProxyBean);


                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return myBeanList;
    }

    /**
     * 给相关bean注入依赖的bean
     *
     * @param myBeanList
     */
    private void injectBeans(List<BeanInfo> myBeanList) {

    }

    private Class getBeanType(Class clz, boolean canJdkProxyBean) {
        Class beanType = null;
        if (canJdkProxyBean) {
            //如果可以使用JDK动态代理，则bean类型取bean的接口类型
            beanType = clz.getInterfaces()[0];
        } else {
            beanType = clz;
        }
        return beanType;
    }

    /**
     * 根据bean名称获取bean对象
     *
     * @param beanName bean名称
     * @param <T>      bean类型
     * @return IOC容器中的bean
     */
    public <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    /**
     * 根据bean类型获取bean对象
     *
     * @param clz 注入到IOC容器中的bean类型
     * @param <T> bean类型
     * @return IOC容器中的bean
     */
    public <T> T getBean(Class clz) {
        return (T) context.getBean(clz);
    }

    /**
     * 创建代理bean
     *
     * @param bean 当前bean对象
     * @return Bean的代理对象
     */
    private Object creatBeanProxy(Object bean) {
        BeanProxy beanProxy = new BeanProxy(bean);
        Object proxyBean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), beanProxy);
        return proxyBean;

    }


}
