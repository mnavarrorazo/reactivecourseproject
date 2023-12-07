package com.griddynamics.reactive.course.service;

import com.griddynamics.reactive.course.dto.OrderInfo;
import com.griddynamics.reactive.course.iservice.IOrderProductConsumerService;
import com.griddynamics.reactive.course.repository.UserInfoRepository;
import com.griddynamics.reactive.course.util.LoggerService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Service
@AllArgsConstructor
public class OrderProductConsumerService implements IOrderProductConsumerService {

    private final UserInfoRepository userRepository;

    private final OrderInfoService orderInfoService;

    private static final Logger LOG = LogManager.getLogger(OrderProductConsumerService.class);


    @Override
    public Flux<OrderInfo> getOrderInfoFromAllUsers(String requestId) {
        return userRepository.findAll()
                .doOnEach(LoggerService.logOnNext(u -> LOG.info("Orders of this phone : -> {}",
                        u.getPhone())))
                .flatMap(orderInfoService::getOrderInfoFlux)
                .doOnEach(LoggerService.logOnNext(o -> LOG.info("{}", o)))
                .doOnEach(LoggerService.logOnError(e -> LOG.error("Exception occurred in getOrderInfoFromAllUsers: -> {}",
                        e.getMessage())))
                .subscribeOn(Schedulers.parallel())
                .contextWrite(Context.of("requestId", requestId));
    }

    @Override
    public Flux<OrderInfo> getOrderInfoByUserId(String requestId, String userId) {

        return userRepository.findById(userId)
                .doOnEach(LoggerService.logOnNext(u -> LOG.info("Orders of this phone : -> {}",
                        u.getPhone())))
                .flatMapMany(orderInfoService::getOrderInfoFlux)
                .doOnEach(LoggerService.logOnNext(o -> LOG.info("{}", o)))
                .doOnEach(LoggerService.logOnError(e -> LOG.error("Exception occurred in getOrderInfoFromAllUsers: -> {}",
                        e.getMessage())))
                .subscribeOn(Schedulers.parallel())
                .contextWrite(Context.of("requestId", requestId));
    }
}
