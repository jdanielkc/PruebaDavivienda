package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.request.UserRegisterRequest;
import com.co.davivienda.ti.prueba.models.response.UserRegisterResponse;

public interface IUserService {

    @Transactional
    public ResponseEntity<UserRegisterResponse> registerUser(UserRegisterRequest request);
    
}
