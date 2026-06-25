package com.example.finmarkprojectfiner.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeControllerFinMark {

    // Shared credentials memory mapping context
    public static final Map<String, String> databaseCredentials = new HashMap<>();

    static {
        databaseCredentials.put("testuser", "password123");
        databaseCredentials.put("admin", "admin123");
    }

    private final List<String> executionLogs = new ArrayList<>();
    private final List<FeedbackEntry> feedbackLogs = new ArrayList<>();
    
    // DYNAMIC CART & SYSTEM ORDERS IN-MEMORY TABLES
    private final List<CartItem> userCart = new ArrayList<>();
    private final List<OrderTrack> globalOrders = new ArrayList<>();
    
    private int replicaCount = 0;
    private boolean isPayrollServiceAlive = false;

    public HomeControllerFinMark() {
        if (executionLogs.isEmpty()) {
            executionLogs.add("🟢 [SYSTEM] FinMark Core Services initialized successfully.");
            executionLogs.add("🛡️ [WAF] Enterprise Security profiles loaded.");
        }
        feedbackLogs.add(new FeedbackEntry("vladimir.bernardo@company.com", "Suggestions", "High", "SQL query optimization required for local warehouse telemetry lines."));
        
        // Seed initial tracking logs so your Admin Registry lists data immediately on startup
        globalOrders.add(new OrderTrack("FIN-98431-TX", "testuser@company.com", "Financial Analysis Premium Core", 85000.00, "Processing Data"));
        globalOrders.add(new OrderTrack("FIN-23194-TX", "vladimir@domain.ph", "Marketing Analytics Tier A", 45000.00, "Deployed"));
    }

    @GetMapping("/login") public String showLoginPage() { return "login"; }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("logs", executionLogs);
        model.addAttribute("replicaCount", replicaCount);
        // Pass the feedback logs to the client dashboard for the notification bell
        model.addAttribute("feedbackLogs", feedbackLogs);
        return "dashboard"; 
    }

    @GetMapping("/financial-services") public String showFinancialServices() { return "financial-services"; }
    @GetMapping("/marketing-services") public String showMarketingServices() { return "marketing-services"; }
    @GetMapping("/bi-services") public String showBiServices() { return "bi-services"; }
    @GetMapping("/consulting-services") public String showConsultingServices() { return "consulting-services"; }

    // Client Feedback Form Routes
    @GetMapping("/feedback") public String showFeedbackDashboard() { return "feedback"; }

    @PostMapping("/submit-feedback")
    public String processFeedback(
            @RequestParam(value = "clientName") String clientName,
            @RequestParam(value = "serviceCategory") String serviceCategory,
            @RequestParam(value = "priority", defaultValue = "Medium") String priority,
            @RequestParam(value = "message") String message) {
        feedbackLogs.add(new FeedbackEntry(clientName, serviceCategory, priority, message));
        return "redirect:/feedback?submitted"; 
    }

    // ADMIN SEPARATE LOG ROUTE 1: Feedback Log Panel
    @GetMapping("/admin/feedback-dashboard")
    public String showAdminFeedbackDashboard(Model model) {
        model.addAttribute("feedbackLogs", feedbackLogs);
        return "admin-dashboard"; 
    }

    // ADMIN SEPARATE LOG ROUTE 2: Dynamic Order Tracker & Status Panel
    @GetMapping("/admin/orders-dashboard")
    public String showAdminOrdersDashboard(Model model) {
        model.addAttribute("globalOrders", globalOrders);
        return "admin-orders-dashboard"; 
    }

    // 🟢 ADMIN SEPARATE LOG ROUTE 3: Executive Business Intelligence & Analytics Reports
    @GetMapping("/admin/bi-reports")
    public String showAdminBIReports(Model model) {
        // Compute running totals out of core transactional allocations dynamically
        double totalRevenue = globalOrders.stream()
                .filter(order -> !order.getStatus().equalsIgnoreCase("Terminated / Complete"))
                .mapToDouble(OrderTrack::getTotalBill)
                .sum();
                
        long pendingDeployments = globalOrders.stream()
                .filter(order -> order.getStatus().equalsIgnoreCase("Pending Activation") || 
                                 order.getStatus().equalsIgnoreCase("Processing Data"))
                .count();

        long feedbackCount = feedbackLogs.size();

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("pendingDeployments", pendingDeployments);
        model.addAttribute("feedbackCount", feedbackCount);
        model.addAttribute("globalOrders", globalOrders); 
        
        return "admin-bi-reports"; 
    }

    // ===============================================
    // CLIENT-SIDE INTERACTIVE CART & CHECKOUT LOGIC
    // ===============================================

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam(value = "serviceName") String serviceName, 
            @RequestParam(value = "price") double price) {
        
        System.out.println("🛒 [CART ADD] Appending Service: " + serviceName + " | Cost: ₱" + price);
        userCart.add(new CartItem(serviceName, price));
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        double total = userCart.stream().mapToDouble(CartItem::getPrice).sum();
        model.addAttribute("cartItems", userCart);
        model.addAttribute("cartTotal", total);
        return "cart";
    }

    @PostMapping("/cart/checkout")
    public String processCheckout(Model model) {
        if (userCart.isEmpty()) { return "redirect:/cart?error=empty"; }

        String referenceNumber = "FIN-" + (10000 + (int)(Math.random() * 90000)) + "-TX";
        double totalAmount = userCart.stream().mapToDouble(CartItem::getPrice).sum();
        
        StringBuilder itemsSummary = new StringBuilder();
        for (CartItem item : userCart) {
            if (itemsSummary.length() > 0) itemsSummary.append(", ");
            itemsSummary.append(item.getServiceName());
        }

        globalOrders.add(new OrderTrack(referenceNumber, "active_session_client", itemsSummary.toString(), totalAmount, "Pending Activation"));
        userCart.clear(); 

        model.addAttribute("refNumber", referenceNumber);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderSummary", itemsSummary.toString());
        return "checkout-success";
    }

    // CLIENT SEARCH VECTOR: Traces order IDs and sets status alerts
    @GetMapping("/order/track")
    public String showOrderTrackingPage(
            @RequestParam(value = "referenceNumber", required = false) String referenceNumber,
            Model model) {
        
        if (referenceNumber == null || referenceNumber.trim().isEmpty()) {
            return "order-tracking";
        }
        
        model.addAttribute("searchedRef", referenceNumber.trim().toUpperCase());
        
        OrderTrack matchedOrder = globalOrders.stream()
                .filter(order -> order.getReferenceNumber().equalsIgnoreCase(referenceNumber.trim()))
                .findFirst()
                .orElse(null);
                
        if (matchedOrder != null) {
            model.addAttribute("orderFound", matchedOrder);
        } else {
            model.addAttribute("errorSearching", true);
        }
        
        return "order-tracking";
    }

    // ===============================================
    // ADMIN CRUD IMPLEMENTATION MAPPINGS
    // ===============================================

    // CRUD: UPDATE OPERATION - Modifies infrastructure provisioning status parameters
    @PostMapping("/admin/orders/update-status")
    public String updateOrderStatus(
            @RequestParam("referenceNumber") String referenceNumber,
            @RequestParam("newStatus") String newStatus) {
        
        System.out.println("🔄 [CRUD UPDATE] Modifying Ref: " + referenceNumber + " -> Status: " + newStatus);
        
        globalOrders.stream()
                .filter(order -> order.getReferenceNumber().equalsIgnoreCase(referenceNumber.trim()))
                .findFirst()
                .ifPresent(order -> order.setStatus(newStatus));
                
        return "redirect:/admin/orders-dashboard";
    }

    // CRUD: DELETE OPERATION - Purges specific tracking references from memory pools
    @PostMapping("/admin/orders/delete")
    public String deleteOrder(@RequestParam("referenceNumber") String referenceNumber) {
        
        System.out.println("❌ [CRUD DELETE] Purging Reference Index: " + referenceNumber);
        globalOrders.removeIf(order -> order.getReferenceNumber().equalsIgnoreCase(referenceNumber.trim()));
        return "redirect:/admin/orders-dashboard";
    }

    // CRUD: FEEDBACK UPDATE STATUS & SUBMIT REPLY OPERATION
    @PostMapping("/admin/feedback/reply")
    public String replyToFeedback(
            @RequestParam("clientName") String clientName,
            @RequestParam("message") String message,
            @RequestParam("adminReply") String adminReply,
            @RequestParam("status") String status) {
        
        System.out.println("💬 [FEEDBACK UPDATE] Modifying Feedback from Client: " + clientName + " | New Status: " + status);
        
        feedbackLogs.stream()
                .filter(entry -> entry.getClientName().equalsIgnoreCase(clientName.trim()) && 
                                 entry.getMessage().equalsIgnoreCase(message.trim()))
                .findFirst()
                .ifPresent(entry -> {
                    entry.setAdminReply(adminReply);
                    entry.setStatus(status);
                });
                
        return "redirect:/admin/feedback-dashboard";
    }

    // CRUD: FEEDBACK DELETE OPERATION
    @PostMapping("/admin/feedback/delete")
    public String deleteFeedback(
            @RequestParam("clientName") String clientName,
            @RequestParam("message") String message) {
        
        System.out.println("❌ [FEEDBACK DELETE] Purging Submission Row from Client: " + clientName);
        
        feedbackLogs.removeIf(entry -> entry.getClientName().equalsIgnoreCase(clientName.trim()) && 
                                       entry.getMessage().equalsIgnoreCase(message.trim()));
                                       
        return "redirect:/admin/feedback-dashboard";
    }

    @PostMapping("/simulate-payroll")
    public String runSimulation(Model model) {
        executionLogs.add("🌟 [Client] Disbursed payroll / triggered transactional cycle.");
        if (!isPayrollServiceAlive) {
            executionLogs.add("❌ API Gateway detected Payroll Microservice is DOWN. Triggering Circuit Breaker Fallback.");
        } else { replicaCount++; }
        model.addAttribute("logs", executionLogs);
        model.addAttribute("replicaCount", replicaCount);
        return "dashboard"; 
    }
}

// =========================================================================
// PACKAGE-PRIVATE DATA MODELS (No 'public' keyword to prevent compilation errors)
// =========================================================================

class FeedbackEntry {
    private final String clientName; 
    private final String serviceCategory; 
    private final String priority; 
    private final String message;
    private String adminReply = "";    
    private String status = "Open";    
    
    public FeedbackEntry(String clientName, String serviceCategory, String priority, String message) {
        this.clientName = clientName; this.serviceCategory = serviceCategory; this.priority = priority; this.message = message;
    }
    public String getClientName() { return clientName; }
    public String getServiceCategory() { return serviceCategory; }
    public String getPriority() { return priority; }
    public String getMessage() { return message; }
    
    public String getAdminReply() { return adminReply; }
    public void setAdminReply(String adminReply) { this.adminReply = adminReply; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

class CartItem {
    private final String serviceName; 
    private final double price;
    
    public CartItem(String serviceName, double price) { this.serviceName = serviceName; this.price = price; }
    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
}

class OrderTrack {
    private final String referenceNumber; 
    private final String clientAccount; 
    private final String servicesOrdered; 
    private final double totalBill; 
    private String status; 
    
    public OrderTrack(String referenceNumber, String clientAccount, String servicesOrdered, double totalBill, String status) {
        this.referenceNumber = referenceNumber; this.clientAccount = clientAccount; this.servicesOrdered = servicesOrdered; this.totalBill = totalBill; this.status = status;
    }
    public String getReferenceNumber() { return referenceNumber; }
    public String getClientAccount() { return clientAccount; }
    public String getServicesOrdered() { return servicesOrdered; }
    public double getTotalBill() { return totalBill; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}