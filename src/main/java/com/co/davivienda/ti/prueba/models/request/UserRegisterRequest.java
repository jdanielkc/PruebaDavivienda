package com.co.davivienda.ti.prueba.models.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRegisterRequest.java
 * 
 * This class represents the request object for user registration.
 * It contains fields for first name, last name, email, password, and confirm password.
 * The class is annotated with Lombok annotations to generate boilerplate code such as getters, setters, and constructors.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest implements Serializable{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
