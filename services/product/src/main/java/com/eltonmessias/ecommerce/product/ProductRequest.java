package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        UUID id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Price should be positive")
        BigDecimal price,
        @Positive(message = "Available quantity should be positive")
        Integer availableQuantity,
        @NotNull(message = "Product category is required")
        Long categoryId
) {
}
