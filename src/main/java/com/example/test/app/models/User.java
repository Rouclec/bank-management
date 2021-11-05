package com.example.test.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
    private /*Role*/ String role = "ROLE_USER";//new Role(null, "ROLE_USER");

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_account_number")
    private List<Account> accountList;

    @JsonIgnore
    @ManyToMany
    private List<Shortee> shorteeList;

    private LocalDateTime dateCreated;

}
