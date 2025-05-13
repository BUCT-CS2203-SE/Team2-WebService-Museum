package com.example.museum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.museum.model.AppUser;
import com.example.museum.service.MyUserService;

@Controller
public class HellotoMain {
    static Integer cnt = 0;
    
    //这个仅用作测试，后端不需要返回页面
    @GetMapping("/home")
    public String Tohome(){
        return "toHome";
    }

    @Autowired
    private MyUserService myce;

    @GetMapping("/test")
    @ResponseBody
    public List<AppUser> getAllUseStrings(){
        return myce.getAllUser();
    }
}
