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
public class EventUpdateDTO implements Serializable {
    private String title;
    private String description;
    private LocalDate eventDate;
    private String location;
    private Integer capacity;
    private Long categoryId;
}