package com.example.ics.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
