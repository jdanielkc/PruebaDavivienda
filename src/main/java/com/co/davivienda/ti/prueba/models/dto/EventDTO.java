package com.co.davivienda.ti.prueba.models.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EventDTO is a Data Transfer Object (DTO) representing an event in the system.
 * It contains fields for event attributes such as id, name, description, event date, category ID, and category name.
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
public class EventDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDate eventDate;
    private Long categoryId;
    private String categoryName;
    private Integer capacity;
    private String location;
}