package com.rozsa.security.filters;

import com.rozsa.business.UserBusiness;
import com.rozsa.configuration.AuthServiceProperties;
import com.rozsa.model.User;
import com.rozsa.security.CustomUserDetails;
import com.rozsa.service.authentication.AuthService;
import com.rozsa.service.authentication.AuthResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    private final AuthServiceProperties authServiceProperties;
    private final AuthService authService;
    private final UserBusiness userBusiness;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String jwt = getToken(req);
        if (jwt == null) {
            chain.doFilter(req, res);
            return;
        }

        String token = "Bearer " + jwt;
        AuthResponse authResponse;
        try {
            authResponse = authService.validate(token);
        } catch (feign.RetryableException e) {
            log.error("AuthService failed to validate token {}.", token, e);
            res.setStatus(HttpStatus.GATEWAY_TIMEOUT.value());
            return;
        }
        catch (FeignException.Unauthorized | FeignException.Forbidden e) {
            log.info("Validation status for token {} is unauthorized", token);
            chain.doFilter(req, res);
            return;
        }
        catch (Exception e) {
            log.error("Authentication service call returned error: " + e.getMessage());
            res.setStatus(HttpStatus.BAD_GATEWAY.value());
            return;
        }

        log.info("Validation status for token {} is {}", token, authResponse);

        CustomUserDetails userDetails = getUserDetails(authResponse);
        if (userDetails == null) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        chain.doFilter(req, res);
    }

    public CustomUserDetails getUserDetails(AuthResponse response) {
        if (!response.isAuthenticated() || response.getUsername() == null) {
            return null;
        }

        User user = userBusiness.getOrCreateUserByUserId(response.getUserId());

        List<GrantedAuthority> authorities = response.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String username = response.getUsername();

        return new CustomUserDetails(user, username, authorities);
    }

    public String getToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }

        String cookieName = authServiceProperties.getAuthCookieName();

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);

        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }
}
