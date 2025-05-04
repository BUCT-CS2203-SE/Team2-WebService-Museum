package com.example.museum.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.museum.exception.UserNotExistException;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

/**
 * 全局异常处理器，捕获并处理全局异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 通用错误响应体
    static class ErrorResponse {
        public LocalDateTime timestamp = LocalDateTime.now();
        public int status;
        public String error;
        public String message;
        public String path;

        public ErrorResponse(HttpStatus status, Exception ex, String path) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = ex.getMessage();
            this.path = path;
        }
    }

    /** 处理自定义业务异常 */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBusiness(UserNotExistException ex, HttpServletRequest req) {
        System.out.println("UsernameNotFoundException: "+ex.getMessage());
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex, req.getRequestURI());
    }

    /** 处理 Spring Security 认证异常 */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuth(UsernameNotFoundException ex, HttpServletRequest req) {
        System.out.println("出现异常: "+ex.getMessage());
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex, req.getRequestURI());
    }

}
