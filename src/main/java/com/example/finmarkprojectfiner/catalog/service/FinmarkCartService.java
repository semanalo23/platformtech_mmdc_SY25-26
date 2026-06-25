package com.example.finmarkprojectfiner.catalog.service;

import com.example.finmarkprojectfiner.catalog.model.FinmarkCart;
import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.repository.FinmarkProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinmarkCartService {

    private FinmarkCart cart = new FinmarkCart();

    @Autowired
    private FinmarkProductRepo productRepo;

    public void addProduct (Long id) {
        FinmarkProduct product = productRepo.findById(id).orElseThrow();
        cart.addProduct(product);
    }

    public FinmarkCart getCart() { return cart; }
}
