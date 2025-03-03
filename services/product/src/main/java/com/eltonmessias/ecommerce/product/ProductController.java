package com.eltonmessias.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(service.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok(service.findProductById(productId));
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<ProductResponse> updateProduct
            (@RequestBody @Valid ProductRequest request, @PathVariable("product-id") Long productId)
    {
        return ResponseEntity.ok(service.updateProduct(request, productId));
    }
}
