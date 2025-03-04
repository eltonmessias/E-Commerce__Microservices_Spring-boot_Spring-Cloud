package com.eltonmessias.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerMapper mapper;
    private final CustomerRepository repository;
    public CustomerResponse createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return mapper.fromCustomer(customer);
    }
}
