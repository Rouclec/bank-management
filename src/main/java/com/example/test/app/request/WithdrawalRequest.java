package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WithdrawalRequest {

        private String accountNumber;
        private Long amount;

}
