package com.example.finmarkprojectfiner.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/resetpassword")
public class ResetPasswordController {

    // Simulated "database" of users and reset tokens
    private Map<String, String> users = new HashMap<>();
    private Map<String, String> resetTokens = new HashMap<>();

    @PostMapping("/request")
    public String requestReset(@RequestParam String username) {
        if (!users.containsKey(username)) {
            return "User not found!";
        }
        String token = UUID.randomUUID().toString();
        resetTokens.put(token, username);
        return "Reset token generated: " + token;
    }

    @PostMapping("/confirm")
    public String confirmReset(@RequestParam String token, @RequestParam String newPassword) {
        String username = resetTokens.get(token);
        if (username == null) {
            return "Invalid or expired token!";
        }
        users.put(username, newPassword); // simulate updating password
        resetTokens.remove(token);
        return "Password reset successful for user: " + username;
    }
}
