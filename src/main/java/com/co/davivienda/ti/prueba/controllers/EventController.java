package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.dto.EventUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.models.response.EventResponse;
import com.co.davivienda.ti.prueba.services.IEventService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final IEventService eventService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<AllEventsResponse> getAllEvents(@PathVariable String userId) {
        return eventService.getAllEvents(userId);
    }

    @GetMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable String userId,
            @PathVariable Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    @PutMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable String userId,
            @PathVariable Long eventId,
            @RequestBody EventUpdateDTO eventUpdateDTO) {
        return eventService.updateEvent(userId, eventId, eventUpdateDTO);
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<EventResponse> deleteEvent(
            @PathVariable String userId,
            @PathVariable Long eventId) {
        return eventService.deleteEvent(userId, eventId);
    }
}