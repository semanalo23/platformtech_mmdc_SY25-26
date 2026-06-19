package com.example.finmarkprojectfiner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeControllerFinMark {

    @GetMapping("/home")
    public String home() {
        return "Welcome! You are logged in.";
    }
}
