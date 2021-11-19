package com.example.test.app.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String institutionName;
}
