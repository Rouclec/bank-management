package com.example.test.app.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private String accountNumber = UUID.randomUUID().toString().replace("-","").substring(0,9);
    private Long accountBalance;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime expiration;

    private boolean isActive;

    private boolean isSuspended;

    private String userName;
    private LocalDateTime createdOn;

}
