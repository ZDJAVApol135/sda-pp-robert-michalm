package com.sda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true)
    private String username;

    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String surname;

    private int age;

    private String email;

}
