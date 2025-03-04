package com.eltonmessias.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return new ResponseEntity<>(service.createCustomer(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.findCustomerById(customerId));
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> updateCustomer
            (@RequestBody @Valid CustomerRequest request, @PathVariable("customer-id") String customerId)
    {
        return ResponseEntity.ok(service.updateCustomer(request, customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("customer-id") String customerId) {
        service.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
