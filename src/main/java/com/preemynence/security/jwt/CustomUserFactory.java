package com.preemynence.security.jwt;

import com.preemynence.dao.Authority;
import com.preemynence.dao.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomUserFactory {

    private CustomUserFactory() {
    }

    public static CustomUser create(User user) {
        return new CustomUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthorityName().name()))
                .collect(Collectors.toList());
    }
}
