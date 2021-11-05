package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionRequest {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Long amount;
}
