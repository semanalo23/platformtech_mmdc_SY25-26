package com.example.finmarkprojectfiner.catalog.service;

import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.repository.FinmarkProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinmarkProdService {

    private final FinmarkProductRepo productRepository;

    // Constructor injection
    public FinmarkProdService(FinmarkProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<FinmarkProduct> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<FinmarkProduct> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Save or update product
    public FinmarkProduct saveProduct(FinmarkProduct product) {
        return productRepository.save(product);
    }

    // Delete product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Example custom query wrapper
    public List<FinmarkProduct> searchByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

}
