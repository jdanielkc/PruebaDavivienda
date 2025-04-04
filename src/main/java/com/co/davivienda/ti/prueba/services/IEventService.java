package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.response.AllEventsResponse;


public interface IEventService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllEventsResponse> getAllEvents(String userId);
}
