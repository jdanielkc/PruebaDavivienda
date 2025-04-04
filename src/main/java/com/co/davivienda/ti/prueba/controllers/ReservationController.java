package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.dto.ReservationCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.ReservationUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllReservationsResponse;
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

    @GetMapping("/{userId}")
    public ResponseEntity<AllReservationsResponse> getUserReservations(
            @PathVariable String userId) {
        return reservationService.getUserReservations(userId);
    }

    @GetMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> getUserReservationById(
            @PathVariable String userId,
            @PathVariable Long reservationId) {
        return reservationService.getUserReservationById(userId, reservationId);
    }
    
    @PutMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable String userId,
            @PathVariable Long reservationId,
            @RequestBody ReservationUpdateDTO reservationUpdateDTO) {
        return reservationService.updateReservation(userId, reservationId, reservationUpdateDTO);
    }
}