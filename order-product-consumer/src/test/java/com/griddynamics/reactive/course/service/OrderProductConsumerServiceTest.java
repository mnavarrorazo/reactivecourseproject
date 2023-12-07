package com.griddynamics.reactive.course.service;

import com.griddynamics.reactive.course.iservice.IOrderProductConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.UUID;

@SpringBootTest
public class OrderProductConsumerServiceTest {

    @Autowired
    private IOrderProductConsumerService orderProductConsumerService;

    private String requestId;

    @BeforeEach
    public void initializeMethod() {
        requestId = UUID.randomUUID().toString();
    }

    @Test
    void getOrderInfoFromAllUsers_CountExpectedEight() {
        var ordersInfoFlux = orderProductConsumerService.getOrderInfoFromAllUsers(requestId);
        StepVerifier.create(ordersInfoFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    void getOrderInfoByUserId() {
        String userId = "user1";
        var ordersInfoFlux = orderProductConsumerService.getOrderInfoByUserId(requestId, userId);
        StepVerifier.create(ordersInfoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void getOrderInfoByUserIdThatDoesNotExists() {
        String userId = "user5";
        var ordersInfoFlux = orderProductConsumerService.getOrderInfoByUserId(requestId, userId);
        StepVerifier.create(ordersInfoFlux)
                .verifyComplete();
    }
}