package com.co.davivienda.ti.prueba.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * UserLoginResponse is a Data Transfer Object (DTO) used for returning user login information.
 * It extends the BaseResponse class and contains user-specific details such as token, email, first name, last name, and user ID.
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
@EqualsAndHashCode(callSuper = false)
public class UserLoginResponse extends BaseResponse {

    private String token;
    
    private String email;

    private String firstName;
    
    private String lastName;

    private Long userId;

}
