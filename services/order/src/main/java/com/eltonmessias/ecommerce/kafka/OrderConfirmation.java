package com.eltonmessias.ecommerce.kafka;

import com.eltonmessias.ecommerce.customer.CustomerResponse;
import com.eltonmessias.ecommerce.order.PaymentMethod;
import com.eltonmessias.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
