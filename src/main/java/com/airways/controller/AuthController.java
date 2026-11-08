package com.airways.controller;

import com.airways.dto.LoginRequestDTO;
import com.airways.model.User;
import com.airways.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setRole(User.Role.USER);
        return ResponseEntity.ok(userService.createUser(user));
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO request) {
        try {
            User user = userService.getUserByUsername(request.getUsername());
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("user", user);
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
