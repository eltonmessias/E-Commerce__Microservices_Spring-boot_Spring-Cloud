package com.eltonmessias.ecommerce.order;

import com.eltonmessias.ecommerce.customer.CustomerClient;
import com.eltonmessias.ecommerce.exception.BusinessException;
import com.eltonmessias.ecommerce.kafka.OrderConfirmation;
import com.eltonmessias.ecommerce.kafka.OrderProducer;
import com.eltonmessias.ecommerce.orderline.OrderLineRequest;
import com.eltonmessias.ecommerce.orderline.OrderLineService;
import com.eltonmessias.ecommerce.payment.PaymentClient;
import com.eltonmessias.ecommerce.payment.PaymentRequest;
import com.eltonmessias.ecommerce.product.ProductClient;
import com.eltonmessias.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public UUID createOrder(@Valid OrderRequest request) {

        // todo check the customer ---> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer found with the provided id:: " + request.customerId()));


        // todo purchase the products ---> OpenFeign

        var purchasedProducts = this.productClient.purchaseProducts(request.products());


        // todo persist the order
        var order = this.repository.save(mapper.toOrder(request));

        // todo persist the orderLines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start the payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // todo send the order confirmation ---> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

      return order.getId();
    }
}
