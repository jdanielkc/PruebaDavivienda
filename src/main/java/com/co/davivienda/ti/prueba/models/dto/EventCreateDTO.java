package com.co.davivienda.ti.prueba.models.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EventCreateDTO is a Data Transfer Object (DTO) used for creating a new event.
 * It contains the necessary fields for event creation, such as title, description, event date, location, capacity, and category ID.
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
public class EventCreateDTO implements Serializable {
    private String title;
    private String description;
    private LocalDate eventDate;
    private String location;
    private Integer capacity;
    private Long categoryId;
}