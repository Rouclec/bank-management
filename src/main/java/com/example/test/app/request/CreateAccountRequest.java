package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private Long durationInMonths;
    private String productName;
}
