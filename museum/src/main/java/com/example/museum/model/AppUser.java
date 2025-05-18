package com.example.museum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AppUser {
    private Long id;
    private String email;
    private String account;
    private String password;
    private String avatar;
    private Boolean isFrozen = false;
    private Boolean isAdmin = false;

    
}