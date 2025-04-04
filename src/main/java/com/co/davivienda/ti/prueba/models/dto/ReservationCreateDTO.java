package com.co.davivienda.ti.prueba.models.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateDTO implements Serializable {
    private Long eventId;
    private String status;
}