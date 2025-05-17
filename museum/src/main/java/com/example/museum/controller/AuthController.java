package com.example.museum.controller;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.exception.UserNotExistException;
import com.example.museum.model.AppUser;
import com.example.museum.model.VerificationCode;
import com.example.museum.service.EmailService;
import com.example.museum.service.MyUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * 用于登录、注册、找回密码
 */
@RestController
@Transactional
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MyUserService myce;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Duration expiration;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) throws UserNotExistException {
        System.out.println("\nGet Login Form: "+creds);
        String nowAccount = creds.get("user");
        String avatar = "";
        if (!creds.get("way").equals("true")) { // 邮箱登录
            AppUser user = myce.findByEmail(nowAccount);
            if (user == null)
                throw new UserNotExistException("该邮箱未注册！");
            nowAccount = user.getAccount();
            avatar = user.getAvatar();
        }else{
            AppUser user = myce.findByAccount(nowAccount);
            if(user == null) throw new UserNotExistException("该账号未注册！");
            avatar = user.getAvatar();
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            nowAccount, creds.get("password"))); // 执行认证
            System.out.println("\n Now Here\n");
            String token = Jwts.builder()
                    .setSubject(auth.getName())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(expiration)))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                            SignatureAlgorithm.HS256)
                    .compact(); // 生成 JWT
            Map<String,String> ans = new HashMap<>();       //avatar可能为空，只能采用这种形式
            ans.put("token",token);ans.put("username",nowAccount);ans.put("avatar",avatar);
            return ans;
        } catch (Exception e) {
            throw new UserNotExistException("密码错误! "+e.getMessage());
        }
    }

    @PostMapping("/regest")
    public ResponseEntity<Map<String,Object>> toRegest(@RequestBody Map<String, String> creds){
        Map<String,Object> response = new HashMap<>();
        String account = creds.get("account");
        String email = creds.get("email");
        if(!emailService.isValidFormat(email)){
            System.out.println("<-- 邮箱校验失败 -->");
            response.put("message", "注册失败，邮箱不可用！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        System.out.println("<-- 邮箱校验成功 -->");
        Boolean isAcnt = myce.existByAccount(account),isEml = myce.existByEmail(email);
        if(!isAcnt && !isEml){
            AppUser user = new AppUser(null, email, account, creds.get("password"),null, false, false);
            if(!myce.addNewUser(user)){
                response.put("message", "注册失败，服务器异常！");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response.put("message", "注册成功!");
            return ResponseEntity.ok(response);
        }
        if(isAcnt && isEml) response.put("message", "账号、邮箱已存在！请更换!"); 
        else if(isAcnt) response.put("message", "账号已存在！请更换!"); 
        else response.put("message", "邮箱已存在！请更换!"); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/getCode")
    public ResponseEntity<Map<String,Object>> getValidCode(@RequestBody Map<String,String> creds){
        Map<String,Object> response = new HashMap<>();
        String email = creds.get("email");String getTi = creds.get("time");
        System.out.println("收到表单信息： "+email+"  -> "+getTi);
        if(!myce.existByEmail(email)){
            response.put("message", "发送验证码失败！该邮箱未被注册,请前往注册！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        VerificationCode vfcode = emailService.findUnconfirmedCode(email);
        if(vfcode != null){ //查找库中有用的验证码返回时间，不重新发送
            Duration ti = Duration.between(LocalDateTime.now(), vfcode.getExpiresAt());
            long sec = ti.getSeconds();
            System.out.println("该验证码还有 "+sec+" 秒过期");
            response.put("message", "验证码已发送!");
            response.put("info", sec);
            return ResponseEntity.ok(response);
        }
        try{
            emailService.sendCode(email);
            response.put("message", "发送验证码成功!");
            response.put("info", 600); //10分钟
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "邮件服务异常，发送验证码失败!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }
    }

    @PostMapping("/forget")
    public ResponseEntity<Map<String,Object>> forGet(@RequestBody Map<String,String> creds){
        Map<String,Object> response = new HashMap<>();
        String email = creds.get("email");
        AppUser user = myce.findByEmail(email);
        if(user == null){
            response.put("message", "修改失败！该邮箱未被注册,请前往注册！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String newpwd = creds.get("newpwd");
        String validCode = creds.get("code");
        if(!emailService.verifyCode(email, validCode)){
            response.put("message", "修改失败！验证码错误，请重新输入！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);         
        }
        user.setPassword(newpwd);
        myce.updateOneUser(user);
        response.put("message", "重置密码成功!");
        return ResponseEntity.ok(response);
    }
}
