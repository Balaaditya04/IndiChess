package com.example.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Size(min = 4, max = 50,
            message = "Username must have characters between 4 and 50")

    @Column(name = "user_name")
    String username;

    @Column(name = "email_id")
    String emailId;

    @Size(min=6,max=512)
    String password;

    String country;

    @Column(name = "provider")
    private String provider = "LOCAL"; // LOCAL or GOOGLE


}

