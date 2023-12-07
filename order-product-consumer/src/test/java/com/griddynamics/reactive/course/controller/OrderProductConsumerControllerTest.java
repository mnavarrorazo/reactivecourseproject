package com.griddynamics.reactive.course.controller;

import com.griddynamics.reactive.course.dto.OrderInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.util.UUID;

@SpringBootTest
public class OrderProductConsumerControllerTest {

    static int port = 8080;

    private static WireMockServer wireMockServer;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    private String requestId;

    @BeforeEach
    void startUp() {
    }

    @AfterEach
    void endUp() {

    }

    @Test
    void getOrderInfoFromAllUsers_CountExpectedEight() {
        wiremockServer.start();
        requestId = UUID.randomUUID().toString();
        configure();
        var uri = UriComponentsBuilder.fromUriString("/v1/orderInfoService")
                .buildAndExpand()
                .toUriString();

        var ordersInfoFlux = webClient.get().uri(uri).headers(httpHeaders -> {
                    httpHeaders.add("requestId", requestId);
                })
                .retrieve()
                .bodyToFlux(OrderInfo.class);

        StepVerifier.create(ordersInfoFlux)
                .expectNextCount(8)
                .verifyComplete();
        wiremockServer.stop();
    }

    /*
    //@Test
    void getOrderInfoByUserId() {
        String userId = "user1";
        var ordersInfoFlux = orderProductConsumerService.getOrderInfoByUserId(requestId, userId);
        StepVerifier.create(ordersInfoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    //@Test
    void getOrderInfoByUserIdThatDoesNotExists() {
        String userId = "user5";
        var ordersInfoFlux = orderProductConsumerService.getOrderInfoByUserId(requestId, userId);
        StepVerifier.create(ordersInfoFlux)
                .verifyComplete();
    }*/

    public void configure() {
        String response = "[\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_1\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"5256\",\n" +
                "        \"productName\": \"IceCream\",\n" +
                "        \"productId\": \"111\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_0\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"3852\",\n" +
                "        \"productName\": \"Milk\",\n" +
                "        \"productId\": \"222\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_0\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"3852\",\n" +
                "        \"productName\": \"Meal\",\n" +
                "        \"productId\": \"333\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_1\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"5256\",\n" +
                "        \"productName\": \"Milk\",\n" +
                "        \"productId\": \"222\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_3\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"9822\",\n" +
                "        \"productName\": \"Apple\",\n" +
                "        \"productId\": \"444\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_3\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"9822\",\n" +
                "        \"productName\": \"Apple\",\n" +
                "        \"productId\": \"444\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_2\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"7894\",\n" +
                "        \"productName\": \"IceCream\",\n" +
                "        \"productId\": \"111\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_2\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"7894\",\n" +
                "        \"productName\": \"Apple\",\n" +
                "        \"productId\": \"444\"\n" +
                "    }\n" +
                "]";
        stubFor(get("/v1/orderInfoService")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));
    }

}