package com.yeef93.fresh_goodies_spring.products;

import com.yeef93.fresh_goodies_spring.exceptions.ApplicationException;
import com.yeef93.fresh_goodies_spring.products.service.ProductService;
import com.yeef93.fresh_goodies_spring.responses.Response;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Log
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Response<List<Product>>> getProducts() {
        return Response.successfulResponse("All products fetched", productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Optional<Product>>> getProduct(@PathVariable Long id) {
        var productFound = productService.getProduct(id);
        if (productFound.isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }
        return Response.successfulResponse("Product detail found", productFound);
    }

    @PutMapping
    public ResponseEntity<Response<Product>> updateProduct(@RequestBody Product product) {
        try {
            var updateProduct = productService.updateProduct(product);
            return Response.successfulResponse(HttpStatus.OK.value(), "Product updated", productService.updateProduct(updateProduct));
        } catch (ApplicationException e) {
            return Response.failedResponse(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response<Product>> createProduct(@Validated @RequestBody Product product) {
        try {
            var createdProduct = productService.addProduct(product);
            return Response.successfulResponse(HttpStatus.CREATED.value(), "New product created", productService.updateProduct(createdProduct));
        } catch (ApplicationException e) {
            return Response.failedResponse(e.getHttpStatus().value(), e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return Response.successfulResponse("Product with ID " + id + " was successfully deleted.");
        } catch (ApplicationException e) {
            return Response.failedResponse(e.getHttpStatus().value(), e.getMessage());
        }
    }


}