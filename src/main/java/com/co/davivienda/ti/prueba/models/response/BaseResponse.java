package com.co.davivienda.ti.prueba.models.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * BaseResponse class to represent a standard response structure.
 * It contains a message, a flag to show the message, and a success status.
 * 
 * This class is used as a base for other response classes to ensure consistency
 * in the API responses.
 * 
 * @Author Jose Daniel Garcia Arias
 * @Version 1.0.0
 * @Since 2025/04/03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable {
    private String message;
    private Boolean showMessage;
}
