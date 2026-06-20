package com.example.finmarkprojectfiner.catalog.service;

import org.springframework.data.redis.core.RedisTemplate;
import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.repository.FinmarkProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinmarkProdService {

    private final FinmarkProductRepo productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // Constructor injection
    public FinmarkProdService(FinmarkProductRepo productRepository,RedisTemplate<String, Object> redisTemplate) {

        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    // Get all products
    public List<FinmarkProduct> getAllProducts() {

        String key = "products:all";

        try {
            List<FinmarkProduct> cached = (List<FinmarkProduct>) redisTemplate.opsForValue().get(key);

            if (cached != null) {
                System.out.println("Cache hit: returning products from Redis");
                return cached; // cache hit
            }
        } catch (Exception e) {
            System.out.println("Redis unavailable, falling back to DB: " + e.getMessage());
        }

        System.out.println("Cache miss: querying database");
        List<FinmarkProduct> products = productRepository.findAll();

        try {
            redisTemplate.opsForValue().set(key, products);
        }
        catch (Exception e){
            System.out.println("Could not update Redis cache: " + e.getMessage());
        }
        return products;
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
