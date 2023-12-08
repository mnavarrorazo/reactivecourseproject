package com.griddynamics.reactive.course.dto;

import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
