package com.example.finmarkprojectfiner.catalog.prodcontroller;

import org.springframework.ui.Model;
import com.example.finmarkprojectfiner.catalog.service.FinmarkProdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinmarkProdPageController {

    private final FinmarkProdService productService;

    public FinmarkProdPageController(FinmarkProdService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products"; // products.html
    }
}
