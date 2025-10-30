package com.empsys.controller;

import com.empsys.dto.LoginRequestDTO;
import com.empsys.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
