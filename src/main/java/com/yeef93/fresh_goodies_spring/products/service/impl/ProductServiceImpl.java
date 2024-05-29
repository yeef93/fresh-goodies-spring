package com.yeef93.fresh_goodies_spring.products.service.impl;

import com.yeef93.fresh_goodies_spring.exceptions.ApplicationException;
import com.yeef93.fresh_goodies_spring.products.Product;
import com.yeef93.fresh_goodies_spring.products.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Log
public class ProductServiceImpl implements ProductService {
    List<Product> products = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @PostConstruct
    public void init() {
        // Add initial data
        products.add(new Product(counter.incrementAndGet(), "White Onion", "Fresh Veggie", "/products/white-onion.png", 0.0027, 1200, new Product.Metadata("g", 100, 40, 1.1, 0.1, 100, 9.3)));
        products.add(new Product(counter.incrementAndGet(), "Tomato", "Fresh Veggie", "/products/tomato.png", 0.0030, 1000, new Product.Metadata("g", 100, 18, 0.9, 0.2, 100, 3.9)));
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Optional<Product> getProduct(long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public Product addProduct(Product product) {
        boolean exists = products.stream().anyMatch(p -> p.getId() == product.getId());
        if (exists) {
            throw new ApplicationException("Product with ID " + product.getId() + " already exists.");
        }
        products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        Product currentProduct = products.stream()
                .filter(p -> p.getId() == product.getId())
                .findFirst()
                .orElse(null);

        if (currentProduct != null) {
            currentProduct.setName(product.getName());
            currentProduct.setCategory(product.getCategory());
            currentProduct.setImageUrl(product.getImageUrl());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setWeight(product.getWeight());
            currentProduct.setMetadata(product.getMetadata());
        }

        return currentProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        boolean removed = products.removeIf(product -> product.getId() == id);
        if (!removed) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }
}
