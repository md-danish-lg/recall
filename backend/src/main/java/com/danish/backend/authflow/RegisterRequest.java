package com.danish.backend.authflow;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class RegisterRequest {
    @NotBlank(message = "email cannot be empty")
    @Email
    private String email;



    @NotBlank(message="password cannot be empty")
    private String password;

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
