package com.co.davivienda.ti.prueba.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.entities.Event;
import com.co.davivienda.ti.prueba.entities.Reservation;
import com.co.davivienda.ti.prueba.entities.User;
import com.co.davivienda.ti.prueba.models.dto.ReservationCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.ReservationDTO;
import com.co.davivienda.ti.prueba.models.dto.ReservationUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllReservationsResponse;
import com.co.davivienda.ti.prueba.models.response.ReservationResponse;
import com.co.davivienda.ti.prueba.repositories.EventRepository;
import com.co.davivienda.ti.prueba.repositories.ReservationRepository;
import com.co.davivienda.ti.prueba.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

        private static final String ERROR_INVALID_USER_ID = "ID de usuario inválido";
        private static final String ERROR_USER_NOT_FOUND = "Usuario no encontrado";
        private static final String ERROR_EVENT_NOT_FOUND = "Evento no encontrado";
        private static final String ERROR_EVENT_FULL = "El evento ha alcanzado su capacidad máxima";

        private final ReservationRepository reservationRepository;
        private final UserRepository userRepository;
        private final EventRepository eventRepository;

        /**
         * Creates a new reservation with the provided data.
         * Validates the user ID and event ID before creating the reservation.
         *
         * @param userId               The ID of the user creating the reservation.
         * @param reservationCreateDTO Data for the new reservation.
         * @return A ResponseEntity containing the created reservation or an error
         *         message.
         */
        @Override
        @Transactional
        public ResponseEntity<ReservationResponse> createReservation(String userId,
                        ReservationCreateDTO reservationCreateDTO) {
                try {
                        // Validate User ID
                        Optional<ResponseEntity<ReservationResponse>> userValidationError = validateUserId(userId);
                        if (userValidationError.isPresent()) {
                                return userValidationError.get();
                        }

                        Long userIdLong = Long.parseLong(userId);
                        User user = userRepository.findById(userIdLong).orElse(null);
                        if (user == null) {
                                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_USER_NOT_FOUND)
                                                                .build());
                        }

                        // Validate Event ID
                        Long eventId = reservationCreateDTO.getEventId();
                        if (eventId == null) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message("ID de evento es requerido")
                                                                .build());
                        }

                        Event event = eventRepository.findById(eventId).orElse(null);
                        if (event == null) {
                                log.warn(ERROR_EVENT_NOT_FOUND + " con ID: {}", eventId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_EVENT_NOT_FOUND)
                                                                .build());
                        }

                        // Check if event is at capacity
                        if (event.getReservations() != null &&
                                        event.getReservations().size() >= event.getCapacity()) {
                                log.warn(ERROR_EVENT_FULL + " - Evento ID: {}", eventId);
                                return ResponseEntity.status(HttpStatus.CONFLICT)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_EVENT_FULL)
                                                                .build());
                        }

                        // Create reservation
                        String status = (reservationCreateDTO.getStatus() != null
                                        && !reservationCreateDTO.getStatus().isEmpty())
                                                        ? reservationCreateDTO.getStatus()
                                                        : "PENDIENTE";

                        Reservation newReservation = Reservation.builder()
                                        .reservationDate(LocalDate.now().toString())
                                        .status(status)
                                        .user(user)
                                        .event(event)
                                        .build();

                        // Save reservation
                        Reservation savedReservation = reservationRepository.save(newReservation);

                        // Convert to DTO
                        ReservationDTO reservationDTO = convertToDTO(savedReservation);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(ReservationResponse.builder()
                                                        .reservation(reservationDTO)
                                                        .showMessage(true)
                                                        .message("Reserva creada exitosamente")
                                                        .build());

                } catch (Exception e) {
                        log.error("Error al crear la reserva", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ReservationResponse.builder()
                                                        .showMessage(true)
                                                        .message("Error al crear la reserva")
                                                        .build());
                }
        }

        /**
         * Validates and parses the user ID from string to Long.
         *
         * @param userId The user ID as string to validate.
         * @return Optional containing error response if validation fails, or empty if
         *         successful.
         */
        private Optional<ResponseEntity<ReservationResponse>> validateUserId(String userId) {
                try {
                        Long.parseLong(userId);
                        return Optional.empty();
                } catch (NumberFormatException e) {
                        log.warn(ERROR_INVALID_USER_ID + ": {}", userId);
                        return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(ReservationResponse.builder()
                                                        .showMessage(true)
                                                        .message(ERROR_INVALID_USER_ID)
                                                        .build()));
                }
        }

        /**
         * Converts a Reservation entity to a ReservationDTO.
         *
         * @param reservation The Reservation entity to convert.
         * @return The converted ReservationDTO.
         */
        private ReservationDTO convertToDTO(Reservation reservation) {
                return ReservationDTO.builder()
                                .id(reservation.getId())
                                .reservationDate(reservation.getReservationDate())
                                .status(reservation.getStatus())
                                .userId(reservation.getUser() != null ? reservation.getUser().getId() : null)
                                .eventId(reservation.getEvent() != null ? reservation.getEvent().getId() : null)
                                .eventTitle(reservation.getEvent() != null ? reservation.getEvent().getTitle() : null)
                                .build();
        }

        /**
         * Deletes a reservation by its ID.
         * Validates the user ID and checks if the reservation belongs to the user
         * before deleting.
         *
         * @param userId        The ID of the user requesting the deletion.
         * @param reservationId The ID of the reservation to delete.
         * @return A ResponseEntity containing success message or error information.
         */
        @Override
        @Transactional
        public ResponseEntity<ReservationResponse> deleteReservation(String userId, Long reservationId) {
                try {
                        // Validate User ID
                        Optional<ResponseEntity<ReservationResponse>> userValidationError = validateUserId(userId);
                        if (userValidationError.isPresent()) {
                                return userValidationError.get();
                        }

                        Long userIdLong = Long.parseLong(userId);
                        User user = userRepository.findById(userIdLong).orElse(null);
                        if (user == null) {
                                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_USER_NOT_FOUND)
                                                                .build());
                        }

                        // Check if reservation exists
                        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
                        if (reservationOptional.isEmpty()) {
                                String errorMessage = "Reserva no encontrada con ID: " + reservationId;
                                log.warn(errorMessage);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(errorMessage)
                                                                .build());
                        }

                        Reservation reservation = reservationOptional.get();

                        // Check if reservation belongs to the user
                        if (reservation.getUser() == null || !reservation.getUser().getId().equals(userIdLong)) {
                                String errorMessage = "La reserva no pertenece al usuario especificado";
                                log.warn(errorMessage + " - Usuario ID: {}, Reserva ID: {}", userId, reservationId);
                                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(errorMessage)
                                                                .build());
                        }

                        // Delete reservation
                        reservationRepository.delete(reservation);

                        return ResponseEntity.ok(ReservationResponse.builder()
                                        .showMessage(true)
                                        .message("Reserva eliminada exitosamente")
                                        .build());

                } catch (Exception e) {
                        log.error("Error al eliminar la reserva con ID: " + reservationId, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ReservationResponse.builder()
                                                        .showMessage(true)
                                                        .message("Error al eliminar la reserva")
                                                        .build());
                }
        }

        /**
         * Gets all reservations for a user.
         * Validates the user ID before fetching the reservations.
         *
         * @param userId The ID of the user requesting the reservations.
         * @return A ResponseEntity containing the list of reservations or an error
         *         message.
         */
        @Override
        @Transactional(readOnly = true)
        public ResponseEntity<AllReservationsResponse> getUserReservations(String userId) {
                try {
                        // Validate User ID
                        Optional<ResponseEntity<ReservationResponse>> userValidationError = validateUserId(userId);
                        if (userValidationError.isPresent()) {
                                return ResponseEntity.status(userValidationError.get().getStatusCode())
                                                .body(AllReservationsResponse.builder()
                                                                .showMessage(true)
                                                                .message(userValidationError.get().getBody()
                                                                                .getMessage())
                                                                .build());
                        }

                        Long userIdLong = Long.parseLong(userId);
                        User user = userRepository.findById(userIdLong).orElse(null);
                        if (user == null) {
                                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(AllReservationsResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_USER_NOT_FOUND)
                                                                .build());
                        }

                        // Get reservations
                        List<Reservation> reservations = reservationRepository
                                        .findByUserIdOrderByReservationDateDesc(userIdLong);

                        // Convert to DTOs
                        List<ReservationDTO> reservationDTOs = reservations.stream()
                                        .map(this::convertToDTO).toList();

                        // Return response
                        return ResponseEntity.ok(AllReservationsResponse.builder()
                                        .reservations(reservationDTOs)
                                        .showMessage(false)
                                        .build());

                } catch (Exception e) {
                        log.error("Error al obtener reservas del usuario con ID: " + userId, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(AllReservationsResponse.builder()
                                                        .showMessage(true)
                                                        .message("Error al obtener reservas")
                                                        .build());
                }
        }

        /**
         * Gets a specific reservation for a user.
         * Validates the user ID and checks if the reservation belongs to the user.
         *
         * @param userId        The ID of the user requesting the reservation.
         * @param reservationId The ID of the reservation to retrieve.
         * @return A ResponseEntity containing the reservation or an error message.
         */
        @Override
        @Transactional(readOnly = true)
        public ResponseEntity<ReservationResponse> getUserReservationById(String userId, Long reservationId) {
                try {
                        // Validate User ID
                        Optional<ResponseEntity<ReservationResponse>> userValidationError = validateUserId(userId);
                        if (userValidationError.isPresent()) {
                                return userValidationError.get();
                        }

                        Long userIdLong = Long.parseLong(userId);
                        User user = userRepository.findById(userIdLong).orElse(null);
                        if (user == null) {
                                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_USER_NOT_FOUND)
                                                                .build());
                        }

                        // Find reservation by id and user id
                        Optional<Reservation> reservationOptional = reservationRepository
                                        .findByIdAndUserId(reservationId, userIdLong);

                        if (reservationOptional.isEmpty()) {
                                String errorMessage = "Reserva no encontrada o no pertenece al usuario";
                                log.warn(errorMessage + " - Usuario ID: {}, Reserva ID: {}", userId, reservationId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(errorMessage)
                                                                .build());
                        }

                        // Convert to DTO
                        ReservationDTO reservationDTO = convertToDTO(reservationOptional.get());

                        // Return response
                        return ResponseEntity.ok(ReservationResponse.builder()
                                        .reservation(reservationDTO)
                                        .showMessage(false)
                                        .build());

                } catch (Exception e) {
                        log.error("Error al obtener la reserva con ID: " + reservationId, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ReservationResponse.builder()
                                                        .showMessage(true)
                                                        .message("Error al obtener la reserva")
                                                        .build());
                }
        }

        /**
         * Updates an existing reservation with the provided data.
         * Validates the user ID and checks if the reservation belongs to the user.
         *
         * @param userId               The ID of the user requesting the update.
         * @param reservationId        The ID of the reservation to update.
         * @param reservationUpdateDTO Data with which to update the reservation.
         * @return A ResponseEntity containing the updated reservation or an error
         *         message.
         */
        @Override
        @Transactional
        public ResponseEntity<ReservationResponse> updateReservation(String userId, Long reservationId,
                        ReservationUpdateDTO reservationUpdateDTO) {
                try {
                        // Validate User ID
                        Optional<ResponseEntity<ReservationResponse>> userValidationError = validateUserId(userId);
                        if (userValidationError.isPresent()) {
                                return userValidationError.get();
                        }

                        Long userIdLong = Long.parseLong(userId);
                        User user = userRepository.findById(userIdLong).orElse(null);
                        if (user == null) {
                                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(ERROR_USER_NOT_FOUND)
                                                                .build());
                        }

                        // Find reservation by id and user id
                        Optional<Reservation> reservationOptional = reservationRepository
                                        .findByIdAndUserId(reservationId, userIdLong);

                        if (reservationOptional.isEmpty()) {
                                String errorMessage = "Reserva no encontrada o no pertenece al usuario";
                                log.warn(errorMessage + " - Usuario ID: {}, Reserva ID: {}", userId, reservationId);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ReservationResponse.builder()
                                                                .showMessage(true)
                                                                .message(errorMessage)
                                                                .build());
                        }

                        // Update reservation
                        Reservation reservation = reservationOptional.get();

                        if (reservationUpdateDTO.getStatus() != null && !reservationUpdateDTO.getStatus().isEmpty()) {
                                reservation.setStatus(reservationUpdateDTO.getStatus());
                        }

                        // Save updated reservation
                        Reservation updatedReservation = reservationRepository.save(reservation);

                        // Convert to DTO
                        ReservationDTO reservationDTO = convertToDTO(updatedReservation);

                        // Return response
                        return ResponseEntity.ok(ReservationResponse.builder()
                                        .reservation(reservationDTO)
                                        .showMessage(true)
                                        .message("Reserva actualizada exitosamente")
                                        .build());

                } catch (Exception e) {
                        log.error("Error al actualizar la reserva con ID: " + reservationId, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ReservationResponse.builder()
                                                        .showMessage(true)
                                                        .message("Error al actualizar la reserva")
                                                        .build());
                }
        }
}