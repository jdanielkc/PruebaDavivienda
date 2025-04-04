package com.co.davivienda.ti.prueba.models.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest implements Serializable {

    private String email;
    
    private String password;

}
