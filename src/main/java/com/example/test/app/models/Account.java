package com.example.test.app.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private String accountNumber;
    private Long accountBalance;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime expiration;

    private boolean isActive;

    private boolean isSuspended;

    private String userName;
}
