package com.example.finmarkprojectfiner.auth;

// 🟢 UPDATED: Correct subpackage target reference import statement
import com.example.finmarkprojectfiner.home.HomeControllerFinMark; 
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
        
        if (HomeControllerFinMark.databaseCredentials.containsKey(username)) {
            return "redirect:/register?error=exists";
        }
        
        HomeControllerFinMark.databaseCredentials.put(username, password); 
        return "redirect:/login?success"; 
    }

    @GetMapping("/all")
    @ResponseBody
    public Map<String, String> listUsers() {
        return HomeControllerFinMark.databaseCredentials; 
    }
}