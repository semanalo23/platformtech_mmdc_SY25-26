package com.example.finmarkprojectfiner.auth;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class FinMarkRegisterController {
    // Simulated "database"
    private Map<String, String> users = new HashMap<>();

    @PostMapping
    public String register(@RequestParam String username, @RequestParam String password) {
        if (users.containsKey(username)) {
            return "User already exists!";
        }
        users.put(username, password); // simulate saving to DB
        return "Registration successful for user: " + username;
    }

    @GetMapping("/all")
    public Map<String, String> listUsers() {
        return users; // just to show what’s stored
    }
}
