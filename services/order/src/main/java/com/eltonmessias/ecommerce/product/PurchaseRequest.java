package com.eltonmessias.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PurchaseRequest(
        @NotNull(message = "Product is mandatory")
        UUID productId,
        @Positive(message = "The quantity is required and should be positive")
        Integer quantity
) {
}
