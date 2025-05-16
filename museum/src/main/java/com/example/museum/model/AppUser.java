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
    private String avatar;
    private Boolean isFrozen = false;
    private Boolean isAdmin = false;
}