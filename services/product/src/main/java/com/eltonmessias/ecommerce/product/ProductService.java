package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import com.eltonmessias.ecommerce.exception.ProductNotFoundException;
import com.eltonmessias.ecommerce.exception.ProductPurchaseException;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        var storedRequests = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequests.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public void deleteProduct(Long productId) {
        var product = repository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        repository.delete(product);
    }
}
