package com.co.davivienda.ti.prueba.models.response;

import com.co.davivienda.ti.prueba.models.dto.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventResponse extends BaseResponse {
    private EventDTO event;
}