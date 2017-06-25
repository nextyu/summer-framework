package com.nextyu.summer.framework.proxy;

/**
 * 代理接口
 * created on 2017-06-23 16:58
 *
 * @author nextyu
 */
public interface Proxy {
    /**
     * 执行链式代理
     * 所谓链式代理，也就是将多个代理通过一条链子串起来，一个个地去执行，执行顺序取决于添加到链上的先后顺序
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
