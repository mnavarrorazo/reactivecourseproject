package com.griddynamics.reactive.course.service;

import com.griddynamics.reactive.course.dto.OrderInfo;
import com.griddynamics.reactive.course.model.User;
import com.griddynamics.reactive.course.util.LoggerService;
import com.griddynamics.reactive.course.util.Utilities;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class OrderInfoService {

    private final OrderSearchService orderSearchService;

    private final ProductInfoService productInfoService;

    private static final Logger LOG = LogManager.getLogger(OrderInfoService.class);

    public Flux<OrderInfo> getOrderInfoFlux(User user) {
        return orderSearchService.getOrderByPhoneNumber(user.getPhone())
                .doOnEach(LoggerService.logOnNext(o -> LOG.info("OrderSearchService Response: {}", o)))
                .subscribeOn(Schedulers.parallel())//Orders
                .flatMap(o -> Utilities.getProductWithHigherScore(
                        productInfoService.getProductNamesByProductCode(o.getProductCode())
                                .doOnEach(LoggerService.logOnNext(p -> LOG.info("ProductInfoService Response: {}", p)))
                                .doOnEach(LoggerService.logOnError(e -> LOG.error("Exception occurred in getProductNamesByProductCode with productCode {} -> exception {}",
                                        o.getProductCode(),
                                        e.getMessage())))
                                .onErrorResume(ex -> Flux.defer(Flux::empty))
                ).map(p -> new OrderInfo(o.getOrderNumber(), user.getName(), user.getPhone(), p.getProductCode(), p.getProductName(), p.getProductId())))
                .subscribeOn(Schedulers.parallel());
    }
}
