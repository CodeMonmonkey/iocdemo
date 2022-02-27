package com.example.demo.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

/**
 * 类工具
 */
public class ClassUtils {

    /**
     * 获取某包下的所有类
     * （将包名转为实际的路径，扫描这个路径下的所有class文件，获取到一个包含所有class名的集合。
     * 还有一个点是，java的类名与实际的class文件名是一致的。所以可以根据实际的文件，获取到类的名字）
     *
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName, boolean isRecursion) {
        Set<String> classNames = new HashSet<>();
        String packagePath = packageName.replace(".", "/");
        //类加载
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(packagePath);
        //地址路径
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (filePath != null) {
            classNames = getClassNameFromDir(filePath, packageName, isRecursion);
        }


        return classNames;

    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath    文件路径
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称（带包名）
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
        Set<String> className = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), true));
                }
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }
        return className;
    }
}
