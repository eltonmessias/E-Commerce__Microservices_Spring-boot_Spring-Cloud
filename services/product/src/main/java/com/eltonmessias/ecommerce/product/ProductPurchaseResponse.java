package com.eltonmessias.ecommerce.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPurchaseResponse(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
