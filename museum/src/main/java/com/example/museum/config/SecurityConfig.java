package com.example.museum.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.example.museum.service.MyUserService;

@Configuration
public class SecurityConfig {

  @Autowired
  private MyUserService myce;

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();  // 将 AuthenticationManager 注入容器
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(Customizer.withDefaults())  // 启用本地 CORS配置
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth

            .requestMatchers("/test/**","/login","/regest","/forget","/getCode","/relic/**").permitAll()
            .anyRequest().authenticated()
            )
        .logout(out -> out
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
            )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .userDetailsService(myce);
    return http.build();  // 自动拦截并校验所有 Bearer Token
  }

  /**
   * 密码加密
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secret) {
      SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      return NimbusJwtDecoder.withSecretKey(key).build();
  }
}
