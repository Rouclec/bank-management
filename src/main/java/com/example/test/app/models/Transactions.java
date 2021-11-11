package com.example.test.app.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transactions {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long transactionId;
        private String transactionType;
        private String senderAccountNumber;
        private String receiverAccountNumber;
        private Long amount;
        private LocalDateTime createdOn;
        private String createdBy;
        private LocalDateTime lastModifiedOn;
        private String lastModifiedBy;
        private String status;


}
