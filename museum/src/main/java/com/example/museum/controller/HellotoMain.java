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

    @GetMapping("/users")
    @ResponseBody
    public List<String> toMain() {
        System.out.println("Redirecting to main page..."+cnt++);
        ArrayList<String> users = new ArrayList<>();
        users.add("张三");
        users.add("李四");
        users.add("王五");
        return users;
    }
    
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
