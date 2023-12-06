package com.griddynamics.reactive.course.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter @Setter
@ToString
@AllArgsConstructor
public class OrderInfo {

    /*Required*/
    private String orderNumber;
    private String userName;
    private String phoneNumber;
    private String productCode;

    /*Not required*/
    private String productName;
    private String productId;

}
