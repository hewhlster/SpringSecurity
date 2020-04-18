package org.fjh.security.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

//权限访问拦截器
@Component
public class AccessSercuirtyInterceptor
        extends AbstractSecurityInterceptor
        implements Filter{

    @Autowired
    @Qualifier(("accessDescisionManagerImpl"))
    AccessDecisionManager accessDecisionManager;
    @Autowired
    AccessSecurityMetadataSource
            accessSecurityMetadataSource;


    /**
     * 初始化时将自定义的DecisionManager，注入到父类AbstractSecurityInterceptor中
     */
    @PostConstruct
    public void initSetManager() {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return accessSecurityMetadataSource;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("AccessSercuirtyInterceptor--doFilter");
       // log.info("[自定义过滤器]: {}", " LoginSecurityInterceptor.doFilter()");
        FilterInvocation fi =
                new FilterInvocation(servletRequest,servletResponse, filterChain);
        // 调用父类的 beforeInvocation ==> accessDecisionManager.decide(..)
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            // 调用父类的 afterInvocation ==> afterInvocationManager.decide(..)
            super.afterInvocation(token, null);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }


}
