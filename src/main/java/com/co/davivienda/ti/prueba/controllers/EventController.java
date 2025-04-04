package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
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

}
