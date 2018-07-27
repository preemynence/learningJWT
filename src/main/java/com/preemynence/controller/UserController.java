package com.preemynence.controller;

import com.preemynence.dto.UserDTO;
import com.preemynence.security.jwt.CustomUser;
import com.preemynence.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public UserDTO getAuthenticatedUser(HttpServletRequest request) {
        String token = jwtTokenUtil.getToken(request.getHeader(tokenHeader));
        String username = jwtTokenUtil.getUsernameFromToken(token);
        CustomUser user = (CustomUser) userDetailsService.loadUserByUsername(username);
        return new UserDTO(user);
    }

}
