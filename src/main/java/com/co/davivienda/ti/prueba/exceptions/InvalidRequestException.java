package com.co.davivienda.ti.prueba.exceptions;

public class InvalidRequestException extends RuntimeException {
    
    public InvalidRequestException(String message) {
        super(message);
    }
}