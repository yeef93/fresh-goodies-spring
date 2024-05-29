package com.yeef93.fresh_goodies_spring.products.service;

import com.yeef93.fresh_goodies_spring.products.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> getProducts();
    public Optional<Product> getProduct(long id);
    public Product addProduct(Product product);
    public Product updateProduct(Product product);
    public void deleteProduct(Long id);
}