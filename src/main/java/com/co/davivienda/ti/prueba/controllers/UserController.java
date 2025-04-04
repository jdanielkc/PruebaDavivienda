package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.request.UserLoginRequest;
import com.co.davivienda.ti.prueba.models.request.UserRegisterRequest;
import com.co.davivienda.ti.prueba.models.response.UserLoginResponse;
import com.co.davivienda.ti.prueba.models.response.UserRegisterResponse;
import com.co.davivienda.ti.prueba.services.IUserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    /**
     * Endpoint to register a new user.
     * 
     * @param request the user registration request
     * @return the response entity containing the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> postUserRegister(@RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

    /**
     * Endpoint to login a user.
     * 
     * @param request the user login request
     * @return the response entity containing the login result
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> postUserLogin(@RequestBody UserLoginRequest request) {
        return userService.loginUser(request);

    }
}
