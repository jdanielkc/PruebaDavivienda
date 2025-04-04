package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.EventUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.models.response.EventResponse;

public interface IEventService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllEventsResponse> getAllEvents(String userId);

    @Transactional(readOnly = true)
    public ResponseEntity<EventResponse> getEventById(String userId, Long eventId);
    
    @Transactional
    public ResponseEntity<EventResponse> updateEvent(String userId, Long eventId, EventUpdateDTO eventUpdateDTO);
    
    @Transactional
    public ResponseEntity<EventResponse> deleteEvent(String userId, Long eventId);
}