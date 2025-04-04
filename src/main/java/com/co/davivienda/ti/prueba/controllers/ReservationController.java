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

/**
 * ReservationController handles reservation-related operations such as creating, deleting, and retrieving reservations.
 * It provides endpoints for reservation management functionalities.
 *
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final IReservationService reservationService;

    /**
     * Endpoint to create a new reservation for a specific user.
     * 
     * @param userId the ID of the user
     * @param reservationCreateDTO the reservation creation request
     * @return the response entity containing the created reservation
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> createReservation(
            @PathVariable String userId,
            @RequestBody ReservationCreateDTO reservationCreateDTO) {
        return reservationService.createReservation(userId, reservationCreateDTO);
    }

    /**
     * Endpoint to delete a reservation by its ID for a specific user.
     * 
     * @param userId the ID of the user
     * @param reservationId the ID of the reservation
     * @return the response entity indicating the deletion result
     */
    @DeleteMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> deleteReservation(
            @PathVariable String userId,
            @PathVariable Long reservationId) {
        return reservationService.deleteReservation(userId, reservationId);
    }

    /**
     * Endpoint to retrieve all reservations for a specific user.
     * 
     * @param userId the ID of the user
     * @return the response entity containing all reservations for the user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<AllReservationsResponse> getUserReservations(
            @PathVariable String userId) {
        return reservationService.getUserReservations(userId);
    }

    /**
     * Endpoint to retrieve a reservation by its ID for a specific user.
     * 
     * @param userId the ID of the user
     * @param reservationId the ID of the reservation
     * @return the response entity containing the reservation details
     */
    @GetMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> getUserReservationById(
            @PathVariable String userId,
            @PathVariable Long reservationId) {
        return reservationService.getUserReservationById(userId, reservationId);
    }
    
    /**
     * Endpoint to update an existing reservation for a specific user.
     * 
     * @param userId the ID of the user
     * @param reservationId the ID of the reservation
     * @param reservationUpdateDTO the reservation update request
     * @return the response entity containing the updated reservation details
     */
    @PutMapping("/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable String userId,
            @PathVariable Long reservationId,
            @RequestBody ReservationUpdateDTO reservationUpdateDTO) {
        return reservationService.updateReservation(userId, reservationId, reservationUpdateDTO);
    }
}