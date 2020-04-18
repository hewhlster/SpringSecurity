package org.fjh.security.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;


//鉴权管理器
@Service
public class AccessDescisionManagerImpl implements AccessDecisionManager {
    Logger logger = LoggerFactory.getLogger(AccessDescisionManagerImpl.class);
    //鉴权处理，正常返回为通过，抛出异常为鉴权失败
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        logger.info("[当前用户的角色],{}", authentication.getAuthorities());
        logger.info("[资源能被访问所需的角色],{}",configAttributes);

        //得到操作请求所需的权限，configAttributes由AccessSecurityMetadataSource类中的getAttributes方法返回
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        //迭代比较
        while (it.hasNext()) {
            ConfigAttribute configAttribute=it.next();
            String role = configAttribute.getAttribute();
            //得到以认证用户的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities
            ) {
                //找到对应的权限，鉴权通过
                if (grantedAuthority.getAuthority().equalsIgnoreCase(role))
                    return;
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
