package com.rozsa.security;

import com.rozsa.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CustomUserDetails(User user, String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.user = user;
    }
}
