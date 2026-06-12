package com.example.finmarkprojectfiner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HomeControllerFinMark {

    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "Welcome! You are logged in.";
    }
}
