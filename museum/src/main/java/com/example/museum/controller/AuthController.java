package com.example.museum.controller;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Duration expiration;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.get("username"), creds.get("password"))); // 执行认证
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(expiration)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact(); // 生成 JWT
        return Collections.singletonMap("token", token); // 返回 {"token":"..."} 
    }
}
