package com.example.test.app.request;

import com.example.test.app.models.Shortee;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateAdminRequest {
    private  Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;


}