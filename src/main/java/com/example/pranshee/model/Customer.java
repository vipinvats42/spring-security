package com.example.pranshee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
// In case if we are using some different table name than the class name, we can
// use @Table annotation to specify the table name
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    // In case if we are using some different column name than the field name, we
    // can use @Column annotation to specify the column name
    // @Column(name = "email")
    @Column(name = "email")
    private String email;
    private String pwd;
    private String role;

}
