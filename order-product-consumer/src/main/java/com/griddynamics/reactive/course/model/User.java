package com.griddynamics.reactive.course.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String name;

    private String phone;
}
