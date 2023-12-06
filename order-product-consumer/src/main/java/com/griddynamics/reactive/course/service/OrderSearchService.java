package com.griddynamics.reactive.course.service;

import com.griddynamics.reactive.course.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Service
@Configuration
@Log4j2
public class OrderSearchService {

    @Qualifier("webClientOrderSearchService")
    @Autowired
    private WebClient webClient;

    public Flux<Order> getOrderByPhoneNumber(String phoneNumber) {
        var uri = UriComponentsBuilder.fromUriString("/order/phone")
                .queryParam("phoneNumber", phoneNumber)
                .buildAndExpand()
                .toUriString();
         return webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(Order.class);
    }

}
