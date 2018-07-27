package com.preemynence.dto;

import lombok.Data;

@Data
public class RefreshTokenResponse {

    private String token;
    private String refreshToken;

}
