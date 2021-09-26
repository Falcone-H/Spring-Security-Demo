package com.example.service;

import com.example.bean.MyUserBean;
import com.example.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 14:12
 */

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserMapper myUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserBean myUserBean = myUserMapper.selectByUsername(username);
        if(myUserBean == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return myUserBean;
    }
}
