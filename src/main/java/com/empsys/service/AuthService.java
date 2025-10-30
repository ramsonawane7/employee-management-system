package com.empsys.service;

import com.empsys.dto.LoginRequestDTO;
import com.empsys.entity.UserCreds;
import com.empsys.repository.UserCredsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredsRepository userCredsRepository;

    public String login(LoginRequestDTO loginDTO) {
        UserCreds user = userCredsRepository.findByUsername(loginDTO.getUsername());

        if (user == null) {
            return "Invalid username!";
        }

        // NOTE: For production, use BCryptPasswordEncoder or similar hashing.
        if (!user.getPasswordHash().equals(loginDTO.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}
