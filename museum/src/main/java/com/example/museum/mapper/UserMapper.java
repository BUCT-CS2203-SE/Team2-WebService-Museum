package com.example.museum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.museum.model.AppUser;


@Mapper
public interface UserMapper {

    // @Select("SELECT username,realname,idNumber,phone,email,avateur FROM user_info WHERE username = #{username}")
    // User getUserByUsername(String username);

    // @Update("UPDATE user_info SET password = #{password}, realname = #{realname}, idNumber = #{idNumber}, phone = #{phone}, email = #{email} WHERE username = #{username}")
    // int updateUserInfo(User user);

    // @Update("UPDATE user_info SET avateur = #{avateur} WHERE username = #{username}")
    // int updateAvatar(@Param("username") String username, @Param("avateur") String avateur);


    AppUser selectByUsername(String account);

    int updateUserInfo(@Param("account") String account,
                       @Param("password") String password,
                       @Param("email") String email);

    int updateAvatar(@Param("account") String account,
                     @Param("avatar") String avatar);




}