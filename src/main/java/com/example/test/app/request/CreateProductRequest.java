package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateProductRequest {

    private String productName;
    private Date productDuration;
    private String description;
    private Long maximumAmount;
}
