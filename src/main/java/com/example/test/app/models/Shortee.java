package com.example.test.app.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Shortee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String idCardPhoto;

}
