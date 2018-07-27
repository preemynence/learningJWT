package com.preemynence.service;

import com.preemynence.dto.UserDTO;

public interface UserService {
    UserDTO signUp(UserDTO userDTO) throws Exception;
    UserDTO getUserByUsername(String username);
}
