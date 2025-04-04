package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.EventCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.EventUpdateDTO;
import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;
import com.co.davivienda.ti.prueba.models.response.EventResponse;

/**
 * IEventService is an interface that defines the contract for event-related operations. It
 * provides methods to retrieve all events, create, update, and delete events. This interface is
 * implemented by the EventService class.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
public interface IEventService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllEventsResponse> getAllEvents(String userId);

    @Transactional(readOnly = true)
    public ResponseEntity<EventResponse> getEventById(String userId, Long eventId);

    @Transactional
    public ResponseEntity<EventResponse> createEvent(String userId, EventCreateDTO eventCreateDTO);

    @Transactional
    public ResponseEntity<EventResponse> updateEvent(String userId, Long eventId, EventUpdateDTO eventUpdateDTO);

    @Transactional
    public ResponseEntity<EventResponse> deleteEvent(String userId, Long eventId);
}