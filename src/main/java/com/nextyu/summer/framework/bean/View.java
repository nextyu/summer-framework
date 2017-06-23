package com.nextyu.summer.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * created on 2017-06-23 13:27
 *
 * @author nextyu
 */
public class View {
    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
