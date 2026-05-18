package com.example.ics.Auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    @NotEmpty
    private String email;
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,15}",message = "Invalid phone number")
    private String phone;
    @NotEmpty
    @Size(min = 8,max = 100)
    private String password;
    private String role = "VIEWER";
}
