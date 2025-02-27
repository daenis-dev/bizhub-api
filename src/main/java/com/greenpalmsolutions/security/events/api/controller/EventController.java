package com.greenpalmsolutions.security.events.api.controller;

import com.greenpalmsolutions.security.events.api.behavior.*;
import com.greenpalmsolutions.security.events.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final CreateEvent createEvent;
    private final FindEvents findEvents;
    private final UpdateEvent updateEvent;
    private final DeleteEvent deleteEvent;

    @PostMapping("/v1/events")
    public ResponseEntity<EventDetails> createEvent(
            @RequestParam("name") String name,
            @RequestParam("start-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime startDateTime,
            @RequestParam("end-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime endDateTime) {
        return new ResponseEntity<>(
                createEvent.createEventForRequest(new CreateEventRequest()
                        .withName(name)
                        .withStartDateTime(startDateTime)
                        .withEndDateTime(endDateTime)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/events")
    public ResponseEntity<List<EventDetails>> findEvents() {
        return ResponseEntity.ok(findEvents.findEventsForCurrentUser());
    }

    @PutMapping("/v1/events/{id}")
    public ResponseEntity<EventDetails> updateEvent(
            @PathVariable("id") String id,
            @RequestParam("name") String name,
            @RequestParam("start-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime startDateTime,
            @RequestParam("end-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime endDateTime) {
        return ResponseEntity.ok(updateEvent.updateEventForRequest(new UpdateEventRequest()
                .withEventId(id)
                .withName(name)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)));
    }

    @DeleteMapping("/v1/events/{id}")
    public ResponseEntity<?> deleteEventForId(@PathVariable("id") String id) {
        deleteEvent.deleteEventForRequest(new DeleteEventRequest().withEventId(id));
        return ResponseEntity.ok().build();
    }
}
