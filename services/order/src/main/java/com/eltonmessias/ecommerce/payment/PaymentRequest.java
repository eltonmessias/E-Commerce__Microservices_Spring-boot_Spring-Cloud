package com.eltonmessias.ecommerce.payment;

import com.eltonmessias.ecommerce.customer.CustomerResponse;
import com.eltonmessias.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        java.util.UUID orderId,
        String orderReference,
        CustomerResponse customer
) {
}
