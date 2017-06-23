package com.nextyu.summer.framework.bean;

import com.nextyu.summer.framework.annotation.RequestMethod;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 封装请求信息
 * created on 2017-06-23 13:02
 *
 * @author nextyu
 */
public class Request {
    /**
     * 请求方法
     */
    private RequestMethod[] requestMethod;
    /**
     * 请求路径
     */
    private String requestPath;



    public Request() {
    }

    public Request(RequestMethod[] requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public RequestMethod[] getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod[] requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
