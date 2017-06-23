package com.nextyu.summer.framework.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 * created on 2017-06-23 13:39
 *
 * @author nextyu
 */
public class CodecUtil {
    private static Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    private CodecUtil() {
        throw new AssertionError();
    }

    /**
     * 将 URL 编码
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * MD5 加密
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }
}
