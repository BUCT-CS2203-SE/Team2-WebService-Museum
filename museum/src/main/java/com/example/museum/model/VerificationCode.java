package com.example.museum.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VerificationCode {
    private Long id;
    private String email;
    private String code;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
