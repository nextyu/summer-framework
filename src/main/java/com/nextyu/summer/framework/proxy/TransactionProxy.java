package com.nextyu.summer.framework.proxy;

import com.nextyu.summer.framework.annotation.Transaction;
import com.nextyu.summer.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 2017-06-25 下午4:06
 *
 * @author nextyu
 */
public class TransactionProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * 标记，保证同一线程中事务控制相关逻辑只会执行一次
     */
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);

            try {
                DatabaseHelper.beginTransaction();
                logger.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                logger.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                logger.debug("rollback transaction");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }

        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
