package com.example.finmarkprojectfiner.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller // Services HTML templates instead of raw text strings
public class HomeControllerFinMark {

    // Simulating system runtime memory states
    private final List<String> executionLogs = new ArrayList<>();
    private int replicaCount = 0;
    private boolean isPayrollServiceAlive = false; // Set to false to demonstrate microservice fallback/circuit breaker

    public HomeControllerFinMark() {
        // Seed initial setup telemetry logs when the class instantiates
        if (executionLogs.isEmpty()) {
            executionLogs.add("🟢 [SYSTEM] FinMark Core Services initialized successfully.");
            executionLogs.add("🛡️ [WAF] Enterprise Security profiles loaded.");
        }
    }

    /**
     * Route mapping to render your light-themed custom login screen.
     * Resolves to src/main/resources/templates/login.html
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    // 🟢 ADD THIS BACK (GET ONLY - For viewing the page layout)
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Safely loads templates/register.html
    }
    /**
     * Route mapping for the main tracking panel hub.
     * Resolves to src/main/resources/templates/dashboard.html
     */
    @GetMapping("/home")
    public String home(Model model) {
        // Inject current system states directly down into the Thymeleaf template context
        model.addAttribute("logs", executionLogs);
        model.addAttribute("replicaCount", replicaCount);
        return "dashboard"; 
    }

    /**
     * Route mapping for Financial Analysis matrix.
     * Resolves to src/main/resources/templates/financial-services.html
     */
    @GetMapping("/financial-services")
    public String showFinancialServices() {
        return "financial-services";
    }

    /**
     * Route mapping for Marketing Analytics matrix.
     * Resolves to src/main/resources/templates/marketing-services.html
     */
    @GetMapping("/marketing-services")
    public String showMarketingServices() {
        return "marketing-services";
    }

    /**
     * Route mapping for Business Intelligence matrix.
     * Resolves to src/main/resources/templates/bi-services.html
     */
    @GetMapping("/bi-services")
    public String showBiServices() {
        return "bi-services";
    }

    /**
     * Route mapping for Consulting Services matrix.
     * Resolves to src/main/resources/templates/consulting-services.html
     */
    @GetMapping("/consulting-services")
    public String showConsultingServices() {
        return "consulting-services";
    }

    /**
     * Route mapping triggered when a user clicks 'Run Simulation' on the Payroll card.
     * Demonstrates real-time architectural resilience/fault tolerance.
     */
    @PostMapping("/simulate-payroll")
    public String runSimulation(Model model) {
        executionLogs.add("🌟 [Client] Disbursed payroll / triggered transactional cycle.");
        
        // Microservice Error Handling Fallback Simulation Logic
        if (!isPayrollServiceAlive) {
            executionLogs.add("❌ API Gateway detected Payroll Microservice is DOWN. Triggering Circuit Breaker Fallback.");
            executionLogs.add("⚠️ [SERVER RESILIENCE] Primary node offline. Request safely queued in local message bus.");
        } else {
            replicaCount++;
            executionLogs.add("✅ API Gateway cleared. Status: Checkout completed successfully!");
            executionLogs.add("💾 Mirrored data transaction to Master Data Store.");
        }

        // Push the updated, real-time lists back to the UI view model
        model.addAttribute("logs", executionLogs);
        model.addAttribute("replicaCount", replicaCount);
        
        return "dashboard"; // Refreshes the dashboard view with the new live log line items injected
    }
}