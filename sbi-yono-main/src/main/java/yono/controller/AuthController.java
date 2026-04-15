package yono.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yono.dto.AuthResponse;
import yono.dto.LoginRequest;
import yono.dto.RegisterRequest;
import yono.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody RegisterRequest request) {
        String message = authService.register(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}