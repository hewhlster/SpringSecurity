package org.fjh.security.authorization;

import org.fjh.security.dao.PermissionMapper;
import org.fjh.security.entity.Permission;
import org.fjh.security.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//自定义权限数据源
@Component
public class AccessSecurityMetadataSource
implements
        FilterInvocationSecurityMetadataSource {

    Logger logger= LoggerFactory.getLogger(AccessSecurityMetadataSource.class);
    @Autowired
    PermissionMapper permissionMapper;

    // key 是url ， value 是对应url资源的角色列表
    private static Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap;


    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();

        // 需要鉴权的url资源，@needAuth标志
//        List<SysPermissionDO> permissionList = authPermissionMapper.queryRolePermission();
//        for (SysPermissionDO permission : permissionList) {
//            String url = permission.getUrl();
//            String method = permission.getMethod();
//            String[] roles = permission.getRoleList().split(",");
//            log.info("{} - {}", url, method);
//            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);
//
//            Collection<ConfigAttribute> attributes = new ArrayList<>();
//            for (String role : roles) {
//                attributes.add(new SecurityConfig(role));
//            }
//            // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
//            attributes.add(new SecurityConfig("@needAuth"));
//            permissionMap.put(requestMatcher, attributes);
//        }


        // 公共的url资源 & 系统接口的url资源，value为null
/*        List<SysPermissionDO> publicList = authPermissionMapper.queryPublicPermission();
        for (SysPermissionDO permission : publicList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, "*".equals(method) ? null : method);
            // value为空时不做鉴权，相当于所有人都可以访问该资源URL
            permissionMap.put(requestMatcher, null);
        }*/

        // 多余的url资源， @noAuth，所有人都无法访问
/*        Collection<ConfigAttribute> attributes = new ArrayList<>();
        attributes.add(new SecurityConfig("@noAuth"));
        permissionMap.put(new AntPathRequestMatcher("/**", null), attributes);

        log.info("[全局权限映射集合初始化]: {}", permissionMap.toString());*/


        List<Permission> plist= permissionMapper.queryRolePermission();
        for (Permission p:plist) {
            //保存权限用
            Collection<ConfigAttribute> attributes = new ArrayList<>();
            //创建权限（资源）和角色对象
            AntPathRequestMatcher requestMatcher =
                    new AntPathRequestMatcher(p.getPurl());
            for (Role r:p.getRlist()) {
                //增加角色标签
                attributes.add(new SecurityConfig(r.getRtag()));
            }
            //加入全局权限列表
            permissionMap.put(requestMatcher, attributes);
        }
        logger.info("[全局权限映射集合初始化]: {}", permissionMap.toString());

    }


    //根据请求取得所需的权限
    //object参数是对应的请求
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        logger.info("[资源被访问：根据URL找到权限配置]: {} {}", object, permissionMap);

        //if (permissionMap == null) {
            //加载资源和权限信息
            loadResourceDefine();
        //}
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                logger.info("[找到的Key]: {}", entry.getKey());
                logger.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
