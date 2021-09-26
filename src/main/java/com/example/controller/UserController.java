package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 12:22
 */

@Controller
public class UserController {
    @RequestMapping("/user")
    public String user(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());

        // 从 SecurityContextHolder 中得到 Authentication 对象，进而获取权限列表，传到前端
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        model.addAttribute("authorities", authorities.toString());

        return "user/user";
    }

    @RequestMapping("/admin")
    public String admin(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());

        // 从 SecurityContextHolder 中得到 Authentication 对象，进而获取权限列表，传到前端
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        model.addAttribute("authorities", authorities.toString());
        return "admin/admin";
    }

    @RequestMapping("/depart1")
    public String depart1(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorityCollection = (Collection<GrantedAuthority>) auth.getAuthorities();
        model.addAttribute("authorities", authorityCollection.toString());
        return "depart1/depart1";
    }

    @RequestMapping("/depart2")
    public String depart2(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorityCollection = (Collection<GrantedAuthority>) auth.getAuthorities();
        model.addAttribute("authorities", authorityCollection.toString());
        return "depart2/depart2";
    }
}
