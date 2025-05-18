package com.example.museum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.mapper.UserMapper;
import com.example.museum.model.AppUser;
import com.example.museum.service.RelicSearchService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    //  getInfo: 获取用户信息
    // @GetMapping("/getInfo")
    // public User getInfo(@RequestParam String username) {
    //     return userMapper.getUserByUsername(username);
    // }

    //  sendInfo: 修改用户信息（除用户名外）
    // @PostMapping("/sendInfo")
    // public String updateUserInfo(@RequestBody User user) {
    //     int updated = userMapper.updateUserInfo(user);
    //     return updated > 0 ? "success" : "fail";
    // }

    // upgradeAvatar: 修改用户头像
    // @PostMapping("/upgradeAvatar")
    // public String upgradeAvatar(@RequestParam String username, @RequestParam String avateur) {
    //     int updated = userMapper.updateAvatar(username, avateur);
    //     return updated > 0 ? "success" : "fail";
    // }

    // @PostMapping("/upgradeAvatar")
    // public Map<String, Object> upgradeAvatar(@RequestBody Map<String, String> userData) {
    //     String username = userData.get("username");
    //     String avatar = userData.get("avatar");

    //     if (username == null || avatar == null) {
    //         throw new RuntimeException("参数缺失");
    //     }

    //     int updated = userMapper.updateAvatar(username, avatar);
    //     if (updated == 0) {
    //         throw new RuntimeException("更新失败，用户不存在");
    //     }

    //     Map<String, Object> res = new HashMap<>();
    //     res.put("msg", "头像更新成功");
    //     res.put("avatar", avatar);
    //     return res;
    // }


        
    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestParam String username) {
        AppUser user = userMapper.selectByUsername(username);
        if (user != null) {
            Map<String, String> result = new HashMap<>();
            result.put("account", user.getAccount());
            result.put("email", user.getEmail());
            result.put("avatar", user.getAvatar());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
    }

    
    // @PostMapping("/sendInfo")
    // public ResponseEntity<?> sendInfo(@RequestParam String account,
    //                                   @RequestParam(required = false) String password,
    //                                   @RequestParam(required = false) String email) {
    //     int updated = userMapper.updateUserInfo(account, password, email);
    //     if (updated > 0) {
    //         return ResponseEntity.ok("信息更新成功");
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更新失败");
    //     }
    // }



    @PutMapping("/sendInfo")
public ResponseEntity<?> sendInfo(@RequestBody Map<String, String> userInfo) {
    String account = userInfo.get("username");
    String password = userInfo.get("password");
    String email = userInfo.get("email");

    int updated = userMapper.updateUserInfo(account, password, email);
    if (updated > 0) {
        return ResponseEntity.ok("信息更新成功");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更新失败");
    }
}




    
    @PostMapping("/upgradeAvatar")
    public ResponseEntity<?> upgradeAvatar(@RequestParam String account,
                                           @RequestParam String avatar) {
        int updated = userMapper.updateAvatar(account, avatar);
        if (updated > 0) {
            return ResponseEntity.ok("头像更新成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("头像更新失败");
        }
    }


//     @PostMapping("/upgradeAvatar")
// public ResponseEntity<?> upgradeAvatar(
//         @RequestParam("account") String account,
//         @RequestParam("file") MultipartFile file,
//         HttpServletRequest request) throws IllegalStateException, java.io.IOException {
//     try {
//         // 1. 保存图片到本地
//         String uploadPath = "D:/uploads/";
//         File dir = new File(uploadPath);
//         if (!dir.exists()) dir.mkdirs();

//         String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//         File dest = new File(uploadPath + fileName);
//         file.transferTo(dest);

//         // 2. 构造头像访问 URL
//         String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//                    + "/uploads/" + fileName;

//         // 3. 更新数据库中用户头像字段
//         int updated = userMapper.updateAvatar(account, url);
//         if (updated > 0) {
//             return ResponseEntity.ok(Map.of("msg", "头像上传成功", "url", url));
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("账号不存在，头像更新失败");
//         }
//     } catch (IOException e) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败：" + e.getMessage());
//     }
// }









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