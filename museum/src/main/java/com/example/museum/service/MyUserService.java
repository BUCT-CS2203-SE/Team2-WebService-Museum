// src/main/java/com/example/museum/service/IUserService.java
package com.example.museum.service;

import java.util.List;

import com.example.museum.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户服务接口，继承 Spring Security 的用户加载接口，
 * 并额外定义了获取所有用户及判断账号是否存在的方法。
 */
public interface MyUserService extends UserDetailsService {
    /**
     * 查询所有用户
     */
    List<AppUser> getAllUser();

    /**
     * 判断指定账号是否已存在
     */
    Boolean existByAccount(String account);
    Boolean existByEmail(String email);
    /**
     * 按账号名查找用户
     */
    AppUser findByAccount(String account);
    /**
     * 按邮箱查找用户
     */
    AppUser findByEmail(String email);
    /**
     * 注册新用户,id不填自增
     */
    Boolean addNewUser(AppUser user);
    /**
     * 更新用户信息
     * @param user 用户实体类
     * @return true / false
     */
    Boolean updateOneUser(AppUser user);
    /**
     * 通过账号名，查找用户id
     * @param account 账号名
     * @return Long
     */
    Long getIdByAccount(String account);
    /**
     * 按照用户id删除用户
     * @param uid 用户id
     * @return bool
     */
    Boolean deleteById(Long uid);
}
