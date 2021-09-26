package com.example.config;

import com.example.bean.MyResourceBean;
import com.example.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 18:36
 */

@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourceMapper resourceMapper;

    // 本方法返回访问资源所需的角色集合
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 从 object 中得到需要访问的资源，既网址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 从数据库中得到所有资源，以及对应的角色
        List<MyResourceBean> resourceBeans = resourceMapper.selectAllResource();
        for(MyResourceBean resourceBean : resourceBeans) {
            // 首先进行地址匹配
            if(antPathMatcher.match(resourceBean.getUrl(), requestUrl)
                && resourceBean.getRolesArray().length > 0) {
                return SecurityConfig.createList(resourceBean.getRolesArray());
            }
        }

        // 匹配不成功返回一个特殊的 ROLE_NONE
        return SecurityConfig.createList("ROLE_NONE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
