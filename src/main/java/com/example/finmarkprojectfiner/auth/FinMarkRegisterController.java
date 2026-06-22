package com.example.finmarkprojectfiner.auth;

import com.example.finmarkprojectfiner.SecurityConfigurationFinMark; // 🟢 Import your security configuration
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class FinMarkRegisterController {

    @PostMapping
    public String register(
            @RequestParam String name,
            @RequestParam String company,
            @RequestParam String email,
            @RequestParam String username, 
            @RequestParam String password) {
        
        // 🟢 Check against our shared live credentials data store
        if (SecurityConfigurationFinMark.databaseCredentials.containsKey(username)) {
            return "redirect:/register?error=exists";
        }
        
        // 🟢 PERSIST DATA: Write directly into the live Spring Security verification filter map
        SecurityConfigurationFinMark.databaseCredentials.put(username, password); 
        
        return "redirect:/login?success"; 
    }

    @GetMapping("/all")
    @ResponseBody
    public Map<String, String> listUsers() {
        // Keeps your debug API functional
        return SecurityConfigurationFinMark.databaseCredentials; 
    }
}