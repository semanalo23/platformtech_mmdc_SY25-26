package com.example.finmarkprojectfiner.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterPageController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "registeracctform"; // routes to html
    }
}
