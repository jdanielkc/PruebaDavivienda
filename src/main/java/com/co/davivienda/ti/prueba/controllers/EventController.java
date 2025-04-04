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

import com.co.davivienda.ti.prueba.models.dto.EventCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.EventUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.models.response.EventResponse;
import com.co.davivienda.ti.prueba.services.IEventService;

import lombok.AllArgsConstructor;

/**
 * EventController handles event-related operations such as retrieving all events, creating, updating, and deleting events.
 * It provides endpoints for event management functionalities.
 *
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final IEventService eventService;

    /**
     * Endpoint to retrieve all events for a specific user.
     * 
     * @param userId the ID of the user
     * @return the response entity containing all events for the user
     */
    @GetMapping("/all/{userId}")
    public ResponseEntity<AllEventsResponse> getAllEvents(@PathVariable String userId) {
        return eventService.getAllEvents(userId);
    }

    /**
     * Endpoint to retrieve an event by its ID for a specific user.
     * 
     * @param userId the ID of the user
     * @param eventId the ID of the event
     * @return the response entity containing the event details
     */
    @GetMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable String userId,
            @PathVariable Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    /**
     * Endpoint to create a new event for a specific user.
     * 
     * @param userId the ID of the user
     * @param eventCreateDTO the event creation request
     * @return the response entity containing the created event details
     */
    @PostMapping("/{userId}")
    public ResponseEntity<EventResponse> createEvent(
            @PathVariable String userId,
            @RequestBody EventCreateDTO eventCreateDTO) {
        return eventService.createEvent(userId, eventCreateDTO);
    }

    /**
     * Endpoint to update an existing event for a specific user.
     * 
     * @param userId the ID of the user
     * @param eventId the ID of the event
     * @param eventUpdateDTO the event update request
     * @return the response entity containing the updated event details
     */
    @PutMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable String userId,
            @PathVariable Long eventId,
            @RequestBody EventUpdateDTO eventUpdateDTO) {
        return eventService.updateEvent(userId, eventId, eventUpdateDTO);
    }

    /**
     * Endpoint to delete an event for a specific user.
     * 
     * @param userId the ID of the user
     * @param eventId the ID of the event
     * @return the response entity indicating the deletion result
     */
    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> deleteEvent(
            @PathVariable String userId,
            @PathVariable Long eventId) {
        return eventService.deleteEvent(userId, eventId);
    }
}