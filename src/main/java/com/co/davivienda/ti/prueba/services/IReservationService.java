package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.ReservationCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.ReservationUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllReservationsResponse;
import com.co.davivienda.ti.prueba.models.response.ReservationResponse;

/**
 * IReservationService is an interface that defines the contract for reservation-related operations. It
 * provides methods to create, delete, update, and retrieve reservations. This interface is
 * implemented by the ReservationService class.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
public interface IReservationService {

    @Transactional
    public ResponseEntity<ReservationResponse> createReservation(String userId,
            ReservationCreateDTO reservationCreateDTO);

    @Transactional
    public ResponseEntity<ReservationResponse> deleteReservation(String userId, Long reservationId);

    @Transactional(readOnly = true)
    public ResponseEntity<AllReservationsResponse> getUserReservations(String userId);

    @Transactional(readOnly = true)
    public ResponseEntity<ReservationResponse> getUserReservationById(String userId, Long reservationId);

    @Transactional
    public ResponseEntity<ReservationResponse> updateReservation(String userId, Long reservationId,
            ReservationUpdateDTO reservationUpdateDTO);
}