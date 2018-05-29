package com.preEmynence.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface AdminService {

    @PreAuthorize("hasRole('ADMIN')")
    String authTest();

}
