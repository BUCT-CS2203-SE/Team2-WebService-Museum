package com.example.museum.service.impl;

import java.util.List;

import com.example.museum.mapper.AppUserMapper;
import com.example.museum.model.AppUser;
import com.example.museum.service.MyUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  // Spring 扫描到这个实现
@Transactional
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    private AppUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        System.out.println("当前尝试登录用户：" + user);
        return User.withUsername(user.getAccount())
                   .password(user.getPassword())
                   .build();
    }

    @Override
    public List<AppUser> getAllUser() {
        return userMapper.getAllUsers();
    }

    @Override
    public Boolean existByAccount(String account) {
        if(userMapper.findByUsername(account) == null) return false;
        else return true;
    }

    @Override
    public Boolean existByEmail(String email){
        if(userMapper.findByEmail(email) == null) return false;
        else return true;
    }

    @Override
    public AppUser findByAccount(String account){
        return userMapper.findByUsername(account);
    }
    @Override
    public AppUser findByEmail(String email){
        return userMapper.findByEmail(email);
    }
    @Override
    public Boolean addNewUser(AppUser user){
        return userMapper.addOneUser(user) == 1;
    }
    @Override
    public Boolean updateOneUser(AppUser user){
        return userMapper.updateOneUser(user) == 1;
    }
    @Override
    public Long getIdByAccount(String account){
        return userMapper.getIdByAccount(account);
    }
}
