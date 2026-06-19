package com.example.finmarkprojectfiner.catalog.prodcontroller;

import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.service.FinmarkProdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class FinmarkProdController {

    private final FinmarkProdService productService;


    // Constructor injection
    public FinmarkProdController(FinmarkProdService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public List<FinmarkProduct> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Optional<FinmarkProduct> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Add new product
    @PostMapping
    public FinmarkProduct addProduct(@RequestBody FinmarkProduct product) {
        return productService.saveProduct(product);
    }

    // Update product
    @PutMapping("/{id}")
    public FinmarkProduct updateProduct(@PathVariable Long id, @RequestBody FinmarkProduct product) {
        product.setId(id);
        return productService.saveProduct(product);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // Search products by name keyword
    @GetMapping("/search")
    public List<FinmarkProduct> searchProducts(@RequestParam String keyword) {
        return productService.searchByName(keyword);
    }
}
