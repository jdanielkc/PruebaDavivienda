package com.co.davivienda.ti.prueba.models.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}