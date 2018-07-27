package com.preemynence.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {

    private String token;
    private String refreshToken;
    private UserDTO user;

}
