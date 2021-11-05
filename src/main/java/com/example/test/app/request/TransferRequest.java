package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TransferRequest {

        private String senderAccountNumber;
        private String receiverAccountNumber;
        private Long amount;

}
