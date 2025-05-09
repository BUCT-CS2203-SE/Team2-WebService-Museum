package com.example.museum;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.museum.mapper.AppUserMapper;
import com.example.museum.mapper.VerificationCodeMapper;
import com.example.museum.model.AppUser;
import com.example.museum.model.VerificationCode;
import com.example.museum.service.EmailService;
import com.example.museum.service.MyUserService;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {
    @Autowired
    private MyUserService myce;
    @Autowired
    private DataSource dataSource;
    
    @Test
    void dataSource() throws SQLException{
        System.out.println(dataSource.getConnection());
    }

    @Test
    void Te1(){
        ArrayList<AppUser> mylist = new ArrayList<>(myce.getAllUser());
        for(AppUser i : mylist){
            System.out.println(i);
        }
    }

    @Autowired
    private AppUserMapper apmr;
    @Test
    void Test1(){
        AppUser user = new AppUser();
        user.setAccount("test1");
        user.setPassword("123");
        user.setEmail("123@12qq.com");
        int x = apmr.addOneUser(user);
        System.out.println(x);
    }
    @Test
    void test2(){
        // apmr.deleteById(5L);
        AppUser user = myce.findByAccount("test1");
        user.setPassword("1234");
        myce.updateOneUser(user);
        System.out.println(user);
    }


}   
