package com.griddynamics.reactive.course.iservice;

import com.griddynamics.reactive.course.dto.OrderInfo;
import reactor.core.publisher.Flux;

public interface IOrderProductConsumerService {

    Flux<OrderInfo> getOrderInfoFromAllUsers(String requestId);
    Flux<OrderInfo> getOrderInfoByUserId(String requestId, String userId);
}
