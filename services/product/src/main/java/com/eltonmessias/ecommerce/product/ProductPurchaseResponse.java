package com.eltonmessias.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
