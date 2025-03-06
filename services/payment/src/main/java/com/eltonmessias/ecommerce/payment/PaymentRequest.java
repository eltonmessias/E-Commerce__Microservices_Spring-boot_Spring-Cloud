package com.eltonmessias.ecommerce.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderId,
        String orderReference,
        Customer customer
) {
}
