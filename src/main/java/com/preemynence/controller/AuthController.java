package com.preemynence.controller;

import com.preemynence.dto.LoginRequest;
import com.preemynence.dto.LoginResponse;
import com.preemynence.dto.RefreshTokenResponse;
import com.preemynence.dto.UserDTO;
import com.preemynence.security.exception.AuthenticationException;
import com.preemynence.security.jwt.CustomUser;
import com.preemynence.security.jwt.JwtTokenUtil;
import com.preemynence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "${jwt.route.register.path}")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO registerUser) throws Exception {

        UserDTO userDTO = userService.signUp(registerUser);

        if (userDTO.getId() != null && userDTO.getId() > 0)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtTokenUtil.generateToken(userDetails));
        loginResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(userDetails));
        loginResponse.setUser(userService.getUserByUsername(userDetails.getUsername()));

        return ResponseEntity.ok(loginResponse);
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = jwtTokenUtil.getToken(request.getHeader(tokenHeader));
        String username = jwtTokenUtil.getUsernameFromToken(token);

        CustomUser user = (CustomUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {

            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
            refreshTokenResponse.setToken(jwtTokenUtil.generateToken(user));
            refreshTokenResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(user));

            return ResponseEntity.ok(refreshTokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
