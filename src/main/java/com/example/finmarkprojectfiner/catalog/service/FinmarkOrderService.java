package com.example.finmarkprojectfiner.catalog.service;

import com.example.finmarkprojectfiner.catalog.model.FinmarkOrder;
import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.repository.FinmarkOrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinmarkOrderService {
    private final FinmarkOrderRepo orderRepository;

    public FinmarkOrderService(FinmarkOrderRepo orderRepository) {
        this.orderRepository = orderRepository;
    }

    public FinmarkOrder createOrder(List<FinmarkProduct> products) {
        FinmarkOrder order = new FinmarkOrder();
        order.setProducts(products);
        order.setStatus("Placed");
        return orderRepository.save(order);
    }

    public FinmarkOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
