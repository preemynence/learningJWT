package com.preEmynence.service.impl;

import com.preEmynence.dao.Authority;
import com.preEmynence.dao.User;
import com.preEmynence.dto.UserDTO;
import com.preEmynence.repository.AuthorityRepository;
import com.preEmynence.repository.UserRepository;
import com.preEmynence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public UserDTO signUp(UserDTO userDTO) throws Exception {

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new Exception("username exists.");
        } else {
            user = new User();
            user.setEnabled(true);
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            List<Authority> authorities = authorityRepository.findAll();
            user.setAuthorities(new ArrayList<>());
            for (String authorityDTO : userDTO.getAuthorities()) {
                for (Authority authority : authorities) {
                    if (authority.getAuthorityName().name().equals(authorityDTO)) {
                        user.getAuthorities().add(authority);
                    }
                }
            }
            user = userRepository.save(user);
        }
        UserDTO result = new UserDTO(user);
        return result;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username));
    }
}
