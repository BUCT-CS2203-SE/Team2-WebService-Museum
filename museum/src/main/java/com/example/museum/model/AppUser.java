package com.example.museum.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AppUser {
    private Long id;
    private String email;
    private String account;
    private String password;
    private Boolean isFrozen = false;
    private Boolean isAdmin = false;
}

/*
 * 这个文件夹用于定义实体类，和数据库中的表相对应,这里是我对应的表（有两个字段没使用，仅测试）
 * 如果你想不修改这里和mapp和对应xml文件，就在你的库里建一个对应的表,然后修改数据源连接到你的库即可进行测试
DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  isFrozen TINYINT(1) NOT NULL DEFAULT 0,
  isAdmin TINYINT(1) NOT NULL DEFAULT 0
);
insert into app_user values(1,'test','123','3309296020@qq.com',0,0);
show events;
SHOW VARIABLES LIKE 'event_scheduler';
-- 创建验证码存储表
DROP TABLE IF EXISTS verification_code;
CREATE TABLE verification_code (
  id           BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email        VARCHAR(255) NOT NULL,
  code         VARCHAR(64)  NOT NULL,
  expires_at   DATETIME     NOT NULL,
  created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_email_code (email, code),
  INDEX idx_expires_at (expires_at)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
-- 创建事件定时清除过期验证码
DROP EVENT IF EXISTS ev_delete_expired_codes;
DELIMITER $$
CREATE EVENT ev_delete_expired_codes
ON SCHEDULE EVERY 1 HOUR
COMMENT '每小时删除过期的验证码'
DO
BEGIN
  DELETE FROM verification_code
    WHERE expires_at < NOW();
END$$
DELIMITER ;
show events;
*/