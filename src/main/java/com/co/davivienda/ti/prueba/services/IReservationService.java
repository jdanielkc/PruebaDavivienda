package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.ReservationCreateDTO;
import com.co.davivienda.ti.prueba.models.response.ReservationResponse;

public interface IReservationService {

    @Transactional
    public ResponseEntity<ReservationResponse> createReservation(String userId,
            ReservationCreateDTO reservationCreateDTO);

    @Transactional
    public ResponseEntity<ReservationResponse> deleteReservation(String userId, Long reservationId);
}