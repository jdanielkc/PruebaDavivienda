package com.co.davivienda.ti.prueba.models.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ReservationDTO is a Data Transfer Object (DTO) representing a reservation in the system.
 * It contains fields for reservation attributes such as id, reservation date, status, user ID, event ID, and event title.
 * This class is annotated with Lombok annotations to generate boilerplate code like getters, setters, and constructors.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO implements Serializable {
    private Long id;
    private String reservationDate;
    private String status;
    private Long userId;
    private Long eventId;
    private String eventTitle;
}