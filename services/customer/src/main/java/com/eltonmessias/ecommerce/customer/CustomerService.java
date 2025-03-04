package com.eltonmessias.ecommerce.customer;

import com.eltonmessias.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerMapper mapper;
    private final CustomerRepository repository;


    public CustomerResponse createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return mapper.fromCustomer(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
    }

    public CustomerResponse findCustomerById(String customerId) {
        var customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer does not exists!"));
        return mapper.fromCustomer(customer);
    }

    public CustomerResponse updateCustomer(@Valid CustomerRequest request, String customerId) {
        var customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer does not exists!"));
        mergeCustomer(customer, request);
        repository.save(customer);
        return mapper.fromCustomer(customer);
    }
    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public void deleteCustomer(String customerId) {
        var customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer does not exists"));
        repository.delete(customer);
    }
}
