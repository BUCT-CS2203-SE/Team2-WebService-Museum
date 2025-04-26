package com.example.museum.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AppUser {
    private Long id;
    private String Account;
    private String password;
}

/*
 * 这个文件夹用于定义实体类，和数据库中的表相对应,这里是我对应的表（有两个字段没使用，仅测试）
 * 如果你想不修改这里和mapp和对应xml文件，就在你的库里建一个对应的表,然后修改数据源连接到你的库即可进行测试
CREATE TABLE app_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  admin TINYINT(1) NOT NULL DEFAULT 0,
  frozen TINYINT(1) NOT NULL DEFAULT 0
);
*/