package com.example.museum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.mapper.UserMapper;
import com.example.museum.model.User;
import com.example.museum.service.RelicSearchService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // getInfo: 获取用户信息
    @GetMapping("/getInfo")
    public User getInfo(@RequestParam String username) {
        return userMapper.getUserByUsername(username);
    }

    // sendInfo: 修改用户信息（除用户名外）
    @PostMapping("/sendInfo")
    public String updateUserInfo(@RequestBody User user) {
        int updated = userMapper.updateUserInfo(user);
        return updated > 0 ? "success" : "fail";
    }

    // upgradeAvatar: 修改用户头像
    // @PostMapping("/upgradeAvatar")
    // public String upgradeAvatar(@RequestParam String username, @RequestParam String avateur) {
    //     int updated = userMapper.updateAvatar(username, avateur);
    //     return updated > 0 ? "success" : "fail";
    // }

    @PostMapping("/upgradeAvatar")
    public Map<String, Object> upgradeAvatar(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String avatar = userData.get("avatar");

        if (username == null || avatar == null) {
            throw new RuntimeException("参数缺失");
        }

        int updated = userMapper.updateAvatar(username, avatar);
        if (updated == 0) {
            throw new RuntimeException("更新失败，用户不存在");
        }

        Map<String, Object> res = new HashMap<>();
        res.put("msg", "头像更新成功");
        res.put("avatar", avatar);
        return res;
    }

    @Autowired
    private RelicSearchService rsce;
    @PostMapping("/myfav")
    public List<Map<String,Object>> getMyFav(@RequestBody Map<String,String> info){
        String username = info.get("username");
        String rn = info.get("relicname");
        String ti = info.get("time");
        System.out.println("\nGet Info: "+info);
        return rsce.searchMyFavRelic(username, rn, ti);
    }
    @PostMapping("/disfav")
    public Map<String,Object> delMyFav(@RequestBody Map<String,String> info){
        String username = info.get("username");
        Long rid = Long.parseLong(info.get("rid"));
        return Map.of("ans",rsce.changeFavRelic(rid, username, false));
    }
    @PostMapping("/browser")
    public List<Map<String,Object>> getMyHistory(@RequestBody Map<String,String> info){
        String uname = info.get("username");
        String rname = info.get("relicname");
        String ti = info.get("time");
        return rsce.getUserHistory(uname, rname, ti);
    }
    @PostMapping("/delbrowser")
    public Map<String,Object> delMyHistory(@RequestBody Map<String,String> info){
        String username = info.get("username");
        Long rid = Long.parseLong(info.get("rid"));
        return Map.of("ans",rsce.changeHistory( username,rid, true));
    }
}