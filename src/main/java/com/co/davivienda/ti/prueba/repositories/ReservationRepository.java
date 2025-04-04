package com.co.davivienda.ti.prueba.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.davivienda.ti.prueba.entities.Reservation;

/**
 * Repository interface for Reservation entity.
 * This interface extends JpaRepository to provide CRUD operations for
 * Reservation.
 * It is annotated with @Repository to indicate that it is a Spring Data
 * repository.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserIdOrderByReservationDateDesc(Long userId);

    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);
}