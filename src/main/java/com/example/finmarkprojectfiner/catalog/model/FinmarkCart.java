package com.example.finmarkprojectfiner.catalog.model;

import java.util.ArrayList;
import java.util.List;

public class FinmarkCart {

    private List<FinmarkProduct> items = new ArrayList<>();

    public void addProduct(FinmarkProduct product) {
        items.add(product);
    }

    public void removeProduct(FinmarkProduct product) {
        items.remove(product);
    }

    public List<FinmarkProduct> getItems() {
        return items;
    }
}
