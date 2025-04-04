package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.dto.ReservationCreateDTO;
import com.co.davivienda.ti.prueba.models.response.ReservationResponse;
import com.co.davivienda.ti.prueba.services.IReservationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> createReservation(
            @PathVariable String userId,
            @RequestBody ReservationCreateDTO reservationCreateDTO) {
        return reservationService.createReservation(userId, reservationCreateDTO);
    }

    @DeleteMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> deleteReservation(
            @PathVariable String userId,
            @PathVariable Long reservationId) {
        return reservationService.deleteReservation(userId, reservationId);
    }
}