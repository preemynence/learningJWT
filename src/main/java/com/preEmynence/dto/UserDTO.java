package com.preEmynence.dto;

import com.preEmynence.dao.Authority;
import com.preEmynence.dao.User;
import com.preEmynence.security.jwt.CustomUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private List<String> authorities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = "*******";
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getAuthorityName().toString());
        }
        this.authorities = roles;
        this.email = user.getEmail();
        this.enabled = user.getEnabled();
    }

    public UserDTO(CustomUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = "*******";
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : user.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        this.authorities = roles;
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
    }
}
