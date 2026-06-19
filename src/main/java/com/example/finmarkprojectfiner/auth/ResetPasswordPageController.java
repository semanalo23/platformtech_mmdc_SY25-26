package com.example.finmarkprojectfiner.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetPasswordPageController {
    @GetMapping("/reset")
    public String showResetForm() {
        return "resetpwform"; // routes to reset
    }
}
