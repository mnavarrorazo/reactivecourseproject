package com.griddynamics.reactive.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.reactive.course.dto.OrderInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest
class OrderProductConsumerControllerTest {


    public static final int port = 8080;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    private String requestId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private WireMockServer wireMockServer;

    @BeforeEach
    void init() throws JsonProcessingException {
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
    }

    @AfterEach
    void end() {
        wireMockServer.stop();
    }

    @Test
    void getOrderInfoFromAllUsers_CountExpectedEight() throws JsonProcessingException {
        runServerGetAllOrdersInfo();
        //wiremockServer.start();
        requestId = UUID.randomUUID().toString();
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
    }

    @Test
    void getOrderInfoFromAllUsers_CountExpectedFour() throws JsonProcessingException {
        runServerGetAllOrdersInfoByUserIdFounded();
        Map<String, String> urlPathVariables = new HashMap<>();
        urlPathVariables.put("userId", "user1");

        //wiremockServer.start();
        requestId = UUID.randomUUID().toString();
        var uri = UriComponentsBuilder.fromUriString("/v1/orderInfoService/{userId}")
                .buildAndExpand(urlPathVariables)
                .toUriString();

        var ordersInfoFlux = webClient.get().uri(uri).headers(httpHeaders -> {
                    httpHeaders.add("requestId", requestId);
                })
                .retrieve()
                .bodyToFlux(OrderInfo.class);

        StepVerifier.create(ordersInfoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void getOrderInfoByUserIdThatDoesNotFind() throws JsonProcessingException {
        runServerGetAllOrdersInfoByUserIdDoesNotFind();
        Map<String, String> urlPathVariables = new HashMap<>();
        urlPathVariables.put("userId", "user2");

        //wiremockServer.start();
        requestId = UUID.randomUUID().toString();
        var uri = UriComponentsBuilder.fromUriString("/v1/orderInfoService/{userId}")
                .buildAndExpand(urlPathVariables)
                .toUriString();

        var ordersInfoFlux = webClient.get().uri(uri).headers(httpHeaders -> {
                    httpHeaders.add("requestId", requestId);
                })
                .retrieve()
                .bodyToFlux(OrderInfo.class);

        StepVerifier.create(ordersInfoFlux)
                .verifyComplete();
    }

    public void runServerGetAllOrdersInfo() throws JsonProcessingException {

        String response = "[\n" +
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
                "        \"orderNumber\": \"Order_3\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"9822\",\n" +
                "        \"productName\": \"Meal\",\n" +
                "        \"productId\": \"333\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_2\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"7894\",\n" +
                "        \"productName\": \"Meal\",\n" +
                "        \"productId\": \"333\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_3\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"9822\",\n" +
                "        \"productName\": \"Meal\",\n" +
                "        \"productId\": \"333\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_2\",\n" +
                "        \"userName\": \"Mark\",\n" +
                "        \"phoneNumber\": \"987654321\",\n" +
                "        \"productCode\": \"7894\",\n" +
                "        \"productName\": \"IceCream\",\n" +
                "        \"productId\": \"111\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_1\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"5256\",\n" +
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
                "    }\n" +
                "]";
        var list = objectMapper.readValue(response, new TypeReference<List<OrderInfo>>() {
        });
        wireMockServer.stubFor(get("/v1/orderInfoService")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(list))));

    }

    public void runServerGetAllOrdersInfoByUserIdFounded() throws JsonProcessingException {

        String response = "[\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_0\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"3852\",\n" +
                "        \"productName\": \"Milk\",\n" +
                "        \"productId\": \"222\"\n" +
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
                "        \"orderNumber\": \"Order_1\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"5256\",\n" +
                "        \"productName\": \"Milk\",\n" +
                "        \"productId\": \"222\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"orderNumber\": \"Order_3\",\n" +
                "        \"userName\": \"John\",\n" +
                "        \"phoneNumber\": \"123456789\",\n" +
                "        \"productCode\": \"9822\",\n" +
                "        \"productName\": \"Milk\",\n" +
                "        \"productId\": \"222\"\n" +
                "    }\n" +
                "]";
        var list = objectMapper.readValue(response, new TypeReference<List<OrderInfo>>() {
        });
        wireMockServer.stubFor(get("/v1/orderInfoService/user1")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(list))));
    }

    public void runServerGetAllOrdersInfoByUserIdDoesNotFind() throws JsonProcessingException {
        wireMockServer.stubFor(get("/v1/orderInfoService/user2")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(List.of()))));
    }

}