package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double price,
        Integer availableQuantity,
        Long categoryId,
        String categoryName
) {
}
