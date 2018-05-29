package com.preEmynence.service;

import com.preEmynence.dto.UserDTO;

public interface UserService {
    UserDTO signUp(UserDTO userDTO) throws Exception;
    UserDTO getUserByUsername(String username);
}
