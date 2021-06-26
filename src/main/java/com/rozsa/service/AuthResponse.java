package com.rozsa.service;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private boolean authenticated;
    private String username;
    private long userId;
    private List<String> authorities;
}
