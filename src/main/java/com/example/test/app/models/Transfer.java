package com.example.test.app.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transfer {
    @Id
    private Long transactionId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Long amount;
    private LocalDateTime date;
    private String status;
}
