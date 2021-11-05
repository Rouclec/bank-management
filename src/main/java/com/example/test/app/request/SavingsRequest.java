package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class SavingsRequest {

    private String accountNumber;
    private Long amount;
}
