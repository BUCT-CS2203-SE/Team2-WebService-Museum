package com.example.museum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private Long id;
    private String name;
    private String password;
    private String realname;
    private String idNumber;
    private String phone;
    private String email;
    private String avateur;
}