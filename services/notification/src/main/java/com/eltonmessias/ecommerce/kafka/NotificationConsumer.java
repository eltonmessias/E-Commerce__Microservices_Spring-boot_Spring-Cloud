package com.eltonmessias.ecommerce.kafka;

import com.eltonmessias.ecommerce.kafka.payment.PaymentConfirmation;
import com.eltonmessias.ecommerce.notification.Notification;
import com.eltonmessias.ecommerce.notification.NotificationRepository;
import com.eltonmessias.ecommerce.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;


    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) {
        log.info("Consuming the message from payment-topic:: {}", paymentConfirmation);
        repository.save(
                Notification.builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        //send email
        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();

    }

}