package com.co.davivienda.ti.prueba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.entities.Event;
import com.co.davivienda.ti.prueba.models.dto.EventDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.repositories.EventRepository;
import com.co.davivienda.ti.prueba.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EventService implements IEventService {

    private static final String ERROR_INVALID_USER_ID = "ID de usuario inválido";
    private static final String ERROR_USER_NOT_FOUND = "Usuario no encontrado";
    private static final String ERROR_GETTING_EVENTS = "Error al obtener eventos";

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves all events from the database and returns them in a response entity.
     * Validates the user ID before fetching the events.
     *
     * @param userId The ID of the user requesting the events.
     * @return A ResponseEntity containing the list of events or an error message.
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AllEventsResponse> getAllEvents(String userId) {
        try {
            // Validations
            Optional<ResponseEntity<AllEventsResponse>> validationError = validateUserId(userId);
            if (validationError.isPresent()) {
                return validationError.get();
            }

            Long userIdLong = Long.parseLong(userId);
            if (!userRepository.existsById(userIdLong)) {
                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(AllEventsResponse.builder()
                                .showMessage(true)
                                .message(ERROR_USER_NOT_FOUND)
                                .build());
            }

            // Get all events
            List<Event> events = eventRepository.findAll();

            // Convert events to DTOs
            List<EventDTO> eventDTOs = events.stream()
                    .map(this::convertToDTO).toList();

            // Build and return the response
            return ResponseEntity.ok(AllEventsResponse.builder()
                    .events(eventDTOs)
                    .showMessage(false)
                    .build());

        } catch (Exception e) {
            log.error(ERROR_GETTING_EVENTS, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AllEventsResponse.builder()
                            .showMessage(true)
                            .message(ERROR_GETTING_EVENTS)
                            .build());
        }
    }

    /**
     * Converts an Event entity to an EventDTO.
     *
     * @param event The Event entity to convert.
     * @return The converted EventDTO.
     */
    private EventDTO convertToDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .categoryId(event.getCategory() != null ? event.getCategory().getId() : null)
                .categoryName(event.getCategory() != null ? event.getCategory().getName() : null)
                .build();
    }

    /**
     * Validates and parses the user ID from string to Long.
     *
     * @param userId The user ID as string to validate.
     * @return Optional containing error response if validation fails, or empty if
     *         successful.
     */
    private Optional<ResponseEntity<AllEventsResponse>> validateUserId(String userId) {
        try {
            Long.parseLong(userId);
            return Optional.empty();
        } catch (NumberFormatException e) {
            log.warn(ERROR_INVALID_USER_ID + ": {}", userId);
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AllEventsResponse.builder()
                            .showMessage(true)
                            .message(ERROR_INVALID_USER_ID)
                            .build()));
        }
    }
}