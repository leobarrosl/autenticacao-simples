package com.leobarrosl.auth.controllers;

import com.leobarrosl.auth.models.dto.AuthDTO;
import com.leobarrosl.auth.models.dto.LoginResponseDTO;
import com.leobarrosl.auth.models.dto.RegisterDTO;
import com.leobarrosl.auth.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthDTO data) {
        try {
            return ResponseEntity.ok(authService.login(data));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO data) {
        authService.register(data);
        return ResponseEntity.noContent().build();
    }
}

