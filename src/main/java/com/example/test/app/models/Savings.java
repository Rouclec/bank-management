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
public class Savings {


        @Id
        private Long transactionId;
        private String accountNumber;
        private Long amount;
        private LocalDateTime date;
        private String status;

}
