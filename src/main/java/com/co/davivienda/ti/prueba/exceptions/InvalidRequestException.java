package com.co.davivienda.ti.prueba.exceptions;

/**
 * InvalidRequestException is a custom exception class that extends RuntimeException.
 * It is used to indicate that a request made to the system is invalid or cannot be processed.
 * This exception can be thrown when the input data does not meet the required criteria or format.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
public class InvalidRequestException extends RuntimeException {
    
    public InvalidRequestException(String message) {
        super(message);
    }
}