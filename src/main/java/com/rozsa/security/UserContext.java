package com.rozsa.security;

import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {
    public static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static ObjectId getId() {
        return getUserDetails().getUser().getId();
    }

    public static long getUserId() {
        return getUserDetails().getUser().getUserId();
    }
}
