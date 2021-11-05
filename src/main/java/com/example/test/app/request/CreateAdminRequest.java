package com.example.test.app.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdminRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;

    @NotNull
    private String shortee1Name;

//    @NotNull
//    private String shortee2Name;


}
