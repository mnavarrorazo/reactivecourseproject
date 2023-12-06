package com.griddynamics.reactive.course.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${services.order-search}")
    private String base;

    @Value("${services.order-search}")
    private String orderSearchUri;

    @Value("${services.product-info}")
    private String productInfoUri;

    @Bean(name = "webClientOrderSearchService")
    public WebClient getWebClientOrderSearchService() {
        return WebClient.builder()
                .baseUrl(orderSearchUri)
                .build();
    }

    @Bean(name = "webClientProductService")
    public WebClient getWebClientProductInfoService() {
        return WebClient.builder()
                .baseUrl(productInfoUri)
                .build();
    }

}
