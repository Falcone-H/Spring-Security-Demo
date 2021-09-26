package com.example.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 18:36
 */

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 从 authentication 中获取当前用户的角色
        Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();
        // 从 configAttributes 中获取访问资源所需要的角色，它来自 MySecurityMetadataSource 的 getAttributes
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute attribute = iterator.next();
            String role = attribute.getAttribute();

            if ("ROLE_NONE".equals(role)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("User no login.");
                } else {
                    return;
                }
            }

            // 逐一进行角色匹配
            for (GrantedAuthority authority : userAuthorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    // 用户具有 ROLE_ADMIN 权限，可以访问所有资源
                    return;
                }
                if (authority.getAuthority().equals(role)) {
                    // 匹配成功就直接返回
                    return;
                }
            }

            //  不能完成匹配
            throw new AccessDeniedException("No authority.");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
