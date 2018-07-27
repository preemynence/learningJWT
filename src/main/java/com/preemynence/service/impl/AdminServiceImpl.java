package com.preemynence.service.impl;

import com.preemynence.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public String authTest() {
        return "Greetings from admin protected method!";
    }
}
