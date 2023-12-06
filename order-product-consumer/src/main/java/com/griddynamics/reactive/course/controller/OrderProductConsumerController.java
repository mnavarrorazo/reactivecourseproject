package com.griddynamics.reactive.course.controller;

import com.griddynamics.reactive.course.dto.OrderInfo;
import com.griddynamics.reactive.course.iservice.IOrderProductConsumerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/orderInfoService")
@AllArgsConstructor
public class OrderProductConsumerController {

    private final IOrderProductConsumerService orderProductConsumerService;

    @GetMapping(value = "")
    public Flux<OrderInfo> getOrdersInfoFromAllUsers(@RequestHeader(name = "requestId") String requestId) {
        return orderProductConsumerService.getOrderInfoFromAllUsers(requestId);
    }

    @GetMapping(value = "/{userId}")
    public Flux<OrderInfo> getOrdersInfoByUserId(@RequestHeader(name = "requestId") String requestId
            , @PathVariable(name = "userId") String userId) {
        return orderProductConsumerService.getOrderInfoByUserId(requestId, userId);
    }
}
