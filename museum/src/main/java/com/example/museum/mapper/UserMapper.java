package com.example.museum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.museum.model.User;


@Mapper
public interface UserMapper {

    @Select("SELECT username,realname,idNumber,phone,email,avateur FROM user_info WHERE username = #{username}")
    User getUserByUsername(String username);

    @Update("UPDATE user_info SET password = #{password}, realname = #{realname}, idNumber = #{idNumber}, phone = #{phone}, email = #{email} WHERE username = #{username}")
    int updateUserInfo(User user);

    @Update("UPDATE user_info SET avateur = #{avateur} WHERE username = #{username}")
    int updateAvatar(@Param("username") String username, @Param("avateur") String avateur);
}