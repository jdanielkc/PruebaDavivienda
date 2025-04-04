package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.request.UserLoginRequest;
import com.co.davivienda.ti.prueba.models.request.UserRegisterRequest;
import com.co.davivienda.ti.prueba.models.response.UserLoginResponse;
import com.co.davivienda.ti.prueba.models.response.UserRegisterResponse;

/**
 * IUserService is an interface that defines the contract for user-related operations. It
 * provides methods to register and login users. This interface is
 * implemented by the UserService class.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
public interface IUserService {

    @Transactional
    public ResponseEntity<UserRegisterResponse> registerUser(UserRegisterRequest request);
    
    @Transactional(readOnly = true)
    public ResponseEntity<UserLoginResponse> loginUser(UserLoginRequest request);
}
