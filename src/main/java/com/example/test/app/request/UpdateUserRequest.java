package com.example.test.app.request;

import com.example.test.app.models.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;


    private Account account;

}
