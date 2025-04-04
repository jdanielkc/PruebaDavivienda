package com.co.davivienda.ti.prueba.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.request.UserRegisterRequest;
import com.co.davivienda.ti.prueba.models.response.UserRegisterResponse;
import com.co.davivienda.ti.prueba.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api/davi-events/users")
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to register a new user.
     * @param request the user registration request
     * @return the response entity containing the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> postUserRegister(@RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

}
