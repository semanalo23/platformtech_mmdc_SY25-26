package com.example.finmarkprojectfiner.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logoutpage"; // maps to logoutpage.html
    }
}
