package com.example.rediscache.service;

import com.example.rediscache.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    // Simulate slow lookup (e.g., DB/API) to demonstrate cache speedup
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        simulateLatency(800);
        return new Product(id, "Product-" + id, Math.round((id % 100) * 1.23 * 100.0) / 100.0);
    }

    private void simulateLatency(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}