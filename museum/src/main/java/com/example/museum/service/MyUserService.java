package com.example.museum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.museum.mapper.AppUserMapper;
import com.example.museum.model.AppUser;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private AppUserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userMapper.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("用户不存在");
        System.out.println("当前尝试登录用户："+user);
        return User.withUsername(user.getAccount())
                    .password(user.getPassword())
                    .build();
    }

    public List<AppUser> getAllUser(){
        return userMapper.getAllUsers();
    }

    public boolean exitByAccount(String account){
        List<AppUser> list = userMapper.getAllUsersByCondition(null, account);
        if(list.size() == 1) return true;
        else return false;
    }
}
