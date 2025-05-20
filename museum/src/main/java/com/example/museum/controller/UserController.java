package com.example.museum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.museum.config.CodeGenerator;
import com.example.museum.service.CommentsService;
import com.example.museum.service.MyUserService;
import com.example.museum.service.RelicSearchService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MyUserService myce;

    
    @PostMapping("/upgradeAvatar")
    public Map<String, Object> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username) throws IOException {

        // 1. 获取上传文件名和扩展名
        String original = file.getOriginalFilename();
        if (original == null || !original.contains(".")) {
            return Map.of("error", "文件名不合法");
        }
        String ext = StringUtils.getFilenameExtension(original);
        String filename = username + CodeGenerator.generate(6) + "." + ext;

        // 2. 取当前工作目录的绝对路径
        Path current = Paths.get("").toAbsolutePath(); // e.g. /opt/app
        Path base = current.getParent(); // 上一级：/opt

        // 3. 在 /opt 下创建 uploads/avatar
        Path uploadDir = base.resolve("uploads").resolve("avatar");
        Files.createDirectories(uploadDir); // 递归创建

        // （可选）打印一下，方便调试
        System.out.println("Saving avatar to: " + uploadDir.resolve(filename));

        // 4. 保存文件
        Path dest = uploadDir.resolve(filename);
        file.transferTo(dest.toFile());

        // 5. 返回可访问 URL
        // WebConfig 中已经把 /avatar/** 映射到 file:/opt/uploads/avatar/
        String url = "http://localhost:8081/api/avatar/" + filename;

        return Map.of("url", url, "ans", myce.updateUserAvatar(username, url));
    }
    
    @PostMapping("/sendInfo")
    public Map<String,Object> UpDateUserInfo(@RequestBody Map<String,String> info){
        String username = info.get("username");
        String pwd = info.get("password");
        if(pwd == null || pwd.equals("")) pwd = null;
        else pwd = new BCryptPasswordEncoder().encode(pwd);
        String email = info.get("email");
        if(email.equals("") || email == null) email = null;
        Boolean ans = myce.updateUserInfo(username, pwd, email);
        if(ans) return Map.of("ans",true);
        else return Map.of("ans",false,"message","该邮箱已存在!");
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

    @Autowired
    private CommentsService csce;
    
    @PostMapping("/mycomts")
    public ResponseEntity<List<Map<String,Object>>> getUserComments(@RequestBody Map<String,String> info){
        String uname = info.get("username");
        String rname = info.get("relicname");
        String cont = info.get("content");
        String ti = info.get("time");
        return ResponseEntity.ok(csce.getUserComments(uname, rname, cont, ti));
    }
    @PostMapping("/discomts")
    public ResponseEntity<Map<String,Object>> DelComments(@RequestBody Map<String,String> info){
        Long id = Long.parseLong(info.get("id"));
        return ResponseEntity.ok(Map.of("ans",csce.delUserCommentsById(id)));
    }
}