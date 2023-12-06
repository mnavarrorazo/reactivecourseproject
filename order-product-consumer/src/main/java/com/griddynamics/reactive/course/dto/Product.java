package com.griddynamics.reactive.course.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Product {
    private String productId;
    private String productCode;
    private String productName;
    private double score;
}
