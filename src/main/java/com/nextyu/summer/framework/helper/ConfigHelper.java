package com.nextyu.summer.framework.helper;

import com.nextyu.summer.framework.constant.ConfigConstant;
import com.nextyu.summer.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 * created on 2017-06-23 11:35
 *
 * @author nextyu
 */
public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    private ConfigHelper() {
        throw new AssertionError();
    }

    /**
     * 获取 JDBC 驱动
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取 JDBC URL
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取 JDBC 用户名
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取 JDBC 密码
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用 view 后缀
     */
    public static String getAppViewSuffix() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_VIEW_SUFFIX, ".jsp");
    }

    /**
     * 获取应用 view 路径
     */
    public static String getAppViewPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_VIEW_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用静态资源路径
     */
    public static String getAppStaticPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_STATIC_PATH, "/static/");
    }
}
