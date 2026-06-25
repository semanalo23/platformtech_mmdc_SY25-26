package com.example.finmarkprojectfiner.catalog.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FinmarkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<FinmarkProduct> products;

    private String status;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<FinmarkProduct> getProducts() { return products; }
    public void setProducts(List<FinmarkProduct> products) { this.products = products; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
