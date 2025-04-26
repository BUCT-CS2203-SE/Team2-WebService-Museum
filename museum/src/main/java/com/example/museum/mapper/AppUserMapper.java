package com.example.museum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.museum.model.AppUser;

@Mapper
public interface AppUserMapper {
    List<AppUser> getAllUsers();
    
    //内部参数选填至少一个灵活使用
    List<AppUser> getAllUsersByCondition(
        @Param("id") Long id,
        @Param("account") String account
    );

    AppUser findByUsername(@Param("account") String username);

    int AddOneUser(AppUser user);
    
    /**
     * 第二种写法，简单语句可以直接这样写
     * @param id Long
     * @return int 受影响的行数
     */
    @Delete("delete from app_user where id = #{id}")
    int deleteById(@Param("id") Long id);
}
