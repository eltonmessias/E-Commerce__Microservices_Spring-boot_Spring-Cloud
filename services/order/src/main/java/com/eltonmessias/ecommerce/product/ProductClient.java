package com.eltonmessias.ecommerce.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(
        url = "${application.config.product-url}"
)
public interface ProductClient {
    @PostMapping("/purchase")
    Optional<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> request);
}
