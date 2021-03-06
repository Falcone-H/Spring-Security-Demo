package com.example.config;

import com.example.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 12:10
 */

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    BackdoorAuthenticationProvider backdoorAuthenticationProvider;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中创建一个名为 'user' 的用户，密码为 'pwd' ，拥有 'USER' 权限
        // 密码使用 BCryptPasswordEncoder 加密
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER");

        // 在内存中创建一个名为 'admin' 的用户，密码为 'pwd' ，拥有 'USER', 'ADMIN' 权限
        // 密码使用 BCryptPasswordEncoder 加密
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER", "ADMIN");

        // 将自定义验证类注册进去
        auth.authenticationProvider(backdoorAuthenticationProvider);

        // 加入数据库验证类
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "index", "/error").permitAll()    // 匹配 "/", "/index", "/error" 路径，不需要权限即可访问
//                .antMatchers("/user/**").hasRole("USER")    // 匹配 "/user" 及其以下路径，都需要 "USER" 权限
//                .antMatchers("/admin/**").hasRole("ADMIN") // 匹配 "/admin" 及其以下路径，都需要 "ADMIN" 权限
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/user").permitAll() // 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll(); // 退出地址为 "/logout", 退出成功后跳转到页面 ”/login“

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(mySecurityMetadataSource);
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        return object;
                    }
                })
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/index.html", "/static/**", "/favicon.ico", "/error", "/login_p");
    }
}
