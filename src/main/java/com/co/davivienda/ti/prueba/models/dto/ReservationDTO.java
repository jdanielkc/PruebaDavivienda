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
public class ReservationDTO implements Serializable {
    private Long id;
    private String reservationDate;
    private String status;
    private Long userId;
    private Long eventId;
    private String eventTitle;
}