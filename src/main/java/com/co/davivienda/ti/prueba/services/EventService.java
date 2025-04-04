package com.co.davivienda.ti.prueba.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.entities.Event;
import com.co.davivienda.ti.prueba.models.dto.EventDTO;
import com.co.davivienda.ti.prueba.models.dto.EventUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.models.response.EventResponse;
import com.co.davivienda.ti.prueba.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

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

    /**
     * Retrieves an event by its ID and user ID, returning it in a response entity.
     * Validates the user ID before fetching the event.
     *
     * @param userId  The ID of the user requesting the event.
     * @param eventId The ID of the event to retrieve.
     * @return A ResponseEntity containing the event or an error message.
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<EventResponse> getEventById(String userId, Long eventId) {
        try {
            // Validations
            Optional<ResponseEntity<EventResponse>> validationError = validateUserIdForEvent(userId);
            if (validationError.isPresent()) {
                return validationError.get();
            }

            Long userIdLong = Long.parseLong(userId);
            if (!userRepository.existsById(userIdLong)) {
                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(ERROR_USER_NOT_FOUND)
                                .build());
            }

            // Get event by ID
            Optional<Event> eventOptional = eventRepository.findById(eventId);

            if (eventOptional.isEmpty()) {
                String errorMessage = "Evento no encontrado con ID: " + eventId;
                log.warn(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(errorMessage)
                                .build());
            }

            // Convert event to DTO
            EventDTO eventDTO = convertToDTO(eventOptional.get());

            // Build and return the response
            return ResponseEntity.ok(EventResponse.builder()
                    .event(eventDTO)
                    .showMessage(false)
                    .build());

        } catch (Exception e) {
            log.error("Error al obtener el evento con ID: " + eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EventResponse.builder()
                            .showMessage(true)
                            .message("Error al obtener el evento")
                            .build());
        }
    }

    /**
     * Validates and parses the user ID from string to Long for event operations.
     *
     * @param userId The user ID as string to validate.
     * @return Optional containing error response if validation fails, or empty if
     *         successful.
     */
    private Optional<ResponseEntity<EventResponse>> validateUserIdForEvent(String userId) {
        try {
            Long.parseLong(userId);
            return Optional.empty();
        } catch (NumberFormatException e) {
            log.warn(ERROR_INVALID_USER_ID + ": {}", userId);
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(EventResponse.builder()
                            .showMessage(true)
                            .message(ERROR_INVALID_USER_ID)
                            .build()));
        }
    }

    /**
     * Updates an existing event with the provided data.
     * Validates the user ID before updating the event.
     *
     * @param userId         The ID of the user requesting the update.
     * @param eventId        The ID of the event to update.
     * @param eventUpdateDTO Data with which to update the event.
     * @return A ResponseEntity containing the updated event or an error message.
     */
    @Override
    @Transactional
    public ResponseEntity<EventResponse> updateEvent(String userId, Long eventId, EventUpdateDTO eventUpdateDTO) {
        try {
            // Validations
            Optional<ResponseEntity<EventResponse>> validationError = validateUserIdForEvent(userId);
            if (validationError.isPresent()) {
                return validationError.get();
            }

            Long userIdLong = Long.parseLong(userId);
            if (!userRepository.existsById(userIdLong)) {
                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(ERROR_USER_NOT_FOUND)
                                .build());
            }

            // Get event by ID
            Optional<Event> eventOptional = eventRepository.findById(eventId);

            if (eventOptional.isEmpty()) {
                String errorMessage = "Evento no encontrado con ID: " + eventId;
                log.warn(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(errorMessage)
                                .build());
            }

            // Update event
            Event event = eventOptional.get();

            if (eventUpdateDTO.getTitle() != null) {
                event.setTitle(eventUpdateDTO.getTitle());
            }
            if (eventUpdateDTO.getDescription() != null) {
                event.setDescription(eventUpdateDTO.getDescription());
            }
            if (eventUpdateDTO.getEventDate() != null) {
                event.setEventDate(eventUpdateDTO.getEventDate());
            }
            if (eventUpdateDTO.getLocation() != null) {
                event.setLocation(eventUpdateDTO.getLocation());
            }
            if (eventUpdateDTO.getCapacity() != null) {
                event.setCapacity(eventUpdateDTO.getCapacity());
            }
            if (eventUpdateDTO.getCategoryId() != null) {
                // Get category by ID - you might want to add validation here
                event.setCategory(categoryRepository.findById(eventUpdateDTO.getCategoryId()).orElse(null));
            }

            // Update last modified date
            event.setUpdateDate(LocalDate.now());

            // Save updated event
            Event updatedEvent = eventRepository.save(event);

            // Convert to DTO and return
            EventDTO eventDTO = convertToDTO(updatedEvent);

            return ResponseEntity.ok(EventResponse.builder()
                    .event(eventDTO)
                    .showMessage(true)
                    .message("Evento actualizado exitosamente")
                    .build());

        } catch (Exception e) {
            log.error("Error al actualizar el evento con ID: " + eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EventResponse.builder()
                            .showMessage(true)
                            .message("Error al actualizar el evento")
                            .build());
        }
    }

    /**
     * Deletes an event by its ID.
     * Validates the user ID before deleting the event.
     *
     * @param userId  The ID of the user requesting the deletion.
     * @param eventId The ID of the event to delete.
     * @return A ResponseEntity containing success message or error information.
     */
    @Override
    @Transactional
    public ResponseEntity<EventResponse> deleteEvent(String userId, Long eventId) {
        try {
            // Validations
            Optional<ResponseEntity<EventResponse>> validationError = validateUserIdForEvent(userId);
            if (validationError.isPresent()) {
                return validationError.get();
            }

            Long userIdLong = Long.parseLong(userId);
            if (!userRepository.existsById(userIdLong)) {
                log.warn(ERROR_USER_NOT_FOUND + " con ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(ERROR_USER_NOT_FOUND)
                                .build());
            }

            // Get event by ID
            Optional<Event> eventOptional = eventRepository.findById(eventId);

            if (eventOptional.isEmpty()) {
                String errorMessage = "Evento no encontrado con ID: " + eventId;
                log.warn(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(errorMessage)
                                .build());
            }

            // Delete event
            Event event = eventOptional.get();

            // Check if the event has reservations associated with it
            if (event.getReservations() != null && !event.getReservations().isEmpty()) {
                String errorMessage = "No se puede eliminar el evento porque tiene reservas asociadas";
                log.warn(errorMessage + " - Evento ID: {}", eventId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(EventResponse.builder()
                                .showMessage(true)
                                .message(errorMessage)
                                .build());
            }

            // Delete the event
            eventRepository.delete(event);

            return ResponseEntity.ok(EventResponse.builder()
                    .showMessage(true)
                    .message("Evento eliminado exitosamente")
                    .build());

        } catch (Exception e) {
            log.error("Error al eliminar el evento con ID: " + eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EventResponse.builder()
                            .showMessage(true)
                            .message("Error al eliminar el evento")
                            .build());
        }
    }
}