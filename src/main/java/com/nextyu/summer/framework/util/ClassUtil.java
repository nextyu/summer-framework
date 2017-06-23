package com.nextyu.summer.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * created on 2017-06-23 11:27
 *
 * @author nextyu
 */
public class ClassUtil {
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    private ClassUtil() {
        throw new AssertionError();
    }

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized 是否初始化类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> aClass;
        try {
            aClass = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return aClass;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下的所有类
     * <p>
     * 根据报名并将其转换为文件路径，读取 class 文件或 jar 包，获取指定的类名去加载类
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (null != url) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classes, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (null != jarURLConnection) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (null != jarFile) {
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()) {
                                    JarEntry jarEntry = entries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (".class".equals(jarEntryName)) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classes, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("get class set failure", e);
            throw new RuntimeException(e);
        }

        return classes;
    }

    private static void addClass(Set<Class<?>> classes, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNoneEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classes, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNoneEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    addClass(classes, subPackagePath, subPackageName);
                }
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classes, String className) {
        Class<?> aClass = loadClass(className, false);
        classes.add(aClass);
    }

}
