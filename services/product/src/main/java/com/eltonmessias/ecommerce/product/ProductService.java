package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import com.eltonmessias.ecommerce.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Long createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(mapper::toProductResponse).collect(Collectors.toList());
    }


    public ProductResponse findProductById(Long id) {
        var product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return mapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(@Valid ProductRequest request, Long productId) {
        var product = repository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setAvailableQuantity(request.availableQuantity());
        product.setCategory(Category.builder()
                        .id(request.categoryId())
                .build());
        repository.save(product);
        return mapper.toProductResponse(product);

    }
}
