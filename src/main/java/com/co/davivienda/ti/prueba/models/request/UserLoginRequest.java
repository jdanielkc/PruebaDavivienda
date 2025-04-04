package com.co.davivienda.ti.prueba.models.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * UserLoginRequest is a Data Transfer Object (DTO) used for user login requests.
 * It contains the necessary fields for user authentication, such as email and password.
 * This class is annotated with Lombok annotations to generate boilerplate code like getters, setters, and constructors.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest implements Serializable {

    private String email;
    
    private String password;

}
