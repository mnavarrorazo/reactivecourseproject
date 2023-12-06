package com.griddynamics.reactive.course.util;

import com.griddynamics.reactive.course.dto.Product;
import reactor.core.publisher.Flux;
import java.util.Comparator;

public class Utilities {

    public static Flux<Product> getProductWithHigherScore(Flux<Product> productFlux) {
        return productFlux.sort(Comparator.comparing(Product::getScore).reversed())
                .take(1);
    }
}
