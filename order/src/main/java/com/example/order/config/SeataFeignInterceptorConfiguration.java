package com.example.order.config;

import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.shardingsphere.transaction.base.seata.at.SeataTransactionHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决ShardingSphere无法加入Seata全局事务的问题
 * 若RPC调用存在xid参数，则手动调用SeataTransactionHolder.set()设置全局事务
 * @author huangxiuqi
 */
@Configuration
public class SeataFeignInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SeataFeignInterceptor()).addPathPatterns("/**");
    }

    public static class SeataFeignInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String xid = RootContext.getXID();
            if (xid == null) {
                xid = request.getHeader(RootContext.KEY_XID);
            }

            if (!StringUtils.isBlank(xid) || SeataTransactionHolder.get() == null) {
                RootContext.bind(xid);
                SeataTransactionHolder.set(GlobalTransactionContext.getCurrentOrCreate());
            }

            return true;
        }
    }
}
