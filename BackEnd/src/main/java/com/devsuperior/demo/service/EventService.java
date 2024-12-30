package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.service.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllEvents(Pageable pageable) {

        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(EventDTO::new);
    }

    @Transactional
    public EventDTO insertEvent(EventDTO eventDTO) {

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());

        City city = cityRepository.findById(eventDTO.getCityId()).orElseThrow(
                () -> new IdNotFoundException("City not found"));
        event.setCity(city);

        event = eventRepository.save(event);
        return new EventDTO(event);

    }
}
