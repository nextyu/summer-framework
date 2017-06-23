package com.nextyu.summer.framework.util;

import com.nextyu.summer.framework.annotation.RequestMethod;

/**
 * created on 2017-06-23 13:44
 *
 * @author nextyu
 */
public class RequestMethodUtil {
    public static RequestMethod getRequestMethodEnum(String requestMethod) {
        switch (requestMethod) {
            case "GET":
            case "get":
                return RequestMethod.GET;

            case "HEAD":
            case "head":
                return RequestMethod.HEAD;

            case "POST":
            case "post":
                return RequestMethod.POST;

            case "PUT":
            case "put":
                return RequestMethod.PUT;

            case "PATCH":
            case "patch":
                return RequestMethod.PATCH;

            case "DELETE":
            case "delete":
                return RequestMethod.DELETE;

            case "OPTIONS":
            case "options":
                return RequestMethod.OPTIONS;

            case "TRACE":
            case "trace":
                return RequestMethod.TRACE;

            default:
                return RequestMethod.GET;
        }
    }
}
