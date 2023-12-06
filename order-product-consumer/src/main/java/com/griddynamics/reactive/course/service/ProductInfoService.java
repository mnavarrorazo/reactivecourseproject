package com.griddynamics.reactive.course.service;

import com.griddynamics.reactive.course.dto.Product;
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
public class ProductInfoService {

    @Qualifier("webClientProductService")
    @Autowired
    private WebClient webClient;

    public Flux<Product> getProductNamesByProductCode(String productCode) {
        var uri = UriComponentsBuilder.fromUriString("/product/names")
                .queryParam("productCode", productCode)
                .buildAndExpand()
                .toUriString();
        return webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
