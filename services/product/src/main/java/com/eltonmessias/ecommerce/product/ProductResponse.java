package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer availableQuantity,
        UUID categoryId,
        String categoryName
) {
}
