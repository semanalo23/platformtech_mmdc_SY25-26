package com.example.finmarkprojectfiner.catalog.repository;

import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinmarkProductRepo extends JpaRepository <FinmarkProduct,Long> {
    // Find products by exact name
    List<FinmarkProduct> findByName(String name);

    // Find products where name contains a keyword
    List<FinmarkProduct> findByNameContaining(String keyword);

    // Find products cheaper than a certain price
    List<FinmarkProduct> findByPriceLessThan(double price);

    // Find products with stock greater than a threshold
    List<FinmarkProduct> findByStockQuantityGreaterThan(Integer quantity);
}
