package com.eltonmessias.ecommerce.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public UUID createPayment(@Valid PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        return payment.getId();
    }
}
